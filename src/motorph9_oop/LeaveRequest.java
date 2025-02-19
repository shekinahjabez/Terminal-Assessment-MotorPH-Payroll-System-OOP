/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package motorph9_oop;

/**
 *
 * @author Shekinah Jabez
 */
import java.time.LocalDate;

public class LeaveRequest {
    private String leaveID;
    private String employeeID;
    private String leaveType;
    private LocalDate startDate;
    private LocalDate endDate;
    private String status; // Pending, Approved, Rejected
    private String reason;

    public LeaveRequest(String leaveID, String employeeID, String leaveType, LocalDate startDate, LocalDate endDate, String reason) {
        this.leaveID = leaveID;
        this.employeeID = employeeID;
        this.leaveType = leaveType;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = "Pending";
        this.reason = reason;
    }
    
    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public String getLeaveDetails() {
        return "Leave ID: " + leaveID + "\nEmployee ID: " + employeeID + "\nType: " + leaveType + "\nStart: " + startDate + "\nEnd: " + endDate + "\nReason: " + reason + "\nStatus: " + status;
    }

    public EmployeeUser request(EmployeeUser employee) {
        return employee;
    }

    public void approve(HRUser hr) {
        this.status = "Approved";
    }

    public boolean isApproved() {
        return status.equals("Approved");
    }

    public String toCSV() {
        return String.join(",", leaveID, employeeID, leaveType, startDate.toString(), endDate.toString(), reason, status);
    }
}

