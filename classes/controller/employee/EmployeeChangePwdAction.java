
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
import formbeans.ChangePwdForm;
import model.EmployeeDAO;
import model.Model;

public class EmployeeChangePwdAction extends Action {

	private FormBeanFactory<ChangePwdForm> formBeanFactory = FormBeanFactory.getInstance(ChangePwdForm.class);

	private EmployeeDAO employeeDAO;

	public EmployeeChangePwdAction(Model model) {
		employeeDAO = model.getEmployeeDAO();
	}

	public String getName() {
		return "e_change_password.do";
	}

	public String perform(HttpServletRequest request) {

		List<String> errors = new ArrayList<String>();
		request.setAttribute("errors", errors);

		// check whether the user is login
		if (!employeeLoginValidate(request)) {
			return "login.do";
		}

		try {

			ChangePwdForm form = formBeanFactory.create(request);

			if (!form.isPresent()) {
				return "/employee/e_change_password.jsp";
			}

			// Check for any validation errors
			errors.addAll(form.getValidationErrors());
			if (errors.size() != 0) {
				return "/employee/e_change_password.jsp";
			}

			Employee user = (Employee) request.getSession().getAttribute("employee");

			// Change the password
			employeeDAO.setPassword(user.getUserName(), form.getNewPassword());

			request.setAttribute("message", "Employee password changed successfully!");
			return "/employee/e_change_password.jsp";
		} catch (RollbackException e) {
			errors.add(e.toString());
			return "/employee/e_change_password.jsp";
		} catch (FormBeanException e) {
			errors.add(e.toString());
			return "/employee/e_change_password.jsp";
		} finally {
			if (Transaction.isActive()) {
				Transaction.rollback();
			}
		}
	}
}
