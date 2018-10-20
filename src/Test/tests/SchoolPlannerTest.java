package tests;

import exceptions.choices.BadNavInputException;
import model.SchoolPlanner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

// run method not tested because all the individual methods are tested separately.
class SchoolPlannerTest {

    private SchoolPlanner sp;

    @BeforeEach
    void setup() {
        sp = new SchoolPlanner("Naveen");
    }

    @Test
    void testVerifyNavInput1() {
        String input = "1";
        try {
            sp.verifyNavInput(input);
        } catch (BadNavInputException e) {
            fail("Exception caught.");
        }
    }

    @Test
    void testVerifyNavInput2() {
        String input = "2";
        try {
            sp.verifyNavInput(input);
        } catch (BadNavInputException e) {
            fail("Exception caught.");
        }
    }

    @Test
    void testVerifyNavInput3() {
        String input = "3";
        try {
            sp.verifyNavInput(input);
        } catch (BadNavInputException e) {
            fail("Exception caught.");
        }
    }

    @Test
    void testVerifyNavInput4() {
        String input = "4";
        try {
            sp.verifyNavInput(input);
        } catch (BadNavInputException e) {
            fail("Exception caught.");
        }
    }

    @Test
    void testVerifyNavInputInvalid() {
        String input = "sdgfs";
        try {
            sp.verifyNavInput(input);
            fail("Exception not caught.");
        } catch (BadNavInputException e) {

        }
    }

    @Test
    void testVerifyNavInput5() {
        String input = "5";
        try {
            sp.verifyNavInput(input);
            fail("Exception not caught.");
        } catch (BadNavInputException e) {

        }
    }


    @Test
    void testValidNavigationInput1() {
        Scanner user_input = new Scanner("1\n");

        assertEquals(sp.navigationInput(user_input), 1);
    }

    @Test
    void testValidNavigationInput2() {
        Scanner user_input = new Scanner("2\n");

        assertEquals(sp.navigationInput(user_input), 2);
    }

    @Test
    void testValidNavigationInput3() {
        Scanner user_input = new Scanner("3\n");

        assertEquals(sp.navigationInput(user_input), 3);
    }

    @Test
    void testValidNavigationInput4() {
        Scanner user_input = new Scanner("4\n");

        assertEquals(sp.navigationInput(user_input), 4);
    }

    @Test
    void testBackToMenuYes() {
        Scanner user_input = new Scanner("y\n");

        // stops execution of addingItem loop, which is why false is expected.
        assertFalse(sp.backToMenu(user_input));
    }

    @Test
    void testBackToMenuNo() {
        Scanner user_input = new Scanner("n\n");

        assertTrue((sp.backToMenu(user_input)));
    }

    @Test
    void testBackToMenuInvalidNo() {
        Scanner user_input = new Scanner("wergr\nnqwere\nn");

        assertTrue((sp.backToMenu(user_input)));
    }
}
