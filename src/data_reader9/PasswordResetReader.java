package data_reader9;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JOptionPane;
import motorph9_MS2.PasswordResetRequest;

/**
 *
 * @author Shekinah Jabez
 */
public class PasswordResetReader {

    private static final String FILE_NAME = "src/data9/Password_Reset_Requests.csv";

    public static void saveRequest(PasswordResetRequest request) {
        File file = new File(FILE_NAME);
        boolean fileExists = file.exists(); 

        try (FileWriter writer = new FileWriter(FILE_NAME, true);
             BufferedWriter bufferedWriter = new BufferedWriter(writer)) {
            
            if (!fileExists) {
                bufferedWriter.write("Employee Number,Employee Name,Date of Request,Status\n");
            }

            bufferedWriter.write(request.toCSV() + "\n");
            bufferedWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /*public static void saveRequest(PasswordResetRequest request) {
        File file = new File(FILE_NAME);
        boolean fileExists = file.exists(); // Check if file already exists

        try (FileWriter writer = new FileWriter(FILE_NAME, true);
             BufferedWriter bufferedWriter = new BufferedWriter(writer)) {
            
            // ✅ If the file is newly created, add headers
            if (!fileExists) {
                bufferedWriter.write("Employee Number,Employee Name,Date of Request,Status\n");
            }

            // ✅ Append the request data
            bufferedWriter.write(request.toCSV() + "\n");
            bufferedWriter.flush();

            JOptionPane.showMessageDialog(null, "Request saved successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error saving request!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }*/
}
