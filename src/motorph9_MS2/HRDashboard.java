/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

package motorph9_MS2;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.Timer;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.*;
import java.io.IOException;
import java.util.List;
import javax.swing.table.DefaultTableModel;


public class HRDashboard extends javax.swing.JFrame { 
    //private JTable employeeTable;
    //private JTable leaveTable;
    private Timer timer;
    private HRUser hrUser;
    private EmployeeDetailsReader employeeReader;
    private LeaveRequestReader leaveRequestReader;
    
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
        //setupSearchListener();
        startClock();
        loadEmployeeTable();
        loadEmployeeData();
    }

    private HRDashboard() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    /*private void setupSearchListener() {
        jTextFieldSearch.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) { checkInput(); }
            @Override
            public void removeUpdate(DocumentEvent e) { checkInput(); }
            @Override
            public void changedUpdate(DocumentEvent e) { checkInput(); }
        });
    }*/
    
    private void startClock() {
        timer = new Timer(1000, e -> updateTimeAndDate());
        timer.start();
    }
    
    /*private void checkInput() {
        setButtonsEnabled(true);
    }

    private void setButtonsEnabled(boolean enabled) {
        jButtonManageEmployees.setEnabled(enabled);
        jButtonLeaveRequests.setEnabled(enabled);
        //jButtonSearch.setEnabled(enabled);
    }*/

    private void updateTimeAndDate() {
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm:ss a");
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, MMMM d, yyyy");
        jLabelTime.setText(timeFormat.format(new Date()));
        jLabelDate.setText(dateFormat.format(new Date()));
    }
    
    private void loadEmployeeTable() {
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

    public JTable getjTableEmployeeRecords() {
        return jTableEmployeeRecords;
    }
  
    /*private void loadEmployeeData() {
        DefaultTableModel model = (DefaultTableModel) jTableEmployeeRecords.getModel();
        model.setRowCount(0); // ✅ Clear table before loading new data

        try {
            List<EmployeeUser> employees = EmployeeDetailsReader.getAllEmployees();
            System.out.println("Total Employees Loaded: " + employees.size()); // ✅ Debugging

            for (EmployeeUser emp : employees) {
                model.addRow(new Object[]{
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
                });
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error loading employee data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }*/
    
    private void loadEmployeeData() {
        DefaultTableModel modelRecords = (DefaultTableModel) jTableEmployeeRecords.getModel();
        DefaultTableModel modelAddEmployee = (DefaultTableModel) jTableAddEmployee.getModel();

        modelRecords.setRowCount(0); // ✅ Clear Employee Records table
        modelAddEmployee.setRowCount(0); // ✅ Clear Add Employee table

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
                modelAddEmployee.addRow(rowData); // ✅ Add to Add Employee table
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error loading employee data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateEmployee() { 
        int selectedRow = jTableEmployeeRecords.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Select an employee to update.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Retrieve employee data from the table   
        String employeeNum = String.valueOf(jTableEmployeeRecords.getValueAt(selectedRow, 0));
        String lastName = String.valueOf(jTableEmployeeRecords.getValueAt(selectedRow, 1));
        String firstName = String.valueOf(jTableEmployeeRecords.getValueAt(selectedRow, 2));
        String sssNumber = String.valueOf(jTableEmployeeRecords.getValueAt(selectedRow, 3));
        String philhealthNumber = String.valueOf(jTableEmployeeRecords.getValueAt(selectedRow, 4));
        String tinNumber = String.valueOf(jTableEmployeeRecords.getValueAt(selectedRow, 5));
        String pagibigNumber = String.valueOf(jTableEmployeeRecords.getValueAt(selectedRow, 6));
        String birthday = String.valueOf(jTableEmployeeRecords.getValueAt(selectedRow, 7));
        String address = String.valueOf(jTableEmployeeRecords.getValueAt(selectedRow, 8));
        String phoneNumber = String.valueOf(jTableEmployeeRecords.getValueAt(selectedRow, 9)); // No cast issues
        String status = String.valueOf(jTableEmployeeRecords.getValueAt(selectedRow, 10));
        String position = String.valueOf(jTableEmployeeRecords.getValueAt(selectedRow, 11));
        String supervisor = String.valueOf(jTableEmployeeRecords.getValueAt(selectedRow, 12));

        // Prompt user for updates
        String newLastName = JOptionPane.showInputDialog("Enter new last name:", lastName);
        String newFirstName = JOptionPane.showInputDialog("Enter new first name:", firstName);
        String newSSS = JOptionPane.showInputDialog("Enter new SSS number:", sssNumber);
        String newPhilHealth = JOptionPane.showInputDialog("Enter new PhilHealth number:", philhealthNumber);
        String newTIN = JOptionPane.showInputDialog("Enter new TIN number:", tinNumber);
        String newPagibig = JOptionPane.showInputDialog("Enter new Pag-IBIG number:", pagibigNumber);
        String newBirthday = JOptionPane.showInputDialog("Enter new birthday:", birthday);
        String newAddress = JOptionPane.showInputDialog("Enter new address:", address);
        String newPhoneNumber = JOptionPane.showInputDialog("Enter new phone number:", phoneNumber);
        String newStatus = JOptionPane.showInputDialog("Enter new status:", status);
        String newPosition = JOptionPane.showInputDialog("Enter new position:", position);
        String newSupervisor = JOptionPane.showInputDialog("Enter new supervisor:", supervisor);

        // Ensure the user entered valid values before updating
        if (newLastName != null && newFirstName != null && newSSS != null &&
            newPhilHealth != null && newTIN != null && newPagibig != null &&
            newBirthday != null && newAddress != null && newPhoneNumber != null &&
            newStatus != null && newPosition != null && newSupervisor != null) {

            try {
                int parsedPhoneNumber = Integer.parseInt(newPhoneNumber.trim()); // ✅ Convert phone number to int

                // Create an updated EmployeeUser object
                EmployeeUser updatedEmployee = new EmployeeUser(
                    employeeNum, newLastName, newFirstName, newBirthday, 
                    newAddress, parsedPhoneNumber, newSSS, newPhilHealth, 
                    newTIN, newPagibig, newStatus, newPosition, newSupervisor
                );

                // Call update method
                hrUser.updateEmployee(updatedEmployee, employeeReader);
                loadEmployeeData(); // Reload table data after update
                JOptionPane.showMessageDialog(this, "Employee updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Invalid phone number. Please enter a numeric value.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Update canceled or invalid input.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void addEmployee() { 
        JTextField jTextFieldEmployeeno = new JTextField();
        JTextField jTextFieldLastname = new JTextField();
        JTextField jTextFieldFirstname = new JTextField();
        JTextField jTextFieldBirthday = new JTextField();
        JTextField jTextFieldAddress = new JTextField();
        JTextField jTextFieldPhoneno = new JTextField();
        JTextField jTextFieldSSSno = new JTextField();
        JTextField jTextFieldPhilhealthno = new JTextField();
        JTextField jTextFieldTINno = new JTextField();
        JTextField jTextFieldPagibigNo = new JTextField();
        JTextField jTextFieldStatus = new JTextField();
        JTextField jTextFieldPosition = new JTextField();
        JTextField jTextFieldSupervisor = new JTextField();

        // Set tooltips for better user guidance
        jTextFieldEmployeeno.setToolTipText("Enter numeric Employee Number.");
        jTextFieldLastname.setToolTipText("Enter employee's last name.");
        jTextFieldFirstname.setToolTipText("Enter employee's first name.");
        jTextFieldBirthday.setToolTipText("Enter date in YYYY-MM-DD format.");
        jTextFieldAddress.setToolTipText("Enter employee's full address.");
        jTextFieldPhoneno.setToolTipText("Enter numeric phone number.");
        jTextFieldSSSno.setToolTipText("Enter numeric SSS Number.");
        jTextFieldPhilhealthno.setToolTipText("Enter numeric PhilHealth Number.");
        jTextFieldTINno.setToolTipText("Enter numeric TIN Number.");
        jTextFieldPagibigNo.setToolTipText("Enter numeric Pag-IBIG Number.");
        jTextFieldStatus.setToolTipText("Enter employment status (e.g., Active, Resigned).");
        jTextFieldPosition.setToolTipText("Enter employee's job position.");
        jTextFieldSupervisor.setToolTipText("Enter supervisor's name.");

        JPanel panel = new JPanel(new GridLayout(13, 2));
        panel.add(new JLabel("Employee Number:")); panel.add(jTextFieldEmployeeno);
        panel.add(new JLabel("Last Name:")); panel.add(jTextFieldLastname);
        panel.add(new JLabel("First Name:")); panel.add(jTextFieldFirstname);
        panel.add(new JLabel("Birthday (YYYY-MM-DD):")); panel.add(jTextFieldBirthday);
        panel.add(new JLabel("Address:")); panel.add(jTextFieldAddress);
        panel.add(new JLabel("Phone Number:")); panel.add(jTextFieldPhoneno);
        panel.add(new JLabel("SSS Number:")); panel.add(jTextFieldSSSno);
        panel.add(new JLabel("PhilHealth Number:")); panel.add(jTextFieldPhilhealthno);
        panel.add(new JLabel("TIN Number:")); panel.add(jTextFieldTINno);
        panel.add(new JLabel("Pag-IBIG Number:")); panel.add(jTextFieldPagibigNo);
        panel.add(new JLabel("Status:")); panel.add(jTextFieldStatus);
        panel.add(new JLabel("Position:")); panel.add(jTextFieldPosition);
        panel.add(new JLabel("Supervisor:")); panel.add(jTextFieldSupervisor);

        int result = JOptionPane.showConfirmDialog(null, panel, "Add Employee", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                // Validate if all fields are filled
                if (jTextFieldEmployeeno.getText().trim().isEmpty() || 
                    jTextFieldLastname.getText().trim().isEmpty() ||
                    jTextFieldFirstname.getText().trim().isEmpty() ||
                    jTextFieldBirthday.getText().trim().isEmpty() ||
                    jTextFieldAddress.getText().trim().isEmpty() ||
                    jTextFieldPhoneno.getText().trim().isEmpty() ||
                    jTextFieldSSSno.getText().trim().isEmpty() ||
                    jTextFieldPhilhealthno.getText().trim().isEmpty() ||
                    jTextFieldTINno.getText().trim().isEmpty() ||
                    jTextFieldPagibigNo.getText().trim().isEmpty() ||
                    jTextFieldStatus.getText().trim().isEmpty() ||
                    jTextFieldPosition.getText().trim().isEmpty() ||
                    jTextFieldSupervisor.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "All fields must be filled!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Convert phone number to integer
                int parsedPhoneNumber = Integer.parseInt(jTextFieldPhoneno.getText().trim());

                // Create a new EmployeeUser object
                EmployeeUser newEmployee = new EmployeeUser(
                    jTextFieldEmployeeno.getText().trim(), jTextFieldLastname.getText().trim(), 
                    jTextFieldFirstname.getText().trim(), jTextFieldBirthday.getText().trim(),
                    jTextFieldAddress.getText().trim(), parsedPhoneNumber,
                    jTextFieldSSSno.getText().trim(), jTextFieldPhilhealthno.getText().trim(),
                    jTextFieldTINno.getText().trim(), jTextFieldPagibigNo.getText().trim(),
                    jTextFieldStatus.getText().trim(), jTextFieldPosition.getText().trim(),
                    jTextFieldSupervisor.getText().trim()
                );

                // Save to CSV
                hrUser.addEmployee(newEmployee, employeeReader);

                // Add to JTable
                DefaultTableModel model = (DefaultTableModel) jTableEmployeeRecords.getModel();
                model.addRow(new Object[]{
                    newEmployee.getEmployeeId(), newEmployee.getLastName(), newEmployee.getFirstName(),
                    newEmployee.getBirthday(), newEmployee.getAddress(), newEmployee.getPhone(),
                    newEmployee.getSSS(), newEmployee.getPhilHealth(), newEmployee.getTIN(),
                    newEmployee.getPagibigNumber(), newEmployee.getStatus(), newEmployee.getPosition(),
                    newEmployee.getImmediateSupervisor()
                });

                // Auto-scroll to the newly added row
                int lastRow = model.getRowCount() - 1;
                jTableEmployeeRecords.setRowSelectionInterval(lastRow, lastRow);
                jTableEmployeeRecords.scrollRectToVisible(jTableEmployeeRecords.getCellRect(lastRow, 0, true));

                // Refresh the table data
                loadEmployeeData();
                JOptionPane.showMessageDialog(null, "Employee added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Invalid input. Please enter correct values.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void resetAddEmployeeForm() {
        jTextFieldEmployeeno.setText("");
        jTextFieldLastname.setText("");
        jTextFieldFirstname.setText("");
        jTextFieldBirthday.setText("");
        jTextFieldAddress.setText("");
        jTextFieldPhoneno.setText("");
        jTextFieldSSSno.setText("");
        jTextFieldPhilhealthno.setText("");
        jTextFieldTINno.setText("");
        jTextFieldPagibigNo.setText("");
        jTextFieldStatus.setText("");
        jTextFieldPosition.setText("");
        jTextFieldSupervisor.setText("");
        jButtonAdd.setEnabled(false); // Disable the add button initially
    }
    
    private void checkFormCompletion() {
        boolean allFilled = !jTextFieldEmployeeno.getText().trim().isEmpty() &&
                            !jTextFieldLastname.getText().trim().isEmpty() &&
                            !jTextFieldFirstname.getText().trim().isEmpty() &&
                            !jTextFieldBirthday.getText().trim().isEmpty() &&
                            !jTextFieldAddress.getText().trim().isEmpty() &&
                            !jTextFieldPhoneno.getText().trim().isEmpty() &&
                            !jTextFieldSSSno.getText().trim().isEmpty() &&
                            !jTextFieldPhilhealthno.getText().trim().isEmpty() &&
                            !jTextFieldTINno.getText().trim().isEmpty() &&
                            !jTextFieldPagibigNo.getText().trim().isEmpty() &&
                            !jTextFieldStatus.getText().trim().isEmpty() &&
                            !jTextFieldPosition.getText().trim().isEmpty() &&
                            !jTextFieldSupervisor.getText().trim().isEmpty();
        jButtonAdd.setEnabled(allFilled);
    }

    // Add document listeners to fields to check form completion in real-time
    private void addFormListeners() {
        DocumentListener listener = new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) { checkFormCompletion(); }
            @Override
            public void removeUpdate(DocumentEvent e) { checkFormCompletion(); }
            @Override
            public void changedUpdate(DocumentEvent e) { checkFormCompletion(); }
        };

        jTextFieldEmployeeno.getDocument().addDocumentListener(listener);
        jTextFieldLastname.getDocument().addDocumentListener(listener);
        jTextFieldFirstname.getDocument().addDocumentListener(listener);
        jTextFieldBirthday.getDocument().addDocumentListener(listener);
        jTextFieldAddress.getDocument().addDocumentListener(listener);
        jTextFieldPhoneno.getDocument().addDocumentListener(listener);
        jTextFieldSSSno.getDocument().addDocumentListener(listener);
        jTextFieldPhilhealthno.getDocument().addDocumentListener(listener);
        jTextFieldTINno.getDocument().addDocumentListener(listener);
        jTextFieldPagibigNo.getDocument().addDocumentListener(listener);
        jTextFieldStatus.getDocument().addDocumentListener(listener);
        jTextFieldPosition.getDocument().addDocumentListener(listener);
        jTextFieldSupervisor.getDocument().addDocumentListener(listener);

        // Real-time numeric validation
        KeyListener numericKeyListener = new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c)) {
                    e.consume(); // Prevents non-numeric input
                }
            }
        };

        jTextFieldEmployeeno.addKeyListener(numericKeyListener);
        jTextFieldPhoneno.addKeyListener(numericKeyListener);
        jTextFieldSSSno.addKeyListener(numericKeyListener);
        jTextFieldPhilhealthno.addKeyListener(numericKeyListener);
        jTextFieldTINno.addKeyListener(numericKeyListener);
        jTextFieldPagibigNo.addKeyListener(numericKeyListener);

        // Real-time validation for date format
        jTextFieldBirthday.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (!jTextFieldBirthday.getText().matches("\\d{4}-\\d{2}-\\d{2}")) {
                    jTextFieldBirthday.setForeground(Color.RED);
                } else {
                    jTextFieldBirthday.setForeground(Color.BLACK);
                }
            }
        });
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
        
    /*private void loadLeaveRequests() {
        DefaultTableModel model = (DefaultTableModel) jTableLeaveRequest.getModel();
        model.setRowCount(0);
        List<LeaveRequest> leaveRequests = leaveRequestReader.getAllLeaveRequests();
        for (LeaveRequest leave : leaveRequests) {
            model.addRow(new Object[]{leave.getLeaveId(), leave.getEmployeeId(), leave.getLeaveType(), leave.getStartDate(), leave.getEndDate(), leave.getStatus()});
        }
    }
    
    private void loadLeaveRequests() {
        DefaultTableModel model = (DefaultTableModel) jTableLeaveRequest.getModel();
        model.setRowCount(0); // Clear existing data

        try {
            List<LeaveRequest> leaveRequests = leaveRequestReader.getAllLeaveRequests();
            for (LeaveRequest leave : leaveRequests) {
                model.addRow(new Object[]{
                    leave.getLeaveID(),
                    leave.getEmployeeID(),
                    leave.getLeaveType(),
                    leave.getDateRequest(),
                    leave.getStartDate(),
                    leave.getEndDate(),
                    leave.getReason(),
                    leave.getStatus(),
                    leave.getApprover(),
                    leave.getDateResponded()
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading leave requests: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }*/
    
    private void loadLeaveRequests() {
        DefaultTableModel model = (DefaultTableModel) jTableLeaveRequest.getModel();
        model.setRowCount(0); // Clear the table before loading new data

        try {
            List<LeaveRequest> leaveRequests = leaveRequestReader.getAllLeaveRequests();
            System.out.println("Total leave requests loaded: " + leaveRequests.size()); // Debugging

            for (LeaveRequest leave : leaveRequests) {
                // ✅ Make sure ALL requests are added, regardless of status
                model.addRow(new Object[]{
                    leave.getLeaveID(),
                    leave.getEmployeeID(),
                    leave.getLeaveType(),
                    leave.getDateRequest(),
                    leave.getStartDate(),
                    leave.getEndDate(),
                    leave.getReason(),
                    leave.getStatus(), // ✅ Show status (Pending, Approved, Rejected)
                    leave.getApprover(),
                    leave.getDateResponded()
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading leave requests: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }



    private void approveLeave() {
        int selectedRow = jTableLeaveRequest.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Select a leave request to approve.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String leaveId = (String) jTableLeaveRequest.getValueAt(selectedRow, 0);
        try {
            hrUser.approveLeave(leaveId,leaveRequestReader);
            loadLeaveRequests();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error approving leave: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void rejectLeave() {
        int selectedRow = jTableLeaveRequest.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Select a leave request to reject.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String leaveId = (String) jTableLeaveRequest.getValueAt(selectedRow, 0);
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
        jPanelLeaveRequest = new javax.swing.JPanel();
        jLabelTitleLeaveRequests = new javax.swing.JLabel();
        jScrollPaneLeaveRequests = new javax.swing.JScrollPane();
        jTableLeaveRequest = new javax.swing.JTable();
        jButtonApprove = new javax.swing.JButton();
        jButtonReject = new javax.swing.JButton();
        jPanelAddEmployee = new javax.swing.JPanel();
        jPanelAdddetails = new javax.swing.JPanel();
        jLabelEmployeeno = new javax.swing.JLabel();
        jTextFieldEmployeeno = new javax.swing.JTextField();
        jLabelLastname = new javax.swing.JLabel();
        jTextFieldLastname = new javax.swing.JTextField();
        jLabelFirstname = new javax.swing.JLabel();
        jTextFieldFirstname = new javax.swing.JTextField();
        jLabelBirthday = new javax.swing.JLabel();
        jLabelPhilhealthno = new javax.swing.JLabel();
        jTextFieldBirthday = new javax.swing.JTextField();
        jTextFieldPhilhealthno = new javax.swing.JTextField();
        jLabelTINno = new javax.swing.JLabel();
        jTextFieldTINno = new javax.swing.JTextField();
        jLabelAddress = new javax.swing.JLabel();
        jLabelPhoneno = new javax.swing.JLabel();
        jLabelSSSno = new javax.swing.JLabel();
        jTextFieldAddress = new javax.swing.JTextField();
        jTextFieldPhoneno = new javax.swing.JTextField();
        jLabelPagibigNo = new javax.swing.JLabel();
        jLabelPagibigNo1 = new javax.swing.JLabel();
        jLabelPagibigNo2 = new javax.swing.JLabel();
        jLabelPagibigNo3 = new javax.swing.JLabel();
        jTextFieldSSSno = new javax.swing.JTextField();
        jTextFieldPosition = new javax.swing.JTextField();
        jTextFieldStatus = new javax.swing.JTextField();
        jTextFieldPagibigNo = new javax.swing.JTextField();
        jTextFieldSupervisor = new javax.swing.JTextField();
        jScrollPaneAddEmployee = new javax.swing.JScrollPane();
        jTableAddEmployee = new javax.swing.JTable();
        jLabelTitleAddEmployee = new javax.swing.JLabel();
        jButtonAdd = new javax.swing.JButton();
        jPanelSidebar = new javax.swing.JPanel();
        jButtonManageEmployees = new javax.swing.JButton();
        jButtonLeaveRequests = new javax.swing.JButton();
        jButtonAddEmployee = new javax.swing.JButton();
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
        jPanelHeader.add(jLabelGreet, new org.netbeans.lib.awtextra.AbsoluteConstraints(1130, 20, -1, -1));

        jLabelDate.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabelDate.setForeground(new java.awt.Color(255, 255, 255));
        jLabelDate.setText("Wednesday, December 25, 2012");
        jLabelDate.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jPanelHeader.add(jLabelDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(1130, 50, -1, -1));

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
                false, true, true, true, true, true, true, true, true, true, true, true, true
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
        jPanelManageEmployee.add(jLabelTitleEmployeeList, new org.netbeans.lib.awtextra.AbsoluteConstraints(910, 110, -1, -1));

        jTabbedMain.addTab("Manage Employees", jPanelManageEmployee);

        jPanelLeaveRequest.setBackground(new java.awt.Color(102, 0, 0));
        jPanelLeaveRequest.setForeground(new java.awt.Color(255, 255, 255));
        jPanelLeaveRequest.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabelTitleLeaveRequests.setFont(new java.awt.Font("Segoe UI Semibold", 1, 20)); // NOI18N
        jLabelTitleLeaveRequests.setForeground(new java.awt.Color(255, 255, 255));
        jLabelTitleLeaveRequests.setText("LEAVE REQUESTS");
        jPanelLeaveRequest.add(jLabelTitleLeaveRequests, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 110, -1, 32));

        jTableLeaveRequest.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Leave ID", "Employee No.", "Leave Type", "Date Requested", "Start Date ", "End Date", "Reason", "Status", "Approver", "Date Responded"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableLeaveRequest.setToolTipText("");
        jScrollPaneLeaveRequests.setViewportView(jTableLeaveRequest);
        if (jTableLeaveRequest.getColumnModel().getColumnCount() > 0) {
            jTableLeaveRequest.getColumnModel().getColumn(1).setResizable(false);
            jTableLeaveRequest.getColumnModel().getColumn(3).setResizable(false);
            jTableLeaveRequest.getColumnModel().getColumn(6).setResizable(false);
            jTableLeaveRequest.getColumnModel().getColumn(8).setResizable(false);
        }

        jPanelLeaveRequest.add(jScrollPaneLeaveRequests, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 190, 970, 403));

        jButtonApprove.setBackground(new java.awt.Color(204, 0, 51));
        jButtonApprove.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jButtonApprove.setForeground(new java.awt.Color(255, 255, 255));
        jButtonApprove.setText("Approve");
        jButtonApprove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonApproveActionPerformed(evt);
            }
        });
        jPanelLeaveRequest.add(jButtonApprove, new org.netbeans.lib.awtextra.AbsoluteConstraints(22, 150, -1, -1));

        jButtonReject.setBackground(new java.awt.Color(204, 0, 51));
        jButtonReject.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jButtonReject.setForeground(new java.awt.Color(255, 255, 255));
        jButtonReject.setText("Reject");
        jButtonReject.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRejectActionPerformed(evt);
            }
        });
        jPanelLeaveRequest.add(jButtonReject, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 150, 80, -1));

        jTabbedMain.addTab("Leave Requests", jPanelLeaveRequest);

        jPanelAddEmployee.setBackground(new java.awt.Color(102, 0, 0));

        jPanelAdddetails.setBackground(new java.awt.Color(0, 0, 0));

        jLabelEmployeeno.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabelEmployeeno.setForeground(new java.awt.Color(255, 255, 255));
        jLabelEmployeeno.setText("EMPLOYEE NO");

        jTextFieldEmployeeno.setBackground(new java.awt.Color(0, 0, 0));
        jTextFieldEmployeeno.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jTextFieldEmployeeno.setForeground(new java.awt.Color(255, 255, 255));
        jTextFieldEmployeeno.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextFieldEmployeenoKeyTyped(evt);
            }
        });

        jLabelLastname.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabelLastname.setForeground(new java.awt.Color(255, 255, 255));
        jLabelLastname.setText("LAST NAME");

        jTextFieldLastname.setBackground(new java.awt.Color(0, 0, 0));
        jTextFieldLastname.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jTextFieldLastname.setForeground(new java.awt.Color(255, 255, 255));

        jLabelFirstname.setBackground(new java.awt.Color(0, 0, 0));
        jLabelFirstname.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabelFirstname.setForeground(new java.awt.Color(255, 255, 255));
        jLabelFirstname.setText("FIRST NAME");

        jTextFieldFirstname.setBackground(new java.awt.Color(0, 0, 0));
        jTextFieldFirstname.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jTextFieldFirstname.setForeground(new java.awt.Color(255, 255, 255));

        jLabelBirthday.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabelBirthday.setForeground(new java.awt.Color(255, 255, 255));
        jLabelBirthday.setText("BIRTHDAY");

        jLabelPhilhealthno.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabelPhilhealthno.setForeground(new java.awt.Color(255, 255, 255));
        jLabelPhilhealthno.setText("PHILHEALTH NO");

        jTextFieldBirthday.setBackground(new java.awt.Color(0, 0, 0));
        jTextFieldBirthday.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jTextFieldBirthday.setForeground(new java.awt.Color(255, 255, 255));

        jTextFieldPhilhealthno.setBackground(new java.awt.Color(0, 0, 0));
        jTextFieldPhilhealthno.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jTextFieldPhilhealthno.setForeground(new java.awt.Color(255, 255, 255));

        jLabelTINno.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabelTINno.setForeground(new java.awt.Color(255, 255, 255));
        jLabelTINno.setText("TIN NO");

        jTextFieldTINno.setBackground(new java.awt.Color(0, 0, 0));
        jTextFieldTINno.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jTextFieldTINno.setForeground(new java.awt.Color(255, 255, 255));

        jLabelAddress.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabelAddress.setForeground(new java.awt.Color(255, 255, 255));
        jLabelAddress.setText("ADDRESS");

        jLabelPhoneno.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabelPhoneno.setForeground(new java.awt.Color(255, 255, 255));
        jLabelPhoneno.setText("PHONE NO");

        jLabelSSSno.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabelSSSno.setForeground(new java.awt.Color(255, 255, 255));
        jLabelSSSno.setText("SSS NO");

        jTextFieldAddress.setBackground(new java.awt.Color(0, 0, 0));
        jTextFieldAddress.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jTextFieldAddress.setForeground(new java.awt.Color(255, 255, 255));

        jTextFieldPhoneno.setBackground(new java.awt.Color(0, 0, 0));
        jTextFieldPhoneno.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jTextFieldPhoneno.setForeground(new java.awt.Color(255, 255, 255));

        jLabelPagibigNo.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabelPagibigNo.setForeground(new java.awt.Color(255, 255, 255));
        jLabelPagibigNo.setText("PAG-IBIG NO");

        jLabelPagibigNo1.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabelPagibigNo1.setForeground(new java.awt.Color(255, 255, 255));
        jLabelPagibigNo1.setText("STATUS");

        jLabelPagibigNo2.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabelPagibigNo2.setForeground(new java.awt.Color(255, 255, 255));
        jLabelPagibigNo2.setText("POSITION");

        jLabelPagibigNo3.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabelPagibigNo3.setForeground(new java.awt.Color(255, 255, 255));
        jLabelPagibigNo3.setText("SUPERVISOR");

        jTextFieldSSSno.setBackground(new java.awt.Color(0, 0, 0));
        jTextFieldSSSno.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jTextFieldSSSno.setForeground(new java.awt.Color(255, 255, 255));

        jTextFieldPosition.setBackground(new java.awt.Color(0, 0, 0));
        jTextFieldPosition.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jTextFieldPosition.setForeground(new java.awt.Color(255, 255, 255));

        jTextFieldStatus.setBackground(new java.awt.Color(0, 0, 0));
        jTextFieldStatus.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jTextFieldStatus.setForeground(new java.awt.Color(255, 255, 255));

        jTextFieldPagibigNo.setBackground(new java.awt.Color(0, 0, 0));
        jTextFieldPagibigNo.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jTextFieldPagibigNo.setForeground(new java.awt.Color(255, 255, 255));

        jTextFieldSupervisor.setBackground(new java.awt.Color(0, 0, 0));
        jTextFieldSupervisor.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jTextFieldSupervisor.setForeground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanelAdddetailsLayout = new javax.swing.GroupLayout(jPanelAdddetails);
        jPanelAdddetails.setLayout(jPanelAdddetailsLayout);
        jPanelAdddetailsLayout.setHorizontalGroup(
            jPanelAdddetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelAdddetailsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelAdddetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelAdddetailsLayout.createSequentialGroup()
                        .addGroup(jPanelAdddetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelEmployeeno)
                            .addComponent(jLabelLastname)
                            .addComponent(jLabelPhilhealthno, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabelAddress, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabelSSSno, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanelAdddetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jLabelPhoneno, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabelBirthday, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabelFirstname, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGap(11, 11, 11)
                        .addGroup(jPanelAdddetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextFieldPhilhealthno, javax.swing.GroupLayout.DEFAULT_SIZE, 408, Short.MAX_VALUE)
                            .addComponent(jTextFieldEmployeeno)
                            .addComponent(jTextFieldLastname)
                            .addComponent(jTextFieldBirthday)
                            .addComponent(jTextFieldFirstname, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jTextFieldAddress)
                            .addComponent(jTextFieldPhoneno)
                            .addComponent(jTextFieldSSSno)))
                    .addGroup(jPanelAdddetailsLayout.createSequentialGroup()
                        .addGroup(jPanelAdddetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelTINno, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabelPagibigNo)
                            .addComponent(jLabelPagibigNo1)
                            .addComponent(jLabelPagibigNo2))
                        .addGap(38, 38, 38)
                        .addGroup(jPanelAdddetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextFieldPosition)
                            .addComponent(jTextFieldPagibigNo)
                            .addComponent(jTextFieldTINno)
                            .addComponent(jTextFieldStatus)
                            .addComponent(jTextFieldSupervisor))))
                .addContainerGap())
            .addGroup(jPanelAdddetailsLayout.createSequentialGroup()
                .addComponent(jLabelPagibigNo3)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanelAdddetailsLayout.setVerticalGroup(
            jPanelAdddetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelAdddetailsLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanelAdddetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelEmployeeno)
                    .addComponent(jTextFieldEmployeeno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanelAdddetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelLastname)
                    .addComponent(jTextFieldLastname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanelAdddetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelFirstname)
                    .addComponent(jTextFieldFirstname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanelAdddetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextFieldBirthday, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelBirthday))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelAdddetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelAddress)
                    .addComponent(jTextFieldAddress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanelAdddetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelAdddetailsLayout.createSequentialGroup()
                        .addComponent(jLabelPhoneno)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabelSSSno))
                    .addGroup(jPanelAdddetailsLayout.createSequentialGroup()
                        .addComponent(jTextFieldPhoneno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldSSSno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelAdddetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextFieldPhilhealthno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabelPhilhealthno))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelAdddetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldTINno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelTINno))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelAdddetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldPagibigNo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelPagibigNo))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelAdddetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelAdddetailsLayout.createSequentialGroup()
                        .addComponent(jTextFieldStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldPosition, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldSupervisor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanelAdddetailsLayout.createSequentialGroup()
                        .addComponent(jLabelPagibigNo1)
                        .addGap(16, 16, 16)
                        .addComponent(jLabelPagibigNo2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabelPagibigNo3)))
                .addGap(141, 141, 141))
        );

        jTableAddEmployee.setAutoCreateRowSorter(true);
        jTableAddEmployee.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jTableAddEmployee.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Employee Number", "Last Name", "First name", "SSS No.", "PhilHealth No.", "TIN No.", "Pagibig No.", "Status", "Position", "Supervisor"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, true, true, true, true, true, true, true, true, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableAddEmployee.setToolTipText("");
        jTableAddEmployee.setSelectionForeground(new java.awt.Color(255, 255, 255));
        jScrollPaneAddEmployee.setViewportView(jTableAddEmployee);

        jLabelTitleAddEmployee.setBackground(new java.awt.Color(255, 255, 255));
        jLabelTitleAddEmployee.setFont(new java.awt.Font("Century Gothic", 1, 20)); // NOI18N
        jLabelTitleAddEmployee.setForeground(new java.awt.Color(255, 255, 255));
        jLabelTitleAddEmployee.setText("ADD EMPLOYEE");

        jButtonAdd.setBackground(new java.awt.Color(204, 0, 51));
        jButtonAdd.setForeground(new java.awt.Color(255, 255, 255));
        jButtonAdd.setText("Add");
        jButtonAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAddActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelAddEmployeeLayout = new javax.swing.GroupLayout(jPanelAddEmployee);
        jPanelAddEmployee.setLayout(jPanelAddEmployeeLayout);
        jPanelAddEmployeeLayout.setHorizontalGroup(
            jPanelAddEmployeeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelAddEmployeeLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanelAdddetails, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(jPanelAddEmployeeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelAddEmployeeLayout.createSequentialGroup()
                        .addComponent(jScrollPaneAddEmployee, javax.swing.GroupLayout.PREFERRED_SIZE, 548, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelAddEmployeeLayout.createSequentialGroup()
                        .addComponent(jButtonAdd)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabelTitleAddEmployee)
                        .addGap(14, 14, 14))))
        );
        jPanelAddEmployeeLayout.setVerticalGroup(
            jPanelAddEmployeeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelAddEmployeeLayout.createSequentialGroup()
                .addGap(140, 140, 140)
                .addGroup(jPanelAddEmployeeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelTitleAddEmployee)
                    .addComponent(jButtonAdd))
                .addGap(18, 18, 18)
                .addComponent(jScrollPaneAddEmployee, javax.swing.GroupLayout.PREFERRED_SIZE, 412, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(47, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelAddEmployeeLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanelAdddetails, javax.swing.GroupLayout.PREFERRED_SIZE, 530, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jTabbedMain.addTab("Add Employee", jPanelAddEmployee);

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

        jButtonAddEmployee.setBackground(new java.awt.Color(204, 0, 51));
        jButtonAddEmployee.setFont(new java.awt.Font("Century Gothic", 1, 13)); // NOI18N
        jButtonAddEmployee.setForeground(new java.awt.Color(255, 255, 255));
        jButtonAddEmployee.setText("Add Employee");
        jButtonAddEmployee.setMaximumSize(new java.awt.Dimension(124, 24));
        jButtonAddEmployee.setMinimumSize(new java.awt.Dimension(124, 24));
        jButtonAddEmployee.setPreferredSize(new java.awt.Dimension(90, 23));
        jButtonAddEmployee.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAddEmployeeActionPerformed(evt);
            }
        });
        jPanelSidebar.add(jButtonAddEmployee, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 150, 170, 40));

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
        jButtonAddEmployee.setBackground (new java.awt.Color(0,0,0));
        loadEmployeeData();
    }//GEN-LAST:event_jButtonManageEmployeesActionPerformed

    private void jButtonLeaveRequestsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonLeaveRequestsActionPerformed
        jTabbedMain.setSelectedIndex(1);
        jButtonManageEmployees.setBackground(new java.awt.Color(0,0,0));
        jButtonLeaveRequests.setBackground(Color.RED);
        jButtonAddEmployee.setBackground (new java.awt.Color(0,0,0));
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
        UpdateEmployeeForm updateEmployeeForm = new UpdateEmployeeForm(this);
        updateEmployeeForm.setLocationRelativeTo(this); // Centers relative to the parent JFrame
        updateEmployeeForm.setVisible(true);
        updateEmployeeForm.populateTextFields(jTableEmployeeRecords.getSelectedRow());
//        updateEmployee();
    }//GEN-LAST:event_jButtonUpdateActionPerformed

    private void jTextFieldEmployeenoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldEmployeenoKeyTyped
        boolean max = jTextFieldEmployeeno.getText().length() > 4;
        char empID = evt.getKeyChar();
        if(!Character.isDigit(empID)|| max){evt.consume();}
    }//GEN-LAST:event_jTextFieldEmployeenoKeyTyped

    private void jButtonAddEmployeeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAddEmployeeActionPerformed
        jTabbedMain.setSelectedIndex(2); 
        jButtonManageEmployees.setBackground(new java.awt.Color(0,0,0));
        jButtonLeaveRequests.setBackground(new java.awt.Color(0,0,0));
        jButtonAddEmployee.setBackground(Color.RED);
        loadEmployeeData();
        resetAddEmployeeForm();
    }//GEN-LAST:event_jButtonAddEmployeeActionPerformed

    private void jButtonAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAddActionPerformed
        addEmployee();
    }//GEN-LAST:event_jButtonAddActionPerformed

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
                new UpdateEmployeeForm(hrDashboard).setVisible(true);  //hrDashboard.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonAdd;
    private javax.swing.JButton jButtonAddEmployee;
    private javax.swing.JButton jButtonApprove;
    private javax.swing.JButton jButtonDelete;
    private javax.swing.JButton jButtonLeaveRequests;
    private javax.swing.JButton jButtonLogout;
    private javax.swing.JButton jButtonManageEmployees;
    private javax.swing.JButton jButtonReject;
    private javax.swing.JButton jButtonUpdate;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabelAddress;
    private javax.swing.JLabel jLabelBirthday;
    private javax.swing.JLabel jLabelDate;
    private javax.swing.JLabel jLabelEmployeeno;
    private javax.swing.JLabel jLabelFirstname;
    private javax.swing.JLabel jLabelGMT;
    private javax.swing.JLabel jLabelGreet;
    private javax.swing.JLabel jLabelLastname;
    private javax.swing.JLabel jLabelPagibigNo;
    private javax.swing.JLabel jLabelPagibigNo1;
    private javax.swing.JLabel jLabelPagibigNo2;
    private javax.swing.JLabel jLabelPagibigNo3;
    private javax.swing.JLabel jLabelPhilhealthno;
    private javax.swing.JLabel jLabelPhoneno;
    private javax.swing.JLabel jLabelSSSno;
    private javax.swing.JLabel jLabelSeparate;
    private javax.swing.JLabel jLabelTINno;
    private javax.swing.JLabel jLabelTime;
    private javax.swing.JLabel jLabelTitleAddEmployee;
    private javax.swing.JLabel jLabelTitleEmployeeList;
    private javax.swing.JLabel jLabelTitleLeaveRequests;
    private javax.swing.JLabel jLblUname1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanelAddEmployee;
    private javax.swing.JPanel jPanelAdddetails;
    private javax.swing.JPanel jPanelHRMain;
    private javax.swing.JPanel jPanelHeader;
    private javax.swing.JPanel jPanelLeaveRequest;
    private javax.swing.JPanel jPanelManageEmployee;
    private javax.swing.JPanel jPanelSidebar;
    private javax.swing.JScrollPane jScrollPaneAddEmployee;
    private javax.swing.JScrollPane jScrollPaneLeaveRequests;
    private javax.swing.JScrollPane jScrollPaneTableEmployeeRecords;
    private javax.swing.JTabbedPane jTabbedMain;
    private javax.swing.JTable jTableAddEmployee;
    private javax.swing.JTable jTableEmployeeRecords;
    private javax.swing.JTable jTableLeaveRequest;
    private javax.swing.JTextField jTextFieldAddress;
    private javax.swing.JTextField jTextFieldBirthday;
    private javax.swing.JTextField jTextFieldEmployeeno;
    private javax.swing.JTextField jTextFieldFirstname;
    private javax.swing.JTextField jTextFieldLastname;
    private javax.swing.JTextField jTextFieldPagibigNo;
    private javax.swing.JTextField jTextFieldPhilhealthno;
    private javax.swing.JTextField jTextFieldPhoneno;
    private javax.swing.JTextField jTextFieldPosition;
    private javax.swing.JTextField jTextFieldSSSno;
    private javax.swing.JTextField jTextFieldStatus;
    private javax.swing.JTextField jTextFieldSupervisor;
    private javax.swing.JTextField jTextFieldTINno;
    // End of variables declaration//GEN-END:variables
}
