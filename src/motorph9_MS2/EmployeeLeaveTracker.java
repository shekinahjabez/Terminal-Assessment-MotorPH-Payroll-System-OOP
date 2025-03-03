/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package motorph9_MS2;

/**
 *
 * @author Shekinah Jabez
 */
public class EmployeeLeaveTracker {
    private int sickLeaveBalance;
    private int vacationLeaveBalance;
    private int birthdayLeaveBalance;
    
    public EmployeeLeaveTracker(int sickLeaveBalance, int vacationLeaveBalance, int birthdayLeaveBalance) {
        this.sickLeaveBalance = sickLeaveBalance;
        this.vacationLeaveBalance = vacationLeaveBalance;
        this.birthdayLeaveBalance = birthdayLeaveBalance;
    }
    
    public int getSickLeaveBalance() {
        return sickLeaveBalance;
    }

    public int getVacationLeaveBalance() {
        return vacationLeaveBalance;
    }

    public int getBirthdayLeaveBalance() {
        return birthdayLeaveBalance;
    }

    // Setter methods
    public void setSickLeaveBalance(int sickLeaveBalance) {
        this.sickLeaveBalance = sickLeaveBalance;
    }

    public void setVacationLeaveBalance(int vacationLeaveBalance) {
        this.vacationLeaveBalance = vacationLeaveBalance;
    }

    public void setBirthdayLeaveBalance(int birthdayLeaveBalance) {
        this.birthdayLeaveBalance = birthdayLeaveBalance;
    }
    
    public boolean hasSufficientLeave(String leaveType, int leaveDuration) {
        switch (leaveType) {
            case "Sick Leave":
                return sickLeaveBalance >= leaveDuration;
            case "Vacation Leave":
                return vacationLeaveBalance >= leaveDuration;
            case "Birthday Leave":
                return birthdayLeaveBalance >= leaveDuration;
            default:
                return false;
        }
    }

    public void deductLeave(String leaveType, int leaveDuration) {
        switch (leaveType) {
            case "Sick Leave":
                sickLeaveBalance -= leaveDuration;
                break;
            case "Vacation Leave":
                vacationLeaveBalance -= leaveDuration;
                break;
            case "Birthday Leave":
                birthdayLeaveBalance -= leaveDuration;
                break;
        }
    }
} 
