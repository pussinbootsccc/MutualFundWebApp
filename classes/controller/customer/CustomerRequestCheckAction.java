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
import databeans.MyTransaction;
import formbeans.RequestCheckForm;
import model.CustomerDAO;
import model.Model;
import model.TransactionDAO;

public class CustomerRequestCheckAction extends Action {
    private FormBeanFactory<RequestCheckForm> formBeanFactory = FormBeanFactory.getInstance(RequestCheckForm.class);
    private CustomerDAO customerDAO;
    private TransactionDAO transactionDAO;

    public CustomerRequestCheckAction(Model model) {
        customerDAO = model.getCustomerDAO();
        transactionDAO = model.getTransactionDAO();
    }

    @Override
    public String getName() {
        return "request-check.do";
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
            RequestCheckForm form = formBeanFactory.create(request);
            request.setAttribute("form", form);

            // If no params were passed, return with no errors so that the form will be
            // presented (we assume for the first time).
            if (!form.isPresent()) {
                return "customer/request-check.jsp";
            }

            // Any validation errors?
            errors.addAll(form.getValidationErrors());
            if (errors.size() != 0) {
                return "customer/request-check.jsp";
            }

            double amountDouble = Double.parseDouble(form.getAmount());
            long amount = (long) (amountDouble * 100);

            Transaction.begin();
            {
                customer = customerDAO.read(customer.getCustomerId());

                long availableCash = customer.getCash();
                if (amount > availableCash) {
                    errors.add("No enough amount in your account");
                    return "customer/request-check.jsp";
                }

                MyTransaction transaction = new MyTransaction();
                transaction.setCustomerId(customer.getCustomerId());
                transaction.setTransactionType(MyTransaction.REQUEST_CHECK);
                transaction.setAmount(amount);
                transactionDAO.addNoT(transaction);

                customer.setCash(availableCash - amount);
                customerDAO.updateNoT(customer);
            }
            Transaction.commit();
            request.setAttribute("message", "Request accepted! Transaction will be made after transition day.");

            return "customer/request-check.jsp";
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
