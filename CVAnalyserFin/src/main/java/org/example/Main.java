package org.example;

import java.util.Scanner;
import java.util.List;
import java.io.IOException;
import java.util.Comparator;

/**
 * Team: Soya Bean
 * SID: 2328441
 */
public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static CVAnalyser analyser = new CVAnalyser();
    private static FileHandler fileHandler = new FileHandler();
    private static JobDescription currentJobDesc = null;

    public static void main(String[] args) {
        System.out.println("=== Smart CV Analyser ===");

        while (true) {
            printMainMenu();
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    currentJobDesc = handleJobDescription();
                    break;
                case "2":
                    if (currentJobDesc == null) {
                        System.out.println("\n⚠️ Please load/create a job description first!");
                        System.out.println("Using sample job description for this analysis...");
                        currentJobDesc = createSampleJobDescription();
                    }
                    processCVs();
                    break;
                case "3":
                    exitProgram();
                    break;
                default:
                    System.out.println("❌ Invalid option, please choose again.");
            }
        }
    }

    private static void printMainMenu() {
        System.out.println("\n--- Main Menu ---");
        System.out.println("1. Load or Create Job Description");
        System.out.println("2. Process and Analyze CVs");
        System.out.println("3. Exit");
        System.out.print("Choose an option: ");
    }

    private static JobDescription handleJobDescription() {
        try {
            System.out.println("\n=== Job Description ===");
            System.out.print("Load from file? (y/n): ");
            String choice = scanner.nextLine().trim().toLowerCase();

            JobDescription jobDesc;
            if (choice.equals("y")) {
                System.out.print("Enter file path: ");
                String path = scanner.nextLine();
                jobDesc = fileHandler.loadJobDescription(path);
                System.out.println("✅ Job description loaded");
            } else {
                jobDesc = createInteractiveJobDescription();
                System.out.print("Save this? (y/n): ");
                if (scanner.nextLine().trim().equalsIgnoreCase("y")) {
                    System.out.print("Enter save path: ");
                    String savePath = scanner.nextLine();
                    fileHandler.saveJobDescription(jobDesc, savePath);
                    System.out.println("✅ Job description saved");
                }
            }
            jobDesc.displayJobDetails();
            return jobDesc;
        } catch (Exception e) {
            System.err.println("\n❌ Error: " + e.getMessage());
            return null;
        }
    }

    private static JobDescription createInteractiveJobDescription() {
        JobDescription jobDesc = new JobDescription();
        System.out.print("Enter job title: ");
        jobDesc.setJobTitle(scanner.nextLine());

        System.out.println("\nAdd Required Skills (enter blank to finish):");
        while (true) {
            System.out.print("Skill: ");
            String skill = scanner.nextLine().trim();
            if (skill.isEmpty()) break;
            jobDesc.addRequiredSkill(skill);
        }

        System.out.println("\nAdd Required Qualifications:");
        String qualification = scanner.nextLine().trim();
        if (!qualification.isEmpty()) {
            jobDesc.addRequiredQualification(qualification);
        }

        System.out.println("\nAdd Preferred Experience:");
        String experience = scanner.nextLine().trim();
        if (!experience.isEmpty()) {
            jobDesc.addPreferredExperience(experience);
        }

        return jobDesc;
    }

    private static void processCVs() {
        try {
            System.out.println("\n=== CV Processing ===");
            System.out.print("Enter CV directory path: ");
            String path = scanner.nextLine();

            List<CV> cvs = fileHandler.processCvDirectory(path, analyser);
            System.out.println("\n🔍 Processed " + cvs.size() + " CV(s)");

            if (cvs.isEmpty()) {
                System.out.println("⚠️ No valid CVs found in directory");
                return;
            }

            System.out.println("\n=== Analysis Results ===");
            cvs.forEach(cv -> {
                double score = analyser.calculateMatchScore(cv, currentJobDesc);
                cv.setMatchScore(score);
                cv.displayCVSummary();
            });

            CV topCandidate = getTopCandidate(cvs);
            System.out.println("\n🏆 Top Candidate");
            topCandidate.displayFullDetails();

        } catch (IOException e) {
            System.err.println("\n❌ File error: " + e.getMessage());
        }
    }

    private static CV getTopCandidate(List<CV> cvs) {
        return cvs.stream()
                .max(Comparator.comparingDouble(CV::getMatchScore))
                .orElseThrow(() -> new RuntimeException("No candidates found"));
    }

    private static JobDescription createSampleJobDescription() {
        JobDescription jobDesc = new JobDescription();
        jobDesc.setJobTitle("Java Developer");
        jobDesc.addRequiredSkill("Java");
        jobDesc.addRequiredSkill("OOP");
        jobDesc.addRequiredQualification("Computer Science Degree");
        return jobDesc;
    }

    private static void exitProgram() {
        System.out.println("\nExiting... Thank you for using CV Analyser!");
        scanner.close();
        System.exit(0);
    }
}