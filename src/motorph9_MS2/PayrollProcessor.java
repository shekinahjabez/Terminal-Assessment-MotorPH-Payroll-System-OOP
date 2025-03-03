/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package motorph9_MS2;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author Shekinah Jabez
 */
public class PayrollProcessor {
    private static final String FILE_PATH = "src/data9/Payroll.csv";
    
    public static void processPayroll(String employeeId) throws IOException {
        LocalDate today = LocalDate.now();
        int month = today.getMonthValue();
        int year = today.getYear();
        
        double basicSalary = SalaryDetailsReader.getBasicSalary(employeeId);
        double totalAllowance = AllowanceDetailsReader.getRiceSubsidyAllowance(employeeId) +
                                AllowanceDetailsReader.getPhoneAllowance(employeeId) +
                                AllowanceDetailsReader.getClothingAllowance(employeeId);
        double grossSalary = basicSalary + totalAllowance;
        double totalDeductions = Deductions.calculatePagibigDeduction() +
                                 Deductions.calculatePhilHealthDeduction(basicSalary) +
                                 Deductions.calculateSSSDeduction(basicSalary) +
                                 Deductions.calculateWithholdingTax(grossSalary);
        double netMonthlySalary = grossSalary - totalDeductions;
        
        List<String[]> payrollData = CSVReader.readCSV(FILE_PATH);
        payrollData.add(new String[]{employeeId, String.valueOf(month), String.valueOf(year),
                String.valueOf(grossSalary), String.valueOf(totalAllowance),
                String.valueOf(totalDeductions), String.valueOf(netMonthlySalary)});
        
        CSVReader.writeCSV(FILE_PATH, payrollData);
        System.out.println("Payroll processed for Employee ID: " + employeeId);
    }
}

