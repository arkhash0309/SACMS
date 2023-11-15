package ClubManager;

import SystemUsers.Student;

public class Attendance {
    Student student;
    boolean attendanceStatus;
    public Attendance(boolean attendanceStatus,Student student){
        this.attendanceStatus = attendanceStatus;
        this.student = student;
    }

}
