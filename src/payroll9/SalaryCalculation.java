package payroll9;

import data_reader9.AllowanceDetailsReader;
import data_reader9.SalaryDetailsReader;
import java.io.IOException;
import java.util.logging.Logger;

public class SalaryCalculation {
    private static final Logger LOGGER = Logger.getLogger(SalaryCalculation.class.getName());

    public static double calculateHourlyRate(String employeeId, SalaryDetailsReader reader) throws IOException {
        try {
            Salary salary = reader.getSalary(employeeId);
            if (salary == null) {
                LOGGER.warning("Salary data not found for employee ID: " + employeeId);
                return 0.0;
            }
            double hourlyRate = salary.getHourlyRate();
            if (hourlyRate <= 0.0) {
                double basicSalary = salary.getBasicSalary();
                hourlyRate = basicSalary / 160;
                LOGGER.warning("Hourly rate missing in CSV. Calculated hourly rate: " + hourlyRate);
            }
            return hourlyRate;
        } catch (NumberFormatException e) {
            LOGGER.severe("Error parsing salary data: " + e.getMessage());
            throw new IOException("Error parsing salary data", e);
        }
    }

    public static double calculateGrossSalary(String employeeId, AllowanceDetailsReader allowanceReader, SalaryDetailsReader reader) throws IOException {
        try {
            Salary salary = reader.getSalary(employeeId);
            if (salary == null) {
                LOGGER.warning("Salary data not found for employee ID: " + employeeId);
                return 0.0;
            }
            double basicSalary = salary.getBasicSalary();
            double riceSubsidy = allowanceReader.getRiceSubsidyAllowance(employeeId);
            double phoneAllowance = allowanceReader.getPhoneAllowance(employeeId);
            double clothingAllowance = allowanceReader.getClothingAllowance(employeeId);
            double totalAllowances = riceSubsidy + phoneAllowance + clothingAllowance;
            return basicSalary + totalAllowances;
        } catch (NumberFormatException e) {
            LOGGER.severe("Error parsing allowance or salary data: " + e.getMessage());
            throw new IOException("Error parsing allowance or salary data", e);
        }
    }

    public static double calculateNetSalary(String employeeId, AllowanceDetailsReader allowanceReader, SalaryDetailsReader reader) throws IOException {
        try {
            double grossSalary = calculateGrossSalary(employeeId, allowanceReader, reader);
            Deductions deductions = new Deductions();
            double pagibigDeduction = deductions.calculatePagibigDeduction();
            double philHealthDeduction = deductions.calculatePhilHealthDeduction(grossSalary);
            double sssDeduction = deductions.calculateSSSDeduction(grossSalary);
            double withholdingTax = deductions.calculateWithholdingTax(grossSalary);
            double totalDeductions = pagibigDeduction + philHealthDeduction + sssDeduction + withholdingTax;
            return grossSalary - totalDeductions;
        } catch (NumberFormatException e) {
            LOGGER.severe("Error in deduction calculations: " + e.getMessage());
            throw new IOException("Error in deduction calculations", e);
        }
    }
}