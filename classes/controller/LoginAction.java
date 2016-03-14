package controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.genericdao.RollbackException;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import databeans.Customer;
import databeans.Employee;
import formbeans.LoginForm;
import model.CustomerDAO;
import model.EmployeeDAO;
import model.Model;

public class LoginAction extends Action {
    private FormBeanFactory<LoginForm> formBeanFactory = FormBeanFactory.getInstance(LoginForm.class);

    private CustomerDAO customerDAO;
    private EmployeeDAO employeeDAO;

    public LoginAction(Model model) {
        customerDAO = model.getCustomerDAO();
        employeeDAO = model.getEmployeeDAO();
    }

    @Override
    public String getName() {
        return "login.do";
    }

    @Override
    public String perform(HttpServletRequest request) {
        List<String> errors = new ArrayList<String>();
        request.setAttribute("errors", errors);

        HttpSession session = request.getSession(false);
        if (session != null) {
            if (session.getAttribute("customer") != null) {
                return "customer-home.do";
            }

            if (session.getAttribute("employee") != null) {
                return "employee-home.do";
            }
        }

        String userGroup = request.getParameter("user-group");
        try {
            LoginForm form = formBeanFactory.create(request);
            request.setAttribute("form", form);

            // If no params were passed, return with no errors so that the form
            // will be
            // presented (we assume for the first time).
            if (!form.isPresent()) {
                return "login.jsp";
            }

            // Any validation errors?
            errors.addAll(form.getValidationErrors());
            if (errors.size() != 0) {
                return "login.jsp";
            }

            // Look up the user
            Customer customer = customerDAO.getByUserName(form.getUserName());
            Employee employee = employeeDAO.getByUserName(form.getUserName());

            if (userGroup == null || !(userGroup.equals("customer") || userGroup.equals("employee"))) {
                errors.add("Please choose a group to login: as customer or as emloyee");
                return "login.jsp";
            }

            if (userGroup.equals("customer")) {
                if (customer == null) {
                    errors.add("Username is not found");
                    return "login.jsp";
                }
                if (!customer.getPassword().equals(form.getPassword())) {
                    errors.add("Incorrect password");
                    return "login.jsp";
                }

                // login success, return home.jsp
                // Attach (this copy of) the user bean to the session
                request.getSession().setAttribute("customer", customer);
                return "customer-home.do";
            } else if (userGroup.equals("employee")) {
                if (employee == null) {
                    errors.add("Username is not found");
                    return "login.jsp";
                }
                if (!employee.getPassword().equals(form.getPassword())) {
                    errors.add("Incorrect password");
                    return "login.jsp";
                }

                // login success, return home.jsp
                // Attach (this copy of) the user bean to the session
                request.getSession().setAttribute("employee", employee);
                return "employee-home.do";
            }

            return "error.jsp";
        } catch (RollbackException e) {
            errors.add(e.getMessage());
            return "login.jsp";
        } catch (FormBeanException e) {
            errors.add(e.getMessage());
            return "login.jsp";
        }
    }
}
