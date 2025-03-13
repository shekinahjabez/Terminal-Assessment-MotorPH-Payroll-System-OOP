package data_reader9;

/**
 *
 * @author Shekinah Jabez
 */
import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import payroll9.Allowance;

public class AllowanceDetailsReader {
    private static final Logger LOGGER = Logger.getLogger(AllowanceDetailsReader.class.getName());
    private String filePath;
    private Map<String, Allowance> allowanceMap;

    
    public AllowanceDetailsReader(String filePath) {
        this.filePath = filePath;
        this.allowanceMap = null;
    }


    public double getRiceSubsidyAllowance(String employeeId) throws IOException {
        return getAllowance(employeeId, 1); // Column index for Rice Subsidy
    }

    public double getPhoneAllowance(String employeeId) throws IOException {
        return getAllowance(employeeId, 2); // Column index for Phone Allowance
    }

    public double getClothingAllowance(String employeeId) throws IOException {
        return getAllowance(employeeId, 3); // Column index for Clothing Allowance
    }

    /*private double getAllowance(String employeeId, int columnIndex) throws IOException {
        List<String[]> dataList = CSVReader.readCSV(filePath); // Use static CSVReader
        if (dataList == null) {
            return 0.0;
        }
        for (String[] data : dataList) {
            if (data.length > 0 && data[0].equals(employeeId) && data.length > columnIndex) {
                try {
                    return Double.parseDouble(data[columnIndex]);
                } catch (NumberFormatException e) {
                    System.err.println("Error parsing allowance for employee " + employeeId + " at column " + columnIndex + ": " + data[columnIndex]);
                    e.printStackTrace();
                    return 0.0;
                }
            }
        }
        return 0.0;
    }*/
    
    /*private double getAllowance(String employeeId, int columnIndex) throws IOException {
        System.out.println("Allowance File Path: " + filePath);
        System.out.println("Employee ID (getAllowance): " + employeeId);

        List<String[]> dataList = CSVReader.readCSV(filePath);
        if (dataList == null) {
            System.out.println("dataList is null.");
            return 0.0;
        }

        System.out.println("dataList size: " + dataList.size());
        if (!dataList.isEmpty()) {
            System.out.println("First row: " + java.util.Arrays.toString(dataList.get(0)));
        }

        for (String[] data : dataList) {
            if (data.length > 0 && data[0].equals(employeeId) && data.length > columnIndex) {
                System.out.println("Found matching employee ID: " + employeeId);
                System.out.println("Data at column " + columnIndex + ": " + data[columnIndex]);
                try {
                    return Double.parseDouble(data[columnIndex]);
                } catch (NumberFormatException e) {
                    System.err.println("Error parsing allowance for employee " + employeeId + " at column " + columnIndex + ": " + data[columnIndex]);
                    e.printStackTrace();
                    return 0.0;
                }
            }
        }
        System.out.println("Employee ID not found: " + employeeId);
        return 0.0;
    }
    
    public Map<String, Allowance> getAllAllowances() {
        Map<String, Allowance> allowanceMap = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            br.readLine(); // Skip header
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length == 4) {
                    String employeeNum = values[0];
                    double riceSubsidy = Double.parseDouble(values[1]);
                    double phoneAllowance = Double.parseDouble(values[2]);
                    double clothingAllowance = Double.parseDouble(values[3]);
                    Allowance allowance = new Allowance(riceSubsidy, phoneAllowance, clothingAllowance);
                    allowanceMap.put(employeeNum, allowance);
                } else {
                    LOGGER.warning("Invalid allowance data: " + line);
                }
            }
        } catch (IOException | NumberFormatException e) {
            LOGGER.severe("Error reading allowances: " + e.getMessage());
            e.printStackTrace();
        }
        return allowanceMap;
    }*/
    
    public Map<String, Allowance> getAllAllowances() {
        if (allowanceMap == null) { // Check cache
            allowanceMap = new HashMap<>();
            try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
                String line;
                br.readLine(); // Skip header
                while ((line = br.readLine()) != null) {
                    String[] values = line.split(",");
                    if (values.length == 4) {
                        String employeeNum = values[0];
                        double riceSubsidy = Double.parseDouble(values[1]);
                        double phoneAllowance = Double.parseDouble(values[2]);
                        double clothingAllowance = Double.parseDouble(values[3]);
                        Allowance allowance = new Allowance(riceSubsidy, phoneAllowance, clothingAllowance);
                        allowanceMap.put(employeeNum, allowance);
                    } else {
                        LOGGER.warning("Invalid allowance data: " + line);
                    }
                }
            } catch (IOException | NumberFormatException e) {
                LOGGER.severe("Error reading allowances: " + e.getMessage());
                e.printStackTrace();
            }
        }
        return allowanceMap;
    }

    private double getAllowance(String employeeId, int columnIndex) {
        Map<String, Allowance> allowances = getAllAllowances(); // Use cached data
        Allowance allowance = allowances.get(employeeId);
        if (allowance == null) {
            return 0.0;
        }
        switch (columnIndex) {
            case 1:
                return allowance.getRiceSubsidy();
            case 2:
                return allowance.getPhoneAllowance();
            case 3:
                return allowance.getClothingAllowance();
            default:
                return 0.0;
        }
    }
}
