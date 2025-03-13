package data_reader9;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDetailsReader {
    private final String employeeFilePath;
    private final String loginFilePath;

    public EmployeeDetailsReader(String employeeFilePath, String loginFilePath) {
        this.employeeFilePath = employeeFilePath;
        this.loginFilePath = loginFilePath;
    }

    // --- Login Data Access ---

    public String[] getLoginDataByUsername(String username) {
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
        try (BufferedReader br = new BufferedReader(new FileReader(loginFilePath))) {
            String line;
            br.readLine();

            while ((line = br.readLine()) != null) {
                String[] data = line.split(",", -1);
                if (data.length >= 4 && data[0].trim().equals(employeeNum.trim())) {
                    return data[3].trim();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean changeUserPassword(String employeeId, String newPassword) {
        try {
            List<String[]> loginData = CSVReader.readCSV(loginFilePath);
            boolean updated = false;

            for (String[] row : loginData) {
                if (row[0].equals(employeeId)) {
                    row[3] = newPassword;
                    row[4] = "NO";
                    updated = true;
                    break;
                }
            }

            if (updated) {
                CSVReader.writeCSV(loginFilePath, loginData);
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    // --- Employee Data Access ---

    public String[] getEmployeeDetails(String empNum) {
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

    public boolean isEmployeeValid(String empNum, String empName) {
        String[] employeeDetails = getEmployeeDetails(empNum);
        if (employeeDetails == null) {
            return false;
        }
        String csvFullName = employeeDetails[2].trim() + " " + employeeDetails[1].trim();
        return csvFullName.equalsIgnoreCase(empName);
    }

    public boolean addEmployee(String[] employeeData) throws IOException {
        List<String[]> data = CSVReader.readCSV(employeeFilePath);
        data.add(employeeData);
        CSVReader.writeCSV(employeeFilePath, data);
        return true;
    }

    public boolean updateEmployee(String[] updatedEmployeeData) throws IOException {
        List<String[]> data = CSVReader.readCSV(employeeFilePath);
        boolean found = false;
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i)[0].equals(updatedEmployeeData[0])) {
                data.set(i, updatedEmployeeData);
                found = true;
                break;
            }
        }
        if (found) CSVReader.writeCSV(employeeFilePath, data);
        return found;
    }

    public boolean deleteEmployee(String employeeId) throws IOException {
        List<String[]> data = CSVReader.readCSV(employeeFilePath);
        boolean removed = data.removeIf(emp -> emp[0].equals(employeeId));
        if (removed) CSVReader.writeCSV(employeeFilePath, data);
        return removed;
    }
}