package controller.customer;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.genericdao.RollbackException;

import controller.Action;
import databeans.Customer;
import databeans.Fund;
import databeans.MyTransaction;
import databeans.TransactionHistoryView;
import model.CustomerDAO;
import model.FundDAO;
import model.Model;
import model.TransactionDAO;
import util.Common;

public class CustomerViewTransactionHistoryAction extends Action {
    private TransactionDAO transactionDAO;
    private FundDAO fundDAO;
    private CustomerDAO customerDAO;

    public CustomerViewTransactionHistoryAction(Model model) {
        transactionDAO = model.getTransactionDAO();
        fundDAO = model.getFundDAO();
        customerDAO = model.getCustomerDAO();
    }

    @Override
    public String getName() {
        return "transaction-history.do";
    }

    @Override
    public String perform(HttpServletRequest request) {
        List<String> errors = new ArrayList<String>();
        request.setAttribute("errors", errors);

        if (!customerLoginValidate(request)) {
            return "login.do";
        }

        try {
            Customer customer = (Customer) request.getSession().getAttribute("customer");
            customer = customerDAO.read(customer.getCustomerId());
            List<MyTransaction> history = transactionDAO.getAllTransactionsByCustomer(customer.getCustomerId());
            // sort transactions by newer transaction
            Collections.reverse(history);
            TransactionHistoryView[] transactions = new TransactionHistoryView[history.size()];
            DecimalFormat dfPrice = new DecimalFormat("######0.00");
            DecimalFormat dfShare = new DecimalFormat("######0.000");
            for (int i = 0; i < history.size(); i++) {
                transactions[i] = new TransactionHistoryView();
                // set date
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date d = history.get(i).getExecuteDate();
                String date = d == null ? "-" : simpleDateFormat.format(d);
                transactions[i].setDate(date);

                // set operation
                transactions[i].setType(Common.transIntToOperation(history.get(i).getTransactionType()));

                // set fund name
                // MAY HAVE PROBLEM!!!!!!!!
                Fund fund = fundDAO.read(history.get(i).getFundId());
                if (fund != null) {
                    transactions[i].setFundName(fund.getFundName());
                    transactions[i].setFundTicker(fund.getFundTicker());
                }

                // set fund share
                // MAY HAVE PROBLEM !!!!!
                if (history.get(i).getShares() != 0) {
                    transactions[i].setShares(dfShare.format(((double) history.get(i).getShares()) / 1000));
                }

                // set fund price
                if (history.get(i).getPrice() != 0) {
                    double price = (double) history.get(i).getPrice() / 100;
                    transactions[i].setPrice(dfPrice.format(price));
                }

                // set amount
                if (history.get(i).getAmount() != 0) {
                    double amount = (double) history.get(i).getAmount() / 100;
                    transactions[i].setAmount(dfPrice.format(amount));
                }

                // set status
                if (history.get(i).getExecuteDate() == null) {
                    transactions[i].setStatus("Pending");
                } else {
                    transactions[i].setStatus("Completed");
                }

            }

            request.setAttribute("transactions", transactions);
            return "customer/transaction-history.jsp";
        } catch (RollbackException e) {
            errors.add(e.toString());
            return "customer/transaction-history.jsp";
        }
    }

}
