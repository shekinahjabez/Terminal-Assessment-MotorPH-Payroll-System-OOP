package motorph9;

import data_reader9.LeaveRequestReader;
import data_reader9.TimeTrackerReader;
import data_reader9.AllowanceDetailsReader;
import data_reader9.SalaryDetailsReader;
import java.io.IOException;
import java.util.logging.Logger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import payroll9.Deductions;
import payroll9.Salary;
import payroll9.SalaryCalculation;
import payroll9.SalaryDetails;


public class EmployeeUser extends User {
    private static final Logger LOGGER = Logger.getLogger(EmployeeUser.class.getName());
    private LocalDateTime clockInTime;
    private LocalDateTime clockOutTime;
    private AllowanceDetailsReader allowanceReader = new AllowanceDetailsReader("src/data9/Allowance.csv");
    private SalaryDetailsReader salaryReader = new SalaryDetailsReader("src/data9/Salary.csv"); 
   
    
    public EmployeeUser(String employeeId, String lastName, String firstName, String birthday, 
                        String address, int phone, String sssNumber, String philhealthNumber, 
                        String tinNumber, String pagibigNumber, String status, 
                        String position, String supervisor) {
        super(employeeId, lastName, firstName, birthday, address, phone, 
              sssNumber, philhealthNumber, tinNumber, pagibigNumber, status, 
              position, supervisor, "Employee"); // Pass userType as "Employee"
    }
     
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
        double grossSalary = SalaryCalculation.calculateGrossSalary(getEmployeeId(), allowanceReader, salaryReader); // Pass salaryReader
        double netSalary = SalaryCalculation.calculateNetSalary(getEmployeeId(), allowanceReader, salaryReader); // Pass salaryReader
        double hourlyRate = SalaryCalculation.calculateHourlyRate(getEmployeeId(), salaryReader); // Pass salaryReader

        double riceSubsidy = allowanceReader.getRiceSubsidyAllowance(getEmployeeId());
        double phoneAllowance = allowanceReader.getPhoneAllowance(getEmployeeId());
        double clothingAllowance = allowanceReader.getClothingAllowance(getEmployeeId());
        double totalAllowances = riceSubsidy + phoneAllowance + clothingAllowance;

        Deductions deductions = new Deductions();
        double pagibigDeduction = deductions.calculatePagibigDeduction();
        double philHealthDeduction = deductions.calculatePhilHealthDeduction(grossSalary);
        double sssDeduction = deductions.calculateSSSDeduction(grossSalary);
        double withholdingTax = deductions.calculateWithholdingTax(grossSalary);
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
        try {
            SalaryDetailsReader reader = new SalaryDetailsReader("src/data9/Salary.csv");
            Salary salary = reader.getSalary(getEmployeeId());
            double basicSalary = salary.getBasicSalary();
            LOGGER.info("Employee " + getEmployeeId() + " basic salary: " + basicSalary);
        } catch (IOException e) {
            LOGGER.severe("Error viewing salary for employee " + getEmployeeId() + ": " + e.getMessage());
            throw e;
        }
    }
    
    public void viewPayrollBreakdown() throws IOException {
        SalaryDetailsReader reader = new SalaryDetailsReader("src/data9/Salary.csv");
        Salary salary = reader.getSalary(getEmployeeId());
        double basicSalary = salary.getBasicSalary();
        double totalAllowances = allowanceReader.getRiceSubsidyAllowance(getEmployeeId()) +
                allowanceReader.getPhoneAllowance(getEmployeeId()) +
                allowanceReader.getClothingAllowance(getEmployeeId());
        double grossSalary = basicSalary + totalAllowances;
        Deductions deductions = new Deductions();
        double totalDeductions = deductions.calculatePagibigDeduction() +
                deductions.calculatePhilHealthDeduction(basicSalary) +
                deductions.calculateSSSDeduction(basicSalary) +
                deductions.calculateWithholdingTax(grossSalary);
        double netSalary = grossSalary - totalDeductions;

        System.out.println("Payroll Breakdown:");
        System.out.println("Basic Salary: " + basicSalary);
        System.out.println("Total Allowances: " + totalAllowances);
        System.out.println("Total Deductions: " + totalDeductions);
        System.out.println("Net Salary: " + netSalary);
    }
    
    /*public void viewTimeLogs() throws IOException {
        List<String[]> logs = TimeTrackerReader.getTimeLogs(getEmployeeId());
        System.out.println("Time Logs:");
        for (String[] log : logs) {
            System.out.println("Clock-In: " + log[1] + " | Clock-Out: " + (log[2].isEmpty() ? "Pending" : log[2]));
        }
    }*/
    
    public void viewTimeLogs() throws IOException {
        List<TimeLog> logs = TimeTrackerReader.getTimeLogsAsObjects(getEmployeeId());
        System.out.println("Time Logs:");
        for (TimeLog log : logs) {
            String timeIn = log.getTimeIn() != null ? log.getTimeIn().format(DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm")) : "Pending";
            String timeOut = log.getTimeOut() != null ? log.getTimeOut().format(DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm")) : "Pending";
            System.out.println("Clock-In: " + timeIn + " | Clock-Out: " + timeOut);
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
