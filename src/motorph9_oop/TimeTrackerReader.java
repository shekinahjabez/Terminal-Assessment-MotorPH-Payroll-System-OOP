/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package motorph9_oop;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Shekinah Jabez
 */
public class TimeTrackerReader {
    private static final String FILE_PATH = "src/data9/TimeTracker.csv";

    public static void clockIn(String employeeId) throws IOException {
        List<String[]> data = CSVReader.readCSV(FILE_PATH);
        LocalDateTime now = LocalDateTime.now();
        data.add(new String[]{employeeId, now.toString(), ""}); // Empty clock-out field
        CSVReader.writeCSV(FILE_PATH, data);
    }

    public static void clockOut(String employeeId) throws IOException {
        List<String[]> data = CSVReader.readCSV(FILE_PATH);
        LocalDateTime now = LocalDateTime.now();
        boolean updated = false;

        for (String[] row : data) {
            if (row[0].equals(employeeId) && row[2].isEmpty()) { // Find last clock-in without clock-out
                row[2] = now.toString();
                updated = true;
                break;
            }
        }
        
        if (updated) {
            CSVReader.writeCSV(FILE_PATH, data);
        } else {
            throw new IOException("No active clock-in found for employee " + employeeId);
        }
    }

    public static List<String[]> getTimeLogs(String employeeId) throws IOException {
        List<String[]> logs = new ArrayList<>();
        for (String[] row : CSVReader.readCSV(FILE_PATH)) {
            if (row[0].equals(employeeId)) {
                logs.add(row);
            }
        }
        return logs;
    }
}

