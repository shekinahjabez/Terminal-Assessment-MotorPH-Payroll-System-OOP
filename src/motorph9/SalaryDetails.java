/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package motorph9;

/**
 *
 * @author Shekinah Jabez
 */
public record SalaryDetails(
    double grossSalary, double netSalary, double hourlyRate,
    double riceSubsidy, double phoneAllowance, double clothingAllowance, double totalAllowances,
    double pagibigDeduction, double philHealthDeduction, double sssDeduction, double withholdingTax, double totalDeductions
) {}

