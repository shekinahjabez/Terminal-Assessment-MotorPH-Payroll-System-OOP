/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package data_reader9;
import java.io.*;
import java.time.LocalDate;
import java.util.*;
import motorph9.LeaveRequest;

public class LeaveRequestReader {
    private static final String FILE_PATH = "src/data9/LeaveRequests.csv";

    public static void addLeaveRequest(LeaveRequest leaveRequest) throws IOException {
        List<String[]> data = CSVReader.readCSV(FILE_PATH);
        data.add(new String[]{
            leaveRequest.getLeaveID(),
            leaveRequest.getEmployeeID(),
            leaveRequest.getLeaveType(),
            leaveRequest.getDateRequest().toString(),
            leaveRequest.getStartDate().toString(),
            leaveRequest.getEndDate().toString(),
            leaveRequest.getReason(),
            leaveRequest.getStatus(),
            leaveRequest.getApprover(),
            leaveRequest.getDateResponded() != null ? leaveRequest.getDateResponded().toString() : "",
            leaveRequest.getRemark()
        });
        CSVReader.writeCSV(FILE_PATH, data);
    }

    public static LeaveRequest getLeaveById(String leaveID) throws IOException {
        for (String[] data : CSVReader.readCSV(FILE_PATH)) {
            if (data[0].equals(leaveID)) {
                return new LeaveRequest(
                    data[0],  // leaveID
                    data[1],  // employeeID
                    data[2],  // leaveType
                    LocalDate.parse(data[3]),  // dateRequest
                    LocalDate.parse(data[4]),  // startDate
                    LocalDate.parse(data[5]),  // endDate
                    data[6],  // reason
                    data[7],  // status (Pending, Approved, Rejected)
                    data.length > 8 ? data[8] : "",  // ✅ Approver (empty if missing)
                    data.length > 9 && !data[9].isEmpty() ? LocalDate.parse(data[9]) : null, // ✅ dateResponded (null if missing)
                    data.length > 10 ? data[10] : ""  // ✅ remark (empty if missing)
                );
            }
        }
        return null; // Return null if leaveID is not found
    }

    public static void updateLeaveRequest(LeaveRequest updatedLeave) throws IOException {
        List<String[]> data = CSVReader.readCSV2(FILE_PATH);
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i)[0].equals(updatedLeave.getLeaveID())) {
                data.set(i, new String[]{
                    updatedLeave.getLeaveID(),
                    updatedLeave.getEmployeeID(),
                    updatedLeave.getLeaveType(),
                    updatedLeave.getDateRequest().toString(),
                    updatedLeave.getStartDate().toString(),
                    updatedLeave.getEndDate().toString(),
                    updatedLeave.getReason(),
                    updatedLeave.getStatus(),
                    updatedLeave.getApprover(),
                    updatedLeave.getDateResponded() != null ? updatedLeave.getDateResponded().toString() : "",
                    updatedLeave.getRemark(),
                });
                break;
            }
        }
        CSVReader.writeCSV(FILE_PATH, data);
    }
 
    public List<LeaveRequest> getAllLeaveRequests() {
        List<LeaveRequest> leaveRequests = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            boolean firstLine = true; // Skip header row

            while ((line = br.readLine()) != null) {
                if (firstLine) {
                    firstLine = false;
                    continue;
                }

                String[] data = line.split(",", -1); // `-1` ensures empty values are included

                if (data.length >= 8) { // Allow empty "approver" and "dateResponded" columns
                    LeaveRequest leave = new LeaveRequest(
                        data[0],  // leaveID
                        data[1],  // employeeID
                        data[2],  // leaveType
                        LocalDate.parse(data[3]),  // dateRequest
                        LocalDate.parse(data[4]),  // startDate
                        LocalDate.parse(data[5]),  // endDate
                        data[6],  // reason
                        data[7],  // status (Pending, Approved, Rejected)
                        data.length > 8 && !data[8].isEmpty() ? data[8] : "",  // ✅ Approver (empty allowed)
                        data.length > 9 && !data[9].isEmpty() ? LocalDate.parse(data[9]) : null, // ✅ dateResponded (null if empty)
                        data.length > 10 ? data[10] : ""  // ✅ remark (empty if missing)
                    );

                    leaveRequests.add(leave);
                } else {
                    System.err.println("Skipping invalid row (Missing Required Data): " + line);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading leave requests: " + e.getMessage());
        }

        return leaveRequests;
    }

}

