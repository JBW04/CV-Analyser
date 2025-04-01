package org.example;

import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.HashMap;
import java.util.Map;

/**
 * Team: Soya Bean
 * SID: 2328441
 */

public class CVAnalyser {
    // The Regex patterns for contact info extraction
    Pattern NAME_PATTERN = Pattern.compile("(?i)(name|full name)[: ]+([A-Z][a-z]+(?: [A-Z][a-z]+)+)");
    Pattern PHONE_PATTERN = Pattern.compile("(\\+?\\d[\\d\\s-]{9,}\\d)");
    Pattern EMAIL_PATTERN = Pattern.compile("([a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,})");

    // Skills Mapping
    private static final Map<String, List<String>> SKILL_SYNONYMS = new HashMap<>();
    static {
        SKILL_SYNONYMS.put("java", List.of("java", "j2ee", "java ee"));
        SKILL_SYNONYMS.put("python", List.of("python", "py"));
        SKILL_SYNONYMS.put("machine learning", List.of("machine learning", "ml", "ai"));
        SKILL_SYNONYMS.put("sql", List.of("sql", "mysql", "postgresql"));
        SKILL_SYNONYMS.put("javascript", List.of("javascript", "js"));
    }

    // Enhanced contact info for the extraction with name detection
    public void extractContactInfo(String text, CV cv) {
        // This will extract the name
        Matcher nameMatcher= NAME_PATTERN.matcher(text);
        if (nameMatcher.find()){
            cv.setName(nameMatcher.group(2));
        }

        // This will extract the email
        Matcher emailMatcher = EMAIL_PATTERN.matcher(text);
        if(emailMatcher.find()){
            cv.setEmail(emailMatcher.group());
        }

        // This will extract the phone number
        Matcher phoneMatcher = PHONE_PATTERN.matcher(text);
        if(phoneMatcher.find()){
            cv.setPhone(phoneMatcher.group());
        }
    }

    // public function that will calculate match score between the cv and job description
    public double calculateMatchScore(CV cv, JobDescription jobDesc){
        double score = 0;
        int totalPossible = 0;

        // Checking the required skills (50% of score)
        totalPossible += jobDesc.getRequiredSkills().size();
        for (String reqSkill: jobDesc.getRequiredSkills()){
            if (containSkill(cv.getSkills(), reqSkill)){
                score += 1;
            }
        }

        // Checks for qualifications (30% of score)
        totalPossible += jobDesc.getRequiredQualifications().size();
        for (String reqQual : jobDesc.getRequiredQualifications()) {
            if (containsKeyword(cv.getEducation(), reqQual)) {
                score += 0.6;
            }
        }

        // Checks the preferred experience (20% of score)
        totalPossible += jobDesc.getPreferredExperience().size();
        for (String prefExp : jobDesc.getPreferredExperience()) {
            if (containsKeyword(cv.getExperience(), prefExp)) {
                score += 0.4;
            }
        }

        // Calculate percentage score
        if (totalPossible > 0){
            double normalizedScore = (score / totalPossible) * 100;
            return Math.min(normalizedScore, 100);
        }
        return 0;
    }

    // Keyword extraction with basic NLP
    public void extractKeywords(String text, CV cv) {
        // First remove all bullet points and symbols
        String cleanedText = text.replaceAll("[●&()\\-]", " ");

        // Then process as before
        String lowerText = cleanedText.toLowerCase();

        // Extract sections for Education, Experience, and Skills
        extractSection(lowerText, "education", cv.getEducation(), cv);
        extractSection(lowerText, "experience", cv.getExperience(), cv);
        extractSection(lowerText, "skills", cv.getSkills(), cv);

        // Process skill synonyms
        for (Map.Entry<String, List<String>> entry : SKILL_SYNONYMS.entrySet()) {
            for (String synonym : entry.getValue()) {
                if (lowerText.contains(synonym)) {
                    if (!cv.getSkills().contains(entry.getKey())) {
                        cv.addSkill(entry.getKey());
                    }
                    break;
                }
            }
        }

    }

    public void extractSection(String text, String sectionName, List<String> items, CV cv) {
        // Adjust regex to better capture the content for each section, ensuring no overlap
        Pattern pattern = Pattern.compile(
                "(?i)" + Pattern.quote(sectionName) + "[\\s:]*\\n([\\s\\S]+?)(?=\\n\\s*[A-Z]{2,}|\\n\\s*[-•]|\\n\\s*\\n|$)",
                Pattern.DOTALL
        );

        Matcher matcher = pattern.matcher(text);
        if (matcher.find()) {
            String sectionContent = matcher.group(1).trim();
            //System.out.println("Found " + sectionName + " content: " + sectionContent);  // Debugging line

            // Split by line breaks and process each entry
            String[] entries = sectionContent.split("\\n\\s*");
            for (String entry : entries) {
                String cleanedEntry = entry.replaceAll("^[•\\s]*", "").trim();

                // If the entry looks like it belongs in the section, add it
                if (!cleanedEntry.isEmpty()) {
                    if (sectionName.equalsIgnoreCase("education")) {
                        cv.addEducation(cleanedEntry);  // Add to Education section
                    } else if (sectionName.equalsIgnoreCase("experience")) {
                        cv.addExperience(cleanedEntry);  // Add to Experience section
                    }
                    items.add(cleanedEntry);  // Add to the list for this section as well
                }
            }
        } else {
            System.out.println("No " + sectionName + " content found.");  // Debugging line
        }
    }


    private boolean containSkill(List<String> skills, String targetSkill) {
        String targetLower = targetSkill.toLowerCase();
        for (String skill : skills) {
            if (skill.toLowerCase().contains(targetLower)) {
                return true;
            }
            // Checks for synonyms
            List<String> synonyms = SKILL_SYNONYMS.get(targetLower);
            if (synonyms != null) {
                for (String synonym : synonyms) {
                    if (skill.toLowerCase().contains(synonym.toLowerCase())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean containsKeyword(List<String> fields, String keyword) {
        String keywordLower = keyword.toLowerCase();
        for (String field : fields) {
            if (field.toLowerCase().contains(keywordLower)){
                return true;
            }
        }
        return false;
    }
}
