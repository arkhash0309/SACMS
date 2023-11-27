package DataBaseManager;

import ClubManager.Attendance;
import ClubManager.Club;
import ClubManager.Event;
import SystemUsers.ClubAdvisor;
import SystemUsers.Student;
import com.example.clubmanagementsystem.HelloApplication;
import javafx.scene.control.CheckBox;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

// This class handles loading data when user do the login to ClubAdvisorDataBaseManager
public class ClubAdvisorDataBaseManager {
    // Static variables to store data requires data that should be loaded to club advisor related work
    public static int loginClubAdvisorId;

    public static ArrayList<Integer> clubIdList = new ArrayList<>();

    public static HashMap<Integer, Club> requiredClub = new HashMap<>();

    public static HashMap<Student, ArrayList<Club>> joinedClubForEachStudent = new HashMap<>();

    // Username is taken to identify the club advisor who logged into the system

    private String userName;

    // club advisor id is taken to identify the club advisor who logged into the system

    private int ClubAdvisorId;

    public static int lastClubIndex;

    // Constructor for ClubAdvisorDataBaseManager, This handles all the methods loading to lists
    public ClubAdvisorDataBaseManager(String userName) throws SQLException {
        this.userName = userName;
        this.ClubAdvisorId = selectClubAdvisorId(this.userName);
        System.out.println(this.ClubAdvisorId);
        populateClubAdvisorArray();
        populateClubDetailArray(Club.clubDetailsList, this.ClubAdvisorId);
        populateEventsDetailArray();
        populateStudentDetailArray();
        getLastClubId();
        setStudentJoinedClubDetails();
        populateAttendanceDetailsArray();
    }

    // default constructor for club advisor database manager
    public ClubAdvisorDataBaseManager(){

    }

    // This method gets the last club id that has been stored in the system
    public void getLastClubId() {
        // Query to find the maximum club id
        String query = "SELECT MAX(clubId) AS maxClubId FROM Club";
        int maxClubId = 0;

        try (PreparedStatement preparedStatement = HelloApplication.connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                maxClubId = resultSet.getInt("maxClubId");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // assign the maximum club id created to the system
        lastClubIndex = maxClubId;
        System.out.println(lastClubIndex);
    }

    // This method selects the club advisor id
    public int selectClubAdvisorId(String userName){
        int teacherInChargeId = 0;
        // select statement to find out the id of the club advisor
        String selectTeacherInChargeIdQuery = "SELECT teacherInChargeId FROM TeacherCredentials WHERE teacherUserName = ?";
        try (PreparedStatement preparedStatement = HelloApplication.connection.prepareStatement(selectTeacherInChargeIdQuery)) {
            preparedStatement.setString(1, userName);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                teacherInChargeId = resultSet.getInt("teacherInChargeId");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return teacherInChargeId;

    }

    // This method populates the club advisor details array by creating Club advisor objects
    public void populateClubAdvisorArray() throws SQLException {
        ClubAdvisor.clubAdvisorDetailsList.clear();

        // Query is to select the login club advisor related details
        String query = "SELECT TIC.teacherInChargeId, TC.teacherUserName, TC.teacherPassword, TIC.TICFName, TIC.TICLName, TIC.teacherContactNum " +
                "FROM TeacherCredentials TC " +
                "JOIN TeacherInCharge TIC ON TC.teacherInChargeId = TIC.teacherInChargeId " +
                "WHERE TC.teacherUserName = ?";

        // Query2 is to select the other club advisor details
        String query2 = "SELECT TIC.teacherInChargeId, TC.teacherUserName, TC.teacherPassword, TIC.TICFName, TIC.TICLName, TIC.teacherContactNum " +
                "FROM TeacherCredentials TC " +
                "JOIN TeacherInCharge TIC ON TC.teacherInChargeId = TIC.teacherInChargeId " +
                "WHERE TC.teacherUserName != ?";

        // query 1, Statement is executed to create Club Advisor Objects
        try (PreparedStatement preparedStatement = HelloApplication.connection.prepareStatement(query)) {
            preparedStatement.setString(1, this.userName);

            try (ResultSet result = preparedStatement.executeQuery()) {
                while (result.next()) {
                    // creation of club advisor object
                    ClubAdvisor clubAdvisor = new ClubAdvisor(
                            result.getString("teacherUserName"),
                            result.getString("teacherPassword"),
                            result.getString("TICFName"),
                            result.getString("TICLName"),
                            result.getString("teacherContactNum"),
                            result.getInt("teacherInChargeId")
                    );

                    // Adding details to club advisor detail array
                    ClubAdvisor.clubAdvisorDetailsList.add(clubAdvisor);

                }
            }
        }

        // query 2, statement is executed to create and populate club advisor details array
        try (PreparedStatement preparedStatement = HelloApplication.connection.prepareStatement(query2)) {
            preparedStatement.setString(1, this.userName);

            try (ResultSet result = preparedStatement.executeQuery()) {
                while (result.next()) {
                    // Creation of club advisor object
                    ClubAdvisor clubAdvisor = new ClubAdvisor(
                            result.getString("teacherUserName"),
                            result.getString("teacherPassword"),
                            result.getString("TICFName"),
                            result.getString("TICLName"),
                            result.getString("teacherContactNum"),
                            result.getInt("teacherInChargeId")
                    );

                    // Adding details to club advisor details list
                    ClubAdvisor.clubAdvisorDetailsList.add(clubAdvisor);

                }
            }
        }

        for(ClubAdvisor clubAdvisor : ClubAdvisor.clubAdvisorDetailsList){
            System.out.println(clubAdvisor.getClubAdvisorId() + ": club advisor Id");
        }
    }

    // This method populates the all student details to student details array
    public void populateStudentDetailArray(){
        Student.studentDetailArray.clear();
        // query to select all the details of the student
        String query = "SELECT STC.studentUserName, STC.studentPassword, STD.studentAdmissionNum, STD.studentFName, " +
                "STD.studentLName, STD.studentGrade, STD.studentContactNum, STD.Gender " +
                "FROM Student STD " +
                "JOIN studentCredentials STC ON STD.studentAdmissionNum = STC.studentAdmissionNum";

        // Execute the statement to create student objects and add it to the student detail array
        try (PreparedStatement preparedStatement = HelloApplication.connection.prepareStatement(query)) {

            try (ResultSet result = preparedStatement.executeQuery()) {
                while (result.next()) {
                    // Creation of student objects
                    Student student  = new Student(
                            result.getString("studentUserName"),
                            result.getString("studentPassword"),
                            result.getString("studentFName"),
                            result.getString("studentLName"),
                            result.getString("studentContactNum"),
                            result.getInt("studentAdmissionNum"),
                            result.getInt("studentGrade"),
                            result.getString("Gender").charAt(0)
                    );

                    // Adding the created student object to student detail array
                    Student.studentDetailArray.add(student);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // This method populates the club detail array
   public void populateClubDetailArray(ArrayList<Club> clubDetailArray, int clubAdvisorId){
       Club.clubDetailsList.clear();
       clubIdList.clear();
       // select the related club details to the selected club advisor
       String query = "SELECT C.clubId, C.clubName, C.clubDescription, C.clubLogo " +
               "FROM Club C JOIN TeacherInCharge TIC ON C.teacherInChargeId = TIC.teacherInChargeId " +
               "WHERE TIC.teacherInChargeId = ?";

       // the selected club details will be populated by making Club objects
       try (PreparedStatement preparedStatement = HelloApplication.connection.prepareStatement(query)) {
           preparedStatement.setInt(1, clubAdvisorId);
           try (ResultSet result = preparedStatement.executeQuery()) {
               while (result.next()) {
                   // Creation of student objects
                   Club club  = new Club(
                           result.getInt("clubId"),
                           result.getString("clubName"),
                           result.getString("clubDescription"),
                           result.getString("clubLogo")
                   );

                   System.out.println(result.getString("clubLogo"));

                   // populating the club detail array with populated club objects
                   clubDetailArray.add(club);
                   clubIdList.add(result.getInt("clubId"));
                   requiredClub.put(result.getInt("clubId"), club);
               }
           }
       } catch (SQLException e) {
           throw new RuntimeException(e);
       }
   }

   // This method populate the event detail array by considering the club advisor logged in to the system
   public void populateEventsDetailArray(){
       Event.eventDetails.clear();
       // select the event details for the selected club id
       for(int clubId : clubIdList){
           String query = "SELECT EventId, eventName, eventDate, eventTime, eventLocation, eventType, eventDeliveryType, eventDescription " +
                   "FROM EventDetails WHERE clubId = ?";

           // populate the event array list using the selected event details by creating event objects
           try (PreparedStatement preparedStatement = HelloApplication.connection.prepareStatement(query)) {
               preparedStatement.setInt(1, clubId);

               try (ResultSet result = preparedStatement.executeQuery()) {
                   while (result.next()) {
                       Date eventDate = result.getDate("eventDate");
                       Time eventTime = result.getTime("eventTime");
                       LocalDate localDate = eventDate.toLocalDate();
                       LocalTime localTime = eventTime.toLocalTime();

                       // Creation of event objects
                       Event event = new Event(
                               result.getString("eventName"),
                               result.getString("eventLocation"),
                               result.getString("eventType"),
                               result.getString("eventDeliveryType"),
                               localDate,
                               localTime,
                               requiredClub.get(clubId),
                               result.getString("eventDescription"),
                               result.getInt("EventId")
                       );

                       Event.eventDetails.add(event);
                   }
               }
           } catch (SQLException e) {
               throw new RuntimeException(e);
           }

       }
   }

   // this method will find out the clubs that each student has joined that has enrolled with the system
   public void setStudentJoinedClubDetails(){
        // clear joinedClubForEachStudent array list
        joinedClubForEachStudent.clear();

        // Check whether student details array is empty
        if(Student.studentDetailArray.isEmpty()) {
            return;
        }

        // Iterate through student array to get the clubs that each student has joined
        for(Student student :Student.studentDetailArray){
            ArrayList<Club> clubs = new ArrayList<>();

            // Get the club related details that each student has joined
            String query = "SELECT c.clubId, c.clubName, c.clubDescription, c.clubLogo " +
                    "FROM StudentClub sc " +
                    "JOIN Club c ON sc.clubId = c.clubId " +
                    "WHERE sc.studentAdmissionNum = ?";

            // This creates a club object and send details to clubs that student has joined
            try (PreparedStatement preparedStatement = HelloApplication.connection.prepareStatement(query)) {

                preparedStatement.setInt(1, student.getStudentAdmissionNum());

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        int clubId = resultSet.getInt("clubId");
                        String clubName = resultSet.getString("clubName");
                        String clubDescription = resultSet.getString("clubDescription");
                        String clubLogo = resultSet.getString("clubLogo");

                        // Creating a club object
                        Club club = new Club(clubId, clubName, clubDescription, clubLogo);
                        clubs.add(club);
                    }
                }

                // find each student joined club using a hash map
                joinedClubForEachStudent.put(student, clubs);

            } catch (SQLException e) {
                e.printStackTrace();
                // Handle the exception as needed
            }

        }

    }

    // This method populate attend related details into all event based attendance array lists
    public void populateAttendanceDetailsArray() throws SQLException {
       // Iterate through the event details array list
       for(Event event : Event.eventDetails){
           // clear the event attendance arraylist for all the students
           event.eventAttendance.clear();
           // find the event related club by iterating through joined clubForeachStudentHashMap
           for (Map.Entry<Student, ArrayList<Club>> entry : joinedClubForEachStudent.entrySet()) {
               Student student = entry.getKey();  // get the student object
               ArrayList<Club> clubs = entry.getValue(); // get the club array list for the student object
               for(Club club : clubs){
                   if(event.getClubName().equals(club.getClubName())){
                       // SQL query to retrieve data from StudentAttendance table based on eventId and clubId
                       String sql = "SELECT * FROM StudentAttendance WHERE EventId = ? AND clubId = ? AND studentAdmissionNum = ?";

                       // Prepare the statement
                       try (PreparedStatement statement = HelloApplication.connection.prepareStatement(sql)) {
                           // Set parameters for the eventId and clubId
                           statement.setInt(1, event.getEventId());
                           statement.setInt(2, club.getClubId());
                           statement.setInt(3, student.getStudentAdmissionNum());

                           // Execute the query
                           try (ResultSet resultSet = statement.executeQuery()) {
                               // Iterate over the result set and retrieve values
                               while (resultSet.next()) {
                                   // get the attendance status of the result from query
                                   boolean attendanceStatus = resultSet.getBoolean("attendanceStatus");
                                   // Make a checkbox object
                                   CheckBox checkBox = new CheckBox();
                                   // Add it to the attendance list
                                   Attendance attendance = new Attendance(attendanceStatus,
                                           student, event, checkBox);
                                   // update the event attendance list of the relavant student
                                   event.eventAttendance.add(attendance);
                                   System.out.println("Work is success and done");
                                   System.out.println(student.getFirstName() + " " + student.getLastName() + " " + attendanceStatus);
                                   break;
                               }
                           }
                       }

                   }
               }
           }

       }
    }

}














