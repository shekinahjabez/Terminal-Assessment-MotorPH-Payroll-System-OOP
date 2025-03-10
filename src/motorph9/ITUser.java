/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package motorph9;

/**
 *
 * @author Shekinah Jabez
 */
public class ITUser extends User {
    private String firstName;
    private String lastName;
    
    public ITUser(String employeeId, String lastName, String firstName, String birthday, 
                        String address, int phone, String sssNumber, String philhealthNumber, 
                        String tinNumber, String pagibigNumber, String status, 
                        String position, String supervisor) {
        super(employeeId, lastName, firstName, birthday, address, phone, 
              sssNumber, philhealthNumber, tinNumber, pagibigNumber, status, 
              position, supervisor, "IT"); // Pass userType as "IT"
    }
    /*public ITUser(String employeeId, String username, String roleName, String password, String firstName, String lastName) {
        super(employeeId, username, roleName, password, firstName, lastName);
    }*/
    
    
    public ITUser(String employeeId, String username, String roleName, String password, String firstName, String lastName, String changePassword) {
        super(employeeId, username, roleName, password, firstName, lastName, changePassword);
    }

    
    /*public ITUser(String employeeId, String username, String roleName, String password) {
        super(employeeId, username, roleName, password);
    }*/
    
    @Override
    public void accessDashboard() {
        System.out.println("Accessing IT Dashboard");
    }
    
    public void manageSystem() {
        System.out.println("Managing IT Systems");
    }
}

