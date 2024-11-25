package ceni.system.votesync.controller.api;

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
        public ResponseEntity<byte[]> pollingStationResults(@RequestParam Integer electionId,
                        @RequestParam(required = false) Integer regionId,
                        @RequestParam(required = false) Integer districtId,
                        @RequestParam(required = false) Integer communeId,
                        @RequestParam(required = false) Integer fokontanyId) throws IOException {
                return ResponseEntity.ok()
                                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=Resultat.pdf")
                                .contentType(MediaType.APPLICATION_PDF)
                                .body(this.electoralResultPdfGenerator.getPollingStationResultPdf(electionId, regionId,
                                                districtId,
                                                communeId, fokontanyId).toByteArray());
        }

        @GetMapping("/fokontany")
        public ResponseEntity<byte[]> fokontanyResults(@RequestParam Integer electionId,
                        @RequestParam(required = false) Integer regionId,
                        @RequestParam(required = false) Integer districtId,
                        @RequestParam(required = false) Integer communeId) throws IOException {
                return ResponseEntity.ok()
                                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=Resultat.pdf")
                                .contentType(MediaType.APPLICATION_PDF)
                                .body(this.electoralResultPdfGenerator.getFokontanyResultPdf(electionId, regionId,
                                                districtId,
                                                communeId).toByteArray());
        }

        @GetMapping("/local/polling-station")
        public ResponseEntity<byte[]> pollingStationLocalElectionResults(
                        @RequestParam Integer electionId,
                        @RequestParam(required = false) Integer regionId,
                        @RequestParam(required = false) Integer districtId,
                        @RequestParam(required = false) Integer communeId,
                        @RequestParam(required = false) Integer fokontanyId) throws IOException {
                return ResponseEntity.ok()
                                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=Resultat.pdf")
                                .contentType(MediaType.APPLICATION_PDF)
                                .body(this.electoralResultPdfGenerator.getPollingStationLocalElectionResultPdf(
                                                electionId, regionId,
                                                districtId,
                                                communeId, fokontanyId).toByteArray());
        }

        @GetMapping("/local/fokontany")
        public ResponseEntity<byte[]> fokontanyLocalElectionResults(@RequestParam Integer electionId,
                        @RequestParam(required = false) Integer regionId,
                        @RequestParam(required = false) Integer districtId,
                        @RequestParam(required = false) Integer communeId) throws IOException {
                return ResponseEntity.ok()
                                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=Resultat.pdf")
                                .contentType(MediaType.APPLICATION_PDF)
                                .body(this.electoralResultPdfGenerator.getFokontanyLocalElectionResultPdf(electionId,
                                                regionId,
                                                districtId, communeId).toByteArray());
        }

        @GetMapping("/local/municipal")
        public ResponseEntity<byte[]> municipalResults(@RequestParam Integer electionId,
                        @RequestParam(required = false) Integer regionId,
                        @RequestParam(required = false) Integer districtId) throws IOException {
                return ResponseEntity.ok()
                                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=Resultat.pdf")
                                .contentType(MediaType.APPLICATION_PDF)
                                .body(this.electoralResultPdfGenerator.getMunicipalResultPdf(electionId, regionId,
                                                districtId).toByteArray());
        }

        @GetMapping("/legislative/communal")
        public ResponseEntity<byte[]> communalResults(@RequestParam Integer electionId,
                        @RequestParam(required = false) Integer regionId,
                        @RequestParam(required = false) Integer districtId) throws IOException {
                return ResponseEntity.ok()
                                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=Resultat.pdf")
                                .contentType(MediaType.APPLICATION_PDF)
                                .body(this.electoralResultPdfGenerator.getCommunalResultPdf(electionId, regionId,
                                                districtId).toByteArray());
        }

        @GetMapping("/legislative/district")
        public ResponseEntity<byte[]> districtResults(@RequestParam Integer electionId,
                        @RequestParam(required = false) Integer regionId) throws IOException {
                return ResponseEntity.ok()
                                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=Resultat.pdf")
                                .contentType(MediaType.APPLICATION_PDF)
                                .body(this.electoralResultPdfGenerator.getDistrictResultPdf(electionId, regionId)
                                                .toByteArray());
        }

        @GetMapping("/presidential/regional")
        public ResponseEntity<byte[]> regionalResults(@RequestParam Integer electionId)
                        throws IOException {
                return ResponseEntity.ok()
                                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=Resultat.pdf")
                                .contentType(MediaType.APPLICATION_PDF)
                                .body(this.electoralResultPdfGenerator.getRegionalResultPdf(electionId).toByteArray());
        }

        @GetMapping("/presidential/provincial")
        public ResponseEntity<byte[]> provincialResults(@RequestParam Integer electionId)
                        throws IOException {
                return ResponseEntity.ok()
                                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=Resultat.pdf")
                                .contentType(MediaType.APPLICATION_PDF)
                                .body(this.electoralResultPdfGenerator.getProvincialResultPdf(electionId)
                                                .toByteArray());
        }

        @GetMapping("/presidential/global")
        public ResponseEntity<byte[]> globalResults(@RequestParam Integer electionId) throws IOException {
                return ResponseEntity.ok()
                                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=Resultat.pdf")
                                .contentType(MediaType.APPLICATION_PDF)
                                .body(this.electoralResultPdfGenerator.getGlobalResultPdf(electionId).toByteArray());
        }
}
