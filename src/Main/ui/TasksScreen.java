package ui;

import exceptions.EmptyCollectionException;
import exceptions.ItemNotFoundException;
import inputs.EventTask;
import inputs.GeneralTask;
import model.collections.CollectionOfGeneralTasks;
import model.collections.CollectionOfEventTasks;
import model.observerPattern.Observer;
import org.jdatepicker.impl.DateComponentFormatter;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.*;

public class TasksScreen extends JPanel implements InputScreen, Observer {

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
        loet.addObserver(this);
        logt.addObserver(this);
        loadItemsIntoListObject();

        BoxLayout flowMain = new BoxLayout(this, BoxLayout.PAGE_AXIS);
        setLayout(flowMain);

        buttonPanel = new JPanel();
        buttonPanel.setSize(620, 100);
        BoxLayout flowBody = new BoxLayout(buttonPanel, BoxLayout.LINE_AXIS);
        buttonPanel.setLayout(flowBody);

        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.LINE_AXIS));
        titlePanel.setBackground(Color.BLACK);
        titlePanel.setOpaque(true);
        JLabel title = new JLabel();
        title.setPreferredSize(new Dimension(620, 50));
        title.setBorder(new EmptyBorder(0, 230, 0, 240));
        title.setText("Tasks and Events");
        title.setFont(new Font("Serif", Font.PLAIN, 20));
        title.setForeground(Color.WHITE);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setAlignmentY(Component.CENTER_ALIGNMENT);
        titlePanel.setAlignmentX(CENTER_ALIGNMENT);
        titlePanel.add(title);

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

        add(titlePanel);
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(tasksScroll);
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(buttonPanel);
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(eventsScroll);
    }

    public void addTaskDialog() {
        JFrame owner = new JFrame();

        JDialog addTaskScreen = new JDialog(owner, "Add Task");
        addTaskScreen.setResizable(false);
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

        JTextArea errorMessage = new JTextArea();
        errorMessage.setVisible(false);
        errorMessage.setOpaque(false);
        errorMessage.setLineWrap(true);
        errorMessage.setAlignmentX(CENTER_ALIGNMENT);
        errorMessage.setMaximumSize(new Dimension(250, 50));
        errorMessage.setBorder(new EmptyBorder(0, 0, 10, 0));

        JButton submit = new JButton("Submit");
        submit.setAlignmentX(CENTER_ALIGNMENT);
        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String taskType = (String) taskTypeBox.getSelectedItem();
                String description;
                Date startDate;
                Date endDate;
                String startDay;
                String startMonth;
                String startYear;
                String endDay;
                String endMonth;
                String endYear;
                String importance;
                String comments;
                ArrayList<String> item;

                if (taskType == "TASK") {
                    description = descriptionText.getText().trim();
                    startDate = (Date) datePickerStart.getModel().getValue();
                    startDay = Integer.toString(datePickerStart.getModel().getDay());
                    startMonth = Integer.toString(datePickerStart.getModel().getMonth());
                    startYear = Integer.toString(datePickerStart.getModel().getYear());
                    startYear = startYear.substring(2);
                    importance = (String) importanceBox.getSelectedItem();
                    item = new ArrayList<>(Arrays.asList(taskType, description, startDay, startMonth, startYear, importance));
                    if (description.equals("")) {
                        errorMessage.setText("Please enter a description.");
                    } else if (startDate == null) {
                        errorMessage.setText("Please enter a date (start date).");
                    } else
                    {
                        try {
                            addToListObject(item);
                            errorMessage.setText("Task added successfully. You may now close this dialog.");
                        } catch (IOException ioex) {
                            errorMessage.setText("Error saving to file.");
                        }
                    }
                }
                else {
                    description = descriptionText.getText().trim();

                    startDate = (Date) datePickerStart.getModel().getValue();
                    endDate = (Date) datePickerEnd.getModel().getValue();

                    startDay = Integer.toString(datePickerStart.getModel().getDay());
                    startMonth = Integer.toString(datePickerStart.getModel().getMonth());
                    startYear = Integer.toString(datePickerStart.getModel().getYear());
                    startYear = startYear.substring(2);

                    endDay = Integer.toString(datePickerEnd.getModel().getDay());
                    endMonth = Integer.toString(datePickerEnd.getModel().getMonth());
                    endYear = Integer.toString(datePickerEnd.getModel().getYear());
                    endYear = endYear.substring(2);

                    importance = (String) importanceBox.getSelectedItem();
                    comments = commentsText.getText();
                    item = new ArrayList<>(Arrays.asList(taskType, description, startDay, startMonth, startYear, endDay, endMonth,
                            endYear, importance, comments));
                    if (description.equals("")) {
                        errorMessage.setText("Please enter a description.");
                    } else if (startDate == null) {
                        errorMessage.setText("Please enter a start date.");
                    } else if (endDate == null) {
                        errorMessage.setText("Please enter an end date.");
                    } else if (endDate.compareTo(startDate) < 0) {
                        errorMessage.setText("The end date must not be before the\nstart date.");
                    } else {
                        try {
                            addToListObject(item);
                            errorMessage.setText("Event added successfully. You may now close this dialog.");
                        } catch (IOException ioex) {
                            errorMessage.setText("Error saving to file.");
                        }
                    }
                }
                errorMessage.setVisible(true);
                addTaskScreen.setSize(new Dimension(300, 780));
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
        addTaskPanel.add(errorMessage);
        addTaskPanel.add(submit);

        addTaskScreen.add(addTaskPanel);
        addTaskScreen.setSize(new Dimension(300, 750));
        addTaskScreen.setVisible(true);
    }

    public void removeTaskDialog() {
        JFrame owner = new JFrame();

        JDialog removeTaskScreen = new JDialog(owner, "Remove Task");
        removeTaskScreen.setResizable(false);
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

        JTextArea errorMessage = new JTextArea();
        errorMessage.setVisible(false);
        errorMessage.setOpaque(false);
        errorMessage.setLineWrap(true);
        errorMessage.setAlignmentX(CENTER_ALIGNMENT);
        errorMessage.setMaximumSize(new Dimension(250, 50));
        errorMessage.setBorder(new EmptyBorder(0, 0, 10, 0));

        JButton submit = new JButton("Submit");
        submit.setAlignmentX(CENTER_ALIGNMENT);
        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String taskType = (String) taskTypeBox.getSelectedItem();
                String description;
                Date startDate;
                Date endDate;
                String startDay;
                String startMonth;
                String startYear;
                String endDay;
                String endMonth;
                String endYear;
                ArrayList<String> item;

                if (taskType == "TASK") {
                    description = descriptionText.getText().trim();
                    startDay = Integer.toString(datePickerStart.getModel().getDay());
                    startMonth = Integer.toString(datePickerStart.getModel().getMonth());
                    startYear = Integer.toString(datePickerStart.getModel().getYear());
                    startYear = startYear.substring(2);
                    item = new ArrayList<>(Arrays.asList(taskType, description, startDay, startMonth, startYear));
                    if (description.equals("")) {
                        errorMessage.setText("Please enter a description.");
                    }
                    else
                    {
                        try {
                            removeItem(item);
                            errorMessage.setText("Task removed successfully. You may\nnow close this dialog.");
                        } catch (ItemNotFoundException e1) {
                            errorMessage.setText("Task not found.");
                        } catch (EmptyCollectionException e1) {
                            errorMessage.setText("No tasks to remove.");
                        }

                    }
                }
                else {
                    description = descriptionText.getText().trim();
                    startDate = (Date) datePickerStart.getModel().getValue();
                    startDay = Integer.toString(datePickerStart.getModel().getDay());
                    startMonth = Integer.toString(datePickerStart.getModel().getMonth());
                    startYear = Integer.toString(datePickerStart.getModel().getYear());
                    startYear = startYear.substring(2);
                    endDate = (Date) datePickerStart.getModel().getValue();
                    endDay = Integer.toString(datePickerEnd.getModel().getDay());
                    endMonth = Integer.toString(datePickerEnd.getModel().getMonth());
                    endYear = Integer.toString(datePickerEnd.getModel().getYear());
                    endYear = endYear.substring(2);
                    item = new ArrayList<>(Arrays.asList(taskType, description, startDay, startMonth, startYear, endDay, endMonth,
                            endYear));
                    if (description.equals("")) {
                        errorMessage.setText("Please enter a description.");
                    } else if (endDate.compareTo(startDate) < 0) {
                        errorMessage.setText("The end date must not be before the\nstart date.");
                    }
                    else {
                        try {
                            removeItem(item);
                            errorMessage.setText("Event removed successfully. You may\nnow close this dialog.");
                        } catch (ItemNotFoundException e1) {
                            errorMessage.setText("Event not found.");
                        } catch (EmptyCollectionException e1) {
                            errorMessage.setText("No events to remove.");
                        }
                    }
                }
                removeTaskScreen.setSize(new Dimension(300, 520));
                errorMessage.setVisible(true);
            }
        });

        removeTaskPanel.add(title);
        removeTaskPanel.add(detailText);
        removeTaskPanel.add(taskTypePanel);
        removeTaskPanel.add(descriptionPanel);
        removeTaskPanel.add(startDatePanel);
        removeTaskPanel.add(endDatePanel);
        removeTaskPanel.add(errorMessage);
        removeTaskPanel.add(submit);

        removeTaskScreen.add(removeTaskPanel);
        removeTaskScreen.setSize(new Dimension(300, 480));
        removeTaskScreen.setVisible(true);
    }

    public void addToListObject(ArrayList<String> item) throws IOException {
        String taskType = item.get(0);
        if (taskType.equals("TASK")) {
            if (item.size() == 6) {
                logt.addItem(item);
            }
        } else {
            if (item.size() == 10) {
                loet.addItem(item);
            }
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
    public void removeItem(ArrayList<String> item) throws ItemNotFoundException, EmptyCollectionException {
        String taskType = item.get(0);
        if (taskType.equals("TASK")) {
            if (item.size() == 5) {
                logt.removeItem(item);
            }
        } else if (taskType.equals("EVENT")) {
            if (item.size() == 8) {
                loet.removeItem(item);
            }
        }

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

    @Override
    public void update() {
        tasksTextArea.setText("");
        tasksTextArea.append("** TASKS **\n\n");
        for (GeneralTask g : logt.getTaskList()) {
            tasksTextArea.append(g.toString());
        }

        eventsTextArea.setText("");
        eventsTextArea.append(" ** EVENTS **\n\n");
        for (EventTask e : loet.getTaskList()) {
            eventsTextArea.append(e.toString());
        }
    }

}
