package controller.customer;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.genericdao.RollbackException;
import org.genericdao.Transaction;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import controller.Action;
import databeans.Customer;
import databeans.CustomerFund;
import databeans.CustomerFundView;
import databeans.Fund;
import databeans.MyTransaction;
import databeans.Position;
import formbeans.SellFundForm;
import model.CustomerDAO;
import model.FundDAO;
import model.Model;
import model.PositionDAO;
import model.TransactionDAO;
import model.viewDAO.CustomerFundDAO;
import util.Common;

public class CustomerSellFundAction extends Action {
    private FormBeanFactory<SellFundForm> formBeanFactory = FormBeanFactory.getInstance(SellFundForm.class);
    private PositionDAO positionDAO;
    private TransactionDAO transactionDAO;
    private FundDAO fundDAO;
    private CustomerFundDAO customerFundDAO;
    private CustomerDAO customerDAO;

    public CustomerSellFundAction(Model model) {
        customerDAO = model.getCustomerDAO();
        positionDAO = model.getPositionDAO();
        transactionDAO = model.getTransactionDAO();
        fundDAO = model.getFundDAO();
        customerFundDAO = model.getCustomerFundDAO();
    }

    @Override
    public String getName() {
        return "sell-fund.do";
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
            if (request.getParameter("fund-id") == null) {
                CustomerFund[] customerFunds = customerFundDAO.getCustomerFund(customer.getCustomerId());
                CustomerFundView[] customerFundViews = new CustomerFundView[customerFunds.length];
                for (int i = 0; i < customerFunds.length; i++) {
                    CustomerFundView customerFundView = new CustomerFundView();
                    customerFundView.setFundName(customerFunds[i].getFundName());
                    customerFundView.setFundId(customerFunds[i].getFundId());
                    customerFundView.setFundTicker(customerFunds[i].getFundTicker());
                    customerFundView.setFundPrice(Common.convertLongPriceToString(customerFunds[i].getFundPrice()));
                    customerFundView.setShares(Common.convertLongShareToString(customerFunds[i].getShares()));
                    customerFundView.setTotal(Common.convertLongPriceToString(customerFunds[i].getTotal()));
                    customerFundViews[i] = customerFundView;
                }

                request.setAttribute("customerFunds", customerFundViews);
                return "customer/sell-fund.jsp";
            }

            SellFundForm form = formBeanFactory.create(request);
            request.setAttribute("form", form);

            long fundId = Long.parseLong(request.getParameter("fund-id"));
            Fund fund = fundDAO.getById(fundId);
            if (fund == null) {
                errors.add("No such fund");
                return "error.jsp";
            }

            Position position = positionDAO.getByCustomerIdAndFundId(customer.getCustomerId(), fundId);
            if (position == null) {
                errors.add("No available shares to sell");
                return "error.jsp";
            }

            long availableShares = position.getShares();
            String availableSharesString = Common.convertLongShareToString(position.getShares());
            String lastSharePrice = Common.convertLongPriceToString(fund.getFundPrice());

            request.setAttribute("fund", fund);
            request.setAttribute("fundId", fundId);
            request.setAttribute("lastSharePrice", lastSharePrice);
            request.setAttribute("availableShares", availableSharesString);

            // If no params were passed, return with no errors so that the form will be
            // presented (we assume for the first time).
            if (!form.isPresent()) {
                return "customer/sell-this-fund.jsp";
            }

            // Any validation errors?
            errors.addAll(form.getValidationErrors());
            if (errors.size() != 0) {
                return "customer/sell-this-fund.jsp";
            }

            double sharesDouble = Double.parseDouble(form.getShares());
            long shares = (long) (sharesDouble * 1000);

            Transaction.begin();
            {
                position = positionDAO.getByCustomerIdAndFundId(customer.getCustomerId(), fundId);

                availableShares = position.getShares();
                if (shares > availableShares) {
                    errors.add("No enough shares in your account");
                    return "customer/sell-this-fund.jsp";
                }

                MyTransaction transaction = new MyTransaction();
                transaction.setCustomerId(customer.getCustomerId());
                transaction.setFundId(fundId);
                transaction.setTransactionType(MyTransaction.SELL_FUND);
                transaction.setShares(shares);
                transactionDAO.addNoT(transaction);

                assert availableShares >= shares;
                if (availableShares == shares) {
                    positionDAO.delete(position.getPositionId());
                } else {
                    position.setShares(availableShares - shares);
                    positionDAO.updateNoT(position);
                }
            }
            Transaction.commit();

            availableSharesString = Common.convertLongShareToString(availableShares - shares);
            request.setAttribute("availableShares", availableSharesString);
            request.setAttribute("message", "Request accepted! Transaction will be made after transition day.");

            return "customer/sell-this-fund.jsp";
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
