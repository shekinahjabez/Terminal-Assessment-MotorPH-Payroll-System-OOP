/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package motorph9_MS2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Shekinah Jabez
 */
public class EmployeeDetailsReader {
   private static final String FILE_PATH = "src/data9/Employee.csv";
     
   /*public User getEmployeeById(String username) {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            br.readLine(); // Skip header row

            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length >= 13) { // Ensure there are enough columns
                    String empNum = data[0].trim();
                    String storedUsername = data[1].trim(); // Last name as username
                    String firstName = data[2].trim();
                    String roleName = "Employee"; // Default role for regular employees

                    System.out.println("Checking user: " + storedUsername);

                    if (storedUsername.equals(username.trim())) {
                        return new EmployeeUser(
                            empNum, storedUsername, firstName, data[3], data[4], // Employee ID, Last Name, First Name, Birthday, Address
                            Integer.parseInt(data[5]), data[6], data[7], // Phone (converted to int), SSS, PhilHealth
                            data[8], data[9], data[10], data[11], data[12] // TIN, Pagibig, Status, Position, Supervisor
                        );
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }*/
   
    public User getEmployeeById(String username) {
        try (BufferedReader br = new BufferedReader(new FileReader("src/data9/Login.csv"))) {
            String line;
            br.readLine(); // ✅ Skip header row

            while ((line = br.readLine()) != null) {
                String[] data = line.split(",", -1);
                if (data.length >= 4) { // Ensure at least username, role, and password exist
                    String empNum = data[0].trim(); // ✅ Employee ID from Login.csv
                    String storedUsername = data[1].trim();
                    String roleName = data[2].trim();
                    String storedPassword = data[3].trim();

                    if (storedUsername.equals(username.trim())) {
                        // ✅ Fetch First Name & Last Name from Employee.csv
                        String[] employeeDetails = getEmployeeDetails(empNum);
                        String firstName = (employeeDetails != null) ? employeeDetails[2] : "Unknown";
                        String lastName = (employeeDetails != null) ? employeeDetails[1] : "Unknown";

                        // ✅ Return Correct User Type
                        switch (roleName.trim()) {
                            case "HR":
                                return new HRUser(empNum, storedUsername, roleName, storedPassword, firstName, lastName);
                            case "IT":
                                return new ITUser(empNum, storedUsername, roleName, storedPassword, firstName, lastName);
                            case "Finance":
                                return new FinanceUser(empNum, storedUsername, roleName, storedPassword, firstName, lastName);
                            case "Employee":
                                return new EmployeeUser(empNum, storedUsername, roleName, storedPassword, firstName, lastName);
                            default:
                                return null;
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    // ✅ Fetch Employee Details from Employee.csv
    private String[] getEmployeeDetails(String empNum) {
        System.out.println("🔍 Searching for Employee Details for ID: " + empNum);

        try (BufferedReader br = new BufferedReader(new FileReader("src/data9/Employee.csv"))) {
            String line;
            br.readLine(); // ✅ Skip header row

            while ((line = br.readLine()) != null) {
                String[] data = line.split(",", -1); // ✅ Ensure empty values are captured properly
                System.out.println("🔎 Checking: " + data[0]); // Debugging output

                if (data.length >= 3 && data[0].trim().equals(empNum)) {
                    System.out.println("✅ Employee Found: " + data[1] + ", " + data[2]); // Debugging output
                    return data; // ✅ Return full employee data
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("❌ Employee Not Found for ID: " + empNum); // Debugging output
        return null; // ✅ Return null if not found
    }

    
    /*private String[] getEmployeeDetails(String empNum) {
        try (BufferedReader br = new BufferedReader(new FileReader("src/data9/Employee.csv"))) {
            String line;
            br.readLine(); // ✅ Skip header row
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",", -1);
                if (data.length >= 3 && data[0].trim().equals(empNum)) {
                    return data; // ✅ Return full employee data
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null; // ✅ Return null if not found
    }*/

   
    /*private static User createUserFromData(String[] data) {
        switch (data[14].toLowerCase()) { // userType column
            case "hr" -> {
                return new HRUser(data[0], data[1], data[2], data[3]);
           }
            case "finance" -> {
                return new FinanceUser(data[0], data[1], data[2], data[3]);
           }
            case "it" -> {
                return new ITUser(data[0], data[1], data[2], data[3]);
           }
            default -> {
                return new EmployeeUser(data[0], data[1], data[2], data[3], data[4], data[5], data[6], data[7], data[8], data[9], data[10], data[11], data[12]);
            }


        }
       // userType column
    }*/

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
    
    public static List<EmployeeUser> getAllEmployees() throws IOException {
        List<EmployeeUser> employees = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            boolean firstLine = true; // Skip header row

            while ((line = br.readLine()) != null) {
                if (firstLine) {
                    firstLine = false;
                    continue;
                }

                String[] data = line.split(",");
                if (data.length >= 13) { // Ensure required fields exist
                    EmployeeUser employee = new EmployeeUser(
                            data[0],  // Employee ID
                            data[1],  // Last Name
                            data[2],  // First Name
                            data[3],  // Birthday
                            data[4],  // Address
                            Integer.parseInt(data[5]), // Phone (converted to int)
                            data[6],  // SSS Number
                            data[7],  // Philhealth Number
                            data[8],  // TIN Number
                            data[9],  // Pagibig Number
                            data[10], // Status
                            data[11], // Position
                            data[12]  // Supervisor
                    );
                    employees.add(employee);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading employee details: " + e.getMessage());
        }
        return employees;
    }
    
    /*public User getLoginDetails(String username) {
        try (BufferedReader br = new BufferedReader(new FileReader("src/data9/Login.csv"))) {
            String line;
            br.readLine(); // ✅ Skip header row

            while ((line = br.readLine()) != null) {
                String[] data = line.split(",", -1);
                if (data.length >= 4) { // 4 columns (employeeNum,username,roleName,password)
                    String empNum = data[0].trim();
                    String storedUsername = data[1].trim();
                    String roleName = data[2].trim();
                    String storedPassword = data[3].trim(); // ✅ Read password from Login.csv

                    if (storedUsername.equals(username.trim())) {
                        // ✅ Fetch First & Last Name from Employee.csv
                        String[] employeeDetails = getEmployeeDetails(empNum);
                        String firstName = (employeeDetails != null) ? employeeDetails[2] : "Unknown";
                        String lastName = (employeeDetails != null) ? employeeDetails[1] : "Unknown";

                        // ✅ Return Correct User Type with First & Last Name
                        switch (roleName) {
                            case "HR":
                                return new HRUser(empNum, storedUsername, "HR", storedPassword, firstName, lastName);
                            case "IT":
                                return new ITUser(empNum, storedUsername, "IT", storedPassword, firstName, lastName);
                            case "Finance":
                                return new FinanceUser(empNum, storedUsername, "Finance", storedPassword, firstName, lastName);
                            case "Employee":
                                return new EmployeeUser(empNum, storedUsername, "Employee", storedPassword, firstName, lastName);
                            default:
                                return null;
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }*/ 
    
    public User getLoginDetails(String username) {
        try (BufferedReader br = new BufferedReader(new FileReader("src/data9/Login.csv"))) {
            String line;
            br.readLine(); // ✅ Skip header row

            while ((line = br.readLine()) != null) {
                String[] data = line.split(",", -1);
                if (data.length >= 4) { // ✅ 4 columns (employeeNum,username,roleName,password)
                    String empNum = data[0].trim(); // ✅ Employee ID from Login.csv
                    System.out.println("🔍 Found empNum for login: " + empNum); // Debugging output

                    String storedUsername = data[1].trim();
                    String roleName = data[2].trim();
                    String storedPassword = data[3].trim(); // ✅ Read password from Login.csv

                    if (storedUsername.equals(username.trim())) {
                        // ✅ Fetch First & Last Name from Employee.csv
                        String[] employeeDetails = getEmployeeDetails(empNum);
                        String firstName = (employeeDetails != null) ? employeeDetails[2] : "Unknown";
                        String lastName = (employeeDetails != null) ? employeeDetails[1] : "Unknown";

                        System.out.println("✅ First Name Retrieved: " + firstName); // Debugging output
                        System.out.println("✅ Last Name Retrieved: " + lastName);  // Debugging output

                        // ✅ Return Correct User Type with First & Last Name
                        switch (roleName) {
                            case "HR":
                                return new HRUser(empNum, storedUsername, "HR", storedPassword, firstName, lastName);
                            case "IT":
                                return new ITUser(empNum, storedUsername, "IT", storedPassword, firstName, lastName);
                            case "Finance":
                                return new FinanceUser(empNum, storedUsername, "Finance", storedPassword, firstName, lastName);
                            case "Employee":
                                return new EmployeeUser(empNum, storedUsername, "Employee", storedPassword, firstName, lastName);
                            default:
                                return null;
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}    
