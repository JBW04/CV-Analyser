# CV Analyser - Anglia Ruskin University Assessment Project 🎓

![Java](https://img.shields.io/badge/Java-17%2B-blue)
![Maven](https://img.shields.io/badge/Apache%20PDFBox-2.0.27-red)
![License](https://img.shields.io/badge/License-MIT-green)

## 📋 Table of Contents
- [Assessment Overview](#-assessment-overview)
- [Technical Implementation](#-technical-implementation)
- [How It Works](#-how-it-works)
- [Installation & Usage](#-installation--usage)
- [Sample Output](#-sample-output)
- [Submission Checklist](#-submission-checklist)
- [Troubleshooting](#-troubleshooting)
- [Academic Compliance](#-academic-compliance)

## 🔍 Project Overview
A Java-based console application that automates CV screening by:
- Parsing CVs in PDF/TXT/SER formats
- Extracting key candidate information
- Matching candidates against job requirements using NLP techniques
- Ranking applicants by relevance score

## 📋 Assessment Overview

### Key Details
- **Weighting**: 70% of Module Mark
- **Submission Format**: Individual CW - Report
- **Platform**: Canvas Submission
- **Feedback Release**: 15/05/2025
- **SID**: 2328441
- **Team**: Soya Bean

### Learning Outcomes Addressed
1. Problem decomposition and algorithm design
2. Understanding programming language paradigms
3. Tool selection for solution design
4. Script creation with control structures

## 🚀 Features

### CV Processing
- Extracts contact info, skills, education, experience
- Supports multiple formats:
  - PDF (via Apache PDFBox)
  - TXT (standard text parsing)
  - SER (serialized Java objects)
- Basic NLP techniques:
  - Regex pattern matching
  - Keyword extraction
  - Synonym mapping

### Job Matching
- Weighted scoring algorithm:
  - Skills (50% weight)
  - Qualifications (30%)
  - Experience (20%)
- Candidate ranking system
- Match percentage calculation

### Data Management
- Job description serialization (.job files)
- Batch CV processing
- Error handling for malformed files

## 🔧 How It Works

### Core Architecture
```mermaid
classDiagram
    class CV{
        +String name
        +String email
        +List<String> skills
        +List<String> education
        +calculateMatchScore()
    }
    class JobDescription{
        +String jobTitle
        +List<String> requiredSkills
        +saveToFile()
    }
    class CVAnalyser{
        +extractContactInfo()
        +calculateMatchScore()
        +extractKeywords()
    }
    class FileHandler{
        +readPdfFile()
        +processCVdirectory()
    }

    CV --|> Serializable
    JobDescription --|> Serializable
    CVAnalyser --> CV
    CVAnalyser --> JobDescription
    FileHandler --> CV


### Console Interaction
=== Smart CV Analyser ===
[1] Create new job description
[2] Load existing job description
Choice: 1

Enter job title: Junior Java Developer
Add required skill (ENTER to finish): Java
Add required skill: Spring Framework
Add qualification: Computer Science Degree

Enter path to CV directory: ./data/cvs
Found 3 CVs to process...

=== Analysis Results ===
=== Candidate Summary ===
Name           : Jack Wallace
Email          : jackwallace519@gmail.com
Phone          : 07740547223
Match Score    : 33.33%

Skills (5):
  • java
  • python
  • machine learning
  • javascript
  • sql

Education (2):
  • bsc in computer science, xyz university, 2019   2022
  • bsc in computer science, xyz university, 2019   2022

Experience (2):
  • software developer at abc corp, 2022   present
  • software developer at abc corp, 2022   present


Developed by Jack Wallace | SID: 2328441 | Team Soya Bean
Module: [010-2 Component Design and implementation report equivalent to 3500 words (2024 MOD003484 TRI2 F01CAM)]