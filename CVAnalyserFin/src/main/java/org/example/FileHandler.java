package org.example;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.ArrayList;

/**
 * Handles file operations including reading CVs, loading/saving job descriptions
 * Team: Soya Bean
 * SID: 2328441
 */
public class FileHandler {

    // For Linux Systems or MAC
    private String expandPath(String path) {
        if (path.startsWith("~" + File.separator)) {
            path = System.getProperty("user.home") + path.substring(1);
        }
        return path;
    }

    public String readTextFile(String filePath) throws IOException {
        filePath = expandPath(filePath);
        return new String(Files.readAllBytes(Paths.get(filePath)));
    }

    public String readPdfFile(String filePath) throws IOException {
        filePath = expandPath(filePath);
        try (PDDocument document = PDDocument.load(new File(filePath))) {
            PDFTextStripper stripper = new PDFTextStripper();
            return stripper.getText(document);
        }
    }

    public void saveJobDescription(JobDescription jobDesc, String filePath) throws IOException {
        filePath = expandPath(filePath);
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(jobDesc);
        }
    }

    public JobDescription loadJobDescription(String filePath) throws IOException, ClassNotFoundException {
        if (!filePath.endsWith(".ser")) {
            throw new IOException("Invalid file format. Expected a serialized .ser file.");
        }
        filePath = expandPath(filePath);
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            return (JobDescription) ois.readObject();
        }
    }

    public List<CV> processCvDirectory(String directoryPath, CVAnalyser analyser) throws IOException {
        directoryPath = expandPath(directoryPath);
        List<CV> cvs = new ArrayList<>();
        File dir = new File(directoryPath);

        if (!dir.exists() || !dir.isDirectory()) {
            throw new IOException("Invalid directory: " + directoryPath);
        }

        File[] files = dir.listFiles((dir1, name) -> name.toLowerCase().endsWith(".pdf") || name.toLowerCase().endsWith(".txt"));
        if (files == null || files.length == 0) {
            throw new IOException("No PDF or TXT files found in directory: " + directoryPath);
        }

        for (File file : files) {
            try {
                String content = file.getName().toLowerCase().endsWith(".pdf") ?
                        readPdfFile(file.getAbsolutePath()) :
                        readTextFile(file.getAbsolutePath());

                if (content.length() < 50) {
                    System.err.println("Skipping file with insufficient content: " + file.getName());
                    continue;
                }

                CV cv = new CV();
                cv.setName(file.getName().replaceFirst("[.][^.]+$", ""));
                analyser.extractContactInfo(content, cv);

                // Correct method call: Pass the CV object, not just the skills list
                analyser.extractKeywords(content, cv); // <-- This line is updated

                cvs.add(cv);
            } catch (IOException e) {
                System.err.println("Error processing file " + file.getName() + ": " + e.getMessage());
            }
        }

        return cvs;
    }

}
