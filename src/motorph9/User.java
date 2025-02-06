/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package motorph9;

public abstract class User {
    protected String employeeId;
    protected String username;
    protected String role;

    public User(String employeeId, String username, String role) {
        this.employeeId = employeeId;
        this.username = username;
        this.role = role;
    }

    public abstract void openDashboard();
}

