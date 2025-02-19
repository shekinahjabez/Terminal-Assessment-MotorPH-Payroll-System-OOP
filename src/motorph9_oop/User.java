/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package motorph9_oop;

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

    public User(String employeeId, String username, String firstName, String userType) {
        this.employeeId = employeeId;
        this.username = username;
        this.firstName = firstName;
        this.userType = userType;
    }
    
    public String toCSV() {
        return String.join(",", employeeId, username, firstName, lastName, birthday, address, 
                            String.valueOf(phone), SSS, PhilHealth, TIN, Pagibig, immediateSupervisor, 
                            status, position, userType);
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
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public abstract void accessDashboard();
}
