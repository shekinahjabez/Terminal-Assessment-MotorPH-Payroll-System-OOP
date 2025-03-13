package motorph_GUI;

import data_reader9.EmployeeDetailsReader;
import data_reader9.LeaveRequestReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import data_reader9.TimeTrackerReader;
import java.time.ZoneId;
import motorph9.EmployeeLeaveTracker;
import motorph9.EmployeeUser;
import motorph9.LeaveProcessor;
import motorph9.LeaveRequest;
import motorph_GUI.Login;
import motorph9.User;
import java.awt.event.ActionListener; 
import java.awt.event.ActionEvent;    
import java.io.BufferedReader;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import javax.swing.Timer;
import payroll9.SalaryDetails;
import motorph9.EmployeeUserDataManager;


/**
 *
 * @author Four Lugtu
 */
public class EmployeeDashboard extends javax.swing.JFrame {
    
    private Timer timer;
    private User loggedInUser; // To store the logged-in user
    private EmployeeDetailsReader employeeDetailsReader; // Use EmployeeDetailsReader
    private EmployeeLeaveTracker leaveTracker; // Use EmployeeLeaveTracker
    private TimeTrackerReader timeTrackerReader;
    public static final LocalDate TODAY = LocalDate.now();
    private LeaveRequest leaveRequest;
    private LeaveProcessor leaveProcessor;
    private EmployeeUserDataManager employeeDataManager = new EmployeeUserDataManager();
    
    /**
     * Creates new form EmployeeDashboards
     */
    public EmployeeDashboard() {
        initComponents();
        this.setResizable(false); // 💡 Prevent resizing
        this.setExtendedState(JFrame.NORMAL);
        setLocationRelativeTo(null); // Center the window
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Define close behavior
        //employeeDetailsReader = new EmployeeDetailsReader(); // Initialize reader
        employeeDetailsReader = new EmployeeDetailsReader("src/data9/Employee.csv", "src/data9/Login.csv");
    }

    public EmployeeDashboard (User user) {
        initComponents(); // Call initComponents() to initialize UI
        this.setResizable(false); // 💡 Prevent resizing
        this.setExtendedState(JFrame.NORMAL);
        setLocationRelativeTo(null); // Center the window
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Define close behavior
        this.loggedInUser = user; // Store the logged-in user
        
        //employeeDetailsReader = new EmployeeDetailsReader(); // Initialize reader
        employeeDetailsReader = new EmployeeDetailsReader("src/data9/Employee.csv", "src/data9/Login.csv");
        timeTrackerReader = new TimeTrackerReader(); // Initialize TimeTrackerReader
        leaveTracker = new EmployeeLeaveTracker(user.getEmployeeId()); //Initialize leave tracker
        leaveProcessor = new LeaveProcessor(leaveTracker); // Properly initialized
        
        startClock();
        displayWelcomeMessage(); // Call method to display welcome message (example)
        loadEmployeeDetails(); // Call method to load and display employee details
        loadAttendanceLogs(null, null);
        loadSalaryInformation();
        initializeLeaveTypeComboBox(); // Populate leave types with balance
        
    }
    
    private void startClock() {
        timer = new Timer(1000, e -> updateTime());
        timer.start();
    }
    
    private void updateTime() {
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm:ss a");
        jLabelTime.setText(timeFormat.format(new Date()));
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
            EmployeeUser employeeDetails = employeeDataManager.getEmployee(loggedInUser.getEmployeeId());
            if (employeeDetails != null) {
                displayEmployeeDetails(employeeDetails); // Call method to populate UI
            } else {
                JOptionPane.showMessageDialog(this, "Employee details not found in data file.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void loadSalaryLogs() {
        DefaultTableModel model = (DefaultTableModel) jTableSalaryLogs.getModel();
        model.setRowCount(0); // Clear existing rows
        
        String csvFile = "src/data9/SalaryLogs.csv"; // Path to your CSV file (adjust if needed)
        String line = "";
        String csvSplitBy = ","; // Delimiter is comma
        boolean isFirstLine = true; // Flag to track the first line
        String loggedInEmployeeId = loggedInUser.getEmployeeId(); // Get logged-in employee ID
        
        List<String[]> salaryLogEntries = new ArrayList<>(); // List to hold salary log data
        
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            while ((line = br.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false; // Skip processing the first line (header row)
                    continue;          // Go to the next line
                }
                
                // use comma as separator
                String[] salaryData = line.split(csvSplitBy);
                
                // Assuming CSV order: Employee No, Month, Year, Gross Salary, Net Month Salary
                if (salaryData.length == 5) {
                    try {
                        String employeeNoFromCSV = salaryData[0].trim();
                        // You might need to parse numbers if they are stored as strings in CSV
                        // For example, if Gross Salary and Net Salary are strings:
                        // double grossSalary = Double.parseDouble(salaryData[3].trim());
                        // double netSalary = Double.parseDouble(salaryData[4].trim());

                        // Add row to table model
                        if (employeeNoFromCSV.equals(loggedInEmployeeId)) {
                            salaryLogEntries.add(salaryData); // Add to list instead of directly to table
                            
                        }                            
                        
                    } catch (NumberFormatException e) {
                        System.err.println("Warning: Could not parse number from CSV line: " + line);
                        // Handle parsing error, maybe skip the row or log it

                        }
                    } else {
                        System.err.println("Warning: Incorrect number of fields in CSV line: " + line);
                        // Handle incorrect line format, maybe skip the row or log it
                    }
                }
            
                // Sort salaryLogEntries in reverse chronological order (latest to oldest)
                Collections.sort(salaryLogEntries, new Comparator<String[]>() {
                    private final Map<String, Integer> monthMap = createMonthMap(); // Month name to number map
                    
                    @Override
                    public int compare(String[] log1, String[] log2) {
                        String year1 = log1[2].trim(); // Year is in column 3 (index 2)
                        String month1 = log1[1].trim(); // Month is in column 2 (index 1)
                        String year2 = log2[2].trim();
                        String month2 = log2[1].trim();
                        
                        System.out.println("Comparing:"); // Debug print: Start comparison
                        System.out.println("  Log 1: Year=" + year1 + ", Month=" + month1); // Debug print: Log 1 details
                        System.out.println("  Log 2: Year=" + year2 + ", Month=" + month2); // Debug print: Log 2 details
                        
                        int yearCompare = year2.compareTo(year1); // Compare years in descending order (latest year first)
                        if (yearCompare != 0) {
                            System.out.println("  Years are different, yearCompare=" + yearCompare); // Debug print: Year compare result
                            return yearCompare; // Years are different, sort by year
                        } else {
                            // Years are the same, sort by month in descending order (latest month first)
                            int month1Num = getMonthNumberFromName(month1, monthMap);
                            int month2Num = getMonthNumberFromName(month2, monthMap);
                            int monthCompare = Integer.compare(month2Num, month1Num);
                            System.out.println("  Years are same, monthCompare=" + monthCompare); // Debug print: Month compare result
                            return monthCompare;
                            //return Integer.compare(month1Num, month2Num); // Compare month numbers in descending order
                        }
                    }
                private Map<String, Integer> createMonthMap() {
                    Map<String, Integer> map = new HashMap<>();
                    map.put("January", 1);   map.put("February", 2);  map.put("March", 3);
                    map.put("April", 4);     map.put("May", 5);     map.put("June", 6);
                    map.put("July", 7);      map.put("August", 8);    map.put("September", 9);
                    map.put("October", 10);  map.put("November", 11); map.put("December", 12);
                    return map;

                }
                
                private int getMonthNumberFromName(String monthName, Map<String, Integer> monthMap) {
                    return monthMap.getOrDefault(monthName, 0); // Default to 0 if month name not found (error case)
                }
            }); 
                
            System.out.println("\n--- Sorted Salary Log Entries (after sorting): ---"); // Debug print: Start of sorted list
            for (String[] logEntry : salaryLogEntries) {
                System.out.println("  Year=" + logEntry[2].trim() + ", Month=" + logEntry[1].trim()); // Debug print: Year and Month of each entry
            }
            System.out.println("--- End of Sorted List ---"); // Debug print: End of sorted list
            
            // Add sorted entries to the table model
            for (String[] salaryData : salaryLogEntries) {
                model.addRow(new Object[]{
                    salaryData[0].trim(),
                    salaryData[1].trim(),
                    salaryData[2].trim(),
                    salaryData[3].trim(),
                    salaryData[4].trim()
                });
            }

        } catch (IOException e) {
            e.printStackTrace(); // Or handle the exception more gracefully (e.g., JOptionPane)
            JOptionPane.showMessageDialog(this, "Error reading Salary Logs CSV file: " + e.getMessage(), "File Error", JOptionPane.ERROR_MESSAGE);
        }
    }   
    


    
    private void loadSalaryInformation() { // Modified to call loadSalaryLogs and keep existing salary details loading        
        loadSalaryLogs(); // Call the new method to load Salary Logs table
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


    private void loadAttendanceLogs(String selectedMonth, String selectedYear) { // ✅ Modified to accept month and year
        if (loggedInUser != null) {
            try {
                List<String[]> logs = TimeTrackerReader.getTimeLogs(loggedInUser.getEmployeeId());
                DefaultTableModel model = (DefaultTableModel) jTableAttendanceLogs.getModel();
                model.setRowCount(0); // Clear existing rows
                
                //System.out.println("loadAttendanceLogs: Logs size from TimeTrackerReader: " + logs.size()); // ✅ Debug: Check logs size
                
                boolean dataFound = false; // Flag to track if any data is found after filtering

                // Define the desired date format for display
                DateTimeFormatter displayFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy"); // ✅ Format: MM/DD/YYYY  <-- UPDATED DATE FORMAT HERE


                for (String[] logData : logs) {
                    //System.out.println("loadAttendanceLogs: LogData from TimeTrackerReader: " + java.util.Arrays.toString(logData)); // ✅ Debug: Print each logData
                    // Assuming your TimeTracker.csv data order is: employeeNum,date,timeIn,timeOut,totalWorkedHrs
                    // Adjust column indices to match your CSV structure

                    String employeeNum = ""; // Added for Employee # column
                    String date = "";
                    String timeIn = "";
                    String timeOut = "";
                    String hoursWorked = "";

                    if (logData.length >= 5) { // Ensure enough columns in each log entry
                        employeeNum = logData[0].trim(); // ✅ Employee # is in column 1 (index 0)
                        date = logData[1].trim();         // Date is in column 2 (index 1)
                        timeIn = logData[2].trim();         // Time In is in column 3 (index 2)
                        timeOut = logData[3].trim();        // Time Out is in column 4 (index 3)
                        hoursWorked = logData[4].trim();   // Hours Worked is in column 5 (index 4)
                    }

                    // ✅ Filtering logic: Check if log entry matches selected month and year
                    if (isLogMatchingFilter(date, selectedMonth, selectedYear)) { // ✅ Call helper method for filtering
                        model.addRow(new Object[]{employeeNum, date, timeIn, timeOut, hoursWorked});
                        dataFound = true; // Set flag to true if at least one row is added
                    }
                }
                
                // ✅ Check if no data was found and show JOptionPane message
                if (!dataFound) { // Check the dataFound flag instead of table row count
                    JOptionPane.showMessageDialog(this, "No attendance logs found for the selected month and year.", "No Data Found", JOptionPane.ERROR_MESSAGE);
                }

            } catch (IOException ex) {
                Logger.getLogger(EmployeeDashboard.class.getName()).log(Level.SEVERE, "Error reading attendance logs", ex);
                JOptionPane.showMessageDialog(this, "Error reading attendance logs from file.", "File Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // ✅ Helper method to check if a log entry matches the filters
    private boolean isLogMatchingFilter(String logDateString, String selectedMonth, String selectedYear) {
        System.out.println("isLogMatchingFilter - Input: logDateString='" + logDateString + "', selectedMonth='" + selectedMonth + "', selectedYear='" + selectedYear + "'"); // ✅ Debug: Input values
        if ((selectedMonth == null || selectedMonth.isEmpty() || selectedMonth.equals("Item 1")) && // Treat "Item 1" as no filter
            (selectedYear == null || selectedYear.isEmpty() || selectedYear.equals("Item 1"))) { // Treat "Item 1" as no filter
            return true; // No filter selected, show all logs
        }

        String logMonth = "";
        String logYear = "";
        
        try {
            
            DateTimeFormatter csvDateFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy"); // ✅ Define input format: MM/DD/YYYY
            LocalDate logDate = LocalDate.parse(logDateString, csvDateFormatter); // ✅ Parse using csvDateFormatter
            logYear = String.valueOf(logDate.getYear());
            logMonth = String.valueOf(logDate.getMonthValue());
            
            System.out.println("isLogMatchingFilter - Parsed Date: " + logDate + ", Year: " + logYear + ", Month (number): " + logMonth); // ✅ Debug: Parsed LocalDate and parts

            
        } catch (java.time.format.DateTimeParseException e) {
            System.err.println("Warning: Unexpected date format in CSV: " + logDateString);
            return false;
        }

        // Assuming date format in CSV is YYYY-MM-DD or similar that can be split by '-'
        //String[] dateParts = logDateString.split("-"); // Split date string to get month and year
        //if (dateParts.length >= 2) {
            //logYear = dateParts[0].trim();       // Year is the first part
            //logMonth = dateParts[1].trim();      // Month is the second part
        //} else {
            // If date format is different, you'll need to adjust date parsing logic here
            //System.err.println("Warning: Unexpected date format in CSV: " + logDateString);
            //return false; // Skip this log entry if date parsing fails
        //}


        boolean monthMatch = true;
        if (selectedMonth != null && !selectedMonth.isEmpty() && !selectedMonth.equals("Item 1")) { // Filter by month if selected
            monthMatch = selectedMonth.equalsIgnoreCase(getMonthNameFromNumber(logMonth)); // Compare month names (case-insensitive)
            System.out.println("isLogMatchingFilter - Month Match: " + monthMatch + ", Selected Month: " + selectedMonth + ", Log Month Name: " + getMonthNameFromNumber(logMonth) + ", Log Month Number: " + logMonth); // ✅ Debug: Month match details
        }

        boolean yearMatch = true;
        if (selectedYear != null && !selectedYear.isEmpty() && !selectedYear.equals("Item 1")) { // Filter by year if selected
            yearMatch = selectedYear.equals(logYear); // Compare years as strings
            System.out.println("isLogMatchingFilter - Year Match: " + yearMatch + ", Selected Year: " + selectedYear + ", Log Year: " + logYear + ", Parsed Log Year: " + logYear); // ✅ Debug: Year match details
        }
        
        boolean finalResult = monthMatch && yearMatch;
        System.out.println("isLogMatchingFilter - Final Result: " + finalResult);
        return finalResult;
        //return monthMatch && yearMatch; // Log entry matches filter if both month and year (if selected) match
    }

    // ✅ Helper method to get month name from month number (String)
    private String getMonthNameFromNumber(String monthNumber) {
        try {
            int month = Integer.parseInt(monthNumber);
            if (month >= 1 && month <= 12) {
                String[] monthNames = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
                return monthNames[month - 1];
            }
        } catch (NumberFormatException e) {
            // Handle parsing error if monthNumber is not a valid number
            System.err.println("Error parsing month number: " + monthNumber);
        }
        return ""; // Return empty string if month number is invalid or parsing fails
    }
    
    private void displayEmployeeDetails(EmployeeUser details) { // Parameter type is now EmployeeUser
        jTextFieldEmployeeID.setText(details.getEmployeeId()); //  Use getEmployeeId()
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
    
    
    /*private void loadSalaryInformation() {
        if (loggedInUser != null && loggedInUser instanceof EmployeeUser) { // Checks for EmployeeUser
            try {
                EmployeeUser employee = (EmployeeUser) loggedInUser;

                // Get salary details from EmployeeUser
                SalaryDetails salaryDetails = employee.getSalaryDetails();

                // Update the UI
                updateSalaryUI(salaryDetails);

            } catch (IOException ex) {
                Logger.getLogger(EmployeeDashboardsss.class.getName()).log(Level.SEVERE, "Error reading salary details", ex);
                JOptionPane.showMessageDialog(this, "Error reading salary details.", "File Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            System.out.println("No logged-in EmployeeUser found!"); // Debugging step
        }
    }*/
    
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
        jPanelHeaders = new javax.swing.JPanel();
        Logo = new javax.swing.JLabel();
        jLabelMotor = new javax.swing.JLabel();
        jLabePH = new javax.swing.JLabel();
        jLabelTime = new javax.swing.JLabel();
        jLabelGMT = new javax.swing.JLabel();
        jLabelGreet = new javax.swing.JLabel();
        jLabelFirstName = new javax.swing.JLabel();
        jButtonLogout = new javax.swing.JButton();
        jPanelMenuBar = new javax.swing.JPanel();
        jButtonRequests = new javax.swing.JButton();
        jButtonEmployeeDetails = new javax.swing.JButton();
        jButtonSalaryInfomation = new javax.swing.JButton();
        jButtonAttendance = new javax.swing.JButton();
        jTabbedPaneEmployee = new javax.swing.JTabbedPane();
        jPanelEmployeeDetails2 = new javax.swing.JPanel();
        jPanelPicture2 = new javax.swing.JPanel();
        jLabel2x2Picture2 = new javax.swing.JLabel();
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
        jPanelRequests = new javax.swing.JPanel();
        jPanelAttendanceLogs = new javax.swing.JPanel();
        jScrollPaneAttendanceLogs = new javax.swing.JScrollPane();
        jTableRequestLogs = new javax.swing.JTable();
        jLabelRequests = new javax.swing.JLabel();
        jLabelCreateRequest = new javax.swing.JLabel();
        jPanelCreateRquest = new javax.swing.JPanel();
        jComboBoxLeaveType = new javax.swing.JComboBox<>();
        jLabelReason = new javax.swing.JLabel();
        jLabelLeaveType = new javax.swing.JLabel();
        jLabelStartDate = new javax.swing.JLabel();
        jLabelEndDate = new javax.swing.JLabel();
        jTextFieldReason = new javax.swing.JTextField();
        jDateChooserStart = new com.toedter.calendar.JDateChooser();
        jDateChooserEnd = new com.toedter.calendar.JDateChooser();
        jLabelAttendanceLogs = new javax.swing.JLabel();
        jButtonSubmitRequest = new javax.swing.JButton();
        jPanelSalaryInformation = new javax.swing.JPanel();
        jPanelSalaryLogs = new javax.swing.JPanel();
        jScrollPaneSalaryLogs = new javax.swing.JScrollPane();
        jTableSalaryLogs = new javax.swing.JTable();
        jLabelSalaryInformation = new javax.swing.JLabel();
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
        jButtonTimeIn = new javax.swing.JButton();
        jButtonTimeOut = new javax.swing.JButton();
        jLabelAttendanceLog = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanelEmployeeDashboard.setBackground(new java.awt.Color(0, 0, 0));
        jPanelEmployeeDashboard.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanelHeaders.setBackground(new java.awt.Color(0, 0, 0));
        jPanelHeaders.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Logo.setBackground(new java.awt.Color(0, 0, 102));
        Logo.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        Logo.setForeground(new java.awt.Color(255, 255, 255));
        Logo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/logos9/LogoMPH_(small1).png"))); // NOI18N
        Logo.setText("Username");
        jPanelHeaders.add(Logo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, -10, 100, 110));

        jLabelMotor.setFont(new java.awt.Font("Century Gothic", 1, 36)); // NOI18N
        jLabelMotor.setForeground(new java.awt.Color(255, 255, 255));
        jLabelMotor.setText("MOTOR");
        jPanelHeaders.add(jLabelMotor, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, -10, -1, 100));

        jLabePH.setFont(new java.awt.Font("Century Gothic", 1, 36)); // NOI18N
        jLabePH.setForeground(new java.awt.Color(204, 0, 51));
        jLabePH.setText("PH");
        jPanelHeaders.add(jLabePH, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, -20, 70, 120));

        jLabelTime.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        jLabelTime.setForeground(new java.awt.Color(255, 255, 255));
        jLabelTime.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelTime.setText("12:12:12 AM");
        jPanelHeaders.add(jLabelTime, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 40, 170, 50));

        jLabelGMT.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabelGMT.setForeground(new java.awt.Color(255, 255, 255));
        jLabelGMT.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelGMT.setText("GMT+8 PH Time");
        jPanelHeaders.add(jLabelGMT, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 20, 170, 40));

        jLabelGreet.setFont(new java.awt.Font("Century Gothic", 1, 36)); // NOI18N
        jLabelGreet.setForeground(new java.awt.Color(255, 255, 255));
        jLabelGreet.setText("Welcome,");
        jPanelHeaders.add(jLabelGreet, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, -10, -1, 100));

        jLabelFirstName.setFont(new java.awt.Font("Century Gothic", 0, 36)); // NOI18N
        jLabelFirstName.setForeground(new java.awt.Color(255, 255, 255));
        jLabelFirstName.setText("FirstName!");
        jPanelHeaders.add(jLabelFirstName, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 30, 260, 100));

        jButtonLogout.setBackground(new java.awt.Color(255, 255, 255));
        jButtonLogout.setFont(new java.awt.Font("Century Gothic", 1, 13)); // NOI18N
        jButtonLogout.setForeground(new java.awt.Color(0, 0, 0));
        jButtonLogout.setText("Logout");
        jButtonLogout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonLogoutActionPerformed(evt);
            }
        });
        jPanelHeaders.add(jButtonLogout, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 90, 90, 30));

        jPanelEmployeeDashboard.add(jPanelHeaders, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1070, 140));

        jPanelMenuBar.setBackground(new java.awt.Color(102, 0, 0));
        jPanelMenuBar.setForeground(new java.awt.Color(51, 0, 0));
        jPanelMenuBar.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButtonRequests.setBackground(new java.awt.Color(204, 0, 51));
        jButtonRequests.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        jButtonRequests.setForeground(new java.awt.Color(255, 255, 255));
        jButtonRequests.setText("Salary Information");
        jButtonRequests.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRequestsActionPerformed(evt);
            }
        });
        jPanelMenuBar.add(jButtonRequests, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 10, 230, 50));

        jButtonEmployeeDetails.setBackground(new java.awt.Color(204, 0, 51));
        jButtonEmployeeDetails.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        jButtonEmployeeDetails.setForeground(new java.awt.Color(255, 255, 255));
        jButtonEmployeeDetails.setText("Employee Details");
        jButtonEmployeeDetails.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonEmployeeDetailsActionPerformed(evt);
            }
        });
        jPanelMenuBar.add(jButtonEmployeeDetails, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 240, 50));

        jButtonSalaryInfomation.setBackground(new java.awt.Color(204, 0, 51));
        jButtonSalaryInfomation.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        jButtonSalaryInfomation.setForeground(new java.awt.Color(255, 255, 255));
        jButtonSalaryInfomation.setText("Requests");
        jButtonSalaryInfomation.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSalaryInfomationActionPerformed(evt);
            }
        });
        jPanelMenuBar.add(jButtonSalaryInfomation, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 10, 230, 50));

        jButtonAttendance.setBackground(new java.awt.Color(204, 0, 51));
        jButtonAttendance.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        jButtonAttendance.setForeground(new java.awt.Color(255, 255, 255));
        jButtonAttendance.setText("Attendance & Tracker");
        jButtonAttendance.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAttendanceActionPerformed(evt);
            }
        });
        jPanelMenuBar.add(jButtonAttendance, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 10, 230, 50));

        jPanelEmployeeDashboard.add(jPanelMenuBar, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 140, 1030, 70));

        jPanelEmployeeDetails2.setBackground(new java.awt.Color(51, 0, 0));
        jPanelEmployeeDetails2.setForeground(new java.awt.Color(255, 255, 255));
        jPanelEmployeeDetails2.setMinimumSize(new java.awt.Dimension(0, 0));
        jPanelEmployeeDetails2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanelPicture2.setBackground(new java.awt.Color(255, 255, 255));
        jPanelPicture2.setBorder(new javax.swing.border.MatteBorder(null));
        jPanelPicture2.setForeground(new java.awt.Color(0, 0, 0));
        jPanelPicture2.setToolTipText("");
        jPanelPicture2.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N

        jLabel2x2Picture2.setBackground(new java.awt.Color(0, 0, 0));
        jLabel2x2Picture2.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
        jLabel2x2Picture2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2x2Picture2.setText("2x2 Picture");

        javax.swing.GroupLayout jPanelPicture2Layout = new javax.swing.GroupLayout(jPanelPicture2);
        jPanelPicture2.setLayout(jPanelPicture2Layout);
        jPanelPicture2Layout.setHorizontalGroup(
            jPanelPicture2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelPicture2Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jLabel2x2Picture2)
                .addContainerGap(25, Short.MAX_VALUE))
        );
        jPanelPicture2Layout.setVerticalGroup(
            jPanelPicture2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelPicture2Layout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addComponent(jLabel2x2Picture2)
                .addContainerGap(46, Short.MAX_VALUE))
        );

        jPanelEmployeeDetails2.add(jPanelPicture2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 60, 120, 110));

        jLabelEmployeeDetails.setBackground(new java.awt.Color(255, 255, 255));
        jLabelEmployeeDetails.setFont(new java.awt.Font("Century Gothic", 1, 25)); // NOI18N
        jLabelEmployeeDetails.setForeground(new java.awt.Color(255, 255, 255));
        jLabelEmployeeDetails.setText("Employee Details");
        jPanelEmployeeDetails2.add(jLabelEmployeeDetails, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, -10, -1, 90));

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

        jTextFieldPosition.setEditable(false);
        jTextFieldPosition.setBackground(new java.awt.Color(255, 255, 255));
        jPanelDetails.add(jTextFieldPosition, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 380, 270, -1));

        jTextFieldEmployeeID.setEditable(false);
        jTextFieldEmployeeID.setBackground(new java.awt.Color(255, 255, 255));
        jPanelDetails.add(jTextFieldEmployeeID, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 20, 270, -1));

        jLabelUsername.setBackground(new java.awt.Color(255, 255, 255));
        jLabelUsername.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabelUsername.setForeground(new java.awt.Color(255, 255, 255));
        jLabelUsername.setText("Username:");
        jPanelDetails.add(jLabelUsername, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 50, -1, -1));

        jTextFieldUsername.setEditable(false);
        jTextFieldUsername.setBackground(new java.awt.Color(255, 255, 255));
        jPanelDetails.add(jTextFieldUsername, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 50, 270, -1));

        jLabelName.setBackground(new java.awt.Color(255, 255, 255));
        jLabelName.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabelName.setForeground(new java.awt.Color(255, 255, 255));
        jLabelName.setText("Name:");
        jPanelDetails.add(jLabelName, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 80, -1, -1));

        jTextFieldName.setEditable(false);
        jTextFieldName.setBackground(new java.awt.Color(255, 255, 255));
        jPanelDetails.add(jTextFieldName, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 80, 270, -1));

        jLabelBirthday.setBackground(new java.awt.Color(255, 255, 255));
        jLabelBirthday.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabelBirthday.setForeground(new java.awt.Color(255, 255, 255));
        jLabelBirthday.setText("Birthday:");
        jPanelDetails.add(jLabelBirthday, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 110, -1, -1));

        jTextFieldBirthday.setEditable(false);
        jTextFieldBirthday.setBackground(new java.awt.Color(255, 255, 255));
        jPanelDetails.add(jTextFieldBirthday, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 110, 270, -1));

        jLabelAddress.setBackground(new java.awt.Color(255, 255, 255));
        jLabelAddress.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabelAddress.setForeground(new java.awt.Color(255, 255, 255));
        jLabelAddress.setText("Address:");
        jPanelDetails.add(jLabelAddress, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 140, -1, -1));

        jTextFieldAddress.setEditable(false);
        jTextFieldAddress.setBackground(new java.awt.Color(255, 255, 255));
        jPanelDetails.add(jTextFieldAddress, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 140, 270, -1));

        jLabelPhoneNumber.setBackground(new java.awt.Color(255, 255, 255));
        jLabelPhoneNumber.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabelPhoneNumber.setForeground(new java.awt.Color(255, 255, 255));
        jLabelPhoneNumber.setText("Phone Number:");
        jPanelDetails.add(jLabelPhoneNumber, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 170, -1, -1));

        jTextFieldPhoneNumber.setEditable(false);
        jTextFieldPhoneNumber.setBackground(new java.awt.Color(255, 255, 255));
        jPanelDetails.add(jTextFieldPhoneNumber, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 170, 270, -1));

        jLabelSSS.setBackground(new java.awt.Color(255, 255, 255));
        jLabelSSS.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabelSSS.setForeground(new java.awt.Color(255, 255, 255));
        jLabelSSS.setText("SSS:");
        jPanelDetails.add(jLabelSSS, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 200, -1, -1));

        jTextFieldSSS.setEditable(false);
        jTextFieldSSS.setBackground(new java.awt.Color(255, 255, 255));
        jPanelDetails.add(jTextFieldSSS, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 200, 270, -1));

        jLabelPhilHealth.setBackground(new java.awt.Color(255, 255, 255));
        jLabelPhilHealth.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabelPhilHealth.setForeground(new java.awt.Color(255, 255, 255));
        jLabelPhilHealth.setText("PhilHealth:");
        jPanelDetails.add(jLabelPhilHealth, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 230, 80, -1));

        jTextFieldPhilHealth.setEditable(false);
        jTextFieldPhilHealth.setBackground(new java.awt.Color(255, 255, 255));
        jPanelDetails.add(jTextFieldPhilHealth, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 230, 270, -1));

        jLabelTIN.setBackground(new java.awt.Color(255, 255, 255));
        jLabelTIN.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabelTIN.setForeground(new java.awt.Color(255, 255, 255));
        jLabelTIN.setText("TIN:");
        jPanelDetails.add(jLabelTIN, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 260, 30, -1));

        jTextFieldTIN.setEditable(false);
        jTextFieldTIN.setBackground(new java.awt.Color(255, 255, 255));
        jPanelDetails.add(jTextFieldTIN, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 260, 270, -1));

        jLabelPagibig.setBackground(new java.awt.Color(255, 255, 255));
        jLabelPagibig.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabelPagibig.setForeground(new java.awt.Color(255, 255, 255));
        jLabelPagibig.setText("PAGIBIG:");
        jPanelDetails.add(jLabelPagibig, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 290, 80, -1));

        jTextFieldPagibig.setEditable(false);
        jTextFieldPagibig.setBackground(new java.awt.Color(255, 255, 255));
        jPanelDetails.add(jTextFieldPagibig, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 290, 270, -1));

        jLabelSupervisor.setBackground(new java.awt.Color(255, 255, 255));
        jLabelSupervisor.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabelSupervisor.setForeground(new java.awt.Color(255, 255, 255));
        jLabelSupervisor.setText("Immediate Supervisor:");
        jPanelDetails.add(jLabelSupervisor, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 320, 170, -1));

        jTextFieldSupervisor.setEditable(false);
        jTextFieldSupervisor.setBackground(new java.awt.Color(255, 255, 255));
        jPanelDetails.add(jTextFieldSupervisor, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 320, 270, -1));

        jLabelStatus.setBackground(new java.awt.Color(255, 255, 255));
        jLabelStatus.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabelStatus.setForeground(new java.awt.Color(255, 255, 255));
        jLabelStatus.setText("Status:");
        jPanelDetails.add(jLabelStatus, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 350, 50, -1));

        jTextFieldStatus.setEditable(false);
        jTextFieldStatus.setBackground(new java.awt.Color(255, 255, 255));
        jPanelDetails.add(jTextFieldStatus, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 350, 270, -1));

        jPanelEmployeeDetails2.add(jPanelDetails, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 60, 500, 420));

        jTabbedPaneEmployee.addTab("Employee Details", jPanelEmployeeDetails2);

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

        jPanelAttendanceLogs.add(jScrollPaneAttendanceLogs, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 660, 370));

        jPanelRequests.add(jPanelAttendanceLogs, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 90, 680, 390));

        jLabelRequests.setBackground(new java.awt.Color(255, 255, 255));
        jLabelRequests.setFont(new java.awt.Font("Century Gothic", 1, 25)); // NOI18N
        jLabelRequests.setForeground(new java.awt.Color(255, 255, 255));
        jLabelRequests.setText("Requests");
        jPanelRequests.add(jLabelRequests, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, -10, -1, 90));

        jLabelCreateRequest.setBackground(new java.awt.Color(255, 255, 255));
        jLabelCreateRequest.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        jLabelCreateRequest.setForeground(new java.awt.Color(255, 255, 255));
        jLabelCreateRequest.setText("Create Request");
        jPanelRequests.add(jLabelCreateRequest, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 50, -1, 80));

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

        jPanelRequests.add(jPanelCreateRquest, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 110, 280, 320));

        jLabelAttendanceLogs.setBackground(new java.awt.Color(255, 255, 255));
        jLabelAttendanceLogs.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        jLabelAttendanceLogs.setForeground(new java.awt.Color(255, 255, 255));
        jLabelAttendanceLogs.setText("Request Logs");
        jPanelRequests.add(jLabelAttendanceLogs, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, -1, 80));

        jButtonSubmitRequest.setBackground(new java.awt.Color(204, 0, 51));
        jButtonSubmitRequest.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jButtonSubmitRequest.setForeground(new java.awt.Color(255, 255, 255));
        jButtonSubmitRequest.setText("Submit");
        jButtonSubmitRequest.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSubmitRequestActionPerformed(evt);
            }
        });
        jPanelRequests.add(jButtonSubmitRequest, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 440, 160, 40));

        jTabbedPaneEmployee.addTab("Requests", jPanelRequests);

        jPanelSalaryInformation.setBackground(new java.awt.Color(51, 0, 0));
        jPanelSalaryInformation.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanelSalaryLogs.setBackground(new java.awt.Color(204, 0, 51));
        jPanelSalaryLogs.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTableSalaryLogs.setBackground(new java.awt.Color(255, 255, 255));
        jTableSalaryLogs.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Employee No.", "Month", "Year", "Gross Salary", "Net Month Salary"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPaneSalaryLogs.setViewportView(jTableSalaryLogs);

        jPanelSalaryLogs.add(jScrollPaneSalaryLogs, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 530, 370));

        jPanelSalaryInformation.add(jPanelSalaryLogs, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 90, 550, 390));

        jLabelSalaryInformation.setBackground(new java.awt.Color(255, 255, 255));
        jLabelSalaryInformation.setFont(new java.awt.Font("Century Gothic", 1, 25)); // NOI18N
        jLabelSalaryInformation.setForeground(new java.awt.Color(255, 255, 255));
        jLabelSalaryInformation.setText("Salary Information");
        jPanelSalaryInformation.add(jLabelSalaryInformation, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, -10, -1, 90));

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

        jPanelSalaryInformation.add(jPanelSalaryCalculations, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 380, 410, 100));

        jLabelSalaryLogs.setBackground(new java.awt.Color(255, 255, 255));
        jLabelSalaryLogs.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        jLabelSalaryLogs.setForeground(new java.awt.Color(255, 255, 255));
        jLabelSalaryLogs.setText("Salary Logs");
        jPanelSalaryInformation.add(jLabelSalaryLogs, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, -1, 80));

        jLabelDeductions.setBackground(new java.awt.Color(255, 255, 255));
        jLabelDeductions.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        jLabelDeductions.setForeground(new java.awt.Color(255, 255, 255));
        jLabelDeductions.setText("Deductions");
        jPanelSalaryInformation.add(jLabelDeductions, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 170, -1, 70));

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

        jPanelSalaryInformation.add(jPanelDeductions, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 220, 200, 130));

        jLabelAllowances.setBackground(new java.awt.Color(255, 255, 255));
        jLabelAllowances.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        jLabelAllowances.setForeground(new java.awt.Color(255, 255, 255));
        jLabelAllowances.setText("Allowances");
        jPanelSalaryInformation.add(jLabelAllowances, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 50, -1, 50));

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

        jPanelSalaryInformation.add(jPanelAllowances, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 90, 200, 100));

        jPanelTotalAllwances.setBackground(new java.awt.Color(204, 0, 51));
        jPanelTotalAllwances.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabelTotalAllowances.setBackground(new java.awt.Color(255, 255, 255));
        jLabelTotalAllowances.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabelTotalAllowances.setForeground(new java.awt.Color(255, 255, 255));
        jLabelTotalAllowances.setText("TOTAL:");
        jPanelTotalAllwances.add(jLabelTotalAllowances, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 20, -1, 50));

        jTextFieldTotalAllowances.setBackground(new java.awt.Color(255, 255, 255));
        jPanelTotalAllwances.add(jTextFieldTotalAllowances, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 30, 80, 30));

        jPanelSalaryInformation.add(jPanelTotalAllwances, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 90, 200, 100));

        jPanelTotalDeductions.setBackground(new java.awt.Color(204, 0, 51));
        jPanelTotalDeductions.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabelTotalDeductions.setBackground(new java.awt.Color(255, 255, 255));
        jLabelTotalDeductions.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabelTotalDeductions.setForeground(new java.awt.Color(255, 255, 255));
        jLabelTotalDeductions.setText("TOTAL:");
        jPanelTotalDeductions.add(jLabelTotalDeductions, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 40, -1, 50));

        jTextFieldTotalDeductions.setBackground(new java.awt.Color(255, 255, 255));
        jPanelTotalDeductions.add(jTextFieldTotalDeductions, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 50, 80, 30));

        jPanelSalaryInformation.add(jPanelTotalDeductions, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 220, 200, 130));

        jLabelSalaryCalculations.setBackground(new java.awt.Color(255, 255, 255));
        jLabelSalaryCalculations.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        jLabelSalaryCalculations.setForeground(new java.awt.Color(255, 255, 255));
        jLabelSalaryCalculations.setText("Salary Calculations");
        jPanelSalaryInformation.add(jLabelSalaryCalculations, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 330, -1, 70));

        jTabbedPaneEmployee.addTab("Salary Information", jPanelSalaryInformation);

        jPanelAttendanceandTracker.setBackground(new java.awt.Color(51, 0, 0));
        jPanelAttendanceandTracker.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabelAttendanceandTracker.setBackground(new java.awt.Color(255, 255, 255));
        jLabelAttendanceandTracker.setFont(new java.awt.Font("Century Gothic", 1, 25)); // NOI18N
        jLabelAttendanceandTracker.setForeground(new java.awt.Color(255, 255, 255));
        jLabelAttendanceandTracker.setText("Attendance & Tracker");
        jPanelAttendanceandTracker.add(jLabelAttendanceandTracker, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, -10, -1, 90));

        jPanelAttendanceAndTracker.setBackground(new java.awt.Color(204, 0, 51));
        jPanelAttendanceAndTracker.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTableAttendanceLogs.setBackground(new java.awt.Color(255, 255, 255));
        jTableAttendanceLogs.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Employee #", "Date", "Time In", "Time Out", "Hours Worked"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPaneAttenedanceandTracker.setViewportView(jTableAttendanceLogs);

        jPanelAttendanceAndTracker.add(jScrollPaneAttenedanceandTracker, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 570, 240));

        jPanelAttendanceandTracker.add(jPanelAttendanceAndTracker, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 80, 610, 260));

        jLabelTimeTracker.setBackground(new java.awt.Color(255, 255, 255));
        jLabelTimeTracker.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        jLabelTimeTracker.setForeground(new java.awt.Color(255, 255, 255));
        jLabelTimeTracker.setText("Time Tracker");
        jPanelAttendanceandTracker.add(jLabelTimeTracker, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 50, -1, 80));

        jLabelSelectYearAttendance.setBackground(new java.awt.Color(255, 255, 255));
        jLabelSelectYearAttendance.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        jLabelSelectYearAttendance.setForeground(new java.awt.Color(255, 255, 255));
        jLabelSelectYearAttendance.setText("Select Year");
        jPanelAttendanceandTracker.add(jLabelSelectYearAttendance, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 320, -1, 70));

        jLabelSelectMonthAttendance.setBackground(new java.awt.Color(255, 255, 255));
        jLabelSelectMonthAttendance.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        jLabelSelectMonthAttendance.setForeground(new java.awt.Color(255, 255, 255));
        jLabelSelectMonthAttendance.setText("Select Month");
        jPanelAttendanceandTracker.add(jLabelSelectMonthAttendance, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 320, -1, 70));

        jPanelSelectYearAttendance.setBackground(new java.awt.Color(204, 0, 51));
        jPanelSelectYearAttendance.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jComboBoxSelectYearAttendance.setBackground(new java.awt.Color(255, 255, 255));
        jComboBoxSelectYearAttendance.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "2025", "2024" }));
        jPanelSelectYearAttendance.add(jComboBoxSelectYearAttendance, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 230, 40));

        jPanelAttendanceandTracker.add(jPanelSelectYearAttendance, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 370, 270, 60));

        jPanelSelectMonthAttendance.setBackground(new java.awt.Color(204, 0, 51));
        jPanelSelectMonthAttendance.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jComboBoxSelectMonthAttendance.setBackground(new java.awt.Color(255, 255, 255));
        jComboBoxSelectMonthAttendance.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December" }));
        jPanelSelectMonthAttendance.add(jComboBoxSelectMonthAttendance, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 230, 40));

        jPanelAttendanceandTracker.add(jPanelSelectMonthAttendance, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 370, 280, 60));

        jButtonAttendanceView.setBackground(new java.awt.Color(204, 0, 51));
        jButtonAttendanceView.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jButtonAttendanceView.setForeground(new java.awt.Color(255, 255, 255));
        jButtonAttendanceView.setText("View");
        jPanelAttendanceandTracker.add(jButtonAttendanceView, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 440, 160, 40));
        jButtonAttendanceView.addActionListener(new ActionListener() { // ✅ Add ActionListener to View button
            //@Override
            public void actionPerformed(ActionEvent e) {
                String selectedMonth = (String) jComboBoxSelectMonthAttendance.getSelectedItem(); // Get selected month
                String selectedYear = (String) jComboBoxSelectYearAttendance.getSelectedItem();   // Get selected year
                loadAttendanceLogs(selectedMonth, selectedYear); // ✅ Call loadAttendanceLogs with month and year
            }
        });

        jPanelTimeTracker.setBackground(new java.awt.Color(102, 0, 0));
        jPanelTimeTracker.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButtonTimeIn.setBackground(new java.awt.Color(204, 0, 51));
        jButtonTimeIn.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        jButtonTimeIn.setForeground(new java.awt.Color(255, 255, 255));
        jButtonTimeIn.setText("TIME IN");
        jButtonTimeIn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonTimeInActionPerformed(evt);
            }
        });
        jPanelTimeTracker.add(jButtonTimeIn, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, 290, 60));

        jButtonTimeOut.setBackground(new java.awt.Color(204, 0, 51));
        jButtonTimeOut.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        jButtonTimeOut.setForeground(new java.awt.Color(255, 255, 255));
        jButtonTimeOut.setText("TIME OUT");
        jButtonTimeOut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonTimeOutActionPerformed(evt);
            }
        });
        jPanelTimeTracker.add(jButtonTimeOut, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 130, 290, 60));

        jPanelAttendanceandTracker.add(jPanelTimeTracker, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 110, 330, 230));

        jLabelAttendanceLog.setBackground(new java.awt.Color(255, 255, 255));
        jLabelAttendanceLog.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        jLabelAttendanceLog.setForeground(new java.awt.Color(255, 255, 255));
        jLabelAttendanceLog.setText("Attendance Logs");
        jPanelAttendanceandTracker.add(jLabelAttendanceLog, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 20, -1, 80));

        jTabbedPaneEmployee.addTab("Attendance & Tracker", jPanelAttendanceandTracker);

        jPanelEmployeeDashboard.add(jTabbedPaneEmployee, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 170, 1030, 530));

        getContentPane().add(jPanelEmployeeDashboard, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1070, 720));

        pack();
    }// </editor-fold>//GEN-END:initComponents
     
    private void jButtonLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonLogoutActionPerformed
        Login newClassInstance = new Login();
        newClassInstance.setVisible(true);

        dispose();
    }//GEN-LAST:event_jButtonLogoutActionPerformed

    private void jButtonAttendanceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAttendanceActionPerformed
        jTabbedPaneEmployee.setSelectedIndex(3);
    }//GEN-LAST:event_jButtonAttendanceActionPerformed

    private void jButtonRequestsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRequestsActionPerformed
        jTabbedPaneEmployee.setSelectedIndex(2);
        loadRequests();
    }//GEN-LAST:event_jButtonRequestsActionPerformed

    private void jButtonSalaryInfomationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSalaryInfomationActionPerformed
        System.out.println("Salary Information Button Clicked!"); // Debugging step
        jTabbedPaneEmployee.setSelectedIndex(1);

        loadSalaryInformation(); // Calls the method to fetch and display salary info
    }//GEN-LAST:event_jButtonSalaryInfomationActionPerformed

    private void jButtonEmployeeDetailsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEmployeeDetailsActionPerformed
        jTabbedPaneEmployee.setSelectedIndex(0);

    }//GEN-LAST:event_jButtonEmployeeDetailsActionPerformed

    private void jButtonSubmitRequestActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSubmitRequestActionPerformed
        String selectedLeave = jComboBoxLeaveType.getSelectedItem().toString();
        String leaveType = selectedLeave.replaceAll("\\s\\(.*\\)", "");

        Date startDateRaw = jDateChooserStart.getDate();
        Date endDateRaw = jDateChooserEnd.getDate();
        String reason = jTextFieldReason.getText().trim(); // ✅ Trim to remove extra spaces

        // Check if reason is empty
        if (reason.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a reason for your leave.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (startDateRaw == null || endDateRaw == null) {
            JOptionPane.showMessageDialog(this, "Please select a valid start and end date.", "Date Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        LocalDate startLocalDate = startDateRaw.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate endLocalDate = endDateRaw.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        // Check for duplicate requests
        List<LeaveRequest> existingRequests = new LeaveRequestReader().getAllLeaveRequests();
        for (LeaveRequest request : existingRequests) {
            if (request.getEmployeeID().equals(loggedInUser.getEmployeeId()) &&
                request.getLeaveType().equals(leaveType) &&
                request.getStartDate().equals(startLocalDate) &&
                request.getEndDate().equals(endLocalDate)) {
                JOptionPane.showMessageDialog(this, "You have already submitted a request for this leave period.", "Duplicate Request", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        try {
            leaveProcessor.processLeaveRequest(loggedInUser.getEmployeeId(), leaveType, startLocalDate, endLocalDate, reason);

            JOptionPane.showMessageDialog(this, "Request submitted successfully!\nUpdated Balances:\nSick Leave: "
                + leaveTracker.getSickLeaveBalance() + "\nVacation Leave: "
                + leaveTracker.getVacationLeaveBalance() + "\nBirthday Leave: "
                + leaveTracker.getBirthdayLeaveBalance(), "Success", JOptionPane.INFORMATION_MESSAGE);

            // Clear fields after submission
            jDateChooserStart.setDate(null);
            jDateChooserEnd.setDate(null);
            jTextFieldReason.setText("");

            loadRequests();
            initializeLeaveTypeComboBox();
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Input Error", JOptionPane.ERROR_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error saving request.", "File Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jButtonSubmitRequestActionPerformed

    private void jButtonTimeInActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonTimeInActionPerformed
        // TdODO add your handling code here:
        try {
            TimeTrackerReader.clockIn(loggedInUser.getEmployeeId()); // Call clockIn method
            JOptionPane.showMessageDialog(this, "Time In recorded successfully!", "Time In", JOptionPane.INFORMATION_MESSAGE); // User feedback
            loadAttendanceLogs(null, null); // Refresh attendance logs (optional - for immediate update)
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error recording Time In.", "Error", JOptionPane.ERROR_MESSAGE); // Error feedback
            Logger.getLogger(EmployeeDashboard.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButtonTimeInActionPerformed

    private void jButtonTimeOutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonTimeOutActionPerformed
        // TODO add your handling code here:
        try {
            TimeTrackerReader.clockOut(loggedInUser.getEmployeeId()); // Call clockOut method
            JOptionPane.showMessageDialog(this, "Time Out recorded successfully!", "Time Out", JOptionPane.INFORMATION_MESSAGE); // User feedback
            loadAttendanceLogs(null, null); // Refresh attendance logs (optional - for immediate update)
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error recording Time Out.", "Error", JOptionPane.ERROR_MESSAGE); // Error feedback
            Logger.getLogger(EmployeeDashboard.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButtonTimeOutActionPerformed

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
    private javax.swing.JButton jButtonEmployeeDetails;
    private javax.swing.JButton jButtonLogout;
    private javax.swing.JButton jButtonRequests;
    private javax.swing.JButton jButtonSalaryInfomation;
    private javax.swing.JButton jButtonSubmitRequest;
    private javax.swing.JButton jButtonTimeIn;
    private javax.swing.JButton jButtonTimeOut;
    private javax.swing.JComboBox<String> jComboBoxLeaveType;
    private javax.swing.JComboBox<String> jComboBoxSelectMonthAttendance;
    private javax.swing.JComboBox<String> jComboBoxSelectYearAttendance;
    private com.toedter.calendar.JDateChooser jDateChooserEnd;
    private com.toedter.calendar.JDateChooser jDateChooserStart;
    private javax.swing.JLabel jLabePH;
    private javax.swing.JLabel jLabel2x2Picture2;
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
    private javax.swing.JLabel jLabelSelectMonthAttendance;
    private javax.swing.JLabel jLabelSelectYearAttendance;
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
    private javax.swing.JPanel jPanelEmployeeDetails2;
    private javax.swing.JPanel jPanelHeaders;
    private javax.swing.JPanel jPanelMenuBar;
    private javax.swing.JPanel jPanelPicture2;
    private javax.swing.JPanel jPanelRequests;
    private javax.swing.JPanel jPanelSalaryCalculations;
    private javax.swing.JPanel jPanelSalaryInformation;
    private javax.swing.JPanel jPanelSalaryLogs;
    private javax.swing.JPanel jPanelSelectMonthAttendance;
    private javax.swing.JPanel jPanelSelectYearAttendance;
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
