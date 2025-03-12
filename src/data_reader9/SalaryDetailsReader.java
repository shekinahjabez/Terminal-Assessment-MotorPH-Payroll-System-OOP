package data_reader9;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.logging.Logger;
import payroll9.Salary;

/**
 *
 * @author Shekinah Jabez
 */
public class SalaryDetailsReader {
    private static final Logger LOGGER = Logger.getLogger(SalaryDetailsReader.class.getName());
    private final String filePath;
    private static final String FILE_PATH = "src/data9/Salary.csv";
    
    public SalaryDetailsReader(String filePath) {
        this.filePath = filePath;
    }

    /*public static double getBasicSalary(String employeeId) throws IOException {
        for (String[] data : CSVReader.readCSV(FILE_PATH)) {
            if (data[0].equals(employeeId)) {
                return Double.parseDouble(data[1]);
            }
        }
        return 0.0;
    }

    public static double getHourlyRate(String employeeId) throws IOException {
        for (String[] data : CSVReader.readCSV(FILE_PATH)) {
            if (data[0].equals(employeeId)) {
                return Double.parseDouble(data[2]);
            }
        }
        return 0.0;
    }

    public static void updateSalary(String employeeId, double newSalary) throws IOException {
        List<String[]> salaries = CSVReader.readCSV(FILE_PATH);
        for (String[] data : salaries) {
            if (data[0].equals(employeeId)) {
                data[1] = String.valueOf(newSalary);
                break;
            }
        }
        CSVReader.writeCSV(FILE_PATH, salaries);
    }*/
    
    public Salary getSalary(String employeeId) throws IOException {
        try {
            for (String[] data : CSVReader.readCSV(filePath)) {
                if (data[0].equals(employeeId)) {
                    double basicSalary = Double.parseDouble(data[1]);
                    double hourlyRate = Double.parseDouble(data[2]);
                    double grossSMRate = Double.parseDouble(data[3]);
                    return new Salary(basicSalary, hourlyRate, grossSMRate);
                }
            }
            LOGGER.warning("Salary data not found for employee: " + employeeId);
            return null; // Or throw a custom exception
        } catch (NumberFormatException e) {
            LOGGER.severe("Error parsing salary data: " + e.getMessage());
            throw new IOException("Error parsing salary data", e);
        }
    }

    public void updateSalary(String employeeId, Salary newSalary) throws IOException {
        List<String[]> salaries = CSVReader.readCSV(filePath);
        List<String[]> updatedSalaries = new ArrayList<>();
        boolean updated = false;

        for (String[] data : salaries) {
            if (data[0].equals(employeeId)) {
                data[1] = String.valueOf(newSalary.getBasicSalary());
                data[2] = String.valueOf(newSalary.getHourlyRate());
                data[3] = String.valueOf(newSalary.getGrossSMRate());
                updated = true;
            }
            updatedSalaries.add(data);
        }

        if (updated) {
            CSVReader.writeCSV(filePath, updatedSalaries);
        } else {
            LOGGER.warning("Salary data not found for employee: " + employeeId);
        }
    }
}
