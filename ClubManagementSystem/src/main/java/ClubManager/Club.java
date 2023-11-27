package ClubManager;

import SystemUsers.ClubAdvisor;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.util.ArrayList;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import SystemDataValidator.ClubValidator;

public class Club implements ClubValidator {

    private String clubAdvisorName;          //Respective Club Advisor who created the club
    private int clubId;                      //The unique identifier of the Club
    private String clubName;                 //Name of the Club
    private String clubDescription;          //Description of the Club
    private String clubLogo;                 //The file path or URL to the club's logo image
    private ImageView absoluteImage;         //An ImageView object to display the club's logo in a JavaFX application
    public static ArrayList<Club> clubDetailsList = new ArrayList<>();   //Arraylist which is used to save club details



    public Club(int clubId, String clubName, String clubDescription) {
        this.clubId = clubId;
        this.clubName = clubName;
        this.clubDescription = clubDescription;
    }

    public Club(int clubId, String clubName, String clubDescription, String clubLogo){
        this.clubId = clubId;                        // Set the unique identifier for the club
        this.clubName = clubName;                    // Set the name of the club
        this.clubDescription = clubDescription;      // Set a description of the club
        this.clubLogo = clubLogo;                    // Set the file path or URL to the club's logo image
        setClubAdvisorName(this.clubName);           // Set the club advisor name
        setAbsoluteImage(clubLogo);                  // Set the ImageView to display the club's logo in a JavaFX application
    }

    //Gets the unique identifier of the club
    public int getClubId() {
        return clubId;
    }
    //Gets the name of the club
    public String getClubName() {
        return clubName;
    }
    //Sets the name of the club
    public void setClubName(String clubName) {
        this.clubName = clubName;
    }
    //Gets the description of the club
    public String getClubDescription() {
        return clubDescription;
    }
    //Sets the brief description of the club
    public void setClubDescription(String clubDescription) {
        this.clubDescription = clubDescription;
    }
    //Gets the file path or URL to the club's logo image
    public String getClubLogo() {
        return clubLogo;
    }
    //Sets the file path or URL to the club's logo image
    public void setClubLogo(String clubLogo) {
        this.clubLogo = clubLogo;
    }
    //Gets the ImageView representing the club's logo
    public ImageView getAbsoluteImage(){
        return absoluteImage;
    }

    public void setAbsoluteImage(String path) {
        // Check whether the image path is null
        if (path != null) {
            double width = 120;      // Set the image width
            double height = 100;     // Set the image height

            // This sets the image path to the Image Object
            Image image = new Image(path);

            // Assigning the ImageView to the absolute image
            this.absoluteImage = new ImageView(image);

            // Set the absolute image width and height
            absoluteImage.setFitWidth(width);
            absoluteImage.setFitHeight(height);
        } else {
            double width = 120;
            double height = 100;

            // If the path is null, use a default image path
            String imagePath = "/Images/360_F_93856984_YszdhleLIiJzQG9L9pSGDCIvNu5GEWCc.jpg";

            // Load the image using the resource path
            Image image = new Image(Objects.requireNonNull(getClass().getResource(imagePath)).toExternalForm());

            // Assigning the ImageView to the absolute image
            this.absoluteImage = new ImageView(image);

            // Set the absolute image width and height
            absoluteImage.setFitWidth(width);
            absoluteImage.setFitHeight(height);
        }
    }

    @Override
    public boolean validateClubId() {
        return false;
    }

    //Represents the validation status for the club name
    public static String clubNameValidateStatus;
    public boolean validateClubName(){
        if (this.clubName.isEmpty()){
            // Check if the club name is empty
            clubNameValidateStatus = "empty";
            return false;
        }else {
            // Check if the club name already exists in the list
            for (Club foundClub : clubDetailsList){
                if (this.clubId != foundClub.getClubId()){
                    if (this.clubName.equals(foundClub.getClubName())){
                        clubNameValidateStatus = "exist";
                        return false;
                    }
                }
            }
            // Check if the club name contains special characters and digits
            if (containsSpecialCharactersAndDigits(this.clubName)){
                clubNameValidateStatus = "format";
                return false;
            }else {
                // Club name is valid
                clubNameValidateStatus = "none";
                return true;
            }
        }
    }

    //Represents the validation status for the club description
    public static String clubDescriptionValidateStatus;
    public boolean validateClubDescription(){
        // Check if the club description is empty
        if (this.clubDescription.isEmpty()){
            clubDescriptionValidateStatus = "empty";
            return false;
        }else {
            // Club description is valid
            clubDescriptionValidateStatus = "none";
            return true;
        }
    }

    //Checks if the given value contains special characters or digits
    public static boolean containsSpecialCharactersAndDigits(String value){
        // Define a regular expression pattern to check for digits and special characters
        String patterCheck = "[\\d\\p{Punct}]";
        // Compile the pattern into a regular expression
        Pattern compiledPattern = Pattern.compile(patterCheck);
        // Create a matcher for the given value
        Matcher matcher = compiledPattern.matcher(value);
        // Return true if the pattern is found in the value
        return matcher.find();
    }

    //Sets the club advisor name based on the provided club name
    public void setClubAdvisorName(String nameOfTheClub){
        // Iterate through the list of club advisors
        for (ClubAdvisor advisor : ClubAdvisor.clubAdvisorDetailsList) {
            // Iterate through the list of clubs created by the advisor
            for (Club clubName : advisor.createdClubDetailsList) {
                // Check if the club name matches the provided club name
                if (clubName.getClubName().equals(nameOfTheClub)) {
                    // Set the club advisor name based on the advisor's first and last name
                    this.clubAdvisorName = advisor.getFirstName() +  " " + advisor.getLastName();
                }
            }
        }
    }

    // Reset validation status variables for club name and description
    {
        clubNameValidateStatus = "";
        clubDescriptionValidateStatus = "";
    }







}
