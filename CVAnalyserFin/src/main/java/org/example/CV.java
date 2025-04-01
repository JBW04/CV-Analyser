package org.example;

import java.util.List;
import java.util.ArrayList;

/**
 * Team: Soya Bean
 * SID: 2328441
 */

public class CV {
    private static final long serialVersionUID = 1L;

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

    public double getMatchScore() {
        return matchScore;
    }

    public void displayCVSummary() {
        System.out.println("\n=== Candidate Summary ===");
        System.out.printf("%-15s: %s\n", "Name", name != null ? name : "Not provided");
        System.out.printf("%-15s: %s\n", "Email", email != null ? email : "Not provided");
        System.out.printf("%-15s: %s\n", "Phone", phone != null ? phone : "Not provided");
        System.out.printf("%-15s: %.2f%%\n", "Match Score", matchScore);
    }

    public void displayFullDetails() {
        displayCVSummary();
        printSection("Skills", skills);
        printSection("Education", education);
        printSection("Experience", experience);
    }

    private void printSection(String title, List<String> items) {
        System.out.printf("\n%s (%d):\n", title, items.size());
        if (items.isEmpty()) {
            System.out.println("  No " + title.toLowerCase() + " found");
        } else {
            items.forEach(item -> System.out.println("  • " + item));
        }
    }

    @Override
    public String toString() {
        return "Resume{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", skills=" + skills +
                ", education=" + education +
                ", experience=" + experience +
                ", matchScore=" + matchScore +
                '}';
    }

}