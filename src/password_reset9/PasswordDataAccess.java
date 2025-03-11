package password_reset9;

import java.io.IOException;
import java.util.List;

/**
 *
 * @author Shekinah Jabez
 */
public interface PasswordDataAccess {
    List<String[]> readLoginData() throws IOException;
    void writeLoginData(List<String[]> loginData) throws IOException;
    List<String[]> readResetRequestsData() throws IOException;
    void writeResetRequestsData(List<String[]> resetRequestsData) throws IOException;
}
