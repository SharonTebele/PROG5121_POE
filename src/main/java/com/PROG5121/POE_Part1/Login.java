package src.main.java.com.PROG5121.POE_Part1;

/**
 * Handles user registration and login validation
 * Author: Sharone Tebele
 * Attribution for Regex: https://www.geeksforgeeks.org/regex-validation-south-african-phone-number/
 */
public class Login {
    String username;
    String password;
    String cellNumber;
    String firstName;
    String lastName;

    public Login(String username, String password, String cellNumber, String firstName, String lastName){
        this.username = username;
        this.password = password;
        this.cellNumber = cellNumber;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Login() {}
    /**checks username contains underscore and <=5 chars
     * return true if valid
     */

    public boolean CheckUserName(){
        return username != null && username.contains("_") && username.length() <= 5;
        }

    public boolean checkPasswordComplexity(){
        if (password == null || password.length() < 8) return false;
            boolean hasCapital = password.matches(".*[A-Z].*");
            boolean hasNumber =  password.matches(".*[0-9].*");
            boolean hasSpecial = password.matches(".*[!@#$%^&*()_+=\\\\-`~<>,.?/;':\\\"\\\\[\\\\]{}|].*");
            return hasCapital && hasNumber && hasSpecial;
    }   

    /**
     * validates SA number with international code +27
     * regex source: Research - SA format is +27 +9 dagits
     */

    public boolean checkCellNumber(){
        //SA regex: +27 followed by 9 digits = 12 chars total, but brief says max 10 chars after code
        // use the required regex: +27[0-9]{9}$
        return cellNumber != null && cellNumber.matches("^\\+27[0-9]{9}$");
    }

    public String registerUser(){
        if(!CheckUserName()){
            return "Username is not correctly formatted, please ensure that your username contains an underscore and is no more than five characters in length.";
        }
        if(!checkPasswordComplexity()){
            return "\"Password is not correctly formatted, please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.";
        }
        if(!checkCellNumber()){
            return "Cell phone number incorrectly formatted or does not contain international code.";
        }
    return "User has been registered successfully.";
    }
    public boolean LoginUser(String enteredUsername, String enteredPassword){
        return this.username.equals(enteredUsername) && this.password.equals(enteredPassword);
    }
    public String returnLoginStatus(String enteredUsername, String enteredPassword, String enteredlastName){
        {
            if (LoginUser(enteredUsername, enteredPassword)){
                return "Welcome " + firstName + ", " + lastName + " it is great to see you again.";
            } else {
                return "Username or password incorrect, please try a gain.";
            }
        }
    }

}
