package motorph9;

import java.io.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import motorph_GUI.FinanceDashboard;

public class SalaryUpdater {
    private FinanceDashboard financeDashboard;
    
    // Constructor to accept FinanceDashboard instance
    public SalaryUpdater(FinanceDashboard dashboard) {
        this.financeDashboard = dashboard;
    }

    public static void updateSalary(String filePath, int employeeNum, double totalAllowances, double totalDeductions, double grossSalary, double netSalary) {
        List<String> lines = new ArrayList<>();
        String header;
        
        System.out.println("Reading file...");
        System.out.println("Employee number: " + employeeNum);
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            header = br.readLine(); // Read header
            lines.add(header);
            String line;
            boolean updated = false;
            
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length >= 8 && Integer.parseInt(values[0].trim()) == employeeNum) {
                    values[4] = String.valueOf(totalAllowances);
                    values[5] = String.valueOf(totalDeductions);
                    values[6] = String.valueOf(grossSalary);
                    values[7] = String.valueOf(netSalary);
                    updated = true;
                }
                lines.add(String.join(",", values));
            }
            
            if (!updated) {
                System.out.println("Employee not found.");
                return;
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
            return;
        }
        
        System.out.println("Updated content: " + lines);
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            for (String l : lines) {
                bw.write(l);
                bw.newLine();
            }
            System.out.println("Salary updated successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    // Helper Method to Prevent NullPointerException
    private String getTableValue(int row, int col) {
        Object value = financeDashboard.getJTableEmployeesList().getValueAt(row, col);
        return (value != null) ? value.toString().trim() : "";
    }
    
    // Place this method inside the class but outside any other method
    private String getSafeTableValue(JTable table, int row, int col) {
        Object value = table.getValueAt(row, col);
        return (value != null) ? value.toString().trim() : "";
    }

    public void openUpdateForm(String filePath, int employeeNum) {
        JFrame frame = new JFrame("Update Salary Details");
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JTextField allowanceField = new JTextField(10);
        JTextField deductionField = new JTextField(10);
        JTextField grossSalaryField = new JTextField(10);
        JTextField netSalaryField = new JTextField(10);


        // Assign selected row values to text fields (with null safety)
        int selectedRow = financeDashboard.getJTableEmployeesList().getSelectedRow();
        if (selectedRow != -1) { // Ensure a row is selected before accessing data
            allowanceField.setText(getSafeTableValue(financeDashboard.getJTableEmployeesList(), selectedRow, 6));
            deductionField.setText(getSafeTableValue(financeDashboard.getJTableEmployeesList(), selectedRow, 7));
            grossSalaryField.setText(getSafeTableValue(financeDashboard.getJTableEmployeesList(), selectedRow, 8));
            netSalaryField.setText(getSafeTableValue(financeDashboard.getJTableEmployeesList(), selectedRow, 9));
        } else {
            // Optional: Clear fields if no row is selected
            allowanceField.setText("");
            deductionField.setText("");
            grossSalaryField.setText("");
            netSalaryField.setText("");
        }

        panel.add(new JLabel("Total Allowances:"));
        panel.add(allowanceField);
        panel.add(new JLabel("Total Deductions:"));
        panel.add(deductionField);
        panel.add(new JLabel("Gross Salary:"));
        panel.add(grossSalaryField);
        panel.add(new JLabel("Net Salary:"));
        panel.add(netSalaryField);

        boolean validInput = false;

        while (!validInput) {
            int result = JOptionPane.showConfirmDialog(frame, panel, "Update Salary Details", JOptionPane.OK_CANCEL_OPTION);

            if (result == JOptionPane.OK_OPTION) {
                try {
                    double totalAllowances = Double.parseDouble(allowanceField.getText().replaceAll(",", ""));
                    double totalDeductions = Double.parseDouble(deductionField.getText().replaceAll(",", ""));
                    double grossSalary = Double.parseDouble(grossSalaryField.getText().replaceAll(",", ""));
                    double netSalary = Double.parseDouble(netSalaryField.getText().replaceAll(",", ""));

                    updateSalary(filePath, employeeNum, totalAllowances, totalDeductions, grossSalary, netSalary);
                    refreshTable();
                    validInput = true; // Exit the loop after successful update
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(frame, "Invalid input. Please enter numeric values.", "Error", JOptionPane.ERROR_MESSAGE);
                    // The loop will continue, redisplaying the form
                }
            } else {
                // If user cancels, exit the loop
                validInput = true;
            }
        }

    }
    
    private String[] getCurrentSalaryDetails(String filePath, int employeeNum) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            br.readLine(); // Skip header
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length >= 8 && Integer.parseInt(values[0].trim()) == employeeNum) {
                    return new String[]{values[4].trim(), values[5].trim(), values[6].trim(), values[7].trim()};
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    private void refreshTable() {
        if (financeDashboard != null) {
            DefaultTableModel model = (DefaultTableModel) financeDashboard.getJTableEmployeesList().getModel();
            model.setRowCount(0); // Clear table
            financeDashboard.loadEmployeeData(); // Reload data
        }
    }
}