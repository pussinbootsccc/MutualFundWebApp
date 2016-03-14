package formbeans;

import java.util.ArrayList;
import java.util.List;

import org.mybeans.form.FormBean;

public class CreateFundForm extends FormBean {
    private String fundName;
    private String fundTicker;
    private String action;

    @Override
    public boolean isPresent() {
        return action != null;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = trimAndConvert(action, "<>\"");
    }

    public String getFundName() {
        return fundName;
    }

    public void setFundName(String fundName) {
        this.fundName = trimAndConvert(fundName, "<>\"");
    }

    public String getFundTicker() {
        return fundTicker;
    }

    public void setFundTicker(String fundTicker) {
        this.fundTicker = trimAndConvert(fundTicker, "<>\"");
    }

    // Validation needed
    public List<String> getValidationErrors() {
        List<String> errors = new ArrayList<String>();
        if (fundName == null || fundName.length() == 0) {
            errors.add("Fund name is required");
        }

        if (fundName != null && fundName.length() > 20) {
            errors.add("Fund name should be less than 20 characters");
        }

        if (fundTicker == null || fundTicker.length() == 0) {
            errors.add("Fund ticker is required");
        }

        if (fundTicker != null && fundTicker.length() >= 6) {
            errors.add("Fund ticker should be one to five characters");
        }

        if (errors.size() > 0) {
            return errors;
        }
        
        if (!fundName.matches("[a-zA-Z0-9]+"))
            errors.add("Fund name should only contain letters and numbers");
        
        if (fundName.matches(".*[<>\"].*"))
            errors.add("Fund name should not contain angle brackets or quotes");
        
        if (!fundTicker.matches("[a-zA-Z0-9]+"))
            errors.add("Fund ticker should only contain letters and numbers");

        if (fundTicker.matches(".*[<>\"].*"))
            errors.add("Fund ticker should not contain angle brackets or quotes");

        
        return errors;
    }
}
