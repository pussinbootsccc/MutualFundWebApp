package controller.employee;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.genericdao.RollbackException;
import org.mybeans.form.FormBeanFactory;

import controller.Action;
import databeans.Customer;
import formbeans.CustomerForm;
import model.CustomerDAO;
import model.Model;

public class ViewCustomerListAction extends Action {
	private FormBeanFactory<CustomerForm> formBeanFactory = FormBeanFactory.getInstance(CustomerForm.class);

	private CustomerDAO customerDAO;

	public ViewCustomerListAction(Model model) {

		customerDAO = model.getCustomerDAO();
	}

	public String getName() {
		return "e_view_customer_list.do";
	}

	public String perform(HttpServletRequest request) {
		// Set up the request attributes (the errors list and the form bean so
		// we can just return to the jsp with the form if the request isn't
		// correct)
		List<String> errors = new ArrayList<String>();
		request.setAttribute("errors", errors);

		// check whether the user is login
		if (!employeeLoginValidate(request)) {
			return "login.do";
		}

		try {
			// Set up user list for nav bar

			Customer[] customerList = customerDAO.getAllCustomers();
			request.setAttribute("customerList", customerList);
			// for (int i = 0; i < customerList.length; i++) {
			// System.out.println(customerList[i].getUserName());
			// }
			return "/employee/e_view_customers.jsp";
		} catch (RollbackException e) {
			errors.add(e.getMessage());
			return "/employee/e_result.jsp";
		} catch (NumberFormatException e) {
			errors.add(e.getMessage());
			return "/employee/e_result.jsp";
		}
	}
}
