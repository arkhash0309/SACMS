package SystemDataValidator;

import java.sql.SQLException;

// work done by- Lakshan
public interface UserValidator {
    boolean validateFirstName(); // Method to validate the first name
    boolean validateLastName(); // Method to validate the last name
    boolean validateContactNumber(); // Method to validate the contact number
    boolean validateUserName(String requiredWork, String user); // Method to validate the user name
    boolean validatePassword(String requiredWork) throws SQLException; // Method to validate the password

}
