package ceni.system.votesync.service.pdf;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import java.awt.Color;

public class ResultPdf {

    protected void addHeaderLogo(PDPageContentStream contentStream, PDDocument document, PDPage page)
            throws IOException {
        // Load header logo from resources
        PDImageXObject logo = PDImageXObject.createFromFile(
                getClass().getClassLoader().getResource("static/entete.jpg").getPath(),
                document);

        // Calculate dimensions
        float logoWidth = page.getMediaBox().getWidth() * 0.8f; // 80% of page width
        float logoHeight = logo.getHeight() * (logoWidth / logo.getWidth());
        float logoX = (page.getMediaBox().getWidth() - logoWidth) / 2; // Center horizontally
        float logoY = page.getMediaBox().getHeight() - logoHeight - 50; // Margin from top

        contentStream.drawImage(logo, logoX, logoY, logoWidth, logoHeight);
    }

    protected void addTitle(String title, PDPageContentStream contentStream, PDPage page) throws IOException {
        float pageWidth = page.getMediaBox().getWidth();
        float pageHeight = page.getMediaBox().getHeight() - 45;
        float leftMargin = 50;
        float rightMargin = 50;
        float maxWidth = pageWidth - leftMargin - rightMargin;

        // Font settings
        PDType1Font font = PDType1Font.HELVETICA_BOLD;
        int fontSize = 12;

        List<String> wrappedLines = wrapText(title, font, fontSize, maxWidth);

        // Calculate starting Y position for centering the title vertically
        float startY = pageHeight - 100; // Adjust Y position as needed
        float leading = 1.5f * fontSize; // Line spacing

        for (String line : wrappedLines) {
            // Calculate text width and X position for centering
            float textWidth = font.getStringWidth(line) / 1000 * fontSize;
            float startX = (pageWidth - textWidth) / 2;

            contentStream.beginText();
            contentStream.setFont(font, fontSize);
            contentStream.setNonStrokingColor(Color.BLUE);
            contentStream.newLineAtOffset(startX, startY);
            contentStream.showText(line);
            contentStream.endText();

            // Move to the next line
            startY -= leading;
        }
    }

    private List<String> wrapText(String text, PDFont font, int fontSize, float maxWidth) throws IOException {
        List<String> lines = new ArrayList<>();
        String[] words = text.split(" ");
        StringBuilder currentLine = new StringBuilder();

        for (String word : words) {
            String testLine = currentLine.length() == 0 ? word : currentLine + " " + word;
            float textWidth = font.getStringWidth(testLine) / 1000 * fontSize;

            if (textWidth > maxWidth) {
                // Current line exceeds maximum width, add it to lines
                lines.add(currentLine.toString());
                currentLine = new StringBuilder(word); // Start a new line
            } else {
                currentLine.append(currentLine.length() == 0 ? word : " " + word);
            }
        }

        // Add the last line if any
        if (currentLine.length() > 0) {
            lines.add(currentLine.toString());
        }

        return lines;
    }

    protected void drawCenteredText(PDPageContentStream contentStream, float x, float y,
            float width, float height, String text,
            PDFont font, Color color, float fontSize) throws IOException {
        float textWidth = font.getStringWidth(text) / 1000 * fontSize;
        float textHeight = font.getFontDescriptor().getCapHeight() / 1000 * fontSize;
        float textX = x + (width - textWidth) / 2;
        float textY = y + (height - textHeight) / 2;
        contentStream.beginText();
        contentStream.setFont(font, fontSize);
        contentStream.setNonStrokingColor(color);
        contentStream.newLineAtOffset(textX, textY);
        contentStream.showText(text);
        contentStream.endText();
    }

    // Helper to draw right-aligned text
    protected void drawRightAlignedText(PDPageContentStream contentStream, float x, float y,
            float width, float height, String text,
            PDFont font, Color color, float fontSize) throws IOException {
        float textWidth = font.getStringWidth(text) / 1000 * fontSize;
        float textX = x + width - textWidth - 5; // 5 is padding from the right
        float textY = y + (height / 2) - (fontSize / 2);
        contentStream.beginText();
        contentStream.setFont(font, fontSize);
        contentStream.setNonStrokingColor(color);
        contentStream.newLineAtOffset(textX, textY);
        contentStream.showText(text);
        contentStream.endText();
    }
}
