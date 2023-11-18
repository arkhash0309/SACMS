package ClubManager;

import SystemUsers.ClubAdvisor;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Club {
    public static ArrayList<Club> clubDetailsList = new ArrayList<>();
    private int clubId;
    private String clubName;
    private String clubDescription;
    private String clubLogo;
    private ImageView absoluteImage;

    public Club(int clubId, String clubName, String clubDescription,String clubLogo) {
        this.clubId = clubId;
        this.clubName = clubName;
        this.clubDescription = clubDescription;
        this.clubLogo = clubLogo;
        setAbsoluteImage(clubLogo);
    }
    public Club(int clubId, String clubName, String clubDescription) {
        this.clubId = clubId;
        this.clubName = clubName;
        this.clubDescription = clubDescription;
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
        //This checks whether the image path is null
        if (path != null){
            double width = 100; //Set the image width
            double height = 100; //Set the image height

            //This sets the image path to the Image Object
            Image image = new Image(path);

            //Assigning the Image View to the absolute image
            this.absoluteImage = new ImageView(image);

            //Set the absolute image width to 120, height to 90
            absoluteImage.setFitWidth(width);
            absoluteImage.setFitHeight(height);
        }else {
            //if image path is null absolute image will be set as null
            this.absoluteImage = null;
        }
    }

    public static String clubNameValidateStatus;
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

    {
        clubNameValidateStatus = "";
        clubDescriptionValidateStatus ="";
    }
}
