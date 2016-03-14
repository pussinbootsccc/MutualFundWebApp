package formbeans;

import java.util.ArrayList;
import java.util.List;

import org.mybeans.form.FormBean;

public class ChangeCustomerPwdForm extends FormBean {
    private String userName;
    private String confirmPassword;
    private String newPassword;

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public String getUserName() {
        return userName;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setConfirmPassword(String s) {
        confirmPassword = s.trim();
    }

    public void setUserName(String s) {
        userName = trimAndConvert(s, "<>\"");
    }

    public void setNewPassword(String s) {
        newPassword = s.trim();
    }

    @Override
    public List<String> getValidationErrors() {
        List<String> errors = new ArrayList<String>();

        if (newPassword == null || newPassword.length() == 0) {
            errors.add("New password is required");
        }

        if (confirmPassword == null || confirmPassword.length() == 0) {
            errors.add("Confirm password is required");
        }

        if (errors.size() > 0) {
            return errors;
        }

        if (!newPassword.equals(confirmPassword)) {
            errors.add("Passwords do not match");
            return errors;
        }
        if (newPassword.matches(".*[<>\"].*"))
            errors.add("Password should not contain angle brackets or quotes");

        return errors;
    }

}
