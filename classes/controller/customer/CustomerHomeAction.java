package controller.customer;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.genericdao.RollbackException;

import controller.Action;
import databeans.Customer;
import databeans.CustomerFund;
import databeans.CustomerFundView;
import databeans.MyTransaction;
import model.CustomerDAO;
import model.Model;
import model.TransactionDAO;
import model.viewDAO.CustomerFundDAO;
import util.Common;

public class CustomerHomeAction extends Action {
    private CustomerFundDAO customerFundDAO;
    private TransactionDAO transactionDAO;
    private CustomerDAO customerDAO;

    public CustomerHomeAction(Model model) {
        customerFundDAO = model.getCustomerFundDAO();
        transactionDAO = model.getTransactionDAO();
        customerDAO = model.getCustomerDAO();
    }

    @Override
    public String getName() {
        return "customer-home.do";
    }

    @Override
    public String perform(HttpServletRequest request) {
        List<String> errors = new ArrayList<String>();
        request.setAttribute("errors", errors);

        if (!customerLoginValidate(request)) {
            return "login.do";
        }
        try {
            if (errors.size() != 0) {
                return "/customer/home.jsp";
            }

            Customer customer = (Customer) request.getSession().getAttribute("customer");
            customer = customerDAO.read(customer.getCustomerId());
            CustomerFund[] customerFunds = customerFundDAO.getCustomerFund(customer.getCustomerId());
            CustomerFundView[] customerFundViews = new CustomerFundView[customerFunds.length];
            for (int i = 0; i < customerFunds.length; i++) {
                CustomerFundView customerFundView = new CustomerFundView();
                customerFundView.setFundName(customerFunds[i].getFundName());
                customerFundView.setFundTicker(customerFunds[i].getFundTicker());
                customerFundView.setFundPrice(Common.convertLongPriceToString(customerFunds[i].getFundPrice()));
                customerFundView.setShares(Common.convertLongShareToString(customerFunds[i].getShares()));
                double fundtotal = ((double) customerFunds[i].getFundPrice() / 100)
                        * ((double) customerFunds[i].getShares() / 1000);
                customerFundView.setTotal(Common.convertDoublePriceToString(fundtotal));
                customerFundViews[i] = customerFundView;
            }

            request.setAttribute("customerFunds", customerFundViews);
            request.setAttribute("available", Common.convertLongPriceToString(customer.getCash()));

            long pending = transactionDAO.getPendingAmountByCustomerId(customer.getCustomerId());
            request.setAttribute("total", Common.convertLongPriceToString(customer.getCash() + pending));

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            if (transactionDAO.getLastTransactionByCustomer(customer.getCustomerId()) == null) {
                request.setAttribute("lastDate", "No Trading");
            } else {
                MyTransaction lastTransaction = transactionDAO.getLastTransactionByCustomer(customer.getCustomerId());
                String lastDate = "";
                if (lastTransaction != null) {
                    lastDate = formatter.format(lastTransaction.getExecuteDate());
                }
                request.setAttribute("lastDate", lastDate);
            }

            return "/customer/home.jsp";
        } catch (RollbackException e) {
            errors.add(e.getMessage());
            return "error.jsp";
        }
    }

}
