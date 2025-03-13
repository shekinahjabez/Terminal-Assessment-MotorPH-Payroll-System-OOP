package motorph9;

import data_reader9.AllowanceDetailsReader;
import data_reader9.CSVReader;
import data_reader9.SalaryDetailsReader;
import java.io.IOException;
import payroll9.Deductions;
import payroll9.PayrollProcessor;
import payroll9.Salary;
import payroll9.SalaryDetails;


/**
 *
 * @author Shekinah Jabez
 */
public class FinanceUser extends User {
    private AllowanceDetailsReader allowanceReader = new AllowanceDetailsReader("src/data9/Allowance.csv");
    
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
        SalaryDetailsReader reader = new SalaryDetailsReader("src/data9/Salary.csv");
        Salary salary = reader.getSalary(getEmployeeId());
        if (salary != null) {
            return (salary.getHourlyRate() > 0) ? salary.getHourlyRate() : SalaryCalculation.calculateHourlyRate(getEmployeeId());
        } else {
            return SalaryCalculation.calculateHourlyRate(getEmployeeId());
        }
    }*/
    
    public double getHourlyRate() throws IOException {
        return SalaryCalculation.calculateHourlyRate(getEmployeeId());
    }
    
    public double getNetSalary() throws IOException {
        return SalaryCalculation.calculateNetSalary(getEmployeeId(), allowanceReader);
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
    
    
    public void processPayroll(String employeeId) throws IOException {
        SalaryDetailsReader salaryReader = new SalaryDetailsReader("src/data9/Salary.csv");
        PayrollProcessor processor = new PayrollProcessor("src/data9/Payroll.csv", salaryReader);
        processor.processPayroll(employeeId);
    }

    public void viewPayroll(String employeeId) throws IOException {
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
    }
     
    /*public SalaryDetails getSalaryDetails(String employeeId) throws IOException {
        double grossSalary = SalaryCalculation.calculateGrossSalary(employeeId);
        double netSalary = SalaryCalculation.calculateNetSalary(employeeId);
        double hourlyRate = SalaryCalculation.calculateHourlyRate(employeeId);

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
    }*/
    
    public SalaryDetails getSalaryDetails(String employeeId) throws IOException {
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
    }

}

class SalaryCalculation {
        
    public static double calculateHourlyRate(String employeeId) throws IOException {
        SalaryDetailsReader reader = new SalaryDetailsReader("src/data9/Salary.csv");
        Salary salary = reader.getSalary(employeeId);
        double hourlyRate = salary.getHourlyRate();
        if (hourlyRate <= 0.0) {
            double basicSalary = salary.getBasicSalary();
            hourlyRate = basicSalary / 160;
            System.out.println("Warning: Hourly rate missing in CSV. Calculated hourly rate: " + hourlyRate);
        }
        return hourlyRate;
    }

      
    /*public static double calculateGrossSalary(String employeeId) throws IOException {
        SalaryDetailsReader reader = new SalaryDetailsReader("src/data9/Salary.csv");
        Salary salary = reader.getSalary(employeeId);
        double basicSalary = salary.getBasicSalary();
        double riceSubsidy = allowanceReader.getRiceSubsidyAllowance(getEmployeeId());
        double phoneAllowance = allowanceReader.getPhoneAllowance(getEmployeeId());
        double clothingAllowance = allowanceReader.getClothingAllowance(getEmployeeId());
        double totalAllowances = riceSubsidy + phoneAllowance + clothingAllowance;
        return basicSalary + totalAllowances;
    }*/
    
    public static double calculateGrossSalary(String employeeId, AllowanceDetailsReader allowanceReader) throws IOException {
        SalaryDetailsReader reader = new SalaryDetailsReader("src/data9/Salary.csv");
        Salary salary = reader.getSalary(employeeId);
        double basicSalary = salary.getBasicSalary();
        double riceSubsidy = allowanceReader.getRiceSubsidyAllowance(employeeId);
        double phoneAllowance = allowanceReader.getPhoneAllowance(employeeId);
        double clothingAllowance = allowanceReader.getClothingAllowance(employeeId);
        double totalAllowances = riceSubsidy + phoneAllowance + clothingAllowance;
        return basicSalary + totalAllowances;
    }

    /*public static double calculateNetSalary(String employeeId) throws IOException {
        double grossSalary = calculateGrossSalary(employeeId);
        double pagibigDeduction = Deductions.calculatePagibigDeduction();
        double philHealthDeduction = Deductions.calculatePhilHealthDeduction(grossSalary);
        double sssDeduction = Deductions.calculateSSSDeduction(grossSalary);
        double withholdingTax = Deductions.calculateWithholdingTax(grossSalary);
        double totalDeductions = pagibigDeduction + philHealthDeduction + sssDeduction + withholdingTax;
        return grossSalary - totalDeductions;
    }*/
    
    public static double calculateNetSalary(String employeeId, AllowanceDetailsReader allowanceReader) throws IOException {
        double grossSalary = calculateGrossSalary(employeeId, allowanceReader);
        double pagibigDeduction = Deductions.calculatePagibigDeduction();
        double philHealthDeduction = Deductions.calculatePhilHealthDeduction(grossSalary);
        double sssDeduction = Deductions.calculateSSSDeduction(grossSalary);
        double withholdingTax = Deductions.calculateWithholdingTax(grossSalary);
        double totalDeductions = pagibigDeduction + philHealthDeduction + sssDeduction + withholdingTax;
        return grossSalary - totalDeductions;
    }
        
}
