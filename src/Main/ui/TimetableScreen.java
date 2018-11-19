package ui;

import inputs.Textbook;
import inputs.UniClass;
import model.InputScreen;
import model.collections.CollectionOfTextbooks;
import model.collections.CollectionOfUniClasses;
import model.observers.Observer;
import model.observers.UniClassesObserver;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class TimetableScreen extends JPanel implements InputScreen {

    private CollectionOfUniClasses louc = new CollectionOfUniClasses();
    private int loadState = 0;
    private JPanel top;
    private JPanel bottom;
    private JTextArea classesTextArea;
    private JTextArea textbooksTextArea;
    private JButton addClass;
    private JButton removeClass;
    private JButton addTextbook;
    private JButton removeTextbook;

    // EFFECTS: creates a new TimetableScreen object.
    public TimetableScreen() {
        loadItemsIntoListObject();

        BoxLayout flowMain = new BoxLayout(this, BoxLayout.PAGE_AXIS);
        setLayout(flowMain);

        top = new JPanel();
        top.setSize(620, 300);

        BoxLayout flowTop = new BoxLayout(top, BoxLayout.PAGE_AXIS);
        top.setLayout(flowTop);

        bottom = new JPanel();
        bottom.setSize(620, 300);

        BoxLayout flowBottom = new BoxLayout(bottom, BoxLayout.LINE_AXIS);
        bottom.setLayout(flowBottom);

        JLabel title = new JLabel();
        title.setPreferredSize(new Dimension(300, 40));
        title.setText("Timetable and Textbooks");
        title.setFont(new Font("Serif", Font.PLAIN, 20));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        classesTextArea = new JTextArea();
        classesTextArea.setMargin(new Insets(10, 10, 10, 10));
        classesTextArea.setAlignmentX(Component.CENTER_ALIGNMENT);
        classesTextArea.setLineWrap(true);
        classesTextArea.setEditable(false);
        JScrollPane classesScroll = new JScrollPane(classesTextArea);
        classesScroll.setPreferredSize(new Dimension(590, 100));
        classesScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        textbooksTextArea = new JTextArea();
        textbooksTextArea.setMargin(new Insets(10, 10, 10, 10));
        textbooksTextArea.setAlignmentX(Component.CENTER_ALIGNMENT);
        textbooksTextArea.setLineWrap(true);
        textbooksTextArea.setEditable(false);
        JScrollPane textbooksScroll = new JScrollPane(textbooksTextArea);
        textbooksScroll.setPreferredSize(new Dimension(590, 100));
        textbooksScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        classesTextArea.append("** CLASSES **\n\n");
        for (UniClass c : louc.getClassMap().values()) {
            classesTextArea.append(c.toString());
        }

        ArrayList<Textbook> textbooks = getListOfUniClasses().getCollectionOfTextbooks().getTextbooksList();

        textbooksTextArea.append(" ** TEXTBOOKS **\n\n");
        for (Textbook t : textbooks) {
            textbooksTextArea.append(t.toStringFull());
        }

        top.add(title);
        top.add(classesScroll);
        top.add(Box.createRigidArea(new Dimension(0, 10)));
        top.add(textbooksScroll);
        top.add(Box.createRigidArea(new Dimension(0, 10)));
        add(top);

        addClass = new JButton("Add Class");
        removeClass = new JButton("Remove Class");
        addTextbook = new JButton("Add Textbook");
        removeTextbook = new JButton("Remove Textbook");

        bottom.add(addClass);
        bottom.add(Box.createRigidArea(new Dimension(10, 0)));
        bottom.add(removeClass);
        bottom.add(Box.createRigidArea(new Dimension(10, 0)));
        bottom.add(addTextbook);
        bottom.add(Box.createRigidArea(new Dimension(10, 0)));
        bottom.add(removeTextbook);

        add(bottom);

    }

    @Override
    public int getloadState() {
        return loadState;
    }

    // EFFECTS: gets input from the user to decide whether they want to add classes or view classes.
    public int handleOptions(Scanner user_input) {
        int handleOptions;
        while (true) {
            try {
                System.out.println("What do you want to do?");
                System.out.println("1. View Classes\n2. Add Classes\n3. Remove Classes\n4. Remove Textbook from Class\n" +
                        "5. Add Textbooks\n6. View Textbooks");
                handleOptions = user_input.nextInt();
                user_input.nextLine();
                Integer x = verifyInput(handleOptions);
                if (x != null) return x;
            }
            catch (InputMismatchException n) {
                System.out.println("You must enter an integer.");
                user_input.nextLine();
            }
        }
    }

    // EFFECTS: verifies input provided in handleOptions() method.
    private Integer verifyInput(int handleOptions) {
        if (handleOptions == 1) {
            return 1;
        } else if (handleOptions == 2) {
            return 2;
        } else if (handleOptions == 3) {
            return 3;
        } else if (handleOptions == 4) {
            return 4;
        } else if (handleOptions == 5) {
            return 5;
        } else if (handleOptions == 6) {
            return 6;
        } else {
            System.out.println("Enter a valid choice.");
            return null;
        }
    }

    // EFFECTS: prints out details when adding an item.
    public void addingItemDetails() {
        System.out.println("All classes must have the following fields:");
        System.out.println("-> A class Type (LECTURE/LAB/DISCUSSION/TUTORIAL)\n-> A name\n" +
                "-> A professor\n-> A location\n-> A start time\n-> An end time.\n-> Days of the week");
        System.out.println("N.B. Start and End Time must be in 24hr format e.g. 1400 = 2 pm.");
        System.out.println("N.B. Enter the number that corresponds to the day e.g. Monday = 1, Thursday = 4 etc.");
    }

    // MODIFIES: this
    // EFFECTS: allows user to create a new UniClass and then saves the created UniClass.
    public void addToListObject(Scanner user_input) {
        addingItemDetails();
        louc.addItem(user_input);
    }

    // MODIFIES: this
    // EFFECTS: loads all classes from a file into the classList.
    public void loadItemsIntoListObject() {
        String filename = "listofclasses.csv";
        String currentClass;
        try {
            FileReader fileReader = new FileReader(filename);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            while ((currentClass = bufferedReader.readLine()) != null) {
                louc.loadSingleItem(currentClass);
            }
        } catch (FileNotFoundException fnfex) {
            loadState = 1;
        } catch (IOException ioex) {
            loadState = 2;
        }
        loadState = 0;
    }

    public void removeItem(Scanner user_input) {
        louc.removeItem(user_input);
    }

    public void removeTextbook(Scanner user_input) {
        louc.removeTextbook(user_input);
    }

    public void saveList() {
        louc.saveCollection();
    }

    public void printStoredItems() {
        louc.printItems();
    }

    public void printTextbooks() {
        louc.printTextbooks();
    }

    public void addTextbook(Scanner user_input) {
        louc.addTextbook(user_input);
    }

    public CollectionOfUniClasses getListOfUniClasses() {
        return louc;
    }

}
