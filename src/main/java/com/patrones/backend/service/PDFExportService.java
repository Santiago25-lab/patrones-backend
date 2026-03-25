package com.patrones.backend.service;

import com.patrones.backend.model.CV;
import com.patrones.backend.model.Education;
import com.patrones.backend.model.Experience;
import com.patrones.backend.model.Section;
import com.patrones.backend.model.Skill;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class PDFExportService {

    public byte[] generateCVPdf(CV cv, List<Section> sections) {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            // In PDFBox 3.x, you define font using Standard14Fonts enum
            var fontTitle = new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD);
            var fontHeading = new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD);
            var fontBody = new PDType1Font(Standard14Fonts.FontName.HELVETICA);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                float margin = 50;
                float yStart = page.getMediaBox().getHeight() - margin;
                float currentY = yStart;

                // Title / Header
                contentStream.setFont(fontTitle, 24);
                contentStream.beginText();
                contentStream.newLineAtOffset(margin, currentY);
                contentStream.showText(cv.getUser().getFullName() != null ? cv.getUser().getFullName() : "CV");
                contentStream.endText();
                currentY -= 30;

                contentStream.setFont(fontBody, 12);
                contentStream.beginText();
                contentStream.newLineAtOffset(margin, currentY);
                contentStream.showText(cv.getUser().getEmail());
                contentStream.endText();
                currentY -= 40;

                // Professional Profile
                if (cv.getProfessionalProfile() != null && !cv.getProfessionalProfile().isEmpty()) {
                    currentY = printSection(contentStream, "Perfil Profesional", cv.getProfessionalProfile(), fontHeading, fontBody, margin, currentY);
                }

                // Experiences
                if (cv.getExperiences() != null && !cv.getExperiences().isEmpty()) {
                    currentY -= 20;
                    contentStream.setFont(fontHeading, 16);
                    contentStream.beginText();
                    contentStream.newLineAtOffset(margin, currentY);
                    contentStream.showText("Experiencia");
                    contentStream.endText();
                    currentY -= 20;

                    for (Experience exp : cv.getExperiences()) {
                        String expLine = exp.getPositionTitle() + " en " + exp.getCompany();
                        currentY = printLine(contentStream, expLine, fontBody, 12, margin, currentY);
                        if (exp.getDescription() != null) {
                            currentY = printLine(contentStream, exp.getDescription(), fontBody, 10, margin + 10, currentY);
                        }
                    }
                }

                // Educations
                if (cv.getEducations() != null && !cv.getEducations().isEmpty()) {
                    currentY -= 20;
                    contentStream.setFont(fontHeading, 16);
                    contentStream.beginText();
                    contentStream.newLineAtOffset(margin, currentY);
                    contentStream.showText("Educación");
                    contentStream.endText();
                    currentY -= 20;

                    for (Education edu : cv.getEducations()) {
                        String eduLine = edu.getDegree() + " en " + edu.getInstitution();
                        currentY = printLine(contentStream, eduLine, fontBody, 12, margin, currentY);
                    }
                }

                // Skills
                if (cv.getSkills() != null && !cv.getSkills().isEmpty()) {
                    currentY -= 20;
                    contentStream.setFont(fontHeading, 16);
                    contentStream.beginText();
                    contentStream.newLineAtOffset(margin, currentY);
                    contentStream.showText("Habilidades");
                    contentStream.endText();
                    currentY -= 20;

                    StringBuilder skillsStr = new StringBuilder();
                    for (Skill skill : cv.getSkills()) {
                        skillsStr.append(skill.getName()).append(" ");
                    }
                    currentY = printLine(contentStream, skillsStr.toString(), fontBody, 12, margin, currentY);
                }

                // Custom Sections
                if (sections != null && !sections.isEmpty()) {
                    for (Section sec : sections) {
                        currentY -= 20;
                        currentY = printSection(contentStream, sec.getSectionTitle(), sec.getContent(), fontHeading, fontBody, margin, currentY);
                    }
                }
            }

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            document.save(baos);
            return baos.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException("Error generating PDF", e);
        }
    }

    private float printSection(PDPageContentStream stream, String title, String body, PDType1Font hFont, PDType1Font bFont, float margin, float y) throws IOException {
        y -= 20;
        stream.setFont(hFont, 16);
        stream.beginText();
        stream.newLineAtOffset(margin, y);
        // Replace unsupported characters to prevent crash
        stream.showText(title != null ? title.replaceAll("[^\\x00-\\x7F]", "") : "");
        stream.endText();
        y -= 20;

        stream.setFont(bFont, 12);
        stream.beginText();
        stream.newLineAtOffset(margin, y);
        // Extremely simple wrapping/escaping for MVP
        stream.showText(body != null ? body.replaceAll("[^\\x00-\\x7F]", "") : "");
        stream.endText();
        y -= 10;
        return y;
    }

    private float printLine(PDPageContentStream stream, String text, PDType1Font font, int size, float x, float y) throws IOException {
        stream.setFont(font, size);
        stream.beginText();
        stream.newLineAtOffset(x, y);
        stream.showText(text != null ? text.replaceAll("[^\\x00-\\x7F]", "") : "");
        stream.endText();
        return y - (size + 5);
    }
}
