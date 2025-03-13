package payroll9;

import java.time.LocalDateTime;

public class PayrollData {
    private String employeeNumber;
    private String fullName;
    private String sssNumber;
    private String philHealthNumber;
    private String tinNumber;
    private String pagibigNumber;
    private double totalAllowances;
    private double totalDeductions;
    private double grossSalary;
    private double netSalary;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    // Constructors, getters, and setters
    public PayrollData(){}
    public PayrollData(String employeeNumber, String fullName, String sssNumber, String philHealthNumber,
                       String tinNumber, String pagibigNumber, double totalAllowances, double totalDeductions,
                       double grossSalary, double netSalary) {
        this.employeeNumber = employeeNumber;
        this.fullName = fullName;
        this.sssNumber = sssNumber;
        this.philHealthNumber = philHealthNumber;
        this.tinNumber = tinNumber;
        this.pagibigNumber = pagibigNumber;
        this.totalAllowances = totalAllowances;
        this.totalDeductions = totalDeductions;
        this.grossSalary = grossSalary;
        this.netSalary = netSalary;
    }

    public String getEmployeeNumber() {
        return employeeNumber;
    }

    public void setEmployeeNumber(String employeeNumber) {
        this.employeeNumber = employeeNumber;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getSssNumber() {
        return sssNumber;
    }

    public void setSssNumber(String sssNumber) {
        this.sssNumber = sssNumber;
    }

    public String getPhilHealthNumber() {
        return philHealthNumber;
    }

    public void setPhilHealthNumber(String philHealthNumber) {
        this.philHealthNumber = philHealthNumber;
    }

    public String getTinNumber() {
        return tinNumber;
    }

    public void setTinNumber(String tinNumber) {
        this.tinNumber = tinNumber;
    }

    public String getPagibigNumber() {
        return pagibigNumber;
    }

    public void setPagibigNumber(String pagibigNumber) {
        this.pagibigNumber = pagibigNumber;
    }

    public double getTotalAllowances() {
        return totalAllowances;
    }

    public void setTotalAllowances(double totalAllowances) {
        this.totalAllowances = totalAllowances;
    }

    public double getTotalDeductions() {
        return totalDeductions;
    }

    public void setTotalDeductions(double totalDeductions) {
        this.totalDeductions = totalDeductions;
    }

    public double getGrossSalary() {
        return grossSalary;
    }

    public void setGrossSalary(double grossSalary) {
        this.grossSalary = grossSalary;
    }

    public double getNetSalary() {
        return netSalary;
    }

    public void setNetSalary(double netSalary) {
        this.netSalary = netSalary;
    }
    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }
}