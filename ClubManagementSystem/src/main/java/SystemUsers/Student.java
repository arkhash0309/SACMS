package SystemUsers;

import ClubManager.Club;
import ClubManager.Event;
import SystemDataValidator.StudentValidator;
import com.example.clubmanagementsystem.HelloApplication;
import javafx.scene.control.Alert;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Student extends User implements StudentValidator {
    private int studentAdmissionNum;
    private int studentGrade;
    private char studentGender;
    public static String admissionNumStatus = "";
    public static ArrayList<Student> studentDetailArray = new ArrayList<>();
    public static ArrayList<Club> studentJoinedClubs = new ArrayList<>();
    public static ArrayList<Event> studentEvent = new ArrayList<>();

    public Student(String userName,String password,
                   String firstName, String lastName,
                   String contactNumber, int studentAdmissionNum,
                   int studentGrade, char studentGender){
        super(userName, password, firstName, lastName, contactNumber);
        this.studentAdmissionNum = studentAdmissionNum;
        this.studentGrade = studentGrade;
        this.studentGender = studentGender;
    }

    public Student(){

    }

    public Student(String userName, String password, String firstName, String lastName) {
        super(userName, password, firstName, lastName);
    }

    public Student(String contactNumber){
        super(contactNumber);
    }

    public Student(int admissionNumValue) {
        super();
        this.studentAdmissionNum = admissionNumValue;
    }

    public Student(String updatedUserName, String updatedFirstName, String updatedLastName,
                   String updatedContactNum, String updatedAdmissionNum) {
        super(updatedUserName,updatedFirstName,updatedLastName, updatedContactNum, updatedAdmissionNum);
    }


    @Override
    public void registerToSystem() {

    }

    @Override
    public void loginToSystem() {

    }

    @Override
    public void viewEvent() {

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
        return studentGender;
    }

    public void setGender(char studentGender) {
        this.studentGender = studentGender;
    }

    @Override
    public boolean validateStudentAdmissionNumber() throws SQLException {
        if(String.valueOf(this.getStudentAdmissionNum()).isEmpty()){
            admissionNumStatus = "empty";
            System.out.println(this.getStudentAdmissionNum());
            return false;
        }

        if(String.valueOf(this.getStudentAdmissionNum()).length() != 6){
            admissionNumStatus = "length";
            System.out.println("more than 6");
            return false;
        }

        int dbStudentAdmissionNum  = 0;
        String sql = "SELECT * FROM Student WHERE studentAdmissionNum = ?";
        PreparedStatement preparedStatement = HelloApplication.connection.prepareStatement(sql);
        preparedStatement.setString(1, String.valueOf(this.getStudentAdmissionNum()));
        ResultSet results = preparedStatement.executeQuery();

        while(results.next()){
            dbStudentAdmissionNum = results.getInt(1);
            System.out.println(dbStudentAdmissionNum);
        }

        if(this.getStudentAdmissionNum() == dbStudentAdmissionNum){
            admissionNumStatus = "exist";
            return false;
        }else{
            admissionNumStatus = "";
            return true;
        }
    }


    public void joinClub(Club clubToJoin) {
        if (Student.studentJoinedClubs.contains(clubToJoin)) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("School Club Management System");
            alert.setHeaderText("You are already a member of " + clubToJoin.getClubName());
            alert.show();
            return;
        }


        String insertQuery = "INSERT INTO StudentClub (studentAdmissionNum, clubId) VALUES (?, ?)";

        try (PreparedStatement preparedStatement = HelloApplication.connection.prepareStatement(insertQuery)) {
            System.out.println("studentAdmissionNum: " + Student.studentDetailArray.get(0).getStudentAdmissionNum());
            System.out.println("clubId: " + clubToJoin.getClubId());

            preparedStatement.setInt(1, Student.studentDetailArray.get(0).getStudentAdmissionNum());
            preparedStatement.setInt(2, clubToJoin.getClubId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }


        Student.studentJoinedClubs.add(clubToJoin);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("School Club Management System");
        alert.setHeaderText("You have successfully joined " + clubToJoin.getClubName());
        alert.show();
    }


    public void leaveClub(Club club, int tableIndex){
        for(Club clubVal : Student.studentJoinedClubs){
            if(clubVal.getClubName().equals(club.getClubName())){
                Student.studentJoinedClubs.remove(tableIndex);
                for(Club x : Student.studentJoinedClubs){
                    System.out.println(x);
                }
                break;
            }
        }

        String deleteQuery = "DELETE FROM StudentClub WHERE studentAdmissionNum = ? AND clubId = ?";

        try (PreparedStatement preparedStatement = HelloApplication.connection.prepareStatement(deleteQuery)) {
            preparedStatement.setInt(1, Student.studentDetailArray.get(0).getStudentAdmissionNum());
            preparedStatement.setInt(2, club.getClubId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Alert deletedEvent = new Alert(Alert.AlertType.INFORMATION);
        deletedEvent.setHeaderText("You have successfully left the club!!!");
        deletedEvent.setTitle("School Club Management System");
        deletedEvent.show();
    }








}
