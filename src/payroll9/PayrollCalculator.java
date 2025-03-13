package payroll9;

import java.io.IOException;
import java.time.LocalDateTime;

/**
 *
 * @author Shekinah Jabez
 */
public interface PayrollCalculator {
    double calculateNetSalary(String employeeId, LocalDateTime startDate, LocalDateTime endDate) throws IOException;
}
