package inputs;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class GeneralTask extends Task {

    protected GregorianCalendar date;

    // EFFECTS: Creates a new GeneralTask object and sets all its fields.
    public GeneralTask(String type, String desc, int day, int month, int year, String importance) {
       super(type, desc, importance);
        // month is stored from 0 (Jan) to 11 (Dec), which is why 1 was subtracted from the month.
        date = new GregorianCalendar(year, month - 1, day);
    }

    // EFFECTS: prints the details of a GeneralTask to the console.
    public void printItem() {
        System.out.println("Task Type: " + taskType);
        System.out.println("Description: " + description);
        System.out.println("Date Due: " + date.get(Calendar.DATE) + "/" + (date.get(Calendar.MONTH) + 1) + "/" + date.get(Calendar.YEAR));
        switch (importance) {
            case LOW:
                System.out.println("Importance: LOW");
                break;
            case MEDIUM:
                System.out.println("Importance: MEDIUM");
                break;
            case HIGH:
                System.out.println("Importance: HIGH");
                break;
            case EXTREME:
                System.out.println("Importance: EXTREME");
                break;
        }
    }

    // EFFECTS: gets the day.
    public int getDay() { return date.get(Calendar.DAY_OF_MONTH); }

    // EFFECTS: gets the month.
    public int getMonth() { return date.get(Calendar.MONTH) + 1; }

    // EFFECTS: gets the year.
    public int getYear() { return date.get(Calendar.YEAR); }

    @Override
    public String toString() {
        String returnVal = "";

        returnVal += "Task Type: " + taskType + "\nDescription: " + description +
                "\nDate Due: " + date.get(Calendar.DATE) + "/" + (date.get(Calendar.MONTH) + 1) + "/" + date.get(Calendar.YEAR);

        switch (importance) {
            case LOW:
                returnVal += "\nImportance: LOW\n";
                break;
            case MEDIUM:
                returnVal += "\nImportance: MEDIUM\n";
                break;
            case HIGH:
                returnVal += "\nImportance: HIGH\n";
                break;
            case EXTREME:
                returnVal += "\nImportance: EXTREME\n";
                break;
        }

        returnVal += "\n";

        return returnVal;
    }
}
