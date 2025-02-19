/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package motorph9_oop;

import java.time.LocalDateTime;

/**
 *
 * @author Shekinah Jabez
 */
public class EmployeeUser extends User {
    private LocalDateTime clockInTime;
    private LocalDateTime clockOutTime;

    public EmployeeUser(String employeeId, String username, String firstName, String userType) {
        super(employeeId, username, firstName, userType);
    }
    
    @Override
    public void accessDashboard() {
        System.out.println("Accessing Employee Dashboard");
    }
    
    public void recordTimeIn() {
        clockInTime = LocalDateTime.now();
        System.out.println("Clocked in at: " + clockInTime);
    }
    
    public void recordTimeOut() {
        clockOutTime = LocalDateTime.now();
        System.out.println("Clocked out at: " + clockOutTime);
    }
    
    public void requestLeave(String leaveID, String leaveType, LocalDate startDate, LocalDate endDate, String reason) throws IOException {
        LeaveRequest leaveRequest = new LeaveRequest(leaveID, getEmployeeId(), leaveType, startDate, endDate, reason);
        LeaveRequestReader.addLeaveRequest(leaveRequest);
        System.out.println("Leave request submitted.");
    }
    
    public LocalDateTime getTimeIn() {
        return clockInTime;
    }
    
    public LocalDateTime getTimeOut() {
        return clockOutTime;
    }
}


