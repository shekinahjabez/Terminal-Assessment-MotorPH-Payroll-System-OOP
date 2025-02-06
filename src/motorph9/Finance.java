/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package motorph9;

/**
 *
 * @author Shekinah Jabez
 */
public class Finance extends User {
    public Finance (String employeeId, String username) {
        super(employeeId, username, "Finance");
    }

    @Override
    public void openDashboard() {
        new FinanceDashboard(this).setVisible(true);
    }
}
