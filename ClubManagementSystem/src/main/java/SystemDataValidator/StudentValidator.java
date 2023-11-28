package SystemDataValidator;

import java.sql.SQLException;

// work done by- Lakshan
public interface StudentValidator {
    // Method to validate the student admission number
    boolean validateStudentAdmissionNumber() throws SQLException;

}
