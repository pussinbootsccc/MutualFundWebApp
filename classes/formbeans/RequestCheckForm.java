package formbeans;

import java.util.ArrayList;
import java.util.List;

import org.mybeans.form.FormBean;

public class RequestCheckForm extends FormBean {
    private String amount;
    private String button;

    public String getAmount() {
        return amount;
    }

    public String getButton() {
        return button;
    }

    public void setAmount(String s) {
        amount = trimAndConvert(s, "<>\"");
    }

    public void setButton(String s) {
        button = trimAndConvert(s, "<>\"");
    }

    public boolean isPresent() {
        return button != null;
    }

    // To check input number problem.
    private void getAmountErrors(List<String> errors) {

        if (amount == null || amount.length() == 0) {
            errors.add("Amount is required");
            return;
        }

        if (amount.indexOf(".") != -1 && (amount.length() - 1 - amount.indexOf(".")) > 2) {
            errors.add("Amount should be within two decimal places");
            return;
        }

        double amountValue = -1;
        try {
            amountValue = Double.valueOf(amount);
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

        if (amount.matches(".*[<>\"].*"))
            errors.add("Amount should not contain angle brackets or quotes");

        if (button == null) {
            errors.add("Button is required");
        }
        return errors;
    }
}
