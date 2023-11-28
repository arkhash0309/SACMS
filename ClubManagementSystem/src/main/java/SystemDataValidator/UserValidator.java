package SystemDataValidator;

import java.sql.SQLException;

// work done by- Lakshan
public interface UserValidator {
    boolean validateFirstName();
    boolean validateLastName();
    boolean validateContactNumber();
    boolean validateUserName(String requiredWork, String user);
    boolean validatePassword(String requiredWork) throws SQLException;

}
