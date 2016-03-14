package formbeans;

import java.util.ArrayList;
import java.util.List;

import org.mybeans.form.FormBean;

public class LoginForm extends FormBean {
    private String userName;
    private String password;
    private String button;

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getButton() {
        return button;
    }

    public void setUserName(String s) {
        userName = trimAndConvert(s, "<>\"");
    }

    public void setPassword(String s) {
        password = s.trim();
    }

    public void setButton(String s) {
        button = trimAndConvert(s, "<>\"");
    }

    public boolean isPresent() {
        return button != null;
    }

    public List<String> getValidationErrors() {
        List<String> errors = new ArrayList<String>();

        if (userName == null || userName.length() == 0)
            errors.add("User name is required");
        if (password == null || password.length() == 0)
            errors.add("Password is required");
        if (errors.size() > 0) {
            return errors;
        }
        
        if (userName.matches(".*[<>\"].*"))
            errors.add("Do not enter angle brackets or quotes");
        
        if (password.matches(".*[<>\"].*"))
            errors.add("Do not enter angle brackets or quotes");
        
        if (button == null)
            errors.add("Button is required");

        return errors;
    }

}
