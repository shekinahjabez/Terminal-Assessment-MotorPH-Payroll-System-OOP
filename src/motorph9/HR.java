/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package motorph9;

/**
 *
 * @author Shekinah Jabez
 */
public class HR extends User {
    public HR(String employeeId, String username) {
        super(employeeId, username, "HR");
    }

    @Override
    public void openDashboard() {
        new HRDashboard(this).setVisible(true);
    }
}

