/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package password_reset9;
import java.io.IOException;

/**
 *
 * @author Shekinah Jabez
 */
public interface PasswordResetDataAccess {
    void saveRequest(PasswordResetRequest request) throws IOException;
}

