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
    private String adminName;
    private String adminEmployeeNumber;
    private String dateOfReset;

    /*public PasswordResetRequest(String employeeNumber, String employeeName, String dateOfRequest) {
        this.employeeNumber = employeeNumber;
        this.employeeName = employeeName;
        this.dateOfRequest = dateOfRequest;
        this.status = "Pending"; // Default status
    }*/
    
    // 3-parameter constructor (for saving new requests)
    public PasswordResetRequest(String employeeNumber, String employeeName, String dateOfRequest) {
        this.employeeNumber = employeeNumber;
        this.employeeName = employeeName;
        this.dateOfRequest = dateOfRequest;
        this.status = "Pending"; // Default status for new requests
        this.adminName = "";
        this.adminEmployeeNumber = "";
        this.dateOfReset = "";
    }
    
    // 7-parameter constructor (for loading existing requests)
    public PasswordResetRequest(String employeeNumber, String employeeName, String dateOfRequest,
                                String status, String adminName, String adminEmployeeNumber, String dateOfReset) {
        this.employeeNumber = employeeNumber;
        this.employeeName = employeeName;
        this.dateOfRequest = dateOfRequest;
        this.status = status;
        this.adminName = adminName;
        this.adminEmployeeNumber = adminEmployeeNumber;
        this.dateOfReset = dateOfReset;
    }

    // Getters
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

    public String getAdminName() {
        return adminName;
    }

    public String getAdminEmployeeNumber() {
        return adminEmployeeNumber;
    }

    public String getDateOfReset() {
        return dateOfReset;
    }
    
    // Setters

    public void setStatus(String status) {
        this.status = status;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public void setAdminEmployeeNumber(String adminEmployeeNumber) {
        this.adminEmployeeNumber = adminEmployeeNumber;
    }

    public void setDateOfReset(String dateOfReset) {
        this.dateOfReset = dateOfReset;
    }
    

    // Convert request to CSV format
    public String toCSV() {
        return employeeNumber + "," + employeeName + "," + dateOfRequest + "," + status;
    }
}

