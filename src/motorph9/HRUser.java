/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package motorph9;

import data_reader9.EmployeeDetailsReader;
import data_reader9.LeaveRequestReader;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Shekinah Jabez
 */
public class HRUser extends User {
    private static final String LEAVE_FILE_PATH = "src/data9/LeaveRequests.csv";
    private String firstName;
    private String lastName;
    
    public HRUser(String employeeId, String lastName, String firstName, String birthday, 
                        String address, int phone, String sssNumber, String philhealthNumber, 
                        String tinNumber, String pagibigNumber, String status, 
                        String position, String supervisor) {
        super(employeeId, lastName, firstName, birthday, address, phone, 
              sssNumber, philhealthNumber, tinNumber, pagibigNumber, status, 
              position, supervisor, "HR");
    }
    
    public HRUser(String employeeId, String username, String roleName, String password, String firstName, String lastName) {
        super(employeeId, username, roleName, password, firstName, lastName);
        
        // ✅ Debugging: Print to confirm assignment
        System.out.println("✅ HRUser Created: " + this.firstName + " " + this.lastName);
    }
    
    /*public HRUser(String employeeId, String username, String roleName, String password) {
        super(employeeId, username, roleName, password);
    }*/
    
    @Override
    public void accessDashboard() {
        System.out.println("Accessing HR Dashboard");
    }
    
    /*public void updateEmployee(User updatedEmployee, EmployeeDetailsReader employeeReader) {
        if (!isValidEmployee(updatedEmployee)) {
            System.out.println("Invalid employee data. Update failed.");
            return;
        }
        
        List<String> employees = new ArrayList<>();
        boolean found = false;
        String FILE_PATH = null;
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith(updatedEmployee.getEmployeeId() + ",")) {
                    employees.add(updatedEmployee.toCSV());
                    found = true;
                } else {
                    employees.add(line);
                }
            }
        } catch (IOException e) {
            System.out.println("Error updating employee: " + e.getMessage());
        }
        
        if (found) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
                for (String employee : employees) {
                    writer.write(employee);
                    writer.newLine();
                }
                System.out.println("Employee updated successfully.");
            } catch (IOException e) {
                System.out.println("Error saving updated employee data: " + e.getMessage());
            }
        } else {
            System.out.println("Employee not found.");
        }
    }*/  
    
    public void updateEmployee(User updatedEmployee, EmployeeDetailsReader employeeReader) {
        try {
            boolean success = employeeReader.updateEmployee(updatedEmployee);
            if (success) {
                System.out.println("Employee updated successfully.");
            } else {
                System.out.println("Employee not found.");
            }
        } catch (IOException e) {
            System.out.println("Error updating employee: " + e.getMessage());
        }
    }
    
    /*public void addEmployee(User newEmployee, EmployeeDetailsReader employeeReader) {
        if (isValidEmployee(newEmployee)) {
            String FILE_PATH = null;
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
                writer.write(newEmployee.toCSV());
                writer.newLine();
                System.out.println("Employee added successfully.");
            } catch (IOException e) {
                System.out.println("Error adding employee: " + e.getMessage());
            }
        } else {
            System.out.println("Invalid employee data. Addition failed.");
        }
    }*/

    public void addEmployee(User newEmployee, EmployeeDetailsReader employeeReader) {
        try {
            employeeReader.addEmployee(newEmployee);
            System.out.println("Employee added successfully.");
        } catch (IOException e) {
            System.out.println("Error adding employee: " + e.getMessage());
        }
    }

    
    /*public void deleteEmployee(String employeeId, EmployeeDetailsReader employeeReader) {
        List<String> employees = new ArrayList<>();
        boolean found = false;
        String FILE_PATH = null;
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.startsWith(employeeId + ",")) {
                    employees.add(line);
                } else {
                    found = true;
                }
            }
        } catch (IOException e) {
            System.out.println("Error deleting employee: " + e.getMessage());
        }
        
        if (found) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
                for (String employee : employees) {
                    writer.write(employee);
                    writer.newLine();
                }
                System.out.println("Employee deleted successfully.");
            } catch (IOException e) {
                System.out.println("Error saving updated employee data: " + e.getMessage());
            }
        } else {
            System.out.println("Employee not found.");
        }
    }*/
    
    public void deleteEmployee(String employeeId, EmployeeDetailsReader employeeReader) {
        try {
            boolean success = employeeReader.deleteEmployee(employeeId);
            if (success) {
                System.out.println("Employee deleted successfully.");
            } else {
                System.out.println("Employee not found.");
            }
        } catch (IOException e) {
            System.out.println("Error deleting employee: " + e.getMessage());
        }
    }

    
    /*public void viewEmployeeRecords() {
        System.out.println("Employee Records:");
        String FILE_PATH = null;
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("Error reading employee records: " + e.getMessage());
        }
    }*/
    
    public void viewEmployeeRecords() {
        System.out.println("Employee Records:");
        String FILE_PATH = "src/data9/Employee.csv";

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("Error reading employee records: " + e.getMessage());
        }
    }

    
    private boolean isValidEmployee(User employee) {
        return employee.getEmployeeId() != null && !employee.getEmployeeId().isEmpty()
                && employee.getUsername() != null && !employee.getUsername().isEmpty();
    }
    
    /*public void approveLeave(String leaveID) throws IOException {
        LeaveRequest leaveRequest = LeaveRequestReader.getLeaveById(leaveID);
        if (leaveRequest != null) {
            leaveRequest.approve(getFirstName() + " " + getLastName()); // Pass HRUser's name
            LeaveRequestReader.updateLeaveRequest(leaveRequest);
            System.out.println("Leave approved.");
        } else {
            System.out.println("Leave request not found.");
        }
    }*/
    
    public void approveLeave(String leaveID, LeaveRequestReader leaveRequestReader) throws IOException {
        LeaveRequest leaveRequest = leaveRequestReader.getLeaveById(leaveID); // ✅ FIXED
        if (leaveRequest != null) {
            leaveRequest.approve(getFirstName() + " " + getLastName());
            leaveRequestReader.updateLeaveRequest(leaveRequest);
            System.out.println("Leave approved.");
        } else {
            System.out.println("Leave request not found.");
        }
    }


    /*public void rejectLeave(String leaveID) throws IOException {
        LeaveRequest leaveRequest = LeaveRequestReader.getLeaveById(leaveID);
        if (leaveRequest != null) {
            leaveRequest.reject(getFirstName() + " " + getLastName()); // Pass HRUser's name
            LeaveRequestReader.updateLeaveRequest(leaveRequest);
            System.out.println("Leave rejected.");
        } else {
            System.out.println("Leave request not found.");
        }
    }*/
    
    public void rejectLeave(String leaveID, LeaveRequestReader leaveRequestReader) throws IOException {
        LeaveRequest leaveRequest = leaveRequestReader.getLeaveById(leaveID); // ✅ FIXED
        if (leaveRequest != null) {
            leaveRequest.reject(getFirstName() + " " + getLastName());
            leaveRequestReader.updateLeaveRequest(leaveRequest);
            System.out.println("Leave rejected.");
        } else {
            System.out.println("Leave request not found.");
        }
    }

    
    private void updateLeaveStatus(String leaveId, String newStatus) throws IOException {
        List<String> leaveRequests = new ArrayList<>();
        boolean found = false;
        try (BufferedReader reader = new BufferedReader(new FileReader(LEAVE_FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data[0].equals(leaveId)) {
                    data[5] = newStatus;
                    found = true;
                }
                leaveRequests.add(String.join(",", data));
            }
        }
        
        if (found) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(LEAVE_FILE_PATH))) {
                for (String leave : leaveRequests) {
                    writer.write(leave);
                    writer.newLine();
                }
                System.out.println("Leave request " + leaveId + " updated to " + newStatus);
            }
        } else {
            System.out.println("Leave request not found.");
        }
    }
}    
