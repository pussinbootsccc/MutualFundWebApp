package controller.employee;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.genericdao.RollbackException;

import controller.Action;
import databeans.Customer;
import databeans.CustomerFund;
import databeans.CustomerFundView;
import databeans.Fund;
import databeans.MyTransaction;
import databeans.TransactionHistoryView;
import model.CustomerDAO;
import model.FundDAO;
import model.Model;
import model.PositionDAO;
import model.TransactionDAO;
import model.viewDAO.CustomerFundDAO;
import util.Common;

public class ViewCustomerDetailAction extends Action {

    private CustomerDAO customerDao;
    private TransactionDAO transactionDao;
    private PositionDAO positionDao;
    private CustomerFundDAO customerFundDao;
    private FundDAO fundDao;

    @Override
    public String getName() {
        return "e_view_customer_detail.do";
    }

    public ViewCustomerDetailAction(Model model) {
        customerDao = model.getCustomerDAO();
        transactionDao = model.getTransactionDAO();
        positionDao = model.getPositionDAO();
        customerFundDao = model.getCustomerFundDAO();
        fundDao = model.getFundDAO();
    }

    /*
     * Get: customer ID Return: customer Name Address cash balance date of the last trading day number of shares of each
     * fund the value of position
     */
    @Override
    public String perform(HttpServletRequest request) {
        List<String> errors = new ArrayList<String>();
        errors = (List<String>) request.getSession().getAttribute("errors");
        request.setAttribute("errors", errors);

        // step1 check whether the user is login and the user has the right to
        // create fund

        if (!employeeLoginValidate(request)) {
            return "login.do";
        }

        String cidString = request.getParameter("customerId");
        if (cidString == null) {
            cidString = "" + request.getSession().getAttribute("customerId");

        } else {
            HttpSession session = request.getSession(false);
            session.setAttribute("customerId", cidString);
        }
        long cid = Integer.parseInt(cidString);

        // check the cid is valid. if so, get the name, address, cash;
        try {
            Customer customer = customerDao.read(cid);
            // TODO make sure it is errors.jsp
            if (customer == null) {
                errors.add("User doesn't exist");
                return "error.jsp";
            }

            // get messageType from session and put it into Attribute, set
            // session null for messageType
            String messageType = (String) request.getSession().getAttribute("messageType");
            request.setAttribute("messageType", messageType);
            request.getSession(false).setAttribute("messageType", null);

            // get message from session and put it into Attribute, set session
            // null for message
            String message = (String) request.getSession().getAttribute("message");
            request.setAttribute("message", message);
            request.getSession(false).setAttribute("message", null);

            request.setAttribute("customer", customer);
            request.setAttribute("balance", Common.convertLongPriceToString(customer.getCash()));

            // get last trading day(Transaction)
            String pattern = "yyyy-MM-dd";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
            MyTransaction myTransaction = transactionDao.getLastTransactionByCustomer(cid);
            if (myTransaction == null) {
                request.setAttribute("lastTransactionDay", "N/A");
            } else {
                Date lastDate = myTransaction.getExecuteDate();
                String dateString = simpleDateFormat.format(lastDate);
                request.setAttribute("lastTransactionDay", dateString);
            }

            // get number of shares of each fund & their value;
            CustomerFund[] customerFunds = customerFundDao.getCustomerFund(cid);
            CustomerFundView[] customerFundViews = new CustomerFundView[customerFunds.length];
            double totalMoney = 0;
            for (int i = 0; i < customerFunds.length; i++) {
                double fundPrice = (double) customerFunds[i].getFundPrice() / 100;
                double shares = (double) customerFunds[i].getShares() / 1000;
                double total = fundPrice * shares;
                totalMoney += Double.valueOf(Common.convertDoublePriceToString(total));

                CustomerFundView customerFundView = new CustomerFundView();
                customerFundView.setFundName(customerFunds[i].getFundName());
                customerFundView.setFundTicker(customerFunds[i].getFundTicker());
                customerFundView.setFundPrice(Common.convertDoublePriceToString(fundPrice));
                customerFundView.setShares(Common.convertDoubleShareToString(shares));
                customerFundView.setTotal(Common.convertDoublePriceToString(total));
                customerFundViews[i] = customerFundView;

            }
            request.setAttribute("customerFunds", customerFundViews);
            request.setAttribute("totalMoney", Common.convertDoublePriceToString(totalMoney));

            // get customer transaction history
            List<MyTransaction> history = transactionDao.getAllTransactionsByCustomer(cid);
            Collections.reverse(history);
            TransactionHistoryView[] transactions = new TransactionHistoryView[history.size()];
            for (int i = 0; i < history.size(); i++) {
                transactions[i] = new TransactionHistoryView();
                // set date
                Date d = history.get(i).getExecuteDate();
                String date = d == null ? "-" : simpleDateFormat.format(d);
                transactions[i].setDate(date);

                // set operation
                transactions[i].setType(Common.transIntToOperation(history.get(i).getTransactionType()));

                // set fund name
                // MAY HAVE PROBLEM!!!!!!!!
                Fund fund = fundDao.read(history.get(i).getFundId());
                if (fund != null) {
                    transactions[i].setFundName(fund.getFundName());
                    transactions[i].setFundTicker(fund.getFundTicker());
                }

                // set fund share
                // MAY HAVE PROBLEM !!!!!
                if (history.get(i).getShares() != 0) {
                    transactions[i].setShares(Common.convertLongShareToString(history.get(i).getShares()));
                    // transactions[i].setShares(dfShare.format(((double)
                    // history.get(i).getShares()) / 1000));
                }

                // set fund price
                if (history.get(i).getPrice() != 0) {
                    double price = (double) history.get(i).getPrice() / 100;
                    transactions[i].setPrice(Common.convertDoublePriceToString(price));
                    // transactions[i].setPrice(dfPrice.format(price));
                }

                // set amount
                if (history.get(i).getAmount() != 0) {
                    double amount = (double) history.get(i).getAmount() / 100;
                    transactions[i].setAmount(Common.convertDoublePriceToString(amount));
                    // transactions[i].setAmount(dfPrice.format(amount));
                }

                // set status
                if (history.get(i).getExecuteDate() == null) {
                    transactions[i].setStatus("Pending");
                } else {
                    transactions[i].setStatus("Completed");
                }

            }

            request.setAttribute("transactions", transactions);
            return "/employee/e_customer_details.jsp";

        } catch (RollbackException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }

}
