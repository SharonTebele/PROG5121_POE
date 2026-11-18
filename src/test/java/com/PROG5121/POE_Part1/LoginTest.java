package src.main.java.com.PROG5121.POE_Part1;


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LoginTest {

    @Test
    public void testUsernameCorrect() {
        Login login = new Login();
        login.username = "kyl_1";
        assertTrue(login.checkUserName());
    }

    @Test
    public void testUsernameIncorrect() {
        Login login = new Login();
        login.username = "kyle!!!!!!!";
        assertFalse(login.checkUserName());
    }

    @Test
    public void testPasswordCorrect() {
        Login login = new Login();
        login.password = "Ch&&sec@ke99!";
        assertTrue(login.checkPasswordComplexity());
    }

    @Test
    public void testPasswordIncorrect() {
        Login login = new Login();
        login.password = "password";
        assertFalse(login.checkPasswordComplexity());
    }

    @Test
    public void testCellCorrect() {
        Login login = new Login();
        login.cellNumber = "+27838970000";
        assertTrue(login.checkCellNumber());
    }

    @Test
    public void testCellIncorrect() {
        Login login = new Login();
        login.cellNumber = "08575975889";
        assertFalse(login.checkCellNumber());
    }

    @Test
    public void testLoginSuccess() {
        Login login = new Login(
                "kyl_1", "Ch&&sec@ke99!",
                "+27838970000", "Kyle", "Test");

        assertTrue(login.loginUser("kyl_1", "Ch&&sec@ke99!"));
    }

    @Test
    public void testLoginFail() {
        Login login = new Login(
                "kyl_1", "Ch&&sec@ke99!",
                "+27838970000", "Kyle", "Test");

        assertFalse(login.loginUser("kyl_1", "wrong"));
    }
}