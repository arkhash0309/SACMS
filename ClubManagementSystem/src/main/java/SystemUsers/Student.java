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

// work done by- Arkhash, Lakshan, Pramuditha and Deelaka
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
        this.setStudentGender(studentGender);
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

    public Student(String userName, String password){
        super(userName, password);
    }

    public Student(String updatedUserName, String updatedFirstName, String updatedLastName,
                   String updatedContactNum, String updatedAdmissionNum) {
        super(updatedUserName,updatedFirstName,updatedLastName, updatedContactNum, updatedAdmissionNum);
    }

//    @Override
//    public String advisorRegisteringToSystem(){
//        return null;
//    }

    //Created for inserting details into generate report membership table
    public Student(int memberAdmissionNum, String memberUserName, String memberFirstName, String memberLastName,int memberGrade,char memberGender, String memberContactNum) {
        super(memberUserName,null,memberFirstName, memberLastName, memberContactNum);
        this.studentAdmissionNum = memberAdmissionNum;
        this.studentGrade = memberGrade;
        this.setStudentGender(memberGender);
    }
    @Override
    public void registerToSystem() {
        // inserting newly registered student's personal details to database
        String studentPersonalDetailsQuery = "INSERT INTO Student (studentAdmissionNum, studentFName, studentLName, " +
                "studentGrade, studentContactNum, Gender) VALUES (?, ?, ?, ?, ?, ?)"; // SQL query
        try (PreparedStatement preparedStatement = HelloApplication.connection.prepareStatement(studentPersonalDetailsQuery)) {
            preparedStatement.setInt(1, this.getStudentAdmissionNum()); // setting admission number
            preparedStatement.setString(2, this.getFirstName()); // setting first name
            preparedStatement.setString(3, this.getLastName()); // setting last name
            preparedStatement.setInt(4, this.getStudentGrade());  // setting grade
            preparedStatement.setInt(5, Integer.parseInt(this.getContactNumber())); // setting contact number
            System.out.println("Coming till contact number");
            preparedStatement.setString(6, String.valueOf(this.getGender())); // setting gender
            System.out.println("going after gender");
            preparedStatement.executeUpdate();
            System.out.println("Personal details inserting perfectly");
        } catch (Exception e) {
            System.out.println(e);
        }
        // inserting newly registered student's credentials to the database
        String studentCredentialsDetailsQuery = "INSERT INTO studentCredentials (studentUserName," +
                " studentPassword, studentAdmissionNum) VALUES (?, ?, ?)"; // SQL query
        try (PreparedStatement preparedStatement = HelloApplication.connection.prepareStatement(studentCredentialsDetailsQuery)) {
            preparedStatement.setString(1, this.getUserName()); // setting username
            preparedStatement.setString(2, this.getPassword()); // setting lastname
            preparedStatement.setInt(3, this.getStudentAdmissionNum()); // setting admission number in order to map two tables
            preparedStatement.executeUpdate();
            System.out.println("Credentials inserting perfectly");
        } catch (Exception e) {
            System.out.println(e);
        }
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
        return getStudentGender();
    }

    public void setGender(char studentGender) {
        this.setStudentGender(studentGender);
    }

    @Override
    public String LoginToSystem() { // this method will check whether entered password is correct
        String correctPassword = null; // store correct password from database
        String credentialChdeckQuery = "select studentPassword from studentCredentials where studentUserName = ?"; // sql query
        try (PreparedStatement preparedStatement = HelloApplication.connection.prepareStatement(credentialChdeckQuery)) { // prepare the statement to execute the code
            preparedStatement.setString(1, this.getUserName()); // we are setting the clubAdvisortLoginPageUserName to where the question mark is
            try (ResultSet results = preparedStatement.executeQuery()) { // results variable will store all the rows in Student table
                while (results.next()) { // this will loop the rows
                    correctPassword = results.getString("studentPassword"); // getting the password
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return correctPassword;
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

    // This method handles student join club functionality
    public void joinClub(Club clubToJoin) {
        if (Student.studentJoinedClubs.contains(clubToJoin)) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("School Club Management System");
            alert.setHeaderText("You are already a member of " + clubToJoin.getClubName());
            alert.show();
            return;
        }

        // This query will insert the club to the StudentClub table
        String insertQuery = "INSERT INTO StudentClub (studentAdmissionNum, clubId) VALUES (?, ?)";

        try (PreparedStatement preparedStatement = HelloApplication.connection.prepareStatement(insertQuery)) {
            System.out.println("studentAdmissionNum: " + Student.studentDetailArray.get(0).getStudentAdmissionNum());
            System.out.println("clubId: " + clubToJoin.getClubId());
            // prepared statement will set the values for the parameters
            preparedStatement.setInt(1, Student.studentDetailArray.get(0).getStudentAdmissionNum());
            preparedStatement.setInt(2, clubToJoin.getClubId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // This will add the club to the studentJoinedClubs array list
        Student.studentJoinedClubs.add(clubToJoin);
        // Alert for the deleted event
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("School Club Management System");
        alert.setHeaderText("You have successfully joined " + clubToJoin.getClubName());
        alert.show();

        giveAttendanceForJoinedClubs(clubToJoin);
    }
    // This method will give attendance for the joined clubs
    public void giveAttendanceForJoinedClubs(Club club){
        // This loop will check whether the club name is equal to the event club name
        for(Event event : Event.eventDetails){
            if(club.getClubName().equals(event.getClubName())){
                // This query will insert the attendance for the student
                String sql = "INSERT INTO StudentAttendance (studentAdmissionNum, clubId, EventId, attendanceStatus) VALUES (?, ?, ?, ?)";

                try (PreparedStatement statement = HelloApplication.connection.prepareStatement(sql)) {
                    // Set values for the parameters in the prepared statement
                    statement.setInt(1, Student.studentDetailArray.get(0).getStudentAdmissionNum());
                    statement.setInt(2, club.getClubId());
                    statement.setInt(3, event.getEventId());
                    statement.setBoolean(4, false);

                    System.out.println(Student.studentDetailArray.get(0).getUserName());
                    // Execute the insert query
                    statement.executeUpdate();
                    System.out.println("Done and dusted join");

                } catch (SQLException e) {
                    System.out.println("Wrong work !!!!!!");
                    e.printStackTrace();
                }
            }
        }
    }

    // This method handle leaving club functionality
    public void leaveClub(Club club, int tableIndex){
        // This loop will remove the club from the studentJoinedClubs array list
        for(Club clubVal : Student.studentJoinedClubs){
            if(clubVal.getClubName().equals(club.getClubName())){
                // This will remove the club from the array list
                Student.studentJoinedClubs.remove(tableIndex);
                for(Club x : Student.studentJoinedClubs){
                    System.out.println(x);
                }
                break;
            }
        }

        // this query will delete the club from the StudentClub table
        String deleteQuery = "DELETE FROM StudentClub WHERE studentAdmissionNum = ? AND clubId = ?";

        try (PreparedStatement preparedStatement = HelloApplication.connection.prepareStatement(deleteQuery)) {
            // prepared statement will set the values for the parameters
            preparedStatement.setInt(1, Student.studentDetailArray.get(0).getStudentAdmissionNum());
            preparedStatement.setInt(2, club.getClubId()); // this will get the club id from the club object
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Alert for the deleted event
        Alert deletedEvent = new Alert(Alert.AlertType.INFORMATION);
        deletedEvent.setHeaderText("You have successfully left the club!!!");
        deletedEvent.setTitle("School Club Management System");
        deletedEvent.show();
    }
    // This method will get the student gender
    public char getStudentGender() {
        return studentGender;
    }
    // This method set student gender
    public void setStudentGender(char studentGender) {
        this.studentGender = studentGender;
    }

//    public static ArrayList<Club> getStudentJoinedClubs() {
//        return studentJoinedClubs;
//    }
}
