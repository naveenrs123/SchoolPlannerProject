package inputs;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class GeneralTask extends Task {

    protected GregorianCalendar date;

    // EFFECTS: Creates a new GeneralTask object and sets all its fields.
    public GeneralTask(String type, String desc, int day, int month, int year, String importance) {
       super(type, desc, importance);
        // month is stored from 0 (Jan) to 11 (Dec)
        date = new GregorianCalendar(year, month, day);
    }

    // EFFECTS: gets the day.
    public int getDay() { return date.get(Calendar.DAY_OF_MONTH); }

    // EFFECTS: gets the month.
    public int getMonth() { return date.get(Calendar.MONTH); }

    // EFFECTS: gets the year.
    public int getYear() { return date.get(Calendar.YEAR); }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        String returnVal;

        stringBuilder.append("Task Type: " + taskType + "\nDescription: " + description +
                "\nDate Due: " + getDay() + "/" + (getMonth() + 1) + "/" + getYear());

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

        stringBuilder.append("\n");
        returnVal = stringBuilder.toString();

        return returnVal;
    }
}
