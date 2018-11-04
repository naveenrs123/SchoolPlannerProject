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
    public int getStartMonth() { return startDate.get(Calendar.MONTH) + 1; }

    // EFFECTS: gets the year.
    public int getStartYear() { return startDate.get(Calendar.YEAR); }

    // EFFECTS: gets the day.
    public int getEndDay() { return endDate.get(Calendar.DAY_OF_MONTH); }

    // EFFECTS: gets the month.
    public int getEndMonth() { return endDate.get(Calendar.MONTH) + 1; }

    // EFFECTS: gets the year.
    public int getEndYear() { return endDate.get(Calendar.YEAR); }

    public String getComments() { return comments; }

    // MODIFIES: this
    // EFFECTS: creates a new EventTask and sets its fields.
    public EventTask(String type, String desc, int startDay, int startMmonth, int startYear, int endDay, int endMonth, int endYear, String importance, String comm) {
        super(type, desc, importance);

        // month is stored from 0 (Jan) to 11 (Dec), which is why 1 was subtracted from the month.
        startDate = new GregorianCalendar(startYear, startMmonth - 1, startDay);
        endDate = new GregorianCalendar(endYear, endMonth - 1, endDay);
        comments = comm;
    }

    // EFFECTS: prints EventTask
    public void printItem() {
        System.out.println("Task Type: " + taskType);
        System.out.println("Description: " + description);
        System.out.println("Start date: " + startDate.get(Calendar.DATE) + "/" + (startDate.get(Calendar.MONTH) + 1) + "/" + startDate.get(Calendar.YEAR));
        System.out.println("End date: " + endDate.get(Calendar.DATE) + "/" + (endDate.get(Calendar.MONTH) + 1) + "/" + endDate.get(Calendar.YEAR));
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
}
