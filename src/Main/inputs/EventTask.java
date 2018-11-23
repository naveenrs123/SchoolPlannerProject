package inputs;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class EventTask extends Task {

    protected GregorianCalendar startDate;
    protected GregorianCalendar endDate;
    protected String comments;

    // EFFECTS: gets the day.
    public int getStartDay() { return startDate.get(Calendar.DAY_OF_MONTH); }

    // EFFECTS: gets the month.
    public int getStartMonth() { return startDate.get(Calendar.MONTH); }

    // EFFECTS: gets the year.
    public int getStartYear() { return startDate.get(Calendar.YEAR); }

    // EFFECTS: gets the day.
    public int getEndDay() { return endDate.get(Calendar.DAY_OF_MONTH); }

    // EFFECTS: gets the month.
    public int getEndMonth() { return endDate.get(Calendar.MONTH); }

    // EFFECTS: gets the year.
    public int getEndYear() { return endDate.get(Calendar.YEAR); }

    public String getComments() { return comments; }

    // MODIFIES: this
    // EFFECTS: creates a new EventTask and sets its fields.
    public EventTask(String type, String desc, int startDay, int startMonth, int startYear, int endDay, int endMonth, int endYear, String importance, String comm) {
        super(type, desc, importance);

        // month is stored from 0 (Jan) to 11 (Dec)
        startDate = new GregorianCalendar(startYear, startMonth, startDay);
        endDate = new GregorianCalendar(endYear, endMonth, endDay);
        comments = comm;
    }

    // EFFECTS: prints EventTask
    public void printItem() {
        System.out.println("Task Type: " + getType());
        System.out.println("Description: " + getDescription());
        System.out.println("Start date: " + getStartDay() + "/" + (getStartMonth() + 1) + "/" + getStartYear());
        System.out.println("End date: " + getEndYear() + "/" + (getEndMonth() + 1) + "/" + getEndYear());
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
        System.out.println("Comments: " + comments);
    }

    @Override
    public String toString() {
        String returnVal = "";

        returnVal += "Task Type: " + getType() + "\nDescription: " + getDescription() + "\nStart Date: " + getStartDay() +
                "/" + (getStartMonth() + 1) + "/" + getStartYear() + "\nEnd Date: " +
                getEndDay() + "/" + (getEndMonth() + 1) + "/" + getEndYear();

        switch (importance) {
            case LOW:
                returnVal += "\nImportance: LOW";
                break;
            case MEDIUM:
                returnVal += "\nImportance: MEDIUM";
                break;
            case HIGH:
                returnVal += "\nImportance: HIGH";
                break;
            case EXTREME:
                returnVal += "\nImportance: EXTREME";
                break;
        }

        returnVal += "\nComments: " + getComments() + "\n\n";

        return returnVal;
    }
}
