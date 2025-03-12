package payroll9;

/**
 *
 * @author Shekinah Jabez
 */
public class Deductions {
    public static double calculatePagibigDeduction() {
        return 100.0; // fixed Pagibig deduction of 100
    }
    
    public static double calculatePhilHealthDeduction(double basicSalary) {
        return (basicSalary * 0.03) / 2;
    }
    
    public static double calculateSSSDeduction(double basicSalary) {
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
    
    public static double calculateWithholdingTax(double netSalary) {
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
    
}
