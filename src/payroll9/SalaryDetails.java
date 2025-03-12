package payroll9;

/**
 *
 * @author Shekinah Jabez
 */
public record SalaryDetails(
    double grossSalary, double netSalary, double hourlyRate,
    double riceSubsidy, double phoneAllowance, double clothingAllowance, double totalAllowances,
    double pagibigDeduction, double philHealthDeduction, double sssDeduction, double withholdingTax, double totalDeductions
) {}

