package inputs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

// name given to avoid causing conflict and confusion with built-in 'class' keyword.
public class UniClass implements StoredObject {
    private String name;
    private String classType;
    private String prof;
    private String location;
    private int startTime; // stored as 24 hour time.
    private int endTime;
    private ArrayList<Integer> days;
    private static ArrayList<String> daysOfWeek = new ArrayList<>
            (Arrays.asList("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"));
    private Textbook textbook;

    public UniClass() {

    }

    // REQUIRES: valid ClassType, valid 24 hour time as an Integer, Days list containing integers between 1 and 7
    // EFFECTS: creates a new UniClass object and sets its fields.
    public UniClass(String cClassType, String cName, String cProf, String cLocation, int cStartTime, int cEndTime, ArrayList<Integer> cDays,
                    Textbook courseTextbook) {
        classType = cClassType;
        name = cName;
        prof = cProf;
        location = cLocation;
        startTime = cStartTime;
        endTime = cEndTime;
        days = new ArrayList<>();
        days.addAll(cDays);
        if (courseTextbook == null) {
            textbook = new Textbook();
        } else {
            textbook = new Textbook();
            addTextbook(courseTextbook);
        }
    }

    public void addTextbook(Textbook t) {
        if (!textbook.equals(t)) {
            textbook = t;
            textbook.addUniClass(this);
        }
    }

    public void removeTextbook(Textbook t) {
        if (textbook.equals(t)) {
            textbook = new Textbook();
            textbook.removeUniClass(this);
        }
    }

    // EFFECTS: prints the details of a UniClass to the console.
    public void printItem() {
        System.out.println(classType + ": " + name + " at " + location + " taught by " + prof + ".");
        System.out.print(String.format("%04d", startTime) + " to " + String.format("%04d", endTime) + " on ");

        if (days.size() == 1) {
            System.out.println(daysOfWeek.get(days.get(0) - 1));
        } else {
            int i;
            for (i = 0; i < days.size() - 1; i++) {
                System.out.print(daysOfWeek.get(days.get(i) - 1) + ", ");
            }
            System.out.println("and " + daysOfWeek.get(days.get(i) - 1) + ".");
        }
        if (textbook.getTitle() != null && textbook.getAuthor() != null) {
            textbook.printTextbook();
        } else {
            System.out.println("No textbook.");
        }

    }

    // EFFECTS: gets the name.
    public String getName() { return name; }

    // EFFECTS: gets the classType.
    public String getClassType() { return classType; }

    // EFFECTS: gets the professor.
    public String getProf() { return prof; }

    // EFFECTS: gets the location.
    public String getLocation() { return location; }

    // EFFECTS: gets the start time.
    public int getStartTime() { return startTime; }

    // EFFECTS: gets the end time.
    public int getEndTime() { return endTime; }

    // EFFECTS: gets the list of days
    public ArrayList<Integer> getDays() { return days; }

    public Textbook getTextbook() {
        return textbook;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UniClass uniClass = (UniClass) o;
        return Objects.equals(name, uniClass.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
