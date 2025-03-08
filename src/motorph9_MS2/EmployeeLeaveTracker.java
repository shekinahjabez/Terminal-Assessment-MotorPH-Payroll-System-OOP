/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package motorph9_MS2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Shekinah Jabez
 */
public class EmployeeLeaveTracker {
    private int sickLeaveBalance;
    private int vacationLeaveBalance;
    private int birthdayLeaveBalance;
    private final String employeeId;
    private static final String FILE_PATH = "src/data9/LeaveBalances.csv";
    
    public EmployeeLeaveTracker(String employeeId) {
        this.employeeId = employeeId;
        loadLeaveBalances();
    }
    
    public int getSickLeaveBalance() {
        return sickLeaveBalance;
    }

    public int getVacationLeaveBalance() {
        return vacationLeaveBalance;
    }

    public int getBirthdayLeaveBalance() {
        return birthdayLeaveBalance;
    }

    public boolean hasSufficientLeave(String leaveType, int leaveDuration) {
        switch (leaveType) {
            case "Sick Leave":
                return sickLeaveBalance >= leaveDuration;
            case "Vacation Leave":
                return vacationLeaveBalance >= leaveDuration;
            case "Birthday Leave":
                return birthdayLeaveBalance >= leaveDuration;
            default:
                return false;
        }
    }

    public void deductLeave(String leaveType, int leaveDuration) {
        switch (leaveType) {
            case "Sick Leave":
                sickLeaveBalance -= leaveDuration;
                break;
            case "Vacation Leave":
                vacationLeaveBalance -= leaveDuration;
                break;
            case "Birthday Leave":
                birthdayLeaveBalance -= leaveDuration;
                break;
        }
        saveLeaveBalances();
    }
    
    private void loadLeaveBalances() {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data[0].equals(employeeId)) {
                    sickLeaveBalance = Integer.parseInt(data[3]);
                    vacationLeaveBalance = Integer.parseInt(data[4]);
                    birthdayLeaveBalance = Integer.parseInt(data[5]);
                    return;
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("Error loading leave balances: " + e.getMessage());
        }
    }
    
    /*private void saveLeaveBalances() {
        List<String> fileContent = new ArrayList<>();
        boolean found = false;
        
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data[0].equals(employeeId)) {
                    fileContent.add(employeeId + "," + sickLeaveBalance + "," + vacationLeaveBalance + "," + birthdayLeaveBalance);
                    found = true;
                } else {
                    fileContent.add(line);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading leave balances: " + e.getMessage());
        }
        
        if (!found) {
            fileContent.add(employeeId + "," + sickLeaveBalance + "," + vacationLeaveBalance + "," + birthdayLeaveBalance);
        }
        
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (String record : fileContent) {
                bw.write(record);
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error saving leave balances: " + e.getMessage());
        }
    }*/
    
    public void saveLeaveBalances() {
        List<String> fileContent = new ArrayList<>();
        boolean found = false;
        
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data[0].equals(employeeId)) {
                    // Preserve firstName & lastName while updating leave balances
                    fileContent.add(employeeId + "," + data[1] + "," + data[2] + "," + sickLeaveBalance + "," + vacationLeaveBalance + "," + birthdayLeaveBalance);
                    found = true;
                } else {
                    fileContent.add(line);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading leave balances: " + e.getMessage());
        }
        
        if (!found) {
            fileContent.add(employeeId + ",Unknown,Unknown," + sickLeaveBalance + "," + vacationLeaveBalance + "," + birthdayLeaveBalance);
        }
        
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (String record : fileContent) {
                bw.write(record);
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error saving leave balances: " + e.getMessage());
        }
    }
}

