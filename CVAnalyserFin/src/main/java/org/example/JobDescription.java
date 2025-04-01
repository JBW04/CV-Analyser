package org.example;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

/**
 * Team: Soya Bean
 * SID: 2328441
 */

public class JobDescription implements Serializable {
    private static final long serialVersionUID = 1L;

    private String jobTitle;
    private List<String> requiredSkills;
    private List<String> requiredQualifications;
    private List<String> preferredExperience;

    public JobDescription() {
        this.requiredSkills = new ArrayList<>();
        this.requiredQualifications = new ArrayList<>();
        this.preferredExperience = new ArrayList<>();
    }

    // Setters
    public void setJobTitle(String title) {
        this.jobTitle = title;
    }

    public void addRequiredSkill(String skill) {
        this.requiredSkills.add(skill);
    }

    public void addRequiredQualification(String qualification){
        this.requiredQualifications.add(qualification);
    }

    public void addPreferredExperience(String experience){
        this.preferredExperience.add(experience);
    }

    // Getters
    public String getJobTitle(){
        return jobTitle;
    }

    public List<String> getRequiredSkills(){
        return requiredSkills;
    }

    public  List<String> getRequiredQualifications(){
        return requiredQualifications;
    }

    public  List<String> getPreferredExperience(){
        return preferredExperience;
    }

    // Displays the job description in details
    public void displayJobDetails() {
        System.out.println("\n=== Job Description ===");
        System.out.println("Title: " + jobTitle);

        System.out.println("\nRequired Skills:");
        for (String skill : requiredSkills){
            System.out.println("- " + skill);
        }

        System.out.println("\nRequired Qualifications:");
        for (String qual : requiredQualifications) {
            System.out.println(qual);
        }

        System.out.println("\nPreferred Experience");
        for (String exp : preferredExperience) {
            System.out.println(exp);
        }
    }

    @Override
    public String toString(){
        return "JobDescription{" +
                "jobTitle='" + jobTitle + '\'' +
                ", requiredSkills=" + requiredSkills +
                ", requiredQualifications=" + requiredQualifications +
                ", preferredExperience=" + preferredExperience +
                '}';
    }


}