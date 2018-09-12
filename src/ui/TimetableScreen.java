package ui;

import javax.swing.*;
import java.awt.*;

public class TimetableScreen extends JPanel {

    public TimetableScreen() {

        System.out.println("Add classes.");
        JLabel timetableLabel = new JLabel("View Your Timetable", JLabel.CENTER);

        setBackground(Color.WHITE);

        timetableLabel.setBackground(Color.GREEN);
        timetableLabel.setSize(100, 100);
        timetableLabel.setOpaque(true);

        add(timetableLabel);

    }

}
