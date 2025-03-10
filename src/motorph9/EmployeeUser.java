/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package motorph9;

import data_reader9.LeaveRequestReader;
import data_reader9.TimeTrackerReader;
import data_reader9.AllowanceDetailsReader;
import data_reader9.SalaryDetailsReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
//import motorph9_MS2.FinanceUser.SalaryDetails;

public class EmployeeUser extends User {
    private LocalDateTime clockInTime;
    private LocalDateTime clockOutTime;
    //private String firstName;
    //private String lastName;
    
    public EmployeeUser(String employeeId, String lastName, String firstName, String birthday, 
                        String address, int phone, String sssNumber, String philhealthNumber, 
                        String tinNumber, String pagibigNumber, String status, 
                        String position, String supervisor) {
        super(employeeId, lastName, firstName, birthday, address, phone, 
              sssNumber, philhealthNumber, tinNumber, pagibigNumber, status, 
              position, supervisor, "Employee"); // Pass userType as "Employee"
    }
     
    /*public EmployeeUser(String employeeId, String username, String roleName, String password, String firstName, String lastName) {
        super(employeeId, username, roleName, password, firstName, lastName);
    }*/

    public EmployeeUser(String employeeId, String username, String roleName, String password, String firstName, String lastName, String changePassword) {
        super(employeeId, username, roleName, password, firstName, lastName, changePassword);
        
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
    
    public SalaryDetails getSalaryDetails() throws IOException {
        double grossSalary = SalaryCalculation.calculateGrossSalary(getEmployeeId());
        double netSalary = SalaryCalculation.calculateNetSalary(getEmployeeId());
        double hourlyRate = SalaryCalculation.calculateHourlyRate(getEmployeeId());

        double riceSubsidy = AllowanceDetailsReader.getRiceSubsidyAllowance(getEmployeeId());
        double phoneAllowance = AllowanceDetailsReader.getPhoneAllowance(getEmployeeId());
        double clothingAllowance = AllowanceDetailsReader.getClothingAllowance(getEmployeeId());
        double totalAllowances = riceSubsidy + phoneAllowance + clothingAllowance;

        double pagibigDeduction = Deductions.calculatePagibigDeduction();
        double philHealthDeduction = Deductions.calculatePhilHealthDeduction(grossSalary);
        double sssDeduction = Deductions.calculateSSSDeduction(grossSalary);
        double withholdingTax = Deductions.calculateWithholdingTax(grossSalary);
        double totalDeductions = pagibigDeduction + philHealthDeduction + sssDeduction + withholdingTax;

        return new SalaryDetails(grossSalary, netSalary, hourlyRate,
                                 riceSubsidy, phoneAllowance, clothingAllowance, totalAllowances,
                                 pagibigDeduction, philHealthDeduction, sssDeduction, withholdingTax, totalDeductions);
    }

    
    public void requestLeave(String leaveID, String leaveType, LocalDate startDate, LocalDate endDate, String reason) throws IOException {
        LeaveRequest leaveRequest = new LeaveRequest(
            leaveID,              // leaveID
            getEmployeeId(),      // employeeID
            leaveType,            // leaveType
            LocalDate.now(),      // ✅ dateRequest (Set to today)
            startDate,            // startDate
            endDate,              // endDate
            reason,               // reason
            "Pending",            // ✅ status (New requests are always "Pending")
            "",                   // ✅ approver (Empty, since not yet approved)
            null,                  // ✅ dateResponded (Null, since not yet processed)
            "" 
        );

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

    public String getEmployeeNum() {
        return super.getEmployeeId();
    }

    public int getPhoneNumber() {
        return super.getPhone();
    }

    public String getSupervisor() {
        return super.getImmediateSupervisor();
    }

    public String getPagibigNumber() {
        return super.getPagibig();
    }
}    
