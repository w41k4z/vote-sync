package ceni.system.votesync.service.pdf;

import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
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
        contentStream.beginText();
        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 20);
        contentStream.setNonStrokingColor(Color.BLUE);
        float titleWidth = PDType1Font.HELVETICA_BOLD.getStringWidth(title) / 1000 * 20;
        contentStream.newLineAtOffset((page.getMediaBox().getWidth() - titleWidth) / 2,
                page.getMediaBox().getHeight() - 170);
        contentStream.showText(title);
        contentStream.endText();
    }
}
