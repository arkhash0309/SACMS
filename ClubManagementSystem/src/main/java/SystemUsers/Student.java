package SystemUsers;

public class Student extends User{
    private int studentAdmissionNum;
    private int studentGrade;
    private char Gender;

    public Student(String userName,String password,
                   String firstName, String lastName,
                   int contactNumber, int studentAdmissionNum,
                   int studentGrade, char Gender){
        super(userName, password, firstName, lastName, contactNumber);
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
}
