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
    
    public static User getEmployeeById(String employeeId) throws IOException {
        for (String[] data : CSVReader.readCSV(FILE_PATH)) {
            if (data[0].equals(employeeId)) {
                return createUserFromData(data);
            }
        }
        return null;
    }

    private static User createUserFromData(String[] data) {
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

    public static void addEmployee(User newEmployee) throws IOException {
        List<String[]> data = CSVReader.readCSV(FILE_PATH);
        data.add(new String[]{newEmployee.getEmployeeId(), newEmployee.getUsername(), newEmployee.getFirstName(),
                newEmployee.getLastName(), newEmployee.getBirthday(), newEmployee.getAddress(),
                String.valueOf(newEmployee.getPhone()), newEmployee.getSSS(), newEmployee.getPhilHealth(),
                newEmployee.getTIN(), newEmployee.getPagibig(), newEmployee.getImmediateSupervisor(),
                newEmployee.getStatus(), newEmployee.getPosition(), newEmployee.getUserType()});
        CSVReader.writeCSV(FILE_PATH, data);
    }

    public static boolean updateEmployee(User updatedEmployee) throws IOException {
        List<String[]> data = CSVReader.readCSV(FILE_PATH);
        boolean found = false;
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i)[0].equals(updatedEmployee.getEmployeeId())) {
                data.set(i, new String[]{updatedEmployee.getEmployeeId(), updatedEmployee.getUsername(),
                        updatedEmployee.getFirstName(), updatedEmployee.getLastName(), updatedEmployee.getBirthday(),
                        updatedEmployee.getAddress(), String.valueOf(updatedEmployee.getPhone()), updatedEmployee.getSSS(),
                        updatedEmployee.getPhilHealth(), updatedEmployee.getTIN(), updatedEmployee.getPagibig(),
                        updatedEmployee.getImmediateSupervisor(), updatedEmployee.getStatus(),
                        updatedEmployee.getPosition(), updatedEmployee.getUserType()});
                found = true;
                break;
            }
        }
        if (found) CSVReader.writeCSV(FILE_PATH, data);
        return found;
    }

    public static boolean deleteEmployee(String employeeId) throws IOException {
        List<String[]> data = CSVReader.readCSV(FILE_PATH);
        boolean removed = data.removeIf(emp -> emp[0].equals(employeeId));
        if (removed) CSVReader.writeCSV(FILE_PATH, data);
        return removed;
    }
}