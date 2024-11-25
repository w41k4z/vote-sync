package ceni.system.votesync.controller.api;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ceni.system.votesync.service.pdf.ElectoralResultPdfGenerator;

@RequestMapping("/api/elections/results/exportation")
@RestController
public class ResultPdfExportationController {

    private ElectoralResultPdfGenerator electoralResultPdfGenerator;

    public ResultPdfExportationController(ElectoralResultPdfGenerator electoralResultPdfGenerator) {
        this.electoralResultPdfGenerator = electoralResultPdfGenerator;
    }

    @GetMapping("/polling-station")
    public ResponseEntity<ByteArrayOutputStream> pollingStationResults(@RequestParam Integer electionId,
            @RequestParam(required = false) Integer regionId,
            @RequestParam(required = false) Integer districtId,
            @RequestParam(required = false) Integer communeId,
            @RequestParam(required = false) Integer fokontanyId) throws IOException {
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=Resultat.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(this.electoralResultPdfGenerator.getPollingStationResultPdf(electionId, regionId, districtId,
                        communeId, fokontanyId));
    }

    @GetMapping("/fokontany")
    public ResponseEntity<ByteArrayOutputStream> fokontanyResults(@RequestParam Integer electionId,
            @RequestParam(required = false) Integer regionId,
            @RequestParam(required = false) Integer districtId,
            @RequestParam(required = false) Integer communeId) throws IOException {
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=Resultat.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(this.electoralResultPdfGenerator.getFokontanyResultPdf(electionId, regionId, districtId,
                        communeId));
    }

    @GetMapping("/local/polling-station")
    public ResponseEntity<ByteArrayOutputStream> pollingStationLocalElectionResults(@RequestParam Integer electionId,
            @RequestParam(required = false) Integer regionId,
            @RequestParam(required = false) Integer districtId,
            @RequestParam(required = false) Integer communeId,
            @RequestParam(required = false) Integer fokontanyId) throws IOException {
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=Resultat.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(this.electoralResultPdfGenerator.getPollingStationLocalElectionResultPdf(electionId, regionId,
                        districtId,
                        communeId, fokontanyId));
    }

    @GetMapping("/local/fokontany")
    public ResponseEntity<ByteArrayOutputStream> fokontanyLocalElectionResults(@RequestParam Integer electionId,
            @RequestParam(required = false) Integer regionId,
            @RequestParam(required = false) Integer districtId,
            @RequestParam(required = false) Integer communeId) throws IOException {
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=Resultat.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(this.electoralResultPdfGenerator.getFokontanyLocalElectionResultPdf(electionId, regionId,
                        districtId, communeId));
    }

    @GetMapping("/local/municipal")
    public ResponseEntity<ByteArrayOutputStream> municipalResults(@RequestParam Integer electionId,
            @RequestParam(required = false) Integer regionId,
            @RequestParam(required = false) Integer districtId) throws IOException {
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=Resultat.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(this.electoralResultPdfGenerator.getMunicipalResultPdf(electionId, regionId, districtId));
    }

    @GetMapping("/legislative/communal")
    public ResponseEntity<ByteArrayOutputStream> communalResults(@RequestParam Integer electionId,
            @RequestParam(required = false) Integer regionId,
            @RequestParam(required = false) Integer districtId) throws IOException {
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=Resultat.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(this.electoralResultPdfGenerator.getCommunalResultPdf(electionId, regionId, districtId));
    }

    @GetMapping("/legislative/district")
    public ResponseEntity<ByteArrayOutputStream> districtResults(@RequestParam Integer electionId,
            @RequestParam(required = false) Integer regionId) throws IOException {
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=Resultat.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(this.electoralResultPdfGenerator.getDistrictResultPdf(electionId, regionId));
    }

    @GetMapping("/presidential/regional")
    public ResponseEntity<ByteArrayOutputStream> regionalResults(@RequestParam Integer electionId) throws IOException {
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=Resultat.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(this.electoralResultPdfGenerator.getRegionalResultPdf(electionId));
    }

    @GetMapping("/presidential/provincial")
    public ResponseEntity<ByteArrayOutputStream> provincialResults(@RequestParam Integer electionId)
            throws IOException {
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=Resultat.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(this.electoralResultPdfGenerator.getProvincialResultPdf(electionId));
    }

    @GetMapping("/presidential/global")
    public ResponseEntity<ByteArrayOutputStream> globalResults(@RequestParam Integer electionId) throws IOException {
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=Resultat.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(this.electoralResultPdfGenerator.getGlobalResultPdf(electionId));
    }
}
