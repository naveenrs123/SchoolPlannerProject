package ui;

import inputs.Task;

import java.util.ArrayList;
import java.util.Scanner;

public class TasksScreen {

    ArrayList<Task> taskList = new ArrayList<>();

    public TasksScreen() {

    }

    public void welcomeMessage()
    {
        System.out.println("You have not added any tasks.");
        System.out.println("Let's add one now!");
    }

    public void addTask()
    {
        Scanner user_input = new Scanner(System.in);

        System.out.println("All tasks must have the following fields:");
        System.out.println("-> A Text Description\n-> A date (day, month, year)\n" +
                "-> A rank of importance (LOW, MEDIUM, HIGH, EXTREME");

        System.out.print("Enter a description: ");
        String description = user_input.nextLine();

        System.out.print("Enter the day: ");
        String day = user_input.nextLine();

        System.out.print("Enter the month: ");
        String month = user_input.nextLine();

        System.out.print("Enter the year: ");
        String year = user_input.nextLine();

        System.out.print("Enter the importance level: ");
        String importanceLevel = user_input.nextLine().toUpperCase();

        Task newTask = new Task(description, Integer.parseInt(day),
                Integer.parseInt(month), Integer.parseInt(year), importanceLevel);

        taskList.add(newTask);

        System.out.println("A new task has been created.");

    }

    public void getTasks() {

        for (Task task : taskList)
        {
            task.printTask();
        }

    }

}
