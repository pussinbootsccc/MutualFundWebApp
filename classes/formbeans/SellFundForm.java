package formbeans;

import java.util.ArrayList;
import java.util.List;

import org.mybeans.form.FormBean;

public class SellFundForm extends FormBean {
    private String shares;
    private String button;

    public String getShares() {
        return shares;
    }

    public String getButton() {
        return button;
    }

    public void setShares(String s) {
        shares = trimAndConvert(s, "<>\"");
    }

    public void setButton(String s) {
        button = trimAndConvert(s, "<>\"");
    }

    public boolean isPresent() {
        return button != null;
    }

    private void getSharesErrors(List<String> errors) {
        if (shares == null || shares.length() == 0) {
            errors.add("Share is required");
            return;
        }

        if (shares.indexOf(".") != -1 && (shares.length() - 1 - shares.indexOf(".")) > 3) {
            errors.add("Shares shoud be within three decimal places");
            return;
        }

        double sharesValue = -1;
        try {
            sharesValue = Double.valueOf(shares);
        } catch (Exception e) {
            errors.add("Please enter valid number, no letter, character, or symbol");
            return;
        }

        if (sharesValue < 0.001 || sharesValue > 100000000) {
            errors.add("The share input should go between 0.001 and 100000000");
            return;
        }

    }

    public List<String> getValidationErrors() {
        List<String> errors = new ArrayList<String>();

        getSharesErrors(errors);

        if (errors.size() > 0) {
            return errors;
        }

        if (shares.matches(".*[<>\"].*"))
            errors.add("Shares should not contain angle brackets or quotes");

        if (button == null) {
            errors.add("Button is required");
        }
        return errors;
    }

}
