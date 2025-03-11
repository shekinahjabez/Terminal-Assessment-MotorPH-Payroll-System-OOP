/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package password_reset9;

import data_reader9.CSVReader;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author Shekinah Jabez
 */
public class PasswordCsvDataAccess implements PasswordDataAccess {
    private static final String LOGIN_FILE = "src/data9/Login.csv";
    private static final String REQUESTS_FILE = "src/data9/Password_Reset_Requests.csv";

    @Override
    public List<String[]> readLoginData() throws IOException {
        return CSVReader.readCSV(LOGIN_FILE);
    }

    @Override
    public void writeLoginData(List<String[]> loginData) throws IOException {
        CSVReader.writeCSV(LOGIN_FILE, loginData);
    }

    @Override
    public List<String[]> readResetRequestsData() throws IOException {
        return CSVReader.readCSV(REQUESTS_FILE);
    }

    @Override
    public void writeResetRequestsData(List<String[]> resetRequestsData) throws IOException {
        CSVReader.writeCSV(REQUESTS_FILE, resetRequestsData);
    }
}
