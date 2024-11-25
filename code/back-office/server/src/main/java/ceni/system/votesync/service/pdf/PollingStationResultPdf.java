package ceni.system.votesync.service.pdf;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.PDPageContentStream.AppendMode;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import java.awt.Color;
import org.springframework.stereotype.Service;

import ceni.system.votesync.model.entity.election.result.provisional.ProvisionalPollingStationResult;
import ceni.system.votesync.model.entity.election.result.provisional.details.ProvisionalPollingStationResultDetails;
import ceni.system.votesync.service.FileStorageService;

@Service
public class PollingStationResultPdf extends ResultPdf {

    private FileStorageService fileStorageService;

    public PollingStationResultPdf(FileStorageService fileStorageService) {
        this.fileStorageService = fileStorageService;
    }

    public void generate(String destination, String documentTitle,
            List<ProvisionalPollingStationResult> pollingStationResults) throws IOException {
        try (PDDocument document = new PDDocument()) {

            for (ProvisionalPollingStationResult pollingStationResult : pollingStationResults) {
                // Create an A4 page
                PDPage page = new PDPage(PDRectangle.A4);
                document.addPage(page);
                try (PDPageContentStream contentStream = new PDPageContentStream(document, page, AppendMode.APPEND,
                        true)) {
                    this.addHeaderLogo(contentStream, document, page);
                    this.addTitle(documentTitle, contentStream, page);
                    this.addResultDetailsSection(contentStream, page, pollingStationResult);
                    float tableStartY = page.getMediaBox().getHeight() - 240;
                    this.addTableDetails(document, page, contentStream, tableStartY, pollingStationResult);
                }
            }

            document.save(destination + "Resultat.pdf");
        }
    }

    private void addResultDetailsSection(PDPageContentStream contentStream, PDPage page,
            ProvisionalPollingStationResult pollingStationResult) throws IOException {
        float fontSize = 9;

        Integer registerdVoters = pollingStationResult.getMale36AndOver() + pollingStationResult.getFemale36AndOver()
                + pollingStationResult.getMale36AndOver() + pollingStationResult.getFemale36AndOver();

        float columnStartY = page.getMediaBox().getHeight() - 175;
        float leftColumnX = 50;
        float rightColumnX = page.getMediaBox().getWidth() - 275;
        float subColumnXOffset = 120;

        // First line
        contentStream.beginText();
        contentStream.setFont(PDType1Font.HELVETICA, fontSize);
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
        contentStream.beginText();
        contentStream.newLineAtOffset(rightColumnX + subColumnXOffset, columnStartY);
        contentStream.showText(pollingStationResult.getVoters().toString());
        contentStream.endText();

        columnStartY -= 15;
        // Second line
        contentStream.beginText();
        contentStream.setFont(PDType1Font.HELVETICA, fontSize);
        contentStream.setNonStrokingColor(Color.BLACK);
        contentStream.newLineAtOffset(leftColumnX, columnStartY);
        contentStream.showText("Distrika: " + pollingStationResult.getDistrict());
        contentStream.endText();
        //
        contentStream.beginText();
        contentStream.newLineAtOffset(rightColumnX, columnStartY);
        contentStream.showText("Vato maty:");
        contentStream.endText();
        //
        contentStream.beginText();
        contentStream.newLineAtOffset(rightColumnX + subColumnXOffset, columnStartY);
        contentStream.showText(pollingStationResult.getNullVotes().toString());
        contentStream.endText();
        //
        Double nullVotesPercentage = (pollingStationResult.getNullVotes().doubleValue() / registerdVoters) * 100;
        contentStream.beginText();
        contentStream.newLineAtOffset(rightColumnX + subColumnXOffset + 20, columnStartY);
        contentStream.showText("Soit: " + String.format("%.2f", nullVotesPercentage) + "%");
        contentStream.endText();

        columnStartY -= 15;
        // Third line
        contentStream.beginText();
        contentStream.setFont(PDType1Font.HELVETICA, fontSize);
        contentStream.setNonStrokingColor(Color.BLACK);
        contentStream.newLineAtOffset(leftColumnX, columnStartY);
        contentStream.showText("Kaominina: " + pollingStationResult.getCommune());
        contentStream.endText();
        //
        contentStream.beginText();
        contentStream.newLineAtOffset(rightColumnX, columnStartY);
        contentStream.showText("Vato fotsy:");
        contentStream.endText();
        //
        contentStream.beginText();
        contentStream.newLineAtOffset(rightColumnX + subColumnXOffset, columnStartY);
        contentStream.showText(pollingStationResult.getBlankVotes().toString());
        contentStream.endText();
        //
        Double blankVotesPercentage = (pollingStationResult.getBlankVotes().doubleValue() / registerdVoters) * 100;
        contentStream.beginText();
        contentStream.newLineAtOffset(rightColumnX + subColumnXOffset + 20, columnStartY);
        contentStream.showText("Soit: " + String.format("%.2f", blankVotesPercentage) + "%");
        contentStream.endText();

        columnStartY -= 15;
        // Fourth line
        contentStream.beginText();
        contentStream.setFont(PDType1Font.HELVETICA, fontSize);
        contentStream.setNonStrokingColor(Color.BLACK);
        contentStream.newLineAtOffset(leftColumnX, columnStartY);
        contentStream.showText("Fokontany: " + pollingStationResult.getFokontany());
        contentStream.endText();
        //
        contentStream.beginText();
        contentStream.newLineAtOffset(rightColumnX, columnStartY);
        contentStream.showText("Vato manan-kery:");
        contentStream.endText();
        //
        Integer validVotes = registerdVoters - pollingStationResult.getNullVotes()
                - pollingStationResult.getBlankVotes();
        contentStream.beginText();
        contentStream.newLineAtOffset(rightColumnX + subColumnXOffset, columnStartY);
        contentStream.showText(validVotes.toString());
        contentStream.endText();
        //
        Double validVotesPercentage = (validVotes.doubleValue() / registerdVoters) * 100;
        contentStream.beginText();
        contentStream.newLineAtOffset(rightColumnX + subColumnXOffset + 20, columnStartY);
        contentStream.showText("Soit: " + String.format("%.2f", validVotesPercentage) + "%");
        contentStream.endText();

        columnStartY -= 15;
        // Fifth line
        contentStream.beginText();
        contentStream.setFont(PDType1Font.HELVETICA, fontSize);
        contentStream.setNonStrokingColor(Color.BLACK);
        contentStream.newLineAtOffset(leftColumnX, columnStartY);
        contentStream.showText("Bureau de vote: " + pollingStationResult.getName());
        contentStream.endText();
        //
        contentStream.beginText();
        contentStream.newLineAtOffset(rightColumnX, columnStartY);
        contentStream.showText("Tonga nifidy:");
        contentStream.endText();
        //
        contentStream.beginText();
        contentStream.newLineAtOffset(rightColumnX + subColumnXOffset, columnStartY);
        contentStream.showText(registerdVoters.toString());
        contentStream.endText();
    }

    private void addTableDetails(PDDocument document, PDPage page, PDPageContentStream contentStream,
            float startY, ProvisionalPollingStationResult pollingStationResult) throws IOException {
        // A4 page dimensions and margins
        float pageWidth = page.getMediaBox().getWidth();
        float margin = 50; // Left and right margin
        float tableWidth = pageWidth - 2 * margin;
        float fontSize = 9;

        // Column width proportions
        float[] columnRatios = { 0.10f, 0.50f, 0.25f, 0.15f };
        float[] columnWidths = new float[columnRatios.length];

        for (int i = 0; i < columnRatios.length; i++) {
            columnWidths[i] = tableWidth * columnRatios[i];
        }

        float cellHeight = 45; // Adjusted to allow space for images and text
        float startX = margin;
        float cursorY = startY;

        Color headerBackgroundColor = Color.decode("#29611D");
        float nextY = cursorY - cellHeight;

        float headerCellHeight = 20;
        // Number column header
        contentStream.setNonStrokingColor(headerBackgroundColor); // Set the header background color
        contentStream.addRect(startX, nextY, columnWidths[0], headerCellHeight); // Draw the cell
        contentStream.fill();
        // Draw cell border
        contentStream.setNonStrokingColor(Color.BLACK); // Cell border color
        contentStream.addRect(startX, nextY, columnWidths[0], headerCellHeight);
        contentStream.stroke();

        contentStream.beginText();
        contentStream.setFont(PDType1Font.HELVETICA, fontSize);
        contentStream.setNonStrokingColor(Color.WHITE);
        contentStream.newLineAtOffset(startX + 5, nextY + ((headerCellHeight) / 2) - 5); // Center vertically
        contentStream.showText("Numéro");
        contentStream.endText();

        startX += columnWidths[0];
        // Candidate information header
        contentStream.setNonStrokingColor(headerBackgroundColor); // Set the header background color
        contentStream.addRect(startX, nextY, columnWidths[1], headerCellHeight); // Draw the cell
        contentStream.fill();
        // Draw cell border
        contentStream.setNonStrokingColor(Color.BLACK); // Cell border color
        contentStream.addRect(startX, nextY, columnWidths[1], headerCellHeight);
        contentStream.stroke();

        contentStream.beginText();
        contentStream.setFont(PDType1Font.HELVETICA, fontSize);
        contentStream.setNonStrokingColor(Color.WHITE);
        contentStream.newLineAtOffset(startX + 5, nextY + ((headerCellHeight) / 2) - 5); // Center vertically
        contentStream.showText("Candidat");
        contentStream.endText();

        startX += columnWidths[1];
        // Candidate votes header
        contentStream.setNonStrokingColor(headerBackgroundColor); // Set the header background color
        contentStream.addRect(startX, nextY, columnWidths[2], headerCellHeight); // Draw the cell
        contentStream.fill();
        // Draw cell border
        contentStream.setNonStrokingColor(Color.BLACK); // Cell border color
        contentStream.addRect(startX, nextY, columnWidths[2], headerCellHeight);
        contentStream.stroke();

        contentStream.beginText();
        contentStream.setFont(PDType1Font.HELVETICA, fontSize);
        contentStream.setNonStrokingColor(Color.WHITE);
        contentStream.newLineAtOffset(startX + 5, nextY + ((headerCellHeight) / 2) - 5); // Center vertically
        contentStream.showText("Voix");
        contentStream.endText();

        startX += columnWidths[2];
        // Rate header
        contentStream.setNonStrokingColor(headerBackgroundColor); // Set the header background color
        contentStream.addRect(startX, nextY, columnWidths[3], headerCellHeight); // Draw the cell
        contentStream.fill();
        // Draw cell border
        contentStream.setNonStrokingColor(Color.BLACK); // Cell border color
        contentStream.addRect(startX, nextY, columnWidths[3], headerCellHeight);
        contentStream.stroke();

        contentStream.beginText();
        contentStream.setFont(PDType1Font.HELVETICA, fontSize);
        contentStream.setNonStrokingColor(Color.WHITE);
        contentStream.newLineAtOffset(startX + 5, nextY + ((headerCellHeight) / 2) - 5); // Center vertically
        contentStream.showText(" % ");
        contentStream.endText();

        startX = margin;
        cursorY = nextY;
        for (int row = 0; row < pollingStationResult.getDetails().size(); row++) {
            nextY = cursorY - cellHeight;
            ProvisionalPollingStationResultDetails details = pollingStationResult.getDetails().get(row);

            // Candidate number cell
            contentStream.setNonStrokingColor(Color.BLACK); // Cell border color
            contentStream.addRect(startX, nextY, columnWidths[0], cellHeight);
            contentStream.stroke();
            //
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, fontSize);
            contentStream.newLineAtOffset(startX + 5, nextY + (cellHeight / 2) - 5); // Center vertically
            contentStream.showText(details.getCandidateNumber().toString());
            contentStream.endText();

            startX += columnWidths[0];
            // Candidate information
            contentStream.setNonStrokingColor(Color.BLACK); // Cell border color
            contentStream.addRect(startX, nextY, columnWidths[1], cellHeight);
            contentStream.stroke();
            //
            Path imagePath = this.fileStorageService.getRootLocation().resolve(details.getImagePath());
            PDImageXObject image = PDImageXObject.createFromFile(imagePath.toString(), document);
            // Calculate image position to center it vertically
            float imageWidth = 25;
            float imageHeight = 25;
            float imageX = startX + 5; // Slight padding inside the cell
            float imageY = nextY + (cellHeight - imageHeight) / 2; // Center vertically
            // Draw the image
            contentStream.drawImage(image, imageX, imageY, imageWidth, imageHeight);
            // Add stacked text next to the image
            float textX = imageX + imageWidth + 5; // Offset from the image
            float textY = nextY + cellHeight - 15; // Adjust to start from top
            //
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, fontSize);
            contentStream.setNonStrokingColor(Color.BLACK);
            contentStream.newLineAtOffset(textX, textY);
            contentStream.showText(details.getCandidateInformation() + "(" + details.getPoliticalEntity() + ")");
            contentStream.newLineAtOffset(0, -12);
            contentStream.showText(details.getPoliticalEntityDescription());
            contentStream.endText();

            startX += columnWidths[1];
            // Candidate votes
            contentStream.setNonStrokingColor(Color.BLACK); // Cell border color
            contentStream.addRect(startX, nextY, columnWidths[2], cellHeight);
            contentStream.stroke();
            //
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, fontSize);
            contentStream.newLineAtOffset(startX + 5, nextY + (cellHeight / 2) - 5); // Center vertically
            contentStream.showText(details.getCandidateVotes().toString());
            contentStream.endText();

            startX += columnWidths[2];
            // Candidate vote rate
            contentStream.setNonStrokingColor(Color.BLACK); // Cell border color
            contentStream.addRect(startX, nextY, columnWidths[3], cellHeight);
            contentStream.stroke();
            //
            Integer registerdVoters = pollingStationResult.getMale36AndOver()
                    + pollingStationResult.getFemale36AndOver()
                    + pollingStationResult.getMale36AndOver() + pollingStationResult.getFemale36AndOver();
            Integer validVotes = registerdVoters - pollingStationResult.getBlankVotes()
                    - pollingStationResult.getNullVotes();
            Double rate = (details.getCandidateNumber().doubleValue() / validVotes) * 100;
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, fontSize);
            contentStream.newLineAtOffset(startX + 5, nextY + (cellHeight / 2) - 5); // Center vertically
            contentStream.showText(String.format("%.2f", rate) + "%");
            contentStream.endText();

            startX = margin;
            cursorY = nextY;
        }
    }
}
