package SystemUsers;

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

    public User(){

    }
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
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

    abstract public void loginToSystem();
    abstract public void viewEvent();

    @Override
    public boolean validateFirstName(){
        if(this.firstName.isEmpty()){
            fNameValidateStatus = "empty";
            return false;
        }else{
            if(containsSpecialCharactersAndDigits(this.firstName)){
                fNameValidateStatus = "format";
                return false;
            }else{
                fNameValidateStatus = "correct";
                return true;
            }
        }
    }

    @Override
    public  boolean validateLastName(){
        if(this.lastName.isEmpty()){
            lNameValidateStatus = "empty";
            return false;
        }else{
            if(containsSpecialCharactersAndDigits(this.lastName)){
                lNameValidateStatus = "format";
                return false;
            }else{
                lNameValidateStatus = "correct";
                return true;
            }
        }
    }

    @Override
    public boolean validateContactNumber(){
        System.out.println(this.contactNumber);
        int contactLength = String.valueOf(this.contactNumber).length();
        System.out.println(contactLength);
        if(contactLength != 10){
            contactNumberValidateStatus = "length";
            System.out.println("Not up to the length !!!");
            return false;
        }else{
            contactNumberValidateStatus = "correct";
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
    public boolean validateUserName(String requiredWork, String user){
        if(user.isEmpty()){
            userNameValidateStatus = "empty";
            return false;
        }

        String columnName = null;

        try {
            String sql;
            if(user.equals("student")){
                sql = "SELECT * FROM studentCredentials  WHERE studentUserName = ?";
            }else{
                sql = "SELECT * FROM TeacherCredentials WHERE teacherUserName = ?";
            }

            PreparedStatement preparedStatement = HelloApplication.connection.prepareStatement(sql);
            preparedStatement.setString(1, this.getUserName());
            ResultSet results = preparedStatement.executeQuery();

            if (results.next()) {
                columnName = results.getString(1);
            }

            if (requiredWork.equals("registration") || requiredWork.equals("updation")) {
                if (columnName != null && columnName.equals(this.getUserName())) {
                    userNameValidateStatus = "exist";
                    System.out.println("That user name already exists !!!");
                    return false;
                } else if (this.getUserName().isEmpty()) {
                    userNameValidateStatus = "empty";
                    System.out.println("Empty !!!");
                    return false;
                } else if ( this.getUserName().contains(" ")) {
                    userNameValidateStatus = "blank";
                    System.out.println("Blank !!!!");
                    return false;
                } else if (this.getUserName().length() > 10 || this.getUserName().length() < 5) {
                    System.out.println(this.getUserName().length());
                    userNameValidateStatus = "length";
                    System.out.println("Lenght !!");
                    return false;
                } else {
                    userNameValidateStatus = "correct";
                    return true;
                }
            } else if (requiredWork.equals("login")) {
                if (columnName != null && columnName.equals(this.getUserName())) {
                    System.out.println("That user name already exists !!!");
                    userNameValidateStatus = "correct";
                    return true;
                } else {
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
    public boolean validatePassword(String requiredWork) throws SQLException{
        if(requiredWork.equals("registration")){
            if(this.getPassword().isEmpty()){
                System.out.println("Empty empty !!!!!!!");
                passwordValidateStatus = "empty";
                return false;
            }
            if(checkPasswordIsValid(this.getPassword())){
                return true;
            }else{
                passwordValidateStatus = "format";
                return false;
            }
        }else{
            // login and edit password
            return false;
        }
    }

    protected static boolean checkPasswordIsValid(String password) {
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
       fNameValidateStatus = "";
       lNameValidateStatus = "";
       contactNumberValidateStatus = "";
       userNameValidateStatus = "";
    }
}
