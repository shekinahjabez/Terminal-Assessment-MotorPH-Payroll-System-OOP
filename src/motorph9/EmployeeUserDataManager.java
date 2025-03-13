package motorph9;

import data_reader9.EmployeeDetailsReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import motorph9.EmployeeUser;
import motorph9.FinanceUser;
import motorph9.HRUser;
import motorph9.ITUser;
import motorph9.User;

public class EmployeeUserDataManager {

    private EmployeeDetailsReader employeeDetailsReader;

    public EmployeeUserDataManager() {
        this.employeeDetailsReader = new EmployeeDetailsReader("src/data9/Employee.csv", "src/data9/Login.csv");
    }

    public EmployeeUser getEmployee(String employeeId) {
        String[] employeeData = employeeDetailsReader.getEmployeeDetails(employeeId);
        if (employeeData != null) {
            return new EmployeeUser(employeeData[0], employeeData[1], employeeData[2], employeeData[3], employeeData[4],
                    Integer.parseInt(employeeData[5]), employeeData[6], employeeData[7], employeeData[8],
                    employeeData[9], employeeData[10], employeeData[11], employeeData[12]);
        }
        return null;
    }

    public User getUser(String username) {
        String[] loginData = employeeDetailsReader.getLoginDataByUsername(username);
        if (loginData != null) {
            String empNum = loginData[0].trim();
            String roleName = loginData[2].trim();
            String storedPassword = loginData[3].trim();
            String changePassword = loginData[4].trim();

            String[] employeeDetails = employeeDetailsReader.getEmployeeDetails(empNum);
            String firstName = (employeeDetails != null) ? employeeDetails[2] : "Unknown";
            String lastName = (employeeDetails != null) ? employeeDetails[1] : "Unknown";

            return createUser(empNum, username, roleName, storedPassword, firstName, lastName, changePassword);
        }
        return null;
    }

    public List<EmployeeUser> getAllEmployees() {
        List<String[]> allEmployeeData = employeeDetailsReader.getAllEmployeesStringArrays();
        List<EmployeeUser> employees = new ArrayList<>();
        for (String[] employeeData : allEmployeeData) {
            employees.add(new EmployeeUser(employeeData[0], employeeData[1], employeeData[2], employeeData[3],
                    employeeData[4], Integer.parseInt(employeeData[5]), employeeData[6], employeeData[7],
                    employeeData[8], employeeData[9], employeeData[10], employeeData[11], employeeData[12]));
        }
        return employees;
    }

    public boolean addEmployee(User employee) {
        String[] employeeData = {employee.getEmployeeId(), employee.getLastName(), employee.getFirstName(),
                employee.getBirthday(), employee.getAddress(), String.valueOf(employee.getPhone()),
                employee.getSSS(), employee.getPhilHealth(), employee.getTIN(), employee.getPagibig(),
                employee.getStatus(), employee.getPosition(), employee.getImmediateSupervisor()};
        try {
            return employeeDetailsReader.addEmployee(employeeData);
        } catch (IOException e) {
            System.err.println("Error adding employee: " + e.getMessage());
            return false;
        }
    }

    public boolean updateEmployee(User employee) {
        String[] updatedEmployeeData = {employee.getEmployeeId(), employee.getLastName(), employee.getFirstName(),
                employee.getBirthday(), employee.getAddress(), String.valueOf(employee.getPhone()),
                employee.getSSS(), employee.getPhilHealth(), employee.getTIN(), employee.getPagibig(),
                employee.getStatus(), employee.getPosition(), employee.getImmediateSupervisor()};
        try {
            return employeeDetailsReader.updateEmployee(updatedEmployeeData);
        } catch (IOException e) {
            System.err.println("Error updating employee: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteEmployee(String employeeId) {
        try {
            return employeeDetailsReader.deleteEmployee(employeeId);
        } catch (IOException e) {
            System.err.println("Error deleting employee: " + e.getMessage());
            return false;
        }
    }

    public boolean changePassword(String employeeId, String newPassword) {
        return employeeDetailsReader.changeUserPassword(employeeId, newPassword);
    }

    public boolean isEmployeeValid(String empNum, String empName) {
        return employeeDetailsReader.isEmployeeValid(empNum, empName);
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
}