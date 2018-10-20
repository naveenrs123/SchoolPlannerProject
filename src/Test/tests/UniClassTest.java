package tests;

import inputs.UniClass;

import java.util.ArrayList;
import java.util.Arrays;
import org.junit.jupiter.api.Test;

// Testing of valid/invalid input handled in TimetableScreenTest
class UniClassTest {

    @Test
    void testConstructor() {
        ArrayList<Integer> days = new ArrayList<>(Arrays.asList(1, 3, 5));
        UniClass newUniClass = new UniClass("LECTURE", "Class", "Prof", "Room", 1400, 1500, days);
    }

    @Test
    void testPrintUniClass() {
        ArrayList<Integer> days = new ArrayList<>(Arrays.asList(1, 3, 5));
        UniClass newUniClass = new UniClass("TUTORIAL", "Class","Prof", "Room", 1400, 1500, days);

        newUniClass.printItem();

    }

    @Test
    void testDifferentClassTypes() {
        ArrayList<Integer> days = new ArrayList<>(Arrays.asList(1, 3, 5));
        UniClass c1 = new UniClass("LECTURE", "Class","Prof", "Room", 1400, 1500, days);
        UniClass c2 = new UniClass("LAB", "Class","Prof", "Room", 1400, 1500, days);
        UniClass c3 = new UniClass("TUTORIAL", "Class","Prof", "Room", 1400, 1500, days);
        UniClass c4 = new UniClass("DISCUSSION", "Class","Prof", "Room", 1400, 1500, days);

        c1.printItem();
        c2.printItem();
        c3.printItem();
        c4.printItem();

    }

}
