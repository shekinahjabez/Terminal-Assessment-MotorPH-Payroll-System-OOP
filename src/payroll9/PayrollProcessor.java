package payroll9;

//import motorph9.Deductions;
import data_reader9.AllowanceDetailsReader;
import data_reader9.CSVReader;
import data_reader9.SalaryDetailsReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import motorph9.FinanceUser;

/**
 *
 * @author Shekinah Jabez
 */
public class PayrollProcessor {
    private final String filePath;
    private final SalaryDetailsReader salaryReader;
    private AllowanceDetailsReader allowanceReader = new AllowanceDetailsReader("src/data9/Allowance.csv");
    //private static final String FILE_PATH = "src/data9/Payroll.csv";
    
    public PayrollProcessor(String filePath, SalaryDetailsReader salaryReader) {
        this.filePath = filePath;
        this.salaryReader = salaryReader;
    }
        
    /*public void processPayroll(String employeeId) throws IOException {
        LocalDate today = LocalDate.now();
        int month = today.getMonthValue();
        int year = today.getYear();

        Salary salary = salaryReader.getSalary(employeeId);
        double basicSalary = salary.getBasicSalary();

        double totalAllowance = AllowanceDetailsReader.getRiceSubsidyAllowance(employeeId) +
                AllowanceDetailsReader.getPhoneAllowance(employeeId) +
                AllowanceDetailsReader.getClothingAllowance(employeeId);
        double grossSalary = basicSalary + totalAllowance;

        //Deductions deductions = new Deductions();
        double totalDeductions = payroll9.Deductions.calculatePagibigDeduction() +
            payroll9.Deductions.calculatePhilHealthDeduction(basicSalary) +
            payroll9.Deductions.calculateSSSDeduction(basicSalary) +
            payroll9.Deductions.calculateWithholdingTax(grossSalary);
        double netMonthlySalary = grossSalary - totalDeductions;

        List<String[]> payrollData = CSVReader.readCSV(filePath);
        payrollData.add(new String[]{employeeId, String.valueOf(month), String.valueOf(year),
                String.valueOf(grossSalary), String.valueOf(totalAllowance),
                String.valueOf(totalDeductions), String.valueOf(netMonthlySalary)});

        CSVReader.writeCSV(filePath, payrollData);
        System.out.println("Payroll processed for Employee ID: " + employeeId);
    }*/
    
    public void processPayroll(String employeeId) throws IOException {
        LocalDate today = LocalDate.now();
        int month = today.getMonthValue();
        int year = today.getYear();

        Salary salary = salaryReader.getSalary(employeeId);
        double basicSalary = salary.getBasicSalary();

        double totalAllowance = allowanceReader.getRiceSubsidyAllowance(employeeId) +
                allowanceReader.getPhoneAllowance(employeeId) +
                allowanceReader.getClothingAllowance(employeeId);
        double grossSalary = basicSalary + totalAllowance;

        double totalDeductions = Deductions.calculatePagibigDeduction() +
                Deductions.calculatePhilHealthDeduction(basicSalary) +
                Deductions.calculateSSSDeduction(basicSalary) +
                Deductions.calculateWithholdingTax(grossSalary);
        double netMonthlySalary = grossSalary - totalDeductions;

        List<String[]> payrollData = CSVReader.readCSV(filePath);
        if(payrollData == null){
            payrollData = new ArrayList<>();
        }

        payrollData.add(new String[]{employeeId, String.valueOf(month), String.valueOf(year),
                String.valueOf(grossSalary), String.valueOf(totalAllowance),
                String.valueOf(totalDeductions), String.valueOf(netMonthlySalary)});

        CSVReader.writeCSV(filePath, payrollData);
        System.out.println("Payroll processed for Employee ID: " + employeeId);
    }

}
    

