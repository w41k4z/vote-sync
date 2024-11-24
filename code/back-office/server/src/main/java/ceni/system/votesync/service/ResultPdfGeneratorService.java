package ceni.system.votesync.service;

import java.io.IOException;
import java.nio.file.Path;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.PDPageContentStream.AppendMode;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.springframework.stereotype.Service;

import java.awt.Color;

@Service
public class ResultPdfGeneratorService {

    private FileStorageService fileStorageService;

    public ResultPdfGeneratorService(FileStorageService fileStorageService) {
        this.fileStorageService = fileStorageService;
    }

    public void test() {
        try (PDDocument document = new PDDocument()) {
            // Create an A4 page
            PDPage page = new PDPage(PDRectangle.A4);
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page, AppendMode.APPEND, true)) {
                // Draw the header logo
                drawHeaderLogo(contentStream, document, page);

                // Add the title
                addTitle(contentStream, page);

                // Add columns (left and right)
                String[] leftColumnTexts = { "Line 1", "Line 2", "Line 3" };
                String[] rightColumnNumbers = { "123", "456", "789" };
                addColumns(contentStream, page, leftColumnTexts, rightColumnNumbers);

                // Add the table
                float tableStartY = page.getMediaBox().getHeight() - 250; // Adjust Y position
                String[][] tableData = {
                        { "Header 1", "Header 2", "Header 3", "Header 4" },
                        { "Row 1 Col 1", "ep/TNF.png", "Row 1 Col 3", "Row 1 Col 4" },
                        { "Row 2 Col 1", "ep/IRMM.png", "Row 2 Col 3", "Row 2 Col 4" }
                };
                drawTable(document, page, contentStream, tableStartY, tableData);
            }

            // Save the document
            document.save("output.pdf");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void drawHeaderLogo(PDPageContentStream contentStream, PDDocument document, PDPage page)
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

    private void addTitle(PDPageContentStream contentStream, PDPage page) throws IOException {
        String title = "My Blue Title";

        contentStream.beginText();
        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 20);
        contentStream.setNonStrokingColor(Color.BLUE);
        float titleWidth = PDType1Font.HELVETICA_BOLD.getStringWidth(title) / 1000 * 20;
        contentStream.newLineAtOffset((page.getMediaBox().getWidth() - titleWidth) / 2,
                page.getMediaBox().getHeight() - 170);
        contentStream.showText(title);
        contentStream.endText();
    }

    private void addColumns(PDPageContentStream contentStream, PDPage page, String[] leftColumnTexts,
            String[] rightColumnNumbers) throws IOException {
        float columnStartY = page.getMediaBox().getHeight() - 200;
        float leftColumnX = 50;
        float rightColumnX = page.getMediaBox().getWidth() - 150;

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

    private void drawTable(PDDocument document, PDPage page, PDPageContentStream contentStream,
            float startY, String[][] data) throws IOException {
        // A4 page dimensions and margins
        float pageWidth = page.getMediaBox().getWidth();
        float margin = 50; // Left and right margin
        float tableWidth = pageWidth - 2 * margin;

        // Column width proportions
        float[] columnRatios = { 0.10f, 0.50f, 0.25f, 0.15f };
        float[] columnWidths = new float[columnRatios.length];

        for (int i = 0; i < columnRatios.length; i++) {
            columnWidths[i] = tableWidth * columnRatios[i];
        }

        float cellHeight = 40; // Adjusted to allow space for images and text
        float startX = margin;
        float cursorY = startY;

        // Hexadecimal color for the header background
        Color headerBackgroundColor = Color.decode("#29611D"); // Example: Hexadecimal color (Orange)

        for (int row = 0; row < data.length; row++) {
            float nextY = cursorY - cellHeight;

            for (int col = 0; col < data[row].length; col++) {
                float cellWidth = columnWidths[col];

                // Draw header background color for the first row (row 0)
                if (row == 0) {
                    contentStream.setNonStrokingColor(headerBackgroundColor); // Set the header background color
                    contentStream.addRect(startX, nextY, cellWidth, cellHeight); // Draw the cell
                    contentStream.fill(); // Fill the cell with the background color
                }

                // Draw cell border
                contentStream.setNonStrokingColor(Color.BLACK); // Cell border color
                contentStream.addRect(startX, nextY, cellWidth, cellHeight);
                contentStream.stroke();

                if (row > 0 && col == 1) { // Second column: Image + stacked text
                    Path imagePath = this.fileStorageService.getRootLocation().resolve(data[row][col]);
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

                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.HELVETICA, 10);
                    contentStream.setNonStrokingColor(Color.BLACK);
                    contentStream.newLineAtOffset(textX, textY);
                    contentStream.showText("Name: " + data[row][col + 1]); // Adjust index for name
                    contentStream.newLineAtOffset(0, -12);
                    contentStream.showText("Description: " + data[row][col + 2]); // Adjust index for description
                    contentStream.endText();
                } else {
                    // Other columns: Add normal cell content
                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.HELVETICA, 10);
                    contentStream.newLineAtOffset(startX + 5, nextY + (cellHeight / 2) - 5); // Center vertically
                    contentStream.showText(data[row][col]);
                    contentStream.endText();
                }

                startX += cellWidth;
            }

            startX = margin;
            cursorY = nextY;
        }
    }
}
