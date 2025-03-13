package data_reader9;

import data_reader9.CSVReader;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import motorph9.TimeLog;

/**
 *
 * @author Shekinah Jabez, Paulo Martin
 */
public class TimeTrackerReader {
    private static final String FILE_PATH = "src/data9/TimeTracker.csv";
    private static final DateTimeFormatter CSV_DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("MM/dd/yyyy,HH:mm"); // ✅ Formatter for CSV date-time

    public static void clockIn(String employeeId) throws IOException {
        List<String[]> data = CSVReader.readCSV(FILE_PATH);
        LocalDateTime now = LocalDateTime.now();
        String formattedDateTime = now.format(CSV_DATE_TIME_FORMATTER); // ✅ Format date and time for CSV

        // ✅ Add all 5 columns: employeeNum, date, timeIn, timeOut, hoursWorked (timeOut and hoursWorked are empty initially)
        data.add(new String[]{employeeId, formattedDateTime.split(",")[0], formattedDateTime.split(",")[1], "", ""});
        CSVReader.writeCSV(FILE_PATH, data);
    }

    public static void clockOut(String employeeId) throws IOException {
        List<String[]> data = CSVReader.readCSV(FILE_PATH);
        LocalDateTime now = LocalDateTime.now();
        String formattedDateTime = now.format(CSV_DATE_TIME_FORMATTER); // ✅ Format date and time for CSV
        boolean updated = false;

        for (String[] row : data) {
            System.out.println("clockOut - Row Length: " + row.length + ", Row Data: " + String.join(",", row)); // ✅ Debug: Check row length and data
            if (row.length >= 3 && row[0].equals(employeeId) && row[3].isEmpty()) { // Find last clock-in without clock-out (timeOut column is now index 3)
                row[3] = formattedDateTime.split(",")[1]; // ✅ Update timeOut column (index 3) with formatted time

                // ✅ Calculate and update hoursWorked column (index 4)
                String timeInString = row[2]; // timeIn is at index 2
                String dateString = row[1]; // date is at index 1
                if (row.length >= 4) { // ✅ Check row.length >= 4 before accessing row[3]
                    try {
                        LocalDateTime timeIn = LocalDateTime.parse(dateString + "," + timeInString, CSV_DATE_TIME_FORMATTER);
                        LocalDateTime timeOut = LocalDateTime.parse(dateString + "," + row[3], CSV_DATE_TIME_FORMATTER);

                        Duration duration = Duration.between(timeIn, timeOut);
                        long hours = duration.toHours();
                        long minutes = duration.toMinutes() % 60;
                        if (row.length > 4) { // ✅ Check if row has index 4 before accessing it
                            row[4] = String.format("%d:%02d", hours, minutes);
                        }
                    } catch (java.time.format.DateTimeParseException e) {
                        System.err.println("Error parsing time for duration calculation in CSV: " + String.join(",", row));
                        if (row.length > 4) { // ✅ Check if row has index 4 before accessing it
                            row[4] = "Error";
                        }
                    }
                }

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
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            br.readLine(); // Skip header

            while ((line = br.readLine()) != null) {
                String[] data = line.split(",", -1);
                if (data.length == 5 && data[0].trim().equals(employeeId)) { // Check for 5 columns and employeeId
                    // ✅ Extract data in the correct order for table columns: Employee #, Date, Time In, Time Out, Hours Worked
                    String employeeNum = data[0].trim();
                    String date = data[1].trim();
                    String timeIn = data[2].trim();
                    String timeOut = data[3].trim();
                    String hoursWorked = data[4].trim();

                    logs.add(new String[]{employeeNum, date, timeIn, timeOut, hoursWorked}); // Add data in correct order
                }
            }
        }
        return logs;
    }
    
    public static List<TimeLog> getTimeLogsAsObjects(String employeeId) throws IOException {
        List<TimeLog> timeLogs = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            br.readLine(); // Skip header

            while ((line = br.readLine()) != null) {
                String[] data = line.split(",", -1);
                if (data.length >= 3 && data[0].trim().equals(employeeId.trim())) {
                    LocalDateTime timeIn = parseDateTime(data[1].trim());
                    LocalDateTime timeOut = parseDateTime(data[2].trim());
                    timeLogs.add(new TimeLog(timeIn, timeOut));
                }
            }
        }
        return timeLogs;
    }

    private static LocalDateTime parseDateTime(String dateTimeString) {
        if (dateTimeString == null || dateTimeString.isEmpty()) {
            return null;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.parse(dateTimeString, formatter);
    }

}
