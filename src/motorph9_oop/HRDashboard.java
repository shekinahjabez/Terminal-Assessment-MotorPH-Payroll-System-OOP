/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package motorph9_oop;


import java.awt.Color;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.Timer;
import javax.swing.JOptionPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.FlowLayout;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.*;
import java.awt.*;
/**
 *
 * @author apcev
 */
public class HRDashboard extends javax.swing.JFrame { 
    /**
     * Creates new form dashboard
     */
    
    public HRDashboard(String firstName, String employeeId) {
       
        initComponents();
        setLocationRelativeTo(null);
        setButtonsEnabled(false);
        jLabelGreet.setText("Welcome, " + firstName + "!");
        //showEmployeeDetails(employeeId);
        
        jTextFieldSearch.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                checkInput();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                checkInput();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                checkInput();
            }

            private void checkInput() {
                String searchInput = jTextFieldSearch.getText().trim();
                boolean isEmployeeNumberEntered = !searchInput.isEmpty();
                setButtonsEnabled(isEmployeeNumberEntered);
            }
        });
        
        Timer timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateTimeAndDate();
            }
        });
        timer.start();

    }
    
     
    public HRDashboard() {
        initComponents();
        setLocationRelativeTo(null);
        setButtonsEnabled(false);
        
        jTextFieldSearch.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                checkInput();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                checkInput();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                checkInput();
            }

            private void checkInput() {
                String searchInput = jTextFieldSearch.getText().trim();
                boolean isEmployeeNumberEntered = !searchInput.isEmpty();
                setButtonsEnabled(isEmployeeNumberEntered);
            }
        });
        
        Timer timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateTimeAndDate();
            }
        });
        timer.start();
    }
    
    private void setButtonsEnabled(boolean enabled) {
        jButtonDashboard.setEnabled(enabled);
        jButtonProfile.setEnabled(enabled);
        jButtonWallet.setEnabled(enabled);
        jButtonSearch.setEnabled(enabled);
    }

    private void updateTimeAndDate() {
        
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm:ss a");
        String time = timeFormat.format(new Date());

        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, MMMM d, yyyy");
        String date = dateFormat.format(new Date());

        jLabelTime.setText(time);
        jLabelDate.setText(date);
    
    }

    /*private void showEmployeeDetails(String employeeId){
        
         try {
            
            int employeeID = Integer.parseInt(employeeId);
            CSVReader reader = new CSVReader("src\\motorph9\\EmployeeDetails.csv");
            String[] userInfo = reader.getUserInfo(String.valueOf(employeeID));
            
            if (userInfo != null) {

                                    
             /*   jLabelDashname.setText(userInfo[3] + ", " + userInfo[4]);
                jLabelDashempnum.setText("Employee Number: " + userInfo[0]);
                jLabelDashbirthday.setText("Birthday: " + userInfo[5]); /*

                
            /*    jLabelFullname.setText(userInfo[3] + ", " + userInfo[4]);
                jLabelEmpnum.setText("Employee Number: " + userInfo[0]);
                jLabelBirthday.setText("Birthday: " + userInfo[5]);
                jLabelAddress.setText("<html>" + "Address: " + userInfo[6] + "</html>");
                jLabelContact.setText("Contact No.: " + userInfo[7]);
                jLabelPosition.setText("Position: " + userInfo[13]);
                jLabelSuper.setText("Supervisor: " + userInfo[14]); /*

                
            /*    jLabelHourlyrate.setText("Hourly Rate: " + userInfo[20]);
                jLabelBasicsalary.setText("Basic Salary: " + userInfo[15]);
                jLabelRice.setText("Rice Subsidy: " + userInfo[16]);
                jLabelPhoneallowance.setText ("Phone Allowance: " + userInfo[17]);
                jLabelClothingallowance.setText ("Clothing Allowance: " + userInfo[18]);
                jLabelSSS.setText("SSS Number: " + userInfo[8]);
                jLabelTin.setText("TIN Number: " + userInfo[10]);
                jLabelPagibig.setText("Pagibig Number: " + userInfo[11]);
                jLabelPhilhealth.setText("Philhealth Number: " + userInfo[9]);
                jLabelGrosssalary.setText("Gross Semi-monthly Salary: " + userInfo[19]);
                jLabelWitholding.setText("Witholding Tax: Value not Available");
                jLabelNetsalary.setText("Net Salary: Value Not Available"); /*

            } else {
                
                JOptionPane.showMessageDialog(null, "Employee Number not found", "Error", JOptionPane.ERROR_MESSAGE);
            }
            
     } catch (NumberFormatException ex) {
            
            JOptionPane.showMessageDialog(null, "Error: Please enter a valid Employee Number.", "Input Error", JOptionPane.ERROR_MESSAGE);
        }        
    }
 /*
    /** 
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanelHeader = new javax.swing.JPanel();
        jLabelSeparate = new javax.swing.JLabel();
        jLabelLogo = new javax.swing.JLabel();
        jTextFieldSearch = new javax.swing.JTextField();
        jButtonSearch = new javax.swing.JButton();
        jLabelGreet = new javax.swing.JLabel();
        jPanelSidebar = new javax.swing.JPanel();
        jButtonDashboard = new javax.swing.JButton();
        jButtonProfile = new javax.swing.JButton();
        jButtonWallet = new javax.swing.JButton();
        jButtonLogout = new javax.swing.JButton();
        jButtonSupport = new javax.swing.JButton();
        jTabbedMain = new javax.swing.JTabbedPane();
        jPanelDashboard = new javax.swing.JPanel();
        jLabelRedIcon = new javax.swing.JLabel();
        jLabelDashname = new javax.swing.JLabel();
        jLabelDashempnum = new javax.swing.JLabel();
        jLabelDashbirthday = new javax.swing.JLabel();
        jButtonLeavereq = new javax.swing.JButton();
        jButtonViewAllEmployees = new javax.swing.JButton();
        jLabelDate = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jPanelProfile = new javax.swing.JPanel();
        jLabelFullname = new javax.swing.JLabel();
        jLabelEmpnum = new javax.swing.JLabel();
        jLabelBirthday = new javax.swing.JLabel();
        jLabelAddress = new javax.swing.JLabel();
        jLabelContact = new javax.swing.JLabel();
        jLabelPosition = new javax.swing.JLabel();
        jLabelSuper = new javax.swing.JLabel();
        jLabelTitleProfile = new javax.swing.JLabel();
        jPanelWallet = new javax.swing.JPanel();
        jLabelTitleWallet = new javax.swing.JLabel();
        jLabelHourlyrate = new javax.swing.JLabel();
        jLabelBasicsalary = new javax.swing.JLabel();
        jLabelRice = new javax.swing.JLabel();
        jLabelPhoneallowance = new javax.swing.JLabel();
        jLabelClothingallowance = new javax.swing.JLabel();
        jLabelSSS = new javax.swing.JLabel();
        jLabelTin = new javax.swing.JLabel();
        jLabelPagibig = new javax.swing.JLabel();
        jLabelPhilhealth = new javax.swing.JLabel();
        jLabelGrosssalary = new javax.swing.JLabel();
        jLabelWitholding = new javax.swing.JLabel();
        jLabelNetsalary = new javax.swing.JLabel();
        jPanelSupport = new javax.swing.JPanel();
        jLabelTitleSupport = new javax.swing.JLabel();
        jLabelSupportHR = new javax.swing.JLabel();
        jLabelSupportAC = new javax.swing.JLabel();
        jLabelSupportIT = new javax.swing.JLabel();
        jLabelHRemail = new javax.swing.JLabel();
        jLabelITemail = new javax.swing.JLabel();
        jLabelACemail = new javax.swing.JLabel();
        jPanelBottom = new javax.swing.JPanel();
        jLabelTime = new javax.swing.JLabel();
        jLabelGMT = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Group 9 - Feature 1");

        jPanel1.setBackground(new java.awt.Color(0, 0, 0));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanelHeader.setBackground(new java.awt.Color(0, 0, 0));
        jPanelHeader.setForeground(new java.awt.Color(255, 102, 102));

        jLabelSeparate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/logos9/horizontal (2).png"))); // NOI18N
        jLabelSeparate.setText("jLabel8");

        jLabelLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/logos9/empdes3.png"))); // NOI18N

        jTextFieldSearch.setText("Look by Employee Number");
        jTextFieldSearch.setSelectionColor(new java.awt.Color(102, 0, 0));
        jTextFieldSearch.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextFieldSearchMouseClicked(evt);
            }
        });
        jTextFieldSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldSearchActionPerformed(evt);
            }
        });

        jButtonSearch.setBackground(new java.awt.Color(0, 0, 51));
        jButtonSearch.setForeground(new java.awt.Color(255, 255, 255));
        jButtonSearch.setText("Search");
        jButtonSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSearchActionPerformed(evt);
            }
        });

        jLabelGreet.setFont(new java.awt.Font("Segoe UI Semibold", 0, 20)); // NOI18N
        jLabelGreet.setForeground(new java.awt.Color(255, 255, 255));
        jLabelGreet.setText("Welcome!");

        javax.swing.GroupLayout jPanelHeaderLayout = new javax.swing.GroupLayout(jPanelHeader);
        jPanelHeader.setLayout(jPanelHeaderLayout);
        jPanelHeaderLayout.setHorizontalGroup(
            jPanelHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelHeaderLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelLogo)
                .addGroup(jPanelHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelHeaderLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabelSeparate, javax.swing.GroupLayout.PREFERRED_SIZE, 404, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanelHeaderLayout.createSequentialGroup()
                        .addGap(42, 42, 42)
                        .addComponent(jTextFieldSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButtonSearch)
                        .addGap(41, 41, 41)
                        .addComponent(jLabelGreet)
                        .addGap(0, 186, Short.MAX_VALUE))))
        );
        jPanelHeaderLayout.setVerticalGroup(
            jPanelHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelHeaderLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanelHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonSearch)
                    .addComponent(jLabelGreet))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabelSeparate, javax.swing.GroupLayout.PREFERRED_SIZE, 1, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(38, 38, 38))
            .addGroup(jPanelHeaderLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelLogo, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.add(jPanelHeader, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 830, 90));

        jPanelSidebar.setBackground(new java.awt.Color(0, 0, 0));

        jButtonDashboard.setBackground(new java.awt.Color(0, 0, 51));
        jButtonDashboard.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jButtonDashboard.setForeground(new java.awt.Color(255, 255, 255));
        jButtonDashboard.setText("Dashboard");
        jButtonDashboard.setPreferredSize(new java.awt.Dimension(90, 23));
        jButtonDashboard.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jButtonDashboardMousePressed(evt);
            }
        });
        jButtonDashboard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonDashboardActionPerformed(evt);
            }
        });

        jButtonProfile.setBackground(new java.awt.Color(0, 0, 51));
        jButtonProfile.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jButtonProfile.setForeground(new java.awt.Color(255, 255, 255));
        jButtonProfile.setText("Profile");
        jButtonProfile.setPreferredSize(new java.awt.Dimension(90, 23));
        jButtonProfile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonProfileActionPerformed(evt);
            }
        });

        jButtonWallet.setBackground(new java.awt.Color(0, 0, 51));
        jButtonWallet.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jButtonWallet.setForeground(new java.awt.Color(255, 255, 255));
        jButtonWallet.setText("Wallet");
        jButtonWallet.setMaximumSize(new java.awt.Dimension(90, 23));
        jButtonWallet.setPreferredSize(new java.awt.Dimension(90, 23));
        jButtonWallet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonWalletActionPerformed(evt);
            }
        });

        jButtonLogout.setBackground(new java.awt.Color(0, 0, 51));
        jButtonLogout.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jButtonLogout.setForeground(new java.awt.Color(255, 255, 255));
        jButtonLogout.setText("Logout");
        jButtonLogout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonLogoutActionPerformed(evt);
            }
        });

        jButtonSupport.setBackground(new java.awt.Color(0, 0, 51));
        jButtonSupport.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jButtonSupport.setForeground(new java.awt.Color(255, 255, 255));
        jButtonSupport.setText("Support");
        jButtonSupport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSupportActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelSidebarLayout = new javax.swing.GroupLayout(jPanelSidebar);
        jPanelSidebar.setLayout(jPanelSidebarLayout);
        jPanelSidebarLayout.setHorizontalGroup(
            jPanelSidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelSidebarLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(jPanelSidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButtonLogout, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonSupport, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonWallet, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonDashboard, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonProfile, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(14, Short.MAX_VALUE))
        );
        jPanelSidebarLayout.setVerticalGroup(
            jPanelSidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelSidebarLayout.createSequentialGroup()
                .addGap(51, 51, 51)
                .addComponent(jButtonDashboard, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonProfile, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButtonWallet, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButtonSupport, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButtonLogout, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel1.add(jPanelSidebar, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 140, 320));

        jPanelDashboard.setBackground(new java.awt.Color(0, 0, 51));

        jLabelRedIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/logos9/dashboard big.png"))); // NOI18N
        jLabelRedIcon.setText("jLabel2");

        jLabelDashname.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jLabelDashname.setForeground(new java.awt.Color(255, 255, 255));
        jLabelDashname.setText("Last Name, First Name");

        jLabelDashempnum.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabelDashempnum.setForeground(new java.awt.Color(255, 255, 255));
        jLabelDashempnum.setText("Employee Number");

        jLabelDashbirthday.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabelDashbirthday.setForeground(new java.awt.Color(255, 255, 255));
        jLabelDashbirthday.setText("Birthday");

        jButtonLeavereq.setBackground(new java.awt.Color(0, 0, 51));
        jButtonLeavereq.setForeground(new java.awt.Color(255, 255, 255));
        jButtonLeavereq.setText("Leave Request");
        jButtonLeavereq.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonLeavereqActionPerformed(evt);
            }
        });

        jButtonViewAllEmployees.setBackground(new java.awt.Color(0, 0, 51));
        jButtonViewAllEmployees.setForeground(new java.awt.Color(255, 255, 255));
        jButtonViewAllEmployees.setText("View All Employees");
        jButtonViewAllEmployees.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonViewAllEmployeesActionPerformed(evt);
            }
        });

        jLabelDate.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jLabelDate.setForeground(new java.awt.Color(255, 255, 255));
        jLabelDate.setText("Wednesday, December 25, 2012");
        jLabelDate.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        jLabel1.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Welcome to MotorPAY");

        javax.swing.GroupLayout jPanelDashboardLayout = new javax.swing.GroupLayout(jPanelDashboard);
        jPanelDashboard.setLayout(jPanelDashboardLayout);
        jPanelDashboardLayout.setHorizontalGroup(
            jPanelDashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelDashboardLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jButtonLeavereq)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 373, Short.MAX_VALUE)
                .addComponent(jButtonViewAllEmployees)
                .addGap(15, 15, 15))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelDashboardLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelRedIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanelDashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelDashboardLayout.createSequentialGroup()
                        .addComponent(jLabelDashname)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanelDashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addGroup(jPanelDashboardLayout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(jLabelDate)))
                        .addGap(35, 35, 35))
                    .addGroup(jPanelDashboardLayout.createSequentialGroup()
                        .addGroup(jPanelDashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelDashbirthday)
                            .addComponent(jLabelDashempnum))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanelDashboardLayout.setVerticalGroup(
            jPanelDashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelDashboardLayout.createSequentialGroup()
                .addGroup(jPanelDashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelDashboardLayout.createSequentialGroup()
                        .addGap(111, 111, 111)
                        .addGroup(jPanelDashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelDashboardLayout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanelDashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabelDate)
                                    .addComponent(jLabelDashname)))
                            .addComponent(jLabelRedIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelDashboardLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabelDashempnum)))
                .addGap(4, 4, 4)
                .addComponent(jLabelDashbirthday)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 200, Short.MAX_VALUE)
                .addGroup(jPanelDashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonViewAllEmployees, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonLeavereq, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32))
        );

        jTabbedMain.addTab("tab2", jPanelDashboard);

        jPanelProfile.setBackground(new java.awt.Color(0, 0, 51));
        jPanelProfile.setForeground(new java.awt.Color(255, 255, 255));

        jLabelFullname.setFont(new java.awt.Font("Segoe UI Semibold", 1, 20)); // NOI18N
        jLabelFullname.setForeground(new java.awt.Color(255, 255, 255));
        jLabelFullname.setText("Last Name, First Name");

        jLabelEmpnum.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabelEmpnum.setForeground(new java.awt.Color(255, 255, 255));
        jLabelEmpnum.setText("Employee Number");

        jLabelBirthday.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabelBirthday.setForeground(new java.awt.Color(255, 255, 255));
        jLabelBirthday.setText("Birthday");

        jLabelAddress.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabelAddress.setForeground(new java.awt.Color(255, 255, 255));
        jLabelAddress.setText("Address");

        jLabelContact.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabelContact.setForeground(new java.awt.Color(255, 255, 255));
        jLabelContact.setText("Contact No.");

        jLabelPosition.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabelPosition.setForeground(new java.awt.Color(255, 255, 255));
        jLabelPosition.setText("Position");

        jLabelSuper.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabelSuper.setForeground(new java.awt.Color(255, 255, 255));
        jLabelSuper.setText("Supervisor");

        jLabelTitleProfile.setFont(new java.awt.Font("Segoe UI Semibold", 1, 20)); // NOI18N
        jLabelTitleProfile.setForeground(new java.awt.Color(255, 255, 255));
        jLabelTitleProfile.setText("Employee Profile");

        javax.swing.GroupLayout jPanelProfileLayout = new javax.swing.GroupLayout(jPanelProfile);
        jPanelProfile.setLayout(jPanelProfileLayout);
        jPanelProfileLayout.setHorizontalGroup(
            jPanelProfileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelProfileLayout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addGroup(jPanelProfileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelProfileLayout.createSequentialGroup()
                        .addGroup(jPanelProfileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabelAddress, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanelProfileLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 421, Short.MAX_VALUE)
                                .addComponent(jLabelTitleProfile)))
                        .addGap(30, 30, 30))
                    .addGroup(jPanelProfileLayout.createSequentialGroup()
                        .addGroup(jPanelProfileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelContact)
                            .addComponent(jLabelFullname, javax.swing.GroupLayout.PREFERRED_SIZE, 378, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabelPosition)
                            .addComponent(jLabelSuper)
                            .addGroup(jPanelProfileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jLabelEmpnum, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 331, Short.MAX_VALUE)
                                .addComponent(jLabelBirthday, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanelProfileLayout.setVerticalGroup(
            jPanelProfileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelProfileLayout.createSequentialGroup()
                .addGap(115, 115, 115)
                .addComponent(jLabelTitleProfile, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(jLabelFullname, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelEmpnum)
                .addGap(18, 18, 18)
                .addComponent(jLabelBirthday)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelContact)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelAddress)
                .addGap(41, 41, 41)
                .addComponent(jLabelPosition)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelSuper)
                .addContainerGap(83, Short.MAX_VALUE))
        );

        jTabbedMain.addTab("tab1", jPanelProfile);

        jPanelWallet.setBackground(new java.awt.Color(0, 0, 51));
        jPanelWallet.setForeground(new java.awt.Color(255, 255, 255));

        jLabelTitleWallet.setFont(new java.awt.Font("Segoe UI Semibold", 1, 20)); // NOI18N
        jLabelTitleWallet.setForeground(new java.awt.Color(255, 255, 255));
        jLabelTitleWallet.setText("Payroll Information");
        jLabelTitleWallet.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        jLabelHourlyrate.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabelHourlyrate.setForeground(new java.awt.Color(255, 255, 255));
        jLabelHourlyrate.setText("Hourly Rate");

        jLabelBasicsalary.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabelBasicsalary.setForeground(new java.awt.Color(255, 255, 255));
        jLabelBasicsalary.setText("Basic Salary");

        jLabelRice.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabelRice.setForeground(new java.awt.Color(255, 255, 255));
        jLabelRice.setText("Rice Subsidy");

        jLabelPhoneallowance.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabelPhoneallowance.setForeground(new java.awt.Color(255, 255, 255));
        jLabelPhoneallowance.setText("Phone Allowance");

        jLabelClothingallowance.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabelClothingallowance.setForeground(new java.awt.Color(255, 255, 255));
        jLabelClothingallowance.setText("Clothing Allowance");

        jLabelSSS.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabelSSS.setForeground(new java.awt.Color(255, 255, 255));
        jLabelSSS.setText("SSS");

        jLabelTin.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabelTin.setForeground(new java.awt.Color(255, 255, 255));
        jLabelTin.setText("TIN");

        jLabelPagibig.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabelPagibig.setForeground(new java.awt.Color(255, 255, 255));
        jLabelPagibig.setText("Pagibig");

        jLabelPhilhealth.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabelPhilhealth.setForeground(new java.awt.Color(255, 255, 255));
        jLabelPhilhealth.setText("Philhealth");

        jLabelGrosssalary.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabelGrosssalary.setForeground(new java.awt.Color(255, 255, 255));
        jLabelGrosssalary.setText("Gross Semi-monthly Salary");

        jLabelWitholding.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabelWitholding.setForeground(new java.awt.Color(255, 255, 255));
        jLabelWitholding.setText("Withholding Tax");

        jLabelNetsalary.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabelNetsalary.setForeground(new java.awt.Color(255, 255, 255));
        jLabelNetsalary.setText("Net Salary");

        javax.swing.GroupLayout jPanelWalletLayout = new javax.swing.GroupLayout(jPanelWallet);
        jPanelWallet.setLayout(jPanelWalletLayout);
        jPanelWalletLayout.setHorizontalGroup(
            jPanelWalletLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelWalletLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabelTitleWallet)
                .addGap(30, 30, 30))
            .addGroup(jPanelWalletLayout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addGroup(jPanelWalletLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabelPagibig)
                    .addComponent(jLabelPhilhealth, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabelBasicsalary, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabelHourlyrate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabelRice, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabelPhoneallowance, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabelClothingallowance, javax.swing.GroupLayout.DEFAULT_SIZE, 255, Short.MAX_VALUE)
                    .addComponent(jLabelSSS, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabelTin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanelWalletLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabelGrosssalary, javax.swing.GroupLayout.DEFAULT_SIZE, 286, Short.MAX_VALUE)
                    .addComponent(jLabelWitholding, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabelNetsalary, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(46, Short.MAX_VALUE))
        );
        jPanelWalletLayout.setVerticalGroup(
            jPanelWalletLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelWalletLayout.createSequentialGroup()
                .addGap(115, 115, 115)
                .addComponent(jLabelTitleWallet)
                .addGap(50, 50, 50)
                .addComponent(jLabelHourlyrate)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelBasicsalary)
                .addGap(18, 18, 18)
                .addGroup(jPanelWalletLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelRice)
                    .addComponent(jLabelGrosssalary))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelPhoneallowance)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelWalletLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelClothingallowance)
                    .addComponent(jLabelWitholding))
                .addGap(18, 18, 18)
                .addGroup(jPanelWalletLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelSSS)
                    .addComponent(jLabelNetsalary))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelTin)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelPagibig)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelPhilhealth)
                .addContainerGap(51, Short.MAX_VALUE))
        );

        jTabbedMain.addTab("tab3", jPanelWallet);

        jPanelSupport.setBackground(new java.awt.Color(0, 0, 51));

        jLabelTitleSupport.setFont(new java.awt.Font("Segoe UI Semibold", 0, 20)); // NOI18N
        jLabelTitleSupport.setForeground(new java.awt.Color(255, 255, 255));
        jLabelTitleSupport.setText("Support");

        jLabelSupportHR.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabelSupportHR.setForeground(new java.awt.Color(255, 255, 255));
        jLabelSupportHR.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelSupportHR.setText("HR Department");

        jLabelSupportAC.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabelSupportAC.setForeground(new java.awt.Color(255, 255, 255));
        jLabelSupportAC.setText("Accounting Department");

        jLabelSupportIT.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabelSupportIT.setForeground(new java.awt.Color(255, 255, 255));
        jLabelSupportIT.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelSupportIT.setText("IT Department");

        jLabelHRemail.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabelHRemail.setForeground(new java.awt.Color(255, 255, 255));
        jLabelHRemail.setText("hr@motorph.com");

        jLabelITemail.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabelITemail.setForeground(new java.awt.Color(255, 255, 255));
        jLabelITemail.setText("it@motorph.com");

        jLabelACemail.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabelACemail.setForeground(new java.awt.Color(255, 255, 255));
        jLabelACemail.setText("accounting@motorph.com");

        javax.swing.GroupLayout jPanelSupportLayout = new javax.swing.GroupLayout(jPanelSupport);
        jPanelSupport.setLayout(jPanelSupportLayout);
        jPanelSupportLayout.setHorizontalGroup(
            jPanelSupportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelSupportLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabelTitleSupport)
                .addGap(30, 30, 30))
            .addGroup(jPanelSupportLayout.createSequentialGroup()
                .addGap(105, 105, 105)
                .addGroup(jPanelSupportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabelSupportIT)
                    .addComponent(jLabelSupportAC)
                    .addComponent(jLabelSupportHR))
                .addGap(61, 61, 61)
                .addGroup(jPanelSupportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelACemail)
                    .addComponent(jLabelHRemail)
                    .addComponent(jLabelITemail, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(116, Short.MAX_VALUE))
        );
        jPanelSupportLayout.setVerticalGroup(
            jPanelSupportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelSupportLayout.createSequentialGroup()
                .addGap(115, 115, 115)
                .addComponent(jLabelTitleSupport)
                .addGap(61, 61, 61)
                .addGroup(jPanelSupportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelSupportHR)
                    .addComponent(jLabelHRemail))
                .addGap(16, 16, 16)
                .addGroup(jPanelSupportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelSupportAC)
                    .addComponent(jLabelACemail))
                .addGap(18, 18, 18)
                .addGroup(jPanelSupportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelSupportIT)
                    .addComponent(jLabelITemail))
                .addContainerGap(183, Short.MAX_VALUE))
        );

        jTabbedMain.addTab("tab4", jPanelSupport);

        jPanel1.add(jTabbedMain, new org.netbeans.lib.awtextra.AbsoluteConstraints(158, -42, 650, 530));

        jPanelBottom.setBackground(new java.awt.Color(0, 0, 51));

        jLabelTime.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabelTime.setForeground(new java.awt.Color(255, 255, 255));
        jLabelTime.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelTime.setText("12:12:12 AM");

        jLabelGMT.setForeground(new java.awt.Color(255, 255, 255));
        jLabelGMT.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelGMT.setText("GMT+8 PH Time");

        javax.swing.GroupLayout jPanelBottomLayout = new javax.swing.GroupLayout(jPanelBottom);
        jPanelBottom.setLayout(jPanelBottomLayout);
        jPanelBottomLayout.setHorizontalGroup(
            jPanelBottomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabelTime, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabelGMT, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
        );
        jPanelBottomLayout.setVerticalGroup(
            jPanelBottomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelBottomLayout.createSequentialGroup()
                .addContainerGap(14, Short.MAX_VALUE)
                .addComponent(jLabelGMT)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelTime, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15))
        );

        jPanel1.add(jPanelBottom, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 410, 120, 80));

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 519, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonLeavereqActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonLeavereqActionPerformed
        // TODO add your handling code here:
         /* Leave newClassInstance = new Leave(this);
                 newClassInstance.setVisible(true);
                 
                 dispose(); /*
    }//GEN-LAST:event_jButtonLeavereqActionPerformed

    private void jButtonDashboardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonDashboardActionPerformed
        // TODO add your handling code here:
        jTabbedMain.setSelectedIndex(0);
        jButtonDashboard.setBackground(Color.RED);
        jButtonProfile.setBackground (new java.awt.Color(0,0,51));
        jButtonWallet.setBackground(new java.awt.Color(0,0,51));
        jButtonSupport.setBackground(new java.awt.Color(0,0,51));
        
    }//GEN-LAST:event_jButtonDashboardActionPerformed

    private void jButtonProfileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonProfileActionPerformed
        // TODO add your handling code here:
        jTabbedMain.setSelectedIndex(1);
        jButtonDashboard.setBackground(new java.awt.Color(0,0,51));
        jButtonProfile.setBackground(Color.RED);
        jButtonWallet.setBackground(new java.awt.Color(0,0,51));
        jButtonSupport.setBackground(new java.awt.Color(0,0,51));
    }//GEN-LAST:event_jButtonProfileActionPerformed

    private void jButtonWalletActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonWalletActionPerformed
        // TODO add your handling code here:
        jTabbedMain.setSelectedIndex(2);
        jButtonDashboard.setBackground(new java.awt.Color(0,0,51));
        jButtonProfile.setBackground(new java.awt.Color(0,0,51));
        jButtonWallet.setBackground(Color.RED);
        jButtonSupport.setBackground(new java.awt.Color(0,0,51));
    }//GEN-LAST:event_jButtonWalletActionPerformed

    private void jButtonDashboardMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonDashboardMousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonDashboardMousePressed

    private void jButtonSupportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSupportActionPerformed
        // TODO add your handling code here:
        jTabbedMain.setSelectedIndex(3);
        jButtonDashboard.setBackground(new java.awt.Color(0,0,51));
        jButtonProfile.setBackground(new java.awt.Color(0,0,51));
        jButtonWallet.setBackground(new java.awt.Color(0,0,51));
        jButtonSupport.setBackground(Color.RED);
    }//GEN-LAST:event_jButtonSupportActionPerformed

    private void jButtonSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSearchActionPerformed
        // TODO add your handling code here: 
        String searchinput = jTextFieldSearch.getText();
        
        showEmployeeDetails(searchinput);     
    }//GEN-LAST:event_jButtonSearchActionPerformed

    private void jTextFieldSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldSearchActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_jTextFieldSearchActionPerformed

    private void jTextFieldSearchMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextFieldSearchMouseClicked
        // TODO add your handling code here:
        jTextFieldSearch.setText("");
    }//GEN-LAST:event_jTextFieldSearchMouseClicked

    private void jButtonLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonLogoutActionPerformed
        Login newClassInstance = new Login();
                 newClassInstance.setVisible(true); 
                 
                dispose();
    }//GEN-LAST:event_jButtonLogoutActionPerformed

    private void jButtonViewAllEmployeesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonViewAllEmployeesActionPerformed
        // TODO add your handling code here:
         Employee newClassInstance = new Employee(this);
                 newClassInstance.setVisible(true);
                 
                 dispose(); 
                 
    }//GEN-LAST:event_jButtonViewAllEmployeesActionPerformed

    /**
     * @param args the command line arguments
     */
    //public static void main(String args[]) {
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
            java.util.logging.Logger.getLogger(HRDashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(HRDashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(HRDashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(HRDashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new HRDashboard().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonDashboard;
    private javax.swing.JButton jButtonLeavereq;
    private javax.swing.JButton jButtonLogout;
    private javax.swing.JButton jButtonProfile;
    private javax.swing.JButton jButtonSearch;
    private javax.swing.JButton jButtonSupport;
    private javax.swing.JButton jButtonViewAllEmployees;
    private javax.swing.JButton jButtonWallet;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabelACemail;
    private javax.swing.JLabel jLabelAddress;
    private javax.swing.JLabel jLabelBasicsalary;
    private javax.swing.JLabel jLabelBirthday;
    private javax.swing.JLabel jLabelClothingallowance;
    private javax.swing.JLabel jLabelContact;
    private javax.swing.JLabel jLabelDashbirthday;
    private javax.swing.JLabel jLabelDashempnum;
    private javax.swing.JLabel jLabelDashname;
    private javax.swing.JLabel jLabelDate;
    private javax.swing.JLabel jLabelEmpnum;
    private javax.swing.JLabel jLabelFullname;
    private javax.swing.JLabel jLabelGMT;
    private javax.swing.JLabel jLabelGreet;
    private javax.swing.JLabel jLabelGrosssalary;
    private javax.swing.JLabel jLabelHRemail;
    private javax.swing.JLabel jLabelHourlyrate;
    private javax.swing.JLabel jLabelITemail;
    private javax.swing.JLabel jLabelLogo;
    private javax.swing.JLabel jLabelNetsalary;
    private javax.swing.JLabel jLabelPagibig;
    private javax.swing.JLabel jLabelPhilhealth;
    private javax.swing.JLabel jLabelPhoneallowance;
    private javax.swing.JLabel jLabelPosition;
    private javax.swing.JLabel jLabelRedIcon;
    private javax.swing.JLabel jLabelRice;
    private javax.swing.JLabel jLabelSSS;
    private javax.swing.JLabel jLabelSeparate;
    private javax.swing.JLabel jLabelSuper;
    private javax.swing.JLabel jLabelSupportAC;
    private javax.swing.JLabel jLabelSupportHR;
    private javax.swing.JLabel jLabelSupportIT;
    private javax.swing.JLabel jLabelTime;
    private javax.swing.JLabel jLabelTin;
    private javax.swing.JLabel jLabelTitleProfile;
    private javax.swing.JLabel jLabelTitleSupport;
    private javax.swing.JLabel jLabelTitleWallet;
    private javax.swing.JLabel jLabelWitholding;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanelBottom;
    private javax.swing.JPanel jPanelDashboard;
    private javax.swing.JPanel jPanelHeader;
    private javax.swing.JPanel jPanelProfile;
    private javax.swing.JPanel jPanelSidebar;
    private javax.swing.JPanel jPanelSupport;
    private javax.swing.JPanel jPanelWallet;
    private javax.swing.JTabbedPane jTabbedMain;
    private javax.swing.JTextField jTextFieldSearch;
    // End of variables declaration//GEN-END:variables
}
