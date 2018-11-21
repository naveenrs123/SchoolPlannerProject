package ui;

import inputs.EventTask;
import inputs.GeneralTask;
import javafx.scene.control.DatePicker;
import model.InputScreen;
import model.collections.CollectionOfGeneralTasks;
import model.collections.CollectionOfEventTasks;
import org.jdatepicker.impl.DateComponentFormatter;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.DateFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.*;

public class TasksScreen extends JPanel implements InputScreen {

    private CollectionOfGeneralTasks logt = new CollectionOfGeneralTasks();
    private CollectionOfEventTasks loet = new CollectionOfEventTasks();
    private int loadState = 0;
    private JPanel buttonPanel;
    private JTextArea tasksTextArea;
    private JTextArea eventsTextArea;
    private JButton addTask;
    private JButton removeTask;

    // Creates a new TasksScreen object.
    public TasksScreen() {
        loadItemsIntoListObject();

        BoxLayout flowMain = new BoxLayout(this, BoxLayout.PAGE_AXIS);
        setLayout(flowMain);

        buttonPanel = new JPanel();
        buttonPanel.setSize(620, 100);
        BoxLayout flowBody = new BoxLayout(buttonPanel, BoxLayout.LINE_AXIS);
        buttonPanel.setLayout(flowBody);

        JLabel title = new JLabel();
        title.setPreferredSize(new Dimension(300, 40));
        title.setText("Tasks and Events");
        title.setFont(new Font("Serif", Font.PLAIN, 20));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        tasksTextArea = new JTextArea();
        tasksTextArea.setMargin(new Insets(10, 10, 10, 10));
        tasksTextArea.setAlignmentX(Component.CENTER_ALIGNMENT);
        tasksTextArea.setLineWrap(true);
        tasksTextArea.setEditable(false);
        JScrollPane tasksScroll = new JScrollPane(tasksTextArea);
        tasksScroll.setPreferredSize(new Dimension(310, 100));
        tasksScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        eventsTextArea = new JTextArea();
        eventsTextArea.setMargin(new Insets(10, 10, 10, 10));
        eventsTextArea.setAlignmentX(Component.CENTER_ALIGNMENT);
        eventsTextArea.setLineWrap(true);
        eventsTextArea.setEditable(false);
        JScrollPane eventsScroll = new JScrollPane(eventsTextArea);
        eventsScroll.setPreferredSize(new Dimension(310, 100));
        eventsScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        tasksTextArea.append("** TASKS **\n\n");
        for (GeneralTask g : logt.getTaskList()) {
            tasksTextArea.append(g.toString());
        }

        eventsTextArea.append(" ** EVENTS **\n\n");
        for (EventTask e : loet.getTaskList()) {
            eventsTextArea.append(e.toString());
        }

        addTask = new JButton("Add Task");
        addTask.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addTaskDialog();
            }
        });
        addTask.setAlignmentX(Component.CENTER_ALIGNMENT);

        removeTask = new JButton("Remove Task");
        removeTask.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeTaskDialog();
            }
        });
        removeTask.setAlignmentX(Component.CENTER_ALIGNMENT);

        buttonPanel.add(addTask);
        buttonPanel.add(Box.createRigidArea(new Dimension(20, 0)));
        buttonPanel.add(removeTask);

        add(title);
        add(tasksScroll);
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(buttonPanel);
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(eventsScroll);
    }

    public void addTaskDialog() {
        JFrame owner = new JFrame();

        JDialog addTaskScreen = new JDialog(owner, "Add Task");
        JPanel addTaskPanel = new JPanel();
        BoxLayout layout = new BoxLayout(addTaskPanel, BoxLayout.PAGE_AXIS);
        addTaskPanel.setLayout(layout);

        JLabel title = new JLabel("Add a Task");
        title.setFont(new Font("Serif", Font.PLAIN, 20));
        title.setAlignmentX(CENTER_ALIGNMENT);
        title.setBorder(new EmptyBorder(20, 10, 10, 10));

        JTextArea detailText = new JTextArea();
        detailText.setMaximumSize(new Dimension(250, 140));
        detailText.setLineWrap(true);
        detailText.setEditable(false);
        detailText.setOpaque(false);
        detailText.setFont(new Font("Serif", Font.PLAIN, 16));
        detailText.append("All tasks must have a task type, a \ndescription, a date, and a rank of \nimportance.\n");
        detailText.append("Events also need a start date, an end \ndate, and optional comments (default \nis NONE).\n");
        detailText.append("For normal tasks, do not change the \nend date and optional comments.");

        JPanel taskTypePanel = new JPanel();
        taskTypePanel.setMaximumSize(new Dimension(190, 50));
        taskTypePanel.setAlignmentY(CENTER_ALIGNMENT);
        taskTypePanel.setLayout(new FlowLayout());
        JLabel taskTypeLabel = new JLabel("Task Type:");
        String types[] = {"TASK", "EVENT"};
        JComboBox taskTypeBox = new JComboBox(types);
        taskTypePanel.add(taskTypeLabel);
        taskTypePanel.add(taskTypeBox);

        JPanel descriptionPanel = new JPanel();
        descriptionPanel.setMaximumSize(new Dimension(300, 130));
        descriptionPanel.setLayout(new FlowLayout());
        descriptionPanel.setAlignmentY(CENTER_ALIGNMENT);
        JLabel descriptionLabel = new JLabel("Description:");
        descriptionLabel.setPreferredSize(new Dimension(80, 100));
        JTextArea descriptionText = new JTextArea();
        descriptionText.setPreferredSize(new Dimension(150, 100));
        descriptionText.setLineWrap(true);
        descriptionText.setEditable(true);
        JScrollPane descriptionScroll = new JScrollPane(descriptionText);
        descriptionScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        descriptionPanel.add(descriptionLabel);
        descriptionPanel.add(Box.createRigidArea(new Dimension(5, 0)));
        descriptionPanel.add(descriptionScroll);

        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");

        JPanel startDatePanel = new JPanel();
        startDatePanel.setLayout(new FlowLayout());
        startDatePanel.setMaximumSize(new Dimension(300, 50));
        startDatePanel.setAlignmentY(CENTER_ALIGNMENT);
        JLabel startDateLabel = new JLabel("Start Date:");
        UtilDateModel modelStart = new UtilDateModel();
        JDatePanelImpl datePanelStart = new JDatePanelImpl(modelStart, p);
        JDatePickerImpl datePickerStart = new JDatePickerImpl(datePanelStart, new DateComponentFormatter());
        startDatePanel.add(startDateLabel);
        startDateLabel.add(Box.createRigidArea(new Dimension(5, 0)));
        startDatePanel.add(datePickerStart);

        // USING JDatePicker freely available on: https://jdatepicker.org/

        JPanel endDatePanel = new JPanel();
        endDatePanel.setMaximumSize(new Dimension(300, 50));
        endDatePanel.setLayout(new FlowLayout());
        endDatePanel.setAlignmentY(CENTER_ALIGNMENT);
        JLabel endDateLabel = new JLabel("End Date:");
        UtilDateModel modelEnd = new UtilDateModel();
        JDatePanelImpl datePanelEnd = new JDatePanelImpl(modelEnd, p);
        JDatePickerImpl datePickerEnd = new JDatePickerImpl(datePanelEnd, new DateComponentFormatter());
        endDatePanel.add(endDateLabel);
        endDatePanel.add(Box.createRigidArea(new Dimension(1, 0)));
        endDatePanel.add(datePickerEnd);

        JPanel importancePanel = new JPanel();
        importancePanel.setMaximumSize(new Dimension(230, 50));
        importancePanel.setAlignmentY(CENTER_ALIGNMENT);
        importancePanel.setLayout(new FlowLayout());
        JLabel importanceLabel = new JLabel("Importance:");
        String importances[] = {"LOW", "MEDIUM", "HIGH", "EXTREME"};
        JComboBox importanceBox = new JComboBox(importances);
        importancePanel.add(importanceLabel);
        importancePanel.add(importanceBox);

        JPanel commentsPanel = new JPanel();
        commentsPanel.setMaximumSize(new Dimension(300, 130));
        commentsPanel.setLayout(new FlowLayout());
        commentsPanel.setAlignmentY(CENTER_ALIGNMENT);
        JLabel commentsLabel = new JLabel("Comments:");
        commentsLabel.setPreferredSize(new Dimension(80, 100));
        JTextArea commentsText = new JTextArea();
        commentsText.setPreferredSize(new Dimension(150, 100));
        commentsText.setLineWrap(true);
        commentsText.setEditable(true);
        commentsText.append("NONE");
        JScrollPane commentsScroll = new JScrollPane(commentsText);
        commentsScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        commentsPanel.add(commentsLabel);
        commentsPanel.add(Box.createRigidArea(new Dimension(5, 0)));
        commentsPanel.add(commentsScroll);

        JButton submit = new JButton("Submit");
        submit.setAlignmentX(CENTER_ALIGNMENT);
        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                owner.dispose();
            }
        });

        addTaskPanel.add(title);
        addTaskPanel.add(detailText);
        addTaskPanel.add(taskTypePanel);
        addTaskPanel.add(descriptionPanel);
        addTaskPanel.add(startDatePanel);
        addTaskPanel.add(endDatePanel);
        addTaskPanel.add(importancePanel);
        addTaskPanel.add(commentsPanel);
        addTaskPanel.add(submit);

        addTaskScreen.add(addTaskPanel);
        addTaskScreen.setSize(new Dimension(300, 720));
        addTaskScreen.setVisible(true);
    }

    public void removeTaskDialog() {
        JFrame owner = new JFrame();

        JDialog removeTaskScreen = new JDialog(owner, "Remove Task");
        JPanel removeTaskPanel = new JPanel();
        BoxLayout layout = new BoxLayout(removeTaskPanel, BoxLayout.PAGE_AXIS);
        removeTaskPanel.setLayout(layout);

        JLabel title = new JLabel("Remove a Task");
        title.setFont(new Font("Serif", Font.PLAIN, 20));
        title.setAlignmentX(CENTER_ALIGNMENT);
        title.setBorder(new EmptyBorder(20, 10, 10, 10));

        JTextArea detailText = new JTextArea();
        detailText.setMaximumSize(new Dimension(250, 80));
        detailText.setLineWrap(true);
        detailText.setEditable(false);
        detailText.setOpaque(false);
        detailText.setFont(new Font("Serif", Font.PLAIN, 16));
        detailText.append("To remove a Task, provide the task \ntype, description, and date (start date).\n\n");
        detailText.append("To remove an Event, also provide the \nend date.");

        JPanel taskTypePanel = new JPanel();
        taskTypePanel.setMaximumSize(new Dimension(190, 50));
        taskTypePanel.setAlignmentY(CENTER_ALIGNMENT);
        taskTypePanel.setLayout(new FlowLayout());
        JLabel taskTypeLabel = new JLabel("Task Type:");
        String types[] = {"TASK", "EVENT"};
        JComboBox taskTypeBox = new JComboBox(types);
        taskTypePanel.add(taskTypeLabel);
        taskTypePanel.add(taskTypeBox);

        JPanel descriptionPanel = new JPanel();
        descriptionPanel.setMaximumSize(new Dimension(300, 130));
        descriptionPanel.setLayout(new FlowLayout());
        descriptionPanel.setAlignmentY(CENTER_ALIGNMENT);
        JLabel descriptionLabel = new JLabel("Description:");
        descriptionLabel.setPreferredSize(new Dimension(80, 100));
        JTextArea descriptionText = new JTextArea();
        descriptionText.setPreferredSize(new Dimension(150, 100));
        descriptionText.setLineWrap(true);
        descriptionText.setEditable(true);
        JScrollPane descriptionScroll = new JScrollPane(descriptionText);
        descriptionScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        descriptionPanel.add(descriptionLabel);
        descriptionPanel.add(Box.createRigidArea(new Dimension(5, 0)));
        descriptionPanel.add(descriptionScroll);

        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");

        JPanel startDatePanel = new JPanel();
        startDatePanel.setLayout(new FlowLayout());
        startDatePanel.setMaximumSize(new Dimension(300, 50));
        startDatePanel.setAlignmentY(CENTER_ALIGNMENT);
        JLabel startDateLabel = new JLabel("Start Date:");
        UtilDateModel modelStart = new UtilDateModel();
        JDatePanelImpl datePanelStart = new JDatePanelImpl(modelStart, p);
        JDatePickerImpl datePickerStart = new JDatePickerImpl(datePanelStart, new DateComponentFormatter());
        startDatePanel.add(startDateLabel);
        startDateLabel.add(Box.createRigidArea(new Dimension(5, 0)));
        startDatePanel.add(datePickerStart);

        // USING JDatePicker freely available on: https://jdatepicker.org/

        JPanel endDatePanel = new JPanel();
        endDatePanel.setMaximumSize(new Dimension(300, 50));
        endDatePanel.setLayout(new FlowLayout());
        endDatePanel.setAlignmentY(CENTER_ALIGNMENT);
        JLabel endDateLabel = new JLabel("End Date:");
        UtilDateModel modelEnd = new UtilDateModel();
        JDatePanelImpl datePanelEnd = new JDatePanelImpl(modelEnd, p);
        JDatePickerImpl datePickerEnd = new JDatePickerImpl(datePanelEnd, new DateComponentFormatter());
        endDatePanel.add(endDateLabel);
        endDatePanel.add(Box.createRigidArea(new Dimension(1, 0)));
        endDatePanel.add(datePickerEnd);

        JButton submit = new JButton("Submit");
        submit.setAlignmentX(CENTER_ALIGNMENT);
        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                owner.dispose();
            }
        });

        removeTaskPanel.add(title);
        removeTaskPanel.add(detailText);
        removeTaskPanel.add(taskTypePanel);
        removeTaskPanel.add(descriptionPanel);
        removeTaskPanel.add(startDatePanel);
        removeTaskPanel.add(endDatePanel);
        removeTaskPanel.add(submit);

        removeTaskScreen.add(removeTaskPanel);
        removeTaskScreen.setSize(new Dimension(300, 480));
        removeTaskScreen.setVisible(true);
    }

    // EFFECTS: gets input from the user to decide whether they want to add tasks or view tasks.
    public int handleOptions(Scanner user_input) {
        int addOrView;
        while (true) {
            try {
                System.out.println("What do you want to do?");
                System.out.println("1. View Tasks\n2. Add Tasks\n3. Remove Tasks");
                addOrView = user_input.nextInt();
                user_input.nextLine();
                Integer x = verifyInput(addOrView);
                if (x != null) return x;
            } catch (InputMismatchException n) {
                System.out.println("You must enter an integer.");
                user_input.nextLine();
            }
        }
    }

    // EFFECTS: verifies input entered in handleOptions() method.
    private Integer verifyInput(int addOrView) {
        if (addOrView == 1) {
            return 1;
        } else if (addOrView == 2) {
            return 2;
        } else if (addOrView == 3) {
            return 3;
        } else {
            System.out.println("Enter a valid choice.");
        }
        return null;
    }

    // EFFECTS: prints out basic details of all tasks.
    public void addingItemDetails() {
        System.out.println("All tasks must have the following fields:");
        System.out.println("-> A Task Type\n-> A Text Description\n-> A numerical date (day, month, year)\n" +
                "-> A rank of importance (LOW, MEDIUM, HIGH, EXTREME");
    }

    // EFFECTS: gets the user's choice of task to add.
    public String chooseTaskType(Scanner user_input) {
        System.out.println("What task would you like to add?\n1. Task\n2. Event");
        String choice = user_input.nextLine();
        while (!(choice.matches("^[0-9]*$")) || !(choice.equals("1") || choice.equals("2"))) {
            System.out.println("You didn't choose a valid option, try again!");
            choice = user_input.nextLine();
        }
        if (choice.equals("1")) {
            return "TASK";
        } else if (choice.equals("2")) {
            return "EVENT";
        } else {
            return "";
        }
    }

    // EFFECTS: gets the user's choice of task to add.
    public String chooseTaskTypeForRemoval(Scanner user_input) {
        System.out.print("Task Type: ");
        String choice = user_input.nextLine().toUpperCase();
        while (!(choice.equals("TASK") || choice.equals("EVENT"))) {
            System.out.println("You didn't choose a valid Task Type, try again.");
            choice = user_input.nextLine();
        }
        return choice;
    }

    // MODIFIES: this
    // EFFECTS: Allows user to add a Task and then stores the created Task. Task is not added if
    //          user input is invalid.
    public void addToListObject(Scanner user_input) {
        addingItemDetails();
        String taskType = chooseTaskType(user_input);
        if (taskType.equals("TASK")) {
            logt.addItem(user_input);
        } else if (taskType.equals("EVENT")) {
            loet.addItem(user_input);
        } else {
            System.out.println("Something went wrong, your item was not created.");
        }
    }

    // MODIFIES: this
    // EFFECTS: loads all tasks stored in a file into the generalTaskList.
    public void loadItemsIntoListObject() {
        try {
            loadEventTaskIntoListObject();
            loadGeneralTaskIntoListObject();
        } catch (FileNotFoundException fnfex) {
            loadState = 1;
        } catch (IOException ioex) {
            loadState = 2;
        }
        loadState = 0;
    }

    @Override
    public int getloadState() {
        return loadState;
    }

    // EFFECTS: calls methods to load a general task into a list object.
    public void loadGeneralTaskIntoListObject() throws IOException {
        String filename = "listofgeneraltasks.csv";
        String currentTask;
        FileReader fileReader = new FileReader(filename);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        while ((currentTask = bufferedReader.readLine()) != null) {
            logt.loadSingleItem(currentTask);
        }
    }

    // EFFECTS: calls methods to load an event task into a list object.
    public void loadEventTaskIntoListObject() throws IOException {
        String filename = "listofeventtasks.csv";
        String currentTask;
        FileReader fileReader = new FileReader(filename);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        while ((currentTask = bufferedReader.readLine()) != null) {
            loet.loadSingleItem(currentTask);
        }

    }

    // EFFECTS: calls methods to remove a task.
    public void removeItem(Scanner user_input) {
        loet.printItems();
        logt.printItems();
        System.out.println("To remove a task, you must select a task type, its description and its date.");
        String taskType = chooseTaskTypeForRemoval(user_input);
        if (taskType.equals("TASK")) {
            logt.removeItem(user_input);
        }
        if (taskType.equals("EVENT")) {
            loet.removeItem(user_input);
        }

    }

    // EFFECTS: prints out all tasks.
    public void printStoredItems() {
        System.out.println("**TASKS**\n");
        loet.printItems();
        logt.printItems();
    }

    // EFFECTS: saves the tasks to files.
    public void saveList() {
        logt.saveCollection();
        loet.saveCollection();
    }

    public CollectionOfEventTasks getLoet() {
        return loet;
    }

    public CollectionOfGeneralTasks getLogt() {
        return logt;
    }
}
