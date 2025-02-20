/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package motorph9_oop;

import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JTextFieldDateEditor;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.util.List;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author Shekinah Jabez
 */
public class Leave extends javax.swing.JFrame {

    private EmployeeLeaveTracker leaveTracker;
    private CSVReader csvReader;
    private List<String[]> leaveBalances;
    private List<LeaveRequestData> leaveRequests = new ArrayList<>();
    private String leaveBalancesFilePath = "src\\motorph9\\LeaveBalances.csv";
    private final HRDashboard dashboard;
    private LeaveRequestService leaveRequestService;
    /**
     * Creates new form LeaveRequest
     */
    public Leave(HRDashboard dashboard) {
        initComponents();
        
        leaveTracker = loadLeaveTracker("src\\motorph9\\LeaveBalances.csv");
        csvReader = new CSVReader("src\\motorph9\\LeaveBalances.csv");
        this.dashboard = dashboard;
        leaveRequestService = new LeaveRequestService();

        jButtonClear.setEnabled(false);
        
        ArrayList<Character> sample = new ArrayList<Character>();
        

        setLocationRelativeTo(null);
        
        Timer timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateTimeAndDate();
            }
        });
        timer.start();
    }

    private void updateTimeAndDate() {
        
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm:ss a");
        String time = timeFormat.format(new Date());

        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, MMMM d, yyyy");
        String date = dateFormat.format(new Date());

        jLabelTime.setText(time);
        jLabelDate.setText(date);
    
    }
    
    private void updateTableModel() {
        DefaultTableModel model = (DefaultTableModel) jTableRequest.getModel();
        model.setRowCount(0);
        
        for (LeaveRequestData request : leaveRequests) {
            model.addRow(new Object[]{
                request.getLeaveType(),
                request.getLeaveDuration(),
                request.getStartDate(),
                request.getEndDate(),
                request.getReason(),
                request.getStatus()
            });
        }
    }

    private EmployeeLeaveTracker loadLeaveTracker(String csvFile) { // Corrected method
        EmployeeLeaveTracker tracker = null;
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            String line;
            br.readLine(); // Skip header line
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length >= 4) {
                    String employeeNo = values[0].trim();
                    int sickLeave = Integer.parseInt(values[1].trim());
                    int vacationLeave = Integer.parseInt(values[2].trim());
                    int birthdayLeave = Integer.parseInt(values[3].trim());
                    tracker = new EmployeeLeaveTracker(sickLeave, vacationLeave, birthdayLeave);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error reading leave tracker CSV file", "Error", JOptionPane.ERROR_MESSAGE);
        }
        return tracker;
    }
    
    private int calculateLeaveDuration(String startDate, String endDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        try {
            Date start = dateFormat.parse(startDate);
            Date end = dateFormat.parse(endDate);
            long diff = end.getTime() - start.getTime();
            return (int) (diff / (1000 * 60 * 60 * 24)) + 1;
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error calculating leave duration.");
            return 0;
        }
    }
    
    private boolean isValidDateFormat(String date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        dateFormat.setLenient(false);
        try {
            dateFormat.parse(date);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    private class LeaveRequestData {
        private String leaveType;
        private String startDate;
        private String endDate;
        private String reason;
        private String status = "Pending";

        public LeaveRequestData(String leaveType, String startDate, String endDate, String reason) {
            this.leaveType = leaveType;
            this.startDate = startDate;
            this.endDate = endDate;
            this.reason = reason;
        }

        public String getLeaveType() {
            return leaveType;
        }

        public String getStartDate() {
            return startDate;
        }

        public String getEndDate() {
            return endDate;
        }

        public String getReason() {
            return reason;
        }

        public String getStatus() {
            return status;
        }
        
        public void setStatus(String status) {
            this.status = status;
        }

        public int getLeaveDuration() {
            SimpleDateFormat dateFormat = new SimpleDateFormat("mm/dd/yyyy");
            try {
                Date start = dateFormat.parse(startDate);
                Date end = dateFormat.parse(endDate);
                long diff = end.getTime() - start.getTime();
                return (int) (diff / (1000 * 60 * 60 * 24)) + 1;
            } catch (Exception e) {
                e.printStackTrace();
                return 0;
            }
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

        jPanelLeaverequestMain = new javax.swing.JPanel();
        jPanelMotorPHTitle = new javax.swing.JPanel();
        jLabelTitle = new javax.swing.JLabel();
        jLabelLeaveRequestmanagement = new javax.swing.JLabel();
        jLabelDate = new javax.swing.JLabel();
        jButtonDashboard = new javax.swing.JButton();
        jPanelButtons = new javax.swing.JPanel();
        jButtonViewrequests = new javax.swing.JButton();
        jButtonAddrequest = new javax.swing.JButton();
        jLabelTime = new javax.swing.JLabel();
        jLabelGMT = new javax.swing.JLabel();
        jTabbedMainRequest = new javax.swing.JTabbedPane();
        jPanelRequests = new javax.swing.JPanel();
        jLabelRequests = new javax.swing.JLabel();
        jScrollPaneRequests = new javax.swing.JScrollPane();
        jTableRequest = new javax.swing.JTable();
        jButtonWithdrawleave = new javax.swing.JButton();
        jPanelLeaverequest = new javax.swing.JPanel();
        jLabelLeaverequest = new javax.swing.JLabel();
        jLabelEmployeeno = new javax.swing.JLabel();
        jTextFieldEmployeeno = new javax.swing.JTextField();
        jLabelLeavetype = new javax.swing.JLabel();
        jLabelStartdate = new javax.swing.JLabel();
        jComboBoxLeavetype = new javax.swing.JComboBox<>();
        jLabelEnddate = new javax.swing.JLabel();
        jLabelMessage = new javax.swing.JLabel();
        jLabelReason = new javax.swing.JLabel();
        jButtonSubmit = new javax.swing.JButton();
        jButtonCancel = new javax.swing.JButton();
        jTextFieldReason = new javax.swing.JTextField();
        jButtonClear = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(830, 490));

        jPanelLeaverequestMain.setBackground(new java.awt.Color(0, 0, 0));
        jPanelLeaverequestMain.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanelMotorPHTitle.setBackground(new java.awt.Color(0, 0, 0));

        jLabelTitle.setFont(new java.awt.Font("Segoe UI Semibold", 0, 20)); // NOI18N
        jLabelTitle.setForeground(new java.awt.Color(204, 0, 0));
        jLabelTitle.setIcon(new javax.swing.ImageIcon(getClass().getResource("/logos9/empdes3.png"))); // NOI18N

        jLabelLeaveRequestmanagement.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabelLeaveRequestmanagement.setForeground(new java.awt.Color(255, 255, 255));
        jLabelLeaveRequestmanagement.setText("LEAVE REQUEST MANAGEMENT");

        jLabelDate.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jLabelDate.setForeground(new java.awt.Color(255, 255, 255));
        jLabelDate.setText("Wednesday, December 25, 2012");
        jLabelDate.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        jButtonDashboard.setBackground(new java.awt.Color(0, 0, 51));
        jButtonDashboard.setForeground(new java.awt.Color(255, 255, 255));
        jButtonDashboard.setText("Dashboard");
        jButtonDashboard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonDashboardActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelMotorPHTitleLayout = new javax.swing.GroupLayout(jPanelMotorPHTitle);
        jPanelMotorPHTitle.setLayout(jPanelMotorPHTitleLayout);
        jPanelMotorPHTitleLayout.setHorizontalGroup(
            jPanelMotorPHTitleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelMotorPHTitleLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 84, Short.MAX_VALUE)
                .addGroup(jPanelMotorPHTitleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelMotorPHTitleLayout.createSequentialGroup()
                        .addComponent(jLabelLeaveRequestmanagement)
                        .addGap(55, 55, 55)
                        .addComponent(jLabelDate)
                        .addGap(20, 20, 20))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelMotorPHTitleLayout.createSequentialGroup()
                        .addComponent(jButtonDashboard)
                        .addGap(58, 58, 58))))
        );
        jPanelMotorPHTitleLayout.setVerticalGroup(
            jPanelMotorPHTitleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelMotorPHTitleLayout.createSequentialGroup()
                .addGroup(jPanelMotorPHTitleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelMotorPHTitleLayout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(jLabelDate)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButtonDashboard))
                    .addGroup(jPanelMotorPHTitleLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanelMotorPHTitleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelLeaveRequestmanagement)
                            .addComponent(jLabelTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(24, Short.MAX_VALUE))
        );

        jPanelLeaverequestMain.add(jPanelMotorPHTitle, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 900, -1));

        jPanelButtons.setBackground(new java.awt.Color(0, 0, 51));

        jButtonViewrequests.setBackground(new java.awt.Color(0, 0, 51));
        jButtonViewrequests.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jButtonViewrequests.setForeground(new java.awt.Color(255, 255, 255));
        jButtonViewrequests.setText("View Requests");
        jButtonViewrequests.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonViewrequestsActionPerformed(evt);
            }
        });

        jButtonAddrequest.setBackground(new java.awt.Color(0, 0, 51));
        jButtonAddrequest.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jButtonAddrequest.setForeground(new java.awt.Color(255, 255, 255));
        jButtonAddrequest.setText("Add Request");
        jButtonAddrequest.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAddrequestActionPerformed(evt);
            }
        });

        jLabelTime.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jLabelTime.setForeground(new java.awt.Color(255, 255, 255));
        jLabelTime.setText("12:12:12 AM");

        jLabelGMT.setForeground(new java.awt.Color(255, 255, 255));
        jLabelGMT.setText("GMT+8 PH Time");

        javax.swing.GroupLayout jPanelButtonsLayout = new javax.swing.GroupLayout(jPanelButtons);
        jPanelButtons.setLayout(jPanelButtonsLayout);
        jPanelButtonsLayout.setHorizontalGroup(
            jPanelButtonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelButtonsLayout.createSequentialGroup()
                .addGroup(jPanelButtonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelButtonsLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanelButtonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jButtonViewrequests, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButtonAddrequest, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanelButtonsLayout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addComponent(jLabelGMT))
                    .addGroup(jPanelButtonsLayout.createSequentialGroup()
                        .addGap(41, 41, 41)
                        .addComponent(jLabelTime)))
                .addContainerGap(21, Short.MAX_VALUE))
        );
        jPanelButtonsLayout.setVerticalGroup(
            jPanelButtonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelButtonsLayout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(jButtonViewrequests)
                .addGap(31, 31, 31)
                .addComponent(jButtonAddrequest)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 195, Short.MAX_VALUE)
                .addComponent(jLabelTime)
                .addGap(18, 18, 18)
                .addComponent(jLabelGMT)
                .addGap(28, 28, 28))
        );

        jPanelLeaverequestMain.add(jPanelButtons, new org.netbeans.lib.awtextra.AbsoluteConstraints(18, 109, -1, 390));

        jTabbedMainRequest.setBackground(new java.awt.Color(0, 0, 0));
        jTabbedMainRequest.setForeground(new java.awt.Color(255, 255, 255));

        jPanelRequests.setBackground(new java.awt.Color(0, 0, 51));

        jLabelRequests.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabelRequests.setForeground(new java.awt.Color(255, 255, 255));
        jLabelRequests.setText("Requests");

        jTableRequest.setBackground(new java.awt.Color(0, 0, 0));
        jTableRequest.setForeground(new java.awt.Color(255, 255, 255));
        jTableRequest.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Leave Type", "Leave Duration", "Start Date ", "End Date", "Reason", "Status"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Integer.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableRequest.setToolTipText("");
        jScrollPaneRequests.setViewportView(jTableRequest);

        jButtonWithdrawleave.setBackground(new java.awt.Color(0, 0, 51));
        jButtonWithdrawleave.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jButtonWithdrawleave.setForeground(new java.awt.Color(255, 255, 255));
        jButtonWithdrawleave.setText("Withdraw Leave");
        jButtonWithdrawleave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonWithdrawleaveActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelRequestsLayout = new javax.swing.GroupLayout(jPanelRequests);
        jPanelRequests.setLayout(jPanelRequestsLayout);
        jPanelRequestsLayout.setHorizontalGroup(
            jPanelRequestsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelRequestsLayout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(jPanelRequestsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButtonWithdrawleave)
                    .addComponent(jScrollPaneRequests, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 615, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanelRequestsLayout.createSequentialGroup()
                        .addComponent(jLabelRequests, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(504, 504, 504)))
                .addContainerGap(22, Short.MAX_VALUE))
        );
        jPanelRequestsLayout.setVerticalGroup(
            jPanelRequestsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelRequestsLayout.createSequentialGroup()
                .addContainerGap(124, Short.MAX_VALUE)
                .addComponent(jLabelRequests)
                .addGap(27, 27, 27)
                .addComponent(jScrollPaneRequests, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(45, 45, 45)
                .addComponent(jButtonWithdrawleave)
                .addGap(25, 25, 25))
        );

        jTabbedMainRequest.addTab("View Request", jPanelRequests);

        jPanelLeaverequest.setBackground(new java.awt.Color(0, 0, 51));
        jPanelLeaverequest.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabelLeaverequest.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabelLeaverequest.setForeground(new java.awt.Color(255, 255, 255));
        jLabelLeaverequest.setText("Leave Request");
        jPanelLeaverequest.add(jLabelLeaverequest, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 140, -1, -1));

        jLabelEmployeeno.setFont(new java.awt.Font("Segoe UI Semibold", 1, 14)); // NOI18N
        jLabelEmployeeno.setForeground(new java.awt.Color(255, 255, 255));
        jLabelEmployeeno.setText("Employee No");
        jPanelLeaverequest.add(jLabelEmployeeno, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 240, -1, -1));

        jTextFieldEmployeeno.setBackground(new java.awt.Color(0, 0, 0));
        jTextFieldEmployeeno.setForeground(new java.awt.Color(255, 255, 255));
        jTextFieldEmployeeno.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextFieldEmployeenoKeyTyped(evt);
            }
        });
        jPanelLeaverequest.add(jTextFieldEmployeeno, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 240, 390, -1));

        jLabelLeavetype.setFont(new java.awt.Font("Segoe UI Semibold", 1, 14)); // NOI18N
        jLabelLeavetype.setForeground(new java.awt.Color(255, 255, 255));
        jLabelLeavetype.setText("Leave Type");
        jPanelLeaverequest.add(jLabelLeavetype, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 270, -1, -1));

        jLabelStartdate.setFont(new java.awt.Font("Segoe UI Semibold", 1, 14)); // NOI18N
        jLabelStartdate.setForeground(new java.awt.Color(255, 255, 255));
        jLabelStartdate.setText("Start Date");
        jPanelLeaverequest.add(jLabelStartdate, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 300, -1, -1));

        jComboBoxLeavetype.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Vacation Leave", "Sick Leave", "Birthday Leave" }));
        jPanelLeaverequest.add(jComboBoxLeavetype, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 270, 390, -1));

        jLabelEnddate.setFont(new java.awt.Font("Segoe UI Semibold", 1, 14)); // NOI18N
        jLabelEnddate.setForeground(new java.awt.Color(255, 255, 255));
        jLabelEnddate.setText("End Date");
        jPanelLeaverequest.add(jLabelEnddate, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 330, -1, -1));

        jLabelMessage.setFont(new java.awt.Font("Segoe UI Semibold", 1, 14)); // NOI18N
        jLabelMessage.setForeground(new java.awt.Color(255, 255, 255));
        jLabelMessage.setText("Please fill out the required information");
        jPanelLeaverequest.add(jLabelMessage, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 190, -1, -1));

        jLabelReason.setFont(new java.awt.Font("Segoe UI Semibold", 1, 14)); // NOI18N
        jLabelReason.setForeground(new java.awt.Color(255, 255, 255));
        jLabelReason.setText("Reason");
        jPanelLeaverequest.add(jLabelReason, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 360, -1, -1));

        jButtonSubmit.setBackground(new java.awt.Color(0, 0, 51));
        jButtonSubmit.setForeground(new java.awt.Color(255, 255, 255));
        jButtonSubmit.setText("Submit");
        jButtonSubmit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSubmitActionPerformed(evt);
            }
        });
        jPanelLeaverequest.add(jButtonSubmit, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 450, -1, -1));

        jButtonCancel.setBackground(new java.awt.Color(0, 0, 51));
        jButtonCancel.setForeground(new java.awt.Color(255, 255, 255));
        jButtonCancel.setText("Cancel");
        jButtonCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCancelActionPerformed(evt);
            }
        });
        jPanelLeaverequest.add(jButtonCancel, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 450, -1, -1));

        jTextFieldReason.setBackground(new java.awt.Color(0, 0, 0));
        jTextFieldReason.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jTextFieldReason.setForeground(new java.awt.Color(255, 255, 255));
        jTextFieldReason.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextFieldReasonKeyReleased(evt);
            }
        });
        jPanelLeaverequest.add(jTextFieldReason, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 360, 390, 70));

        jButtonClear.setBackground(new java.awt.Color(0, 0, 51));
        jButtonClear.setForeground(new java.awt.Color(255, 255, 255));
        jButtonClear.setText("Clear");
        jButtonClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonClearActionPerformed(evt);
            }
        });
        jPanelLeaverequest.add(jButtonClear, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 450, -1, -1));

        jTabbedMainRequest.addTab("Add Request", jPanelLeaverequest);

        jPanelLeaverequestMain.add(jTabbedMainRequest, new org.netbeans.lib.awtextra.AbsoluteConstraints(172, -39, -1, 540));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanelLeaverequestMain, javax.swing.GroupLayout.PREFERRED_SIZE, 898, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelLeaverequestMain, javax.swing.GroupLayout.PREFERRED_SIZE, 540, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonViewrequestsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonViewrequestsActionPerformed
        // TODO add your handling code here:
        jTabbedMainRequest.setSelectedIndex(0);
    }//GEN-LAST:event_jButtonViewrequestsActionPerformed

    private void jButtonAddrequestActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAddrequestActionPerformed
        // TODO add your handling code here:
        jTabbedMainRequest.setSelectedIndex(1);
    }//GEN-LAST:event_jButtonAddrequestActionPerformed

    private void jButtonDashboardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonDashboardActionPerformed
        // TODO add your handling code here:
        
        dashboard.setVisible(true);
       /*HRDashboard newClassInstance = new HRDashboard(firstName,currentEmployeeId);
                 newClassInstance.setVisible(true);*/

                 
                 dispose(); 
    }//GEN-LAST:event_jButtonDashboardActionPerformed

    private void jButtonSubmitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSubmitActionPerformed
        // TODO add your handling code here:
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");                       
        String employeeNo = jTextFieldEmployeeno.getText();
        String leaveType = jComboBoxLeavetype.getSelectedItem().toString();                        
        String reason = jTextFieldReason.getText();
        
        String startDate;
        String endDate;
            
        try{             
            startDate = dateFormat.format(jDateChooser_StartDate.getDate());       
            endDate = dateFormat.format(jDateChooser_EndDate.getDate());
        }catch(NullPointerException  e){
            e.printStackTrace();
            startDate = null;
            endDate = null;
        }
       
        if (employeeNo.isEmpty() || leaveType.isEmpty() || startDate == null || endDate == null || reason.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields must be filled out.");
            return;
        }

        try {
            int leaveDuration = calculateLeaveDuration(startDate, endDate);
            if (leaveDuration <= 0) {
                JOptionPane.showMessageDialog(this, "Invalid dates: End date must be after start date..");
                return;
            }

            if (!leaveTracker.hasSufficientLeave(leaveType, leaveDuration)) {
                JOptionPane.showMessageDialog(this, "Insufficient leave balance for " + leaveType + ".");
                return;
            }

            
            CSVReader csvReader = new CSVReader("src\\motorph9\\EmployeeDetails.csv");
            csvReader.loadLeaveBalances("src\\motorph9\\LeaveBalances.csv"); 
            String[] employeeInfo = csvReader.getEmployeeInfoFromLeaveBalances(employeeNo);
            if (employeeInfo == null) {
                JOptionPane.showMessageDialog(this, "Employee number not found.");
                return;
            }
            String firstName = employeeInfo[5]; 
            String lastName = employeeInfo[4];  

            StringBuilder message = new StringBuilder("Please confirm your leave request details:\n\n");
            message.append("Employee Number: ").append(employeeNo).append("\n");
            message.append("Employee Name: ").append(firstName).append(" ").append(lastName).append("\n");
            message.append("Leave Type: ").append(leaveType).append("\n");
            message.append("Start Date: ").append(startDate).append("\n");
            message.append("End Date: ").append(endDate).append("\n");
            message.append("Leave Duration: ").append(leaveDuration).append(" day/s\n\n");
            message.append("Do you want to submit this leave request?\n");
            
            message.append("Leave Balances:\n");
            message.append("Sick Leave Balance: ").append(leaveTracker.getSickLeaveBalance()).append("\n");
            message.append("Vacation Leave Balance: ").append(leaveTracker.getVacationLeaveBalance()).append("\n");
            message.append("Birthday Leave Balance: ").append(leaveTracker.getBirthdayLeaveBalance()).append("\n");
            
            int response = JOptionPane.showConfirmDialog(this, message.toString(), "Confirm Leave Request", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
            if (response == JOptionPane.YES_OPTION) {
                leaveTracker.deductLeave(leaveType, leaveDuration);
                
                LeaveRequestData leaveRequest = new LeaveRequestData(leaveType, startDate, endDate, reason);
                leaveRequests.add(leaveRequest);
                updateTableModel();

                jTextFieldEmployeeno.setText("");
                jDateChooser_StartDate.setCalendar(null);
                jDateChooser_EndDate.setCalendar(null);
                jTextFieldReason.setText("");

                JOptionPane.showMessageDialog(this, "Leave request submitted successfully.");
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error submitting leave request.");
        }
    }//GEN-LAST:event_jButtonSubmitActionPerformed

    private void jButtonCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCancelActionPerformed
        // TODO add your handling code here:     
        if (jTextFieldEmployeeno.getText().isEmpty() &&            
            (jDateChooser_StartDate.getCalendar() == null) &&              
            (jDateChooser_EndDate.getCalendar() == null) &&
            jTextFieldReason.getText().isEmpty()) {
                     
                jTextFieldEmployeeno.setText("");
                jDateChooser_StartDate.setCalendar(null);
                jDateChooser_EndDate.setCalendar(null);
                jTextFieldReason.setText("");
                jTabbedMainRequest.setSelectedIndex(0);
                return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this, 
                                                    "Do you want to cancel the leave request?", 
                                                    "Cancel Leave Request", 
                                                    JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            // Clear the text fields
                jTextFieldEmployeeno.setText("");
                jDateChooser_StartDate.setCalendar(null);
                jDateChooser_EndDate.setCalendar(null);
                jTextFieldReason.setText("");
                jTabbedMainRequest.setSelectedIndex(0);                
        }                                   
    }//GEN-LAST:event_jButtonCancelActionPerformed

    private void jButtonWithdrawleaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonWithdrawleaveActionPerformed
        // TODO add your handling code here:
        int selectedRow = jTableRequest.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a leave request to withdraw.");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Do you want to withdraw the selected leave request?", "Withdraw Leave Request", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            // Change the status of the selected leave request to "Withdrawn"
            LeaveRequestData selectedRequest = leaveRequests.get(selectedRow);
            selectedRequest.setStatus("Withdrawn");
            
            // Update the table model to reflect the status change
            updateTableModel();
        }
    }//GEN-LAST:event_jButtonWithdrawleaveActionPerformed

    private void jButtonClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonClearActionPerformed
        // TODO add your handling code here:
        if (!jTextFieldEmployeeno.getText().isEmpty() ||            
            !(jDateChooser_StartDate.getCalendar() == null) ||              
            !(jDateChooser_EndDate.getCalendar() == null) ||
            !jTextFieldReason.getText().isEmpty()) {
         
                jTextFieldEmployeeno.setText("");
                jDateChooser_StartDate.setCalendar(null);
                jDateChooser_EndDate.setCalendar(null);
                jTextFieldReason.setText("");
        }
        
        jButtonClear.setEnabled(false);
    }//GEN-LAST:event_jButtonClearActionPerformed
    
    private void jTextFieldReasonKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldReasonKeyReleased
        // TODO add your handling code here:
        if(!jTextFieldReason.getText().isEmpty() || !jTextFieldReason.getText().isBlank()){    
            jButtonClear.setEnabled(true);
            return;
        }
        areFieldsEmpty();
    }//GEN-LAST:event_jTextFieldReasonKeyReleased

    private void jTextFieldEmployeenoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldEmployeenoKeyTyped
        boolean max = jTextFieldEmployeeno.getText().length() > 4; 
        char empID = evt.getKeyChar();                             
        if(!Character.isDigit(empID)|| max){evt.consume();} 
                             
        if(jTextFieldEmployeeno.getText().length() > -1){
            if(Character.isDigit(empID)){
                jButtonClear.setEnabled(true);
                return;
            }
        }       
        areFieldsEmpty();       
    }//GEN-LAST:event_jTextFieldEmployeenoKeyTyped
    
   private void areFieldsEmpty(){
       
       if(jTextFieldEmployeeno.getText().isBlank() && 
          jTextFieldEmployeeno.getText().isEmpty() &&
          jTextFieldReason.getText().isBlank() && 
          jTextFieldReason.getText().isEmpty()){
            jButtonClear.setEnabled(false);            
       }   
   }
    
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
            java.util.logging.Logger.getLogger(Leave.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Leave.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Leave.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Leave.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                //new Leave().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonAddrequest;
    private javax.swing.JButton jButtonCancel;
    private javax.swing.JButton jButtonClear;
    private javax.swing.JButton jButtonDashboard;
    private javax.swing.JButton jButtonSubmit;
    private javax.swing.JButton jButtonViewrequests;
    private javax.swing.JButton jButtonWithdrawleave;
    private javax.swing.JComboBox<String> jComboBoxLeavetype;
    private javax.swing.JLabel jLabelDate;
    private javax.swing.JLabel jLabelEmployeeno;
    private javax.swing.JLabel jLabelEnddate;
    private javax.swing.JLabel jLabelGMT;
    private javax.swing.JLabel jLabelLeaveRequestmanagement;
    private javax.swing.JLabel jLabelLeaverequest;
    private javax.swing.JLabel jLabelLeavetype;
    private javax.swing.JLabel jLabelMessage;
    private javax.swing.JLabel jLabelReason;
    private javax.swing.JLabel jLabelRequests;
    private javax.swing.JLabel jLabelStartdate;
    private javax.swing.JLabel jLabelTime;
    private javax.swing.JLabel jLabelTitle;
    private javax.swing.JPanel jPanelButtons;
    private javax.swing.JPanel jPanelLeaverequest;
    private javax.swing.JPanel jPanelLeaverequestMain;
    private javax.swing.JPanel jPanelMotorPHTitle;
    private javax.swing.JPanel jPanelRequests;
    private javax.swing.JScrollPane jScrollPaneRequests;
    private javax.swing.JTabbedPane jTabbedMainRequest;
    private javax.swing.JTable jTableRequest;
    private javax.swing.JTextField jTextFieldEmployeeno;
    private javax.swing.JTextField jTextFieldReason;
    // End of variables declaration//GEN-END:variables
}
