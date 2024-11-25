package ceni.system.votesync.service.pdf;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.springframework.stereotype.Service;

import ceni.system.votesync.service.entity.result.LegislativeResultService;
import ceni.system.votesync.service.entity.result.LocalResultService;
import ceni.system.votesync.service.entity.result.PresidentialResultService;

@Service
public class ElectoralResultPdfGenerator {
    private LocalResultService localResultService;
    private LegislativeResultService legislativeResultService;
    private PresidentialResultService presidentialResultService;

    private PollingStationResultPdf pollingStationResultPdf;
    private PollingStationLocalElectionResultPdf pollingStationLocalElectionResultPdf;
    private FokontanyResultPdf fokontanyResultPdf;
    private FokontanyLocalElectionResultPdf fokontanyLocalElectionResultPdf;
    private CommunalResultPdf communalResultPdf;
    private MunicipalResultPdf municipalResultPdf;
    private DistrictResultPdf districtResultPdf;
    private RegionalResultPdf regionalResultPdf;
    private ProvincialResultPdf provincialResultPdf;
    private GlobalResultPdf globalResultPdf;

    public ElectoralResultPdfGenerator(LocalResultService localResultService,
            LegislativeResultService legislativeResultService, PresidentialResultService presidentialResultService,
            PollingStationResultPdf pollingStationResultPdf,
            PollingStationLocalElectionResultPdf pollingStationLocalElectionResultPdf,
            FokontanyResultPdf fokontanyResultPdf,
            FokontanyLocalElectionResultPdf fokontanyLocalElectionResultPdf, CommunalResultPdf communalResultPdf,
            MunicipalResultPdf municipalResultPdf, DistrictResultPdf districtResultPdf,
            RegionalResultPdf regionalResultPdf,
            ProvincialResultPdf provincialResultPdf, GlobalResultPdf globalResultPdf) {
        this.localResultService = localResultService;
        this.legislativeResultService = legislativeResultService;
        this.presidentialResultService = presidentialResultService;
        this.pollingStationResultPdf = pollingStationResultPdf;
        this.pollingStationLocalElectionResultPdf = pollingStationLocalElectionResultPdf;
        this.fokontanyResultPdf = fokontanyResultPdf;
        this.fokontanyLocalElectionResultPdf = fokontanyLocalElectionResultPdf;
        this.communalResultPdf = communalResultPdf;
        this.municipalResultPdf = municipalResultPdf;
        this.districtResultPdf = districtResultPdf;
        this.regionalResultPdf = regionalResultPdf;
        this.provincialResultPdf = provincialResultPdf;
        this.globalResultPdf = globalResultPdf;
    }

    public ByteArrayOutputStream getPollingStationResultPdf(Integer electionId, Integer regionId, Integer districtId,
            Integer communeId,
            Integer fokontanyId) throws IOException {
        return this.pollingStationResultPdf
                .generate(this.legislativeResultService.getPollingStationResults(electionId, regionId, districtId,
                        communeId, fokontanyId));
    }

    public ByteArrayOutputStream getPollingStationLocalElectionResultPdf(Integer electionId, Integer regionId,
            Integer districtId, Integer communeId,
            Integer fokontanyId) throws IOException {
        return this.pollingStationLocalElectionResultPdf.generate(
                this.localResultService.getPollingStationLocalElectionResults(electionId, regionId, districtId,
                        communeId, fokontanyId));
    }

    public ByteArrayOutputStream getFokontanyResultPdf(Integer electionId, Integer regionId, Integer districtId,
            Integer communeId) throws IOException {
        return this.fokontanyResultPdf
                .generate(this.legislativeResultService.getFokontanyResults(electionId, regionId, districtId,
                        communeId));
    }

    public ByteArrayOutputStream getFokontanyLocalElectionResultPdf(Integer electionId, Integer regionId,
            Integer districtId, Integer communeId) throws IOException {
        return this.fokontanyLocalElectionResultPdf.generate(
                this.localResultService.getFokontanyLocalElectionResults(electionId, regionId, districtId,
                        communeId));
    }

    public ByteArrayOutputStream getCommunalResultPdf(Integer electionId, Integer regionId, Integer districtId)
            throws IOException {
        return this.communalResultPdf.generate(
                this.legislativeResultService.getCommunalResults(electionId, regionId, districtId));
    }

    public ByteArrayOutputStream getMunicipalResultPdf(Integer electionId, Integer regionId, Integer districtId)
            throws IOException {
        return this.municipalResultPdf.generate(
                this.localResultService.getMunicipalResults(electionId, regionId, districtId));
    }

    public ByteArrayOutputStream getDistrictResultPdf(Integer electionId, Integer regionId) throws IOException {
        return this.districtResultPdf.generate(
                this.legislativeResultService.getDistrictResults(electionId, regionId));
    }

    public ByteArrayOutputStream getRegionalResultPdf(Integer electionId) throws IOException {
        return this.regionalResultPdf.generate(
                this.presidentialResultService.getRegionalResults(electionId));
    }

    public ByteArrayOutputStream getProvincialResultPdf(Integer electionId) throws IOException {
        return this.provincialResultPdf.generate(
                this.presidentialResultService.getProvincialResults(electionId));
    }

    public ByteArrayOutputStream getGlobalResultPdf(Integer electionId) throws IOException {
        return this.globalResultPdf.generate(
                this.presidentialResultService.getGlobalResult(electionId));
    }
}
