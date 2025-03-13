package data_reader9;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import payroll9.Salary;

/**
 * @author Shekinah Jabez
 */
public class SalaryDetailsReader {
    private static final Logger LOGGER = Logger.getLogger(SalaryDetailsReader.class.getName());
    private final String filePath;
    private Map<String, Salary> salaryMap = null; // Initialize to null

    public SalaryDetailsReader(String filePath) {
        this.filePath = filePath;
    }

    private void loadSalaries() throws IOException {
        salaryMap = new HashMap<>(); // Create a new map

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            br.readLine(); // Skip header
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length > 0) {
                    String employeeId = data[0].trim();
                    try {
                        Salary salary = new Salary(
                                Double.parseDouble(data[1]),
                                Double.parseDouble(data[2]),
                                Double.parseDouble(data[3])
                        );
                        salaryMap.put(employeeId, salary);
                    } catch (NumberFormatException e) {
                        LOGGER.log(Level.WARNING, "Error parsing salary data: {0}", e.getMessage());
                    }
                }
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error loading salaries from file: {0}", e.getMessage());
            throw e;
        }
    }

    public Salary getSalary(String employeeId) throws IOException {
        if (salaryMap == null) {
            loadSalaries();
        }
        return salaryMap.get(employeeId);
    }

    public Map<String, Salary> getAllSalaries() throws IOException {
        if (salaryMap == null) {
            loadSalaries();
        }
        return salaryMap;
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
            salaryMap = null; // Invalidate cache after update
        } else {
            LOGGER.log(Level.WARNING, "Salary data not found for employee: {0}", employeeId);
        }
    }
}