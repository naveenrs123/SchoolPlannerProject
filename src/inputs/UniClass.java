package inputs;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Collections;

public class UniClass {

    String name;
    String prof;
    String location;
    int startTime; // stored as 24 hour time.
    int endTime;
    int[] days;
    ArrayList<String> daysOfWeek = new ArrayList<>();

    public UniClass(String cName, String cProf, String cLocation, int cStartTime, int cEndTime, int[] cDays) {
        name = cName;
        prof = cProf;
        location = cLocation;
        startTime = cStartTime;
        endTime = cEndTime;
        days = new int[5];
        System.arraycopy(cDays, 0, days, 0, cDays.length);

        Collections.addAll(daysOfWeek, "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday");

    }

    public void printUniClass() {
        System.out.println(name + " at " + location + " taught by " + prof + ".");
        System.out.print(startTime + " to " + endTime + " on ");

        int i;
        for (i = 0; i < days.length - 1; i++)
        {
            if (days[i+1] == 0)
            {
                break;
            }
            else
            {
                System.out.print(daysOfWeek.get(days[i] - 1 ) + ", ");
            }

        }

        System.out.println("and " +  daysOfWeek.get(days[i] - 1) + ".");

    }

}
