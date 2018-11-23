package ui;

import inputs.EventTask;
import inputs.GeneralTask;
import inputs.Textbook;
import inputs.UniClass;
import model.collections.CollectionOfTextbooks;

import javax.swing.*;
import java.awt.*;

public class MainScreen extends JPanel {

    private JTextArea classesTextArea;
    private JTextArea tasksTextArea;
    private JTextArea textbooksTextArea;

    // EFFECTS: creates a new MainScreen object.
    public MainScreen()
    {
        BoxLayout flow = new BoxLayout(this, BoxLayout.PAGE_AXIS);
        setLayout(flow);

        JLabel title = new JLabel();
        title.setPreferredSize(new Dimension(300, 60));
        title.setText("Welcome to Your School Planner");
        title.setFont(new Font("Serif", Font.PLAIN, 20));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        classesTextArea = new JTextArea();
        classesTextArea.setMargin(new Insets(10, 10, 10, 10));
        classesTextArea.setAlignmentX(Component.CENTER_ALIGNMENT);
        classesTextArea.setLineWrap(true);
        classesTextArea.setEditable(false);
        JScrollPane classesScroll = new JScrollPane(classesTextArea);
        classesScroll.setPreferredSize(new Dimension(400, 200));
        classesScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        tasksTextArea = new JTextArea();
        tasksTextArea.setMargin(new Insets(10, 10, 10, 10));
        tasksTextArea.setAlignmentX(Component.CENTER_ALIGNMENT);
        tasksTextArea.setLineWrap(true);
        tasksTextArea.setEditable(false);
        JScrollPane tasksScroll = new JScrollPane(tasksTextArea);
        tasksScroll.setPreferredSize(new Dimension(400, 200));
        tasksScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        textbooksTextArea = new JTextArea();
        textbooksTextArea.setMargin(new Insets(10, 10, 10, 10));
        textbooksTextArea.setAlignmentX(Component.CENTER_ALIGNMENT);
        textbooksTextArea.setLineWrap(true);
        textbooksTextArea.setEditable(false);
        JScrollPane textbooksScroll = new JScrollPane(textbooksTextArea);
        textbooksScroll.setPreferredSize(new Dimension(400, 200));
        textbooksScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);


        add(title);
        add(classesScroll);
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(tasksScroll);
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(textbooksScroll);


    }

    // EFFECTS: outputs details about user's classes.
    public void myClasses(TimetableScreen screenTimetable) {
        int isClassesLoaded = screenTimetable.getloadState();
        if (isClassesLoaded == 0) {
            classesTextArea.setText("");
            classesTextArea.append("** CLASSES **\n\n");
            for (UniClass c : screenTimetable.getListOfUniClasses().getClassMap().values()) {
                classesTextArea.append(c.toString());
            }
        } else if (isClassesLoaded == 1) {
            classesTextArea.setText("You have no classes.");
        } else if (isClassesLoaded == 2) {
            classesTextArea.setText("Unable to load classes. Oops!");
        }
    }

    // EFFECTS: outputs details about user's tasks.
    public void myTasks(TasksScreen screenTasks) {
        int isTasksLoaded = screenTasks.getloadState();
        if (isTasksLoaded == 0) {
            tasksTextArea.setText("");
            tasksTextArea.append("** EVENTS **\n\n");
            for (EventTask e : screenTasks.getLoet().getTaskList()) {
                tasksTextArea.append(e.toString());
            }

            tasksTextArea.append("** TASKS **\n\n");
            for (GeneralTask g : screenTasks.getLogt().getTaskList()) {
                tasksTextArea.append(g.toString());
            }

        } else if (isTasksLoaded == 1) {
            tasksTextArea.setText("You have no tasks.");
        } else if (isTasksLoaded == 2) {
            tasksTextArea.setText("Unable to load tasks. Oops!");
        }
    }

    // EFFECTS: outputs details about user's tasks.
    public void myTextbooks(TimetableScreen screenTimetable) {
        CollectionOfTextbooks textbooks = screenTimetable.getListOfUniClasses().getCollectionOfTextbooks();
        textbooksTextArea.setText("");
        textbooksTextArea.append(" ** TEXTBOOKS **\n\n");
        for (Textbook t : textbooks.getTextbooksList()) {
            textbooksTextArea.append(t.toStringFull());
        }
    }
}



