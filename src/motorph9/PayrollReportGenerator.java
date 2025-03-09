/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package motorph9;

import data_reader9.CSVReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author Shekinah Jabez
 */
public class PayrollReportGenerator {
    private static final String FILE_PATH = "src/data9/Payroll.csv";
    private static final String REPORT_FILE = "src/data9/PayrollReport.csv";
    
    public static void generateReport() throws IOException {
        List<String[]> payrollData = CSVReader.readCSV(FILE_PATH);
        
        System.out.println("\n========= Payroll Report =========");
        System.out.printf("%-12s %-8s %-6s %-12s %-12s %-15s %-15s\n",
                "Employee ID", "Month", "Year", "Gross Salary", "Total Allowance", "Total Deductions", "Net Salary");
        System.out.println("------------------------------------------------------------------------------------");
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(REPORT_FILE))) {
            writer.write("Employee ID,Month,Year,Gross Salary,Total Allowance,Total Deductions,Net Salary\n");
            
            for (String[] data : payrollData) {
                System.out.printf("%-12s %-8s %-6s %-12s %-12s %-15s %-15s\n",
                        data[0], data[1], data[2], data[3], data[4], data[5], data[6]);
                writer.write(String.join(",", data) + "\n");
            }
        }
        
        System.out.println("===============================================\n");
        System.out.println("Payroll report saved to: " + REPORT_FILE);
    }
}

