package ui;

import inputs.Textbook;
import inputs.UniClass;
import model.InputScreen;
import model.collections.CollectionOfTextbooks;
import model.collections.CollectionOfUniClasses;
import model.observers.Observer;
import model.observers.UniClassesObserver;

import javax.sql.rowset.JdbcRowSet;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowListener;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
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
        addClass.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addClassDialog();
            }
        });
        removeClass = new JButton("Remove Class");
        removeClass.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeClassDialog();
            }
        });
        addTextbook = new JButton("Add Textbook");
        addTextbook.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addTextbookDialog();
            }
        });
        removeTextbook = new JButton("Remove Textbook");
        removeTextbook.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeTextbookDialog();
            }
        });

        bottom.add(addClass);
        bottom.add(Box.createRigidArea(new Dimension(10, 0)));
        bottom.add(removeClass);
        bottom.add(Box.createRigidArea(new Dimension(10, 0)));
        bottom.add(addTextbook);
        bottom.add(Box.createRigidArea(new Dimension(10, 0)));
        bottom.add(removeTextbook);

        add(bottom);

    }

    public void addClassDialog() {
        JFrame owner = new JFrame();

        JDialog addClassScreen = new JDialog(owner, "Add Class");
        JPanel addClassPanel = new JPanel();
        BoxLayout layout = new BoxLayout(addClassPanel, BoxLayout.PAGE_AXIS);
        addClassPanel.setLayout(layout);

        JLabel title = new JLabel("Add a Class");
        title.setFont(new Font("Serif", Font.PLAIN, 20));
        title.setAlignmentX(CENTER_ALIGNMENT);
        title.setBorder(new EmptyBorder(20, 10, 10, 10));

        JTextArea detailText = new JTextArea();
        detailText.setMaximumSize(new Dimension(250, 120));
        detailText.setLineWrap(true);
        detailText.setEditable(false);
        detailText.setOpaque(false);
        detailText.setFont(new Font("Serif", Font.PLAIN, 16));
        detailText.append("All classes must have a class type, a \nname, a location, and a professor.\n\n" + "They must also have a " +
                "start time\n (e.g. 1400), an end time (e.g. 1600),\na set of days when the class runs,\n and an optional textbook.");

        JPanel classTypePanel = new JPanel();
        classTypePanel.setMaximumSize(new Dimension(250, 40));
        classTypePanel.setAlignmentY(CENTER_ALIGNMENT);
        classTypePanel.setLayout(new FlowLayout());
        JLabel classTypeLabel = new JLabel("Class Type:");
        String types[] = {"LECTURE", "LAB", "DISCUSSION", "TUTORIAL"};
        JComboBox classTypeBox = new JComboBox(types);
        classTypePanel.add(classTypeLabel);
        classTypePanel.add(classTypeBox);

        JPanel classNamePanel = new JPanel();
        classNamePanel.setMaximumSize(new Dimension(250, 40));
        classNamePanel.setAlignmentY(CENTER_ALIGNMENT);
        classNamePanel.setLayout(new FlowLayout());
        JLabel classNameLabel = new JLabel("Class Name:");
        JTextField classNameText = new JTextField();
        classNameText.setPreferredSize(new Dimension(130, 30));
        classNamePanel.add(classNameLabel);
        classNamePanel.add(classNameText);

        JPanel professorPanel = new JPanel();
        professorPanel.setMaximumSize(new Dimension(250, 40));
        professorPanel.setAlignmentY(CENTER_ALIGNMENT);
        professorPanel.setLayout(new FlowLayout());
        JLabel professorLabel = new JLabel("Professor:");
        JTextField professorText = new JTextField();
        professorText.setPreferredSize(new Dimension(130, 30));
        professorPanel.add(professorLabel);
        professorPanel.add(professorText);

        JPanel locationPanel = new JPanel();
        locationPanel.setMaximumSize(new Dimension(250, 40));
        locationPanel.setAlignmentY(CENTER_ALIGNMENT);
        locationPanel.setLayout(new FlowLayout());
        JLabel locationLabel = new JLabel("Location:");
        JTextField locationText = new JTextField();
        locationText.setPreferredSize(new Dimension(130, 30));
        locationPanel.add(locationLabel);
        locationPanel.add(locationText);

        JPanel startTimePanel = new JPanel();
        startTimePanel.setMaximumSize(new Dimension(250, 40));
        startTimePanel.setAlignmentY(CENTER_ALIGNMENT);
        startTimePanel.setLayout(new FlowLayout());
        JLabel startTimeLabel = new JLabel("Start Time:");
        JTextField startTimeText = new JTextField();
        startTimeText.setPreferredSize(new Dimension(130, 30));
        startTimePanel.add(startTimeLabel);
        startTimePanel.add(startTimeText);

        JPanel endTimePanel = new JPanel();
        endTimePanel.setMaximumSize(new Dimension(250, 40));
        endTimePanel.setAlignmentY(CENTER_ALIGNMENT);
        endTimePanel.setLayout(new FlowLayout());
        JLabel endTimeLabel = new JLabel("End Time:");
        JTextField endTimeText = new JTextField();
        endTimeText.setPreferredSize(new Dimension(130, 30));
        endTimePanel.add(endTimeLabel);
        endTimePanel.add(endTimeText);

        JPanel daysPanel = new JPanel();
        daysPanel.setMaximumSize(new Dimension(250, 130));
        daysPanel.setAlignmentY(CENTER_ALIGNMENT);
        daysPanel.setLayout(new FlowLayout());
        JTextArea daysLabel = new JTextArea("Days:\n\n(Use Ctrl/Cmd \nto select more \nthan one day)");
        daysLabel.setLineWrap(true);
        daysLabel.setOpaque(false);
        daysLabel.setEditable(false);
        daysLabel.setMaximumSize(new Dimension(150, 120));
        String days[] = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
        JList<String> daysList = new JList<>(days);
        daysList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        JScrollPane daysScroll = new JScrollPane(daysList);
        daysScroll.setPreferredSize(new Dimension(90, 100));
        daysScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        daysPanel.add(daysLabel);
        daysPanel.add(Box.createRigidArea(new Dimension(5, 0)));
        daysPanel.add(daysScroll);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.LINE_AXIS));
        buttonPanel.setMaximumSize(new Dimension(250, 40));
        JButton addTextbook = new JButton("Add Textbook");
        JButton submit = new JButton("Submit");
        addTextbook.setAlignmentX(CENTER_ALIGNMENT);
        submit.setAlignmentX(CENTER_ALIGNMENT);
        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                owner.dispose();
            }
        });


        buttonPanel.add(addTextbook);
        buttonPanel.add(Box.createRigidArea(new Dimension(30, 0)));
        buttonPanel.add(submit);
        buttonPanel.setAlignmentX(CENTER_ALIGNMENT);

        JTextArea textbookText = new JTextArea();
        textbookText.setMaximumSize(new Dimension(250, 100));
        textbookText.setMargin(new Insets(5, 5, 5, 5));
        textbookText.setLineWrap(true);
        textbookText.setEditable(false);
        textbookText.setVisible(false);

        ArrayList<String> textbookDetails = new ArrayList<>();
        JDialog addTextbookScreen = new JDialog();
        addTextbook.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textbookInfoDialog(addTextbookScreen, textbookDetails);
                if (textbookDetails.size() != 0) {
                    textbookText.setText("Title: " + textbookDetails.get(0) + "\n" +
                            "Author: " + textbookDetails.get(1) + "\n" +
                            "Pages: " + textbookDetails.get(2) + "\n");
                    addClassScreen.setSize(new Dimension(300, 740));
                    textbookText.setVisible(true);
                }
            }
        });

        addClassPanel.add(title);
        addClassPanel.add(detailText);
        addClassPanel.add(classTypePanel);
        addClassPanel.add(classNamePanel);
        addClassPanel.add(professorPanel);
        addClassPanel.add(locationPanel);
        addClassPanel.add(startTimePanel);
        addClassPanel.add(endTimePanel);
        addClassPanel.add(daysPanel);
        addClassPanel.add(textbookText);
        addClassPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        addClassPanel.add(buttonPanel);

        addClassScreen.add(addClassPanel);
        addClassScreen.setSize(new Dimension(300, 630));
        addClassScreen.setVisible(true);

    }

    public void textbookInfoDialog(JDialog dialog, ArrayList<String> textbookDetails) {
        JFrame owner = new JFrame();
        dialog = new JDialog(owner, "Add Textbook");
        dialog.setModal(true);
        dialog.getRootPane().setWindowDecorationStyle(JRootPane.NONE);
        dialog.setUndecorated(true);
        JPanel textbookInfoPanel = new JPanel();
        BoxLayout layout = new BoxLayout(textbookInfoPanel, BoxLayout.PAGE_AXIS);
        textbookInfoPanel.setLayout(layout);

        JLabel title = new JLabel("Add a Textbook");
        title.setFont(new Font("Serif", Font.PLAIN, 20));
        title.setAlignmentX(CENTER_ALIGNMENT);
        title.setBorder(new EmptyBorder(20, 10, 10, 10));

        JTextArea detailText = new JTextArea();
        detailText.setMaximumSize(new Dimension(280, 100));
        detailText.setLineWrap(true);
        detailText.setEditable(false);
        detailText.setOpaque(false);
        detailText.setFont(new Font("Serif", Font.PLAIN, 16));
        detailText.append("To add a textbook, please provide\n a title, author and number of pages.\n\n" +
        "To exit without adding, leave all\nfields blank and click submit.");
        detailText.setAlignmentX(CENTER_ALIGNMENT);
        detailText.setBorder(new EmptyBorder(0, 30, 0, 0));

        JPanel textbookNamePanel = new JPanel();
        textbookNamePanel.setMaximumSize(new Dimension(250, 40));
        textbookNamePanel.setAlignmentY(CENTER_ALIGNMENT);
        textbookNamePanel.setLayout(new FlowLayout());
        JLabel textbookNameLabel = new JLabel("Title:");
        JTextField textbookNameText = new JTextField();
        textbookNameText.setPreferredSize(new Dimension(130, 30));
        textbookNamePanel.add(textbookNameLabel);
        textbookNamePanel.add(textbookNameText);

        JPanel authorPanel = new JPanel();
        authorPanel.setMaximumSize(new Dimension(250, 40));
        authorPanel.setAlignmentY(CENTER_ALIGNMENT);
        authorPanel.setLayout(new FlowLayout());
        JLabel authorLabel = new JLabel("Author:");
        JTextField authorText = new JTextField();
        authorText.setPreferredSize(new Dimension(130, 30));
        authorPanel.add(authorLabel);
        authorPanel.add(authorText);

        JPanel pagesPanel = new JPanel();
        pagesPanel.setMaximumSize(new Dimension(250, 40));
        pagesPanel.setAlignmentY(CENTER_ALIGNMENT);
        pagesPanel.setLayout(new FlowLayout());
        JLabel pagesLabel = new JLabel("Pages:");
        JTextField pagesText = new JTextField();
        pagesText.setPreferredSize(new Dimension(130, 30));
        pagesPanel.add(pagesLabel);
        pagesPanel.add(pagesText);


        JButton submit = new JButton("Submit");
        submit.setAlignmentX(CENTER_ALIGNMENT);
        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!textbookNameText.getText().equals("")) { textbookDetails.add(textbookNameText.getText()); }
                if (!authorText.getText().equals("")) { textbookDetails.add(authorText.getText()); }
                if (!pagesText.getText().equals("")) { textbookDetails.add(pagesText.getText()); }
                owner.dispose();
            }
        });

        textbookInfoPanel.add(title);
        textbookInfoPanel.add(detailText);
        textbookInfoPanel.add(textbookNamePanel);
        textbookInfoPanel.add(authorPanel);
        textbookInfoPanel.add(pagesPanel);
        textbookInfoPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        textbookInfoPanel.add(submit);

        dialog.add(textbookInfoPanel);
        dialog.setSize(new Dimension(300, 330));
        dialog.setVisible(true);
    }

    public void removeClassDialog() {
        JFrame owner = new JFrame();

        JDialog removeClassScreen = new JDialog(owner, "Add Class");
        JPanel removeClassPanel = new JPanel();
        BoxLayout layout = new BoxLayout(removeClassPanel, BoxLayout.PAGE_AXIS);
        removeClassPanel.setLayout(layout);

        JLabel title = new JLabel("Remove a Class");
        title.setFont(new Font("Serif", Font.PLAIN, 20));
        title.setAlignmentX(CENTER_ALIGNMENT);
        title.setBorder(new EmptyBorder(20, 10, 10, 10));

        JTextArea detailText = new JTextArea();
        detailText.setMaximumSize(new Dimension(250, 40));
        detailText.setLineWrap(true);
        detailText.setEditable(false);
        detailText.setOpaque(false);
        detailText.setFont(new Font("Serif", Font.PLAIN, 16));
        detailText.append("To remove a class, provide its name\n and class type.");

        JPanel classTypePanel = new JPanel();
        classTypePanel.setMaximumSize(new Dimension(250, 40));
        classTypePanel.setAlignmentY(CENTER_ALIGNMENT);
        classTypePanel.setLayout(new FlowLayout());
        JLabel classTypeLabel = new JLabel("Class Type:");
        String types[] = {"LECTURE", "LAB", "DISCUSSION", "TUTORIAL"};
        JComboBox classTypeBox = new JComboBox(types);
        classTypePanel.add(classTypeLabel);
        classTypePanel.add(classTypeBox);

        JPanel classNamePanel = new JPanel();
        classNamePanel.setMaximumSize(new Dimension(250, 40));
        classNamePanel.setAlignmentY(CENTER_ALIGNMENT);
        classNamePanel.setLayout(new FlowLayout());
        JLabel classNameLabel = new JLabel("Class Name:");
        JTextField classNameText = new JTextField();
        classNameText.setPreferredSize(new Dimension(130, 30));
        classNamePanel.add(classNameLabel);
        classNamePanel.add(classNameText);

        JButton submit = new JButton("Submit");
        submit.setAlignmentX(CENTER_ALIGNMENT);
        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                owner.dispose();
            }
        });

        removeClassPanel.add(title);
        removeClassPanel.add(detailText);
        removeClassPanel.add(classTypePanel);
        removeClassPanel.add(classNamePanel);
        removeClassPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        removeClassPanel.add(submit);

        removeClassScreen.add(removeClassPanel);
        removeClassScreen.setSize(new Dimension(300, 250));
        removeClassScreen.setVisible(true);
    }

    public void addTextbookDialog() {
        JFrame owner = new JFrame();
        JDialog addTextbookScreen = new JDialog(owner, "Add Textbook");
        JPanel addTextbookPanel = new JPanel();
        BoxLayout layout = new BoxLayout(addTextbookPanel, BoxLayout.PAGE_AXIS);
        addTextbookPanel.setLayout(layout);

        JLabel title = new JLabel("Add a Textbook");
        title.setFont(new Font("Serif", Font.PLAIN, 20));
        title.setAlignmentX(CENTER_ALIGNMENT);
        title.setBorder(new EmptyBorder(20, 10, 10, 10));

        JTextArea detailText = new JTextArea();
        detailText.setMaximumSize(new Dimension(280, 110));
        detailText.setLineWrap(true);
        detailText.setEditable(false);
        detailText.setOpaque(false);
        detailText.setFont(new Font("Serif", Font.PLAIN, 16));
        detailText.append("To add a textbook, please provide\n a title, author and number of pages.\n\n" +
        "Also provide the class type and\nname of the class that uses the\ntextbook to be added.");
        detailText.setAlignmentX(CENTER_ALIGNMENT);
        detailText.setBorder(new EmptyBorder(0, 30, 0, 0));

        JPanel classTypePanel = new JPanel();
        classTypePanel.setMaximumSize(new Dimension(250, 40));
        classTypePanel.setAlignmentY(CENTER_ALIGNMENT);
        classTypePanel.setLayout(new FlowLayout());
        JLabel classTypeLabel = new JLabel("Class Type:");
        String types[] = {"LECTURE", "LAB", "DISCUSSION", "TUTORIAL"};
        JComboBox classTypeBox = new JComboBox(types);
        classTypePanel.add(classTypeLabel);
        classTypePanel.add(classTypeBox);

        JPanel classNamePanel = new JPanel();
        classNamePanel.setMaximumSize(new Dimension(250, 40));
        classNamePanel.setAlignmentY(CENTER_ALIGNMENT);
        classNamePanel.setLayout(new FlowLayout());
        JLabel classNameLabel = new JLabel("Class Name:");
        JTextField classNameText = new JTextField();
        classNameText.setPreferredSize(new Dimension(130, 30));
        classNamePanel.add(classNameLabel);
        classNamePanel.add(classNameText);

        JPanel textbookNamePanel = new JPanel();
        textbookNamePanel.setMaximumSize(new Dimension(250, 40));
        textbookNamePanel.setAlignmentY(CENTER_ALIGNMENT);
        textbookNamePanel.setLayout(new FlowLayout());
        JLabel textbookNameLabel = new JLabel("Title:");
        JTextField textbookNameText = new JTextField();
        textbookNameText.setPreferredSize(new Dimension(130, 30));
        textbookNamePanel.add(textbookNameLabel);
        textbookNamePanel.add(textbookNameText);

        JPanel authorPanel = new JPanel();
        authorPanel.setMaximumSize(new Dimension(250, 40));
        authorPanel.setAlignmentY(CENTER_ALIGNMENT);
        authorPanel.setLayout(new FlowLayout());
        JLabel authorLabel = new JLabel("Author:");
        JTextField authorText = new JTextField();
        authorText.setPreferredSize(new Dimension(130, 30));
        authorPanel.add(authorLabel);
        authorPanel.add(authorText);

        JPanel pagesPanel = new JPanel();
        pagesPanel.setMaximumSize(new Dimension(250, 40));
        pagesPanel.setAlignmentY(CENTER_ALIGNMENT);
        pagesPanel.setLayout(new FlowLayout());
        JLabel pagesLabel = new JLabel("Pages:");
        JTextField pagesText = new JTextField();
        pagesText.setPreferredSize(new Dimension(130, 30));
        pagesPanel.add(pagesLabel);
        pagesPanel.add(pagesText);

        JButton submit = new JButton("Submit");
        submit.setAlignmentX(CENTER_ALIGNMENT);
        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                owner.dispose();
            }
        });

        addTextbookPanel.add(title);
        addTextbookPanel.add(detailText);
        addTextbookPanel.add(classTypePanel);
        addTextbookPanel.add(classNamePanel);
        addTextbookPanel.add(textbookNamePanel);
        addTextbookPanel.add(authorPanel);
        addTextbookPanel.add(pagesPanel);
        addTextbookPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        addTextbookPanel.add(submit);

        addTextbookScreen.add(addTextbookPanel);
        addTextbookScreen.setSize(300, 440);
        addTextbookScreen.setVisible(true);

    }

    public void removeTextbookDialog() {
        JFrame owner = new JFrame();

        JDialog removeTextbookScreen = new JDialog(owner, "Add Class");
        JPanel removeTextbookPanel = new JPanel();
        BoxLayout layout = new BoxLayout(removeTextbookPanel, BoxLayout.PAGE_AXIS);
        removeTextbookPanel.setLayout(layout);

        JLabel title = new JLabel("Remove a Textbook");
        title.setFont(new Font("Serif", Font.PLAIN, 20));
        title.setAlignmentX(CENTER_ALIGNMENT);
        title.setBorder(new EmptyBorder(20, 10, 10, 10));

        JTextArea detailText = new JTextArea();
        detailText.setMaximumSize(new Dimension(250, 60));
        detailText.setLineWrap(true);
        detailText.setEditable(false);
        detailText.setOpaque(false);
        detailText.setFont(new Font("Serif", Font.PLAIN, 16));
        detailText.append("To remove a textbook from a class,\nprovide the name and class type of\nthe class.");
        detailText.setBorder(new EmptyBorder(0, 20, 0, 0));

        JPanel classTypePanel = new JPanel();
        classTypePanel.setMaximumSize(new Dimension(250, 40));
        classTypePanel.setAlignmentY(CENTER_ALIGNMENT);
        classTypePanel.setLayout(new FlowLayout());
        JLabel classTypeLabel = new JLabel("Class Type:");
        String types[] = {"LECTURE", "LAB", "DISCUSSION", "TUTORIAL"};
        JComboBox classTypeBox = new JComboBox(types);
        classTypePanel.add(classTypeLabel);
        classTypePanel.add(classTypeBox);

        JPanel classNamePanel = new JPanel();
        classNamePanel.setMaximumSize(new Dimension(250, 40));
        classNamePanel.setAlignmentY(CENTER_ALIGNMENT);
        classNamePanel.setLayout(new FlowLayout());
        JLabel classNameLabel = new JLabel("Class Name:");
        JTextField classNameText = new JTextField();
        classNameText.setPreferredSize(new Dimension(130, 30));
        classNamePanel.add(classNameLabel);
        classNamePanel.add(classNameText);

        JButton submit = new JButton("Submit");
        submit.setAlignmentX(CENTER_ALIGNMENT);
        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                owner.dispose();
            }
        });

        removeTextbookPanel.add(title);
        removeTextbookPanel.add(detailText);
        removeTextbookPanel.add(classTypePanel);
        removeTextbookPanel.add(classNamePanel);
        removeTextbookPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        removeTextbookPanel.add(submit);

        removeTextbookScreen.add(removeTextbookPanel);
        removeTextbookScreen.setSize(new Dimension(300, 270));
        removeTextbookScreen.setVisible(true);
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
