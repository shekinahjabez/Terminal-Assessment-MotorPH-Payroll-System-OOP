/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package motorph9;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.Timer;
import javax.swing.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;

/**
 *
 * @author Shekinah Jabez
 */
public class EmployeeRecords extends javax.swing.JFrame {
    
    private String currentEmployeeId;
    private final HRDashboard dashboard;

    /**
     * Creates new form EmployeeDetails
     */
    public EmployeeRecords (String employeeNumber, HRDashboard dashboard) {
        
        initComponents();
        
        jTextFieldEmployeeno.setText(employeeNumber);
         
        CSVReader reader = new CSVReader("src\\motorph9\\EmployeeDetails.csv");
        
        setLocationRelativeTo(null); 
        
        initializeMonthComboBox();
        
        this.currentEmployeeId = employeeNumber;
        this.dashboard = dashboard;
        
        Timer timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateTimeAndDate();
            }
        });
        timer.start();
   
    //public void loadEmployeeInfo(String employeeNo) {    
        String[] employee = reader.getEmployeeInfo(employeeNumber);
        if (employee != null) {
            
            jTextFieldEmployeeno.setText(employee[0]);
            jTextFieldLastname.setText(employee[3]);
            jTextFieldFirstname.setText(employee[4]);
            jTextFieldSssno.setText(employee[8]);
            jTextFieldPhilhealthno.setText(employee[9]);
            jTextFieldTinno.setText(employee[10]);
            jTextFieldPagibigno.setText(employee[11]);
            
            
        }

         
    }
    
    private void updateTimeAndDate() {
        
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm:ss a");
        String time = timeFormat.format(new Date());

        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, MMMM d, yyyy");
        String date = dateFormat.format(new Date());

        jLabelTime.setText(time);
        jLabelDate.setText(date);
    
    }
    
     private void initializeMonthComboBox() {
        String[] months = new String[] {
            "January", "February", "March", "April", "May", "June", 
            "July", "August", "September", "October", "November", "December"
        };
        jComboBoxMonths.setModel(new javax.swing.DefaultComboBoxModel<>(months));
    }
    
    private static double calculatePagibigDeduction() {
    return 100.0; // fixed Pagibig deduction of 100
    
    }
     
    private static double calculateSSSDeduction(double basicSalary) {
        double[] thresholds = {19750, 20250, 20750, 21250, 21750, 22250, 22750, 23250, 23750, 24250};
        double[] deductions = {900.00, 922.50, 945.00, 967.50, 990.00, 1012.50, 1035.00, 1057.50, 1080.00, 1102.50};

        for (int i = 0; i < thresholds.length; i++) {
            if (basicSalary >= thresholds[i] && (i == thresholds.length - 1 || basicSalary < thresholds[i + 1])) {
                return deductions[i];
            }
        }
         // Default deduction if basicSalary exceeds all thresholds
        var s = 1125.00;
        return s;
    }
    
    // Method to calculate withholding tax based on the provided net salary
    private static double calculateWithholdingTax(double netSalary) {
        double[] thresholds = {20832, 33333, 66667, 166667, 666667};
        double[] rates = {0.20, 0.25, 0.30, 0.32, 0.35};
        double[] baseTaxes = {0, 2500, 10833, 40833.33, 200833.33};

        double withholdingTax = 0;
        for (int i = 0; i < thresholds.length; i++) {
            if (netSalary > thresholds[i]) {
                withholdingTax = baseTaxes[i] + (netSalary - thresholds[i]) * rates[i];
            } else {
                break;
            }
        }
        return withholdingTax;
    }
    
    public int getWeekdaysInMonth(String monthName, int year) {
        int month = convertMonthNameToNumber(monthName);
        YearMonth yearMonth = YearMonth.of(year, month);
        int daysInMonth = yearMonth.lengthOfMonth();

        int weekdaysCount = 0;
        for (int day = 1; day <= daysInMonth; day++) {
            LocalDate date = LocalDate.of(year, month, day);
            DayOfWeek dayOfWeek = date.getDayOfWeek();
            if (dayOfWeek != DayOfWeek.SATURDAY && dayOfWeek != DayOfWeek.SUNDAY) {
                weekdaysCount++;
            }
        }
        return weekdaysCount;
    }
    
    private int convertMonthNameToNumber(String monthName) {
        switch (monthName.toLowerCase()) {
            case "january":
                return 1;
            case "february":
                return 2;
            case "march":
                return 3;
            case "april":
                return 4;
            case "may":
                return 5;
            case "june":
                return 6;
            case "july":
                return 7;
            case "august":
                return 8;
            case "september":
                return 9;
            case "october":
                return 10;
            case "november":
                return 11;
            case "december":
                return 12;
            default:
                throw new IllegalArgumentException("Invalid month name: " + monthName);
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

        jPanelEmployeedetailsmain = new javax.swing.JPanel();
        jPanelDetails = new javax.swing.JPanel();
        jLabelLastname = new javax.swing.JLabel();
        jLabelFirstname = new javax.swing.JLabel();
        jLabelEmployeeno = new javax.swing.JLabel();
        jLabelSSSno = new javax.swing.JLabel();
        jLabelPhilhealthno = new javax.swing.JLabel();
        jLabelTINno = new javax.swing.JLabel();
        jLabelPagibigno = new javax.swing.JLabel();
        jTextFieldEmployeeno = new javax.swing.JTextField();
        jTextFieldLastname = new javax.swing.JTextField();
        jTextFieldFirstname = new javax.swing.JTextField();
        jTextFieldPhilhealthno = new javax.swing.JTextField();
        jTextFieldSssno = new javax.swing.JTextField();
        jTextFieldPagibigno = new javax.swing.JTextField();
        jTextFieldTinno = new javax.swing.JTextField();
        jLabelBasicsalary = new javax.swing.JLabel();
        jTextFieldBasicsalary = new javax.swing.JTextField();
        jTextFieldTotaldeductions = new javax.swing.JTextField();
        jTextFieldNetpay = new javax.swing.JTextField();
        jLabelTotalallowances = new javax.swing.JLabel();
        jButtonCompute = new javax.swing.JButton();
        jLabelTotaldeductions = new javax.swing.JLabel();
        jLabelNetpay = new javax.swing.JLabel();
        jTextFieldTotalallowances = new javax.swing.JTextField();
        jLabelHourlyrate = new javax.swing.JLabel();
        jTextFieldHourlyrate = new javax.swing.JTextField();
        jLabelPayslipofthemonth = new javax.swing.JLabel();
        jTextFieldPayslipofthemonth = new javax.swing.JTextField();
        jLabelGrosspay = new javax.swing.JLabel();
        jTextFieldGrosspay = new javax.swing.JTextField();
        jButtonEmployeelist = new javax.swing.JButton();
        jLabelTitle = new javax.swing.JLabel();
        jLabelDate = new javax.swing.JLabel();
        jLabelSelectmonth = new javax.swing.JLabel();
        jComboBoxMonths = new javax.swing.JComboBox<>();
        jPanelDateandTime = new javax.swing.JPanel();
        jLabelTime = new javax.swing.JLabel();
        jLabelGMT = new javax.swing.JLabel();
        jLabelEmployeesectionTitle = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(830, 490));

        jPanelEmployeedetailsmain.setBackground(new java.awt.Color(0, 0, 0));
        jPanelEmployeedetailsmain.setMaximumSize(new java.awt.Dimension(2147483647, 2147483647));
        jPanelEmployeedetailsmain.setMinimumSize(new java.awt.Dimension(830, 490));

        jPanelDetails.setBackground(new java.awt.Color(0, 0, 51));

        jLabelLastname.setFont(new java.awt.Font("Segoe UI Semibold", 1, 14)); // NOI18N
        jLabelLastname.setForeground(new java.awt.Color(255, 255, 255));
        jLabelLastname.setText("LAST NAME");

        jLabelFirstname.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabelFirstname.setForeground(new java.awt.Color(255, 255, 255));
        jLabelFirstname.setText("FIRST NAME");

        jLabelEmployeeno.setFont(new java.awt.Font("Segoe UI Semibold", 1, 14)); // NOI18N
        jLabelEmployeeno.setForeground(new java.awt.Color(255, 255, 255));
        jLabelEmployeeno.setText("EMPLOYEE NO.");

        jLabelSSSno.setFont(new java.awt.Font("Segoe UI Semibold", 1, 14)); // NOI18N
        jLabelSSSno.setForeground(new java.awt.Color(255, 255, 255));
        jLabelSSSno.setText("SSS NO.");

        jLabelPhilhealthno.setFont(new java.awt.Font("Segoe UI Semibold", 1, 14)); // NOI18N
        jLabelPhilhealthno.setForeground(new java.awt.Color(255, 255, 255));
        jLabelPhilhealthno.setText("PHILHEALTH NO.");

        jLabelTINno.setFont(new java.awt.Font("Segoe UI Semibold", 1, 14)); // NOI18N
        jLabelTINno.setForeground(new java.awt.Color(255, 255, 255));
        jLabelTINno.setText("TIN NO.");

        jLabelPagibigno.setFont(new java.awt.Font("Segoe UI Semibold", 1, 14)); // NOI18N
        jLabelPagibigno.setForeground(new java.awt.Color(255, 255, 255));
        jLabelPagibigno.setText("PAGIBIG NO.");

        jTextFieldEmployeeno.setEditable(false);
        jTextFieldEmployeeno.setBackground(new java.awt.Color(0, 0, 0));
        jTextFieldEmployeeno.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jTextFieldEmployeeno.setForeground(new java.awt.Color(255, 255, 255));

        jTextFieldLastname.setEditable(false);
        jTextFieldLastname.setBackground(new java.awt.Color(0, 0, 0));
        jTextFieldLastname.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jTextFieldLastname.setForeground(new java.awt.Color(255, 255, 255));

        jTextFieldFirstname.setEditable(false);
        jTextFieldFirstname.setBackground(new java.awt.Color(0, 0, 0));
        jTextFieldFirstname.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jTextFieldFirstname.setForeground(new java.awt.Color(255, 255, 255));

        jTextFieldPhilhealthno.setEditable(false);
        jTextFieldPhilhealthno.setBackground(new java.awt.Color(0, 0, 0));
        jTextFieldPhilhealthno.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jTextFieldPhilhealthno.setForeground(new java.awt.Color(255, 255, 255));

        jTextFieldSssno.setEditable(false);
        jTextFieldSssno.setBackground(new java.awt.Color(0, 0, 0));
        jTextFieldSssno.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jTextFieldSssno.setForeground(new java.awt.Color(255, 255, 255));

        jTextFieldPagibigno.setEditable(false);
        jTextFieldPagibigno.setBackground(new java.awt.Color(0, 0, 0));
        jTextFieldPagibigno.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jTextFieldPagibigno.setForeground(new java.awt.Color(255, 255, 255));

        jTextFieldTinno.setEditable(false);
        jTextFieldTinno.setBackground(new java.awt.Color(0, 0, 0));
        jTextFieldTinno.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jTextFieldTinno.setForeground(new java.awt.Color(255, 255, 255));

        jLabelBasicsalary.setFont(new java.awt.Font("Segoe UI Semibold", 1, 14)); // NOI18N
        jLabelBasicsalary.setForeground(new java.awt.Color(255, 255, 255));
        jLabelBasicsalary.setText("BASIC SALARY");

        jTextFieldBasicsalary.setEditable(false);
        jTextFieldBasicsalary.setBackground(new java.awt.Color(0, 0, 0));
        jTextFieldBasicsalary.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jTextFieldBasicsalary.setForeground(new java.awt.Color(255, 255, 255));

        jTextFieldTotaldeductions.setEditable(false);
        jTextFieldTotaldeductions.setBackground(new java.awt.Color(0, 0, 0));
        jTextFieldTotaldeductions.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jTextFieldTotaldeductions.setForeground(new java.awt.Color(255, 255, 255));

        jTextFieldNetpay.setEditable(false);
        jTextFieldNetpay.setBackground(new java.awt.Color(0, 0, 0));
        jTextFieldNetpay.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jTextFieldNetpay.setForeground(new java.awt.Color(255, 255, 255));

        jLabelTotalallowances.setBackground(new java.awt.Color(0, 0, 0));
        jLabelTotalallowances.setFont(new java.awt.Font("Segoe UI Semibold", 1, 14)); // NOI18N
        jLabelTotalallowances.setForeground(new java.awt.Color(255, 255, 255));
        jLabelTotalallowances.setText("TOTAL ALLOWANCES");

        jButtonCompute.setBackground(new java.awt.Color(0, 0, 51));
        jButtonCompute.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jButtonCompute.setForeground(new java.awt.Color(255, 255, 255));
        jButtonCompute.setText("Compute");
        jButtonCompute.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonComputeActionPerformed(evt);
            }
        });

        jLabelTotaldeductions.setFont(new java.awt.Font("Segoe UI Semibold", 1, 14)); // NOI18N
        jLabelTotaldeductions.setForeground(new java.awt.Color(255, 255, 255));
        jLabelTotaldeductions.setText("TOTAL DEDUCTIONS");

        jLabelNetpay.setFont(new java.awt.Font("Segoe UI Semibold", 1, 14)); // NOI18N
        jLabelNetpay.setForeground(new java.awt.Color(255, 255, 255));
        jLabelNetpay.setText("NET PAY");

        jTextFieldTotalallowances.setEditable(false);
        jTextFieldTotalallowances.setBackground(new java.awt.Color(0, 0, 0));
        jTextFieldTotalallowances.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jTextFieldTotalallowances.setForeground(new java.awt.Color(255, 255, 255));

        jLabelHourlyrate.setFont(new java.awt.Font("Segoe UI Semibold", 1, 14)); // NOI18N
        jLabelHourlyrate.setForeground(new java.awt.Color(255, 255, 255));
        jLabelHourlyrate.setText("HOURLY RATE");

        jTextFieldHourlyrate.setEditable(false);
        jTextFieldHourlyrate.setBackground(new java.awt.Color(0, 0, 0));
        jTextFieldHourlyrate.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jTextFieldHourlyrate.setForeground(new java.awt.Color(255, 255, 255));

        jLabelPayslipofthemonth.setFont(new java.awt.Font("Segoe UI Semibold", 1, 14)); // NOI18N
        jLabelPayslipofthemonth.setForeground(new java.awt.Color(255, 255, 255));
        jLabelPayslipofthemonth.setText("PAYSLIP OF THE MONTH");

        jTextFieldPayslipofthemonth.setEditable(false);
        jTextFieldPayslipofthemonth.setBackground(new java.awt.Color(0, 0, 0));
        jTextFieldPayslipofthemonth.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jTextFieldPayslipofthemonth.setForeground(new java.awt.Color(255, 255, 255));

        jLabelGrosspay.setFont(new java.awt.Font("Segoe UI Semibold", 1, 14)); // NOI18N
        jLabelGrosspay.setForeground(new java.awt.Color(255, 255, 255));
        jLabelGrosspay.setText("GROSS PAY");

        jTextFieldGrosspay.setEditable(false);
        jTextFieldGrosspay.setBackground(new java.awt.Color(0, 0, 0));
        jTextFieldGrosspay.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jTextFieldGrosspay.setForeground(new java.awt.Color(255, 255, 255));

        jButtonEmployeelist.setBackground(new java.awt.Color(0, 0, 51));
        jButtonEmployeelist.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jButtonEmployeelist.setForeground(new java.awt.Color(255, 255, 255));
        jButtonEmployeelist.setText("Employee List");
        jButtonEmployeelist.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonEmployeelistActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelDetailsLayout = new javax.swing.GroupLayout(jPanelDetails);
        jPanelDetails.setLayout(jPanelDetailsLayout);
        jPanelDetailsLayout.setHorizontalGroup(
            jPanelDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelDetailsLayout.createSequentialGroup()
                .addGroup(jPanelDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelDetailsLayout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addGroup(jPanelDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelTINno)
                            .addComponent(jLabelPagibigno)
                            .addComponent(jLabelPhilhealthno)))
                    .addGroup(jPanelDetailsLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanelDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelSSSno, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabelFirstname, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabelLastname, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanelDetailsLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabelEmployeeno)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanelDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jTextFieldPagibigno, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE)
                    .addComponent(jTextFieldTinno, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextFieldPhilhealthno, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextFieldSssno, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextFieldFirstname, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextFieldLastname, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextFieldEmployeeno, javax.swing.GroupLayout.Alignment.LEADING))
                .addGap(18, 40, Short.MAX_VALUE)
                .addGroup(jPanelDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelDetailsLayout.createSequentialGroup()
                        .addGroup(jPanelDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelDetailsLayout.createSequentialGroup()
                                    .addComponent(jLabelNetpay, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(73, 73, 73))
                                .addComponent(jLabelTotalallowances, javax.swing.GroupLayout.Alignment.TRAILING))
                            .addComponent(jLabelBasicsalary)
                            .addComponent(jLabelHourlyrate)
                            .addComponent(jLabelTotaldeductions, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabelGrosspay, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(37, 37, 37)
                        .addGroup(jPanelDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jTextFieldHourlyrate, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE)
                            .addComponent(jTextFieldNetpay, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextFieldTotaldeductions, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextFieldTotalallowances, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextFieldBasicsalary, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextFieldGrosspay, javax.swing.GroupLayout.Alignment.LEADING)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelDetailsLayout.createSequentialGroup()
                        .addComponent(jLabelPayslipofthemonth)
                        .addGap(18, 18, 18)
                        .addComponent(jTextFieldPayslipofthemonth, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 38, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelDetailsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButtonEmployeelist)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButtonCompute)
                .addGap(24, 24, 24))
        );
        jPanelDetailsLayout.setVerticalGroup(
            jPanelDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelDetailsLayout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addGroup(jPanelDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelEmployeeno)
                    .addComponent(jTextFieldEmployeeno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelBasicsalary)
                    .addComponent(jTextFieldBasicsalary, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanelDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldLastname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelLastname, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelTotalallowances)
                    .addComponent(jTextFieldTotalallowances, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanelDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelDetailsLayout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addGroup(jPanelDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabelFirstname)
                            .addComponent(jTextFieldFirstname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabelGrosspay)))
                    .addGroup(jPanelDetailsLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jTextFieldGrosspay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(jPanelDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelDetailsLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanelDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabelSSSno)
                            .addComponent(jTextFieldSssno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabelTotaldeductions)
                            .addComponent(jTextFieldTotaldeductions, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanelDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabelPhilhealthno)
                            .addComponent(jTextFieldPhilhealthno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabelNetpay)
                            .addComponent(jTextFieldNetpay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanelDetailsLayout.createSequentialGroup()
                        .addGap(109, 109, 109)
                        .addGroup(jPanelDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabelTINno)
                            .addComponent(jTextFieldTinno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabelHourlyrate)
                            .addComponent(jTextFieldHourlyrate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanelDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabelPagibigno)
                            .addComponent(jTextFieldPagibigno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabelPayslipofthemonth)
                            .addComponent(jTextFieldPayslipofthemonth, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 86, Short.MAX_VALUE)
                        .addGroup(jPanelDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButtonCompute)
                            .addComponent(jButtonEmployeelist))
                        .addGap(19, 19, 19))))
        );

        jLabelTitle.setFont(new java.awt.Font("Segoe UI Semibold", 0, 20)); // NOI18N
        jLabelTitle.setForeground(new java.awt.Color(204, 0, 0));
        jLabelTitle.setIcon(new javax.swing.ImageIcon(getClass().getResource("/logos9/empdes3.png"))); // NOI18N

        jLabelDate.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jLabelDate.setForeground(new java.awt.Color(255, 255, 255));
        jLabelDate.setText("Wednesday, December 25, 2012");

        jLabelSelectmonth.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabelSelectmonth.setForeground(new java.awt.Color(255, 255, 255));
        jLabelSelectmonth.setText("Select Month");

        jComboBoxMonths.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jComboBoxMonths.setForeground(new java.awt.Color(0, 0, 51));

        jPanelDateandTime.setBackground(new java.awt.Color(0, 0, 51));

        jLabelTime.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jLabelTime.setForeground(new java.awt.Color(255, 255, 255));
        jLabelTime.setText("12:12:12 AM");

        jLabelGMT.setForeground(new java.awt.Color(255, 255, 255));
        jLabelGMT.setText("GMT+8 PH Time");

        javax.swing.GroupLayout jPanelDateandTimeLayout = new javax.swing.GroupLayout(jPanelDateandTime);
        jPanelDateandTime.setLayout(jPanelDateandTimeLayout);
        jPanelDateandTimeLayout.setHorizontalGroup(
            jPanelDateandTimeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelDateandTimeLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanelDateandTimeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelDateandTimeLayout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jLabelTime))
                    .addComponent(jLabelGMT))
                .addGap(51, 51, 51))
        );
        jPanelDateandTimeLayout.setVerticalGroup(
            jPanelDateandTimeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelDateandTimeLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelGMT, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabelTime)
                .addContainerGap(17, Short.MAX_VALUE))
        );

        jLabelEmployeesectionTitle.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabelEmployeesectionTitle.setForeground(new java.awt.Color(255, 255, 255));
        jLabelEmployeesectionTitle.setText("EMPLOYEE SECTION");

        javax.swing.GroupLayout jPanelEmployeedetailsmainLayout = new javax.swing.GroupLayout(jPanelEmployeedetailsmain);
        jPanelEmployeedetailsmain.setLayout(jPanelEmployeedetailsmainLayout);
        jPanelEmployeedetailsmainLayout.setHorizontalGroup(
            jPanelEmployeedetailsmainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelEmployeedetailsmainLayout.createSequentialGroup()
                .addGroup(jPanelEmployeedetailsmainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanelEmployeedetailsmainLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabelTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanelEmployeedetailsmainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelEmployeedetailsmainLayout.createSequentialGroup()
                                .addGap(115, 115, 115)
                                .addComponent(jLabelEmployeesectionTitle))
                            .addGroup(jPanelEmployeedetailsmainLayout.createSequentialGroup()
                                .addGap(32, 32, 32)
                                .addComponent(jLabelSelectmonth, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jComboBoxMonths, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabelDate, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanelDateandTime, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanelEmployeedetailsmainLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jPanelDetails, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanelEmployeedetailsmainLayout.setVerticalGroup(
            jPanelEmployeedetailsmainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelEmployeedetailsmainLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelEmployeedetailsmainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelEmployeedetailsmainLayout.createSequentialGroup()
                        .addGap(0, 12, Short.MAX_VALUE)
                        .addComponent(jPanelDateandTime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanelEmployeedetailsmainLayout.createSequentialGroup()
                        .addGroup(jPanelEmployeedetailsmainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanelEmployeedetailsmainLayout.createSequentialGroup()
                                .addGap(9, 9, 9)
                                .addComponent(jLabelEmployeesectionTitle)
                                .addGap(20, 20, 20)
                                .addGroup(jPanelEmployeedetailsmainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jComboBoxMonths, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanelEmployeedetailsmainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabelSelectmonth)
                                        .addComponent(jLabelDate)))))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addComponent(jPanelDetails, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(23, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelEmployeedetailsmain, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelEmployeedetailsmain, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonComputeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonComputeActionPerformed
        // TODO add your handling code here:
        String selectedMonth = (String) jComboBoxMonths.getSelectedItem();
        int year = YearMonth.now().getYear();
        String employeeNumberText = jTextFieldEmployeeno.getText();
        String employeeNumber = employeeNumberText;
        // Get employee details from CSV
        CSVReader reader = new CSVReader("src\\motorph9\\EmployeeDetails.csv");
        String[] employeeInfo = reader.getEmployeeInfo(employeeNumber);
        if (employeeInfo != null) {
            try {
                double basicSalary = Double.parseDouble(employeeInfo[15]);

                
                double riceSubsidy = Double.parseDouble(employeeInfo[16]);
                double phoneAllowance = Double.parseDouble(employeeInfo[17]);
                double clothingAllowance = Double.parseDouble(employeeInfo[18]);
                
                
                double totalAllowances = riceSubsidy + phoneAllowance + clothingAllowance;
                
                double grossSalary = basicSalary + totalAllowances;

                jTextFieldBasicsalary.setText(String.valueOf(basicSalary));
                jTextFieldTotalallowances.setText(String.valueOf(totalAllowances));
                jTextFieldGrosspay.setText(String.valueOf(grossSalary));
                
                // Calculate deductions
                double pagibigDeduction = calculatePagibigDeduction();
                double sssDeduction = calculateSSSDeduction(basicSalary);
                double withholdingTax = calculateWithholdingTax(basicSalary + totalAllowances - pagibigDeduction - sssDeduction);
                double totalDeductions = pagibigDeduction + sssDeduction + withholdingTax;

                double netSalary = basicSalary + totalAllowances - totalDeductions;

                jTextFieldTotaldeductions.setText(String.valueOf(totalDeductions));
                jTextFieldNetpay.setText(String.valueOf(netSalary));
                
                double hourlyRate = Double.parseDouble(employeeInfo[20]);
                
                jTextFieldHourlyrate.setText(String.valueOf(hourlyRate));
                
                int weekdaysInMonth = getWeekdaysInMonth(selectedMonth, year);

                double payoftheMonth = hourlyRate * weekdaysInMonth * 8 - totalDeductions; // Assuming 8 hours per day
                
                jTextFieldPayslipofthemonth.setText(String.valueOf(payoftheMonth));

                // Show the payslip for the selected month
                String payslip = String.format(
                        "Payslip for %s\n\nEmployee Number: %s\nBasic Salary: %.2f\nTotal Allowances: %.2f\nGross Salary: %.2f\nTotal Deductions: %.2f\nNet Salary: %.2f\n\nHourly Rate: %.2f\nWorking Days in Month: %d\nPay of the Month: %.2f",
                        selectedMonth, employeeNumberText, basicSalary, totalAllowances, grossSalary, totalDeductions, netSalary, hourlyRate, weekdaysInMonth, payoftheMonth);

                JOptionPane.showMessageDialog(null, payslip, "Payslip", JOptionPane.INFORMATION_MESSAGE);

            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Error parsing employee info", "Error", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(null, "Employee not found", "Error", JOptionPane.ERROR_MESSAGE);
        }

    }//GEN-LAST:event_jButtonComputeActionPerformed

    private void jButtonEmployeelistActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEmployeelistActionPerformed
        // TODO add your handling code here:
        EmployeeList newClassInstance = new EmployeeList(dashboard);
                 newClassInstance.setVisible(true);
                
                dispose();

    }//GEN-LAST:event_jButtonEmployeelistActionPerformed

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
            java.util.logging.Logger.getLogger(EmployeeRecords.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(EmployeeRecords.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(EmployeeRecords.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(EmployeeRecords.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                //new EmployeeRecords().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonCompute;
    private javax.swing.JButton jButtonEmployeelist;
    private javax.swing.JComboBox<String> jComboBoxMonths;
    private javax.swing.JLabel jLabelBasicsalary;
    private javax.swing.JLabel jLabelDate;
    private javax.swing.JLabel jLabelEmployeeno;
    private javax.swing.JLabel jLabelEmployeesectionTitle;
    private javax.swing.JLabel jLabelFirstname;
    private javax.swing.JLabel jLabelGMT;
    private javax.swing.JLabel jLabelGrosspay;
    private javax.swing.JLabel jLabelHourlyrate;
    private javax.swing.JLabel jLabelLastname;
    private javax.swing.JLabel jLabelNetpay;
    private javax.swing.JLabel jLabelPagibigno;
    private javax.swing.JLabel jLabelPayslipofthemonth;
    private javax.swing.JLabel jLabelPhilhealthno;
    private javax.swing.JLabel jLabelSSSno;
    private javax.swing.JLabel jLabelSelectmonth;
    private javax.swing.JLabel jLabelTINno;
    private javax.swing.JLabel jLabelTime;
    private javax.swing.JLabel jLabelTitle;
    private javax.swing.JLabel jLabelTotalallowances;
    private javax.swing.JLabel jLabelTotaldeductions;
    private javax.swing.JPanel jPanelDateandTime;
    private javax.swing.JPanel jPanelDetails;
    private javax.swing.JPanel jPanelEmployeedetailsmain;
    private javax.swing.JTextField jTextFieldBasicsalary;
    private javax.swing.JTextField jTextFieldEmployeeno;
    private javax.swing.JTextField jTextFieldFirstname;
    private javax.swing.JTextField jTextFieldGrosspay;
    private javax.swing.JTextField jTextFieldHourlyrate;
    private javax.swing.JTextField jTextFieldLastname;
    private javax.swing.JTextField jTextFieldNetpay;
    private javax.swing.JTextField jTextFieldPagibigno;
    private javax.swing.JTextField jTextFieldPayslipofthemonth;
    private javax.swing.JTextField jTextFieldPhilhealthno;
    private javax.swing.JTextField jTextFieldSssno;
    private javax.swing.JTextField jTextFieldTinno;
    private javax.swing.JTextField jTextFieldTotalallowances;
    private javax.swing.JTextField jTextFieldTotaldeductions;
    // End of variables declaration//GEN-END:variables
}
