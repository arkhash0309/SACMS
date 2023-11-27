package SystemUsers;

import ClubAdvisorDashboardManager.ClubAdvisorActivityController;
import DataBaseManager.StudentDataBaseManager;
import SystemDataValidator.UserValidator;
import com.example.clubmanagementsystem.HelloApplication;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

abstract public class User implements UserValidator {
    private String userName;
    private String password;
    private String firstName;
    private String lastName;
    private String contactNumber;
    public static String fNameValidateStatus;
    public static String lNameValidateStatus;
    public static String contactNumberValidateStatus;
    public static String passwordValidateStatus;
    public static String userNameValidateStatus;


    public User(String userName, String password,
                String firstName, String lastName, String contactNumber){
        this.userName = userName;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.contactNumber = contactNumber;
    }

    public User(String userName, String password,
                String firstName, String lastName){
        this.userName = userName;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        System.out.println("first name  :" + this.firstName);
        System.out.println("last name : " + this.lastName);
    }
    public User(String contactNumber){
        this.contactNumber = contactNumber;
    }

//    public User(String userName, String password){
//        this.userName = userName;
//        this.password = password;
//   }

    public User(String UserName,String password){
        this.userName = UserName;
        this.password = password;
    }

    public User(){

    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        System.out.println(password);
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    abstract public void registerToSystem();
    abstract public String LoginToSystem();

    @Override
    public boolean validateFirstName(){ // validating first name
        if(this.firstName.isEmpty()){ // if first name is empty
            fNameValidateStatus = "empty"; // setting fNameValidateStatus to "empty"
            return false;
        }else{
            if(containsSpecialCharactersAndDigits(this.firstName)){ // if first name field contains numbers and special characters
                fNameValidateStatus = "format"; // setting fNameValidateStatus to "format"
                return false;
            }else{
                fNameValidateStatus = "correct"; // if first name is entered correctly
                return true;
            }
        }
    }

    @Override
    public  boolean validateLastName(){ // validating last name
        if(this.lastName.isEmpty()){ // if last name field is empty
            lNameValidateStatus = "empty"; // setting lNameValidateStatus to "empty"
            return false;
        }else{
            if(containsSpecialCharactersAndDigits(this.lastName)){ // if last name field contains numbers and special characters
                lNameValidateStatus = "format"; // setting lNameValidateStatus to "format"
                return false;
            }else{
                lNameValidateStatus = "correct"; // if last name is entered correctly
                return true; // returning
            }
        }
    }

    @Override
    public boolean validateContactNumber(){ // validating contact number
        int contactLength = String.valueOf(this.contactNumber).length(); // taking the length of the contact number
        if(contactLength != 10){ // if length of the contact number is not equal to 10
            contactNumberValidateStatus = "length"; // setting contactNumberValidateStatus to "length"
            System.out.println("Not up to the length !!!");
            return false;
        }else{
            contactNumberValidateStatus = "correct"; // if contact number entered correctly
            return true;
        }
    }

    public static boolean containsSpecialCharactersAndDigits(String value){
        String patterCheck = "[\\d\\p{Punct}]";
        Pattern compiledPattern = Pattern.compile(patterCheck);
        Matcher matcher = compiledPattern.matcher(value);
        return matcher.find();
    }

    @Override
    public boolean validateUserName(String requiredWork, String user){ /* requiredWork is to check whether it is registration
                                                                                    or updating, user is for advisor and student */

        if(this.userName.isEmpty()){ // if username is empty
            userNameValidateStatus = "empty"; // setting userNameValidateStatus to "empty"
            return false;
        }
        // hold a row from database
        String rowName = null;
        try {
            String sql;
            // Determine the SQL query based on the user type
            if(user.equals("student")){
                sql = "SELECT * FROM studentCredentials  WHERE studentUserName = ?";
            }else{
                sql = "SELECT * FROM TeacherCredentials WHERE teacherUserName = ?";
            }
            PreparedStatement preparedStatement = HelloApplication.connection.prepareStatement(sql);
            preparedStatement.setString(1, this.getUserName()); // setting username
            ResultSet results = preparedStatement.executeQuery();

            if (results.next()) {
                rowName = results.getString(1); // retrieving a row from database
            }
            // validation for registration or updating
            if (requiredWork.equals("registration") || requiredWork.equals("updation")) {
                // updating for advisor
                if(requiredWork.equals("updation") && !user.equals("student")){
                    if(this.userName.equals(ClubAdvisorActivityController.username)){
                        userNameValidateStatus = "correct";
                        return true;
                    }
                }
                if(requiredWork.equals("updation") && user.equals("student")){
                    // Updating for student
                    if(this.getUserName().equals(StudentDataBaseManager.getStudentUserName())){
                        userNameValidateStatus = "correct";
                        return true;
                    }
                }
                if (rowName != null && rowName.equals(this.getUserName())) {
                    // username already exists
                    userNameValidateStatus = "exist"; // setting userNameValidateStatus to "exist"
                    System.out.println("That user name already exists !!!");
                    return false;
                } else if (this.getUserName().isEmpty()) {
                    // empty username
                    userNameValidateStatus = "empty"; // setting userNameValidateStatus to "empty"
                    System.out.println("Empty !!!");
                    return false;
                } else if ( this.getUserName().contains(" ")) {
                    // username contains spaces
                    userNameValidateStatus = "blank"; // setting userNameValidateStatus to "blank"
                    System.out.println("Blank !!!!");
                    return false;
                } else if (this.getUserName().length() > 10 || this.getUserName().length() < 5) {
                    System.out.println(this.getUserName().length());
                    // username length is not within the specified range
                    userNameValidateStatus = "length"; // setting userNameValidateStatus to "length"
                    System.out.println("Length !!");
                    return false;
                } else {
                    // username meets all criteria
                    userNameValidateStatus = "correct"; // setting userNameValidateStatus to "correct"
                    return true;
                }
            } else if (requiredWork.equals("login")) {
                // validating login
                if (rowName != null && rowName.equals(this.getUserName())) {
                    System.out.println("That user name already exists !!!");
                    userNameValidateStatus = "correct";
                    return true;
                } else {
                    // username doesn't exist for login
                    userNameValidateStatus = "exist";
                    return false;
                }
            } else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    @Override
    public boolean validatePassword(String requiredWork) throws SQLException{ // validating password
        if(requiredWork.equals("registration")){
            if(this.getPassword().isEmpty()){ // if password is empty
                System.out.println("Empty empty !!!!!!!");
                passwordValidateStatus = "empty";
                return false;
            } else if(checkPasswordIsValid(this.getPassword())){ // if password is valid according to checkPasswordIsValid method
                return true;
            }else{
                System.out.println("format");
                passwordValidateStatus = "format"; // setting passwordValidateStatus to "format"
                return false;
            }
        }else{
            // login and edit password
            if(this.getPassword().isEmpty()){
                System.out.println("Empty !!!!!!!");
                passwordValidateStatus = "empty";
                return false;
            }else if(checkPasswordIsValid(this.getPassword())){
                return true;
            }else{
                passwordValidateStatus = "format";
                return false;
            }
        }
    }

    protected static boolean checkPasswordIsValid(String password) { // checking whether password contains special characters
        // at least 8 digits, can add chars, numbers
        String pattern = "^(?=.*\\d)(?=.*[@#$%^&+=]).{8,}$";

        // compile the given pattern by ignoring the case
        Pattern compilePattern = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE);

        // Check password checks the given regex pattern
        Matcher matcher = compilePattern.matcher(password);

        // return result
        return matcher.matches();
    }

    {
       fNameValidateStatus = "correct";
       lNameValidateStatus = "correct";
       contactNumberValidateStatus = "correct";
       userNameValidateStatus = "correct";
       passwordValidateStatus = "correct";
    }
}