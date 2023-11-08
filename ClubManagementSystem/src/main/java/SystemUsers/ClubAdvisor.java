package SystemUsers;

public class ClubAdvisor extends User{
    private int clubAdvisorId;

    public ClubAdvisor(String userName,String password,
                       String firstName, String lastName,
                       int contactNumber, int clubAdvisorId){
        super(userName, password, firstName, lastName, contactNumber);
        this.clubAdvisorId = clubAdvisorId;
    }


    public int getClubAdvisorIdId() {
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
}
