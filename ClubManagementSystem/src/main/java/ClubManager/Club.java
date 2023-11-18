package ClubManager;


import SystemUsers.ClubAdvisor;
import com.example.clubmanagementsystem.HelloApplication;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
    private ImageView absoluteImage;
    public static ArrayList<Club> clubDetailsList = new ArrayList<>();


    public Club(int clubId, String clubName, String clubDescription) {
        this.clubId = clubId;
        this.clubName = clubName;
        this.clubDescription = clubDescription;
    }

    public Club(int clubId, String clubName, String clubDescription, String clubLogo){
        this.clubId = clubId;
        this.clubName = clubName;
        this.clubDescription = clubDescription;
        this.clubLogo = clubLogo;
        setClubAdvisorName(this.clubName);
        setAbsoluteImage(clubLogo);
    }


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

    public void setImageView(String logo){
        clubLogo = String.valueOf(logo);
    }
    public ImageView getAbsoluteImage(){
        return absoluteImage;
    }
    public void setAbsoluteImage(String path){
        System.out.println("Pramuwa  : " + path);
        //This checks whether the image path is null
        if (path != null){
            double width = 120; //Set the image width
            double height = 100; //Set the image height

            //This sets the image path to the Image Object
            Image image = new Image(path);

            //Assigning the Image View to the absolute image
            this.absoluteImage = new ImageView(image);

            //Set the absolute image width to 120, height to 90
            absoluteImage.setFitWidth(width);
            absoluteImage.setFitHeight(height);
        }else {

            double width = 120;
            double height = 100;

            String imagePath = "Images/360_F_93856984_YszdhleLIiJzQG9L9pSGDCIvNu5GEWCc.jpg";
            String absolutePath = Objects.requireNonNull(HelloApplication.class.getResource(imagePath)).getPath();

            Image image = new Image(absolutePath);

            //Assigning the Image View to the absolute image
            this.absoluteImage = new ImageView(image);

            //Set the absolute image width to 120, height to 90
            absoluteImage.setFitWidth(width);
            absoluteImage.setFitHeight(height);

        }
    }

    public static String clubNameValidateStatus;

    @Override
    public boolean validateClubId() {
        return false;
    }

    public boolean validateClubName(){
        if (this.clubName.isEmpty()){
            clubNameValidateStatus = "empty";
            return false;
        }else {
            if (containsSpecialCharactersAndDigits(this.clubName)){
                clubNameValidateStatus = "format";
                return false;
            }else {
                clubNameValidateStatus = "none";
                return true;
            }
        }
    }

    public static String clubDescriptionValidateStatus;
    public boolean validateClubDescription(){
        if (this.clubDescription.isEmpty()){
            clubDescriptionValidateStatus = "empty";
            return false;
        }else {
            clubDescriptionValidateStatus = "none";
            return true;
        }
    }


    public static boolean containsSpecialCharactersAndDigits(String value){
        String patterCheck = "[\\d\\p{Punct}]";
        Pattern compiledPattern = Pattern.compile(patterCheck);
        Matcher matcher = compiledPattern.matcher(value);
        return matcher.find();
    }

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



    {
        clubNameValidateStatus = "";
        clubDescriptionValidateStatus = "";
    }







}
