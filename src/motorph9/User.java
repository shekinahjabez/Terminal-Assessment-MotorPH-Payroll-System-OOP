/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package motorph9;
public abstract class User {
    private String employeeId;
    private String username;
    private String firstName;
    private String lastName;
    private String birthday;
    private String address;
    private int phone;
    private String SSS;
    private String PhilHealth;
    private String TIN;
    private String Pagibig;
    private String immediateSupervisor;
    private String status;
    private String position;
    private String userType;
    private final String roleName;
    private String password;
    private String changePassword; // ✅ New field to track password reset status


    /*public User(String employeeId, String lastName, String firstName, String birthday, 
                String address, int phone, String sssNumber, String philhealthNumber, 
                String tinNumber, String pagibigNumber, String status, 
                String position, String supervisor, String roleName) {
        this.employeeId = employeeId;
        this.lastName = lastName;
        this.firstName = firstName;
        this.birthday = birthday;
        this.address = address;
        this.phone = phone;
        this.SSS = sssNumber;
        this.PhilHealth = philhealthNumber;
        this.TIN = tinNumber;
        this.Pagibig = pagibigNumber;
        this.status = status;
        this.position = position;
        this.immediateSupervisor = supervisor;
        this.roleName = roleName;
    }*/
    
    /**
     * Constructor for creating a User with full details.
     */
    public User(String employeeId, String lastName, String firstName, String birthday, 
                String address, int phone, String sssNumber, String philhealthNumber, 
                String tinNumber, String pagibigNumber, String status, 
                String position, String supervisor, String roleName) {
        this.employeeId = employeeId;
        this.lastName = lastName;
        this.firstName = firstName;
        this.birthday = birthday;
        this.address = address;
        this.phone = phone;
        this.SSS = sssNumber;
        this.PhilHealth = philhealthNumber;
        this.TIN = tinNumber;
        this.Pagibig = pagibigNumber;
        this.status = status;
        this.position = position;
        this.immediateSupervisor = supervisor;
        this.roleName = roleName;
        this.changePassword = "NO"; // ✅ Default is "NO" unless reset
    }
    
    /*public User(String employeeId, String username, String roleName, String password, String firstName, String lastName) {
        this.employeeId = employeeId;
        this.username = username;
        this.roleName = roleName;
        this.password = password;
        this.firstName = firstName;  
        this.lastName = lastName;
        this.changePassword = changePassword; // ✅ Assign from Login.csv
    }*/
    
    /**
     * Constructor for login validation (without full details).
     */
    public User(String employeeId, String username, String roleName, String password, 
                String firstName, String lastName, String changePassword) {
        this.employeeId = employeeId;
        this.username = username;
        this.roleName = roleName;
        this.password = password;
        this.firstName = firstName;  
        this.lastName = lastName;
        this.changePassword = changePassword; // ✅ Assign from Login.csv
    }
    
    
    
    /*public String toCSV() {
        return String.join(",", employeeId, username, firstName, lastName, birthday, address, 
                            String.valueOf(phone), SSS, PhilHealth, TIN, Pagibig, immediateSupervisor, 
                            status, position, userType);
    }*/
    
    /**
     * Converts user data to CSV format for easy storage and retrieval.
     */
    public String toCSV() {
        return String.join(",", employeeId, username, firstName, lastName, birthday, address, 
                            String.valueOf(phone), SSS, PhilHealth, TIN, Pagibig, immediateSupervisor, 
                            status, position, userType, changePassword); // ✅ Include changePassword
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public String getSSS() {
        return SSS;
    }

    public void setSSS(String SSS) {
        this.SSS = SSS;
    }

    public String getPhilHealth() {
        return PhilHealth;
    }

    public void setPhilHealth(String PhilHealth) {
        this.PhilHealth = PhilHealth;
    }

    public String getTIN() {
        return TIN;
    }

    public void setTIN(String TIN) {
        this.TIN = TIN;
    }

    public String getPagibig() {
        return Pagibig;
    }

    public void setPagibig(String Pagibig) {
        this.Pagibig = Pagibig;
    }

    public String getImmediateSupervisor() {
        return immediateSupervisor;
    }

    public void setImmediateSupervisor(String immediateSupervisor) {
        this.immediateSupervisor = immediateSupervisor;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getUserType() {
        return roleName;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getPassword() {
        return password;
    }
    
    public String getChangePassword() { 
        return changePassword; 
    } // Getter for Change Password
    
    public void setChangePassword(String changePassword) { 
        this.changePassword = changePassword; 
    } // ✅ Setter for Change Password
    
    public abstract void accessDashboard();


}
