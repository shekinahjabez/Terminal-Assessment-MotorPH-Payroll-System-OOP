/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package motorph9;

import data_reader9.AllowanceDetailsReader;
import data_reader9.CSVReader;
import data_reader9.SalaryDetailsReader;
import java.io.IOException;

/**
 *
 * @author Shekinah Jabez
 */
public class FinanceUser extends User {
    public FinanceUser(String employeeId, String lastName, String firstName, String birthday, 
                        String address, int phone, String sssNumber, String philhealthNumber, 
                        String tinNumber, String pagibigNumber, String status, 
                        String position, String supervisor) {
        super(employeeId, lastName, firstName, birthday, address, phone, 
              sssNumber, philhealthNumber, tinNumber, pagibigNumber, status, 
              position, supervisor, "Finance"); // Pass userType as "Finance"
    }
    
    /*public FinanceUser(String employeeId, String username, String roleName, String password, String firstName, String lastName) {
        super(employeeId, username, roleName, password, firstName, lastName);
    }*/    
    
    public FinanceUser(String employeeId, String username, String roleName, String password, String firstName, String lastName, String changePassword) {
        super(employeeId, username, roleName, password, firstName, lastName, changePassword);
        
    }

    
    public double getHourlyRate() throws IOException {
        double hourlyRate = SalaryDetailsReader.getHourlyRate(getEmployeeId());
        return (hourlyRate > 0) ? hourlyRate : SalaryCalculation.calculateHourlyRate(getEmployeeId());
    }
    
    public double getNetSalary() throws IOException {
        return SalaryCalculation.calculateNetSalary(getEmployeeId());
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
        PayrollProcessor.processPayroll(employeeId);
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
    
    public SalaryDetails getSalaryDetails(String employeeId) throws IOException {
        double grossSalary = SalaryCalculation.calculateGrossSalary(employeeId);
        double netSalary = SalaryCalculation.calculateNetSalary(employeeId);
        double hourlyRate = SalaryCalculation.calculateHourlyRate(employeeId);

        double riceSubsidy = AllowanceDetailsReader.getRiceSubsidyAllowance(employeeId);
        double phoneAllowance = AllowanceDetailsReader.getPhoneAllowance(employeeId);
        double clothingAllowance = AllowanceDetailsReader.getClothingAllowance(employeeId);
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

    /*public record SalaryDetails(
        double grossSalary, double netSalary, double hourlyRate,
        double riceSubsidy, double phoneAllowance, double clothingAllowance, double totalAllowances,
        double pagibigDeduction, double philHealthDeduction, double sssDeduction, double withholdingTax, double totalDeductions
    ) {}*/
}

class Deductions {
    public static double calculatePagibigDeduction() {
        return 100.0; // fixed Pagibig deduction of 100
    }
    
    public static double calculatePhilHealthDeduction(double basicSalary) {
        return (basicSalary * 0.03) / 2;
    }
    
    public static double calculateSSSDeduction(double basicSalary) {
        double[] thresholds = {19750, 20250, 20750, 21250, 21750, 22250, 22750, 23250, 23750, 24250};
        double[] deductions = {900.00, 922.50, 945.00, 967.50, 990.00, 1012.50, 1035.00, 1057.50, 1080.00, 1102.50};

        for (int i = 0; i < thresholds.length; i++) {
            if (basicSalary >= thresholds[i] && (i == thresholds.length - 1 || basicSalary < thresholds[i + 1])) {
                return deductions[i];
            }
        }
         // Default deduction if basicSalary exceeds all thresholds
        var s = 1125.00;
        return s;
    }
    
    public static double calculateWithholdingTax(double netSalary) {
        double[] thresholds = {20832, 33333, 66667, 166667, 666667};
        double[] rates = {0.20, 0.25, 0.30, 0.32, 0.35};
        double[] baseTaxes = {0, 2500, 10833, 40833.33, 200833.33};

        double withholdingTax = 0;
        for (int i = 0; i < thresholds.length; i++) {
            if (netSalary > thresholds[i]) {
                withholdingTax = baseTaxes[i] + (netSalary - thresholds[i]) * rates[i];
            } else {
                break;
            }
        }
        return withholdingTax;
    }
}

class Allowances {
    public static double getTotalAllowance(String employeeId) throws IOException {
        return AllowanceDetailsReader.getRiceSubsidyAllowance(employeeId) +
               AllowanceDetailsReader.getPhoneAllowance(employeeId) +
               AllowanceDetailsReader.getClothingAllowance(employeeId);
    }
}


/*class Allowances {
    public static double calculateClothingAllowance() {
        return 500.0; // Placeholder value
    }
    
    public static double calculateRiceSubsidyAllowance() {
        return 1500.0; // Placeholder value
    }
    
    public static double calculatePhoneAllowance() {
        return 800.0; // Placeholder value
    }
    
    public static double calculateTotalAllowance() {
        return calculateClothingAllowance() + calculateRiceSubsidyAllowance() + calculatePhoneAllowance();
    }

    static double getTotalAllowance(String employeeId) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}*/

class SalaryCalculation {
    /*public static double calculateHourlyRate(String employeeId) throws IOException {
        double basicSalary = SalaryDetailsReader.getBasicSalary(employeeId);
        return basicSalary / 160; // Assuming 160 work hours per month
    }*/
    
    public static double calculateHourlyRate(String employeeId) throws IOException {
        double hourlyRate = SalaryDetailsReader.getHourlyRate(employeeId); // Fetch from CSV

        // If CSV does not have an hourly rate (0.0 or missing), fall back to calculation
        if (hourlyRate <= 0.0) {
            double basicSalary = SalaryDetailsReader.getBasicSalary(employeeId);
            hourlyRate = basicSalary / 160; // ✅ Only calculate if missing from CSV
            System.out.println("Warning: Hourly rate missing in CSV. Calculated hourly rate: " + hourlyRate);
        }

        return hourlyRate;
    }

    
    public static double calculateGrossSalary(String employeeId) throws IOException {
        double basicSalary = SalaryDetailsReader.getBasicSalary(employeeId);
        double riceSubsidy = AllowanceDetailsReader.getRiceSubsidyAllowance(employeeId);
        double phoneAllowance = AllowanceDetailsReader.getPhoneAllowance(employeeId);
        double clothingAllowance = AllowanceDetailsReader.getClothingAllowance(employeeId);
        double totalAllowances = riceSubsidy + phoneAllowance + clothingAllowance;
        return basicSalary + totalAllowances;
    }

    public static double calculateNetSalary(String employeeId) throws IOException {
        double grossSalary = calculateGrossSalary(employeeId);
        double pagibigDeduction = Deductions.calculatePagibigDeduction();
        double philHealthDeduction = Deductions.calculatePhilHealthDeduction(grossSalary);
        double sssDeduction = Deductions.calculateSSSDeduction(grossSalary);
        double withholdingTax = Deductions.calculateWithholdingTax(grossSalary);
        double totalDeductions = pagibigDeduction + philHealthDeduction + sssDeduction + withholdingTax;
        return grossSalary - totalDeductions;
    }
        
    /*public static double calculateGrossSalary(String employeeId) throws IOException {
        double basicSalary = SalaryDetailsReader.getBasicSalary(employeeId);
        return basicSalary + Allowances.getTotalAllowance(employeeId);
    }

    public static double calculateNetSalary(String employeeId) throws IOException {
        double basicSalary = SalaryDetailsReader.getBasicSalary(employeeId);
        double grossSalary = calculateGrossSalary(employeeId);
        double totalDeductions = Deductions.calculatePagibigDeduction() +
                                 Deductions.calculatePhilHealthDeduction(basicSalary) +
                                 Deductions.calculateSSSDeduction(basicSalary) +
                                 Deductions.calculateWithholdingTax(grossSalary);
        return grossSalary - totalDeductions;
    }*/
}
