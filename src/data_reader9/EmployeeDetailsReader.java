package data_reader9;

import data_reader9.CSVReader;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import motorph9.EmployeeUser;
import motorph9.FinanceUser;
import motorph9.HRUser;
import motorph9.ITUser;
import motorph9.User;

/**
 *
 * @author Shekinah Jabez
 */
public class EmployeeDetailsReader {
   private final String employeeFilePath;
   private final String loginFilePath; 
   //private static final String FILE_PATH = "src/data9/Employee.csv";
   
    public EmployeeDetailsReader(String employeeFilePath, String loginFilePath) {
        this.employeeFilePath = employeeFilePath;
        this.loginFilePath = loginFilePath;
       
    }
        
    private String[] getLoginDataByUsername(String username) {
        try (BufferedReader br = new BufferedReader(new FileReader(loginFilePath))) {
            String line;
            br.readLine(); // Skip header

            while ((line = br.readLine()) != null) {
                String[] data = line.split(",", -1);
                if (data.length >= 2 && data[1].trim().equalsIgnoreCase(username.trim())) {
                    return data;
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading Login CSV: " + e.getMessage());
        }
        return null;
    }
    
    public String getPasswordByEmployeeNum(String employeeNum) {
        try (BufferedReader br = new BufferedReader(new FileReader("src/data9/Login.csv"))) {
            String line;
            br.readLine(); // ✅ Skip header row

            while ((line = br.readLine()) != null) {
                String[] data = line.split(",", -1);
                if (data.length >= 4) { // ✅ Ensure at least employeeNum, username, role, and password exist
                    String storedEmpNum = data[0].trim();
                    String storedPassword = data[3].trim(); // ✅ Password is in column 3

                    if (storedEmpNum.equals(employeeNum.trim())) {
                        return storedPassword; // ✅ Return password directly
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null; // ❌ Return null if no matching employee number is found
    }

    private User createUser(String empNum, String username, String roleName, String password, String firstName, String lastName, String changePassword) {
        switch (roleName.trim().toUpperCase()) {
            case "HR":
                return new HRUser(empNum, username, "HR", password, firstName, lastName, changePassword);
            case "IT":
                return new ITUser(empNum, username, "IT", password, firstName, lastName, changePassword);
            case "FINANCE":
                return new FinanceUser(empNum, username, "Finance", password, firstName, lastName, changePassword);
            case "EMPLOYEE":
                return new EmployeeUser(empNum, username, "Employee", password, firstName, lastName, changePassword);
            default:
                return null;
        }
    }
    
    public User getEmployeeById(String username) {
        String[] loginData = getLoginDataByUsername(username);
        if (loginData == null) {
            return null;
        }

        String empNum = loginData[0].trim();
        String roleName = loginData[2].trim();
        String storedPassword = loginData[3].trim();
        String changePassword = loginData[4].trim();

        String[] employeeDetails = getEmployeeDetails(empNum);
        String firstName = (employeeDetails != null) ? employeeDetails[2] : "Unknown";
        String lastName = (employeeDetails != null) ? employeeDetails[1] : "Unknown";

        return createUser(empNum, username, roleName, storedPassword, firstName, lastName, changePassword);
    }

   /*public User getEmployeeById(String username) {
        try (BufferedReader br = new BufferedReader(new FileReader("src/data9/Login.csv"))) {
            String line;
            br.readLine(); // ✅ Skip header row

            while ((line = br.readLine()) != null) {
                String[] data = line.split(",", -1);
                if (data.length >= 5) { // ✅ Ensure at least 5 columns exist (including Change Password)
                    String empNum = data[0].trim(); // ✅ Employee ID from Login.csv
                    String storedUsername = data[1].trim();
                    String roleName = data[2].trim();
                    String storedPassword = data[3].trim();
                    String changePassword = data[4].trim(); // ✅ Read Change Password status

                    if (storedUsername.equals(username.trim())) {
                        // ✅ Fetch First Name & Last Name from Employee.csv
                        String[] employeeDetails = getEmployeeDetails(empNum);
                        String firstName = (employeeDetails != null) ? employeeDetails[2] : "Unknown";
                        String lastName = (employeeDetails != null) ? employeeDetails[1] : "Unknown";

                        // ✅ Return Correct User Type with changePassword
                        switch (roleName.trim()) {
                            case "HR":
                                return new HRUser(empNum, storedUsername, roleName, storedPassword, firstName, lastName, changePassword);
                            case "IT":
                                return new ITUser(empNum, storedUsername, roleName, storedPassword, firstName, lastName, changePassword);
                            case "Finance":
                                return new FinanceUser(empNum, storedUsername, roleName, storedPassword, firstName, lastName, changePassword);
                            case "Employee":
                                return new EmployeeUser(empNum, storedUsername, roleName, storedPassword, firstName, lastName, changePassword);
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

    /*public EmployeeUser getEmployeeDetailsByNumber(String employeeNumToFind) throws IOException {        
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {    
            String line;
            br.readLine(); // Skip header row
            
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",", -1); // Split by comma, handle empty values
                
                if (data.length >= 13) { // Ensure we have enough columns
                   String employeeId = data[0].trim(); 
                   if (employeeId.equals(employeeNumToFind.trim())) { 
                      // Create EmployeeUser object from CSV row (using data from "Employee Reset Data.csv")
                      return new EmployeeUser(
                             data[0].trim(), // employeeId
                             data[1].trim(), // lastName
                             data[2].trim(), // firstName
                             data[3].trim(), // birthday
                             data[4].trim(), // address
                             Integer.parseInt(data[5].trim()), // phone
                             data[6].trim(), // sssNumber
                             data[7].trim(), // philhealthNumber
                             data[8].trim(), // tinNumber
                             data[9].trim(), // pagibigNumber
                             data[10].trim(), // status
                             data[11].trim(), // position
                             data[12].trim()  // supervisor
                     );   
                      
                   }   
                    
                }
                
            }
            
        } catch (IOException e) {
            System.err.println("Error reading Employee Reset Data CSV: " + e.getMessage());
            throw e; // Re-throw the exception to be handled by caller
        }
        
        return null;
        
    }*/
    
    public EmployeeUser getEmployeeDetailsByNumber(String employeeNumToFind) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(employeeFilePath))) {
            String line;
            br.readLine(); // Skip header row

            while ((line = br.readLine()) != null) {
                String[] data = line.split(",", -1);

                if (data.length >= 13 && data[0].trim().equals(employeeNumToFind.trim())) {
                    return new EmployeeUser(data[0].trim(), data[1].trim(), data[2].trim(), data[3].trim(), data[4].trim(),
                            Integer.parseInt(data[5].trim()), data[6].trim(), data[7].trim(), data[8].trim(),
                            data[9].trim(), data[10].trim(), data[11].trim(), data[12].trim());
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading Employee CSV: " + e.getMessage());
            throw e;
        }
        return null;
    }

    // Fetch Employee Details from Employee.csv
    /*private String[] getEmployeeDetails(String empNum) {
        System.out.println("🔍 Searching for Employee Details for ID: " + empNum);

        try (BufferedReader br = new BufferedReader(new FileReader("src/data9/Employee.csv"))) {
            String line;
            br.readLine(); // ✅ Skip header row

            while ((line = br.readLine()) != null) {
                String[] data = line.split(",", -1); // ✅ Handle missing values correctly
                if (data.length >= 3 && data[0].trim().equals(empNum.trim())) {
                    System.out.println("✅ Employee Found: " + data[1] + ", " + data[2]); // Debugging output
                    return data; // ✅ Return full employee data
                }
            }
        } catch (IOException e) {
            System.err.println("❌ Error reading Employee.csv: " + e.getMessage());
        }

        System.out.println("❌ Employee Not Found for ID: " + empNum);
        return null; // ✅ Return null if not found
    }*/
    
    private String[] getEmployeeDetails(String empNum) {
        try (BufferedReader br = new BufferedReader(new FileReader(employeeFilePath))) {
            String line;
            br.readLine();

            while ((line = br.readLine()) != null) {
                String[] data = line.split(",", -1);
                if (data.length >= 3 && data[0].trim().equals(empNum.trim())) {
                    return data;
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading Employee CSV: " + e.getMessage());
        }
        return null;
    }


    /*public static void addEmployee(User newEmployee) throws IOException {
        List<String[]> data = CSVReader.readCSV(FILE_PATH);
        data.add(new String[]{newEmployee.getEmployeeId(), newEmployee.getUsername(), newEmployee.getFirstName(),
                newEmployee.getLastName(), newEmployee.getBirthday(), newEmployee.getAddress(),
                String.valueOf(newEmployee.getPhone()), newEmployee.getSSS(), newEmployee.getPhilHealth(),
                newEmployee.getTIN(), newEmployee.getPagibig(), newEmployee.getImmediateSupervisor(),
                newEmployee.getStatus(), newEmployee.getPosition(), newEmployee.getUserType()});
        CSVReader.writeCSV(FILE_PATH, data);
    }*/
    
    public void addEmployee(User newEmployee) throws IOException {
        List<String[]> data = CSVReader.readCSV(employeeFilePath);
        data.add(new String[]{newEmployee.getEmployeeId(), newEmployee.getUsername(), newEmployee.getFirstName(),
                newEmployee.getLastName(), newEmployee.getBirthday(), newEmployee.getAddress(),
                String.valueOf(newEmployee.getPhone()), newEmployee.getSSS(), newEmployee.getPhilHealth(),
                newEmployee.getTIN(), newEmployee.getPagibig(), newEmployee.getImmediateSupervisor(),
                newEmployee.getStatus(), newEmployee.getPosition(), newEmployee.getUserType()});
        CSVReader.writeCSV(employeeFilePath, data);
    }

    /*public static boolean updateEmployee(User updatedEmployee) throws IOException {
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
    }*/
    
    public boolean updateEmployee(User updatedEmployee) throws IOException {
        List<String[]> data = CSVReader.readCSV(employeeFilePath);
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
        if (found) CSVReader.writeCSV(employeeFilePath, data);
        return found;
    }


    /*public static boolean deleteEmployee(String employeeId) throws IOException {
        List<String[]> data = CSVReader.readCSV(FILE_PATH);
        boolean removed = data.removeIf(emp -> emp[0].equals(employeeId));
        if (removed) CSVReader.writeCSV(FILE_PATH, data);
        return removed;
    }*/
    
    public boolean deleteEmployee(String employeeId) throws IOException {
        List<String[]> data = CSVReader.readCSV(employeeFilePath);
        boolean removed = data.removeIf(emp -> emp[0].equals(employeeId));
        if (removed) CSVReader.writeCSV(employeeFilePath, data);
        return removed;
    }

    
    /*public static List<EmployeeUser> getAllEmployees() throws IOException {
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
    }*/
    
    public List<EmployeeUser> getAllEmployees() throws IOException {
        List<EmployeeUser> employees = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(employeeFilePath))) {
            String line;
            br.readLine();

            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length >= 13) {
                    employees.add(new EmployeeUser(data[0], data[1], data[2], data[3], data[4],
                            Integer.parseInt(data[5]), data[6], data[7], data[8], data[9],
                            data[10], data[11], data[12]));
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading employee details: " + e.getMessage());
        }
        return employees;
    }
    
    public List<String[]> getAllEmployeesStringArrays() {
        List<String[]> employeeData = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(employeeFilePath))) {
            String line = br.readLine(); // Skip header
            while ((line = br.readLine()) != null) {
                employeeData.add(line.split(",", -1));
            }
        } catch (IOException e) {
            System.err.println("Error reading employee data: " + e.getMessage());
        }
        return employeeData;
    }

    
    /**
     * Checks if the given Employee Number and Name exist in Employee.csv.
     * This method is used for **validating Password Reset Requests**.
     *
     * @param empNum The Employee Number entered by the user.
     * @param empName The Full Name entered by the user (should match "FirstName LastName").
     * @return true if the Employee Number and Name match an entry in Employee.csv, false otherwise.
     */
    /*public boolean isEmployeeValid(String empNum, String empName) {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            br.readLine(); // ✅ Skip header row

            while ((line = br.readLine()) != null) {
                String[] data = line.split(",", -1); // ✅ Handle empty values properly
                
                if (data.length >= 3) { // ✅ Ensure at least Employee ID, Last Name, and First Name exist
                    String csvEmpNum = data[0].trim();  // ✅ Employee ID (Column 0)
                    String csvFullName = data[2].trim() + " " + data[1].trim(); // ✅ Construct full name as "FirstName LastName"

                    // ✅ Compare Employee Number & Name (case-insensitive)
                    if (csvEmpNum.equals(empNum) && csvFullName.equalsIgnoreCase(empName)) {
                        return true; // ✅ Employee found
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("❌ Error reading employee database: " + e.getMessage());
        }

        return false; // ❌ Employee not found
    }*/
    
    public boolean isEmployeeValid(String empNum, String empName) {
        String[] employeeDetails = getEmployeeDetails(empNum);
        if (employeeDetails == null) {
            return false;
        }
        String csvFullName = employeeDetails[2].trim() + " " + employeeDetails[1].trim();
        return csvFullName.equalsIgnoreCase(empName);
    }
    
    public User getLoginDetails(String username) {
        String[] loginData = getLoginDataByUsername(username);
        if (loginData == null) {
            return null;
        }
        String empNum = loginData[0].trim();
        String roleName = loginData[2].trim().toUpperCase();
        String storedPassword = loginData[3].trim();
        String changePassword = loginData[4].trim();

        String[] employeeDetails = getEmployeeDetails(empNum);
        String firstName = (employeeDetails != null) ? employeeDetails[2] : "Unknown";
        String lastName = (employeeDetails != null) ? employeeDetails[1] : "Unknown";

        return createUser(empNum, username, roleName, storedPassword, firstName, lastName, changePassword);
    }
    
    public boolean changeUserPassword(String employeeId, String newPassword) {
        try {
            List<String[]> loginData = CSVReader.readCSV("src/data9/Login.csv");
            boolean updated = false;

            for (String[] row : loginData) {
                if (row[0].equals(employeeId)) { // ✅ Match Employee ID
                    row[3] = newPassword; // ✅ Update password
                    row[4] = "NO"; // ✅ Reset Change Password flag
                    updated = true;
                    break;
                }
            }

            if (updated) {
                CSVReader.writeCSV("src/data9/Login.csv", loginData);
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    /*public boolean changeUserPassword(String employeeId, String newPassword) {
        try {
            List<String[]> loginData = CSVReader
        
    }*/    
}    
