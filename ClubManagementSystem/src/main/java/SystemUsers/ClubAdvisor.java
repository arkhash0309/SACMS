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
}
