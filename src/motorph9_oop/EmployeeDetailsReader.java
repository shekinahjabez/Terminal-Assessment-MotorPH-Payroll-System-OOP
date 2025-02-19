/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package motorph9_oop;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Shekinah Jabez
 */
public class EmployeeDetailsReader {
   private static final String FILE_PATH = "src/data9/Employee.csv";
    
    public User getEmployeeById(String employeeId) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] data = line.split(",");
            if (data.length > 0 && data[0].equals(employeeId)) {
                reader.close();
                return createUserFromData(data);
            }
        }
        reader.close();
        return null;
    }
    
    private User createUserFromData(String[] data) {
        switch (data[14].toLowerCase()) { // userType column
            case "hr":
                return new HRUser(data[0], data[1], data[2], data[3], data[4], data[5], Integer.parseInt(data[6]),
                        data[7], data[8], data[9], data[10], data[11], data[12], data[13], data[14]);
            case "finance":
                return new FinanceUser(data[0], data[1], data[2], data[3], data[4], data[5], Integer.parseInt(data[6]),
                        data[7], data[8], data[9], data[10], data[11], data[12], data[13], data[14]);
            case "it":
                return new ITUser(data[0], data[1], data[2], data[3], data[4], data[5], Integer.parseInt(data[6]),
                        data[7], data[8], data[9], data[10], data[11], data[12], data[13], data[14]);
            default:
                return new EmployeeUser(data[0], data[1], data[2], data[3], data[4], data[5], Integer.parseInt(data[6]),
                        data[7], data[8], data[9], data[10], data[11], data[12], data[13], data[14]);
        }
    }
    
    
    public void addEmployee(User newEmployee) throws IOException {
        FileWriter writer = new FileWriter(FILE_PATH, true);
        BufferedWriter buffer = new BufferedWriter(writer);
        PrintWriter out = new PrintWriter(buffer);
        out.println(newEmployee.toCSV());
        out.close();
    }
    
    public boolean updateEmployee(User updatedEmployee) throws IOException {
        List<User> employees = getAllEmployees();
        boolean found = false;
        for (int i = 0; i < employees.size(); i++) {
            if (employees.get(i).getEmployeeId().equals(updatedEmployee.getEmployeeId())) {
                employees.set(i, updatedEmployee);
                found = true;
                break;
            }
        }
        if (found) writeAllEmployees(employees);
        return found;
    }
    
    public boolean deleteEmployee(String employeeId) throws IOException {
        List<User> employees = getAllEmployees();
        boolean removed = employees.removeIf(emp -> emp.getEmployeeId().equals(employeeId));
        if (removed) writeAllEmployees(employees);
        return removed;
    }
    
    private List<User> getAllEmployees() throws IOException {
        List<User> employees = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] data = line.split(",");
            if (data.length >= 15) {
                employees.add(createUserFromData(data));
            }
        }
        reader.close();
        return employees;
    }
    
    private void writeAllEmployees(List<User> employees) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH));
        for (User emp : employees) {
            writer.write(emp.toCSV() + "\n");
        }
        writer.close();
    }
}
