/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package motorph_GUI;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;
//import java.lang.System.Logger;
//import java.lang.System.Logger.Level;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;
import motorph9.ITUser;
import password_reset9.PasswordCsvDataAccess;
import password_reset9.ResetPasswordProcessor;
import password_reset9.PasswordDataAccess;
import password_reset9.PasswordResetService;

/**
 *
 * @author Four Lugtu
 */
public class ITDashboard extends javax.swing.JFrame {
    private Timer timer;
    private ITUser itUser;
    private static final String FILE_PATH = "src/data9/Password_Reset_Requests.csv"; // CSV file path
    private ResetPasswordProcessor resetPasswordProcessor;

    /**
      * Creates new form ITDashboar
      * @param itUser*/
  public ITDashboard(ITUser itUser) {  // Pass ITUser object directly
      this.itUser = itUser; // Assign it correctly
      initComponents(); // Call initComponents() to initialize UI
      setLocationRelativeTo(null); // Center the window
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Define close behavior
      checkFileExists(); // Ensure the file exists
      startClock();
      setupTable(); // Set correct table headers
      loadPasswordResetRequests(); // Load data into JTable
      setITUserDetails(); // Show IT User details
      setupTableSelectionListener();
      initializeDependencies();

        // Debugging: Print ITUser details
        System.out.println("ITUser Data: ");
        System.out.println("Username: " + itUser.getUsername());
        System.out.println("First Name: " + itUser.getFirstName());
        System.out.println("Last Name: " + itUser.getLastName());

        jLabelGreet.setText("Welcome, " + itUser.getFirstName() + "!");
    }

    private ITDashboard() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
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
    
    /**
     * Restricts jDateChooser to **only allow today's date** (prevents past & future selections).
     */
    
    public void checkFileExists() {
        File file = new File(FILE_PATH);
        if (file.exists()) {
            System.out.println("✅ CSV file found: " + FILE_PATH);
        } else {
            System.err.println("❌ CSV file not found: " + FILE_PATH);
        }
    }
    
    private void setupTable() {
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{
            "Employee Number", "Employee Name", "Date of Request", 
            "Status", "Admin Name", "Admin Employee No.", "Date of Reset"
        });
        jTablePasswordResetTickets.setModel(model); // Apply headers to table
    }
    
    public void loadPasswordResetRequests() {
        DefaultTableModel model = (DefaultTableModel) jTablePasswordResetTickets.getModel();
        model.setRowCount(0); // Clear table before loading new data
        jButtonResetPassword.setEnabled(false);

        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            boolean firstLine = true;
            int rowCount = 0;

            while ((line = br.readLine()) != null) {
                System.out.println("🔍 Reading line: " + line);

                if (firstLine) {
                    firstLine = false;
                    continue;
                }

                String[] data = line.split(",", -1);

                String empNum = data.length > 0 ? data[0] : "";
                String empName = data.length > 1 ? data[1] : "";
                String dateRequest = data.length > 2 ? data[2] : "";
                String status = data.length > 3 ? data[3] : "Pending";
                String adminName = data.length > 4 ? data[4] : "";
                String adminEmpNum = data.length > 5 ? data[5] : "";
                String dateReset = data.length > 6 ? data[6] : "";

                model.addRow(new Object[]{empNum, empName, dateRequest, status, adminName, adminEmpNum, dateReset});
                rowCount++;
            }

            model.fireTableDataChanged(); // Explicitly refresh the table
            System.out.println("✅ Loaded " + rowCount + " requests into JTable.");

        } catch (FileNotFoundException e) {
            System.err.println("❌ Password Reset Requests file not found: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("❌ Error loading password reset requests: " + e.getMessage());
        }
    }

    private void setITUserDetails() {
        jTextFieldEmployeeNumber.setText(itUser.getEmployeeId()); // IT Employee ID
        jTextFieldName.setText(itUser.getFirstName() + " " + itUser.getLastName()); // IT Full Name

        jTextFieldEmployeeNumber.setEditable(false); // IT cannot change Employee Number
        jTextFieldName.setEditable(false); //  IT cannot change their Name
    }
    
    private void setupTableSelectionListener() {
        jTablePasswordResetTickets.getSelectionModel().addListSelectionListener(event -> {
            int selectedRow = jTablePasswordResetTickets.getSelectedRow();
            if (selectedRow == -1) {
                jButtonResetPassword.setEnabled(false);
                return;
            }

            DefaultTableModel model = (DefaultTableModel) jTablePasswordResetTickets.getModel();
            String status = model.getValueAt(selectedRow, 3).toString(); // Get Status Column

            // ✅ Enable Reset Button Only for Pending Requests
            jButtonResetPassword.setEnabled(status.equalsIgnoreCase("Pending"));
        });
    }
    
    private void initializeDependencies() {
        try {
            PasswordDataAccess passwordDataAccess = new PasswordCsvDataAccess();
            PasswordResetService passwordResetService = new PasswordResetService(passwordDataAccess);
            resetPasswordProcessor = new ResetPasswordProcessor(passwordResetService);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error initializing dependencies: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
    private String generateComplexDefaultPassword(String employeeNumber) {
        String basePassword = "Default" + employeeNumber;
        String specialChars = "!@#$%^&*";
        Random random = new Random();
        int randomIndex = random.nextInt(specialChars.length());
        char randomChar = specialChars.charAt(randomIndex);

        return basePassword + randomChar + random.nextInt(100);
    }

    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel3 = new javax.swing.JPanel();
        jPanelITMain = new javax.swing.JPanel();
        jLabelPH = new javax.swing.JLabel();
        jLabelMotor = new javax.swing.JLabel();
        Logo = new javax.swing.JLabel();
        jLabelGMT = new javax.swing.JLabel();
        jLabelTime = new javax.swing.JLabel();
        jPanelHeader = new javax.swing.JPanel();
        jLabelGreet = new javax.swing.JLabel();
        jLabelDate = new javax.swing.JLabel();
        jTabbedPaneIT = new javax.swing.JTabbedPane();
        jPanelPasswordReset = new javax.swing.JPanel();
        jScrollPanePasswordResetTickets = new javax.swing.JScrollPane();
        jTablePasswordResetTickets = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabelAdminInformation = new javax.swing.JLabel();
        jTextFieldName = new javax.swing.JTextField();
        jLabelName = new javax.swing.JLabel();
        jTextFieldEmployeeNumber = new javax.swing.JTextField();
        jLabelEmployeeNumber = new javax.swing.JLabel();
        jButtonResetPassword = new javax.swing.JButton();
        jLabelPasswordResetTickets = new javax.swing.JLabel();
        jPanelCreateAccount = new javax.swing.JPanel();
        jScrollPaneTableEmployeeRecords = new javax.swing.JScrollPane();
        jTableEmployeeRecords = new javax.swing.JTable();
        jLabelCreateEmployeesAccoun = new javax.swing.JLabel();
        jButtonDeleteAccount = new javax.swing.JButton();
        jButtonCreateAccount = new javax.swing.JButton();
        jButtonLogout1 = new javax.swing.JButton();
        jButtonCreateEmployeeAccount = new javax.swing.JButton();
        jButtonPasswordResetTickets = new javax.swing.JButton();

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanelITMain.setBackground(new java.awt.Color(0, 0, 0));
        jPanelITMain.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabelPH.setFont(new java.awt.Font("Century Gothic", 1, 36)); // NOI18N
        jLabelPH.setForeground(new java.awt.Color(204, 0, 51));
        jLabelPH.setText("PH");
        jPanelITMain.add(jLabelPH, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, -20, 70, 120));

        jLabelMotor.setFont(new java.awt.Font("Century Gothic", 1, 36)); // NOI18N
        jLabelMotor.setForeground(new java.awt.Color(255, 255, 255));
        jLabelMotor.setText("MOTOR");
        jPanelITMain.add(jLabelMotor, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, -10, -1, 100));

        Logo.setBackground(new java.awt.Color(0, 0, 102));
        Logo.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        Logo.setForeground(new java.awt.Color(255, 255, 255));
        Logo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/logos9/LogoMPH_(small1).png"))); // NOI18N
        Logo.setText("Username");
        jPanelITMain.add(Logo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, -10, 100, 110));

        jLabelGMT.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabelGMT.setForeground(new java.awt.Color(255, 255, 255));
        jLabelGMT.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelGMT.setText("GMT+8 PH Time");
        jPanelITMain.add(jLabelGMT, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 20, 170, -1));

        jLabelTime.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        jLabelTime.setForeground(new java.awt.Color(255, 255, 255));
        jLabelTime.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelTime.setText("12:12:12 AM");
        jPanelITMain.add(jLabelTime, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 40, 170, 32));

        jPanelHeader.setBackground(new java.awt.Color(0, 0, 0));
        jPanelHeader.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabelGreet.setFont(new java.awt.Font("Century Gothic", 1, 20)); // NOI18N
        jLabelGreet.setForeground(new java.awt.Color(255, 255, 255));
        jLabelGreet.setText("Welcome!");
        jPanelHeader.add(jLabelGreet, new org.netbeans.lib.awtextra.AbsoluteConstraints(798, 20, 250, -1));

        jLabelDate.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabelDate.setForeground(new java.awt.Color(255, 255, 255));
        jLabelDate.setText("Wednesday, December 25, 2012");
        jLabelDate.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jPanelHeader.add(jLabelDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 50, -1, -1));

        jPanelITMain.add(jPanelHeader, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1080, 90));

        jPanelPasswordReset.setBackground(new java.awt.Color(102, 0, 0));
        jPanelPasswordReset.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTablePasswordResetTickets.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Employee No.", "Employee Name", "Date of Request", "Status", "Admin Name", "Admin Employee No.", "Date & Time of Reset"
            }
        ));
        jScrollPanePasswordResetTickets.setViewportView(jTablePasswordResetTickets);

        jPanelPasswordReset.add(jScrollPanePasswordResetTickets, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 180, 800, 380));

        jPanel2.setBackground(new java.awt.Color(102, 0, 0));
        jPanel2.setForeground(new java.awt.Color(102, 0, 0));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel6.setBackground(new java.awt.Color(51, 0, 0));
        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabelAdminInformation.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        jLabelAdminInformation.setForeground(new java.awt.Color(255, 255, 255));
        jLabelAdminInformation.setText(" ADMIN INFORMATION");
        jPanel6.add(jLabelAdminInformation, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 200, 20));

        jTextFieldName.setEditable(false);
        jTextFieldName.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.add(jTextFieldName, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, 160, 30));

        jLabelName.setFont(new java.awt.Font("Century Gothic", 0, 15)); // NOI18N
        jLabelName.setForeground(new java.awt.Color(255, 255, 255));
        jLabelName.setText("Name:");
        jPanel6.add(jLabelName, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, -1, 50));

        jTextFieldEmployeeNumber.setEditable(false);
        jTextFieldEmployeeNumber.setBackground(new java.awt.Color(255, 255, 255));
        jTextFieldEmployeeNumber.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldEmployeeNumberActionPerformed(evt);
            }
        });
        jPanel6.add(jTextFieldEmployeeNumber, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 70, 180, 30));

        jLabelEmployeeNumber.setFont(new java.awt.Font("Century Gothic", 0, 15)); // NOI18N
        jLabelEmployeeNumber.setForeground(new java.awt.Color(255, 255, 255));
        jLabelEmployeeNumber.setText(" Employee Number:");
        jPanel6.add(jLabelEmployeeNumber, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 40, -1, 30));

        jButtonResetPassword.setBackground(new java.awt.Color(204, 0, 51));
        jButtonResetPassword.setFont(new java.awt.Font("Century Gothic", 1, 13)); // NOI18N
        jButtonResetPassword.setForeground(new java.awt.Color(255, 255, 255));
        jButtonResetPassword.setText("Reset Password");
        jButtonResetPassword.setMaximumSize(new java.awt.Dimension(132, 27));
        jButtonResetPassword.setMinimumSize(new java.awt.Dimension(132, 27));
        jButtonResetPassword.setPreferredSize(new java.awt.Dimension(90, 23));
        jButtonResetPassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonResetPasswordActionPerformed(evt);
            }
        });
        jPanel6.add(jButtonResetPassword, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 70, 130, 30));

        jPanel2.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 820, 120));

        jPanelPasswordReset.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 840, 150));

        jLabelPasswordResetTickets.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        jLabelPasswordResetTickets.setForeground(new java.awt.Color(255, 255, 255));
        jLabelPasswordResetTickets.setText("PASSWORD RESET TICKETS");
        jPanelPasswordReset.add(jLabelPasswordResetTickets, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 150, -1, -1));

        jTabbedPaneIT.addTab("PasswordReset", jPanelPasswordReset);

        jPanelCreateAccount.setBackground(new java.awt.Color(102, 0, 0));
        jPanelCreateAccount.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTableEmployeeRecords.setAutoCreateRowSorter(true);
        jTableEmployeeRecords.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
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

        jPanelCreateAccount.add(jScrollPaneTableEmployeeRecords, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, 820, 480));

        jLabelCreateEmployeesAccoun.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        jLabelCreateEmployeesAccoun.setForeground(new java.awt.Color(255, 255, 255));
        jLabelCreateEmployeesAccoun.setText("CREATE EMPLOYEES ACCOUNT");
        jPanelCreateAccount.add(jLabelCreateEmployeesAccoun, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, -1, -1));

        jButtonDeleteAccount.setBackground(new java.awt.Color(251, 0, 54));
        jButtonDeleteAccount.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        jButtonDeleteAccount.setForeground(new java.awt.Color(255, 255, 255));
        jButtonDeleteAccount.setText("Delete Account");
        jPanelCreateAccount.add(jButtonDeleteAccount, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 40, 130, 30));

        jButtonCreateAccount.setBackground(new java.awt.Color(254, 0, 51));
        jButtonCreateAccount.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        jButtonCreateAccount.setForeground(new java.awt.Color(255, 255, 255));
        jButtonCreateAccount.setText("Create Account");
        jPanelCreateAccount.add(jButtonCreateAccount, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 40, 140, 30));

        jTabbedPaneIT.addTab("CreateAccount", jPanelCreateAccount);

        jPanelITMain.add(jTabbedPaneIT, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 50, 840, 730));

        jButtonLogout1.setFont(new java.awt.Font("Century Gothic", 1, 13)); // NOI18N
        jButtonLogout1.setText("Logout");
        jButtonLogout1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonLogout1ActionPerformed(evt);
            }
        });
        jPanelITMain.add(jButtonLogout1, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 580, 200, 40));

        jButtonCreateEmployeeAccount.setBackground(new java.awt.Color(153, 0, 0));
        jButtonCreateEmployeeAccount.setFont(new java.awt.Font("Century Gothic", 1, 13)); // NOI18N
        jButtonCreateEmployeeAccount.setForeground(new java.awt.Color(255, 255, 255));
        jButtonCreateEmployeeAccount.setText("Create Employee Account");
        jButtonCreateEmployeeAccount.setMaximumSize(new java.awt.Dimension(132, 27));
        jButtonCreateEmployeeAccount.setMinimumSize(new java.awt.Dimension(132, 27));
        jButtonCreateEmployeeAccount.setPreferredSize(new java.awt.Dimension(90, 23));
        jButtonCreateEmployeeAccount.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCreateEmployeeAccountActionPerformed(evt);
            }
        });
        jPanelITMain.add(jButtonCreateEmployeeAccount, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 100, 200, 40));

        jButtonPasswordResetTickets.setBackground(new java.awt.Color(153, 0, 0));
        jButtonPasswordResetTickets.setFont(new java.awt.Font("Century Gothic", 1, 13)); // NOI18N
        jButtonPasswordResetTickets.setForeground(new java.awt.Color(255, 255, 255));
        jButtonPasswordResetTickets.setText("Password Reset Tickets");
        jButtonPasswordResetTickets.setMaximumSize(new java.awt.Dimension(132, 27));
        jButtonPasswordResetTickets.setMinimumSize(new java.awt.Dimension(132, 27));
        jButtonPasswordResetTickets.setPreferredSize(new java.awt.Dimension(90, 23));
        jButtonPasswordResetTickets.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonPasswordResetTicketsActionPerformed(evt);
            }
        });
        jPanelITMain.add(jButtonPasswordResetTickets, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 150, 200, 40));

        getContentPane().add(jPanelITMain, new org.netbeans.lib.awtextra.AbsoluteConstraints(7, 0, 1080, 660));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonCreateEmployeeAccountActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCreateEmployeeAccountActionPerformed
       jTabbedPaneIT.setSelectedIndex(1);
       jButtonPasswordResetTickets.setBackground(new java.awt.Color(0,0,0));
       jButtonCreateEmployeeAccount.setBackground(Color.RED);
    }//GEN-LAST:event_jButtonCreateEmployeeAccountActionPerformed

    private void jButtonLogout1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonLogout1ActionPerformed
        Login newClassInstance = new Login();
        newClassInstance.setVisible(true);

        dispose();
    }//GEN-LAST:event_jButtonLogout1ActionPerformed

    private void jButtonResetPasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonResetPasswordActionPerformed
        int selectedRow = jTablePasswordResetTickets.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a request to process.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String employeeNumber = (String) jTablePasswordResetTickets.getValueAt(selectedRow, 0);
        String adminName = jTextFieldName.getText();
        String adminEmpNum = jTextFieldEmployeeNumber.getText();

        resetPasswordProcessor.resetPassword(employeeNumber, adminName, adminEmpNum, this); // Process reset first

        String tempPassword = generateComplexDefaultPassword(employeeNumber); // Generate password after reset
        JOptionPane.showMessageDialog(this, "Temporary Password: " + tempPassword + "\n\n(This is a simulation. In a real system, the password would be delivered securely.)", "Password Reset", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_jButtonResetPasswordActionPerformed

    private void jButtonPasswordResetTicketsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonPasswordResetTicketsActionPerformed
        jTabbedPaneIT.setSelectedIndex(0);
        jButtonPasswordResetTickets.setBackground(Color.RED);
        jButtonCreateEmployeeAccount.setBackground(new java.awt.Color(0,0,0));
        jButtonPasswordResetTickets.setBackground(new java.awt.Color(0,0,0));
        jButtonCreateEmployeeAccount.setBackground(new java.awt.Color(0,0,0));
    }//GEN-LAST:event_jButtonPasswordResetTicketsActionPerformed

    private void jTextFieldEmployeeNumberActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldEmployeeNumberActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldEmployeeNumberActionPerformed

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
            java.util.logging.Logger.getLogger(ITDashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ITDashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ITDashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ITDashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new ITDashboard().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Logo;
    private javax.swing.JButton jButtonCreateAccount;
    private javax.swing.JButton jButtonCreateEmployeeAccount;
    private javax.swing.JButton jButtonDeleteAccount;
    private javax.swing.JButton jButtonLogout1;
    private javax.swing.JButton jButtonPasswordResetTickets;
    private javax.swing.JButton jButtonResetPassword;
    private javax.swing.JLabel jLabelAdminInformation;
    private javax.swing.JLabel jLabelCreateEmployeesAccoun;
    private javax.swing.JLabel jLabelDate;
    private javax.swing.JLabel jLabelEmployeeNumber;
    private javax.swing.JLabel jLabelGMT;
    private javax.swing.JLabel jLabelGreet;
    private javax.swing.JLabel jLabelMotor;
    private javax.swing.JLabel jLabelName;
    private javax.swing.JLabel jLabelPH;
    private javax.swing.JLabel jLabelPasswordResetTickets;
    private javax.swing.JLabel jLabelTime;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanelCreateAccount;
    private javax.swing.JPanel jPanelHeader;
    private javax.swing.JPanel jPanelITMain;
    private javax.swing.JPanel jPanelPasswordReset;
    private javax.swing.JScrollPane jScrollPanePasswordResetTickets;
    private javax.swing.JScrollPane jScrollPaneTableEmployeeRecords;
    private javax.swing.JTabbedPane jTabbedPaneIT;
    private javax.swing.JTable jTableEmployeeRecords;
    private javax.swing.JTable jTablePasswordResetTickets;
    private javax.swing.JTextField jTextFieldEmployeeNumber;
    private javax.swing.JTextField jTextFieldName;
    // End of variables declaration//GEN-END:variables
}
