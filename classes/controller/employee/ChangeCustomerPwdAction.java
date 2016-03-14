
package controller.employee;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.genericdao.RollbackException;
import org.genericdao.Transaction;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import controller.Action;
import databeans.Customer;
import formbeans.ChangeCustomerPwdForm;
import model.CustomerDAO;
import model.Model;

public class ChangeCustomerPwdAction extends Action {

	private FormBeanFactory<ChangeCustomerPwdForm> formBeanFactory = FormBeanFactory
			.getInstance(ChangeCustomerPwdForm.class);

	private CustomerDAO customerDAO;

	public ChangeCustomerPwdAction(Model model) {
		customerDAO = model.getCustomerDAO();
	}

	@Override
	public String getName() {
		return "e_change_customer_password.do";
	}

	@Override
	public String perform(HttpServletRequest request) {
		// Set up error list
		List<String> errors = new ArrayList<String>();
		request.getSession().setAttribute("errors", errors);
		request.setAttribute("errors", errors);

		// check whether the user is login
		if (!employeeLoginValidate(request)) {
			return "login.do";
		}

		try {
			/*
			 * String customerId = request.getParameter("customerId"); if
			 * (customerId == null) { errors.add("Customer Id is required.");
			 * return "/employee/e_view_customers.jsp"; } Customer customer =
			 * customerDAO.read(Long.valueOf(customerId)); if (customer == null)
			 * { errors.add("Customer Id is invalid."); return
			 * "/employee/e_view_customers.jsp"; }
			 * 
			 * request.setAttribute("customer", customer);
			 */
			ChangeCustomerPwdForm form = formBeanFactory.create(request);
			if (!form.isPresent()) {
				request.setAttribute("message", "");
				request.setAttribute("messageType", "passwordError");

				return "error.jsp";
			}
			// validate the customer & get customer id
			Customer customer = customerDAO.getByUserName(form.getUserName());
			if (customer == null) {
				errors.add("Customer doesn't exist");
				request.setAttribute("messageType", "passwordError");
				request.getSession(false).setAttribute("messageType", null);
				request.getSession(false).setAttribute("errors", null);
				return "error.jsp";
			}
			errors.addAll(form.getValidationErrors());

			if (errors.size() != 0) {
				request.getSession().setAttribute("customerId", "" + customer.getCustomerId());
				request.getSession().setAttribute("messageType", "passwordError");
				return "e_view_customer_detail.do";

			}

			customerDAO.setPassword(customer.getUserName(), form.getNewPassword());

			request.getSession().setAttribute("customerId", customer.getCustomerId());
			request.getSession().setAttribute("messageType", "passwordSuccess");
			request.getSession().setAttribute("message", "Password changed successfully for " + customer.getUserName());
			return "e_view_customer_detail.do";
		} catch (RollbackException | FormBeanException e) {
			errors.add(e.toString());
			request.setAttribute("messageType", "passwordError");
			return "/employee/e_customer_details.jsp";
		} finally {
			if (Transaction.isActive()) {
				Transaction.rollback();
			}
		}
	}
}
