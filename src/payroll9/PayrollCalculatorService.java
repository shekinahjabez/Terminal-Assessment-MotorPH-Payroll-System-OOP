package payroll9;

import data_reader9.AllowanceDetailsReader;
import data_reader9.SalaryDetailsReader;
import java.io.IOException;
import java.time.LocalDateTime;
import motorph9.FinanceUser;

public class PayrollCalculatorService implements PayrollCalculator {

    @Override
    public double calculateNetSalary(String employeeId, LocalDateTime startDate, LocalDateTime endDate) throws IOException {
        AllowanceDetailsReader allowanceReader = new AllowanceDetailsReader("src/data9/Allowance.csv");
        SalaryDetailsReader salaryReader = new SalaryDetailsReader("src/data9/Salary.csv");
        double basicSalary = salaryReader.getSalary(employeeId).getBasicSalary();
        double riceSubsidy = allowanceReader.getRiceSubsidyAllowance(employeeId);
        double phoneAllowance = allowanceReader.getPhoneAllowance(employeeId);
        double clothingAllowance = allowanceReader.getClothingAllowance(employeeId);
        double totalAllowances = riceSubsidy + phoneAllowance + clothingAllowance;
        double grossSalary = basicSalary + totalAllowances;
        double pagibigDeduction = Deductions.calculatePagibigDeduction();
        double philHealthDeduction = Deductions.calculatePhilHealthDeduction(grossSalary);
        double sssDeduction = Deductions.calculateSSSDeduction(grossSalary);
        double withholdingTax = Deductions.calculateWithholdingTax(grossSalary);
        double totalDeductions = pagibigDeduction + philHealthDeduction + sssDeduction + withholdingTax;
        return grossSalary - totalDeductions;
    }
}