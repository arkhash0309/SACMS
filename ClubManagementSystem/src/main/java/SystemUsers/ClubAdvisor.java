package SystemUsers;

import com.example.clubmanagementsystem.HelloApplication;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ClubAdvisor extends User{
    private int clubAdvisorId;

    public static boolean advisorFNameValidStatus;
    public static  boolean advisorLNameValidStatus;
    public static boolean  advisorContactNumberValidStatus;
    public static boolean advisorIdStatus;
    public static boolean advisorUserNameStatus;
    public static boolean advisorPasswordStatus;

    public ClubAdvisor(String userName,String password,
                       String firstName, String lastName,
                       int contactNumber, int clubAdvisorId){
        super(userName, password, firstName, lastName, contactNumber);
        this.clubAdvisorId = clubAdvisorId;
    }


    public int getClubAdvisorId() {
        return clubAdvisorId;
    }

    public void setClubAdvisorId(int clubAdvisorId) {
        this.clubAdvisorId = clubAdvisorId;
    }

    @Override
    public boolean validateUserName(String requiredWork, String user) {
        return super.validateUserName(requiredWork, user);
    }

    @Override
    public boolean validatePassword(String requiredWork) {
        return false;
    }

    public boolean validateClubAdvisorId() throws SQLException {
        if(String.valueOf(this.getClubAdvisorId()).isEmpty()){
            return false;
        }
        String dbClubAdvisorId = null;
        String sql = "SELECT * FROM TeacherInCharge  WHERE teacherInChargeId = ?";
        PreparedStatement preparedStatement = HelloApplication.connection.prepareStatement(sql);
        preparedStatement.setString(1, String.valueOf(this.getClubAdvisorId()));
        ResultSet results = preparedStatement.executeQuery();

        while(results.next()){
            dbClubAdvisorId = results.getString(1);
            System.out.println(dbClubAdvisorId);
        }

        assert dbClubAdvisorId != null;
        if(this.getClubAdvisorId() == Integer.parseInt(dbClubAdvisorId)){
            return false;
        }else{
            return true;
        }
    }
}
