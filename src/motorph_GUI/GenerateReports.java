package motorph_GUI;

import data_reader9.AllowanceDetailsReader;
import data_reader9.SalaryDetailsReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import motorph9.EmployeeUser;
import motorph9.EmployeeUserDataManager;
import payroll9.PayrollCalculatorService;
import payroll9.PayrollData;
import java.util.HashMap;
import java.util.Map;
import payroll9.Salary;
import payroll9.Allowance;
import payroll9.Deductions;

/**
 *
 * @author Four Lugtu
 */
public class GenerateReports extends javax.swing.JFrame {
    private Timer timer;
    private PayrollCalculatorService payrollCalculator = new PayrollCalculatorService();

    /**
     * Creates new form GenerateReports
     */
    public GenerateReports() {
        initComponents();
        setLocationRelativeTo(null); // Center the window
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        startClock();
    }
    
    private PayrollData calculatePayrollData(String employeeNumber) {
        PayrollData data = new PayrollData();
        EmployeeUserDataManager employeeDataManager = new EmployeeUserDataManager();
        EmployeeUser employee = employeeDataManager.getEmployee(employeeNumber);

        if(employee != null) {
            // Set employee basic info
            data.setEmployeeNumber(employee.getEmployeeId());
            data.setFullName(employee.getFirstName() + " " + employee.getLastName());
            data.setSssNumber(employee.getSSS());
            data.setPhilHealthNumber(employee.getPhilHealth());
            data.setTinNumber(employee.getTIN());
            data.setPagibigNumber(employee.getPagibig());

            // Get the current month and year selected
            String month = (String) jComboBoxSelectMonth.getSelectedItem();
            String year = (String) jComboBoxSelectYear.getSelectedItem();

            // Convert to dates
            LocalDateTime startDate = convertMonthYearToStartDate(month, year);
            LocalDateTime endDate = convertMonthYearToEndDate(month, year);

            try {
                // Get all the necessary calculations by reusing the existing code
                // We'll need to extract these values from the calculateNetSalary method

                // Call a new method to get all the payroll details
                Map<String, Double> payrollDetails = getPayrollDetails(employeeNumber, startDate, endDate);

                // Set these values in the PayrollData object
                data.setGrossSalary(payrollDetails.get("grossSalary"));
                data.setTotalAllowances(payrollDetails.get("totalAllowances"));
                data.setTotalDeductions(payrollDetails.get("totalDeductions"));

                // Note: Net salary is set separately in the compute action
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return data;
    }

    // New method to calculate and return all payroll details
    private Map<String, Double> getPayrollDetails(String employeeId, LocalDateTime startDate, LocalDateTime endDate) {
        Map<String, Double> details = new HashMap<>();

        try {
            AllowanceDetailsReader allowanceReader = new AllowanceDetailsReader("src/data9/Allowance.csv");
            SalaryDetailsReader salaryReader = new SalaryDetailsReader("src/data9/Salary.csv");

            // Read salary data once
            Map<String, Salary> salaryMap = salaryReader.getAllSalaries();
            Salary salary = salaryMap.get(employeeId);

            // Read allowance data once
            Map<String, Allowance> allowanceMap = allowanceReader.getAllAllowances();
            Allowance allowance = allowanceMap.get(employeeId);

            if (salary == null || allowance == null) {
                System.out.println("Error: Salary or allowance data not found for employee " + employeeId);
                return details; // Return empty map
            }

            double basicSalary = salary.getBasicSalary();
            double riceSubsidy = allowance.getRiceSubsidy();
            double phoneAllowance = allowance.getPhoneAllowance();
            double clothingAllowance = allowance.getClothingAllowance();

            double totalAllowances = riceSubsidy + phoneAllowance + clothingAllowance;
            double grossSalary = basicSalary + totalAllowances;

            double pagibigDeduction = Deductions.calculatePagibigDeduction();
            double philHealthDeduction = Deductions.calculatePhilHealthDeduction(grossSalary);
            double sssDeduction = Deductions.calculateSSSDeduction(grossSalary);
            double withholdingTax = Deductions.calculateWithholdingTax(grossSalary);
            double totalDeductions = pagibigDeduction + philHealthDeduction + sssDeduction + withholdingTax;

            // Store all values in the map
            details.put("grossSalary", grossSalary);
            details.put("totalAllowances", totalAllowances);
            details.put("totalDeductions", totalDeductions);
            details.put("netSalary", grossSalary - totalDeductions);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return details;
    }
    
    private void startClock() {
        timer = new Timer(1000, e -> updateTime());
        timer.start();
    }
    
    private void updateTime() {
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm:ss a");
        jLabelTime.setText(timeFormat.format(new Date()));
    }
    
    
    public void setEmployeeDetails(PayrollData payrollData) { // Correct method
        jTextFieldEmployeeNumber.setText(payrollData.getEmployeeNumber());
        jTextFieldName.setText(payrollData.getFullName());
        jTextFieldSSSNumber.setText(payrollData.getSssNumber());
        jTextFieldPhilHealthNumber.setText(payrollData.getPhilHealthNumber());
        jTextFieldTIN.setText(payrollData.getTinNumber());
        jTextFieldPAGIBIGNumber.setText(payrollData.getPagibigNumber());
        jTextFieldTotalAllowance.setText(String.valueOf(payrollData.getTotalAllowances()));
        jTextFieldTotalDeductions.setText(String.valueOf(payrollData.getTotalDeductions()));
        jTextFieldGrossSalary.setText(String.valueOf(payrollData.getGrossSalary()));
        jTextFieldNetSalary.setText(String.valueOf(payrollData.getNetSalary()));
    }

    public void clearFields() {
        jTextFieldEmployeeNumber.setText("");
        jTextFieldName.setText("");
        jTextFieldSSSNumber.setText("");
        jTextFieldPhilHealthNumber.setText("");
        jTextFieldTIN.setText("");
        jTextFieldPAGIBIGNumber.setText("");
        jTextFieldTotalAllowance.setText("");
        jTextFieldTotalDeductions.setText("");
        jTextFieldGrossSalary.setText("");
        jTextFieldNetSalary.setText("");
    }
    
     private LocalDateTime convertMonthYearToStartDate(String month, String year) {
        // Implement logic to convert month and year to LocalDateTime (start date)
        int monthValue = getMonthNumber(month);
        int yearValue = Integer.parseInt(year.trim()); // Trim year string
        return LocalDateTime.of(yearValue, monthValue, 1, 0, 0);
    }

    private LocalDateTime convertMonthYearToEndDate(String month, String year) {
        // Implement logic to convert month and year to LocalDateTime (end date)
        int monthValue = getMonthNumber(month);
        int yearValue = Integer.parseInt(year.trim()); // Trim year string
        //get the last day of the month
        java.time.YearMonth yearMonthObject = java.time.YearMonth.of(yearValue, monthValue);
        int daysInMonth = yearMonthObject.lengthOfMonth();
        return LocalDateTime.of(yearValue, monthValue, daysInMonth, 23, 59, 59);
    }
    
    private int getMonthNumber(String month) {
        //Implement logic to convert month string to month number.
        return switch (month) {
            case "January" -> 1;
            case "February" -> 2;
            case "March" -> 3;
            case "April" -> 4;
            case "May" -> 5;
            case "June" -> 6;
            case "July" -> 7;
            case "August" -> 8;
            case "September" -> 9;
            case "October" -> 10;
            case "November" -> 11;
            case "December" -> 12;
            default -> 0;
        };
    }
    
    private void writePayrollDataToCSV(PayrollData data, String month, String year) {
        String filePath = "src/data9/salarylogs.csv"; // Ensure this is the correct path to your file
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(filePath, true)); // Open in append mode

            // Format the data as a comma-separated string
            String csvRow = data.getEmployeeNumber() + "," +
                           month + "," +
                           year + "," +
                           data.getGrossSalary() + "," +
                           data.getNetSalary();

            writer.write(csvRow);
            writer.newLine(); // Add a new line
            System.out.println("Payroll data written to CSV for employee " + data.getEmployeeNumber());

        } catch (java.io.IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error writing to CSV file: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if (writer != null) {
                    writer.close(); // Close the writer in a finally block
                }
            } catch (java.io.IOException e) {
                e.printStackTrace();
                // Handle error during closing (optional, but good practice)
            }
        }
    }
    
    private boolean isPayrollAlreadyLogged(String employeeNumber, String month, String year) {
        String filePath = "src/data9/salarylogs.csv";
        java.io.BufferedReader reader = null;
        String line = "";
        String delimiter = ",";

        try {
            reader = new java.io.BufferedReader(new java.io.FileReader(filePath));
            reader.readLine(); // Skip the header row

            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(delimiter);
                if (fields.length >= 3 &&
                    fields[0].trim().equals(employeeNumber.trim()) &&
                    fields[1].trim().equals(month.trim()) &&
                    fields[2].trim().equals(year.trim())) {
                    return true; // Record already exists
                }
            }
        } catch (java.io.IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error reading salary logs file.", "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (java.io.IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return false; // Record not found
    }


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanelBackground = new javax.swing.JPanel();
        jLabelPH = new javax.swing.JLabel();
        jLabelMotor = new javax.swing.JLabel();
        Logo = new javax.swing.JLabel();
        jLabelGMT = new javax.swing.JLabel();
        jLabelTime = new javax.swing.JLabel();
        jPanelBackground2 = new javax.swing.JPanel();
        jLabelSelectYear = new javax.swing.JLabel();
        jLabelEmployeeNumber1 = new javax.swing.JLabel();
        jLabelName = new javax.swing.JLabel();
        jLabelSSSNumber = new javax.swing.JLabel();
        jLabelPhilHealthNumber = new javax.swing.JLabel();
        jLabelTIN = new javax.swing.JLabel();
        jLabelPAGIBIGNumber = new javax.swing.JLabel();
        jLabelTotalAllowance = new javax.swing.JLabel();
        jLabelTotalDeductions = new javax.swing.JLabel();
        jLabelSelectMonth = new javax.swing.JLabel();
        jTextFieldNetSalary = new javax.swing.JTextField();
        jLabelGrossSalary = new javax.swing.JLabel();
        jLabelNetSalary = new javax.swing.JLabel();
        jPanelSeparator = new javax.swing.JPanel();
        jButtonCompute = new javax.swing.JButton();
        jTextFieldEmployeeNumber = new javax.swing.JTextField();
        jTextFieldName = new javax.swing.JTextField();
        jTextFieldSSSNumber = new javax.swing.JTextField();
        jTextFieldPhilHealthNumber = new javax.swing.JTextField();
        jTextFieldTIN = new javax.swing.JTextField();
        jTextFieldPAGIBIGNumber = new javax.swing.JTextField();
        jTextFieldTotalAllowance = new javax.swing.JTextField();
        jTextFieldTotalDeductions = new javax.swing.JTextField();
        jTextFieldGrossSalary = new javax.swing.JTextField();
        jComboBoxSelectYear = new javax.swing.JComboBox<>();
        jComboBoxSelectMonth = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanelBackground.setBackground(new java.awt.Color(0, 0, 0));
        jPanelBackground.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabelPH.setFont(new java.awt.Font("Century Gothic", 1, 36)); // NOI18N
        jLabelPH.setForeground(new java.awt.Color(204, 0, 51));
        jLabelPH.setText("PH");
        jPanelBackground.add(jLabelPH, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, -10, 70, 110));

        jLabelMotor.setFont(new java.awt.Font("Century Gothic", 1, 36)); // NOI18N
        jLabelMotor.setForeground(new java.awt.Color(255, 255, 255));
        jLabelMotor.setText("MOTOR");
        jPanelBackground.add(jLabelMotor, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 0, -1, 90));

        Logo.setBackground(new java.awt.Color(0, 0, 102));
        Logo.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        Logo.setForeground(new java.awt.Color(255, 255, 255));
        Logo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/logos9/LogoMPH_(small1).png"))); // NOI18N
        Logo.setText("Username");
        jPanelBackground.add(Logo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 100, 100));

        jLabelGMT.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabelGMT.setForeground(new java.awt.Color(255, 255, 255));
        jLabelGMT.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelGMT.setText("GMT+8 PH Time");
        jPanelBackground.add(jLabelGMT, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 20, 170, 20));

        jLabelTime.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        jLabelTime.setForeground(new java.awt.Color(255, 255, 255));
        jLabelTime.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelTime.setText("12:12:12 AM");
        jPanelBackground.add(jLabelTime, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 40, 170, 30));

        jPanelBackground2.setBackground(new java.awt.Color(102, 0, 0));
        jPanelBackground2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabelSelectYear.setBackground(new java.awt.Color(255, 255, 255));
        jLabelSelectYear.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        jLabelSelectYear.setForeground(new java.awt.Color(255, 255, 255));
        jLabelSelectYear.setText("Select Year:");
        jPanelBackground2.add(jLabelSelectYear, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 420, -1, -1));

        jLabelEmployeeNumber1.setBackground(new java.awt.Color(255, 255, 255));
        jLabelEmployeeNumber1.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        jLabelEmployeeNumber1.setForeground(new java.awt.Color(255, 255, 255));
        jLabelEmployeeNumber1.setText("Employee Number:");
        jPanelBackground2.add(jLabelEmployeeNumber1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, -1, -1));

        jLabelName.setBackground(new java.awt.Color(255, 255, 255));
        jLabelName.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        jLabelName.setForeground(new java.awt.Color(255, 255, 255));
        jLabelName.setText(" Name:");
        jPanelBackground2.add(jLabelName, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 60, -1, -1));

        jLabelSSSNumber.setBackground(new java.awt.Color(255, 255, 255));
        jLabelSSSNumber.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        jLabelSSSNumber.setForeground(new java.awt.Color(255, 255, 255));
        jLabelSSSNumber.setText(" SSS Number:");
        jPanelBackground2.add(jLabelSSSNumber, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 100, -1, -1));

        jLabelPhilHealthNumber.setBackground(new java.awt.Color(255, 255, 255));
        jLabelPhilHealthNumber.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        jLabelPhilHealthNumber.setForeground(new java.awt.Color(255, 255, 255));
        jLabelPhilHealthNumber.setText("PhilHealth Number:");
        jPanelBackground2.add(jLabelPhilHealthNumber, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 140, -1, -1));

        jLabelTIN.setBackground(new java.awt.Color(255, 255, 255));
        jLabelTIN.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        jLabelTIN.setForeground(new java.awt.Color(255, 255, 255));
        jLabelTIN.setText(" TIN:");
        jPanelBackground2.add(jLabelTIN, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 180, -1, -1));

        jLabelPAGIBIGNumber.setBackground(new java.awt.Color(255, 255, 255));
        jLabelPAGIBIGNumber.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        jLabelPAGIBIGNumber.setForeground(new java.awt.Color(255, 255, 255));
        jLabelPAGIBIGNumber.setText("PAGIBIG Number:");
        jPanelBackground2.add(jLabelPAGIBIGNumber, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 220, -1, -1));

        jLabelTotalAllowance.setBackground(new java.awt.Color(255, 255, 255));
        jLabelTotalAllowance.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        jLabelTotalAllowance.setForeground(new java.awt.Color(255, 255, 255));
        jLabelTotalAllowance.setText(" Total Allowance:");
        jPanelBackground2.add(jLabelTotalAllowance, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 260, -1, -1));

        jLabelTotalDeductions.setBackground(new java.awt.Color(255, 255, 255));
        jLabelTotalDeductions.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        jLabelTotalDeductions.setForeground(new java.awt.Color(255, 255, 255));
        jLabelTotalDeductions.setText("Total Deductions:");
        jPanelBackground2.add(jLabelTotalDeductions, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 300, -1, -1));

        jLabelSelectMonth.setBackground(new java.awt.Color(255, 255, 255));
        jLabelSelectMonth.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        jLabelSelectMonth.setForeground(new java.awt.Color(255, 255, 255));
        jLabelSelectMonth.setText(" Select Month:");
        jPanelBackground2.add(jLabelSelectMonth, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 380, -1, -1));

        jTextFieldNetSalary.setEditable(false);
        jTextFieldNetSalary.setBackground(new java.awt.Color(255, 255, 255));
        jTextFieldNetSalary.setForeground(new java.awt.Color(0, 0, 0));
        jPanelBackground2.add(jTextFieldNetSalary, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 500, 220, 30));

        jLabelGrossSalary.setBackground(new java.awt.Color(255, 255, 255));
        jLabelGrossSalary.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        jLabelGrossSalary.setForeground(new java.awt.Color(255, 255, 255));
        jLabelGrossSalary.setText(" Gross Salary:");
        jPanelBackground2.add(jLabelGrossSalary, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 460, -1, -1));

        jLabelNetSalary.setBackground(new java.awt.Color(255, 255, 255));
        jLabelNetSalary.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        jLabelNetSalary.setForeground(new java.awt.Color(255, 255, 255));
        jLabelNetSalary.setText("Net Salary:");
        jPanelBackground2.add(jLabelNetSalary, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 500, -1, -1));

        jPanelSeparator.setBackground(new java.awt.Color(0, 0, 0));

        javax.swing.GroupLayout jPanelSeparatorLayout = new javax.swing.GroupLayout(jPanelSeparator);
        jPanelSeparator.setLayout(jPanelSeparatorLayout);
        jPanelSeparatorLayout.setHorizontalGroup(
            jPanelSeparatorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 450, Short.MAX_VALUE)
        );
        jPanelSeparatorLayout.setVerticalGroup(
            jPanelSeparatorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );

        jPanelBackground2.add(jPanelSeparator, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 350, 450, 10));

        jButtonCompute.setBackground(new java.awt.Color(254, 0, 51));
        jButtonCompute.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jButtonCompute.setForeground(new java.awt.Color(255, 255, 255));
        jButtonCompute.setText("Compute");
        jButtonCompute.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonComputeActionPerformed(evt);
            }
        });
        jPanelBackground2.add(jButtonCompute, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 550, 190, 40));

        jTextFieldEmployeeNumber.setEditable(false);
        jTextFieldEmployeeNumber.setBackground(new java.awt.Color(255, 255, 255));
        jTextFieldEmployeeNumber.setForeground(new java.awt.Color(0, 0, 0));
        jPanelBackground2.add(jTextFieldEmployeeNumber, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 20, 220, 30));

        jTextFieldName.setEditable(false);
        jTextFieldName.setBackground(new java.awt.Color(255, 255, 255));
        jTextFieldName.setForeground(new java.awt.Color(0, 0, 0));
        jPanelBackground2.add(jTextFieldName, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 60, 220, 30));

        jTextFieldSSSNumber.setEditable(false);
        jTextFieldSSSNumber.setBackground(new java.awt.Color(255, 255, 255));
        jTextFieldSSSNumber.setForeground(new java.awt.Color(0, 0, 0));
        jPanelBackground2.add(jTextFieldSSSNumber, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 100, 220, 30));

        jTextFieldPhilHealthNumber.setEditable(false);
        jTextFieldPhilHealthNumber.setBackground(new java.awt.Color(255, 255, 255));
        jTextFieldPhilHealthNumber.setForeground(new java.awt.Color(0, 0, 0));
        jPanelBackground2.add(jTextFieldPhilHealthNumber, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 140, 220, 30));

        jTextFieldTIN.setEditable(false);
        jTextFieldTIN.setBackground(new java.awt.Color(255, 255, 255));
        jTextFieldTIN.setForeground(new java.awt.Color(0, 0, 0));
        jPanelBackground2.add(jTextFieldTIN, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 180, 220, 30));

        jTextFieldPAGIBIGNumber.setEditable(false);
        jTextFieldPAGIBIGNumber.setBackground(new java.awt.Color(255, 255, 255));
        jTextFieldPAGIBIGNumber.setForeground(new java.awt.Color(0, 0, 0));
        jPanelBackground2.add(jTextFieldPAGIBIGNumber, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 220, 220, 30));

        jTextFieldTotalAllowance.setEditable(false);
        jTextFieldTotalAllowance.setBackground(new java.awt.Color(255, 255, 255));
        jTextFieldTotalAllowance.setForeground(new java.awt.Color(0, 0, 0));
        jPanelBackground2.add(jTextFieldTotalAllowance, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 260, 220, 30));

        jTextFieldTotalDeductions.setEditable(false);
        jTextFieldTotalDeductions.setBackground(new java.awt.Color(255, 255, 255));
        jTextFieldTotalDeductions.setForeground(new java.awt.Color(0, 0, 0));
        jPanelBackground2.add(jTextFieldTotalDeductions, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 300, 220, 30));

        jTextFieldGrossSalary.setEditable(false);
        jTextFieldGrossSalary.setBackground(new java.awt.Color(255, 255, 255));
        jTextFieldGrossSalary.setForeground(new java.awt.Color(0, 0, 0));
        jPanelBackground2.add(jTextFieldGrossSalary, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 460, 220, 30));

        jComboBoxSelectYear.setBackground(new java.awt.Color(255, 255, 255));
        jComboBoxSelectYear.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jComboBoxSelectYear.setForeground(new java.awt.Color(0, 0, 0));
        jComboBoxSelectYear.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Year", "2024 ", "2025" }));
        jPanelBackground2.add(jComboBoxSelectYear, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 420, 220, 30));

        jComboBoxSelectMonth.setBackground(new java.awt.Color(255, 255, 255));
        jComboBoxSelectMonth.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jComboBoxSelectMonth.setForeground(new java.awt.Color(0, 0, 0));
        jComboBoxSelectMonth.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Month", "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December" }));
        jComboBoxSelectMonth.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxSelectMonthActionPerformed(evt);
            }
        });
        jPanelBackground2.add(jComboBoxSelectMonth, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 380, 220, 30));

        jPanelBackground.add(jPanelBackground2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 100, 450, 610));

        getContentPane().add(jPanelBackground, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 450, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jComboBoxSelectMonthActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxSelectMonthActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBoxSelectMonthActionPerformed

    private void jButtonComputeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonComputeActionPerformed
        String employeeNumber = jTextFieldEmployeeNumber.getText();
        String month = (String) jComboBoxSelectMonth.getSelectedItem();
        String year = (String) jComboBoxSelectYear.getSelectedItem();

        // Validation
        if ("Year".equals(year) || year == null || year.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please select a valid year.", "Error", JOptionPane.ERROR_MESSAGE);
            return; // Stop execution
        }

        if ("Month".equals(month) || month == null || month.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please select a valid month.", "Error", JOptionPane.ERROR_MESSAGE);
            return; // Stop execution
        }
        
        // Check if payroll is already logged
        if (isPayrollAlreadyLogged(employeeNumber, month, year)) {
            JOptionPane.showMessageDialog(this, "Payroll for " + month + " " + year + " for employee " + employeeNumber + " has already been computed.", "Duplicate Entry", JOptionPane.WARNING_MESSAGE);
            return; // Stop further processing
        }

        // Convert month and year to LocalDateTime (start and end dates)
        LocalDateTime startDate = convertMonthYearToStartDate(month, year);
        LocalDateTime endDate = convertMonthYearToEndDate(month, year);

        try {
            double netSalary = payrollCalculator.calculateNetSalary(employeeNumber, startDate, endDate);
            PayrollData payrollData = calculatePayrollData(employeeNumber);
            //payrollData.setGrossSalary(netSalary);
            payrollData.setNetSalary(netSalary);
            setEmployeeDetails(payrollData);
            
            JOptionPane.showMessageDialog(this, "Payroll data computed successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            
            // Call the method to write to SalaryLogs.csv 
            writePayrollDataToCSV(payrollData, month, year);
            
            this.dispose();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error calculating net salary: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }//GEN-LAST:event_jButtonComputeActionPerformed

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
            java.util.logging.Logger.getLogger(GenerateReports.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GenerateReports.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GenerateReports.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GenerateReports.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GenerateReports().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Logo;
    private javax.swing.JButton jButtonCompute;
    private javax.swing.JComboBox<String> jComboBoxSelectMonth;
    private javax.swing.JComboBox<String> jComboBoxSelectYear;
    private javax.swing.JLabel jLabelEmployeeNumber1;
    private javax.swing.JLabel jLabelGMT;
    private javax.swing.JLabel jLabelGrossSalary;
    private javax.swing.JLabel jLabelMotor;
    private javax.swing.JLabel jLabelName;
    private javax.swing.JLabel jLabelNetSalary;
    private javax.swing.JLabel jLabelPAGIBIGNumber;
    private javax.swing.JLabel jLabelPH;
    private javax.swing.JLabel jLabelPhilHealthNumber;
    private javax.swing.JLabel jLabelSSSNumber;
    private javax.swing.JLabel jLabelSelectMonth;
    private javax.swing.JLabel jLabelSelectYear;
    private javax.swing.JLabel jLabelTIN;
    private javax.swing.JLabel jLabelTime;
    private javax.swing.JLabel jLabelTotalAllowance;
    private javax.swing.JLabel jLabelTotalDeductions;
    private javax.swing.JPanel jPanelBackground;
    private javax.swing.JPanel jPanelBackground2;
    private javax.swing.JPanel jPanelSeparator;
    private javax.swing.JTextField jTextFieldEmployeeNumber;
    private javax.swing.JTextField jTextFieldGrossSalary;
    private javax.swing.JTextField jTextFieldName;
    private javax.swing.JTextField jTextFieldNetSalary;
    private javax.swing.JTextField jTextFieldPAGIBIGNumber;
    private javax.swing.JTextField jTextFieldPhilHealthNumber;
    private javax.swing.JTextField jTextFieldSSSNumber;
    private javax.swing.JTextField jTextFieldTIN;
    private javax.swing.JTextField jTextFieldTotalAllowance;
    private javax.swing.JTextField jTextFieldTotalDeductions;
    // End of variables declaration//GEN-END:variables
}
