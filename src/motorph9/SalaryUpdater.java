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
        boolean updated = false; // Flag to check if update happened

        System.out.println("Reading file: " + filePath);
        System.out.println("Searching for Employee #: " + employeeNum);

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            header = br.readLine(); // Read and store header
            if (header != null) {
                lines.add(header);
            }

            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",", -1); // Preserve empty values
                System.out.println("CSV row: " + Arrays.toString(values)); // Debugging

                if (values.length >= 4) { // ✅ Change from 8 to 4
                    String fileEmployeeNum = values[0].trim().replaceAll("[^0-9]", ""); // Remove spaces & non-numeric chars

                    System.out.println("Checking Employee #: " + fileEmployeeNum + " vs " + employeeNum);

                    if (fileEmployeeNum.equals(String.valueOf(employeeNum))) {
                        values[2] = String.format("%.2f", totalAllowances); // ✅ Adjusted index for Total Allowances
                        values[3] = String.format("%.2f", netSalary); // ✅ Adjusted index for Net Salary

                        updated = true;
                        System.out.println("✅ Employee found! Updating salary.");
                    }
                }
                lines.add(String.join(",", values));
            }

            if (!updated) {
                System.out.println("❌ Employee NOT FOUND: " + employeeNum);
            }

        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
            return;
        }

        if (!updated) {
            System.out.println("Employee not found: " + employeeNum);
            return;
        }

        // Write updated content back to the file
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            for (String l : lines) {
                bw.write(l);
                bw.newLine();
            }
            System.out.println("Salary updated successfully.");
        } catch (IOException e) {
            System.err.println("Error writing file: " + e.getMessage());
        }
    }

    // Safe method to retrieve table value
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

        // Assign selected row values to text fields
        int selectedRow = financeDashboard.getJTableEmployeesList().getSelectedRow();
        if (selectedRow != -1) { // Ensure a row is selected
            allowanceField.setText(getSafeTableValue(financeDashboard.getJTableEmployeesList(), selectedRow, 6));
            deductionField.setText(getSafeTableValue(financeDashboard.getJTableEmployeesList(), selectedRow, 7));
            grossSalaryField.setText(getSafeTableValue(financeDashboard.getJTableEmployeesList(), selectedRow, 8));
            netSalaryField.setText(getSafeTableValue(financeDashboard.getJTableEmployeesList(), selectedRow, 9));
        } else {
            // Clear fields if no row is selected
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

        System.out.println("Current salary details displayed");

        boolean validInput = false;
        while (!validInput) {
            int result = JOptionPane.showConfirmDialog(frame, panel, "Update Salary Details", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                try {
                    double totalAllowances = Double.parseDouble(allowanceField.getText().replaceAll(",", ""));
                    double totalDeductions = Double.parseDouble(deductionField.getText().replaceAll(",", ""));
                    double grossSalary = Double.parseDouble(grossSalaryField.getText().replaceAll(",", ""));
                    double netSalary = Double.parseDouble(netSalaryField.getText().replaceAll(",", ""));

                    System.out.println("OK button clicked");
                    updateSalary(filePath, employeeNum, totalAllowances, totalDeductions, grossSalary, netSalary);
                    refreshTable();
                    validInput = true; // Exit loop after update
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(frame, "Invalid input. Please enter numeric values.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                validInput = true; // Exit if user cancels
            }
        }
    }

    private String[] getCurrentSalaryDetails(String filePath, int employeeNum) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            br.readLine(); // Skip header
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",", -1); // Preserve empty values
                if (values.length >= 8 && values[0].trim().equals(String.valueOf(employeeNum))) {
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
