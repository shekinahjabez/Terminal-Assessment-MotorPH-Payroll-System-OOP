/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package motorph9_oop;

/**
 *
 * @author Shekinah Jabez
 */
public class ITUser extends User {
    public ITUser(String employeeId, String username, String firstName, String userType) {
        super(employeeId, username, firstName, userType);
    }
    
    @Override
    public void accessDashboard() {
        System.out.println("Accessing IT Dashboard");
    }
    
    public void manageSystem() {
        System.out.println("Managing IT Systems");
    }
}

