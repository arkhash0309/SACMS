package ClubManager;

import SystemUsers.Student;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;

import java.util.ArrayList;

public class Attendance {
    public Student student;
    private String studentName;
    private int studentAdmissionNum;
    private String clubName;
    private String eventName;
    private Event event;
    private boolean attendanceStatusProperty;
    public static ArrayList<Attendance> atdTracker = new ArrayList<>();
    @FXML
    private CheckBox attendanceTracker;

    public void checkboxStatusTracker() {
        if (this.attendanceStatusProperty == true) {
            attendanceTracker.setSelected(true);
        } else {
            attendanceTracker.setSelected(false);
        }
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(Student student) {
        this.studentName = student.getFirstName() + " " + student.getLastName();
    }

    public int getStudentAdmissionNum() {
        return studentAdmissionNum;
    }

    public void setStudentAdmissionNum(Student student) {
        this.studentAdmissionNum = student.getStudentAdmissionNum();
    }

    public String getClubName() {
        return clubName;
    }

    public void setClubName(Event clubName) {
        this.clubName = clubName.getClubName();
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(Event eventName) {
        this.eventName = eventName.getEventName();
    }

    public Attendance(boolean attendanceStatus, Student student, Event event, CheckBox attendanceTracker) {
        this.attendanceStatusProperty = attendanceStatus;
        this.student = student;
        this.event = event;
        this.attendanceTracker = attendanceTracker;
        setClubName(event);
        setEventName(event);
        setStudentAdmissionNum(student);
        setStudentName(student);
        checkboxStatusTracker();
    }

    public Attendance(boolean attendanceStatus, CheckBox stat) {
        this.attendanceStatusProperty = attendanceStatus;
        // Assuming you want to initialize the CheckBox in the constructor
        this.attendanceTracker = stat;
    }

    public CheckBox getAttendanceTracker() {
        return attendanceTracker;
    }

    public void setAttendanceTracker (CheckBox attendanceTracker) {
        this.attendanceTracker = attendanceTracker;
    }

    public boolean attendanceStatusProperty() {
        return attendanceStatusProperty;
    }






}