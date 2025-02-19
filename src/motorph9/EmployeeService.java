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

    public Employee getEmployeeById(String employeeId) {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data[0].equals(employeeId)) {
                    return new Employee(data[0], data[3], data[4], data[13], data[14]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Employee> getAllEmployees() {
        List<Employee> employees = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                employees.add(new Employee(data[0], data[3], data[4], data[13], data[14]));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return employees;
    }

    public void addEmployee(Employee employee) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            bw.write(employee.getEmployeeId() + "," + employee.getLastName() + "," + employee.getFirstName() + "," + employee.getPosition() + "," + employee.getSupervisor());
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateEmployee(Employee employee) {
        List<Employee> employees = getAllEmployees();
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Employee emp : employees) {
                if (emp.getEmployeeId().equals(employee.getEmployeeId())) {
                    bw.write(employee.getEmployeeId() + "," + employee.getLastName() + "," + employee.getFirstName() + "," + employee.getPosition() + "," + employee.getSupervisor());
                } else {
                    bw.write(emp.getEmployeeId() + "," + emp.getLastName() + "," + emp.getFirstName() + "," + emp.getPosition() + "," + emp.getSupervisor());
                }
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteEmployee(String employeeId) {
        List<Employee> employees = getAllEmployees();
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Employee emp : employees) {
                if (!emp.getEmployeeId().equals(employeeId)) {
                    bw.write(emp.getEmployeeId() + "," + emp.getLastName() + "," + emp.getFirstName() + "," + emp.getPosition() + "," + emp.getSupervisor());
                    bw.newLine();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
