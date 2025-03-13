package motorph9;

import data_reader9.AllowanceDetailsReader;
import data_reader9.CSVReader;
import data_reader9.SalaryDetailsReader;
import java.io.IOException;
import payroll9.Deductions;
import payroll9.PayrollProcessor;
import payroll9.Salary;
import payroll9.SalaryCalculation;
import payroll9.SalaryDetails;


/**
 *
 * @author Shekinah Jabez
 */
public class FinanceUser extends User {
    private AllowanceDetailsReader allowanceReader = new AllowanceDetailsReader("src/data9/Allowance.csv");
    private SalaryDetailsReader salaryReader = new SalaryDetailsReader("src/data9/Salary.csv"); // Reuse reader
     
    public FinanceUser(String employeeId, String lastName, String firstName, String birthday, 
                        String address, int phone, String sssNumber, String philhealthNumber, 
                        String tinNumber, String pagibigNumber, String status, 
                        String position, String supervisor) {
        super(employeeId, lastName, firstName, birthday, address, phone, 
              sssNumber, philhealthNumber, tinNumber, pagibigNumber, status, 
              position, supervisor, "Finance"); // Pass userType as "Finance"
    }
     
    public FinanceUser(String employeeId, String username, String roleName, String password, String firstName, String lastName, String changePassword) {
        super(employeeId, username, roleName, password, firstName, lastName, changePassword);
        
    }
     
    /*public double getHourlyRate() throws IOException {
        return SalaryCalculation.calculateHourlyRate(getEmployeeId());
    }
    
    public double getNetSalary() throws IOException {
        return SalaryCalculation.calculateNetSalary(getEmployeeId(), allowanceReader);
    }*/
    
    public double getHourlyRate() throws IOException {
        return SalaryCalculation.calculateHourlyRate(getEmployeeId(), salaryReader); // Pass reader
    }

    public double getNetSalary() throws IOException {
        return SalaryCalculation.calculateNetSalary(getEmployeeId(), allowanceReader, salaryReader); // Pass reader
    }
    
    @Override
    public void accessDashboard() {
        System.out.println("Accessing Finance Dashboard");
    }
    
    public void computeMonthlyPayroll() {
        System.out.println("Computing monthly payroll");
    }
    
    public void generatePayrollReport() {
        System.out.println("Generating payroll report");
    }
   
    /*public void processPayroll(String employeeId) throws IOException {
        SalaryDetailsReader salaryReader = new SalaryDetailsReader("src/data9/Salary.csv");
        PayrollProcessor processor = new PayrollProcessor("src/data9/Payroll.csv", salaryReader);
        processor.processPayroll(employeeId);
    }*/
    
    public void processPayroll(String employeeId) throws IOException {
        PayrollProcessor processor = new PayrollProcessor("src/data9/Payroll.csv", salaryReader);
        processor.processPayroll(employeeId);
    }

    /*public void viewPayroll(String employeeId) throws IOException {
        System.out.println("Payroll Details for Employee ID: " + employeeId);
        for (String[] data : CSVReader.readCSV("src/motorph9/Payroll.csv")) {
            if (data[0].equals(employeeId)) {
                System.out.println("Month: " + data[1] + " | Year: " + data[2]);
                System.out.println("Gross Salary: " + data[3]);
                System.out.println("Total Allowance: " + data[4]);
                System.out.println("Total Deductions: " + data[5]);
                System.out.println("Net Monthly Salary: " + data[6]);
                return;
            }
        }
        System.out.println("No payroll record found for Employee ID: " + employeeId);
    }*/
     
    /*public SalaryDetails getSalaryDetails(String employeeId) throws IOException {
        double grossSalary = SalaryCalculation.calculateGrossSalary(employeeId, allowanceReader);
        double netSalary = SalaryCalculation.calculateNetSalary(employeeId, allowanceReader);
        double hourlyRate = SalaryCalculation.calculateHourlyRate(employeeId);

        double riceSubsidy = allowanceReader.getRiceSubsidyAllowance(employeeId);
        double phoneAllowance = allowanceReader.getPhoneAllowance(employeeId);
        double clothingAllowance = allowanceReader.getClothingAllowance(employeeId);
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
    }*/
    
    public SalaryDetails getSalaryDetails(String employeeId) throws IOException {
        double grossSalary = SalaryCalculation.calculateGrossSalary(employeeId, allowanceReader, salaryReader); // Pass salaryReader
        double netSalary = SalaryCalculation.calculateNetSalary(employeeId, allowanceReader, salaryReader); // Pass salaryReader
        double hourlyRate = SalaryCalculation.calculateHourlyRate(employeeId, salaryReader); // Pass salaryReader

        double riceSubsidy = allowanceReader.getRiceSubsidyAllowance(employeeId);
        double phoneAllowance = allowanceReader.getPhoneAllowance(employeeId);
        double clothingAllowance = allowanceReader.getClothingAllowance(employeeId);
        double totalAllowances = riceSubsidy + phoneAllowance + clothingAllowance;

        Deductions deductions = new Deductions();
        double pagibigDeduction = deductions.calculatePagibigDeduction();
        double philHealthDeduction = deductions.calculatePhilHealthDeduction(grossSalary);
        double sssDeduction = deductions.calculateSSSDeduction(grossSalary);
        double withholdingTax = deductions.calculateWithholdingTax(grossSalary);
        double totalDeductions = pagibigDeduction + philHealthDeduction + sssDeduction + withholdingTax;
        
        System.out.println("Salary: " + salaryReader.getSalary(employeeId));
        System.out.println("Allowances: Rice=" + allowanceReader.getRiceSubsidyAllowance(employeeId) +
                            ", Phone=" + allowanceReader.getPhoneAllowance(employeeId) +
                            ", Clothing=" + allowanceReader.getClothingAllowance(employeeId));
        
        System.out.println("Gross Salary: " + grossSalary);
        System.out.println("Net Salary: " + netSalary);
        System.out.println("Total Deductions: " + totalDeductions);
        
        return new SalaryDetails(grossSalary, netSalary, hourlyRate,
                riceSubsidy, phoneAllowance, clothingAllowance, totalAllowances,
                pagibigDeduction, philHealthDeduction, sssDeduction, withholdingTax, totalDeductions);
        
    }

}
