package src.main.java.com.PROG5121.POE_Part1;


public class Login {

    String username;
    String password;
    String cellNumber;
    String firstName;
    String lastName;

    public Login() {
    }

    public Login(String username, String password, String cellNumber,
                 String firstName, String lastName) {
        this.username = username;
        this.password = password;
        this.cellNumber = cellNumber;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public boolean checkUserName() {
        return username != null
                && username.contains("_")
                && username.length() <= 5;
    }

    public boolean CheckUserName() {
        return checkUserName();
    }

    public boolean checkPasswordComplexity() {
        if (password == null || password.length() < 8) {
            return false;
        }

        boolean hasCapital = password.matches(".*[A-Z].*");
        boolean hasNumber = password.matches(".*[0-9].*");
        boolean hasSpecial = password.matches(".*[^a-zA-Z0-9].*");

        return hasCapital && hasNumber && hasSpecial;
    }

    public boolean checkCellNumber() {
        return cellNumber != null
                && cellNumber.matches("^\\+27[0-9]{9}$");
    }

    public boolean checkCellPhoneNumber() {
        return checkCellNumber();
    }

    public String registerUser() {

        if (!checkUserName()) {
            return "Username is not correctly formatted.";
        }

        if (!checkPasswordComplexity()) {
            return "Password is not correctly formatted.";
        }

        if (!checkCellNumber()) {
            return "Cell phone number incorrectly formatted.";
        }

        return "User has been registered successfully.";
    }

    public boolean loginUser(String enteredUsername,
                             String enteredPassword) {
        return username != null
                && password != null
                && username.equals(enteredUsername)
                && password.equals(enteredPassword);
    }

    public boolean LoginUser(String enteredUsername,
                             String enteredPassword) {
        return loginUser(enteredUsername, enteredPassword);
    }

    public String returnLoginStatus(String enteredUsername,
                                    String enteredPassword) {

        if (loginUser(enteredUsername, enteredPassword)) {
            return "Welcome " + firstName + ", " + lastName
                    + " it is great to see you again.";
        }

        return "Username or password incorrect, please try again.";
    }

    public String returnLoginStatus(String enteredUsername,
                                    String enteredPassword,
                                    String ignoredLastName) {
        return returnLoginStatus(enteredUsername, enteredPassword);
    }
}