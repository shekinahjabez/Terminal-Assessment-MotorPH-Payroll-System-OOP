/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package motorph9_oop;

/**
 *
 * @author Shekinah Jabez
 */
public class HRUser extends User {
    public HRUser(String employeeId, String username, String firstName, String userType) {
        super(employeeId, username, firstName, userType);
    }
    
    @Override
    public void accessDashboard() {
        System.out.println("Accessing HR Dashboard");
    }
    
    public void updateEmployee(User updatedEmployee, EmployeeDetailsReader reader) {
        try {
            if (reader.updateEmployee(updatedEmployee)) {
                System.out.println("Employee updated successfully.");
            } else {
                System.out.println("Employee not found.");
            }
        } catch (Exception e) {
            System.out.println("Error updating employee: " + e.getMessage());
        }
    }
    
    public void addEmployee(User newEmployee, EmployeeDetailsReader reader) {
        try {
            reader.addEmployee(newEmployee);
            System.out.println("Employee added successfully.");
        } catch (Exception e) {
            System.out.println("Error adding employee: " + e.getMessage());
        }
    }    
    
    public void deleteEmployee(String employeeId, EmployeeDetailsReader reader) {
        try {
            if (reader.deleteEmployee(employeeId)) {
                System.out.println("Employee deleted successfully.");
            } else {
                System.out.println("Employee not found.");
            }
        } catch (Exception e) {
            System.out.println("Error deleting employee: " + e.getMessage());
        }
    }
    
    public void viewEmployeeRecords() {
        System.out.println("Viewing employee records");
    }
    
    public void approveLeave(String leaveID) throws IOException {
        LeaveRequest leaveRequest = LeaveRequestReader.getLeaveById(leaveID);
        if (leaveRequest != null) {
            leaveRequest.approve(this);
            LeaveRequestReader.updateLeaveRequest(leaveRequest);
            System.out.println("Leave approved.");
        } else {
            System.out.println("Leave request not found.");
        }
    }
    
    public void rejectLeave(String leaveID) throws IOException {
        LeaveRequest leaveRequest = LeaveRequestReader.getLeaveById(leaveID);
        if (leaveRequest != null) {
            leaveRequest.rejectLeave();
            LeaveRequestReader.updateLeaveRequest(leaveRequest);
            System.out.println("Leave rejected.");
        } else {
            System.out.println("Leave request not found.");
    }
}

