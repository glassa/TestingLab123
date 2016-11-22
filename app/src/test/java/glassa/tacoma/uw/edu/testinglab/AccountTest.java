package glassa.tacoma.uw.edu.testinglab;

import org.junit.Test;

import glassa.tacoma.uw.edu.testinglab.authenticate.Account;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.fail;
import static org.junit.Assert.assertNotEquals;

/**
 * Created by aglass on 11/14/2016.
 */

public class AccountTest {

    @Test
    public void testAccountConstructor() {
        assertNotNull(new Account("mmuppa@uw.edu", "test1@3"));
    }

    @Test
    public void testAccountConstructorBadEmail() {
        try {
            new Account("mmuppauw.edu", "test1@3");
            fail("Expected IllegalArgumentException to be thrown : Username with no @ symbol");
        } catch(IllegalArgumentException e) {
            assertEquals("failure - strings are not equal", "java.lang.IllegalArgumentException: Invalid Email", e.toString());
        }
        try {
            new Account("mmuppa@uwedu", "test1@3");
            fail("Expected IllegalArgumentException to be thrown : Username with no domain");
        } catch(IllegalArgumentException e) {
            assertEquals("failure - strings are not equal", "java.lang.IllegalArgumentException: Invalid Email", e.toString());
        }
        try {
            new Account("a@u!u", "test1@3");
            fail("Expected IllegalArgumentException to be thrown : Username with strange symbol");
        } catch(IllegalArgumentException e) {
            assertEquals("failure - strings are not equal", "java.lang.IllegalArgumentException: Invalid Email", e.toString());
        }
        try {
            new Account("mmuppauw.ed@u", "test1@3");
            fail("Expected IllegalArgumentException to be thrown : Username correct symbols out of order");
        } catch(IllegalArgumentException e) {
            assertEquals("failure - strings are not equal", "java.lang.IllegalArgumentException: Invalid Email", e.toString());
        }
        try {
            new Account("mmuppa@uw..edu", "test1@3");
            fail("Expected IllegalArgumentException to be thrown : Username with double period");
        } catch(IllegalArgumentException e) {
            assertEquals("failure - strings are not equal", "java.lang.IllegalArgumentException: Invalid Email", e.toString());
        }
    }

    @Test
    public void testAccountConstructorBadPassword() {
        try {
            new Account("tonylee@uw.edu", "sef");
            fail("Expected IllegalArgumentException to be thrown : Password too short, no numbers or symbols");
        } catch(IllegalArgumentException e) {
            assertEquals("failure - strings are not equal", "java.lang.IllegalArgumentException: Invalid Password", e.toString());
        }
        try {
            new Account("tonylee@uw.edu", "seafeaff");
            fail("Expected IllegalArgumentException to be thrown : no numbers or symbols");
        } catch(IllegalArgumentException e) {
            assertEquals("failure - strings are not equal", "java.lang.IllegalArgumentException: Invalid Password", e.toString());
        }
        try {
            new Account("tonylee@uw.edu", "seafea!ff");
            fail("Expected IllegalArgumentException to be thrown : no numbers");
        } catch(IllegalArgumentException e) {
            assertEquals("failure - strings are not equal", "java.lang.IllegalArgumentException: Invalid Password", e.toString());
        }
        try {
            new Account("tonylee@uw.edu", "seafea3ff");
            fail("Expected IllegalArgumentException to be thrown : no symbols");
        } catch(IllegalArgumentException e) {
            assertEquals("failure - strings are not equal", "java.lang.IllegalArgumentException: Invalid Password", e.toString());
        }
    }

    @Test
    public void testAccountGettersAndSetters() {
        Account testAcc = new Account("tonyklee@uw.edu", "fork!spoon3");

        //test successes
        assertEquals(testAcc.getEmail(), "tonyklee@uw.edu");
        assertEquals(testAcc.getPassword(), "fork!spoon3");

        //test failures
        assertNotEquals(testAcc.getEmail(), "tonyklee1@uw.edu");
        assertNotEquals(testAcc.getPassword(), "fork!spoon2");

        //test setters
        testAcc.setUserEmail("tonykslee1@gmail.com");
        assertEquals(testAcc.getEmail(), "tonykslee1@gmail.com");
        assertEquals(testAcc.getPassword(), "fork!spoon3");

        testAcc.setUserPassword("knives#napkin5");
        assertEquals(testAcc.getEmail(), "tonykslee1@gmail.com");
        assertEquals(testAcc.getPassword(), "knives#napkin5");

        //test failures
        assertNotEquals(testAcc.getEmail(), "tonyklee@uw.edu");
        assertNotEquals(testAcc.getPassword(), "fork!spoon3");
    }

}
