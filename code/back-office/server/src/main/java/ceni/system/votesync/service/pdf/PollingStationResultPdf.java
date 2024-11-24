package ceni.system.votesync.service.pdf;

import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import java.awt.Color;
import org.springframework.stereotype.Service;

import ceni.system.votesync.model.entity.election.result.provisional.ProvisionalPollingStationResult;
import ceni.system.votesync.service.FileStorageService;

@Service
public class PollingStationResultPdf {

    private FileStorageService fileStorageService;

    public PollingStationResultPdf(FileStorageService fileStorageService) {
        this.fileStorageService = fileStorageService;
    }

    public void generate(String destination, String documentTitle,
            ProvisionalPollingStationResult pollingStationResult) {
    }

    private void addResultDetailsSection(PDPageContentStream contentStream, PDPage page,
            ProvisionalPollingStationResult pollingStationResult) throws IOException {
        float columnStartY = page.getMediaBox().getHeight() - 200;
        float leftColumnX = 50;
        float rightColumnX = page.getMediaBox().getWidth() - 350;
        float subColumnXOffset = 100;

        // First line
        contentStream.beginText();
        contentStream.setFont(PDType1Font.HELVETICA, 12);
        contentStream.setNonStrokingColor(Color.BLACK);
        contentStream.newLineAtOffset(leftColumnX, columnStartY);
        contentStream.showText("Faritra: " + pollingStationResult.getRegion());
        contentStream.endText();
        //
        contentStream.beginText();
        contentStream.newLineAtOffset(rightColumnX, columnStartY);
        contentStream.showText("Voasoratra anarana:");
        contentStream.endText();
        //

        for (int i = 0; i < leftColumnTexts.length; i++) {
            // Left column text
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, 12);
            contentStream.setNonStrokingColor(Color.BLACK);
            contentStream.newLineAtOffset(leftColumnX, columnStartY - i * 15);
            contentStream.showText(leftColumnTexts[i]);
            contentStream.endText(); // Ensure text block ends

            // Right column text
            contentStream.beginText();
            contentStream.newLineAtOffset(rightColumnX, columnStartY - i * 15);
            contentStream.showText(rightColumnNumbers[i]);
            contentStream.endText(); // Ensure text block ends
        }
    }
}
