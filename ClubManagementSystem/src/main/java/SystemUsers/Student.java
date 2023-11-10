package SystemUsers;

import com.example.clubmanagementsystem.HelloApplication;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Student extends User{
    private int studentAdmissionNum;
    private int studentGrade;
    private char Gender;
    public static boolean studentUserNameStat;
    public static boolean studentFirstName;
    public static boolean studentLastName;


    public Student(String userName,String password,
                   String firstName, String lastName,
                   int contactNumber, int studentAdmissionNum,
                   int studentGrade, char Gender){
        super(userName, password, firstName, lastName, contactNumber);
    }

    public Student(){

    }

    public int getStudentAdmissionNum() {
        return studentAdmissionNum;
    }

    public void setStudentAdmissionNum(int studentAdmissionNum) {
        this.studentAdmissionNum = studentAdmissionNum;
    }

    public int getStudentGrade() {
        return studentGrade;
    }

    public void setStudentGrade(int studentGrade) {
        this.studentGrade = studentGrade;
    }

    public char getGender() {
        return Gender;
    }

    public void setGender(char gender) {
        Gender = gender;
    }

    @Override
    public boolean validateUserName(String requiredWork, String user) {
        return super.validateUserName(requiredWork, user);
    }

    @Override
    public boolean validatePassword(String requiredWork) throws SQLException {
        if(requiredWork.equals("Login")){
            String dbPasswordName = null;
            String sql = "SELECT * FROM studentCredentials  WHERE studentUserName = ?";
            PreparedStatement preparedStatement = HelloApplication.connection.prepareStatement(sql);
            preparedStatement.setString(1, this.getUserName());
            ResultSet results = preparedStatement.executeQuery();
            while(results.next()){
                dbPasswordName = results.getString(2);
                System.out.println(dbPasswordName);
            }
            return false;
        }else{
            return false;
        }
    }

    private TextField studentFName, studentLName, studentAdmissionNumber, studentContactNumber,
    studentUserName, studentPassword, studentConfirmPassword;



    // fName, lName, ContactNum, UserName, Password, admissionNum, Gender - done already with validation
    // Grade- to be validated
    public boolean validateGrade(int studentGrade) {
        String studentGradeInString = String.valueOf(this.studentGrade);
        if (!studentGradeInString.matches("\\d+")) {
            System.out.println("Please enter only numbers.");
            return false;
        } else {
            System.out.println("Valid input!");
            return true;
        }
    }

    public boolean validateAdmissionNumber(int studentAdmissionNum) {
        int admissionNumLength = String.valueOf(this.studentAdmissionNum).length();
        if (admissionNumLength != 4) {
            System.out.println("Please enter four digits.");
            return false;
        } else if (!String.valueOf(this.studentAdmissionNum).matches("\\d+")) {
            System.out.println("Please enter only numeric values");
            return false;
        } else {
            System.out.println("Entered correctly.");
            return true;
        }
    }

    public boolean validateGender(char gender) {
        String genderInput = Character.toString(gender);
        if ((!genderInput.equals("M")) && !genderInput.equals("F")) {
            return false;
        } else {
            return true;
        }
    }

}
