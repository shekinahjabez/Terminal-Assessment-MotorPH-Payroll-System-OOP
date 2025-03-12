/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package payroll9;

/**
 *
 * @author Shekinah Jabez
 */
public class Salary {
    private double basicSalary;
    private double hourlyRate;
    private double grossSMRate; // Added grossSMRate

    public Salary(double basicSalary, double hourlyRate, double grossSMRate) {
        this.basicSalary = basicSalary;
        this.hourlyRate = hourlyRate;
        this.grossSMRate = grossSMRate;
    }

    public double getBasicSalary() {
        return basicSalary;
    }

    public double getHourlyRate() {
        return hourlyRate;
    }

    public double getGrossSMRate() {
        return grossSMRate;
    }
}