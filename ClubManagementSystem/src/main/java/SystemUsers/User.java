package SystemUsers;

import com.example.clubmanagementsystem.HelloApplication;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

abstract public class User{
    private String userName;
    private String password;
    private String firstName;
    private String lastName;
    private int contactNumber;

    public User(String userName, String password,
                String firstName, String lastName, int contactNumber){
        this.userName = userName;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.contactNumber = contactNumber;
    }

    public User(){

    }
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(int contactNumber) {
        this.contactNumber = contactNumber;
    }

    public boolean validateFirstName(){
        if(this.firstName.isEmpty()){
            return false;
        }else{
            return true;
        }
    }

    public  boolean validateLastName(){
        if(this.lastName.isEmpty()){
            return false;
        }else{
            return true;
        }
    }

    public boolean validateContactNumber(){
        int contactLength = String.valueOf(this.contactNumber).length();
        if(contactLength != 10){
            return false;
        }else{
            return true;
        }
    }


    public void registerToSystem(){

    }

    public void loginToSystem(){

    }

    public boolean validateUserName(String requiredWork, String user){

        String columnName = null;

        try {
            String sql = "SELECT * FROM studentCredentials  WHERE studentUserName = ?";
            PreparedStatement preparedStatement = HelloApplication.connection.prepareStatement(sql);
            preparedStatement.setString(1, this.getUserName());
            ResultSet results = preparedStatement.executeQuery();

            if (results.next()) {
                columnName = results.getString(1);
            }

            if (requiredWork.equals("registration")) {
                if (columnName != null && columnName.equals(this.getUserName())) {
                    System.out.println("That user name already exists !!!");
                    if(user.equals("student")){
                        Student.studentUserNameStat = true;
                    }
                    return false;
                } else if (this.getUserName().isEmpty() || this.getUserName().contains(" ")) {
                    if(user.equals("student")){
                        Student.studentUserNameStat = true;
                    }
                    return false;
                } else if (this.getUserName().length() > 10) {
                    if(user.equals("student")){
                        Student.studentUserNameStat = true;
                    }
                    return false;
                } else {
                    return true;
                }
            } else if (requiredWork.equals("login")) {
                if (columnName != null && columnName.equals(this.getUserName())) {
                    System.out.println("That user name already exists !!!");
                    if(user.equals("student")){
                        Student.studentUserNameStat = true;
                    }
                    return false;
                } else {
                    return true;
                }
            } else {
                if(user.equals("student")){
                    Student.studentUserNameStat = true;
                }
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    abstract public boolean validatePassword(String requiredWork) throws SQLException;

    {
        userName = "Lakshan23";
        password = "Thanuja1234";
    }
}
