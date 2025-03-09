/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package motorph9;

/**
 *
 * @author Shekinah Jabez
 */
public class PasswordResetRequest {
    private String employeeNumber;
    private String employeeName;
    private String dateOfRequest;
    private String status;

    public PasswordResetRequest(String employeeNumber, String employeeName, String dateOfRequest) {
        this.employeeNumber = employeeNumber;
        this.employeeName = employeeName;
        this.dateOfRequest = dateOfRequest;
        this.status = "Pending"; // Default status
    }

    public String getEmployeeNumber() {
        return employeeNumber;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public String getDateOfRequest() {
        return dateOfRequest;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // Convert request to CSV format
    public String toCSV() {
        return employeeNumber + "," + employeeName + "," + dateOfRequest + "," + status;
    }
}

