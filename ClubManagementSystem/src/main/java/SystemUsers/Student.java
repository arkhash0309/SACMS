package SystemUsers;

import com.example.clubmanagementsystem.HelloApplication;

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


}
