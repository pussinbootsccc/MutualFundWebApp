package formbeans;

import java.util.ArrayList;
import java.util.List;

import org.mybeans.form.FormBean;

public class ChangePwdForm extends FormBean {
    private String confirmPassword;
    private String newPassword;
    private String button;

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public String getButton() {
        return button;
    }

    public void setConfirmPassword(String s) {
        confirmPassword = s.trim();
    }

    public void setNewPassword(String s) {
        newPassword = s.trim();
    }

    public void setButton(String s) {
        button = trimAndConvert(s, "<>\"");
    }

    @Override
    public boolean isPresent() {
        return button != null;
    }

    @Override
    public List<String> getValidationErrors() {
        List<String> errors = new ArrayList<String>();

        if (newPassword == null || newPassword.length() == 0) {
            errors.add("New Password is required");
        }

        if (confirmPassword == null || confirmPassword.length() == 0) {
            errors.add("Confirm Password is required");
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
