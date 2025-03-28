import java.util.List;
import java.util.ArrayList;

/**
 * Team: Soya Bean
 * SID: 2328441
 */

public class CV {
    private String name;
    private String email;
    private String phone;
    private List<String> skills;
    private List<String> education;
    private List<String> experience;
    private double matchScore;

    public CV() {
        this.skills = new ArrayList<>();
        this.education = new ArrayList<>();
        this.experience = new ArrayList<>();
        this.matchScore = 0.0;
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public void setPhone(String phone){
        this.phone = phone;
    }

    public void addSkill(String skill){
        this.skills.add(skill);
    }

    public void addEducation(String education){
        this.education.add(education);
    }

    public void addExperience(String experience){
        this.experience.add(experience);
    }

    public void setMatchScore(double score){
        this.matchScore = score;
    }

    // Getters
    public String getName(){
        return name;
    }

    public String getEmail(){
        return email;
    }

    public String getPhone(){
        return phone;
    }

    public List<String> getSkills(){
        return skills;
    }

    public List<String> getEducation(){
        return education;
    }

    public List<String> getExperience(){
        return experience;
    }

    // Displaying the summary of the cv
    public void displayCVSummary(){
        System.out.println("\n=== Resume Summary ===");
        System.out.println("Name: " + name);
        System.out.println("Email: " + email);
        System.out.println("Phone: " + phone);
        System.out.println("Match Score: " + matchScore);
    }

    // Displays the full cv details
    public void displayFullDetails(){
        displayCVSummary();

        System.out.println("\nSkills:");
        for (String skill: skills){
            System.out.println("- " + skill);
        }

        System.out.println("\nEducation");
        for(String edu: education){
            System.out.println("- " + edu);
        }

        System.out.println("\nExperience");
        for (String exp: experience) {
            System.out.println("- " + exp);
        }
    }

}
