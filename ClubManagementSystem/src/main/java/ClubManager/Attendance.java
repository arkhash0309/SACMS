package ClubManager;

import SystemUsers.Student;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;

import java.util.ArrayList;

public class Attendance {
    public Student student;
    private SimpleBooleanProperty attendanceStatusProperty;
    private Event eventName;

    public static ArrayList<Attendance> atdTracker = new ArrayList<>();

    @FXML
    private CheckBox attendanceTracker;

    public Attendance(boolean attendanceStatus, Student student) {
        this.attendanceStatusProperty = new SimpleBooleanProperty(attendanceStatus);
        this.student = student;
    }

    public Attendance(boolean attendanceStatus, CheckBox stat) {
        this.attendanceStatusProperty = new SimpleBooleanProperty(attendanceStatus);
        // Assuming you want to initialize the CheckBox in the constructor
        this.attendanceTracker = stat;
    }

    public SimpleBooleanProperty attendanceStatusProperty() {
        return attendanceStatusProperty;
    }


    public boolean isAttendanceStatus() {
        return attendanceStatusProperty.get();
    }

    public void setAttendanceStatus(boolean attendanceStatus) {
        this.attendanceStatusProperty.set(attendanceStatus);
    }

    public CheckBox getAttendanceTracker() {
        return attendanceTracker;
    }


}
