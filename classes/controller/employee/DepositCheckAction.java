package controller.employee;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.genericdao.RollbackException;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import controller.Action;
import databeans.Customer;
import databeans.MyTransaction;
import formbeans.DepositCheckForm;
import model.CustomerDAO;
import model.Model;
import model.TransactionDAO;

public class DepositCheckAction extends Action {

	private FormBeanFactory<DepositCheckForm> formBeanFactory = FormBeanFactory.getInstance(DepositCheckForm.class);

	private CustomerDAO customerDAO;
	private TransactionDAO transactionDAO;

	@Override
	public String getName() {
		return "e_deposit_check.do";
	}

	public DepositCheckAction(Model model) {
		customerDAO = model.getCustomerDAO();
		transactionDAO = model.getTransactionDAO();
	}

	@Override
	public String perform(HttpServletRequest request) {
		List<String> errors = new ArrayList<String>();
		request.getSession().setAttribute("errors", errors);
        request.setAttribute("errors", errors);

		// step1 check whether the user is login and the user has the right to
		// create fund
		if (!employeeLoginValidate(request)) {
			return "login.do";
		}

		// fill the form
		try {
			DepositCheckForm form = formBeanFactory.create(request);

			if (!form.isPresent()) {
				request.setAttribute("message", "");
				request.setAttribute("messageType", "depositCheckError");
				return "error.jsp";
			}
			// validate the customer & get customer id
			Customer customer = customerDAO.getByUserName(form.getUserName());
			if (customer == null) {
				errors.add("Customer doesn't exist!");
				request.setAttribute("messageType", "depositCheckError");
				request.getSession(false).setAttribute("messageType", null);
				request.getSession(false).setAttribute("errors", null);
				return "error.jsp";
			}

			request.setAttribute("customer", customer);

			errors.addAll(form.getValidationErrors());
			if (errors.size() > 0) {
			    request.getSession().setAttribute("customerId", ""+customer.getCustomerId());
				request.getSession().setAttribute("messageType", "depositCheckError");
				return "e_view_customer_detail.do";
			}

			long cid = customer.getCustomerId();

			double cashDouble = Double.parseDouble(form.getCash());
			long cash = (long) (cashDouble * 100);

			// add to transactionform
			MyTransaction myTransaction = new MyTransaction();
			myTransaction.setAmount(cash);
			myTransaction.setCustomerId(cid);
			myTransaction.setTransactionType(MyTransaction.DEPOSIT_CHECK);
			transactionDAO.create(myTransaction);

			request.getSession().setAttribute("message",
					"Request accepted! Customer: " + form.getUserName() + ", amount: $" + form.getCash());

			request.getSession().setAttribute("messageType", "depositCheckSuccess");
			request.getSession().setAttribute("customerId", ""+cid);
			return "e_view_customer_detail.do";

		} catch (FormBeanException e) {
            request.getSession().setAttribute("messageType", "depositCheckSuccess");
			errors.add(e.getMessage());
			return "error.jsp";
		} catch (RollbackException e) {
			errors.add(e.getMessage());
			return "error.jsp";
		}

	}

}
