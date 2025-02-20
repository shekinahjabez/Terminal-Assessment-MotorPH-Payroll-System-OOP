/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package motorph9_oop;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

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
            leaveRequest.getDateResponded() != null ? leaveRequest.getDateResponded().toString() : ""
        });
        CSVReader.writeCSV(FILE_PATH, data);
    }

    public static LeaveRequest getLeaveById(String leaveID) throws IOException {
        for (String[] data : CSVReader.readCSV(FILE_PATH)) {
            if (data[0].equals(leaveID)) {
                return new LeaveRequest(
                    data[0], data[1], data[2], LocalDate.parse(data[3]), LocalDate.parse(data[4]), data[6]);
            }
        }
        return null;
    }

    public static void updateLeaveRequest(LeaveRequest updatedLeave) throws IOException {
        List<String[]> data = CSVReader.readCSV(FILE_PATH);
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
                    updatedLeave.getDateResponded() != null ? updatedLeave.getDateResponded().toString() : ""
                });
                break;
            }
        }
        CSVReader.writeCSV(FILE_PATH, data);
    }
}

