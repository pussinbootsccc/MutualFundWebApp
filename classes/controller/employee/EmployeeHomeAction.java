package controller.employee;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.genericdao.RollbackException;

import controller.Action;
import databeans.Customer;
import model.CustomerDAO;
import model.Model;

public class EmployeeHomeAction extends Action {
    private CustomerDAO customerDAO;

    @Override
    public String getName() {
        return "employee-home.do";
    }

    public EmployeeHomeAction(Model model) {
        customerDAO = model.getCustomerDAO();
    }

    @Override
    public String perform(HttpServletRequest request) {
        List<String> errors = new ArrayList<String>();
        request.setAttribute("errors", errors);

        if (!employeeLoginValidate(request)) {
            return "login.do";
        }

        try {
            Customer[] customers = customerDAO.getAllCustomers();
            request.setAttribute("customers", customers);
        } catch (RollbackException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        return "/employee/home.jsp";
    }

}
