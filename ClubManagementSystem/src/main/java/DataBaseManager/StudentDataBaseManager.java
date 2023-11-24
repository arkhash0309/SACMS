package DataBaseManager;

import ClubManager.Club;
import ClubManager.Event;
import SystemUsers.ClubAdvisor;
import SystemUsers.Student;
import com.example.clubmanagementsystem.HelloApplication;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;

// This class
public class StudentDataBaseManager {
    private int StudentAdmissionNum;
    private static String StudentUserName;

    public StudentDataBaseManager(String StudentUserName) throws SQLException {
        this.setStudentUserName(StudentUserName);
        this.StudentAdmissionNum = getStudentAdmissionNum(this.getStudentUserName());
        System.out.println(this.StudentAdmissionNum);
        populateStudentDetails();
        populateAllClubDetails();
        populateAllClubAdvisorDetails();
        populateClubAdvisorCreatedClubs();
        populateStudentJoinedClubs();
        populateEventDetails();
        populateAllEvents();
    }

    public int getStudentAdmissionNum(String userName){
        int studentAdmissionNum = 0;
        String selectStudentQuery = "SELECT studentAdmissionNum FROM studentCredentials WHERE studentUserName = ?";
        try (PreparedStatement preparedStatement = HelloApplication.connection.prepareStatement(selectStudentQuery)) {
            preparedStatement.setString(1, userName);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                studentAdmissionNum = resultSet.getInt("studentAdmissionNum");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return studentAdmissionNum;
    }

    public void populateStudentDetails(){
        Student.studentDetailArray.clear();
        String query = "SELECT STC.studentUserName, STC.studentPassword, STD.studentAdmissionNum, STD.studentFName, " +
                "STD.studentLName, STD.studentGrade, STD.studentContactNum, STD.Gender " +
                "FROM Student STD " +
                "JOIN studentCredentials STC ON STD.studentAdmissionNum = STC.studentAdmissionNum  " +
                "WHERE STD.studentAdmissionNum = ?";

        try (PreparedStatement preparedStatement = HelloApplication.connection.prepareStatement(query)) {
            preparedStatement.setInt(1, this.StudentAdmissionNum);
            try (ResultSet result = preparedStatement.executeQuery()) {
                while (result.next()) {
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

                    Student.studentDetailArray.add(student);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        System.out.println(Student.studentDetailArray.size());
    }

    public void populateAllClubDetails(){
        Club.clubDetailsList.clear();
        String query = "SELECT C.clubId, C.clubName, C.clubDescription, C.clubLogo " +
                "FROM Club C JOIN TeacherInCharge TIC ON C.teacherInChargeId = TIC.teacherInChargeId ";

        try (PreparedStatement preparedStatement = HelloApplication.connection.prepareStatement(query)) {
            try (ResultSet result = preparedStatement.executeQuery()) {
                while (result.next()) {
                    Club club  = new Club(
                            result.getInt("clubId"),
                            result.getString("clubName"),
                            result.getString("clubDescription"),
                            result.getString("clubLogo")
                    );

                    System.out.println(result.getString("clubLogo"));

                    Club.clubDetailsList.add(club);
                    System.out.println("Elama Elama");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void populateAllClubAdvisorDetails() throws SQLException {
        ClubAdvisor.clubAdvisorDetailsList.clear();

        String query = "SELECT TIC.teacherInChargeId, TC.teacherUserName, TC.teacherPassword, TIC.TICFName, TIC.TICLName, TIC.teacherContactNum " +
                "FROM TeacherCredentials TC " +
                "JOIN TeacherInCharge TIC ON TC.teacherInChargeId = TIC.teacherInChargeId";

        try (PreparedStatement preparedStatement = HelloApplication.connection.prepareStatement(query)) {
            try (ResultSet result = preparedStatement.executeQuery()) {
                while (result.next()) {
                    ClubAdvisor clubAdvisor = new ClubAdvisor(
                            result.getString("teacherUserName"),
                            result.getString("teacherPassword"),
                            result.getString("TICFName"),
                            result.getString("TICLName"),
                            result.getString("teacherContactNum"),
                            result.getInt("teacherInChargeId")
                    );

                    ClubAdvisor.clubAdvisorDetailsList.add(clubAdvisor);

                }
            }
        }

    }

    public void populateClubAdvisorCreatedClubs(){
        int count = 0;
        for(ClubAdvisor clubAdvisor : ClubAdvisor.clubAdvisorDetailsList){
            clubAdvisor.createdClubDetailsList.clear();

            String query = "SELECT C.clubId, C.clubName, C.clubDescription, C.clubLogo " +
                    "FROM Club C " +
                    "JOIN TeacherCredentials TC ON C.teacherInChargeId = TC.teacherInChargeId " +
                    "WHERE TC.teacherUserName = ?";

            try (PreparedStatement preparedStatement = HelloApplication.connection.prepareStatement(query)) {
                preparedStatement.setString(1, clubAdvisor.getUserName());

                try (ResultSet result = preparedStatement.executeQuery()) {
                    while (result.next()) {
                        int clubId = result.getInt("clubId");
                        String clubName = result.getString("clubName");
                        String clubDescription = result.getString("clubDescription");
                        String clubLogo = result.getString("clubLogo");


                        Club club = new Club(clubId, clubName, clubDescription, clubLogo);

                        clubAdvisor.createdClubDetailsList.add(club);
                    }

                    ClubAdvisor.clubAdvisorDetailsList.set(count, clubAdvisor);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            count++;
        }
    }

    public void populateStudentJoinedClubs(){
        Student.studentJoinedClubs.clear();
        String query = "SELECT C.clubId, C.clubName, C.clubDescription, C.clubLogo " +
                "FROM Club C " +
                "JOIN StudentClub SC ON C.clubId = SC.clubId " +
                "WHERE SC.studentAdmissionNum = ?";

        try (PreparedStatement preparedStatement = HelloApplication.connection.prepareStatement(query)) {
            preparedStatement.setInt(1, this.StudentAdmissionNum);

            try (ResultSet result = preparedStatement.executeQuery()) {
                while (result.next()) {
                    int clubId = result.getInt("clubId");
                    String clubName = result.getString("clubName");
                    String clubDescription = result.getString("clubDescription");
                    String clubLogo = result.getString("clubLogo");

                    Club clubDetail = new Club(clubId, clubName, clubDescription, clubLogo);

                    Student.studentJoinedClubs.add(clubDetail);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception appropriately
        }



    }


    public void populateEventDetails(){
        Student.studentEvent.clear();
        for(Club club : Student.studentJoinedClubs){
            String query = "SELECT EventId, eventName, eventDate, eventTime, eventLocation, eventType, eventDeliveryType, eventDescription " +
                    "FROM EventDetails WHERE clubId = ?";

            try (PreparedStatement preparedStatement = HelloApplication.connection.prepareStatement(query)) {
                preparedStatement.setInt(1, club.getClubId());

                try (ResultSet result = preparedStatement.executeQuery()) {
                    while (result.next()) {
                        Date eventDate = result.getDate("eventDate");
                        Time eventTime = result.getTime("eventTime");
                        LocalDate localDate = eventDate.toLocalDate();
                        LocalTime localTime = eventTime.toLocalTime();

                        Event event = new Event(
                                result.getString("eventName"),
                                result.getString("eventLocation"),
                                result.getString("eventType"),
                                result.getString("eventDeliveryType"),
                                localDate,
                                localTime,
                                club,
                                result.getString("eventDescription"),
                                result.getInt("EventId")
                        );

                        Student.studentEvent.add(event);
                    }
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }
    }

    public void populateAllEvents(){
        Event.eventDetails.clear();
        for(Club club : Club.clubDetailsList){
            String query = "SELECT EventId, eventName, eventDate, eventTime, eventLocation, eventType, eventDeliveryType, eventDescription " +
                    "FROM EventDetails WHERE clubId = ?";

            try (PreparedStatement preparedStatement = HelloApplication.connection.prepareStatement(query)) {
                preparedStatement.setInt(1, club.getClubId());

                try (ResultSet result = preparedStatement.executeQuery()) {
                    while (result.next()) {
                        Date eventDate = result.getDate("eventDate");
                        Time eventTime = result.getTime("eventTime");
                        LocalDate localDate = eventDate.toLocalDate();
                        LocalTime localTime = eventTime.toLocalTime();

                        Event event = new Event(
                                result.getString("eventName"),
                                result.getString("eventLocation"),
                                result.getString("eventType"),
                                result.getString("eventDeliveryType"),
                                localDate,
                                localTime,
                                club,
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


    public static String getStudentUserName() {
        return StudentUserName;
    }

    public static void setStudentUserName(String studentUserName) {
        StudentUserName = studentUserName;
    }
}
