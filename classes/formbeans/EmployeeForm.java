
package formbeans;

import java.util.ArrayList;
import java.util.List;

import org.mybeans.form.FormBean;

public class EmployeeForm extends FormBean {
    private String userName;
    private String firstName;
    private String lastName;
    private String password;
    private String confirmPassword;
    private String action;

    public String getUserName() {
        return userName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPassword() {
        return password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public String getAction() {
        return action;
    }

    public void setUserName(String userName) {
        this.userName = trimAndConvert(userName, "<>\"");
    }

    public void setFirstName(String s) {
        firstName = trimAndConvert(s, "<>\"");
    }

    public void setLastName(String s) {
        lastName = trimAndConvert(s, "<>\"");
    }

    public void setPassword(String s) {
        password = s.trim();
    }

    public void setConfirmPassword(String s) {
        confirmPassword = s.trim();
    }

    public void setAction(String s) {
        action = trimAndConvert(s, "<>\"");
    }

    @Override
    public List<String> getValidationErrors() {
        List<String> errors = new ArrayList<String>();

        if (userName == null || userName.length() == 0)
            errors.add("User name is required");

        if (firstName == null || firstName.length() == 0)
            errors.add("First name is required");

        if (lastName == null || lastName.length() == 0)
            errors.add("Last name is required");

        if (password == null || password.length() == 0)
            errors.add("Password is required");

        if (confirmPassword == null || confirmPassword.length() == 0)
            errors.add("Confirm password is required");

        if (errors.size() > 0) {
            return errors;
        }

        if (!password.equals(confirmPassword)) {
            errors.add("Passwords do not match");
            return errors;
        }
        
        if (password.matches(".*[<>\"].*"))
            errors.add("Password should not contain angle brackets or quotes");
        
        if (userName != null && userName.length() >= 17) {
            errors.add("Username should be no longer than 16 characters");
        }
        
        if (userName != null && userName.length() <= 2) {
            errors.add("Username should be no shorter than 3 characters");
        }
        
        if (userName.matches(".*[<>\"].*"))
            errors.add("User name should not contain angle brackets or quotes");

        if (firstName.matches(".*[<>\"].*") || !firstName.matches("[a-zA-Z]+"))
            errors.add("First name should not contain number or special characters");

        if (lastName.matches(".*[<>\"].*") || !lastName.matches("[a-zA-z]+([ '-][a-zA-Z]+)*"))
            errors.add("Last name should not contain number or special characters");
        
        return errors;
    }

}
