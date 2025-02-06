/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package motorph9;

/**
 *
 * @author Shekinah Jabez
 */
public class Employee extends User {
    public Employee (String employeeId, String username) {
        super(employeeId, username, "Employee");
    }

    @Override
    public void openDashboard() {
        new EmployeeDashboard(this).setVisible(true);
    }
}

