package ui;

import exceptions.BadTimeException;
import exceptions.EmptyCollectionException;
import exceptions.ItemNotFoundException;
import exceptions.NoTextbookException;
import inputs.Textbook;
import inputs.UniClass;
import model.collections.CollectionOfUniClasses;
import model.observerPattern.Observer;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.*;
import java.util.List;

public class TimetableScreen extends JPanel implements InputScreen, Observer {

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
        louc.addObserver(this);
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

        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.LINE_AXIS));
        titlePanel.setBackground(Color.BLACK);
        titlePanel.setOpaque(true);
        JLabel title = new JLabel();
        title.setPreferredSize(new Dimension(620, 50));
        title.setBorder(new EmptyBorder(0, 200, 0, 210));
        title.setText("Timetable and Textbooks");
        title.setFont(new Font("Serif", Font.PLAIN, 20));
        title.setForeground(Color.WHITE);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setAlignmentY(Component.CENTER_ALIGNMENT);
        titlePanel.setAlignmentX(CENTER_ALIGNMENT);
        titlePanel.add(title);

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

        top.add(titlePanel);
        top.add(Box.createRigidArea(new Dimension(0, 10)));
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
        addClassScreen.setResizable(false);
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

        JTextArea errorMessage = new JTextArea();
        errorMessage.setVisible(false);
        errorMessage.setOpaque(false);
        errorMessage.setLineWrap(true);
        errorMessage.setAlignmentX(CENTER_ALIGNMENT);
        errorMessage.setMaximumSize(new Dimension(250, 60));
        errorMessage.setBorder(new EmptyBorder(0, 0, 10, 0));

        ArrayList<String> textbookDetails = new ArrayList<>();
        addTextbook.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textbookInfoDialog(textbookDetails);
                if (textbookDetails.size() != 0) {
                    textbookText.setText("Title: " + textbookDetails.get(0) + "\n" +
                            "Author: " + textbookDetails.get(1) + "\n" +
                            "Pages: " + textbookDetails.get(2) + "\n");
                    addClassScreen.setSize(new Dimension(300, 740));
                    textbookText.setVisible(true);
                }
            }
        });

        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String classType = (String) classTypeBox.getSelectedItem();
                classType = classType.trim();
                String className = classNameText.getText().trim();
                String professor = professorText.getText().trim();
                String location = locationText.getText().trim();
                String startTime = startTimeText.getText().trim();
                String endTime = endTimeText.getText().trim();

                List<String> days = daysList.getSelectedValuesList();
                if (textbookDetails.size() == 0) {
                    Collections.addAll(textbookDetails, "", "", "");
                }
                ArrayList<String> item = new ArrayList<>(Arrays.asList(classType, className, professor, location, startTime, endTime));
                item.addAll(textbookDetails);
                item.addAll(days);

                if (className.equals("")) {
                    errorMessage.setText("Please enter a class name.");
                } else if (professor.equals("")) {
                    errorMessage.setText("Please enter a professor.");
                } else if (location.equals("")) {
                    errorMessage.setText("Please enter a location.");
                } else if (startTime.equals("")) {
                    errorMessage.setText("Please enter a start time.");
                } else if (endTime.equals("")) {
                    errorMessage.setText("Please enter an end time.");
                } else if (days.size() == 0) {
                    errorMessage.setText("Please select at least 1 day.");
                }
                else {
                    try {
                        addToListObject(item);
                        errorMessage.setText("Class added successfully. You may now close this dialog.");
                    } catch (NumberFormatException nfex) {
                        errorMessage.setText("Y");
                    } catch (BadTimeException e1) {
                        errorMessage.setText("The start or end time is not a number.");
                    } catch (IOException e1) {
                        errorMessage.setText("Error saving class to file.");
                    }
                }
                if (textbookText.isVisible()) {
                    addClassScreen.setSize(new Dimension(300, 760));
                } else
                {
                    addClassScreen.setSize(new Dimension(300, 650));
                }
                errorMessage.setVisible(true);
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
        addClassPanel.add(errorMessage);
        addClassPanel.add(buttonPanel);

        addClassScreen.add(addClassPanel);
        addClassScreen.setSize(new Dimension(300, 630));
        addClassScreen.setVisible(true);

    }

    public void textbookInfoDialog(ArrayList<String> textbookDetails) {
        JFrame owner = new JFrame();
        JDialog dialog = new JDialog(owner, "Add a Textbook");
        dialog.setResizable(false);
        dialog.setModal(true);
        JPanel textbookInfoPanel = new JPanel();
        BoxLayout layout = new BoxLayout(textbookInfoPanel, BoxLayout.PAGE_AXIS);
        textbookInfoPanel.setLayout(layout);

        JLabel title = new JLabel("Add a Textbook");
        title.setFont(new Font("Serif", Font.PLAIN, 20));
        title.setAlignmentX(CENTER_ALIGNMENT);
        title.setBorder(new EmptyBorder(20, 10, 10, 10));

        JTextArea detailText = new JTextArea();
        detailText.setMaximumSize(new Dimension(280, 60));
        detailText.setLineWrap(true);
        detailText.setEditable(false);
        detailText.setOpaque(false);
        detailText.setFont(new Font("Serif", Font.PLAIN, 16));
        detailText.append("To add a textbook, please provide\n a title, author and number of pages.");
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

        JTextArea errorMessage = new JTextArea();
        errorMessage.setVisible(false);
        errorMessage.setOpaque(false);
        errorMessage.setLineWrap(true);
        errorMessage.setAlignmentX(CENTER_ALIGNMENT);
        errorMessage.setMaximumSize(new Dimension(250, 60));
        errorMessage.setBorder(new EmptyBorder(0, 0, 10, 0));

        JButton submit = new JButton("Submit");
        submit.setAlignmentX(CENTER_ALIGNMENT);
        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String title = textbookNameText.getText().trim();
                String author = authorText.getText().trim();
                String pages = pagesText.getText().trim();

                if (textbookDetails.size() == 3) {
                    textbookDetails.clear();
                }
                if (!textbookNameText.getText().equals("")) { textbookDetails.add(title); }
                if (!authorText.getText().equals("")) { textbookDetails.add(author) ; }
                if (!pagesText.getText().equals("")) { textbookDetails.add(pages); }

                if (!title.equals("") && !author.equals("") && !pages.equals("")) {
                    errorMessage.setText("Textbook information acquired. You\nmay now close this dialog.");
                }
                else {
                    errorMessage.setText("Not all fields are filled.");
                }

                dialog.setSize(new Dimension(300, 320));
                errorMessage.setVisible(true);
            }
        });

        textbookInfoPanel.add(title);
        textbookInfoPanel.add(detailText);
        textbookInfoPanel.add(textbookNamePanel);
        textbookInfoPanel.add(authorPanel);
        textbookInfoPanel.add(pagesPanel);
        textbookInfoPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        textbookInfoPanel.add(errorMessage);
        textbookInfoPanel.add(submit);

        dialog.add(textbookInfoPanel);
        dialog.setSize(new Dimension(300, 300));
        dialog.setVisible(true);
    }

    public void removeClassDialog() {
        JFrame owner = new JFrame();

        JDialog removeClassScreen = new JDialog(owner, "Add Class");
        removeClassScreen.setResizable(false);
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

        JTextArea errorMessage = new JTextArea();
        errorMessage.setVisible(false);
        errorMessage.setOpaque(false);
        errorMessage.setLineWrap(true);
        errorMessage.setAlignmentX(CENTER_ALIGNMENT);
        errorMessage.setMaximumSize(new Dimension(250, 60));
        errorMessage.setBorder(new EmptyBorder(0, 0, 10, 0));

        JButton submit = new JButton("Submit");
        submit.setAlignmentX(CENTER_ALIGNMENT);
        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String classType = (String) classTypeBox.getSelectedItem();
                String className = classNameText.getText().trim();

                if (className.equals("")) {
                    errorMessage.setText("Please enter a class name.");
                } else {
                    try {
                        removeItem(classType, className);
                        errorMessage.setText("Class removed successfully. You may\nnow close this dialog.");
                    } catch (EmptyCollectionException e1) {
                        errorMessage.setText("No classes to remove.");
                    } catch (ItemNotFoundException e1) {
                        errorMessage.setText("Class not found.");
                    }
                }
                removeClassScreen.setSize(new Dimension(300, 270));
                errorMessage.setVisible(true);
            }
        });

        removeClassPanel.add(title);
        removeClassPanel.add(detailText);
        removeClassPanel.add(classTypePanel);
        removeClassPanel.add(classNamePanel);
        removeClassPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        removeClassPanel.add(errorMessage);
        removeClassPanel.add(submit);

        removeClassScreen.add(removeClassPanel);
        removeClassScreen.setSize(new Dimension(300, 250));
        removeClassScreen.setVisible(true);
    }

    public void addTextbookDialog() {
        JFrame owner = new JFrame();
        JDialog addTextbookScreen = new JDialog(owner, "Add Textbook");
        addTextbookScreen.setResizable(false);
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

        JTextArea errorMessage = new JTextArea();
        errorMessage.setVisible(false);
        errorMessage.setOpaque(false);
        errorMessage.setLineWrap(true);
        errorMessage.setAlignmentX(CENTER_ALIGNMENT);
        errorMessage.setMaximumSize(new Dimension(250, 60));
        errorMessage.setBorder(new EmptyBorder(0, 0, 10, 0));

        JButton submit = new JButton("Submit");
        submit.setAlignmentX(CENTER_ALIGNMENT);
        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String classType = (String) classTypeBox.getSelectedItem();
                String className = classNameText.getText().trim();
                String title = textbookNameText.getText().trim();
                String author = authorText.getText().trim();
                String pages = pagesText.getText().trim();

                ArrayList<String> item = new ArrayList<>(Arrays.asList(classType, className, title, author, pages));

                if (className.equals("")) {
                    errorMessage.setText("Please enter a class name.");
                }
                else if (title.equals("")) {
                    errorMessage.setText("Please enter a title.");
                }
                else if (author.equals("")) {
                    errorMessage.setText("Please enter an author.");
                }
                else if (pages.equals("")) {
                    errorMessage.setText("Please enter the number of pages.");
                }
                else {
                    try {
                        addTextbook(item);
                        errorMessage.setText("Textbook added successfully. You \nmay now close this dialog.");
                    } catch (NumberFormatException nfex) {
                        errorMessage.setText("Pages entered must be a whole number.");
                    } catch (ItemNotFoundException e1) {
                        errorMessage.setText("Class not found.");
                    }
                }
                errorMessage.setVisible(true);
                addTextbookScreen.setSize(new Dimension(300, 460));
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
        addTextbookPanel.add(errorMessage);
        addTextbookPanel.add(submit);

        addTextbookScreen.add(addTextbookPanel);
        addTextbookScreen.setSize(300, 440);
        addTextbookScreen.setVisible(true);

    }

    public void removeTextbookDialog() {
        JFrame owner = new JFrame();

        JDialog removeTextbookScreen = new JDialog(owner, "Add Class");
        removeTextbookScreen.setResizable(false);
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

        JTextArea errorMessage = new JTextArea();
        errorMessage.setVisible(false);
        errorMessage.setOpaque(false);
        errorMessage.setLineWrap(true);
        errorMessage.setAlignmentX(CENTER_ALIGNMENT);
        errorMessage.setMaximumSize(new Dimension(250, 60));
        errorMessage.setBorder(new EmptyBorder(0, 0, 10, 0));

        JButton submit = new JButton("Submit");
        submit.setAlignmentX(CENTER_ALIGNMENT);
        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String classType = (String) classTypeBox.getSelectedItem();
                String className = classNameText.getText().trim();

                if (className.equals("")) {
                    errorMessage.setText("Please enter a class name.");
                } else {
                    try {
                        removeTextbook(classType, className);
                        errorMessage.setText("Textbook successfully removed. You \nmay now close this dialog.");
                    } catch (EmptyCollectionException e1) {
                        errorMessage.setText("No classes found.");
                    } catch (NoTextbookException e1) {
                        errorMessage.setText("Class does not have a textbook.");
                    } catch (ItemNotFoundException e1) {
                        errorMessage.setText("Class with textbook not found.");
                    }
                }
                errorMessage.setVisible(true);
                removeTextbookScreen.setSize(new Dimension(300, 290));
            }
        });

        removeTextbookPanel.add(title);
        removeTextbookPanel.add(detailText);
        removeTextbookPanel.add(classTypePanel);
        removeTextbookPanel.add(classNamePanel);
        removeTextbookPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        removeTextbookPanel.add(errorMessage);
        removeTextbookPanel.add(submit);

        removeTextbookScreen.add(removeTextbookPanel);
        removeTextbookScreen.setSize(new Dimension(300, 270));
        removeTextbookScreen.setVisible(true);
    }

    @Override
    public int getloadState() {
        return loadState;
    }

    // MODIFIES: this
    // EFFECTS: allows user to create a new UniClass and then saves the created UniClass.
    public void addToListObject(ArrayList<String> item) throws NumberFormatException, BadTimeException, IOException {
        louc.addItem(item);
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


    public void removeItem(String classType, String className) throws EmptyCollectionException, ItemNotFoundException {
        louc.removeItem(classType, className);
    }

    public void removeTextbook(String classType, String className) throws EmptyCollectionException, NoTextbookException, ItemNotFoundException {
        louc.removeTextbook(classType, className);
    }

    public void saveList() {
        louc.saveCollection();
    }

    public void addTextbook(ArrayList<String> item) throws NumberFormatException, ItemNotFoundException {
        louc.addTextbook(item);
    }

    public CollectionOfUniClasses getListOfUniClasses() {
        return louc;
    }

    @Override
    public void update() {
        classesTextArea.setText("");
        classesTextArea.append("** CLASSES **\n\n");
        for (UniClass c : louc.getClassMap().values()) {
            classesTextArea.append(c.toString());
        }

        ArrayList<Textbook> textbooks = getListOfUniClasses().getCollectionOfTextbooks().getTextbooksList();

        textbooksTextArea.setText("");
        textbooksTextArea.append(" ** TEXTBOOKS **\n\n");
        for (Textbook t : textbooks) {
            textbooksTextArea.append(t.toStringFull());
        }
    }
}
