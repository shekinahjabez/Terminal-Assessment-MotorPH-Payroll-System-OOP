/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package motorph9;

/**
 *
 * @author Shekinah Jabez
 */
public class IT extends User {
    public IT(String employeeId, String username) {
        super(employeeId, username, "IT");
    }

    @Override
    public void openDashboard() {
        new ITDashboard(this).setVisible(true);
    }
}
