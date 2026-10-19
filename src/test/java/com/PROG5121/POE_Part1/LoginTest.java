package com.mycompany.poe_part1;

**
 * Handles user registration and login validation
 * Author: Sharone Tebele
 * Attribution for Regex: https://www.geeksforgeeks.org/regex-validation-south-african-phone-number/
 */

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LoginTest {
    @Test 
    public void testUsernameCorrect() { 
        Login l = new Login(); 
        l.username = "kyl_1"; 
        assertTrue(l.checkUserName()); 
    }
    @Test 
    public void testUsernameIncorrect() { 
        Login l = new Login(); 
        l.username = "kyle!!!!!!!"; 
        assertFalse(l.checkUserName()); 
    }
    @Test 
    public void testPasswordCorrect() { 
        Login l = new Login(); 
        l.password = "Ch&&sec@ke99!"; 
        assertTrue(l.checkPasswordComplexity()); 
    }
    @Test 
    public void testPasswordIncorrect() { 
        Login l = new Login(); 
        l.password = "password"; 
        assertFalse(l.checkPasswordComplexity()); 
    }
    @Test public void testCellCorrect() { 
        Login l = new Login(); 
        l.cellNumber = "+27838970000"; 
        assertTrue(l.checkCellPhoneNumber()); 
    }
    @Test public void testCellIncorrect() { 
        Login l = new Login(); 
        l.cellNumber = "08575975889"; 
        assertFalse(l.checkCellPhoneNumber()); 
    }
    @Test
    public void testLoginSuccess() { 
        Login l = new Login("kyl_1","Ch&&sec@ke99!","+27838970000","Kyle","Test"); 
        assertTrue(l.loginUser("kyl_1","Ch&&sec@ke99!")); 
    }
    @Test 
    public void testLoginFail() { 
        Login l = new Login("kyl_1","Ch&&sec@ke99!","+27838970000","Kyle","Test"); 
        assertFalse(l.loginUser("kyl_1","wrong")); 
    }
}