
package controller.employee;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.genericdao.RollbackException;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import controller.Action;
import databeans.Customer;
import formbeans.CustomerForm;
import model.CustomerDAO;
import model.Model;

public class CreateCustomerAction extends Action {

	private FormBeanFactory<CustomerForm> formBeanFactory = FormBeanFactory.getInstance(CustomerForm.class);

	private CustomerDAO userDAO;

	public CreateCustomerAction(Model model) {
		userDAO = model.getCustomerDAO();
	}

	@Override
	public String getName() {
		return "e_create_customer.do";
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
			CustomerForm form = formBeanFactory.create(request);
			request.setAttribute("form", form);
			if (!form.isPresent()) {
				return "/employee/e_create_customer.jsp";
			}

			errors.addAll(form.getValidationErrors());
			if (errors.size() != 0) {
				return "/employee/e_create_customer.jsp";
			}

			// Create customer need to check whether username has existed
			if (userDAO.getByUserName(form.getUserName()) != null) {
				errors.add("Username already exists, please change another one");
				return "/employee/e_create_customer.jsp";
			}

			Customer user = new Customer();
			user.setUserName(form.getUserName());
			user.setFirstName(form.getFirstName());
			user.setLastName(form.getLastName());
			user.setAddr_line1(form.getAddressLine1());
			user.setAddr_line2(form.getAddressLine2());
			user.setCity(form.getCity());
			user.setState(form.getState());
			user.setPassword(form.getPassword());
			user.setZip(form.getZip());
			userDAO.add(user);

			user.setCustomerId(userDAO.getByUserName(form.getUserName()).getCustomerId());

			request.setAttribute("message", "Create customer account successfully!");
			return "/employee/e_result.jsp";
		} catch (RollbackException e) {
			errors.add(e.getMessage());
			return "/employee/e_create_customer.jsp";
		} catch (FormBeanException e) {
			errors.add(e.getMessage());
			return "/employee/e_create_customer.jsp";
		}
	}
}
