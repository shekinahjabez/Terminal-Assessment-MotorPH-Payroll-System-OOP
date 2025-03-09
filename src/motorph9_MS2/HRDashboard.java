/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

/*to do mark:

Leave: check if you can set the spacing per COlumns

*Done:
fix Add Employee: Save but disposed if there's error
restricted UPdate & Adding of dates 
fixed UPdateEmployee's "No changes were made." this prompt's dispose() after.
move Welcome
fix default color of Leave button
improve Save, Cancel, and OK Buttons
corrected jdateChooser UX


*/

package motorph9_MS2;
import data_reader9.EmployeeDetailsReader;
import data_reader9.LeaveRequestReader;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.Timer;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.*;
import java.io.IOException;
import java.util.List;
import java.util.regex.Pattern;
import javax.swing.table.DefaultTableModel;
import com.toedter.calendar.JDateChooser;
import java.awt.Font;
import java.util.Calendar;

public class HRDashboard extends javax.swing.JFrame { 
    private Timer timer;
    private HRUser hrUser;
    private EmployeeDetailsReader employeeReader;
    private LeaveRequestReader leaveRequestReader;

    // Swing components for UpdateEmployee - Manage Employee
    private JTextField txtEmployeeID = new JTextField();
    private JTextField txtLastName = new JTextField();
    private JTextField txtFirstName = new JTextField();
    private JDateChooser chooserBirthday = new JDateChooser();

    private JTextField txtAddress = new JTextField();
    private JTextField txtPhoneNumber = new JTextField();
    private JTextField txtSSSNumber = new JTextField();
    private JTextField txtPhilHealthNumber = new JTextField();
    private JTextField txtTINNumber = new JTextField();
    private JTextField txtPagIbigNumber = new JTextField();
    private JTextField txtStatus = new JTextField();
    private JTextField txtPosition = new JTextField();
    private JTextField txtSupervisor = new JTextField();

    private JButton btnSaveUpdate, btnCancelUpdate, btnSaveAdd, btnCancelAdd;
    
    JDialog dialogUpdateEmployee = new JDialog(this, "Update Employee", true);
    JDialog dialogAddEmployee = new JDialog(this, "Add Employee", true);

   
    public HRDashboard(HRUser hrUser, EmployeeDetailsReader employeeReader, LeaveRequestReader leaveRequestReader) {
        this.hrUser = hrUser;
        this.employeeReader = employeeReader;
        this.leaveRequestReader = leaveRequestReader;
        initComponents();
        setLocationRelativeTo(null);
        
         // ✅ Debugging: Print HRUser details
        System.out.println("HRUser Data: ");
        System.out.println("Username: " + hrUser.getUsername());
        System.out.println("First Name: " + hrUser.getFirstName());
        System.out.println("Last Name: " + hrUser.getLastName());
        
        jLabelGreet.setText("Welcome, " + hrUser.getFirstName() + "!");
        jButtonLeaveRequests.setBackground (new java.awt.Color(0,0,0));
        //setupSearchListener();
        startClock();
        loadEmployeeTable();
        loadEmployeeData();
    }

    private HRDashboard() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    public JTable getjTableEmployeeRecords() {
        return jTableEmployeeRecords;
    }
    
    private void startClock() {
        timer = new Timer(1000, e -> updateTimeAndDate());
        timer.start();
    }
  
    private void updateTimeAndDate() {
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm:ss a");
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, MMMM d, yyyy");
        jLabelTime.setText(timeFormat.format(new Date()));
        jLabelDate.setText(dateFormat.format(new Date()));
    }
    
    public void loadEmployeeTable() {
        if (jTableEmployeeRecords == null) {
            System.err.println("❌ Error: jTableemployeeTable is still null!");
            return;
        }

        DefaultTableModel model = (DefaultTableModel) jTableEmployeeRecords.getModel();
        model.setRowCount(0); // ✅ Clear old data before adding new ones

        try {
            List<EmployeeUser> employees = employeeReader.getAllEmployees(); // ✅ Load employees

            // ✅ Debugging: Print total employees loaded
            System.out.println("Loading employees into table: " + employees.size());

            for (EmployeeUser emp : employees) {
                model.addRow(new Object[]{
                    emp.getEmployeeId(), emp.getLastName(), emp.getFirstName(),
                    emp.getSSS(), emp.getPhilHealth(), emp.getTIN(),
                    emp.getPagibigNumber()
                });
            }

            if (employees.isEmpty()) {
                System.out.println("⚠️ No employees found!");
            }

        } catch (IOException e) {
            System.err.println("❌ Error loading employees: " + e.getMessage());
        }
    }
    
    private void loadEmployeeData() {
        DefaultTableModel modelRecords = (DefaultTableModel) jTableEmployeeRecords.getModel();

        modelRecords.setRowCount(0); // ✅ Clear Employee Records table

        try {
            List<EmployeeUser> employees = EmployeeDetailsReader.getAllEmployees();
            System.out.println("Total Employees Loaded: " + employees.size()); // ✅ Debugging

            for (EmployeeUser emp : employees) {
                Object[] rowData = {
                    emp.getEmployeeNum(),
                    emp.getLastName(),
                    emp.getFirstName(),
                    emp.getBirthday(),
                    emp.getAddress(),
                    emp.getPhone(),
                    emp.getSSS(),
                    emp.getPhilHealth(),
                    emp.getTIN(),
                    emp.getPagibigNumber(),
                    emp.getStatus(),
                    emp.getPosition(),
                    emp.getImmediateSupervisor()
                };

                modelRecords.addRow(rowData); // ✅ Add to Employee Records table
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error loading employee data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    // Method to create a styled JLabel
    private JLabel createStyledLabel(String text, Font font) {
        JLabel label = new JLabel(text);
        label.setFont(font);
        label.setForeground(Color.WHITE); // White text
        return label;
    }

    // Method to create a styled JTextField
    private JTextField createStyledTextField(JTextField textField, Font font) {
        textField.setFont(font);
        textField.setBackground(Color.BLACK);
        textField.setForeground(Color.WHITE);
        return textField;
    }

    // Method to create a styled JButton
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Century Gothic", Font.PLAIN, 13));
        button.setBackground(new Color(204, 0, 51)); // Red background
        button.setForeground(Color.WHITE); // White text
        button.setFocusPainted(false);
        return button;
    }
    
    private JPanel createUpdateEmployeePanel(JDialog dialog) {
        JPanel updateEmpPanel = new JPanel(new GridLayout(15, 2));
        updateEmpPanel.setBackground(new Color(0, 0, 0)); // Set panel background

        Font labelFont = new Font("Century Gothic", Font.PLAIN, 13);

        updateEmpPanel.add(createStyledLabel("Employee ID:", labelFont));
        txtEmployeeID.setEditable(false);
        updateEmpPanel.add(createStyledTextField(txtEmployeeID, labelFont));

        updateEmpPanel.add(createStyledLabel("Last Name:", labelFont));
        updateEmpPanel.add(createStyledTextField(txtLastName, labelFont));

        updateEmpPanel.add(createStyledLabel("First Name:", labelFont));
        updateEmpPanel.add(createStyledTextField(txtFirstName, labelFont));

        updateEmpPanel.add(createStyledLabel("Birthday:", labelFont));
        updateEmpPanel.add(chooserBirthday);
        chooserBirthday.getCalendarButton().setBackground(Color.BLACK);
        chooserBirthday.getDateEditor().getUiComponent().setBackground(Color.BLACK);
        chooserBirthday.getDateEditor().getUiComponent().setForeground(Color.WHITE);


        updateEmpPanel.add(createStyledLabel("Address:", labelFont));
        updateEmpPanel.add(createStyledTextField(txtAddress, labelFont));

        updateEmpPanel.add(createStyledLabel("Phone Number:", labelFont));
        updateEmpPanel.add(createStyledTextField(txtPhoneNumber, labelFont));

        updateEmpPanel.add(createStyledLabel("SSS Number:", labelFont));
        updateEmpPanel.add(createStyledTextField(txtSSSNumber, labelFont));

        updateEmpPanel.add(createStyledLabel("PhilHealth Number:", labelFont));
        updateEmpPanel.add(createStyledTextField(txtPhilHealthNumber, labelFont));

        updateEmpPanel.add(createStyledLabel("TIN Number:", labelFont));
        updateEmpPanel.add(createStyledTextField(txtTINNumber, labelFont));

        updateEmpPanel.add(createStyledLabel("Pag-IBIG Number:", labelFont));
        updateEmpPanel.add(createStyledTextField(txtPagIbigNumber, labelFont));

        updateEmpPanel.add(createStyledLabel("Status:", labelFont));
        updateEmpPanel.add(createStyledTextField(txtStatus, labelFont));

        updateEmpPanel.add(createStyledLabel("Position:", labelFont));
        updateEmpPanel.add(createStyledTextField(txtPosition, labelFont));

        updateEmpPanel.add(createStyledLabel("Supervisor:", labelFont));
        updateEmpPanel.add(createStyledTextField(txtSupervisor, labelFont));

        // Save Button
        btnSaveUpdate = createStyledButton("Save");
        btnSaveUpdate.addActionListener(e -> {
            saveUpdatedEmployee();
        });
        updateEmpPanel.add(btnSaveUpdate);

        // Cancel Button
        btnCancelUpdate = createStyledButton("Cancel");
        btnCancelUpdate.addActionListener(e -> dialog.dispose()); // Close dialog on cancel
        updateEmpPanel.add(btnCancelUpdate);

        return updateEmpPanel;
    }
    
    private JPanel createAddEmployeePanel(JDialog dialog) {
        JPanel addEmpPanel = new JPanel(new GridLayout(15, 2));
        addEmpPanel.setBackground(new Color(0, 0, 0)); // Set panel background

        Font labelFont = new Font("Century Gothic", Font.PLAIN, 13);

        addEmpPanel.add(createStyledLabel("Employee ID:", labelFont));
        txtEmployeeID.setEditable(false);
        addEmpPanel.add(createStyledTextField(txtEmployeeID, labelFont));

        addEmpPanel.add(createStyledLabel("Last Name:", labelFont));
        addEmpPanel.add(createStyledTextField(txtLastName, labelFont));

        addEmpPanel.add(createStyledLabel("First Name:", labelFont));
        addEmpPanel.add(createStyledTextField(txtFirstName, labelFont));

        addEmpPanel.add(createStyledLabel("Birthday:", labelFont));
        addEmpPanel.add(chooserBirthday);
        chooserBirthday.getCalendarButton().setBackground(Color.BLACK);
        chooserBirthday.getDateEditor().getUiComponent().setBackground(Color.BLACK);
        chooserBirthday.getDateEditor().getUiComponent().setForeground(Color.WHITE);

        addEmpPanel.add(createStyledLabel("Address:", labelFont));
        addEmpPanel.add(createStyledTextField(txtAddress, labelFont));

        addEmpPanel.add(createStyledLabel("Phone Number:", labelFont));
        addEmpPanel.add(createStyledTextField(txtPhoneNumber, labelFont));

        addEmpPanel.add(createStyledLabel("SSS Number:", labelFont));
        addEmpPanel.add(createStyledTextField(txtSSSNumber, labelFont));

        addEmpPanel.add(createStyledLabel("PhilHealth Number:", labelFont));
        addEmpPanel.add(createStyledTextField(txtPhilHealthNumber, labelFont));

        addEmpPanel.add(createStyledLabel("TIN Number:", labelFont));
        addEmpPanel.add(createStyledTextField(txtTINNumber, labelFont));

        addEmpPanel.add(createStyledLabel("Pag-IBIG Number:", labelFont));
        addEmpPanel.add(createStyledTextField(txtPagIbigNumber, labelFont));

        addEmpPanel.add(createStyledLabel("Status:", labelFont));
        addEmpPanel.add(createStyledTextField(txtStatus, labelFont));

        addEmpPanel.add(createStyledLabel("Position:", labelFont));
        addEmpPanel.add(createStyledTextField(txtPosition, labelFont));

        addEmpPanel.add(createStyledLabel("Supervisor:", labelFont));
        addEmpPanel.add(createStyledTextField(txtSupervisor, labelFont));

        // Save Button
        btnSaveAdd = createStyledButton("Save");
        btnSaveAdd.addActionListener(e -> {
            saveAddedEmployee();
        });
        addEmpPanel.add(btnSaveAdd);

        // Cancel Button
        btnCancelAdd = createStyledButton("Cancel");
        btnCancelAdd.addActionListener(e -> dialog.dispose()); // Close dialog on cancel
        addEmpPanel.add(btnCancelAdd);

        return addEmpPanel;
    }

    private void updateEmployee() {
    int selectedRow = jTableEmployeeRecords.getSelectedRow();
    if (selectedRow == -1) {
        JOptionPane.showMessageDialog(this, "Select an employee to update.", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    // Debugging: Print row data before assigning to fields
    for (int i = 0; i < jTableEmployeeRecords.getColumnCount(); i++) {
        Object value = jTableEmployeeRecords.getValueAt(selectedRow, i);
        System.out.println("Column " + i + ": " + (value != null ? value.toString() : "null"));
    }

    // Assign selected row values to text fields (with null safety)
    txtEmployeeID.setText(getTableValue(selectedRow, 0));
    txtLastName.setText(getTableValue(selectedRow, 1));
    txtFirstName.setText(getTableValue(selectedRow, 2));

    // Handle Birthday using JDateChooser with 18-year restriction
    try {
        String birthdayStr = getTableValue(selectedRow, 3);
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

        // Calculate the minimum allowed date (18 years ago)
        Calendar minAgeCalendar = Calendar.getInstance();
        minAgeCalendar.add(Calendar.YEAR, -18);
        Date minAllowedDate = minAgeCalendar.getTime(); // User must be born before this date

        chooserBirthday.setSelectableDateRange(null, minAllowedDate); // Restrict future dates

        if (!birthdayStr.isEmpty()) {
            Date parsedDate = sdf.parse(birthdayStr);
            chooserBirthday.setDate(parsedDate);
        } else {
            chooserBirthday.setDate(null); // Set empty if no value
        }

        // Styling JDateChooser (Black background, White text)
        JTextField dateField = (JTextField) chooserBirthday.getDateEditor().getUiComponent();
        dateField.setBackground(Color.BLACK);
        dateField.setForeground(Color.WHITE);
        dateField.setCaretColor(Color.WHITE); // Ensures cursor is visible

        // Prevent text color change on focus
        dateField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                dateField.setForeground(Color.WHITE); // Keep text white on focus
            }

            @Override
            public void focusLost(FocusEvent e) {
                dateField.setForeground(Color.WHITE); // Keep text white when losing focus
            }
        });

    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error parsing birthday date!", "Error", JOptionPane.ERROR_MESSAGE);
    }

    txtAddress.setText(getTableValue(selectedRow, 4));
    txtPhoneNumber.setText(getTableValue(selectedRow, 5));
    txtSSSNumber.setText(getTableValue(selectedRow, 6));
    txtPhilHealthNumber.setText(getTableValue(selectedRow, 7));
    txtTINNumber.setText(getTableValue(selectedRow, 8));
    txtPagIbigNumber.setText(getTableValue(selectedRow, 9));
    txtStatus.setText(getTableValue(selectedRow, 10));
    txtPosition.setText(getTableValue(selectedRow, 11));
    txtSupervisor.setText(getTableValue(selectedRow, 12));

    // Show update panel in a dialog
    dialogUpdateEmployee.setContentPane(createUpdateEmployeePanel(dialogUpdateEmployee));
    dialogUpdateEmployee.pack();
    dialogUpdateEmployee.setLocationRelativeTo(this);
    dialogUpdateEmployee.setVisible(true);
}

    // Helper Method to Prevent NullPointerException
    private String getTableValue(int row, int col) {
        Object value = jTableEmployeeRecords.getValueAt(row, col);
        return (value != null) ? value.toString().trim() : "";
    }

    private void saveUpdatedEmployee() {
        File inputFile = new File("src/data9/Employee.csv");
        File tempFile = new File("src/data9/Employee_temp.csv");

        int selectedRow = jTableEmployeeRecords.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "No employee selected!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String employeeID = jTableEmployeeRecords.getValueAt(selectedRow, 0).toString();
        boolean isModified = false;

        // Validation patterns
        Pattern alphabeticPattern = Pattern.compile("^[A-Za-z ]+$");
        Pattern numeric9Pattern = Pattern.compile("^\\d{9}$"); // Phone Number (9 digits)
        Pattern idPattern = Pattern.compile("^\\d{2}-\\d{7}-\\d$");
        Pattern philhealthPattern = Pattern.compile("^\\d{12}$");
        Pattern tinPattern = Pattern.compile("^\\d{3}-\\d{3}-\\d{3}-\\d{3}$");
        Pattern pagibigPattern = Pattern.compile("^\\d{12}$");

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

            String line;
            boolean found = false;

            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",", -1);

                if (data[0].equals(employeeID)) {
                    found = true;
                    String[] newData = new String[data.length];

                    for (int i = 0; i < data.length && i < 13; i++) {
                        String newValue;

                        if (i == 3) { // Handle Birthday (JDateChooser)
                            if (chooserBirthday.getDate() == null) {
                                JOptionPane.showMessageDialog(this, "Birthday field cannot be empty.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                                return;
                            }

                            Date selectedDate = chooserBirthday.getDate();
                            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

                            Calendar currentCalendar = Calendar.getInstance();
                            Calendar minDateCalendar = Calendar.getInstance();
                            minDateCalendar.add(Calendar.YEAR, -18); // Subtract 18 years from today
                            Date minAllowedDate = minDateCalendar.getTime(); // Minimum valid birthday

                            if (selectedDate.after(currentCalendar.getTime())) {
                                JOptionPane.showMessageDialog(this, "Birthday cannot be a future date.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                                return;
                            }

                            if (selectedDate.after(minAllowedDate)) {
                                String minAllowedDateStr = sdf.format(minAllowedDate);
                                JOptionPane.showMessageDialog(this, "Date selected must not be later than " + minAllowedDateStr + ".", "Validation Error", JOptionPane.ERROR_MESSAGE);
                                return;
                            }

                            newValue = sdf.format(selectedDate);
                        } else {
                            newValue = getTextFieldValue(i);
                        }

                        // Validate inputs
                        if ((i == 1 || i == 2) && !alphabeticPattern.matcher(newValue).matches()) {
                            showValidationError("Invalid Name Format", "Only letters and spaces allowed.", newValue);
                            return;
                        }
                        if (i == 5 && !numeric9Pattern.matcher(newValue).matches()) {
                            showValidationError("Invalid Phone Number", "Must be exactly 9 digits.", newValue);
                            return;
                        }
                        if (i == 6 && !idPattern.matcher(newValue).matches()) {
                            showValidationError("Invalid SSS Number", "Format: XX-XXXXXXX-X", newValue);
                            return;
                        }
                        if (i == 7 && !philhealthPattern.matcher(newValue).matches()) {
                            showValidationError("Invalid PhilHealth Number", "Must be exactly 12 digits.", newValue);
                            return;
                        }
                        if (i == 8 && !tinPattern.matcher(newValue).matches()) {
                            showValidationError("Invalid TIN Number", "Format: XXX-XXX-XXX-XXX", newValue);
                            return;
                        }
                        if (i == 9 && !pagibigPattern.matcher(newValue).matches()) {
                            showValidationError("Invalid Pag-IBIG Number", "Must be exactly 12 digits.", newValue);
                            return;
                        }

                        if (!data[i].equals(newValue)) {
                            isModified = true;
                            newData[i] = newValue;
                        } else {
                            newData[i] = data[i];
                        }
                    }

                    writer.write(String.join(",", newData) + "\n");
                } else {
                    writer.write(line + "\n");
                }
            }

            if (!found) {
                JOptionPane.showMessageDialog(this, "Employee not found in CSV!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error saving employee data!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Replace original file with updated file
        if (inputFile.delete() && tempFile.renameTo(inputFile)) {
            if (isModified) {
                JOptionPane.showMessageDialog(this, "Employee updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                loadEmployeeData();
                dialogUpdateEmployee.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "No changes were made!", "Info", JOptionPane.INFORMATION_MESSAGE);
                dialogUpdateEmployee.dispose();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Error updating file!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }



    private void saveAddedEmployee() {
        File inputFile = new File("src/data9/Employee.csv");

        // Validation patterns
        Pattern employeeIdPattern = Pattern.compile("^\\d{5}$"); // Employee ID (5 digits)
        Pattern alphabeticPattern = Pattern.compile("^[A-Za-z ]+$");  
        Pattern numeric9Pattern = Pattern.compile("^\\d{9}$"); 
        Pattern idPattern = Pattern.compile("^\\d{2}-\\d{7}-\\d$");  
        Pattern philhealthPattern = Pattern.compile("^\\d{12}$");  
        Pattern tinPattern = Pattern.compile("^\\d{3}-\\d{3}-\\d{3}-\\d{3}$");  
        Pattern pagibigPattern = Pattern.compile("^\\d{12}$");  

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(inputFile, true))) {
            String[] newData = new String[13];

            for (int i = 0; i < 13; i++) {
                String newValue;

                if (i == 3) { // Handle Birthday (DateChooser)
                    if (chooserBirthday.getDate() == null) {
                        JOptionPane.showMessageDialog(this, "Birthday field cannot be empty.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
                    Date selectedDate = chooserBirthday.getDate();
                    Calendar cal = Calendar.getInstance();
                    cal.add(Calendar.YEAR, -18); // Calculate 18 years ago
                    Date minAllowedDate = cal.getTime();
                    String formattedMinDate = sdf.format(minAllowedDate);

                    if (selectedDate.after(minAllowedDate)) {
                        JOptionPane.showMessageDialog(this, "Date selected must not be on a later date than " + formattedMinDate + ".", "Validation Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    newValue = sdf.format(selectedDate);
                } else {
                    newValue = getTextFieldValue(i);
                }

                // Validate inputs
                if (i == 0 && !employeeIdPattern.matcher(newValue).matches()) {
                    showValidationError("Invalid Employee ID", "Must be exactly 5 digits.", newValue);
                    return;
                }
                if ((i == 1 || i == 2) && !alphabeticPattern.matcher(newValue).matches()) {
                    showValidationError("Invalid Name Format", "Only letters and spaces allowed.", newValue);
                    return;
                }
                if (i == 5 && !numeric9Pattern.matcher(newValue).matches()) {
                    showValidationError("Invalid Numeric Value", "Must be exactly 9 digits.", newValue);
                    return;
                }
                if (i == 6 && !idPattern.matcher(newValue).matches()) {
                    showValidationError("Invalid SSS Number", "Format: XX-XXXXXXX-X", newValue);
                    return;
                }
                if (i == 7 && !philhealthPattern.matcher(newValue).matches()) {
                    showValidationError("Invalid PhilHealth Number", "Must be exactly 12 digits.", newValue);
                    return;
                }
                if (i == 8 && !tinPattern.matcher(newValue).matches()) {
                    showValidationError("Invalid TIN Number", "Format: XXX-XXX-XXX-XXX", newValue);
                    return;
                }
                if (i == 9 && !pagibigPattern.matcher(newValue).matches()) {
                    showValidationError("Invalid Pag-IBIG Number", "Must be exactly 12 digits.", newValue);
                    return;
                }

                newData[i] = newValue;
            }

            writer.write(String.join(",", newData) + "\n");

        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error saving employee data!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JOptionPane.showMessageDialog(this, "Employee added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        loadEmployeeData();
    }
    
    // Get value from corresponding text field
    private String getTextFieldValue(int index) {
        switch (index) {
            case 0: return txtEmployeeID.getText().trim();
            case 1: return txtLastName.getText().trim();
            case 2: return txtFirstName.getText().trim();
            case 4: return txtAddress.getText().trim();
            case 5: return txtPhoneNumber.getText().trim();
            case 6: return txtSSSNumber.getText().trim();
            case 7: return txtPhilHealthNumber.getText().trim();
            case 8: return txtTINNumber.getText().trim();
            case 9: return txtPagIbigNumber.getText().trim();
            case 10: return txtStatus.getText().trim();
            case 11: return txtPosition.getText().trim();
            case 12: return txtSupervisor.getText().trim();
            default: return "";
        }
    }

    // Show validation error pop-up
    private void showValidationError(String title, String message, String value) {
        JOptionPane.showMessageDialog(this, title + ": " + value + "\n" + message, "Validation Error", JOptionPane.ERROR_MESSAGE);
    }

    /* sample Add Employee Data:
    
    LastName          Pacquiao
    FirstName         Manny
    Birthday          12/25/1994              
    Address           123QC
    phoneNumber       123456789
    sssNumber         12-3456789-0
    philhealthNumber  123456789012
    tinNumber         123-456-789-000
    pagibigNumber     123456789012
    status            Probationary
    position          Account Rank and File
    supervisor        De Leon Selena

    */
    
    private void addEmployee() {
        int newEmployeeID = getNextEmployeeID(); // Get next available Employee ID
        txtEmployeeID.setText(String.valueOf(newEmployeeID));

        // Assign blanks to other text fields
        resetEmployeeForm();

        // Restrict birthday selection to at least 18 years ago
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, -18); // Move back 18 years
        chooserBirthday.setMaxSelectableDate(cal.getTime()); // Restrict future dates

        // Ensure the date chooser is empty initially
        chooserBirthday.setDate(null);

        // Get reference to the date field inside JDateChooser
        JTextField dateField = (JTextField) chooserBirthday.getDateEditor().getUiComponent();
        dateField.setBackground(Color.BLACK);
        dateField.setForeground(Color.WHITE);
        dateField.setCaretColor(Color.WHITE); // Ensures cursor is visible

        // Fix: Update text color dynamically when the date is selected/changed
        chooserBirthday.getDateEditor().addPropertyChangeListener("date", evt -> {
            dateField.setForeground(Color.WHITE); // Ensure text remains white when date is selected
        });

        // Prevent text color from changing when gaining/losing focus
        dateField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                dateField.setForeground(Color.WHITE); // Keep text white on focus
            }

            @Override
            public void focusLost(FocusEvent e) {
                dateField.setForeground(Color.WHITE); // Keep text white when losing focus
            }
        });

        // Move default view to 18 years ago when the user clicks the date chooser
        dateField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (chooserBirthday.getDate() == null) {
                    SwingUtilities.invokeLater(() -> {
                        Calendar defaultCal = Calendar.getInstance();
                        defaultCal.add(Calendar.YEAR, -18);
                        chooserBirthday.setDate(defaultCal.getTime()); // Set the default view to 18 years ago
                        chooserBirthday.setDate(null); // Clear selection after setting the view
                    });
                }
            }
        });

        System.out.println("Add Employee form launched.");

        // Show Add panel in a dialog
        dialogAddEmployee.setContentPane(createAddEmployeePanel(dialogAddEmployee));
        dialogAddEmployee.pack();
        dialogAddEmployee.setLocationRelativeTo(this);
        dialogAddEmployee.setVisible(true);
    }

    // Helper Method to Reset Form Fields
    private void resetEmployeeForm() {
        txtLastName.setText("");
        txtFirstName.setText("");
        txtAddress.setText("");
        txtPhoneNumber.setText("");
        txtSSSNumber.setText("");
        txtPhilHealthNumber.setText("");
        txtTINNumber.setText("");
        txtPagIbigNumber.setText("");
        txtStatus.setText("");
        txtPosition.setText("");
        txtSupervisor.setText("");
        chooserBirthday.setDate(null); // Ensure birthday field is reset
    }

    // Method to get the next Employee ID from Employee.csv
    private int getNextEmployeeID() {
        String filePath = "src/data9/Employee.csv"; // Update with correct path if needed
        int maxID = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                try {
                    int currentID = Integer.parseInt(data[0].trim());
                    if (currentID > maxID) {
                        maxID = currentID;
                    }
                } catch (NumberFormatException e) {
                    System.err.println("Skipping invalid Employee ID: " + data[0]);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading Employee.csv: " + e.getMessage());
        }

        return maxID + 1;
    }
                    
    private void deleteEmployee() {
        int selectedRow = jTableEmployeeRecords.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Select an employee to delete.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String id = (String) jTableEmployeeRecords.getValueAt(selectedRow, 0);

        int confirm = JOptionPane.showConfirmDialog(this, 
            "Are you sure you want to delete this employee?", 
            "Confirm Deletion", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            hrUser.deleteEmployee(id, employeeReader); // Calls HRUser's delete method
            loadEmployeeData(); // Refresh the JTable after deletion
        }
    }    
    
    private void loadLeaveRequests() {
        DefaultTableModel pendingModel = (DefaultTableModel) jTablePendingLeaveRequest.getModel();
        DefaultTableModel processedModel = (DefaultTableModel) jTableProcessedLeaveRequest.getModel();

        pendingModel.setRowCount(0);  // Clear the Pending table
        processedModel.setRowCount(0); // Clear the Processed table

        int pendingCount = 0;
        int processedCount = 0;

        try {
            List<LeaveRequest> leaveRequests = leaveRequestReader.getAllLeaveRequests();
            System.out.println("Total leave requests loaded: " + leaveRequests.size()); // Debugging

            for (LeaveRequest leave : leaveRequests) {
                switch (leave.getStatus().toLowerCase()) {
                    case "pending":
                        pendingModel.addRow(new Object[]{
                            leave.getLeaveID(),
                            leave.getEmployeeID(),
                            leave.getLeaveType(),
                            leave.getDateRequest(),
                            leave.getStartDate(),
                            leave.getEndDate(),
                            leave.getReason()
                        });
                        pendingCount++;
                        break;
                    case "approved":
                    case "rejected":
                        processedModel.addRow(new Object[]{
                            leave.getLeaveID(),
                            leave.getEmployeeID(),
                            leave.getLeaveType(),
                            leave.getDateRequest(),
                            leave.getStartDate(),
                            leave.getEndDate(),
                            leave.getReason(),
                            leave.getStatus(),
                            leave.getApprover(),
                            leave.getDateResponded(),
                            leave.getRemark()
                        });
                        processedCount++;
                        break;
                }
            }

            // Print counts for better visibility
            System.out.println("Total pending requests: " + pendingCount);
            System.out.println("Total processed requests (Approved/Rejected): " + processedCount);

            // Display messages if no records are found
            if (pendingModel.getRowCount() == 0) {
                JOptionPane.showMessageDialog(this, "No pending leave requests found.", "Info", JOptionPane.INFORMATION_MESSAGE);
            }

            if (processedModel.getRowCount() == 0) {
                JOptionPane.showMessageDialog(this, "No approved or rejected leave requests found.", "Info", JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading leave requests: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    private void approveLeave() {
        int selectedRow = jTablePendingLeaveRequest.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Select a leave request to approve.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String leaveId = (String) jTablePendingLeaveRequest.getValueAt(selectedRow, 0);
        try {
            hrUser.approveLeave(leaveId,leaveRequestReader);
            loadLeaveRequests();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error approving leave: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void rejectLeave() {
        int selectedRow = jTablePendingLeaveRequest.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Select a leave request to reject.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String leaveId = (String) jTablePendingLeaveRequest.getValueAt(selectedRow, 0);
        try {
            hrUser.rejectLeave(leaveId,leaveRequestReader);
            loadLeaveRequests();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error rejecting leave: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

        
    /** 
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanelHRMain = new javax.swing.JPanel();
        jPanelHeader = new javax.swing.JPanel();
        jLabelSeparate = new javax.swing.JLabel();
        jLabelGreet = new javax.swing.JLabel();
        jLabelDate = new javax.swing.JLabel();
        jLblUname1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jTabbedMain = new javax.swing.JTabbedPane();
        jPanelManageEmployee = new javax.swing.JPanel();
        jScrollPaneTableEmployeeRecords = new javax.swing.JScrollPane();
        jTableEmployeeRecords = new javax.swing.JTable();
        jButtonDelete = new javax.swing.JButton();
        jButtonUpdate = new javax.swing.JButton();
        jLabelTitleEmployeeList = new javax.swing.JLabel();
        jButtonAdd = new javax.swing.JButton();
        jPanelPendingLeaveRequest = new javax.swing.JPanel();
        jLabelTitleLeaveRequests = new javax.swing.JLabel();
        jScrollPaneLeaveRequests = new javax.swing.JScrollPane();
        jTablePendingLeaveRequest = new javax.swing.JTable();
        jButtonApprove = new javax.swing.JButton();
        jButtonReject = new javax.swing.JButton();
        jButtonHistory = new javax.swing.JButton();
        jPanelProcessedLeaveRequest = new javax.swing.JPanel();
        jLabelTitleLeaveRequests1 = new javax.swing.JLabel();
        jScrollPaneLeaveRequests1 = new javax.swing.JScrollPane();
        jTableProcessedLeaveRequest = new javax.swing.JTable();
        jButtonApprove1 = new javax.swing.JButton();
        jButtonReject1 = new javax.swing.JButton();
        jButtonHistory1 = new javax.swing.JButton();
        jPanelSidebar = new javax.swing.JPanel();
        jButtonManageEmployees = new javax.swing.JButton();
        jButtonLeaveRequests = new javax.swing.JButton();
        jButtonLogout = new javax.swing.JButton();
        jLabelGMT = new javax.swing.JLabel();
        jLabelTime = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Group 9 - Feature 1");

        jPanelHRMain.setBackground(new java.awt.Color(0, 0, 0));
        jPanelHRMain.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanelHeader.setBackground(new java.awt.Color(0, 0, 0));
        jPanelHeader.setForeground(new java.awt.Color(255, 102, 102));
        jPanelHeader.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabelSeparate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/logos9/horizontal (2).png"))); // NOI18N
        jLabelSeparate.setText("jLabel8");
        jPanelHeader.add(jLabelSeparate, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 51, 404, 1));

        jLabelGreet.setFont(new java.awt.Font("Century Gothic", 1, 20)); // NOI18N
        jLabelGreet.setForeground(new java.awt.Color(255, 255, 255));
        jLabelGreet.setText("Welcome!");
        jPanelHeader.add(jLabelGreet, new org.netbeans.lib.awtextra.AbsoluteConstraints(1100, 20, -1, -1));

        jLabelDate.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabelDate.setForeground(new java.awt.Color(255, 255, 255));
        jLabelDate.setText("Wednesday, December 25, 2012");
        jLabelDate.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jPanelHeader.add(jLabelDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(1100, 50, -1, -1));

        jLblUname1.setBackground(new java.awt.Color(0, 0, 102));
        jLblUname1.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLblUname1.setForeground(new java.awt.Color(255, 255, 255));
        jLblUname1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/logos9/LogoMPH_(small1).png"))); // NOI18N
        jLblUname1.setText("Username");
        jPanelHeader.add(jLblUname1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, -10, 100, 110));

        jLabel3.setFont(new java.awt.Font("Century Gothic", 1, 36)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("MOTOR");
        jPanelHeader.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, -10, -1, 100));

        jLabel5.setFont(new java.awt.Font("Century Gothic", 1, 36)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(204, 0, 51));
        jLabel5.setText("PH");
        jPanelHeader.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, -20, 70, 120));

        jPanelHRMain.add(jPanelHeader, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1340, 90));

        jPanelManageEmployee.setBackground(new java.awt.Color(102, 0, 0));
        jPanelManageEmployee.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTableEmployeeRecords.setAutoCreateRowSorter(true);
        jTableEmployeeRecords.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jTableEmployeeRecords.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Employee No.", "Last Name", "First name", "Birthday", "Address", "Phone No.", "SSS No.", "PhilHealth No.", "TIN No.", "Pagibig No.", "Status", "Position", "Supervisor"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class, java.lang.Object.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableEmployeeRecords.setToolTipText("");
        jTableEmployeeRecords.setSelectionForeground(new java.awt.Color(255, 255, 255));
        jScrollPaneTableEmployeeRecords.setViewportView(jTableEmployeeRecords);
        if (jTableEmployeeRecords.getColumnModel().getColumnCount() > 0) {
            jTableEmployeeRecords.getColumnModel().getColumn(3).setResizable(false);
            jTableEmployeeRecords.getColumnModel().getColumn(9).setResizable(false);
        }

        jPanelManageEmployee.add(jScrollPaneTableEmployeeRecords, new org.netbeans.lib.awtextra.AbsoluteConstraints(19, 208, 1090, 420));

        jButtonDelete.setBackground(new java.awt.Color(204, 0, 51));
        jButtonDelete.setForeground(new java.awt.Color(255, 255, 255));
        jButtonDelete.setText("Delete");
        jButtonDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonDeleteActionPerformed(evt);
            }
        });
        jPanelManageEmployee.add(jButtonDelete, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 163, -1, 30));

        jButtonUpdate.setBackground(new java.awt.Color(204, 0, 51));
        jButtonUpdate.setForeground(new java.awt.Color(255, 255, 255));
        jButtonUpdate.setText("Update");
        jButtonUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonUpdateActionPerformed(evt);
            }
        });
        jPanelManageEmployee.add(jButtonUpdate, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 163, -1, 30));

        jLabelTitleEmployeeList.setBackground(new java.awt.Color(255, 255, 255));
        jLabelTitleEmployeeList.setFont(new java.awt.Font("Century Gothic", 1, 20)); // NOI18N
        jLabelTitleEmployeeList.setForeground(new java.awt.Color(255, 255, 255));
        jLabelTitleEmployeeList.setText("EMPLOYEE RECORDS");
        jPanelManageEmployee.add(jLabelTitleEmployeeList, new org.netbeans.lib.awtextra.AbsoluteConstraints(910, 110, -1, 32));

        jButtonAdd.setBackground(new java.awt.Color(204, 0, 51));
        jButtonAdd.setForeground(new java.awt.Color(255, 255, 255));
        jButtonAdd.setText("Add");
        jButtonAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAddActionPerformed(evt);
            }
        });
        jPanelManageEmployee.add(jButtonAdd, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 163, -1, 30));

        jTabbedMain.addTab("Manage Employees", jPanelManageEmployee);

        jPanelPendingLeaveRequest.setBackground(new java.awt.Color(102, 0, 0));
        jPanelPendingLeaveRequest.setForeground(new java.awt.Color(255, 255, 255));
        jPanelPendingLeaveRequest.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabelTitleLeaveRequests.setFont(new java.awt.Font("Segoe UI Semibold", 1, 20)); // NOI18N
        jLabelTitleLeaveRequests.setForeground(new java.awt.Color(255, 255, 255));
        jLabelTitleLeaveRequests.setText("LEAVE REQUESTS");
        jPanelPendingLeaveRequest.add(jLabelTitleLeaveRequests, new org.netbeans.lib.awtextra.AbsoluteConstraints(910, 110, -1, 32));

        jTablePendingLeaveRequest.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Leave ID", "Employee No.", "Leave Type", "Date Requested", "Start Date ", "End Date", "Reason"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Object.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jTablePendingLeaveRequest.setToolTipText("");
        jScrollPaneLeaveRequests.setViewportView(jTablePendingLeaveRequest);
        if (jTablePendingLeaveRequest.getColumnModel().getColumnCount() > 0) {
            jTablePendingLeaveRequest.getColumnModel().getColumn(1).setResizable(false);
            jTablePendingLeaveRequest.getColumnModel().getColumn(3).setResizable(false);
            jTablePendingLeaveRequest.getColumnModel().getColumn(6).setResizable(false);
        }

        jPanelPendingLeaveRequest.add(jScrollPaneLeaveRequests, new org.netbeans.lib.awtextra.AbsoluteConstraints(19, 208, 970, 403));

        jButtonApprove.setBackground(new java.awt.Color(204, 0, 51));
        jButtonApprove.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jButtonApprove.setForeground(new java.awt.Color(255, 255, 255));
        jButtonApprove.setText("Approve");
        jButtonApprove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonApproveActionPerformed(evt);
            }
        });
        jPanelPendingLeaveRequest.add(jButtonApprove, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 163, -1, 30));

        jButtonReject.setBackground(new java.awt.Color(204, 0, 51));
        jButtonReject.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jButtonReject.setForeground(new java.awt.Color(255, 255, 255));
        jButtonReject.setText("Reject");
        jButtonReject.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRejectActionPerformed(evt);
            }
        });
        jPanelPendingLeaveRequest.add(jButtonReject, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 163, 80, 30));

        jButtonHistory.setBackground(new java.awt.Color(204, 0, 51));
        jButtonHistory.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jButtonHistory.setForeground(new java.awt.Color(255, 255, 255));
        jButtonHistory.setText("TemporaryBtn History");
        jButtonHistory.setBorderPainted(false);
        jButtonHistory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonHistoryActionPerformed(evt);
            }
        });
        jPanelPendingLeaveRequest.add(jButtonHistory, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 163, 170, 30));

        jTabbedMain.addTab("Leave Requests", jPanelPendingLeaveRequest);

        jPanelProcessedLeaveRequest.setBackground(new java.awt.Color(102, 0, 0));
        jPanelProcessedLeaveRequest.setForeground(new java.awt.Color(255, 255, 255));
        jPanelProcessedLeaveRequest.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabelTitleLeaveRequests1.setFont(new java.awt.Font("Segoe UI Semibold", 1, 20)); // NOI18N
        jLabelTitleLeaveRequests1.setForeground(new java.awt.Color(255, 255, 255));
        jLabelTitleLeaveRequests1.setText("LEAVE REQUESTS");
        jPanelProcessedLeaveRequest.add(jLabelTitleLeaveRequests1, new org.netbeans.lib.awtextra.AbsoluteConstraints(910, 110, -1, 32));

        jTableProcessedLeaveRequest.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Leave ID", "Employee No.", "Leave Type", "Date Requested", "Start Date ", "End Date", "Reason", "Status", "Approver", "Date Responded", "Remarks"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableProcessedLeaveRequest.setToolTipText("");
        jScrollPaneLeaveRequests1.setViewportView(jTableProcessedLeaveRequest);
        if (jTableProcessedLeaveRequest.getColumnModel().getColumnCount() > 0) {
            jTableProcessedLeaveRequest.getColumnModel().getColumn(1).setResizable(false);
            jTableProcessedLeaveRequest.getColumnModel().getColumn(3).setResizable(false);
            jTableProcessedLeaveRequest.getColumnModel().getColumn(6).setResizable(false);
            jTableProcessedLeaveRequest.getColumnModel().getColumn(8).setResizable(false);
        }

        jPanelProcessedLeaveRequest.add(jScrollPaneLeaveRequests1, new org.netbeans.lib.awtextra.AbsoluteConstraints(19, 208, 970, 403));

        jButtonApprove1.setBackground(new java.awt.Color(204, 0, 51));
        jButtonApprove1.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jButtonApprove1.setForeground(new java.awt.Color(255, 255, 255));
        jButtonApprove1.setText("Approve");
        jButtonApprove1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonApprove1ActionPerformed(evt);
            }
        });
        jPanelProcessedLeaveRequest.add(jButtonApprove1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 163, -1, 30));

        jButtonReject1.setBackground(new java.awt.Color(204, 0, 51));
        jButtonReject1.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jButtonReject1.setForeground(new java.awt.Color(255, 255, 255));
        jButtonReject1.setText("Reject");
        jButtonReject1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonReject1ActionPerformed(evt);
            }
        });
        jPanelProcessedLeaveRequest.add(jButtonReject1, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 163, 80, 30));

        jButtonHistory1.setBackground(new java.awt.Color(204, 0, 51));
        jButtonHistory1.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jButtonHistory1.setForeground(new java.awt.Color(255, 255, 255));
        jButtonHistory1.setText("TemporaryBtn History");
        jButtonHistory1.setBorderPainted(false);
        jButtonHistory1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonHistory1ActionPerformed(evt);
            }
        });
        jPanelProcessedLeaveRequest.add(jButtonHistory1, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 163, 170, 30));

        jTabbedMain.addTab("Leave Requests", jPanelProcessedLeaveRequest);

        jPanelHRMain.add(jTabbedMain, new org.netbeans.lib.awtextra.AbsoluteConstraints(208, -42, 1130, 680));

        jPanelSidebar.setBackground(new java.awt.Color(0, 0, 0));
        jPanelSidebar.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButtonManageEmployees.setBackground(new java.awt.Color(204, 0, 51));
        jButtonManageEmployees.setFont(new java.awt.Font("Century Gothic", 1, 13)); // NOI18N
        jButtonManageEmployees.setForeground(new java.awt.Color(255, 255, 255));
        jButtonManageEmployees.setText("Manage Employees");
        jButtonManageEmployees.setMaximumSize(new java.awt.Dimension(132, 27));
        jButtonManageEmployees.setMinimumSize(new java.awt.Dimension(132, 27));
        jButtonManageEmployees.setPreferredSize(new java.awt.Dimension(90, 23));
        jButtonManageEmployees.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonManageEmployeesActionPerformed(evt);
            }
        });
        jPanelSidebar.add(jButtonManageEmployees, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 170, 40));

        jButtonLeaveRequests.setBackground(new java.awt.Color(204, 0, 51));
        jButtonLeaveRequests.setFont(new java.awt.Font("Century Gothic", 1, 13)); // NOI18N
        jButtonLeaveRequests.setForeground(new java.awt.Color(255, 255, 255));
        jButtonLeaveRequests.setText("Leave Requests");
        jButtonLeaveRequests.setPreferredSize(new java.awt.Dimension(90, 23));
        jButtonLeaveRequests.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonLeaveRequestsActionPerformed(evt);
            }
        });
        jPanelSidebar.add(jButtonLeaveRequests, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, 170, 40));

        jPanelHRMain.add(jPanelSidebar, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 190, 320));

        jButtonLogout.setFont(new java.awt.Font("Century Gothic", 1, 13)); // NOI18N
        jButtonLogout.setText("Logout");
        jButtonLogout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonLogoutActionPerformed(evt);
            }
        });
        jPanelHRMain.add(jButtonLogout, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 420, 165, 30));

        jLabelGMT.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabelGMT.setForeground(new java.awt.Color(255, 255, 255));
        jLabelGMT.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelGMT.setText("GMT+8 PH Time");
        jPanelHRMain.add(jLabelGMT, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 510, 170, -1));

        jLabelTime.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        jLabelTime.setForeground(new java.awt.Color(255, 255, 255));
        jLabelTime.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelTime.setText("12:12:12 AM");
        jPanelHRMain.add(jLabelTime, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 530, 170, 32));

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelHRMain, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelHRMain, javax.swing.GroupLayout.PREFERRED_SIZE, 641, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonManageEmployeesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonManageEmployeesActionPerformed
        jTabbedMain.setSelectedIndex(0);
        jButtonManageEmployees.setBackground(Color.RED);
        jButtonLeaveRequests.setBackground (new java.awt.Color(0,0,0));
        loadEmployeeData();
    }//GEN-LAST:event_jButtonManageEmployeesActionPerformed

    private void jButtonLeaveRequestsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonLeaveRequestsActionPerformed
        jTabbedMain.setSelectedIndex(1);
        jButtonManageEmployees.setBackground(new java.awt.Color(0,0,0));
        jButtonLeaveRequests.setBackground(Color.RED);
        loadLeaveRequests();
    }//GEN-LAST:event_jButtonLeaveRequestsActionPerformed

    private void jButtonLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonLogoutActionPerformed
        Login newClassInstance = new Login();
                 newClassInstance.setVisible(true); 
                 
                dispose();
    }//GEN-LAST:event_jButtonLogoutActionPerformed

    private void jButtonApproveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonApproveActionPerformed
       approveLeave();
    }//GEN-LAST:event_jButtonApproveActionPerformed

    private void jButtonDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonDeleteActionPerformed
        deleteEmployee();
    }//GEN-LAST:event_jButtonDeleteActionPerformed

    private void jButtonRejectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRejectActionPerformed
        rejectLeave();
    }//GEN-LAST:event_jButtonRejectActionPerformed

    private void jButtonUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonUpdateActionPerformed
        updateEmployee();
    }//GEN-LAST:event_jButtonUpdateActionPerformed

    private void jButtonAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAddActionPerformed
        addEmployee();
    }//GEN-LAST:event_jButtonAddActionPerformed

    private void jButtonHistoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonHistoryActionPerformed
        jTabbedMain.setSelectedIndex(2);
        jButtonManageEmployees.setBackground(new java.awt.Color(0,0,0));
        jButtonLeaveRequests.setBackground(Color.RED);
        loadLeaveRequests();
    }//GEN-LAST:event_jButtonHistoryActionPerformed

    private void jButtonHistory1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonHistory1ActionPerformed
        jTabbedMain.setSelectedIndex(2);
        jButtonManageEmployees.setBackground(new java.awt.Color(0,0,0));
        jButtonLeaveRequests.setBackground(Color.RED);
        loadLeaveRequests();
    }//GEN-LAST:event_jButtonHistory1ActionPerformed

    private void jButtonReject1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonReject1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonReject1ActionPerformed

    private void jButtonApprove1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonApprove1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonApprove1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                HRDashboard hrDashboard = new HRDashboard(); // Store the instance in a variable
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonAdd;
    private javax.swing.JButton jButtonApprove;
    private javax.swing.JButton jButtonApprove1;
    private javax.swing.JButton jButtonDelete;
    private javax.swing.JButton jButtonHistory;
    private javax.swing.JButton jButtonHistory1;
    private javax.swing.JButton jButtonLeaveRequests;
    private javax.swing.JButton jButtonLogout;
    private javax.swing.JButton jButtonManageEmployees;
    private javax.swing.JButton jButtonReject;
    private javax.swing.JButton jButtonReject1;
    private javax.swing.JButton jButtonUpdate;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabelDate;
    private javax.swing.JLabel jLabelGMT;
    private javax.swing.JLabel jLabelGreet;
    private javax.swing.JLabel jLabelSeparate;
    private javax.swing.JLabel jLabelTime;
    private javax.swing.JLabel jLabelTitleEmployeeList;
    private javax.swing.JLabel jLabelTitleLeaveRequests;
    private javax.swing.JLabel jLabelTitleLeaveRequests1;
    private javax.swing.JLabel jLblUname1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanelHRMain;
    private javax.swing.JPanel jPanelHeader;
    private javax.swing.JPanel jPanelManageEmployee;
    private javax.swing.JPanel jPanelPendingLeaveRequest;
    private javax.swing.JPanel jPanelProcessedLeaveRequest;
    private javax.swing.JPanel jPanelSidebar;
    private javax.swing.JScrollPane jScrollPaneLeaveRequests;
    private javax.swing.JScrollPane jScrollPaneLeaveRequests1;
    private javax.swing.JScrollPane jScrollPaneTableEmployeeRecords;
    private javax.swing.JTabbedPane jTabbedMain;
    private javax.swing.JTable jTableEmployeeRecords;
    private javax.swing.JTable jTablePendingLeaveRequest;
    private javax.swing.JTable jTableProcessedLeaveRequest;
    // End of variables declaration//GEN-END:variables

}
