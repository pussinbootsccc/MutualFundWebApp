/**
 * Add cancel function in buy fund
 */

package controller.customer;

import java.util.ArrayList;
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
import formbeans.BuyFundForm;
import model.CustomerDAO;
import model.FundDAO;
import model.Model;
import model.TransactionDAO;
import util.Common;

public class CustomerBuyFundAction extends Action {
    private FormBeanFactory<BuyFundForm> formBeanFactory = FormBeanFactory.getInstance(BuyFundForm.class);
    private CustomerDAO customerDAO;
    private TransactionDAO transactionDAO;
    private FundDAO fundDAO;

    public CustomerBuyFundAction(Model model) {
        customerDAO = model.getCustomerDAO();
        transactionDAO = model.getTransactionDAO();
        fundDAO = model.getFundDAO();
    }

    @Override
    public String getName() {
        return "buy-fund.do";
    }

    @Override
    public String perform(HttpServletRequest request) {
        List<String> errors = new ArrayList<String>();
        request.setAttribute("errors", errors);

        if (!customerLoginValidate(request)) {
            return "login.do";
        }

        try {
            if (request.getParameter("fund-id") == null) {
                Fund[] funds = fundDAO.getAllFunds();
                AllFundView[] allFundViews = new AllFundView[funds.length];
                for (int i = 0; i < funds.length; i++) {
                    AllFundView allFundView = new AllFundView();
                    allFundView.setFundName(funds[i].getFundName());
                    allFundView.setFundId(funds[i].getFundId());
                    allFundView.setFundTicker(funds[i].getFundTicker());
                    if (funds[i].getFundPrice() == 0) {
                        allFundView.setFundPrice("-");
                    } else {
                        allFundView.setFundPrice(Common.convertLongPriceToString(funds[i].getFundPrice()));
                    }
                    allFundViews[i] = allFundView;
                }
                request.setAttribute("fundList", allFundViews);
                return "customer/buy-fund.jsp";
            }

            Customer customer = (Customer) request.getSession().getAttribute("customer");
            customer = customerDAO.read(customer.getCustomerId());
            BuyFundForm form = formBeanFactory.create(request);
            request.setAttribute("form", form);

            long fundId = Long.parseLong(request.getParameter("fund-id"));
            Fund fund = fundDAO.getById(fundId);
            if (fund == null) {
                errors.add("No such fund");
                return "error.jsp";
            }

            long availableCash = customer.getCash();
            String availableCashString = Common.convertLongPriceToString(availableCash);
            String lastSharePrice = Common.convertLongPriceToString(fund.getFundPrice());

            request.setAttribute("fund", fund);
            request.setAttribute("fundId", fundId);
            request.setAttribute("lastSharePrice", lastSharePrice);
            request.setAttribute("availableCash", availableCashString);

            // If no params were passed, return with no errors so that the form will be
            // presented (we assume for the first time).
            if (!form.isPresent()) {
                return "customer/buy-this-fund.jsp";
            }

            // Any validation errors?
            errors.addAll(form.getValidationErrors());
            if (errors.size() != 0) {
                return "customer/buy-this-fund.jsp";
            }

            double amountDouble = Double.parseDouble(form.getAmount());
            long amount = (long) (amountDouble * 100);

            Transaction.begin();
            {
                customer = customerDAO.read(customer.getCustomerId());

                availableCash = customer.getCash();
                if (amount > availableCash) {
                    errors.add("No enough amount in your account");
                    return "customer/buy-this-fund.jsp";
                }

                MyTransaction transaction = new MyTransaction();
                transaction.setCustomerId(customer.getCustomerId());
                transaction.setFundId(fundId);
                transaction.setTransactionType(MyTransaction.BUY_FUND);
                transaction.setAmount(amount);
                transactionDAO.addNoT(transaction);

                customer.setCash(availableCash - amount);
                customerDAO.updateNoT(customer);
            }
            Transaction.commit();

            availableCashString = Common.convertLongPriceToString(availableCash - amount);
            request.setAttribute("availableCash", availableCashString);
            request.setAttribute("message", "Request accepted! Transaction will be made after transition day.");

            return "customer/buy-this-fund.jsp";

        } catch (NumberFormatException e) {
            // if fund-id is not long
            errors.add("No such fund");
            return "error.jsp";
        } catch (FormBeanException e) {
            errors.add(e.getMessage());
            return "error.jsp";
        } catch (RollbackException e) {
            errors.add(e.getMessage());
            return "error.jsp";
        } finally {
            if (Transaction.isActive()) {
                Transaction.rollback();
            }
        }
    }

}
