package inputs;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class Task {

    private enum Importance {
        LOW, MEDIUM, HIGH, EXTREME
    }

    private String description;
    private GregorianCalendar date;
    private Importance importance;

    public Task(String desc, int day, int month, int year, String importance) {
        description = desc;
        date = new GregorianCalendar(year, month, day);

        switch (importance.toUpperCase()) {
            case "LOW":
                this.importance = Importance.LOW;
                break;
            case "MEDIUM":
                this.importance = Importance.MEDIUM;
                break;
            case "HIGH":
                this.importance = Importance.HIGH;
                break;
            case "EXTREME":
                this.importance = Importance.EXTREME;
                break;
            default:
                this.importance = Importance.LOW;
        }
    }

    public void printTask()
    {
        System.out.println("Description: " + description);
        System.out.println("Date Due: " + date.get(Calendar.DATE) + "/" + date.get(Calendar.MONTH) +
                "/" + date.get(Calendar.YEAR));

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
}
