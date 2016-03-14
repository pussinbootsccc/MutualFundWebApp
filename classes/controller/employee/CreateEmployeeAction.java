
package controller.employee;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.genericdao.RollbackException;
import org.genericdao.Transaction;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import controller.Action;
import databeans.Employee;
import formbeans.EmployeeForm;
import model.EmployeeDAO;
import model.Model;

public class CreateEmployeeAction extends Action {

    private FormBeanFactory<EmployeeForm> formBeanFactory = FormBeanFactory.getInstance(EmployeeForm.class);

    private EmployeeDAO userDAO;

    public CreateEmployeeAction(Model model) {
        userDAO = model.getEmployeeDAO();
    }

    @Override
    public String getName() {
        return "e_create_employee.do";
    }

    @Override
    public String perform(HttpServletRequest request) {
        List<String> errors = new ArrayList<String>();
        request.setAttribute("errors", errors);

        // check whether the user is login
        if (!employeeLoginValidate(request)) {
            return "login.do";
        }

        try {
            EmployeeForm form = formBeanFactory.create(request);
            request.setAttribute("form", form);
            if (!form.isPresent()) {
                return "/employee/e_create_employee.jsp";
            }

            errors.addAll(form.getValidationErrors());
            if (errors.size() != 0) {
                return "/employee/e_create_employee.jsp";
            }

            // Create customer need to check whether username has existed
            if (userDAO.getByUserName(form.getUserName()) != null) {
                errors.add("Username exists, please change another one");
                return "/employee/e_create_employee.jsp";
            }

            Employee employee = new Employee();
            employee.setUserName(form.getUserName());
            employee.setFirstName(form.getFirstName());
            employee.setLastName(form.getLastName());
            employee.setPassword(form.getPassword());
            userDAO.createEmployee(employee);

            employee.setEmployeeId(userDAO.getByUserName(form.getUserName()).getEmployeeId());
            request.setAttribute("message", "Create employee account successfully!");
            return "/employee/e_result.jsp";
        } catch (RollbackException e) {
            errors.add(e.getMessage());
            return "/employee/e_create_employee.jsp";
        } catch (FormBeanException e) {
            errors.add(e.getMessage());
            return "/employee/e_create_employee.jsp";
        } finally {
            if (Transaction.isActive()) {
                Transaction.rollback();
            }
        }
    }
}
