package payroll9;

import data_reader9.AllowanceDetailsReader;
import data_reader9.SalaryDetailsReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;
import motorph9.FinanceUser;

public class PayrollCalculatorService implements PayrollCalculator {

    
    @Override
    public double calculateNetSalary(String employeeId, LocalDateTime startDate, LocalDateTime endDate) throws IOException {

        System.out.println("Employee ID: " + employeeId);
        System.out.println("Allowance File Path: src/data9/Allowance.csv");
        System.out.println("Salary File Path: src/data9/Salary.csv");

        AllowanceDetailsReader allowanceReader = new AllowanceDetailsReader("src/data9/Allowance.csv");
        SalaryDetailsReader salaryReader = new SalaryDetailsReader("src/data9/Salary.csv");

        // Read salary data once
        Map<String, Salary> salaryMap = salaryReader.getAllSalaries();
        Salary salary = salaryMap.get(employeeId);

        // Read allowance data once
        Map<String, Allowance> allowanceMap = allowanceReader.getAllAllowances();
        Allowance allowance = allowanceMap.get(employeeId);

        if (salary == null || allowance == null) {
            System.out.println("Error: Salary or allowance data not found for employee " + employeeId);
            return 0.0; // Or throw an exception
        }

        double basicSalary = salary.getBasicSalary();
        double riceSubsidy = allowance.getRiceSubsidy();
        double phoneAllowance = allowance.getPhoneAllowance();
        double clothingAllowance = allowance.getClothingAllowance();

        System.out.println("Basic Salary: " + basicSalary);
        System.out.println("Rice Subsidy: " + riceSubsidy);
        System.out.println("Phone Allowance: " + phoneAllowance);
        System.out.println("Clothing Allowance: " + clothingAllowance);

        double totalAllowances = riceSubsidy + phoneAllowance + clothingAllowance;
        double grossSalary = basicSalary + totalAllowances;

        double pagibigDeduction = Deductions.calculatePagibigDeduction();
        double philHealthDeduction = Deductions.calculatePhilHealthDeduction(grossSalary);
        double sssDeduction = Deductions.calculateSSSDeduction(grossSalary);
        double withholdingTax = Deductions.calculateWithholdingTax(grossSalary);
        double totalDeductions = pagibigDeduction + philHealthDeduction + sssDeduction + withholdingTax;

        System.out.println("Gross Salary: " + grossSalary);
        System.out.println("Total Deductions: " + totalDeductions);

        return grossSalary - totalDeductions;
    }
}