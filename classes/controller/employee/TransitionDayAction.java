package controller.employee;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.genericdao.RollbackException;
import org.genericdao.Transaction;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import controller.Action;
import databeans.AllFundView;
import databeans.Customer;
import databeans.Fund;
import databeans.MyTransaction;
import formbeans.TransactionDayForm;
import model.CustomerDAO;
import model.FundDAO;
import model.FundPriceHistoryDAO;
import model.Model;
import model.PositionDAO;
import model.TransactionDAO;
import util.Common;

public class TransitionDayAction extends Action {

    private FormBeanFactory<TransactionDayForm> formBeanFactory = FormBeanFactory.getInstance(TransactionDayForm.class);

    private TransactionDAO transactionDao;
    private CustomerDAO customerDao;
    private PositionDAO positionDao;
    private FundPriceHistoryDAO fundPriceHistoryDao;
    private FundDAO fundDao;

    @Override
    public String getName() {
        return "e_transition_day.do";
    }

    public TransitionDayAction(Model model) {
        transactionDao = model.getTransactionDAO();
        customerDao = model.getCustomerDAO();
        positionDao = model.getPositionDAO();
        fundPriceHistoryDao = model.getFundPriceHistoryDAO();
        fundDao = model.getFundDAO();
    }

    @Override
    public String perform(HttpServletRequest request) {
        List<String> errors = new ArrayList<String>();
        request.setAttribute("errors", errors);

        // check whether the user is login and the user has the right to create
        // fund
        if (!employeeLoginValidate(request)) {
            return "login.do";
        }

        try {
            Date lastTransitionDay = fundPriceHistoryDao.getLastTransitionDay();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String lastTransitionDayString = simpleDateFormat.format(lastTransitionDay);
            // fill data into form
            Fund[] funds = fundDao.getAllFunds();
            AllFundView[] allFundViews = new AllFundView[funds.length];
            for (int i = 0; i < funds.length; i++) {
                AllFundView allFundView = new AllFundView();
                allFundView.setFundName(funds[i].getFundName());
                allFundView.setFundId(funds[i].getFundId());
                allFundView.setFundTicker(funds[i].getFundTicker());
                allFundView.setFundPrice(Common.convertLongPriceToString(funds[i].getFundPrice()));
                allFundViews[i] = allFundView;
            }
            Date newDay = new Date(lastTransitionDay.getTime() + (1000 * 60 * 60 * 24));
            String newDayString = simpleDateFormat.format(newDay);
            request.setAttribute("fundList", allFundViews);
            request.setAttribute("lastTransitionDay", lastTransitionDayString);
            request.setAttribute("newDay", newDayString);

            TransactionDayForm form = formBeanFactory.create(request);
            if (!form.isPresent()) {

                return "/employee/e_transition_day.jsp";
            }
            errors.addAll(form.getValidationErrors());
            if (errors.size() > 0) {
                return "/employee/e_transition_day.jsp";
            }
            Date today = form.getDateValue();
            // get last transition day
            // check the transition day is after last transition day
            if (lastTransitionDay != null && (lastTransitionDay.after(today) || lastTransitionDay.equals(today))) {
                errors.add("The date is invalid");
                return "/employee/e_transition_day.jsp";
            }

            // check the length of fund first
            int fundsNumber = fundDao.getFundNumbers();
            if (fundsNumber != form.getFundIds().length) {
                errors.add("A new fund has been added, please try again");
                return "/employee/e_transition_day.jsp";
            }

            Transaction.begin();
            // update all funds price; Two table impacted; fundpricehistory & fund
            // create fundPriceHistory records
            // System.out.println(request.getParameter("fundPrices"));

            long[] longFundPrices = new long[form.getFundPrices().length];
            double[] fundsPrices = new double[form.getFundPrices().length];
            for (int i = 0; i < form.getFundPrices().length; i++) {
                fundsPrices[i] = Double.parseDouble(form.getFundPrices()[i]);
                longFundPrices[i] = (long) (fundsPrices[i] * 100);
            }
            fundPriceHistoryDao.UpdateAllFundsPrice(form.getIdValues(), longFundPrices, today);
            // update fund prices in fund table
            fundDao.updateAllFundPrices(form.getIdValues(), longFundPrices);

            // handle all pending transactions;
            MyTransaction[] transactions = transactionDao.getPendingTransactions();

            for (MyTransaction myTransaction : transactions) {
                /*
                 * transactionType: 1: sell 2: buy 3: deposit check 4: request check
                 */
                int type = myTransaction.getTransactionType();
                if (type == MyTransaction.SELL_FUND) {
                    // sell 1 deduct the position 2 add cash
                    double shares = (double) myTransaction.getShares() / 1000;
                    // TODO the data type if different
                    double currentPrice = (double) fundPriceHistoryDao.getCurrentPrice(myTransaction.getFundId())
                            .getPrice() / 100;
                    String amountString = Common.convertDoublePriceToString(shares * currentPrice);
                    long amount = (long) (Double.valueOf(amountString) * 100);

                    myTransaction.setAmount(amount);
                    myTransaction.setPrice(fundPriceHistoryDao.getCurrentPrice(myTransaction.getFundId()).getPrice());
                    Customer customer = customerDao.read(myTransaction.getCustomerId());
                    customer.setCash(customer.getCash() + amount);
                    customerDao.update(customer);
                } else if (type == MyTransaction.BUY_FUND) {
                    // buy add position
                    double amount = (double) myTransaction.getAmount() / 100;
                    double currentPrice = (double) fundPriceHistoryDao.getCurrentPrice(myTransaction.getFundId())
                            .getPrice() / 100;
                    long shares = (long) (amount / currentPrice * 1000);
                    myTransaction.setPrice(fundPriceHistoryDao.getCurrentPrice(myTransaction.getFundId()).getPrice());
                    myTransaction.setShares(shares);
                    positionDao.updatePositionInTransactionDay(myTransaction.getFundId(), myTransaction.getCustomerId(),
                            shares, 2);

                } else if (type == MyTransaction.DEPOSIT_CHECK) {
                    // deposit
                    long amount = myTransaction.getAmount();
                    Customer customer = customerDao.read(myTransaction.getCustomerId());
                    customer.setCash(customer.getCash() + amount);
                    customerDao.update(customer);

                } else if (type == 4) {
                    // request check
                    // long amount = (long) myTransaction.getAmount();
                    // Customer customer =
                    // customerDao.read(myTransaction.getCustomerId());
                    // customer.setCash(customer.getCash() - amount);
                    // customerDao.updateCustomer(customer);
                }

                // Add Date to all;
                myTransaction.setExecuteDate(today);
                transactionDao.update(myTransaction);
            }
            Transaction.commit();

            request.setAttribute("message", "Handle all pending transactions successfully");
            return "e_transition_day.do";
        } catch (FormBeanException e) {
            errors.add(e.getMessage());
            return "error.jsp";
        } catch (RollbackException e) {
            errors.add(e.getMessage());
            return "error.jsp";
        } finally {
            if (Transaction.isActive())
                Transaction.rollback();
        }

    }

}
