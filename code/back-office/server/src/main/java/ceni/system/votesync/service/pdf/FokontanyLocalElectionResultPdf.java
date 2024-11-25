package ceni.system.votesync.service.pdf;

import java.io.ByteArrayOutputStream;
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

import ceni.system.votesync.model.entity.election.result.provisional.ProvisionalFokontanyResult;
import ceni.system.votesync.model.entity.election.result.provisional.details.ProvisionalFokontanyResultDetails;
import ceni.system.votesync.service.FileStorageService;
import ceni.system.votesync.service.NumberStringFormatter;

@Service
public class FokontanyLocalElectionResultPdf extends ResultPdf {

        private FileStorageService fileStorageService;

        public FokontanyLocalElectionResultPdf(FileStorageService fileStorageService) {
                this.fileStorageService = fileStorageService;
        }

        public ByteArrayOutputStream generate(List<ProvisionalFokontanyResult> results) throws IOException {
                try (PDDocument document = new PDDocument(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {

                        for (ProvisionalFokontanyResult result : results) {
                                // Create an A4 page
                                String documentTitle = "VOKATRA VONJIMAIKA AMIN'NY FIFIDIANANA: "
                                                + result.getElection();
                                PDPage page = new PDPage(PDRectangle.A4);
                                document.addPage(page);
                                try (PDPageContentStream contentStream = new PDPageContentStream(document, page,
                                                AppendMode.APPEND,
                                                true)) {
                                        this.addHeaderLogo(contentStream, document, page);
                                        this.addTitle(documentTitle, contentStream, page);
                                        this.addResultDetailsSection(contentStream, page, result);
                                        float tableStartY = page.getMediaBox().getHeight() - 280;
                                        this.addTableDetails(document, page, contentStream, tableStartY, result);
                                }
                        }

                        document.save(out);
                        return out;
                }
        }

        private void addResultDetailsSection(PDPageContentStream contentStream, PDPage page,
                        ProvisionalFokontanyResult result) throws IOException {
                float fontSize = 9;

                Integer registeredVoters = result.getMaleUnder36() + result.getFemaleUnder36()
                                + result.getMale36AndOver() + result.getFemale36AndOver();

                float columnStartY = page.getMediaBox().getHeight() - 175;
                float leftColumnX = 50;
                float rightColumnX = page.getMediaBox().getWidth() - 300;
                float subColumnXOffset = 120;

                // First line
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA, fontSize);
                contentStream.setNonStrokingColor(Color.BLACK);
                contentStream.newLineAtOffset(leftColumnX, columnStartY);
                contentStream.showText("Faritra: " + result.getRegion());
                contentStream.endText();
                //
                contentStream.beginText();
                contentStream.newLineAtOffset(rightColumnX, columnStartY);
                contentStream.showText("Voasoratra anarana:");
                contentStream.endText();
                //
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, fontSize);
                contentStream.newLineAtOffset(rightColumnX + subColumnXOffset, columnStartY);
                contentStream.showText(NumberStringFormatter.formatNumberWithSpaces(
                                result.getVoters()));
                contentStream.endText();

                columnStartY -= 15;
                // Second line
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA, fontSize);
                contentStream.setNonStrokingColor(Color.BLACK);
                contentStream.newLineAtOffset(leftColumnX, columnStartY);
                contentStream.showText("Distrika: " + result.getMunicipalityDistrict());
                contentStream.endText();
                //
                contentStream.beginText();
                contentStream.newLineAtOffset(rightColumnX, columnStartY);
                contentStream.showText("Vato maty:");
                contentStream.endText();
                //
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, fontSize);
                contentStream.newLineAtOffset(rightColumnX + subColumnXOffset, columnStartY);
                contentStream.showText(NumberStringFormatter.formatNumberWithSpaces(
                                result.getNullVotes()));
                contentStream.endText();
                //
                Double nullVotesPercentage = (result.getNullVotes().doubleValue() / registeredVoters) * 100;
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, fontSize);
                contentStream.newLineAtOffset(rightColumnX + subColumnXOffset + 50, columnStartY);
                contentStream.showText("Soit: " + String.format("%.2f", nullVotesPercentage) + "%");
                contentStream.endText();

                columnStartY -= 15;
                // Third line
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA, fontSize);
                contentStream.setNonStrokingColor(Color.BLACK);
                contentStream.newLineAtOffset(leftColumnX, columnStartY);
                contentStream.showText("Kaominina: " + result.getMunicipality());
                contentStream.endText();
                //
                contentStream.beginText();
                contentStream.newLineAtOffset(rightColumnX, columnStartY);
                contentStream.showText("Vato fotsy:");
                contentStream.endText();
                //
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, fontSize);
                contentStream.newLineAtOffset(rightColumnX + subColumnXOffset, columnStartY);
                contentStream.showText(NumberStringFormatter.formatNumberWithSpaces(result.getBlankVotes()));
                contentStream.endText();
                //
                Double blankVotesPercentage = (result.getBlankVotes().doubleValue() / registeredVoters) * 100;
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, fontSize);
                contentStream.newLineAtOffset(rightColumnX + subColumnXOffset + 50, columnStartY);
                contentStream.showText("Soit: " + String.format("%.2f", blankVotesPercentage) + "%");
                contentStream.endText();

                columnStartY -= 20;
                // Fourth line
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, fontSize);
                contentStream.setNonStrokingColor(Color.BLACK);
                contentStream.newLineAtOffset(leftColumnX, columnStartY);
                contentStream.showText("Fokontany: " + result.getName());
                contentStream.endText();
                //
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA, fontSize);
                contentStream.newLineAtOffset(rightColumnX, columnStartY + 5);
                contentStream.showText("Vato manan-kery:");
                contentStream.endText();
                //
                Integer validVotes = registeredVoters - result.getNullVotes()
                                - result.getBlankVotes();
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, fontSize);
                contentStream.newLineAtOffset(rightColumnX + subColumnXOffset, columnStartY + 5);
                contentStream.showText(NumberStringFormatter.formatNumberWithSpaces(validVotes));
                contentStream.endText();
                //
                Double validVotesPercentage = (validVotes.doubleValue() / registeredVoters) * 100;
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, fontSize);
                contentStream.newLineAtOffset(rightColumnX + subColumnXOffset + 50, columnStartY + 5);
                contentStream.showText("Soit: " + String.format("%.2f", validVotesPercentage) + "%");
                contentStream.endText();

                columnStartY -= 10;
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA, fontSize);
                contentStream.newLineAtOffset(rightColumnX, columnStartY);
                contentStream.showText("Tonga nifidy:");
                contentStream.endText();
                //
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, fontSize);
                contentStream.newLineAtOffset(rightColumnX + subColumnXOffset, columnStartY);
                contentStream.showText(NumberStringFormatter.formatNumberWithSpaces(registeredVoters));
                contentStream.endText();

                columnStartY -= 25;
                // Participation Rate
                Double participationRate = (registeredVoters.doubleValue() / result.getVoters()) * 100;
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, fontSize + 1);
                contentStream.setNonStrokingColor(Color.BLACK);
                contentStream.newLineAtOffset(leftColumnX, columnStartY);
                contentStream.showText("Taux de participation: " + String.format("%.2f", participationRate) + "%");
                contentStream.endText();
        }

        private void addTableDetails(PDDocument document, PDPage page, PDPageContentStream contentStream,
                        float startY, ProvisionalFokontanyResult result) throws IOException {
                // A4 page dimensions and margins
                float pageWidth = page.getMediaBox().getWidth();
                float margin = 50; // Left and right margin
                float tableWidth = pageWidth - 2 * margin;
                float fontSize = 9;

                // Column width proportions
                float[] columnRatios = { 0.15f, 0.45f, 0.25f, 0.15f };
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
                // Draw headers
                String[] headers = { "Numéro", "Candidat", "Voix", "%" };
                for (int i = 0; i < headers.length; i++) {
                        contentStream.setNonStrokingColor(headerBackgroundColor);
                        contentStream.addRect(startX, nextY, columnWidths[i], headerCellHeight);
                        contentStream.fill();

                        // Calculate text position for centering
                        String headerText = headers[i];
                        float textWidth = PDType1Font.HELVETICA_BOLD.getStringWidth(headerText) / 1000 * (fontSize + 2);
                        float textX = startX + (columnWidths[i] - textWidth) / 2;
                        float textY = nextY + (headerCellHeight / 2) - (fontSize / 2);

                        // Draw header text
                        contentStream.beginText();
                        contentStream.setFont(PDType1Font.HELVETICA_BOLD, fontSize + 2);
                        contentStream.setNonStrokingColor(Color.WHITE);
                        contentStream.newLineAtOffset(textX, textY);
                        contentStream.showText(headerText);
                        contentStream.endText();

                        startX += columnWidths[i];
                }

                startX = margin;
                cursorY = nextY;

                Integer registeredVoters = result.getMaleUnder36()
                                + result.getFemaleUnder36()
                                + result.getMale36AndOver() + result.getFemale36AndOver();
                Integer validVotes = registeredVoters - result.getBlankVotes()
                                - result.getNullVotes();
                for (int row = 0; row < result.getDetails().size(); row++) {
                        nextY = cursorY - cellHeight;
                        ProvisionalFokontanyResultDetails details = result.getDetails().get(row);

                        // Column 1: Numéro (centered)
                        drawCenteredText(contentStream, startX, nextY, columnWidths[0], cellHeight,
                                        details.getCandidateNumber().toString(), PDType1Font.HELVETICA_BOLD,
                                        Color.BLACK, fontSize);

                        startX += columnWidths[0];

                        // Candidate information
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
                        float textX = imageX + imageWidth + 10; // Offset from the image
                        float textY = nextY + cellHeight - 20; // Adjust to start from top
                        //
                        contentStream.beginText();
                        contentStream.setFont(PDType1Font.HELVETICA_BOLD, fontSize);
                        contentStream.setNonStrokingColor(Color.BLACK);
                        contentStream.newLineAtOffset(textX, textY);
                        contentStream.showText(
                                        details.getCandidateInformation() + "  (" + details.getPoliticalEntity() + ")");
                        contentStream.newLineAtOffset(0, -12);
                        contentStream.setFont(PDType1Font.HELVETICA, fontSize);
                        contentStream.showText(details.getPoliticalEntityDescription());
                        contentStream.endText();

                        startX += columnWidths[1];
                        // Candidate votes
                        drawRightAlignedText(contentStream, startX, nextY, columnWidths[2], cellHeight,
                                        NumberStringFormatter.formatNumberWithSpaces(details
                                                        .getCandidateVotes()),
                                        PDType1Font.HELVETICA_BOLD, Color.BLACK, fontSize);

                        startX += columnWidths[2];
                        // Candidate vote rate
                        Double rate = (details.getCandidateVotes().doubleValue() / validVotes) * 100;
                        drawRightAlignedText(contentStream, startX, nextY, columnWidths[3], cellHeight,
                                        String.format("%.2f", rate) + "%", PDType1Font.HELVETICA, Color.BLACK,
                                        fontSize);

                        startX = margin;
                        cursorY = nextY;
                }

                nextY = cursorY - cellHeight;
                startX += columnWidths[0];
                drawRightAlignedText(contentStream, startX, nextY, columnWidths[1], cellHeight,
                                "Total:",
                                PDType1Font.HELVETICA_BOLD, Color.RED, fontSize);
                startX += columnWidths[1];
                drawRightAlignedText(contentStream, startX, nextY, columnWidths[2], cellHeight,
                                NumberStringFormatter.formatNumberWithSpaces(validVotes),
                                PDType1Font.HELVETICA_BOLD, Color.BLACK, fontSize);
        }
}
