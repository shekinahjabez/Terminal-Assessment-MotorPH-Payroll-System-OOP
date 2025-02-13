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
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import static com.opencsv.ICSVWriter.NO_QUOTE_CHARACTER;
import com.opencsv.exceptions.CsvException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Shekinah Jabez
 */
public class EmployeeList extends javax.swing.JFrame {
    
    private static final String CSV_FILE = "src\\motorph9\\EmployeeDetails.csv";
    private final HRDashboard dashboard;
    private final EmployeeService employeeService;
    
    /**
     * Creates new form Employee
     */
    public EmployeeList(HRDashboard dashboard) {
        initComponents();
       
        setLocationRelativeTo(null);
        
        employeeService = new EmployeeService();
        populateTableFromCSV(CSV_FILE);

        jButtonViewEmployee.setEnabled(false);

        jButtonUpdate.setEnabled(false); 
        
        this.dashboard = dashboard;
        
        jTableEmployees.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int selectedRow = jTableEmployees.getSelectedRow();
                    jButtonViewEmployee.setEnabled(selectedRow != -1);
                }
            }
        });
        
        
        jTableEmployees.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int selectedRow = jTableEmployees.getSelectedRow();
                jButtonUpdate.setEnabled(selectedRow != -1);
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

    
    private void updateTimeAndDate() {
        
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm:ss a");
        String time = timeFormat.format(new Date());

        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, MMMM d, yyyy");
        String date = dateFormat.format(new Date());

        jLabelTime.setText(time);
        jLabelDate.setText(date);
    
    }
    
    public void populateTableFromCSV(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            Vector<Vector<String>> data = new Vector<>();
            Vector<String> columnNames = new Vector<>();

           
            line = br.readLine();
            String[] columns = line.split(",");
            columnNames.add(columns[0].trim()); // EmployeeList Number
            columnNames.add(columns[3].trim()); // Last Name
            columnNames.add(columns[4].trim()); // First Name
            columnNames.add(columns[8].trim()); // SSS No.
            columnNames.add(columns[9].trim()); // PhilHealth No.
            columnNames.add(columns[10].trim()); // TIN
            columnNames.add(columns[11].trim()); // Pagibig No.

            
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                Vector<String> row = new Vector<>();
                row.add(values[0].trim()); // EmployeeList Number
                row.add(values[3].trim()); // Last Name
                row.add(values[4].trim()); // First Name
                row.add(values[8].trim()); // SSS No.
                row.add(values[9].trim()); // PhilHealth No.
                row.add(values[10].trim()); // TIN
                row.add(values[11].trim()); // Pagibig No.
                data.add(row);
            }


            
            DefaultTableModel model = new DefaultTableModel(data, columnNames);
            jTableEmployees.setModel(model);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

     
     private void updateEmployeeDetails() {
        
        
        int selectedRow = jTableEmployees.getSelectedRow();

        
        if (selectedRow != -1) {
        
        String lastName = (String) jTableEmployees.getValueAt(selectedRow, 1);
        String firstName = (String) jTableEmployees.getValueAt(selectedRow, 2);
        String sssNo = (String) jTableEmployees.getValueAt(selectedRow, 3);
        String philHealthNo = (String) jTableEmployees.getValueAt(selectedRow, 4);
        String tinNo = (String) jTableEmployees.getValueAt(selectedRow, 5);
        String pagibigNo = (String) jTableEmployees.getValueAt(selectedRow, 6);

           
        jTableEmployees.setValueAt(lastName, selectedRow, 1); // Update last name           
        jTableEmployees.setValueAt(firstName, selectedRow, 2); // Update first name         
        jTableEmployees.setValueAt(sssNo, selectedRow, 3); // Update SSS No.         
        jTableEmployees.setValueAt(philHealthNo, selectedRow, 4); // Update PhilHealth No.               
        jTableEmployees.setValueAt(tinNo, selectedRow, 5); // Update TIN           
        jTableEmployees.setValueAt(pagibigNo, selectedRow, 6); // Update Pagibig No.
                         
        try{
            employeeService.updateCSVFile(lastName, firstName, sssNo, philHealthNo, tinNo, pagibigNo, selectedRow);
        }
        catch (IOException ex) {
            Logger.getLogger(EmployeeList.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}

     private void refreshJTable() {
       
        DefaultTableModel model = (DefaultTableModel) jTableEmployees.getModel();
        model.setRowCount(0);
        
        populateTableFromCSV(CSV_FILE);
        
    }
       
    private void updateCSVFile(String employeeNumber) {
        try (BufferedReader br = new BufferedReader(new FileReader(CSV_FILE))) {
            String line;
            int csvLimit = 0;            
            Vector<String> lines = new Vector<>();
       
            
            while ((line = br.readLine())!= null) {
                String[] values = line.split(",");
                if (!values[0].trim().equals(employeeNumber)) {
                    lines.add(line);                  
                }
            }

               
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(CSV_FILE))) {
                for (String lineToWrite : lines) {                                                                                          
                    bw.write(lineToWrite);
                    //This condition prevents writing blank row at the last part of the csv
                    if(csvLimit < jTableEmployees.getRowCount()){
                        bw.newLine();
                        csvLimit++;}
                    else{
                        bw.flush();
                        bw.close();
                        br.close();
                        break;}
                }              
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Refresh the JTable to reflect changes
        refreshJTable();
    }
    
    public void updateCSVFile(String lastName,
                              String firstName, 
                              String sssNo,
                              String philHealthNo,
                              String tinNo,
                              String pagibigNo,
                              int selectedRow) throws FileNotFoundException, IOException{
                      
        try{           
            DefaultTableModel modelToCSV = (DefaultTableModel) jTableEmployees.getModel();
            int jTable_columnCount = modelToCSV.getColumnCount();
            
            //Saving all the Column Name from JTable in An Array
            String[] jTableColumnName = new String[jTable_columnCount];           
            for (int i=0;i<jTable_columnCount;i++){
                jTableColumnName[i] = modelToCSV.getColumnName(i);
            }
            
            //Getting Row of Data edited from Jtable
            String[] arrEditedJTableRowData = new String [jTable_columnCount];                     
            for (int i = 0; i < jTable_columnCount; i++) {                               
                arrEditedJTableRowData[i] = (String) modelToCSV.getValueAt(selectedRow, i);
             }         
            
            //Reading the CSV Document
            List<List<String>> records = new ArrayList<>();      
            try (BufferedReader br = new BufferedReader(new FileReader(CSV_FILE))) {
            String line;
                while ((line = br.readLine()) != null) {
                    String[] values = line.split(",");                   
                    records.add(Arrays.asList(values));                  
                }
            }           
            catch(IOException e){e.printStackTrace();}
        
            //Getting the ColumnName from the CSV file.
            List <String> csvOneLineRead = records.get(0);        
            int csvMaxIndex = csvOneLineRead.size();
      
            //Converting the from an Array List to an Array
            String[] arrCSVOneLineRead = new String[csvMaxIndex];
            for (int i = 0; i < csvOneLineRead.size(); i++) {
                arrCSVOneLineRead[i] = csvOneLineRead.get(i);
            }
        
            //Getting the actual index of an array based from JTable Column Name
            int[] jTableToCSVIdentifier = new int[jTable_columnCount];
            for(int i=0;i<jTable_columnCount;i++){
                for(int j=0;j<arrCSVOneLineRead.length;j++){
                    if(jTableColumnName[i].equalsIgnoreCase(arrCSVOneLineRead[j])){
                        jTableToCSVIdentifier[i] = j;
                    }
                }
            }            
                             
            try{             
                CSVReader csvReader = new CSVReader(new FileReader(new File(CSV_FILE)));                
                List<String[]> allData = csvReader.readAll();
                               
                for(int i=0;i<jTableToCSVIdentifier.length;i++){
                   allData.get(selectedRow+1)[jTableToCSVIdentifier[i]] = arrEditedJTableRowData[i];
                }
                
                CSVWriter csvWriter = new CSVWriter (new FileWriter(new File(CSV_FILE)));
                csvWriter.writeAll(allData,false);
                csvWriter.flush();
                csvWriter.close();
                        
                }
            catch(CsvException e){e.printStackTrace();}                   
        }
        catch (IOException e){e.printStackTrace();}
    }
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanelMain = new javax.swing.JPanel();
        jLableTitle = new javax.swing.JLabel();
        jScrollPaneTableEmployees = new javax.swing.JScrollPane();
        jTableEmployees = new javax.swing.JTable();
        jPanelDateAndTime = new javax.swing.JPanel();
        jLabelGMT = new javax.swing.JLabel();
        jLabelTime = new javax.swing.JLabel();
        jLabelDate = new javax.swing.JLabel();
        jButtonDashboard = new javax.swing.JButton();
        jButtonViewEmployee = new javax.swing.JButton();
        jButtonUpdate = new javax.swing.JButton();
        jButtonDelete = new javax.swing.JButton();
        jLabelEmployeelistTitle = new javax.swing.JLabel();
        jButtonAdd = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(830, 490));

        jPanelMain.setBackground(new java.awt.Color(0, 0, 0));
        jPanelMain.setMaximumSize(new java.awt.Dimension(2147483647, 2147483647));
        jPanelMain.setMinimumSize(new java.awt.Dimension(830, 490));
        jPanelMain.setPreferredSize(new java.awt.Dimension(830, 490));

        jLableTitle.setFont(new java.awt.Font("Segoe UI Semibold", 1, 20)); // NOI18N
        jLableTitle.setForeground(new java.awt.Color(204, 0, 0));
        jLableTitle.setIcon(new javax.swing.ImageIcon(getClass().getResource("/logos9/empdes3.png"))); // NOI18N

        jTableEmployees.setBackground(new java.awt.Color(0, 0, 51));
        jTableEmployees.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jTableEmployees.setForeground(new java.awt.Color(255, 255, 255));
        jTableEmployees.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Employee Number", "Last Name", "First name", "SSS No.", "PhilHealth No.", "TIN No.", "Pagibig No."
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, true, true, true, true, true, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableEmployees.setToolTipText("");
        jScrollPaneTableEmployees.setViewportView(jTableEmployees);

        jPanelDateAndTime.setBackground(new java.awt.Color(0, 0, 51));

        jLabelGMT.setForeground(new java.awt.Color(255, 255, 255));
        jLabelGMT.setText("GMT+8 PH Time");

        jLabelTime.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jLabelTime.setForeground(new java.awt.Color(255, 255, 255));
        jLabelTime.setText("12:12:12 AM");

        javax.swing.GroupLayout jPanelDateAndTimeLayout = new javax.swing.GroupLayout(jPanelDateAndTime);
        jPanelDateAndTime.setLayout(jPanelDateAndTimeLayout);
        jPanelDateAndTimeLayout.setHorizontalGroup(
            jPanelDateAndTimeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelDateAndTimeLayout.createSequentialGroup()
                .addGroup(jPanelDateAndTimeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelDateAndTimeLayout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(jLabelGMT))
                    .addGroup(jPanelDateAndTimeLayout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(jLabelTime)))
                .addGap(0, 11, Short.MAX_VALUE))
        );
        jPanelDateAndTimeLayout.setVerticalGroup(
            jPanelDateAndTimeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelDateAndTimeLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelTime)
                .addGap(18, 18, 18)
                .addComponent(jLabelGMT)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabelDate.setBackground(new java.awt.Color(0, 0, 51));
        jLabelDate.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jLabelDate.setForeground(new java.awt.Color(255, 255, 255));
        jLabelDate.setText("Wednesday, December 25, 2012");

        jButtonDashboard.setBackground(new java.awt.Color(0, 0, 51));
        jButtonDashboard.setForeground(new java.awt.Color(255, 255, 255));
        jButtonDashboard.setText("Dashboard");
        jButtonDashboard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonDashboardActionPerformed(evt);
            }
        });

        jButtonViewEmployee.setBackground(new java.awt.Color(0, 0, 51));
        jButtonViewEmployee.setForeground(new java.awt.Color(255, 255, 255));
        jButtonViewEmployee.setText("View Employee ");
        jButtonViewEmployee.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonViewEmployeeActionPerformed(evt);
            }
        });

        jButtonUpdate.setBackground(new java.awt.Color(0, 0, 51));
        jButtonUpdate.setForeground(new java.awt.Color(255, 255, 255));
        jButtonUpdate.setText("Update");
        jButtonUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonUpdateActionPerformed(evt);
            }
        });

        jButtonDelete.setBackground(new java.awt.Color(0, 0, 51));
        jButtonDelete.setForeground(new java.awt.Color(255, 255, 255));
        jButtonDelete.setText("Delete");
        jButtonDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonDeleteActionPerformed(evt);
            }
        });

        jLabelEmployeelistTitle.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabelEmployeelistTitle.setForeground(new java.awt.Color(255, 255, 255));
        jLabelEmployeelistTitle.setText("EMPLOYEE LIST");

        jButtonAdd.setBackground(new java.awt.Color(0, 0, 51));
        jButtonAdd.setForeground(new java.awt.Color(255, 255, 255));
        jButtonAdd.setText("Add");
        jButtonAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAddActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelMainLayout = new javax.swing.GroupLayout(jPanelMain);
        jPanelMain.setLayout(jPanelMainLayout);
        jPanelMainLayout.setHorizontalGroup(
            jPanelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelMainLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPaneTableEmployees)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelMainLayout.createSequentialGroup()
                        .addGap(0, 681, Short.MAX_VALUE)
                        .addComponent(jLabelDate))
                    .addGroup(jPanelMainLayout.createSequentialGroup()
                        .addComponent(jLableTitle)
                        .addGroup(jPanelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelMainLayout.createSequentialGroup()
                                .addGap(43, 43, 43)
                                .addComponent(jButtonDashboard, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButtonViewEmployee)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButtonUpdate)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButtonDelete)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButtonAdd)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanelMainLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabelEmployeelistTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(171, 171, 171)))
                        .addComponent(jPanelDateAndTime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanelMainLayout.setVerticalGroup(
            jPanelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelMainLayout.createSequentialGroup()
                .addGroup(jPanelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelMainLayout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addGroup(jPanelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanelMainLayout.createSequentialGroup()
                                .addComponent(jLabelEmployeelistTitle)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jButtonDashboard, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButtonViewEmployee, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButtonUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButtonDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButtonAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jPanelDateAndTime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanelMainLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLableTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabelDate)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPaneTableEmployees, javax.swing.GroupLayout.PREFERRED_SIZE, 424, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(15, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelMain, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 865, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelMain, javax.swing.GroupLayout.DEFAULT_SIZE, 546, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    private void jButtonUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonUpdateActionPerformed
        // TODO add your handling code here:
       // Add action listener for the "Update" button
       jButtonUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get the selected row index
                int selectedRow = jTableEmployees.getSelectedRow();
                if (selectedRow != -1) {
                    // Get the data from the selected row
                    String employeeNumber = (String) jTableEmployees.getValueAt(selectedRow, 0);
                    String lastName = (String) jTableEmployees.getValueAt(selectedRow, 1);
                    String firstName = (String) jTableEmployees.getValueAt(selectedRow, 2);
                    String sssNo = (String) jTableEmployees.getValueAt(selectedRow, 3);
                    String philHealthNo = (String) jTableEmployees.getValueAt(selectedRow, 4);
                    String tinNo = (String) jTableEmployees.getValueAt(selectedRow, 5);
                    String pagibigNo = (String) jTableEmployees.getValueAt(selectedRow, 6);
                                                                  
                    jTableEmployees.setValueAt(employeeNumber, selectedRow, 0); // Update employee number
                    jTableEmployees.setValueAt(lastName, selectedRow, 1); // Update last name
                    jTableEmployees.setValueAt(firstName, selectedRow, 2); // Update first name
                    jTableEmployees.setValueAt(sssNo, selectedRow, 3); // Update SSS No.
                    jTableEmployees.setValueAt(philHealthNo, selectedRow, 4); // Update PhilHealth No.
                    jTableEmployees.setValueAt(tinNo, selectedRow, 5); // Update TIN
                    jTableEmployees.setValueAt(pagibigNo, selectedRow, 6); // Update Pagibig No. 
                                      
                    updateEmployeeDetails(); 
                    refreshJTable();
                }
            }    
        });
    }//GEN-LAST:event_jButtonUpdateActionPerformed

    private void jButtonDashboardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonDashboardActionPerformed
        
        dashboard.setVisible(true);
        /*HRDashboard newClassInstance = new HRDashboard(firstName,currentEmployeeId);
                 newClassInstance.setVisible(true);*/
                 
                 dispose();
    }//GEN-LAST:event_jButtonDashboardActionPerformed

    private void jButtonViewEmployeeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonViewEmployeeActionPerformed
        
        // Add an ActionListener to the "View EmployeeList" button
        jButtonViewEmployee.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = jTableEmployees.getSelectedRow();
                if (selectedRow != -1) {
                    
                    String employeeNumber = (String) jTableEmployees.getValueAt(selectedRow, 0);
                    String lastName = (String) jTableEmployees.getValueAt(selectedRow, 1);
                    String firstName = (String) jTableEmployees.getValueAt(selectedRow, 2);
                    String sssNo = (String) jTableEmployees.getValueAt(selectedRow, 3);
                    String philHealthNo = (String) jTableEmployees.getValueAt(selectedRow, 4);
                    String tinNo = (String) jTableEmployees.getValueAt(selectedRow, 5);
                    String pagibigNo = (String) jTableEmployees.getValueAt(selectedRow, 6);

                    
                    EmployeeRecords newClassInstance = new EmployeeRecords(employeeNumber,dashboard);
                            newClassInstance.setVisible(true);
                            
                            dispose();
                            
                }
                
            }
        });
    }//GEN-LAST:event_jButtonViewEmployeeActionPerformed

    private void jButtonDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonDeleteActionPerformed
        // TODO add your handling code here:
        jButtonDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = jTableEmployees.getSelectedRow();
                if (selectedRow != -1) {
                     
                     int response = JOptionPane.showConfirmDialog(null, 
                              "Do you really want to delete this employee?", 
                               "Confirm Deletion", 
                               JOptionPane.YES_NO_OPTION, 
                               JOptionPane.WARNING_MESSAGE);
                    
                    if (response == JOptionPane.YES_OPTION) {
                        // Get the employee number from the selected row
                        String employeeNumber = (String) jTableEmployees.getValueAt(selectedRow, 0);
                        // Delete the selected row from the JTable model
                        DefaultTableModel model = (DefaultTableModel) jTableEmployees.getModel();
                        model.removeRow(selectedRow);
                    
                        
                        updateCSVFile(employeeNumber);

                    }
                } else {
                   // Show a message if no row is selected
                   JOptionPane.showMessageDialog(null, 
                           "Please select an employee to delete.", 
                           "No Selection", 
                           JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
    }//GEN-LAST:event_jButtonDeleteActionPerformed

    private void jButtonAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAddActionPerformed
        // TODO add your handling code here:
        AddEmployee newClassInstance = new AddEmployee(dashboard);
                   newClassInstance.setVisible(true);
                
                dispose();
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
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(EmployeeList.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(EmployeeList.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(EmployeeList.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(EmployeeList.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        /*java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Employee().setVisible(true);
            }
        //</editor-fold>

        /* Create and display the form */
        /*java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new EmployeeList().setVisible(true);
            }
        });*/
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonAdd;
    private javax.swing.JButton jButtonDashboard;
    private javax.swing.JButton jButtonDelete;
    private javax.swing.JButton jButtonUpdate;
    private javax.swing.JButton jButtonViewEmployee;
    private javax.swing.JLabel jLabelDate;
    private javax.swing.JLabel jLabelEmployeelistTitle;
    private javax.swing.JLabel jLabelGMT;
    private javax.swing.JLabel jLabelTime;
    private javax.swing.JLabel jLableTitle;
    private javax.swing.JPanel jPanelDateAndTime;
    private javax.swing.JPanel jPanelMain;
    private javax.swing.JScrollPane jScrollPaneTableEmployees;
    private javax.swing.JTable jTableEmployees;
    // End of variables declaration//GEN-END:variables

    public String getPosition() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public String getEmployeeId() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public String getFullName() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
