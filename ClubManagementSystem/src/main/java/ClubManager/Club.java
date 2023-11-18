package ClubManager;

import SystemDataValidator.ClubValidator;
import SystemUsers.ClubAdvisor;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.ArrayList;

public class Club implements ClubValidator {
    private String clubAdvisorName;
    private int clubId;
    private String clubName;
    private String clubDescription;
    private String clubLogo;

    public Club(int clubId, String clubName, String clubDescription, String clubLogo){
        this.clubId = clubId;
        this.clubName = clubName;
        this.clubDescription = clubDescription;
        this.clubLogo = clubLogo;
        setClubAdvisorName(this.clubName);
    }

    public static ArrayList<Club> clubDetailsList = new ArrayList<>();

    public int getClubId() {
        return clubId;
    }

    public void setClubId(int clubId) {
        this.clubId = clubId;
    }

    public String getClubName() {
        return clubName;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }

    public String getClubDescription() {
        return clubDescription;
    }

    public void setClubDescription(String clubDescription) {
        this.clubDescription = clubDescription;
    }

    public String getClubLogo() {
        return clubLogo;
    }

    public void setClubLogo(String clubLogo) {
        this.clubLogo = clubLogo;
    }

    public static String clubNameValidateStatus;

    public void setClubAdvisorName(String nameOfTheClub){
        for (ClubAdvisor advisor : ClubAdvisor.clubAdvisorDetailsList) {
            System.out.println("bn");
            for (Club clubName : advisor.createdClubDetailsList) {
                System.out.println("Incharge clubName ");
                if (clubName.getClubName().equals(nameOfTheClub)) {
                    this.clubAdvisorName = advisor.getFirstName() +  " " + advisor.getLastName();
                }
            }
        }

    }


    public String getClubAdvisorName() {
        return clubAdvisorName;
    }
}
