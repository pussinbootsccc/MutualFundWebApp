package controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import databeans.Customer;
import databeans.Employee;

public class LogoutAction extends Action {

    public String getName() {
        return "logout.do";
    }

    public String perform(HttpServletRequest request) {
        // Set up the errors list
        List<String> errors = new ArrayList<String>();
        request.setAttribute("errors", errors);

        Customer customer = (Customer) request.getSession(false).getAttribute("customer");
        Employee employee = (Employee) request.getSession(false).getAttribute("employee");
        if (customer == null && employee == null) {
            return "login.do";
        }

        HttpSession session = request.getSession(false);
        if (session != null) {
            session.setAttribute("customer", null);
            session.setAttribute("employee", null);
        }

        request.setAttribute("message", "You are now logged out");
        return "login.jsp";
    }

}
