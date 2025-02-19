/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package motorph9_oop;

import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.Timer;

/**
 *
 * @author Shekinah Jabez
 */
public abstract class Dashboard extends JFrame {
    protected User user;

    public Dashboard(User user) {
        this.user = user;
        setLocationRelativeTo(null);
    }

    protected void updateTimeAndDate(JLabel timeLabel, JLabel dateLabel) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm:ss a");
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, MMMM d, yyyy");

        Timer timer = new Timer(1000, e -> {
            timeLabel.setText(timeFormat.format(new Date()));
            dateLabel.setText(dateFormat.format(new Date()));
        });
        timer.start();
    }
}
