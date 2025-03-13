package payroll9;

/**
 *
 * @author Shekinah Jabez
 */
public class Allowance {
    private double riceSubsidy;
    private double phoneAllowance;
    private double clothingAllowance;

    public Allowance(double riceSubsidy, double phoneAllowance, double clothingAllowance) {
        this.riceSubsidy = riceSubsidy;
        this.phoneAllowance = phoneAllowance;
        this.clothingAllowance = clothingAllowance;
    }

    public double getRiceSubsidy() {
        return riceSubsidy;
    }

    public double getPhoneAllowance() {
        return phoneAllowance;
    }

    public double getClothingAllowance() {
        return clothingAllowance;
    }

    public double getTotalAllowance(){
        return riceSubsidy + phoneAllowance + clothingAllowance;
    }
}