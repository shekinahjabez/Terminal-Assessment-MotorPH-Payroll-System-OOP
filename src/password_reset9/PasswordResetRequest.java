package password_reset9;

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
    private String defaultPassword;
  
    // Constructor with all parameters
    public PasswordResetRequest(String employeeNumber, String employeeName, String dateOfRequest,
                                String defaultPassword, String status, String adminName,
                                String adminEmployeeNumber, String dateOfReset) {
        this.employeeNumber = employeeNumber;
        this.employeeName = employeeName;
        this.dateOfRequest = dateOfRequest;
        this.defaultPassword = defaultPassword;
        this.status = status;
        this.adminName = adminName;
        this.adminEmployeeNumber = adminEmployeeNumber;
        this.dateOfReset = dateOfReset;
    }
    
    // Constructor for new requests (default status and other fields)
    public PasswordResetRequest(String employeeNumber, String employeeName, String dateOfRequest, String defaultPassword) {
        this(employeeNumber, employeeName, dateOfRequest, defaultPassword, "Pending", "", "", "");
    }
    
    public PasswordResetRequest(String employeeNumber, String employeeName, String dateOfRequest) {
        this.employeeNumber = employeeNumber;
        this.employeeName = employeeName;
        this.dateOfRequest = dateOfRequest;
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

    public String getDefaultpassword() {
        return defaultPassword;
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
      
    public String toCSV() {
        return employeeNumber + "," + employeeName + "," + dateOfRequest + ",Pending,,,\n";
    }

    public String[] toArray() {
        return new String[]{
                employeeNumber,
                employeeName,
                dateOfRequest,
                "Pending",
                "",
                "",
                ""
        };
    }    
}

