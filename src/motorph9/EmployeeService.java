/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package motorph9;

import java.io.*;
import java.util.*;

/**
 *
 * @author Shekinah Jabez
 */
public class EmployeeService {
    private static final String FILE_PATH = "src/motorph9/EmployeeDetails.csv";

    public EmployeeList getEmployeeById(String employeeId) {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data[0].equals(employeeId)) {
                    return new EmployeeList(data[0], data[3], data[4], data[13], data[14]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Employees getEmployeeById(String employeeId) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}

