
package formbeans;

import java.util.ArrayList;
import java.util.List;

import org.mybeans.form.FormBean;

import util.Common;

public class CustomerForm extends FormBean {
    private String userName;
    private String firstName;
    private String lastName;
    private String password;
    private String confirmPassword;
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String state;
    private String zip;
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

    public String getAddressLine1() {
        return addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getZip() {
        return zip;
    }

    public void setUserName(String userName) {
        this.userName = trimAndConvert(userName, "<>>\"]");
    }

    public void setFirstName(String s) {
        firstName = trimAndConvert(s, "<>>\"]");
    }

    public void setLastName(String s) {
        lastName = trimAndConvert(s, "<>>\"]");
    }

    public void setPassword(String s) {
    	
    	
        password = s.trim();
    }

    public void setConfirmPassword(String s) {
        confirmPassword = s.trim();
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String s) {
        action = s;
    }

    public List<String> getValidationErrors() {
        List<String> errors = new ArrayList<String>();

        if (userName == null || userName.length() == 0)
            errors.add("User name is required");

        if (password == null || password.length() == 0)
            errors.add("Password is required");

        if (confirmPassword == null || confirmPassword.length() == 0)
            errors.add("Confirm password is required");
        if (firstName == null || firstName.length() == 0)
            errors.add("First name is required");

        if (lastName == null || lastName.length() == 0)
            errors.add("Last name is required");

        if (addressLine1 == null || addressLine1.length() == 0)
            errors.add("Address Line1 is required");

        if (city == null || city.length() == 0)
            errors.add("City is required");

        if (state == null || state.length() == 0)
            errors.add("State is required");

        if (zip == null || zip.length() == 0)
            errors.add("Zip code is required");

        if (errors.size() > 0)
            return errors;

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

        if (addressLine1.matches(".*[<>\"].*"))
            errors.add("AddressLine1 should not contain angles bracket or quotes");

        if (addressLine2 != null && addressLine2.matches(".*[<>\"].*"))
            errors.add("AddressLine2 should not contain angle brackets or quotes");

        if (city.matches(".*[<>\"].*"))
            errors.add("City should not contain angle brackets or quotes");

        if (city.matches("[\\w|\\W]*[0-9][\\w|\\W]*"))
            errors.add("City should not contain numbers");

        if (state.matches(".*[<>\"].*"))
            errors.add("State should not contain angle brackets or quotse");

        if (!Common.US_STATES.contains(state)) {
            errors.add("Not a valid US state");
        }

        if (zip.matches(".*[<>\"].*"))
            errors.add("Zip may not contain angle brackets or quotes");

        if (!zip.matches("[0-9]{5}"))
            errors.add("Zip should be a 5-digit number");

        return errors;
    }

}
