package controller.customer;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.genericdao.RollbackException;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import controller.Action;
import databeans.Customer;
import formbeans.ChangeCustomerPwdForm;
import model.CustomerDAO;
import model.Model;

public class CustomerChangePwdAction extends Action {
    private FormBeanFactory<ChangeCustomerPwdForm> formBeanFactory = FormBeanFactory
            .getInstance(ChangeCustomerPwdForm.class);
    private CustomerDAO customerDAO;

    public CustomerChangePwdAction(Model model) {
        customerDAO = model.getCustomerDAO();
    }

    public String getName() {
        return "change-pwd.do";
    }

    public String perform(HttpServletRequest request) {
        // Set up error list
        List<String> errors = new ArrayList<String>();
        request.setAttribute("errors", errors);

        // check whether the user logged in
        if (!customerLoginValidate(request)) {
            return "login.do";
        }

        try {
            Customer customer = (Customer) request.getSession(false).getAttribute("customer");
            customer = customerDAO.read(customer.getCustomerId());
            // Load the form parameters into a form bean
            ChangeCustomerPwdForm form = formBeanFactory.create(request);

            // If no params were passed, return with no errors so that the form
            // will be
            // presented (we assume for the first time).
            if (!form.isPresent()) {
                return "customer/change-pwd.jsp";
            }

            // Check for any validation errors
            errors.addAll(form.getValidationErrors());
            if (errors.size() != 0) {
                return "customer/change-pwd.jsp";
            }

            // Change the password
            customerDAO.setPassword(customer.getUserName(), form.getNewPassword());
            request.setAttribute("message", "Password changed");
            return "customer/change-pwd.jsp";
        } catch (FormBeanException e) {
            errors.add(e.toString());
            return "customer/change-pwd.jsp";
        } catch (RollbackException e) {
            errors.add(e.toString());
            return "change-pwd.jsp";
        }
    }
}
