/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package motorph9;

import data_reader9.LeaveRequestReader;
import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import motorph_GUI.EmployeeDashboard;

/**
 *
 * @author Shekinah Jabez
 */
public class LeaveProcessor {
    private EmployeeLeaveTracker leaveTracker;
    
    public LeaveProcessor(EmployeeLeaveTracker leaveTracker) {
        this.leaveTracker = leaveTracker;
    }
    
    /*public void processLeaveRequest(String employeeId, String leaveType, LocalDate startLocalDate, LocalDate endLocalDate, String reason) throws IOException {
        if (startLocalDate.isBefore(EmployeeDashboard.TODAY) || endLocalDate.isBefore(EmployeeDashboard.TODAY)) {
            throw new IllegalArgumentException("Leave cannot be in the past!");
        }
        
        if (startLocalDate.isAfter(endLocalDate)) {
            throw new IllegalArgumentException("Start date cannot be later than end date!");
        }
        
        if (startLocalDate.getDayOfWeek() == DayOfWeek.SATURDAY || startLocalDate.getDayOfWeek() == DayOfWeek.SUNDAY ||
            endLocalDate.getDayOfWeek() == DayOfWeek.SATURDAY || endLocalDate.getDayOfWeek() == DayOfWeek.SUNDAY) {
            throw new IllegalArgumentException("Leave cannot start or end on a weekend!");
        }
        
        int leaveDuration = calculateWeekdays(startLocalDate, endLocalDate);
        
        if (!leaveTracker.hasSufficientLeave(leaveType, leaveDuration)) {
            throw new IllegalArgumentException("Insufficient leave balance.");
        }
        
        String leaveId = generateLeaveId();
        
        LeaveRequest leaveRequest = new LeaveRequest(
            leaveId,
            employeeId,
            leaveType,
            EmployeeDashboard.TODAY,
            startLocalDate,
            endLocalDate,
            reason,
            "Pending",
            "",
            null,
            "" // 'Remark' parameter
        );
        
        LeaveRequestReader.addLeaveRequest(leaveRequest);
        leaveTracker.deductLeave(leaveType, leaveDuration);
        leaveTracker.saveLeaveBalances();
    }*/
    
    public void processLeaveRequest(String employeeId, String leaveType, LocalDate startLocalDate, LocalDate endLocalDate, String reason) throws IOException {
        if (startLocalDate.isBefore(LocalDate.now()) || endLocalDate.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Leave cannot be in the past!");
        }

        if (startLocalDate.isAfter(endLocalDate)) {
            throw new IllegalArgumentException("Start date cannot be later than end date!");
        }

        if (startLocalDate.getDayOfWeek() == DayOfWeek.SATURDAY || startLocalDate.getDayOfWeek() == DayOfWeek.SUNDAY ||
            endLocalDate.getDayOfWeek() == DayOfWeek.SATURDAY || endLocalDate.getDayOfWeek() == DayOfWeek.SUNDAY) {
            throw new IllegalArgumentException("Leave cannot start or end on a weekend!");
        }

        int leaveDuration = calculateWeekdays(startLocalDate, endLocalDate);

        if (!leaveTracker.hasSufficientLeave(leaveType, leaveDuration)) {
            throw new IllegalArgumentException("Insufficient leave balance.");
        }

        String leaveId = generateLeaveId();

        LeaveRequest leaveRequest = new LeaveRequest(
            leaveId,
            employeeId,
            leaveType,
            LocalDate.now(), // ✅ Dynamically set to the real current date
            startLocalDate,
            endLocalDate,
            reason,
            "Pending",
            "",
            null,
            ""
        );

        LeaveRequestReader.addLeaveRequest(leaveRequest);
        leaveTracker.deductLeave(leaveType, leaveDuration);
        leaveTracker.saveLeaveBalances();
    }

     
    private int calculateWeekdays(LocalDate startDate, LocalDate endDate) {
        int weekdays = 0;
        for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
            DayOfWeek day = date.getDayOfWeek();
            if (day != DayOfWeek.SATURDAY && day != DayOfWeek.SUNDAY) {
                weekdays++;
            }
        }
        return weekdays;
    }
    
    private String generateLeaveId() {
        List<LeaveRequest> requests = new LeaveRequestReader().getAllLeaveRequests();
        int maxId = 0;
        for (LeaveRequest request : requests) {
            String idStr = request.getLeaveID().replace("L", "");
            try {
                int idNum = Integer.parseInt(idStr);
                if (idNum > maxId) maxId = idNum;
            } catch (NumberFormatException ignored) {}
        }
        return "L" + String.format("%03d", maxId + 1);
    }
}

