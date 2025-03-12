/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package motorph9;
import java.time.LocalDate;

public class LeaveRequest {
    private String leaveID;
    private String employeeID;
    private String leaveType;
    private LocalDate dateRequest;
    private LocalDate startDate;
    private LocalDate endDate;
    private String status; // Pending, Approved, Rejected
    private String reason;
    private String approver;
    private LocalDate dateResponded;
    private String remark;

    public LeaveRequest(String leaveID, String employeeID, String leaveType, LocalDate dateRequest,LocalDate startDate, LocalDate endDate, String reason, String status,String approver, LocalDate dateResponded, String remark) {
        this.leaveID = leaveID;
        this.employeeID = employeeID;
        this.leaveType = leaveType;
        this.dateRequest = dateRequest;
        this.startDate = startDate;
        this.endDate = endDate; 
        this.reason = reason;
        this.status = status;
        this.approver = approver;
        this.dateResponded = dateResponded; 
        this.remark = remark;
    }

    public String getLeaveID() {
        return leaveID;
    }

    public String getEmployeeID() {
        return employeeID;
    }

    public String getLeaveType() {
        return leaveType;
    }

    public LocalDate getDateRequest() {
        return dateRequest;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public String getStatus() {
        return status;
    }

    public String getReason() {
        return reason;
    }

    public String getApprover() {
        return approver;
    }

    public LocalDate getDateResponded() {
        return dateResponded;
    }
    
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
    
    public String getLeaveDetails() {
        return "Leave ID: " + leaveID + "\nEmployee ID: " + employeeID + "\nType: " + leaveType + "\nStart: " + startDate + "\nEnd: " + endDate + "\nReason: " + reason + "\nStatus: " + status;
    }

    public void approve(String approver) {
        this.status = "Approved";
        this.approver = approver;
        this.dateResponded = LocalDate.now();
    }

    public void reject(String approver) {
        this.status = "Rejected";
        this.approver = approver;
        this.dateResponded = LocalDate.now();
    }

    public boolean isApproved() {
        return "Approved".equalsIgnoreCase(status);
    }
  
    public String toCSV() {
        return String.join(",",
            leaveID, employeeID, leaveType, dateRequest.toString(), startDate.toString(), endDate.toString(),
            reason, status, (approver != null ? approver : ""), (dateResponded != null ? dateResponded.toString() : "")
        );
    }
    
    @Override
    public String toString() {
        return getLeaveDetails();
    }
}

