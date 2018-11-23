package tests;

import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CollectionOfUniClassesTest {

    /*
    private String filename = "listofclasses.csv";
    CollectionOfUniClasses louc;

    @BeforeEach
    void setup() {
        louc = new CollectionOfUniClasses();
    }

    @AfterEach
    void deleteFile()
    {
        File file = new File("/Users/naveensivasankar/Desktop/ /UBC/Year 2/CPSC 210/Personal Project/projectw1_team413/" + filename);

        if (file.delete()) {
            System.out.println("File deleted successfully.");
        } else {
            System.out.println("Error deleting file.");
        }
    }

    // EXCEPTIONS TESTS

    @Test
    void testUserClassTypeValid() {
        Scanner user_input = new Scanner("LECTURE");
        try {
            louc.getInputHandler().userClassType(user_input);
        } catch (BadClassTypeException e) {
            fail("Exception caught.");
        }
    }

    @Test
    void testUserClassTypeInvalid() {
        Scanner user_input = new Scanner("OUTDOOR EXPLORATION");
        try {
            louc.getInputHandler().userClassType(user_input);
            fail("Exception not caught.");
        } catch (BadClassTypeException e) {

        }
    }

    // ADDING CLASSES TESTS

    @Test
    void testAddUniClassValidInput() {
        Scanner user_input = new Scanner("Tutorial\nCPSC 210\nElisa Baniassad\nSWNG 122\n1200\n1300\n1\n3\n5\n0\nN");
        louc.addItem(user_input);

        ArrayList<String> key = new ArrayList<>(Arrays.asList("TUTORIAL", "CPSC 210"));
        assertEquals("TUTORIAL", louc.getClassMap().get(key).getClassType());
        assertEquals("CPSC 210", louc.getClassMap().get(key).getName());
        assertEquals("Elisa Baniassad", louc.getClassMap().get(key).getProf());
        assertEquals("SWNG 122", louc.getClassMap().get(key).getLocation());
        assertEquals(1200, louc.getClassMap().get(key).getStartTime());
        assertEquals(1300, louc.getClassMap().get(key).getEndTime());

        ArrayList<Integer> days = new ArrayList<>(Arrays.asList(1, 3, 5));
        assertEquals(days, louc.getClassMap().get(key).getDays());

        Textbook t = louc.getClassMap().get(key).getTextbook();
        assertEquals(null, t.getTitle());
        assertEquals(null, t.getAuthor());
        assertEquals(0, t.getPages());
    }

    @Test
    void testAddUniClassAndView() {
        // adding one class already tested.
        Scanner user_input = new Scanner("Tutorial\nCPSC 210\nElisa Baniassad\nSWNG 122\n1200\n1300\n1\n3\n5\n0\nN");
        louc.addItem(user_input);
        louc.printItems();
    }

    @Test
    void testAddMultipleUniClassesValidInput() {
        Scanner user_input1 = new Scanner("LECTURE\nCPSC 210\nElisa Baniassad\nSWNG 122\n1200\n1300\n1\n3\n5\n0\nN");
        louc.addItem(user_input1);
        ArrayList<String> key0 = new ArrayList<>(Arrays.asList("LECTURE", "CPSC 210"));

        Scanner user_input2 = new Scanner("TUTORIAL\nCPSC 310\nElisa Baniassad\nWESB 100\n0800\n0900\n2\n4\n0\nN");
        louc.addItem(user_input2);
        ArrayList<String> key1 = new ArrayList<>(Arrays.asList("TUTORIAL", "CPSC 310"));

        Scanner user_input3 = new Scanner("DISCUSSION\nMATH 221\nSome Dude\nLSK 201\n1700\n1800\n1\n3\n5\n0\nN");
        louc.addItem(user_input3);
        ArrayList<String> key2 = new ArrayList<>(Arrays.asList("DISCUSSION", "MATH 221"));

        assertEquals("LECTURE", louc.getClassMap().get(key0).getClassType());
        assertEquals("CPSC 210", louc.getClassMap().get(key0).getName());
        assertEquals("Elisa Baniassad", louc.getClassMap().get(key0).getProf());
        assertEquals("SWNG 122", louc.getClassMap().get(key0).getLocation());
        assertEquals(1200, louc.getClassMap().get(key0).getStartTime());
        assertEquals(1300, louc.getClassMap().get(key0).getEndTime());

        ArrayList<Integer> days0 = new ArrayList<>(Arrays.asList(1, 3, 5));
        assertEquals(days0, louc.getClassMap().get(key0).getDays());

        Textbook t0 = louc.getClassMap().get(key0).getTextbook();
        assertEquals(null, t0.getTitle());
        assertEquals(null, t0.getAuthor());
        assertEquals(0, t0.getPages());

        assertEquals("TUTORIAL", louc.getClassMap().get(key1).getClassType());
        assertEquals("CPSC 310", louc.getClassMap().get(key1).getName());
        assertEquals("Elisa Baniassad", louc.getClassMap().get(key1).getProf());
        assertEquals("WESB 100", louc.getClassMap().get(key1).getLocation());
        assertEquals(800, louc.getClassMap().get(key1).getStartTime());
        assertEquals(900, louc.getClassMap().get(key1).getEndTime());

        ArrayList<Integer> days1 = new ArrayList<>(Arrays.asList(2, 4));
        assertEquals(days1, louc.getClassMap().get(key1).getDays());

        Textbook t1 = louc.getClassMap().get(key1).getTextbook();
        assertEquals(null, t1.getTitle());
        assertEquals(null, t1.getAuthor());
        assertEquals(0, t1.getPages());

        assertEquals("DISCUSSION", louc.getClassMap().get(key2).getClassType());
        assertEquals("MATH 221", louc.getClassMap().get(key2).getName());
        assertEquals("Some Dude", louc.getClassMap().get(key2).getProf());
        assertEquals("LSK 201", louc.getClassMap().get(key2).getLocation());
        assertEquals(1700, louc.getClassMap().get(key2).getStartTime());
        assertEquals(1800, louc.getClassMap().get(key2).getEndTime());

        ArrayList<Integer> days2 = new ArrayList<>(Arrays.asList(1, 3, 5));
        assertEquals(days2, louc.getClassMap().get(key2).getDays());

        Textbook t2 = louc.getClassMap().get(key2).getTextbook();
        assertEquals(null, t2.getTitle());
        assertEquals(null, t2.getAuthor());
        assertEquals(0, t1.getPages());
    }

    @Test
    void testAddMultipleUniClassesAndView() {
        // adding multiple classes already tested.
        Scanner user_input1 = new Scanner("LECTURE\nCPSC 210\nElisa Baniassad\nSWNG 122\n1200\n1300\n1\n3\n5\n0\nN");
        louc.addItem(user_input1);

        Scanner user_input2 = new Scanner("TUTORIAL\nCPSC 310\nElisa Baniassad\nWESB 100\n0800\n0900\n2\n4\n0\nN");
        louc.addItem(user_input2);

        Scanner user_input3 = new Scanner("DISCUSSION\nMATH 221\nSome Dude\nLSK 201\n1700\n1800\n1\n3\n5\n0\nN");
        louc.addItem(user_input3);

        louc.printItems();
    }

    @Test
    void testAddMultipleUniClassesInvalidTimes() {
        // If all tasks were not created, test succeeded.
        Scanner user_input1 = new Scanner("LECTURE\nCPSC 210\nElisa Baniassad\nSWNG 122\n-1\n1300\n1\n3\n5\n0");
        louc.addItem(user_input1);

        Scanner user_input2 = new Scanner("LAB\nCPSC 310\nElisa Baniassad\nWESB 100\n0800\n2400\n2\n4\n0");
        louc.addItem(user_input2);

        Scanner user_input3 = new Scanner("TUTORIAL\nMATH 221\nSome Dude\nLSK 201\n2401\n1800\n1\n3\n5\n0");
        louc.addItem(user_input3);

        assertEquals(0, louc.getClassMap().size());
    }

    @Test
    void testAddUniClassesInvalidDays() {
        // If task was not created, test succeeded.
        Scanner user_input1 = new Scanner("LECTURE\nCPSC 210\nElisa Baniassad\nSWNG 122\n1200\n1300\nsdfg\n9\n-1\n0");
        louc.addItem(user_input1);
        assertEquals(0, louc.getClassMap().size());
    }

    @Test
    void testRemoveItem() {
        Scanner user_input = new Scanner("LECTURE\nCPSC 210\nElisa Baniassad\nSWNG 122\n1200\n1300\n1\n3\n5\n0\nN\nLECTURE\nCPSC 210");
        louc.addItem(user_input);

        try {
            louc.removeItem(user_input);
        } catch (ItemNotFoundException e) {
            e.printStackTrace();
        }
        assertEquals(0, louc.getClassMap().size());

    }

    @Test
    void testRemoveItemMultiple() {
        Scanner user_input1 = new Scanner("LECTURE\nCPSC 210\nElisa Baniassad\nSWNG 122\n1200\n1300\n1\n3\n5\n0\nN\nLECTURE\nCPSC 210");
        louc.addItem(user_input1);

        Scanner user_input2 = new Scanner("LAB\nCPSC 310\nElisa Baniassad\nWESB 100\n0800\n2400\n2\n4\n0\nLAB\nCPSC 310");
        louc.addItem(user_input2);

        try {
            louc.removeItem(user_input1);
            louc.removeItem(user_input2);
        } catch (ItemNotFoundException e) {
            e.printStackTrace();
        }

        assertEquals(0, louc.getClassMap().size());
    }

    @Test
    void testAddTextbook() {
        Scanner user_input = new Scanner("LECTURE\nCPSC 210\nElisa Baniassad\nSWNG 122\n1200\n1300\n1\n3\n5\n0\nN\nLECTURE\nCPSC 210");
        louc.addItem(user_input);

        Scanner user_input1 = new Scanner("LECTURE\nCPSC 210\nTextbook\nAuthor\n300");
        louc.addTextbook(user_input1);

        ArrayList<String> key = new ArrayList<>(Arrays.asList("LECTURE", "CPSC 210"));
        Textbook t = louc.getClassMap().get(key).getTextbook();
        assertEquals("Textbook", t.getTitle());
        assertEquals("Author", t.getAuthor());
        assertEquals(300, t.getPages());
    }

    @Test
    void testRemoveTextbook() {
        Scanner user_input = new Scanner("LECTURE\nCPSC 210\nElisa Baniassad\nSWNG 122\n1200\n1300\n1\n3\n5\n0\nN\nLECTURE\nCPSC 210");
        louc.addItem(user_input);

        Scanner user_input1 = new Scanner("LECTURE\nCPSC 210\nTextbook\nAuthor\n300");
        louc.addTextbook(user_input1);

        ArrayList<String> key = new ArrayList<>(Arrays.asList("LECTURE", "CPSC 210"));

        Scanner user_input2 = new Scanner("LECTURE\nCPSC 210");
        louc.removeTextbook(user_input2);

        Textbook t = louc.getClassMap().get(key).getTextbook();
        assertEquals(null, t.getTitle());
        assertEquals(null, t.getAuthor());
        assertEquals(0, t.getPages());

    }

    */
}
