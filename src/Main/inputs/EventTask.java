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

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        String returnVal;

        stringBuilder.append("Task Type: " + getType() + "\nDescription: " + getDescription() + "\nStart Date: " + getStartDay() +
                "/" + (getStartMonth() + 1) + "/" + getStartYear() + "\nEnd Date: " +
                getEndDay() + "/" + (getEndMonth() + 1) + "/" + getEndYear());

        switch (importance) {
            case LOW:
                stringBuilder.append("\nImportance: LOW\n");
                break;
            case MEDIUM:
                stringBuilder.append("\nImportance: MEDIUM\n");
                break;
            case HIGH:
                stringBuilder.append("\nImportance: HIGH\n");
                break;
            case EXTREME:
                stringBuilder.append("\nImportance: EXTREME\n");
                break;
        }

        stringBuilder.append("Comments: " + getComments() + "\n\n");

        returnVal = stringBuilder.toString();
        return returnVal;
    }
}
