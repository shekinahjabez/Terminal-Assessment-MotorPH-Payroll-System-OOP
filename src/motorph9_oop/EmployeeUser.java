/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package motorph9_oop;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author Shekinah Jabez
 */
public class EmployeeUser extends User {
    private LocalDateTime clockInTime;
    private LocalDateTime clockOutTime;

    public EmployeeUser(String employeeId, String username, String firstName, String userType, String par4, String par5, int parseInt, String par6, String par7, String par8, String par9, String par10, String par11, String par12, String par13) {
        super(employeeId, username, firstName, userType);
    }

    public LocalDateTime getClockInTime() {
        return clockInTime;
    }

    public LocalDateTime getClockOutTime() {
        return clockOutTime;
    }
    
    @Override
    public void accessDashboard() {
        System.out.println("Accessing Employee Dashboard");
    }
    
    public void recordTimeIn() {
        clockInTime = LocalDateTime.now();
        System.out.println("Clocked in at: " + clockInTime);
    }
    
    public void recordTimeOut() {
        clockOutTime = LocalDateTime.now();
        System.out.println("Clocked out at: " + clockOutTime);
    }
    
    public void requestLeave(String leaveID, String leaveType, LocalDate startDate, LocalDate endDate, String reason) throws IOException {
        LeaveRequest leaveRequest = new LeaveRequest(leaveID, getEmployeeId(), leaveType, startDate, endDate, reason);
        LeaveRequestReader.addLeaveRequest(leaveRequest);
        System.out.println("Leave request submitted.");
    }
    
    public void viewSalary() throws IOException {
        double basicSalary = SalaryDetailsReader.getBasicSalary(getEmployeeId());
        System.out.println("Your basic salary: " + basicSalary);
    }
    
    public void viewPayrollBreakdown() throws IOException {
        double basicSalary = SalaryDetailsReader.getBasicSalary(getEmployeeId());
        double totalAllowances = AllowanceDetailsReader.getRiceSubsidyAllowance(getEmployeeId()) +
                                 AllowanceDetailsReader.getPhoneAllowance(getEmployeeId()) +
                                 AllowanceDetailsReader.getClothingAllowance(getEmployeeId());
        double grossSalary = basicSalary + totalAllowances;
        double totalDeductions = Deductions.calculatePagibigDeduction() +
                                 Deductions.calculatePhilHealthDeduction(basicSalary) +
                                 Deductions.calculateSSSDeduction(basicSalary) +
                                 Deductions.calculateWithholdingTax(grossSalary);
        double netSalary = grossSalary - totalDeductions;
        
        System.out.println("Payroll Breakdown:");
        System.out.println("Basic Salary: " + basicSalary);
        System.out.println("Total Allowances: " + totalAllowances);
        System.out.println("Total Deductions: " + totalDeductions);
        System.out.println("Net Salary: " + netSalary);
    }
    
    public void viewTimeLogs() throws IOException {
        List<String[]> logs = TimeTrackerReader.getTimeLogs(getEmployeeId());
        System.out.println("Time Logs:");
        for (String[] log : logs) {
            System.out.println("Clock-In: " + log[1] + " | Clock-Out: " + (log[2].isEmpty() ? "Pending" : log[2]));
        }
    }
    
}


