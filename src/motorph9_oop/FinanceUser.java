/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package motorph9_oop;

import java.io.IOException;

/**
 *
 * @author Shekinah Jabez
 */
public class FinanceUser extends User {
    public FinanceUser(String employeeId, String username, String firstName, String userType) {
        super(employeeId, username, firstName, userType);
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
    
    public String processPayroll() {
        return "Payroll processed successfully";
    }
    
}

class Deductions {
    public static double calculatePagibigDeduction() {
        return 100.0; // Placeholder value
    }
    
    public static double calculatePhilHealthDeduction(double basicSalary) {
        return (basicSalary * 0.03) / 2;
    }
    
    public static double calculateSSSDeduction(double basicSalary) {
        return basicSalary * 0.045; // Example calculation
    }
    
    public static double calculateWithholdingTax(double netSalary) {
        return netSalary * 0.10; // Example calculation
    }
}

class Allowances {
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

}

class SalaryCalculation {
    public static double calculateHourlyRate(String employeeId) throws IOException {
        double basicSalary = SalaryDetailsReader.getBasicSalary(employeeId);
        return basicSalary / 160; // Assuming 160 work hours per month
    }
    
    public static double calculateGrossSalary(String employeeId) throws IOException {
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
    }
}
