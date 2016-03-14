package formbeans;

import java.util.ArrayList;
import java.util.List;

import org.mybeans.form.FormBean;

public class DepositCheckForm extends FormBean {
	private String userName;
	private String cash;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getCash() {
		return cash;
	}

	public void setCash(String cash) {
		this.cash = cash;
	}
	// To check input number problem.
	private void getAmountErrors(List<String> errors) {

		if (cash == null || cash.length() == 0) {
			errors.add("Amount is required");
			return;
		}
		if (cash.indexOf(".") != -1 && (cash.length() - 1 - cash.indexOf(".")) > 2) {
			errors.add("Amount should be within two decimal places");
			return;
		}

		double amountValue = -1;
		try {
			amountValue = Double.valueOf(cash);
		} catch (Exception e) {
			errors.add("Please enter valid number, no letter, character, or symbol");
			return;
		}
		if (amountValue < 0.01 || amountValue > 100000000) {
			errors.add("The amount input should go between 0.01 and 100000000");
			return;
		}
	}
    // To check other problems.
	public List<String> getValidationErrors() {
		List<String> errors = new ArrayList<String>();
		getAmountErrors(errors);
		
		if (errors.size() > 0) {
			return errors;
		}
		
		if (cash.matches(".*[<>\"].*"))
			errors.add("amount may not contain angle brackets or quotes");
		
		return errors;
	}

}
