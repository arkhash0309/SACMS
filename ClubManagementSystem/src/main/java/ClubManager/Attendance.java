package ClubManager;

import SystemUsers.Student;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;

import java.util.ArrayList;

// work done by- Arkhash
public class Attendance {
    public Student student;
    private String studentName;
    private int studentAdmissionNum;
    private String clubName;
    private String eventName;
    private Event event;
    private boolean attendanceStatusProperty; // a boolean value for the attendance
    public static ArrayList<Attendance> atdTracker = new ArrayList<>(); // an array list of data type Atttendance
    private String nameAttendanceStatus;

    @FXML
    private CheckBox attendanceTracker;

    // method to track the check boxes
    public void checkboxStatusTracker() {
        if (this.attendanceStatusProperty == true) {
            attendanceTracker.setSelected(true);
        } else {
            attendanceTracker.setSelected(false);
        }
    }

    // getter to get the student object
    public Student getStudent() {
        return student;
    }

    // setter to set the student object
    public void setStudent(Student student) {
        this.student = student;
    }

    // getter to get the student name
    public String getStudentName() {
        return studentName;
    }

    // setter to set the student name
    public void setStudentName(Student student) {
        this.studentName = student.getFirstName() + " " + student.getLastName();
    }

    public int getStudentAdmissionNum() {
        return studentAdmissionNum;
    }

    // setter to set the admission num
    public void setStudentAdmissionNum(Student student) {
        this.studentAdmissionNum = student.getStudentAdmissionNum();
    }

    // getter to get the club name
    public String getClubName() {
        return clubName;
    }

    // setter to get the club name
    public void setClubName(Event clubName) {
        this.clubName = clubName.getClubName();
    }

    // getter to get the event name
    public String getEventName() {
        return eventName;
    }

    // setter to  set the event name
    public void setEventName(Event eventName) {
        this.eventName = eventName.getEventName();
    }

    // the main constructor for attendance
    public Attendance(boolean attendanceStatus, Student student, Event event, CheckBox attendanceTracker) {
        this.attendanceStatusProperty = attendanceStatus;
        this.student = student;
        this.setEvent(event);
        this.attendanceTracker = attendanceTracker;
        setClubName(event);
        setEventName(event);
        setStudentAdmissionNum(student);
        setStudentName(student);
        checkboxStatusTracker();
        setNameAttendanceStatus();
    }

    // another constructor for attendance
    public Attendance(boolean attendanceStatus, CheckBox stat) {
        this.attendanceStatusProperty = attendanceStatus;
        // Assuming you want to initialize the CheckBox in the constructor
        this.attendanceTracker = stat;
    }


    //getter to get the checkbox
    public CheckBox getAttendanceTracker() {
        return attendanceTracker;
    }  // a getter method to get the attendance tracker

    // setter to set the checkbox
    public void setAttendanceTracker (CheckBox attendanceTracker) {
        this.attendanceTracker = attendanceTracker;
    }// a setter method to set the attendance tracker

    // boolean method to decide the attendance
    public boolean attendanceStatusProperty() {
        return attendanceStatusProperty;
    } // a getter method to get the attendance status


    // setter to set the attendance value
    public void setAttendanceStatusProperty(boolean attendanceStatusProperty) {
        this.attendanceStatusProperty = attendanceStatusProperty;
    } // a setter method to set the attendance status

    
    public String getNameAttendanceStatus() {
        return nameAttendanceStatus;
    }

    // a setter method to define attended and absent
    public void setNameAttendanceStatus() {
        if(this.attendanceStatusProperty){
            this.nameAttendanceStatus = "Attended";
        }else{
            this.nameAttendanceStatus = "Absent";
        }
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Attendance(){

    }
}