/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package motorph9_MS2;

import data_reader9.EmployeeDetailsReader;
import data_reader9.LeaveRequestReader;
import java.awt.Color;
import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import motorph9_MS2.EmployeeLeaveTracker;
import motorph9_MS2.EmployeeUser;
import motorph9_MS2.LeaveRequest;
import motorph9_MS2.Login;
import motorph9_MS2.SalaryDetails;
import motorph9_MS2.User;

/**
 *
 * @author Four Lugtu
 */
public class EmployeeDashboard extends javax.swing.JFrame {
    
    private User loggedInUser; // To store the logged-in user
    private EmployeeDetailsReader employeeDetailsReader; // Use EmployeeDetailsReader
    private EmployeeLeaveTracker leaveTracker; // Use EmployeeLeaveTracker
    /**
     * Creates new form EmployeeDashboards
     */
    public EmployeeDashboard() {
        initComponents();
        setLocationRelativeTo(null); // Center the window
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Define close behavior
        employeeDetailsReader = new EmployeeDetailsReader(); // Initialize reader
    }

    public EmployeeDashboard(User user) {
        initComponents(); // Call initComponents() to initialize UI
        setLocationRelativeTo(null); // Center the window
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Define close behavior
        this.loggedInUser = user; // Store the logged-in user
        employeeDetailsReader = new EmployeeDetailsReader(); // Initialize reader
        leaveTracker = new EmployeeLeaveTracker(user.getEmployeeId()); //Initialize leave tracker
        displayWelcomeMessage(); // Call method to display welcome message (example)
        loadEmployeeDetails(); // Call method to load and display employee details
        loadSalaryInformation();
        initializeLeaveTypeComboBox(); // Populate leave types with balance
    }
    
    private void displayWelcomeMessage() {
        if (loggedInUser != null) {
            jLabelFirstName.setText(loggedInUser.getFirstName() + "!"); // Example: Set welcome label
            // You can display other user-specific information here as needed
        } else {
            jLabelFirstName.setText("Employee!"); // Default if user is null (shouldn't happen in login flow)
        }
    }
    
    private void loadEmployeeDetails() {
        if (loggedInUser != null) {
            try {
                // ✅ Use getEmployeeResetDetailsByNumber from EmployeeDetailsReader
                EmployeeUser employeeDetails = (EmployeeUser) employeeDetailsReader.getEmployeeDetailsByNumber(loggedInUser.getEmployeeId());
                if (employeeDetails != null) {
                    displayEmployeeDetails(employeeDetails); // ✅ Call method to populate UI
                } else {
                    JOptionPane.showMessageDialog(this, "Employee details not found in data file.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (IOException ex) {
                Logger.getLogger(EmployeeDashboard.class.getName()).log(Level.SEVERE, "Error reading employee details", ex);
                JOptionPane.showMessageDialog(this, "Error reading employee details from file.", "File Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void displayEmployeeDetails(EmployeeUser details) { // ✅ Parameter type is now EmployeeUser
        jTextFieldEmployeeID.setText(details.getEmployeeId()); // ✅ Use getEmployeeId()
        jTextFieldPosition.setText(details.getPosition());
        jTextFieldUsername.setText(loggedInUser.getUsername());
        jTextFieldName.setText(details.getFirstName() + " " + details.getLastName());
        jTextFieldBirthday.setText(details.getBirthday());
        jTextFieldAddress.setText(details.getAddress());
        jTextFieldPhoneNumber.setText(String.valueOf(details.getPhone())); // ✅ Use getPhone() and convert to String
        jTextFieldSSS.setText(details.getSSS()); // ✅ Use getSSS()
        jTextFieldPhilHealth.setText(details.getPhilHealth()); // ✅ Use getPhilHealth()
        jTextFieldTIN.setText(details.getTIN()); // ✅ Use getTIN()
        jTextFieldPagibig.setText(details.getPagibig()); // ✅ Use getPagibig()
        jTextFieldSupervisor.setText(details.getImmediateSupervisor()); // ✅ Use getImmediateSupervisor()
        jTextFieldStatus.setText(details.getStatus()); // ✅ Use getStatus()
        // Note: No JTextField for "Username" in your initComponents, using jTextFieldUsername for display
    }
    
    
    private void loadSalaryInformation() {
        if (loggedInUser != null && loggedInUser instanceof EmployeeUser) { // Checks for EmployeeUser
            try {
                EmployeeUser employee = (EmployeeUser) loggedInUser;

                // Get salary details from EmployeeUser
                SalaryDetails salaryDetails = employee.getSalaryDetails();

                // Update the UI
                updateSalaryUI(salaryDetails);

            } catch (IOException ex) {
                Logger.getLogger(EmployeeDashboard.class.getName()).log(Level.SEVERE, "Error reading salary details", ex);
                JOptionPane.showMessageDialog(this, "Error reading salary details.", "File Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            System.out.println("No logged-in EmployeeUser found!"); // Debugging step
        }
    }
    
    private void updateSalaryUI(SalaryDetails salary) {
        DecimalFormat formatter = new DecimalFormat("#,##0.00"); // Formats numbers like 12,345.67

        // Update Salary Fields
        jTextFieldGrossSalary.setText(formatter.format(salary.grossSalary()));
        jTextFieldNetSalary.setText(formatter.format(salary.netSalary()));
        jTextFieldHourlyRate.setText(formatter.format(salary.hourlyRate()));

        // Update Allowance Breakdown
        jTextFieldRiceSubsidy.setText(formatter.format(salary.riceSubsidy()));
        jTextFieldPhone.setText(formatter.format(salary.phoneAllowance()));
        jTextFieldClothing.setText(formatter.format(salary.clothingAllowance()));
        jTextFieldTotalAllowances.setText(formatter.format(salary.totalAllowances()));

        // Update Deduction Breakdown
        jTextFieldPagibigDeductions.setText(formatter.format(salary.pagibigDeduction()));
        jTextFieldPhilhealth.setText(formatter.format(salary.philHealthDeduction()));
        jTextFieldSSSDeductions.setText(formatter.format(salary.sssDeduction()));
        jTextFieldWithholdingTax.setText(formatter.format(salary.withholdingTax()));
        jTextFieldTotalDeductions.setText(formatter.format(salary.totalDeductions()));
    }
    
    private void loadRequests() {
        DefaultTableModel model = (DefaultTableModel) jTableRequestLogs.getModel();
        model.setRowCount(0);
        
        List<LeaveRequest> requests = new LeaveRequestReader().getAllLeaveRequests();
        for (LeaveRequest request : requests) {
            if (request.getEmployeeID().equals(loggedInUser.getEmployeeId())) {
                model.addRow(new Object[]{
                    request.getLeaveType(),
                    request.getDateRequest(),
                    request.getStartDate(),
                    request.getEndDate(),
                    request.getReason(),
                    request.getStatus(),
                    request.getApprover().isEmpty() ? "HR" : request.getApprover(),
                    request.getDateResponded() != null ? request.getDateResponded().toString() : "Pending"
                });
            }
        }
    }
    
    private void initializeLeaveTypeComboBox() {
        jComboBoxLeaveType.setModel(new DefaultComboBoxModel<>(new String[]{
            "Sick Leave (" + leaveTracker.getSickLeaveBalance() + " left)",
            "Vacation Leave (" + leaveTracker.getVacationLeaveBalance() + " left)",
            "Birthday Leave (" + leaveTracker.getBirthdayLeaveBalance() + " left)"
        }));
    }


    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanelEmployeeDashboard = new javax.swing.JPanel();
        Logo = new javax.swing.JLabel();
        jLabePH = new javax.swing.JLabel();
        jLabelFirstName = new javax.swing.JLabel();
        jLabelMotor = new javax.swing.JLabel();
        jLabelGreet = new javax.swing.JLabel();
        jLabelGMT = new javax.swing.JLabel();
        jLabelTime = new javax.swing.JLabel();
        jPanelMenuBar = new javax.swing.JPanel();
        jButtonAttendance = new javax.swing.JButton();
        jButtonRequests = new javax.swing.JButton();
        jButtonSalaryInfomation = new javax.swing.JButton();
        jButtonEmployeeDetails = new javax.swing.JButton();
        jPanelEmployeeMain = new javax.swing.JPanel();
        jTabbedPaneEmployee = new javax.swing.JTabbedPane();
        jPanelEmployeeDetails = new javax.swing.JPanel();
        jPanelPicture = new javax.swing.JPanel();
        jLabel2x2Picture = new javax.swing.JLabel();
        jLabelEmployeeDetails = new javax.swing.JLabel();
        jPanelDetails = new javax.swing.JPanel();
        jLabelEmployeeID = new javax.swing.JLabel();
        jLabelPosition = new javax.swing.JLabel();
        jTextFieldPosition = new javax.swing.JTextField();
        jTextFieldEmployeeID = new javax.swing.JTextField();
        jLabelUsername = new javax.swing.JLabel();
        jTextFieldUsername = new javax.swing.JTextField();
        jLabelName = new javax.swing.JLabel();
        jTextFieldName = new javax.swing.JTextField();
        jLabelBirthday = new javax.swing.JLabel();
        jTextFieldBirthday = new javax.swing.JTextField();
        jLabelAddress = new javax.swing.JLabel();
        jTextFieldAddress = new javax.swing.JTextField();
        jLabelPhoneNumber = new javax.swing.JLabel();
        jTextFieldPhoneNumber = new javax.swing.JTextField();
        jLabelSSS = new javax.swing.JLabel();
        jTextFieldSSS = new javax.swing.JTextField();
        jLabelPhilHealth = new javax.swing.JLabel();
        jTextFieldPhilHealth = new javax.swing.JTextField();
        jLabelTIN = new javax.swing.JLabel();
        jTextFieldTIN = new javax.swing.JTextField();
        jLabelPagibig = new javax.swing.JLabel();
        jTextFieldPagibig = new javax.swing.JTextField();
        jLabelSupervisor = new javax.swing.JLabel();
        jTextFieldSupervisor = new javax.swing.JTextField();
        jLabelStatus = new javax.swing.JLabel();
        jTextFieldStatus = new javax.swing.JTextField();
        jButtonLogout = new javax.swing.JButton();
        jPanelSalaryInformation = new javax.swing.JPanel();
        jPanelSalaryLogs = new javax.swing.JPanel();
        jScrollPaneSalaryLogs = new javax.swing.JScrollPane();
        jTableSalaryLogs = new javax.swing.JTable();
        jLabelSalaryInformation = new javax.swing.JLabel();
        jPanelSelectYearSalary = new javax.swing.JPanel();
        jComboBoxSelectYearSalary = new javax.swing.JComboBox<>();
        jPanelSalaryCalculations = new javax.swing.JPanel();
        jLabelNetSalary = new javax.swing.JLabel();
        jTextFieldNetSalary = new javax.swing.JTextField();
        jLabelHourlyRate = new javax.swing.JLabel();
        jLabelGrossSalary = new javax.swing.JLabel();
        jTextFieldHourlyRate = new javax.swing.JTextField();
        jTextFieldGrossSalary = new javax.swing.JTextField();
        jLabelSalaryLogs = new javax.swing.JLabel();
        jLabelDeductions = new javax.swing.JLabel();
        jPanelDeductions = new javax.swing.JPanel();
        jLabelPhilHealthDeductions = new javax.swing.JLabel();
        jLabelWithholdingTax = new javax.swing.JLabel();
        jTextFieldPhilhealth = new javax.swing.JTextField();
        jTextFieldWithholdingTax = new javax.swing.JTextField();
        jLabelSSSDeductions = new javax.swing.JLabel();
        jTextFieldSSSDeductions = new javax.swing.JTextField();
        jLabelPagibigDeductions = new javax.swing.JLabel();
        jTextFieldPagibigDeductions = new javax.swing.JTextField();
        jLabelAllowances = new javax.swing.JLabel();
        jPanelAllowances = new javax.swing.JPanel();
        jTextFieldPhone = new javax.swing.JTextField();
        jTextFieldRiceSubsidy = new javax.swing.JTextField();
        jLabelClothing = new javax.swing.JLabel();
        jLabelRiceSubsidy = new javax.swing.JLabel();
        jLabelPhone = new javax.swing.JLabel();
        jTextFieldClothing = new javax.swing.JTextField();
        jPanelTotalAllwances = new javax.swing.JPanel();
        jLabelTotalAllowances = new javax.swing.JLabel();
        jTextFieldTotalAllowances = new javax.swing.JTextField();
        jPanelTotalDeductions = new javax.swing.JPanel();
        jLabelTotalDeductions = new javax.swing.JLabel();
        jTextFieldTotalDeductions = new javax.swing.JTextField();
        jLabelSalaryCalculations = new javax.swing.JLabel();
        jPanelSelectMonthSalary = new javax.swing.JPanel();
        jComboBoxSelectMonthSalary = new javax.swing.JComboBox<>();
        jLabelSelectMonthSalary = new javax.swing.JLabel();
        jLabelSelectYearSalary = new javax.swing.JLabel();
        jButtonViewSalary = new javax.swing.JButton();
        jPanelRequests = new javax.swing.JPanel();
        jPanelAttendanceLogs = new javax.swing.JPanel();
        jScrollPaneAttendanceLogs = new javax.swing.JScrollPane();
        jTableRequestLogs = new javax.swing.JTable();
        jLabelRequests = new javax.swing.JLabel();
        jLabelCreateRequest = new javax.swing.JLabel();
        jLabelSelectMonth = new javax.swing.JLabel();
        jLabelSelectYear = new javax.swing.JLabel();
        jPanelCreateRquest = new javax.swing.JPanel();
        jComboBoxLeaveType = new javax.swing.JComboBox<>();
        jLabelReason = new javax.swing.JLabel();
        jLabelLeaveType = new javax.swing.JLabel();
        jLabelStartDate = new javax.swing.JLabel();
        jLabelEndDate = new javax.swing.JLabel();
        jTextFieldReason = new javax.swing.JTextField();
        jDateChooserStart = new com.toedter.calendar.JDateChooser();
        jDateChooserEnd = new com.toedter.calendar.JDateChooser();
        jPanelSelectMonth = new javax.swing.JPanel();
        jComboBoxSelectMonth = new javax.swing.JComboBox<>();
        jLabelAttendanceLogs = new javax.swing.JLabel();
        jPanelSelectYear = new javax.swing.JPanel();
        jComboBoxSelectYear = new javax.swing.JComboBox<>();
        jButtonViewRequests = new javax.swing.JButton();
        jButtonSubmitRequest = new javax.swing.JButton();
        jPanelAttendanceandTracker = new javax.swing.JPanel();
        jLabelAttendanceandTracker = new javax.swing.JLabel();
        jPanelAttendanceAndTracker = new javax.swing.JPanel();
        jScrollPaneAttenedanceandTracker = new javax.swing.JScrollPane();
        jTableAttendanceLogs = new javax.swing.JTable();
        jLabelTimeTracker = new javax.swing.JLabel();
        jLabelSelectYearAttendance = new javax.swing.JLabel();
        jLabelSelectMonthAttendance = new javax.swing.JLabel();
        jPanelSelectYearAttendance = new javax.swing.JPanel();
        jComboBoxSelectYearAttendance = new javax.swing.JComboBox<>();
        jPanelSelectMonthAttendance = new javax.swing.JPanel();
        jComboBoxSelectMonthAttendance = new javax.swing.JComboBox<>();
        jButtonAttendanceView = new javax.swing.JButton();
        jPanelTimeTracker = new javax.swing.JPanel();
        jButtonBreak = new javax.swing.JButton();
        jButtonTimeIn = new javax.swing.JButton();
        jButtonTimeOut = new javax.swing.JButton();
        jLabelAttendanceLog = new javax.swing.JLabel();
        jPanelHeaders = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(1060, 638));
        getContentPane().setLayout(null);

        jPanelEmployeeDashboard.setBackground(new java.awt.Color(0, 0, 0));
        jPanelEmployeeDashboard.setMaximumSize(new java.awt.Dimension(1041, 620));
        jPanelEmployeeDashboard.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Logo.setBackground(new java.awt.Color(0, 0, 102));
        Logo.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        Logo.setForeground(new java.awt.Color(255, 255, 255));
        Logo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/logos9/LogoMPH_(small1).png"))); // NOI18N
        Logo.setText("Username");
        jPanelEmployeeDashboard.add(Logo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, -10, 100, 110));

        jLabePH.setFont(new java.awt.Font("Century Gothic", 1, 36)); // NOI18N
        jLabePH.setForeground(new java.awt.Color(204, 0, 51));
        jLabePH.setText("PH");
        jPanelEmployeeDashboard.add(jLabePH, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, -20, 70, 120));

        jLabelFirstName.setFont(new java.awt.Font("Century Gothic", 0, 36)); // NOI18N
        jLabelFirstName.setForeground(new java.awt.Color(255, 255, 255));
        jLabelFirstName.setText("FirstName!");
        jPanelEmployeeDashboard.add(jLabelFirstName, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, -10, -1, 100));

        jLabelMotor.setFont(new java.awt.Font("Century Gothic", 1, 36)); // NOI18N
        jLabelMotor.setForeground(new java.awt.Color(255, 255, 255));
        jLabelMotor.setText("MOTOR");
        jPanelEmployeeDashboard.add(jLabelMotor, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, -10, -1, 100));

        jLabelGreet.setFont(new java.awt.Font("Century Gothic", 1, 36)); // NOI18N
        jLabelGreet.setForeground(new java.awt.Color(255, 255, 255));
        jLabelGreet.setText("Welcome,");
        jPanelEmployeeDashboard.add(jLabelGreet, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, -10, -1, 100));

        jLabelGMT.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabelGMT.setForeground(new java.awt.Color(255, 255, 255));
        jLabelGMT.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelGMT.setText("GMT+8 PH Time");
        jPanelEmployeeDashboard.add(jLabelGMT, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 10, 170, 30));

        jLabelTime.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        jLabelTime.setForeground(new java.awt.Color(255, 255, 255));
        jLabelTime.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelTime.setText("12:12:12 AM");
        jPanelEmployeeDashboard.add(jLabelTime, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 30, 170, 40));

        jPanelMenuBar.setBackground(new java.awt.Color(102, 0, 0));
        jPanelMenuBar.setForeground(new java.awt.Color(51, 0, 0));
        jPanelMenuBar.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButtonAttendance.setBackground(new java.awt.Color(204, 0, 51));
        jButtonAttendance.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        jButtonAttendance.setForeground(new java.awt.Color(255, 255, 255));
        jButtonAttendance.setText("Attendance");
        jButtonAttendance.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAttendanceActionPerformed(evt);
            }
        });
        jPanelMenuBar.add(jButtonAttendance, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 10, 200, 50));

        jButtonRequests.setBackground(new java.awt.Color(204, 0, 51));
        jButtonRequests.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        jButtonRequests.setForeground(new java.awt.Color(255, 255, 255));
        jButtonRequests.setText("Requests");
        jButtonRequests.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRequestsActionPerformed(evt);
            }
        });
        jPanelMenuBar.add(jButtonRequests, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 10, 210, 50));

        jButtonSalaryInfomation.setBackground(new java.awt.Color(204, 0, 51));
        jButtonSalaryInfomation.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        jButtonSalaryInfomation.setForeground(new java.awt.Color(255, 255, 255));
        jButtonSalaryInfomation.setText("Salary Information");
        jButtonSalaryInfomation.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSalaryInfomationActionPerformed(evt);
            }
        });
        jPanelMenuBar.add(jButtonSalaryInfomation, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 10, 200, 50));

        jButtonEmployeeDetails.setBackground(new java.awt.Color(204, 0, 51));
        jButtonEmployeeDetails.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        jButtonEmployeeDetails.setForeground(new java.awt.Color(255, 255, 255));
        jButtonEmployeeDetails.setText("Employee Details");
        jButtonEmployeeDetails.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonEmployeeDetailsActionPerformed(evt);
            }
        });
        jPanelMenuBar.add(jButtonEmployeeDetails, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 10, 200, 50));

        jPanelEmployeeDashboard.add(jPanelMenuBar, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 90, 1020, 70));

        jPanelEmployeeMain.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanelEmployeeDetails.setBackground(new java.awt.Color(51, 0, 0));
        jPanelEmployeeDetails.setForeground(new java.awt.Color(255, 255, 255));
        jPanelEmployeeDetails.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanelPicture.setBackground(new java.awt.Color(255, 255, 255));
        jPanelPicture.setBorder(new javax.swing.border.MatteBorder(null));
        jPanelPicture.setForeground(new java.awt.Color(0, 0, 0));
        jPanelPicture.setToolTipText("");
        jPanelPicture.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N

        jLabel2x2Picture.setBackground(new java.awt.Color(0, 0, 0));
        jLabel2x2Picture.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
        jLabel2x2Picture.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2x2Picture.setText("2x2 Picture");

        javax.swing.GroupLayout jPanelPictureLayout = new javax.swing.GroupLayout(jPanelPicture);
        jPanelPicture.setLayout(jPanelPictureLayout);
        jPanelPictureLayout.setHorizontalGroup(
            jPanelPictureLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelPictureLayout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jLabel2x2Picture)
                .addContainerGap(25, Short.MAX_VALUE))
        );
        jPanelPictureLayout.setVerticalGroup(
            jPanelPictureLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelPictureLayout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addComponent(jLabel2x2Picture)
                .addContainerGap(46, Short.MAX_VALUE))
        );

        jPanelEmployeeDetails.add(jPanelPicture, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 70, 120, 110));

        jLabelEmployeeDetails.setBackground(new java.awt.Color(255, 255, 255));
        jLabelEmployeeDetails.setFont(new java.awt.Font("Century Gothic", 1, 25)); // NOI18N
        jLabelEmployeeDetails.setForeground(new java.awt.Color(255, 255, 255));
        jLabelEmployeeDetails.setText("Employee Details");
        jPanelEmployeeDetails.add(jLabelEmployeeDetails, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, -10, -1, 90));

        jPanelDetails.setBackground(new java.awt.Color(204, 0, 51));
        jPanelDetails.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabelEmployeeID.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabelEmployeeID.setForeground(new java.awt.Color(255, 255, 255));
        jLabelEmployeeID.setText("Employee ID:");
        jPanelDetails.add(jLabelEmployeeID, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 20, -1, -1));

        jLabelPosition.setBackground(new java.awt.Color(255, 255, 255));
        jLabelPosition.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabelPosition.setForeground(new java.awt.Color(255, 255, 255));
        jLabelPosition.setText("Position:");
        jPanelDetails.add(jLabelPosition, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 380, 60, -1));

        jTextFieldPosition.setBackground(new java.awt.Color(255, 255, 255));
        jPanelDetails.add(jTextFieldPosition, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 380, 270, -1));

        jTextFieldEmployeeID.setBackground(new java.awt.Color(255, 255, 255));
        jPanelDetails.add(jTextFieldEmployeeID, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 20, 270, -1));

        jLabelUsername.setBackground(new java.awt.Color(255, 255, 255));
        jLabelUsername.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabelUsername.setForeground(new java.awt.Color(255, 255, 255));
        jLabelUsername.setText("Username:");
        jPanelDetails.add(jLabelUsername, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 50, -1, -1));

        jTextFieldUsername.setBackground(new java.awt.Color(255, 255, 255));
        jPanelDetails.add(jTextFieldUsername, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 50, 270, -1));

        jLabelName.setBackground(new java.awt.Color(255, 255, 255));
        jLabelName.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabelName.setForeground(new java.awt.Color(255, 255, 255));
        jLabelName.setText("Name:");
        jPanelDetails.add(jLabelName, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 80, -1, -1));

        jTextFieldName.setBackground(new java.awt.Color(255, 255, 255));
        jPanelDetails.add(jTextFieldName, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 80, 270, -1));

        jLabelBirthday.setBackground(new java.awt.Color(255, 255, 255));
        jLabelBirthday.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabelBirthday.setForeground(new java.awt.Color(255, 255, 255));
        jLabelBirthday.setText("Birthday:");
        jPanelDetails.add(jLabelBirthday, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 110, -1, -1));

        jTextFieldBirthday.setBackground(new java.awt.Color(255, 255, 255));
        jPanelDetails.add(jTextFieldBirthday, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 110, 270, -1));

        jLabelAddress.setBackground(new java.awt.Color(255, 255, 255));
        jLabelAddress.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabelAddress.setForeground(new java.awt.Color(255, 255, 255));
        jLabelAddress.setText("Address:");
        jPanelDetails.add(jLabelAddress, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 140, -1, -1));

        jTextFieldAddress.setBackground(new java.awt.Color(255, 255, 255));
        jPanelDetails.add(jTextFieldAddress, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 140, 270, -1));

        jLabelPhoneNumber.setBackground(new java.awt.Color(255, 255, 255));
        jLabelPhoneNumber.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabelPhoneNumber.setForeground(new java.awt.Color(255, 255, 255));
        jLabelPhoneNumber.setText("Phone Number:");
        jPanelDetails.add(jLabelPhoneNumber, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 170, -1, -1));

        jTextFieldPhoneNumber.setBackground(new java.awt.Color(255, 255, 255));
        jPanelDetails.add(jTextFieldPhoneNumber, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 170, 270, -1));

        jLabelSSS.setBackground(new java.awt.Color(255, 255, 255));
        jLabelSSS.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabelSSS.setForeground(new java.awt.Color(255, 255, 255));
        jLabelSSS.setText("SSS:");
        jPanelDetails.add(jLabelSSS, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 200, -1, -1));

        jTextFieldSSS.setBackground(new java.awt.Color(255, 255, 255));
        jPanelDetails.add(jTextFieldSSS, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 200, 270, -1));

        jLabelPhilHealth.setBackground(new java.awt.Color(255, 255, 255));
        jLabelPhilHealth.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabelPhilHealth.setForeground(new java.awt.Color(255, 255, 255));
        jLabelPhilHealth.setText("PhilHealth:");
        jPanelDetails.add(jLabelPhilHealth, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 230, 80, -1));

        jTextFieldPhilHealth.setBackground(new java.awt.Color(255, 255, 255));
        jPanelDetails.add(jTextFieldPhilHealth, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 230, 270, -1));

        jLabelTIN.setBackground(new java.awt.Color(255, 255, 255));
        jLabelTIN.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabelTIN.setForeground(new java.awt.Color(255, 255, 255));
        jLabelTIN.setText("TIN:");
        jPanelDetails.add(jLabelTIN, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 260, 30, -1));

        jTextFieldTIN.setBackground(new java.awt.Color(255, 255, 255));
        jPanelDetails.add(jTextFieldTIN, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 260, 270, -1));

        jLabelPagibig.setBackground(new java.awt.Color(255, 255, 255));
        jLabelPagibig.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabelPagibig.setForeground(new java.awt.Color(255, 255, 255));
        jLabelPagibig.setText("PAGIBIG:");
        jPanelDetails.add(jLabelPagibig, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 290, 80, -1));

        jTextFieldPagibig.setBackground(new java.awt.Color(255, 255, 255));
        jPanelDetails.add(jTextFieldPagibig, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 290, 270, -1));

        jLabelSupervisor.setBackground(new java.awt.Color(255, 255, 255));
        jLabelSupervisor.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabelSupervisor.setForeground(new java.awt.Color(255, 255, 255));
        jLabelSupervisor.setText("Immediate Supervisor:");
        jPanelDetails.add(jLabelSupervisor, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 320, 170, -1));

        jTextFieldSupervisor.setBackground(new java.awt.Color(255, 255, 255));
        jPanelDetails.add(jTextFieldSupervisor, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 320, 270, -1));

        jLabelStatus.setBackground(new java.awt.Color(255, 255, 255));
        jLabelStatus.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabelStatus.setForeground(new java.awt.Color(255, 255, 255));
        jLabelStatus.setText("Status:");
        jPanelDetails.add(jLabelStatus, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 350, 50, -1));

        jTextFieldStatus.setBackground(new java.awt.Color(255, 255, 255));
        jPanelDetails.add(jTextFieldStatus, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 350, 270, -1));

        jPanelEmployeeDetails.add(jPanelDetails, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 70, 500, 420));

        jButtonLogout.setBackground(new java.awt.Color(255, 255, 255));
        jButtonLogout.setFont(new java.awt.Font("Century Gothic", 1, 13)); // NOI18N
        jButtonLogout.setForeground(new java.awt.Color(0, 0, 0));
        jButtonLogout.setText("Logout");
        jButtonLogout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonLogoutActionPerformed(evt);
            }
        });
        jPanelEmployeeDetails.add(jButtonLogout, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 460, 140, 30));

        jTabbedPaneEmployee.addTab("Employee Details", jPanelEmployeeDetails);

        jPanelSalaryInformation.setBackground(new java.awt.Color(51, 0, 0));
        jPanelSalaryInformation.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanelSalaryLogs.setBackground(new java.awt.Color(204, 0, 51));
        jPanelSalaryLogs.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTableSalaryLogs.setBackground(new java.awt.Color(255, 255, 255));
        jTableSalaryLogs.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Employee No.", "Month", "Year", "Gross Salary", "Total Allowance", "Total Deductions", "Net Month Salary"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPaneSalaryLogs.setViewportView(jTableSalaryLogs);
        if (jTableSalaryLogs.getColumnModel().getColumnCount() > 0) {
            jTableSalaryLogs.getColumnModel().getColumn(0).setResizable(false);
            jTableSalaryLogs.getColumnModel().getColumn(1).setResizable(false);
            jTableSalaryLogs.getColumnModel().getColumn(2).setResizable(false);
            jTableSalaryLogs.getColumnModel().getColumn(3).setResizable(false);
        }

        jPanelSalaryLogs.add(jScrollPaneSalaryLogs, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 530, 240));

        jPanelSalaryInformation.add(jPanelSalaryLogs, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 110, 550, 260));

        jLabelSalaryInformation.setBackground(new java.awt.Color(255, 255, 255));
        jLabelSalaryInformation.setFont(new java.awt.Font("Century Gothic", 1, 25)); // NOI18N
        jLabelSalaryInformation.setForeground(new java.awt.Color(255, 255, 255));
        jLabelSalaryInformation.setText("Salary Information");
        jPanelSalaryInformation.add(jLabelSalaryInformation, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 30, -1, 90));

        jPanelSelectYearSalary.setBackground(new java.awt.Color(204, 0, 51));
        jPanelSelectYearSalary.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jComboBoxSelectYearSalary.setBackground(new java.awt.Color(255, 255, 255));
        jComboBoxSelectYearSalary.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanelSelectYearSalary.add(jComboBoxSelectYearSalary, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 230, 40));

        jPanelSalaryInformation.add(jPanelSelectYearSalary, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 400, 270, 60));

        jPanelSalaryCalculations.setBackground(new java.awt.Color(204, 0, 51));
        jPanelSalaryCalculations.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabelNetSalary.setBackground(new java.awt.Color(255, 255, 255));
        jLabelNetSalary.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabelNetSalary.setForeground(new java.awt.Color(255, 255, 255));
        jLabelNetSalary.setText("Net Salary:");
        jPanelSalaryCalculations.add(jLabelNetSalary, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 60, -1, 40));

        jTextFieldNetSalary.setBackground(new java.awt.Color(255, 255, 255));
        jPanelSalaryCalculations.add(jTextFieldNetSalary, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 70, 80, -1));

        jLabelHourlyRate.setBackground(new java.awt.Color(255, 255, 255));
        jLabelHourlyRate.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabelHourlyRate.setForeground(new java.awt.Color(255, 255, 255));
        jLabelHourlyRate.setText("Hourly Rate:");
        jPanelSalaryCalculations.add(jLabelHourlyRate, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 0, -1, 40));

        jLabelGrossSalary.setBackground(new java.awt.Color(255, 255, 255));
        jLabelGrossSalary.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabelGrossSalary.setForeground(new java.awt.Color(255, 255, 255));
        jLabelGrossSalary.setText("Gross Salary:");
        jPanelSalaryCalculations.add(jLabelGrossSalary, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 30, 100, 40));

        jTextFieldHourlyRate.setBackground(new java.awt.Color(255, 255, 255));
        jPanelSalaryCalculations.add(jTextFieldHourlyRate, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 10, 80, -1));

        jTextFieldGrossSalary.setBackground(new java.awt.Color(255, 255, 255));
        jPanelSalaryCalculations.add(jTextFieldGrossSalary, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 40, 80, -1));

        jPanelSalaryInformation.add(jPanelSalaryCalculations, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 400, 400, 100));

        jLabelSalaryLogs.setBackground(new java.awt.Color(255, 255, 255));
        jLabelSalaryLogs.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        jLabelSalaryLogs.setForeground(new java.awt.Color(255, 255, 255));
        jLabelSalaryLogs.setText("Salary Logs");
        jPanelSalaryInformation.add(jLabelSalaryLogs, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, -1, 80));

        jLabelDeductions.setBackground(new java.awt.Color(255, 255, 255));
        jLabelDeductions.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        jLabelDeductions.setForeground(new java.awt.Color(255, 255, 255));
        jLabelDeductions.setText("Deductions");
        jPanelSalaryInformation.add(jLabelDeductions, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 190, -1, 70));

        jPanelDeductions.setBackground(new java.awt.Color(204, 0, 51));
        jPanelDeductions.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabelPhilHealthDeductions.setBackground(new java.awt.Color(255, 255, 255));
        jLabelPhilHealthDeductions.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabelPhilHealthDeductions.setForeground(new java.awt.Color(255, 255, 255));
        jLabelPhilHealthDeductions.setText("PhilHealth:");
        jPanelDeductions.add(jLabelPhilHealthDeductions, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 30, 80, 40));

        jLabelWithholdingTax.setBackground(new java.awt.Color(255, 255, 255));
        jLabelWithholdingTax.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        jLabelWithholdingTax.setForeground(new java.awt.Color(255, 255, 255));
        jLabelWithholdingTax.setText("Withholding Tax:");
        jPanelDeductions.add(jLabelWithholdingTax, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 100, 40));

        jTextFieldPhilhealth.setBackground(new java.awt.Color(255, 255, 255));
        jPanelDeductions.add(jTextFieldPhilhealth, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 40, 80, -1));

        jTextFieldWithholdingTax.setBackground(new java.awt.Color(255, 255, 255));
        jPanelDeductions.add(jTextFieldWithholdingTax, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 100, 80, -1));

        jLabelSSSDeductions.setBackground(new java.awt.Color(255, 255, 255));
        jLabelSSSDeductions.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabelSSSDeductions.setForeground(new java.awt.Color(255, 255, 255));
        jLabelSSSDeductions.setText("SSS:");
        jPanelDeductions.add(jLabelSSSDeductions, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 60, 40, 40));

        jTextFieldSSSDeductions.setBackground(new java.awt.Color(255, 255, 255));
        jPanelDeductions.add(jTextFieldSSSDeductions, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 70, 80, -1));

        jLabelPagibigDeductions.setBackground(new java.awt.Color(255, 255, 255));
        jLabelPagibigDeductions.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabelPagibigDeductions.setForeground(new java.awt.Color(255, 255, 255));
        jLabelPagibigDeductions.setText("PAGIBIG:");
        jPanelDeductions.add(jLabelPagibigDeductions, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 0, -1, 40));

        jTextFieldPagibigDeductions.setBackground(new java.awt.Color(255, 255, 255));
        jPanelDeductions.add(jTextFieldPagibigDeductions, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 10, 80, -1));

        jPanelSalaryInformation.add(jPanelDeductions, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 240, 200, 130));

        jLabelAllowances.setBackground(new java.awt.Color(255, 255, 255));
        jLabelAllowances.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        jLabelAllowances.setForeground(new java.awt.Color(255, 255, 255));
        jLabelAllowances.setText("Allowances");
        jPanelSalaryInformation.add(jLabelAllowances, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 70, -1, 50));

        jPanelAllowances.setBackground(new java.awt.Color(204, 0, 51));
        jPanelAllowances.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTextFieldPhone.setBackground(new java.awt.Color(255, 255, 255));
        jPanelAllowances.add(jTextFieldPhone, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 70, 80, -1));

        jTextFieldRiceSubsidy.setBackground(new java.awt.Color(255, 255, 255));
        jPanelAllowances.add(jTextFieldRiceSubsidy, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 40, 80, -1));

        jLabelClothing.setBackground(new java.awt.Color(255, 255, 255));
        jLabelClothing.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabelClothing.setForeground(new java.awt.Color(255, 255, 255));
        jLabelClothing.setText("Clothing:");
        jPanelAllowances.add(jLabelClothing, new org.netbeans.lib.awtextra.AbsoluteConstraints(38, 0, -1, 40));

        jLabelRiceSubsidy.setBackground(new java.awt.Color(255, 255, 255));
        jLabelRiceSubsidy.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabelRiceSubsidy.setForeground(new java.awt.Color(255, 255, 255));
        jLabelRiceSubsidy.setText("Rice Subsidy:");
        jPanelAllowances.add(jLabelRiceSubsidy, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 100, 40));

        jLabelPhone.setBackground(new java.awt.Color(255, 255, 255));
        jLabelPhone.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabelPhone.setForeground(new java.awt.Color(255, 255, 255));
        jLabelPhone.setText("Phone:");
        jPanelAllowances.add(jLabelPhone, new org.netbeans.lib.awtextra.AbsoluteConstraints(49, 60, 60, 40));

        jTextFieldClothing.setBackground(new java.awt.Color(255, 255, 255));
        jPanelAllowances.add(jTextFieldClothing, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 10, 80, -1));

        jPanelSalaryInformation.add(jPanelAllowances, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 110, 200, 100));

        jPanelTotalAllwances.setBackground(new java.awt.Color(204, 0, 51));
        jPanelTotalAllwances.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabelTotalAllowances.setBackground(new java.awt.Color(255, 255, 255));
        jLabelTotalAllowances.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabelTotalAllowances.setForeground(new java.awt.Color(255, 255, 255));
        jLabelTotalAllowances.setText("TOTAL:");
        jPanelTotalAllwances.add(jLabelTotalAllowances, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 20, -1, 50));

        jTextFieldTotalAllowances.setBackground(new java.awt.Color(255, 255, 255));
        jPanelTotalAllwances.add(jTextFieldTotalAllowances, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 32, 80, 30));

        jPanelSalaryInformation.add(jPanelTotalAllwances, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 110, 190, 100));

        jPanelTotalDeductions.setBackground(new java.awt.Color(204, 0, 51));
        jPanelTotalDeductions.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabelTotalDeductions.setBackground(new java.awt.Color(255, 255, 255));
        jLabelTotalDeductions.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabelTotalDeductions.setForeground(new java.awt.Color(255, 255, 255));
        jLabelTotalDeductions.setText("TOTAL:");
        jPanelTotalDeductions.add(jLabelTotalDeductions, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 40, -1, 50));

        jTextFieldTotalDeductions.setBackground(new java.awt.Color(255, 255, 255));
        jPanelTotalDeductions.add(jTextFieldTotalDeductions, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 50, 80, 30));

        jPanelSalaryInformation.add(jPanelTotalDeductions, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 240, 190, 130));

        jLabelSalaryCalculations.setBackground(new java.awt.Color(255, 255, 255));
        jLabelSalaryCalculations.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        jLabelSalaryCalculations.setForeground(new java.awt.Color(255, 255, 255));
        jLabelSalaryCalculations.setText("Salary Calculations");
        jPanelSalaryInformation.add(jLabelSalaryCalculations, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 350, -1, 70));

        jPanelSelectMonthSalary.setBackground(new java.awt.Color(204, 0, 51));
        jPanelSelectMonthSalary.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jComboBoxSelectMonthSalary.setBackground(new java.awt.Color(255, 255, 255));
        jComboBoxSelectMonthSalary.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanelSelectMonthSalary.add(jComboBoxSelectMonthSalary, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 230, 40));

        jPanelSalaryInformation.add(jPanelSelectMonthSalary, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 400, 270, 60));

        jLabelSelectMonthSalary.setBackground(new java.awt.Color(255, 255, 255));
        jLabelSelectMonthSalary.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        jLabelSelectMonthSalary.setForeground(new java.awt.Color(255, 255, 255));
        jLabelSelectMonthSalary.setText("Select Month");
        jPanelSalaryInformation.add(jLabelSelectMonthSalary, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 350, -1, 70));

        jLabelSelectYearSalary.setBackground(new java.awt.Color(255, 255, 255));
        jLabelSelectYearSalary.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        jLabelSelectYearSalary.setForeground(new java.awt.Color(255, 255, 255));
        jLabelSelectYearSalary.setText("Select Year");
        jPanelSalaryInformation.add(jLabelSelectYearSalary, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 350, -1, 70));

        jButtonViewSalary.setBackground(new java.awt.Color(204, 0, 51));
        jButtonViewSalary.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jButtonViewSalary.setForeground(new java.awt.Color(255, 255, 255));
        jButtonViewSalary.setText("View");
        jPanelSalaryInformation.add(jButtonViewSalary, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 470, 160, 30));

        jTabbedPaneEmployee.addTab("Salary Information", jPanelSalaryInformation);

        jPanelRequests.setBackground(new java.awt.Color(51, 0, 0));
        jPanelRequests.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanelAttendanceLogs.setBackground(new java.awt.Color(204, 0, 51));
        jPanelAttendanceLogs.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTableRequestLogs.setBackground(new java.awt.Color(255, 255, 255));
        jTableRequestLogs.setForeground(new java.awt.Color(0, 0, 0));
        jTableRequestLogs.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Leave Type", "Date Request", "Start Date", "End Date", "Reason", "Status", "Approver", "Date Approved"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPaneAttendanceLogs.setViewportView(jTableRequestLogs);

        jPanelAttendanceLogs.add(jScrollPaneAttendanceLogs, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 660, 240));

        jPanelRequests.add(jPanelAttendanceLogs, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 110, 680, 260));

        jLabelRequests.setBackground(new java.awt.Color(255, 255, 255));
        jLabelRequests.setFont(new java.awt.Font("Century Gothic", 1, 25)); // NOI18N
        jLabelRequests.setForeground(new java.awt.Color(255, 255, 255));
        jLabelRequests.setText("Requests");
        jPanelRequests.add(jLabelRequests, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 30, -1, 90));

        jLabelCreateRequest.setBackground(new java.awt.Color(255, 255, 255));
        jLabelCreateRequest.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        jLabelCreateRequest.setForeground(new java.awt.Color(255, 255, 255));
        jLabelCreateRequest.setText("Create Request");
        jPanelRequests.add(jLabelCreateRequest, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 70, -1, 80));

        jLabelSelectMonth.setBackground(new java.awt.Color(255, 255, 255));
        jLabelSelectMonth.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        jLabelSelectMonth.setForeground(new java.awt.Color(255, 255, 255));
        jLabelSelectMonth.setText("Select Month");
        jPanelRequests.add(jLabelSelectMonth, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 350, -1, 70));

        jLabelSelectYear.setBackground(new java.awt.Color(255, 255, 255));
        jLabelSelectYear.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        jLabelSelectYear.setForeground(new java.awt.Color(255, 255, 255));
        jLabelSelectYear.setText("Select Year");
        jPanelRequests.add(jLabelSelectYear, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 350, -1, 70));

        jPanelCreateRquest.setBackground(new java.awt.Color(204, 0, 51));
        jPanelCreateRquest.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jComboBoxLeaveType.setBackground(new java.awt.Color(255, 255, 255));
        jComboBoxLeaveType.setForeground(new java.awt.Color(0, 0, 0));
        jComboBoxLeaveType.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Vacation Leave", "Sick Leave", "Birthday Leave" }));
        jPanelCreateRquest.add(jComboBoxLeaveType, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 10, 170, -1));

        jLabelReason.setBackground(new java.awt.Color(255, 255, 255));
        jLabelReason.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabelReason.setForeground(new java.awt.Color(255, 255, 255));
        jLabelReason.setText("Reason:");
        jPanelCreateRquest.add(jLabelReason, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 120, 80, 40));

        jLabelLeaveType.setBackground(new java.awt.Color(255, 255, 255));
        jLabelLeaveType.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabelLeaveType.setForeground(new java.awt.Color(255, 255, 255));
        jLabelLeaveType.setText("Leave Type:");
        jPanelCreateRquest.add(jLabelLeaveType, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 90, 40));

        jLabelStartDate.setBackground(new java.awt.Color(255, 255, 255));
        jLabelStartDate.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabelStartDate.setForeground(new java.awt.Color(255, 255, 255));
        jLabelStartDate.setText("Start Date:");
        jPanelCreateRquest.add(jLabelStartDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 80, 40));

        jLabelEndDate.setBackground(new java.awt.Color(255, 255, 255));
        jLabelEndDate.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabelEndDate.setForeground(new java.awt.Color(255, 255, 255));
        jLabelEndDate.setText("End Date:");
        jPanelCreateRquest.add(jLabelEndDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, 80, 40));

        jTextFieldReason.setBackground(new java.awt.Color(255, 255, 255));
        jTextFieldReason.setForeground(new java.awt.Color(0, 0, 0));
        jTextFieldReason.setText("Please put reason");
        jPanelCreateRquest.add(jTextFieldReason, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 160, 260, 150));

        jDateChooserStart.setBackground(new java.awt.Color(255, 255, 255));
        jDateChooserStart.setForeground(new java.awt.Color(0, 0, 0));
        jPanelCreateRquest.add(jDateChooserStart, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 50, 170, -1));

        jDateChooserEnd.setBackground(new java.awt.Color(255, 255, 255));
        jDateChooserEnd.setForeground(new java.awt.Color(0, 0, 0));
        jPanelCreateRquest.add(jDateChooserEnd, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 90, 170, -1));

        jPanelRequests.add(jPanelCreateRquest, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 130, 280, 320));

        jPanelSelectMonth.setBackground(new java.awt.Color(204, 0, 51));
        jPanelSelectMonth.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jComboBoxSelectMonth.setBackground(new java.awt.Color(255, 255, 255));
        jComboBoxSelectMonth.setForeground(new java.awt.Color(0, 0, 0));
        jComboBoxSelectMonth.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanelSelectMonth.add(jComboBoxSelectMonth, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 230, 40));

        jPanelRequests.add(jPanelSelectMonth, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 400, 270, 60));

        jLabelAttendanceLogs.setBackground(new java.awt.Color(255, 255, 255));
        jLabelAttendanceLogs.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        jLabelAttendanceLogs.setForeground(new java.awt.Color(255, 255, 255));
        jLabelAttendanceLogs.setText("Request Logs");
        jPanelRequests.add(jLabelAttendanceLogs, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, -1, 80));

        jPanelSelectYear.setBackground(new java.awt.Color(204, 0, 51));
        jPanelSelectYear.setForeground(new java.awt.Color(0, 0, 0));
        jPanelSelectYear.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jComboBoxSelectYear.setBackground(new java.awt.Color(255, 255, 255));
        jComboBoxSelectYear.setForeground(new java.awt.Color(0, 0, 0));
        jComboBoxSelectYear.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanelSelectYear.add(jComboBoxSelectYear, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 230, 40));

        jPanelRequests.add(jPanelSelectYear, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 400, 270, 60));

        jButtonViewRequests.setBackground(new java.awt.Color(204, 0, 51));
        jButtonViewRequests.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jButtonViewRequests.setForeground(new java.awt.Color(255, 255, 255));
        jButtonViewRequests.setText("View");
        jPanelRequests.add(jButtonViewRequests, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 470, 160, 30));

        jButtonSubmitRequest.setBackground(new java.awt.Color(204, 0, 51));
        jButtonSubmitRequest.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jButtonSubmitRequest.setForeground(new java.awt.Color(255, 255, 255));
        jButtonSubmitRequest.setText("Submit");
        jButtonSubmitRequest.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSubmitRequestActionPerformed(evt);
            }
        });
        jPanelRequests.add(jButtonSubmitRequest, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 460, 160, 30));

        jTabbedPaneEmployee.addTab("Requests", jPanelRequests);

        jPanelAttendanceandTracker.setBackground(new java.awt.Color(51, 0, 0));
        jPanelAttendanceandTracker.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabelAttendanceandTracker.setBackground(new java.awt.Color(255, 255, 255));
        jLabelAttendanceandTracker.setFont(new java.awt.Font("Century Gothic", 1, 25)); // NOI18N
        jLabelAttendanceandTracker.setForeground(new java.awt.Color(255, 255, 255));
        jLabelAttendanceandTracker.setText("Attendance & Tracker");
        jPanelAttendanceandTracker.add(jLabelAttendanceandTracker, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 30, -1, 90));

        jPanelAttendanceAndTracker.setBackground(new java.awt.Color(204, 0, 51));
        jPanelAttendanceAndTracker.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTableAttendanceLogs.setBackground(new java.awt.Color(255, 255, 255));
        jTableAttendanceLogs.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPaneAttenedanceandTracker.setViewportView(jTableAttendanceLogs);

        jPanelAttendanceAndTracker.add(jScrollPaneAttenedanceandTracker, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 530, 240));

        jPanelAttendanceandTracker.add(jPanelAttendanceAndTracker, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 110, 560, 260));

        jLabelTimeTracker.setBackground(new java.awt.Color(255, 255, 255));
        jLabelTimeTracker.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        jLabelTimeTracker.setForeground(new java.awt.Color(255, 255, 255));
        jLabelTimeTracker.setText("Time Tracker");
        jPanelAttendanceandTracker.add(jLabelTimeTracker, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 80, -1, 80));

        jLabelSelectYearAttendance.setBackground(new java.awt.Color(255, 255, 255));
        jLabelSelectYearAttendance.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        jLabelSelectYearAttendance.setForeground(new java.awt.Color(255, 255, 255));
        jLabelSelectYearAttendance.setText("Select Year");
        jPanelAttendanceandTracker.add(jLabelSelectYearAttendance, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 350, -1, 70));

        jLabelSelectMonthAttendance.setBackground(new java.awt.Color(255, 255, 255));
        jLabelSelectMonthAttendance.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        jLabelSelectMonthAttendance.setForeground(new java.awt.Color(255, 255, 255));
        jLabelSelectMonthAttendance.setText("Select Month");
        jPanelAttendanceandTracker.add(jLabelSelectMonthAttendance, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 350, -1, 70));

        jPanelSelectYearAttendance.setBackground(new java.awt.Color(204, 0, 51));
        jPanelSelectYearAttendance.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jComboBoxSelectYearAttendance.setBackground(new java.awt.Color(255, 255, 255));
        jComboBoxSelectYearAttendance.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanelSelectYearAttendance.add(jComboBoxSelectYearAttendance, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 230, 40));

        jPanelAttendanceandTracker.add(jPanelSelectYearAttendance, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 400, 270, 60));

        jPanelSelectMonthAttendance.setBackground(new java.awt.Color(204, 0, 51));
        jPanelSelectMonthAttendance.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jComboBoxSelectMonthAttendance.setBackground(new java.awt.Color(255, 255, 255));
        jComboBoxSelectMonthAttendance.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanelSelectMonthAttendance.add(jComboBoxSelectMonthAttendance, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 230, 40));

        jPanelAttendanceandTracker.add(jPanelSelectMonthAttendance, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 400, 280, 60));

        jButtonAttendanceView.setBackground(new java.awt.Color(204, 0, 51));
        jButtonAttendanceView.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jButtonAttendanceView.setForeground(new java.awt.Color(255, 255, 255));
        jButtonAttendanceView.setText("View");
        jPanelAttendanceandTracker.add(jButtonAttendanceView, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 470, 160, 30));

        jPanelTimeTracker.setBackground(new java.awt.Color(102, 0, 0));
        jPanelTimeTracker.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButtonBreak.setBackground(new java.awt.Color(204, 0, 51));
        jButtonBreak.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        jButtonBreak.setForeground(new java.awt.Color(255, 255, 255));
        jButtonBreak.setText("BREAK");
        jPanelTimeTracker.add(jButtonBreak, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 90, 200, 50));

        jButtonTimeIn.setBackground(new java.awt.Color(204, 0, 51));
        jButtonTimeIn.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        jButtonTimeIn.setForeground(new java.awt.Color(255, 255, 255));
        jButtonTimeIn.setText("TIME IN");
        jPanelTimeTracker.add(jButtonTimeIn, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 20, 200, 50));

        jButtonTimeOut.setBackground(new java.awt.Color(204, 0, 51));
        jButtonTimeOut.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        jButtonTimeOut.setForeground(new java.awt.Color(255, 255, 255));
        jButtonTimeOut.setText("TIME OUT");
        jPanelTimeTracker.add(jButtonTimeOut, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 160, 200, 50));

        jPanelAttendanceandTracker.add(jPanelTimeTracker, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 140, 430, 230));

        jLabelAttendanceLog.setBackground(new java.awt.Color(255, 255, 255));
        jLabelAttendanceLog.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        jLabelAttendanceLog.setForeground(new java.awt.Color(255, 255, 255));
        jLabelAttendanceLog.setText("Attendance Logs");
        jPanelAttendanceandTracker.add(jLabelAttendanceLog, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, -1, 80));

        jTabbedPaneEmployee.addTab("Attendance & Tracker", jPanelAttendanceandTracker);

        jPanelEmployeeMain.add(jTabbedPaneEmployee, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, -40, 1020, 550));

        jPanelEmployeeDashboard.add(jPanelEmployeeMain, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 110, 1020, 510));

        jPanelHeaders.setBackground(new java.awt.Color(0, 0, 0));

        javax.swing.GroupLayout jPanelHeadersLayout = new javax.swing.GroupLayout(jPanelHeaders);
        jPanelHeaders.setLayout(jPanelHeadersLayout);
        jPanelHeadersLayout.setHorizontalGroup(
            jPanelHeadersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1060, Short.MAX_VALUE)
        );
        jPanelHeadersLayout.setVerticalGroup(
            jPanelHeadersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 110, Short.MAX_VALUE)
        );

        jPanelEmployeeDashboard.add(jPanelHeaders, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1060, 110));

        getContentPane().add(jPanelEmployeeDashboard);
        jPanelEmployeeDashboard.setBounds(0, 0, 1090, 640);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonLogoutActionPerformed
        Login newClassInstance = new Login();
        newClassInstance.setVisible(true);

        dispose();
    }//GEN-LAST:event_jButtonLogoutActionPerformed

    private void jButtonEmployeeDetailsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEmployeeDetailsActionPerformed
        jTabbedPaneEmployee.setSelectedIndex(0);
        jButtonEmployeeDetails.setBackground(Color.RED);
        jButtonSalaryInfomation.setBackground(new java.awt.Color(0,0,0));
        jButtonRequests.setBackground(new java.awt.Color(0,0,0));
        jButtonAttendance.setBackground(new java.awt.Color(0,0,0));
                
    }//GEN-LAST:event_jButtonEmployeeDetailsActionPerformed

    private void jButtonSalaryInfomationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSalaryInfomationActionPerformed
        System.out.println("Salary Information Button Clicked!"); // Debugging step
        jTabbedPaneEmployee.setSelectedIndex(1);
        jButtonEmployeeDetails.setBackground(new java.awt.Color(0,0,0));
        jButtonSalaryInfomation.setBackground(Color.RED);
        jButtonRequests.setBackground(new java.awt.Color(0,0,0));
        jButtonAttendance.setBackground(new java.awt.Color(0,0,0));
        loadSalaryInformation(); // Calls the method to fetch and display salary info
    }//GEN-LAST:event_jButtonSalaryInfomationActionPerformed

    private void jButtonRequestsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRequestsActionPerformed
        jTabbedPaneEmployee.setSelectedIndex(2);
        jButtonEmployeeDetails.setBackground(new java.awt.Color(0,0,0));
        jButtonSalaryInfomation.setBackground(new java.awt.Color(0,0,0));
        jButtonRequests.setBackground(Color.RED);
        jButtonAttendance.setBackground(new java.awt.Color(0,0,0));
        loadRequests();
    }//GEN-LAST:event_jButtonRequestsActionPerformed

    private void jButtonAttendanceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAttendanceActionPerformed
        jTabbedPaneEmployee.setSelectedIndex(3);
        jButtonEmployeeDetails.setBackground(new java.awt.Color(0,0,0));
        jButtonSalaryInfomation.setBackground(new java.awt.Color(0,0,0));
        jButtonRequests.setBackground(new java.awt.Color(0,0,0));
        jButtonAttendance.setBackground(Color.RED);
    }//GEN-LAST:event_jButtonAttendanceActionPerformed

    private void jButtonSubmitRequestActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSubmitRequestActionPerformed
        String selectedLeave = jComboBoxLeaveType.getSelectedItem().toString();
        String leaveType = selectedLeave.split(" ")[0]; // Extract actual leave type (e.g., "Sick Leave")
        
        Date startDate = jDateChooserStart.getDate();
        Date endDate = jDateChooserEnd.getDate();
        String reason = jTextFieldReason.getText();
        
        if (startDate == null || endDate == null || reason.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill out all fields correctly.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (startDate.after(endDate)) {
            JOptionPane.showMessageDialog(this, "Start date must be before end date.", "Date Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        int leaveDuration = (int) ((endDate.getTime() - startDate.getTime()) / (1000 * 60 * 60 * 24)) + 1;
        
        if (!leaveTracker.hasSufficientLeave(leaveType, leaveDuration)) {
            JOptionPane.showMessageDialog(this, "Insufficient leave balance.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        List<LeaveRequest> existingRequests = new LeaveRequestReader().getAllLeaveRequests();
        for (LeaveRequest request : existingRequests) {
            if (request.getEmployeeID().equals(loggedInUser.getEmployeeId()) &&
                request.getLeaveType().equals(leaveType) &&
                request.getStartDate().equals(startDate.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate()) &&
                request.getEndDate().equals(endDate.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate())) {
                JOptionPane.showMessageDialog(this, "You have already submitted a request for this leave period.", "Duplicate Request", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
        
        
        LeaveRequest leaveRequest = new LeaveRequest(
            UUID.randomUUID().toString(),  // Generate a unique leave ID
            loggedInUser.getEmployeeId(),
            leaveType,
            LocalDate.now(),
            startDate.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate(),
            endDate.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate(),
            reason,
            "Pending",
            "",
            null
        );
        
        try {
            LeaveRequestReader.addLeaveRequest(leaveRequest);
            leaveTracker.deductLeave(leaveType, leaveDuration);
            JOptionPane.showMessageDialog(this, "Request submitted successfully!\nUpdated Balances:\nSick Leave: " + leaveTracker.getSickLeaveBalance() + "\nVacation Leave: " + leaveTracker.getVacationLeaveBalance() + "\nBirthday Leave: " + leaveTracker.getBirthdayLeaveBalance(), "Success", JOptionPane.INFORMATION_MESSAGE);
            loadRequests();
            initializeLeaveTypeComboBox();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error saving request.", "File Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jButtonSubmitRequestActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(EmployeeDashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(EmployeeDashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(EmployeeDashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(EmployeeDashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new EmployeeDashboard().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Logo;
    private javax.swing.JButton jButtonAttendance;
    private javax.swing.JButton jButtonAttendanceView;
    private javax.swing.JButton jButtonBreak;
    private javax.swing.JButton jButtonEmployeeDetails;
    private javax.swing.JButton jButtonLogout;
    private javax.swing.JButton jButtonRequests;
    private javax.swing.JButton jButtonSalaryInfomation;
    private javax.swing.JButton jButtonSubmitRequest;
    private javax.swing.JButton jButtonTimeIn;
    private javax.swing.JButton jButtonTimeOut;
    private javax.swing.JButton jButtonViewRequests;
    private javax.swing.JButton jButtonViewSalary;
    private javax.swing.JComboBox<String> jComboBoxLeaveType;
    private javax.swing.JComboBox<String> jComboBoxSelectMonth;
    private javax.swing.JComboBox<String> jComboBoxSelectMonthAttendance;
    private javax.swing.JComboBox<String> jComboBoxSelectMonthSalary;
    private javax.swing.JComboBox<String> jComboBoxSelectYear;
    private javax.swing.JComboBox<String> jComboBoxSelectYearAttendance;
    private javax.swing.JComboBox<String> jComboBoxSelectYearSalary;
    private com.toedter.calendar.JDateChooser jDateChooserEnd;
    private com.toedter.calendar.JDateChooser jDateChooserStart;
    private javax.swing.JLabel jLabePH;
    private javax.swing.JLabel jLabel2x2Picture;
    private javax.swing.JLabel jLabelAddress;
    private javax.swing.JLabel jLabelAllowances;
    private javax.swing.JLabel jLabelAttendanceLog;
    private javax.swing.JLabel jLabelAttendanceLogs;
    private javax.swing.JLabel jLabelAttendanceandTracker;
    private javax.swing.JLabel jLabelBirthday;
    private javax.swing.JLabel jLabelClothing;
    private javax.swing.JLabel jLabelCreateRequest;
    private javax.swing.JLabel jLabelDeductions;
    private javax.swing.JLabel jLabelEmployeeDetails;
    private javax.swing.JLabel jLabelEmployeeID;
    private javax.swing.JLabel jLabelEndDate;
    private javax.swing.JLabel jLabelFirstName;
    private javax.swing.JLabel jLabelGMT;
    private javax.swing.JLabel jLabelGreet;
    private javax.swing.JLabel jLabelGrossSalary;
    private javax.swing.JLabel jLabelHourlyRate;
    private javax.swing.JLabel jLabelLeaveType;
    private javax.swing.JLabel jLabelMotor;
    private javax.swing.JLabel jLabelName;
    private javax.swing.JLabel jLabelNetSalary;
    private javax.swing.JLabel jLabelPagibig;
    private javax.swing.JLabel jLabelPagibigDeductions;
    private javax.swing.JLabel jLabelPhilHealth;
    private javax.swing.JLabel jLabelPhilHealthDeductions;
    private javax.swing.JLabel jLabelPhone;
    private javax.swing.JLabel jLabelPhoneNumber;
    private javax.swing.JLabel jLabelPosition;
    private javax.swing.JLabel jLabelReason;
    private javax.swing.JLabel jLabelRequests;
    private javax.swing.JLabel jLabelRiceSubsidy;
    private javax.swing.JLabel jLabelSSS;
    private javax.swing.JLabel jLabelSSSDeductions;
    private javax.swing.JLabel jLabelSalaryCalculations;
    private javax.swing.JLabel jLabelSalaryInformation;
    private javax.swing.JLabel jLabelSalaryLogs;
    private javax.swing.JLabel jLabelSelectMonth;
    private javax.swing.JLabel jLabelSelectMonthAttendance;
    private javax.swing.JLabel jLabelSelectMonthSalary;
    private javax.swing.JLabel jLabelSelectYear;
    private javax.swing.JLabel jLabelSelectYearAttendance;
    private javax.swing.JLabel jLabelSelectYearSalary;
    private javax.swing.JLabel jLabelStartDate;
    private javax.swing.JLabel jLabelStatus;
    private javax.swing.JLabel jLabelSupervisor;
    private javax.swing.JLabel jLabelTIN;
    private javax.swing.JLabel jLabelTime;
    private javax.swing.JLabel jLabelTimeTracker;
    private javax.swing.JLabel jLabelTotalAllowances;
    private javax.swing.JLabel jLabelTotalDeductions;
    private javax.swing.JLabel jLabelUsername;
    private javax.swing.JLabel jLabelWithholdingTax;
    private javax.swing.JPanel jPanelAllowances;
    private javax.swing.JPanel jPanelAttendanceAndTracker;
    private javax.swing.JPanel jPanelAttendanceLogs;
    private javax.swing.JPanel jPanelAttendanceandTracker;
    private javax.swing.JPanel jPanelCreateRquest;
    private javax.swing.JPanel jPanelDeductions;
    private javax.swing.JPanel jPanelDetails;
    private javax.swing.JPanel jPanelEmployeeDashboard;
    private javax.swing.JPanel jPanelEmployeeDetails;
    private javax.swing.JPanel jPanelEmployeeMain;
    private javax.swing.JPanel jPanelHeaders;
    private javax.swing.JPanel jPanelMenuBar;
    private javax.swing.JPanel jPanelPicture;
    private javax.swing.JPanel jPanelRequests;
    private javax.swing.JPanel jPanelSalaryCalculations;
    private javax.swing.JPanel jPanelSalaryInformation;
    private javax.swing.JPanel jPanelSalaryLogs;
    private javax.swing.JPanel jPanelSelectMonth;
    private javax.swing.JPanel jPanelSelectMonthAttendance;
    private javax.swing.JPanel jPanelSelectMonthSalary;
    private javax.swing.JPanel jPanelSelectYear;
    private javax.swing.JPanel jPanelSelectYearAttendance;
    private javax.swing.JPanel jPanelSelectYearSalary;
    private javax.swing.JPanel jPanelTimeTracker;
    private javax.swing.JPanel jPanelTotalAllwances;
    private javax.swing.JPanel jPanelTotalDeductions;
    private javax.swing.JScrollPane jScrollPaneAttendanceLogs;
    private javax.swing.JScrollPane jScrollPaneAttenedanceandTracker;
    private javax.swing.JScrollPane jScrollPaneSalaryLogs;
    private javax.swing.JTabbedPane jTabbedPaneEmployee;
    private javax.swing.JTable jTableAttendanceLogs;
    private javax.swing.JTable jTableRequestLogs;
    private javax.swing.JTable jTableSalaryLogs;
    private javax.swing.JTextField jTextFieldAddress;
    private javax.swing.JTextField jTextFieldBirthday;
    private javax.swing.JTextField jTextFieldClothing;
    private javax.swing.JTextField jTextFieldEmployeeID;
    private javax.swing.JTextField jTextFieldGrossSalary;
    private javax.swing.JTextField jTextFieldHourlyRate;
    private javax.swing.JTextField jTextFieldName;
    private javax.swing.JTextField jTextFieldNetSalary;
    private javax.swing.JTextField jTextFieldPagibig;
    private javax.swing.JTextField jTextFieldPagibigDeductions;
    private javax.swing.JTextField jTextFieldPhilHealth;
    private javax.swing.JTextField jTextFieldPhilhealth;
    private javax.swing.JTextField jTextFieldPhone;
    private javax.swing.JTextField jTextFieldPhoneNumber;
    private javax.swing.JTextField jTextFieldPosition;
    private javax.swing.JTextField jTextFieldReason;
    private javax.swing.JTextField jTextFieldRiceSubsidy;
    private javax.swing.JTextField jTextFieldSSS;
    private javax.swing.JTextField jTextFieldSSSDeductions;
    private javax.swing.JTextField jTextFieldStatus;
    private javax.swing.JTextField jTextFieldSupervisor;
    private javax.swing.JTextField jTextFieldTIN;
    private javax.swing.JTextField jTextFieldTotalAllowances;
    private javax.swing.JTextField jTextFieldTotalDeductions;
    private javax.swing.JTextField jTextFieldUsername;
    private javax.swing.JTextField jTextFieldWithholdingTax;
    // End of variables declaration//GEN-END:variables
}
