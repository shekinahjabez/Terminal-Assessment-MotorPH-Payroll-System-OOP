package motorph9;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.io.*;
import java.util.*;
import javax.swing.*;

public class CSVReader {

    private static List<String[]> employeeData;
    private static List<String[]> leaveBalancesData;
    private String filePath; 
    private String leaveBalancesFilePath;
    
    public CSVReader(String filePath) {
        this.filePath = filePath;
        loadCSVData();
    }    

    public void loadLeaveBalances(String leaveBalancesFilePath) {
        this.leaveBalancesFilePath = leaveBalancesFilePath;
        loadLeaveBalancesData();
    }

    public String[] getUserInfo(String userID) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");

                if (values.length >= 21 && values[0].trim().equals(userID)) {
                    return values;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error reading CSV file", "Error", JOptionPane.ERROR_MESSAGE);
        }
        return null;
    }

    public void showCSVInJTable(String filePath) {
        List<List<String>> data = readCSV(filePath);

        DefaultTableModel model = new DefaultTableModel();

        // Add column names
        List<String> columnNames = data.get(0);
        for (String columnName : columnNames) {
            model.addColumn(columnName);
        }

        // Add data rows
        for (int i = 1; i < data.size(); i++) {
            List<String> row = data.get(i);
            model.addRow(row.toArray());
        }

        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        JFrame frame = new JFrame("CSV Viewer");
        frame.add(scrollPane);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private List<List<String>> readCSV(String filePath) {
        List<List<String>> records = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                records.add(new ArrayList<>(List.of(values)));
            }
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error reading CSV file", "Error", JOptionPane.ERROR_MESSAGE);
        }
        return records;
    }
    
    public void loadCSVData() {
        employeeData = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            br.readLine(); // Skip header line
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");

                if (values.length >= 7) {  // Ensure there are enough columns
                    employeeData.add(values);
                } else {
                    System.err.println("Invalid line format: " + line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error reading CSV file", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    // Method to get all employee data
    public List<String[]> getAllEmployeeData() {
        return employeeData;
    }

    // Method to get user information by employee number
    public static String[] getEmployeeInfo(String employeeNo) {
        for (String[] employee : employeeData) {
            if (employee[0].trim().equals(employeeNo)) {
                return employee;
            }
        }
        return null;
    }
    
    // Methods for leave balances CSV
    public void loadLeaveBalancesData() {
        leaveBalancesData = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(leaveBalancesFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                leaveBalancesData.add(parts);
            }
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error reading leave balances CSV file", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public String[] getEmployeeInfoFromLeaveBalances(String employeeNo) {
        for (String[] row : leaveBalancesData) {
            if (row[0].trim().equals(employeeNo)) {
                return row;
            }
        }
        return null;
    }

    public void writeLeaveBalancesCSV(String leaveBalancesFilePath, List<String[]> data) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(leaveBalancesFilePath))) {
            for (String[] row : data) {
                writer.println(String.join(",", row));
            }
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error writing leave balances CSV file", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void main(String[] args) {
        CSVReader csvReader = new CSVReader("src\\motorph9\\EmployeeDetails.csv");
        csvReader.loadLeaveBalances("src\\motorph9\\LeaveBalances.csv");
        csvReader.showCSVInJTable("src\\motorph9\\EmployeeDetails.csv");
    }
}
