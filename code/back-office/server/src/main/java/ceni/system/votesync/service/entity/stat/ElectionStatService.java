package ceni.system.votesync.service.entity.stat;

import java.util.List;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.stereotype.Service;

import ceni.system.votesync.model.entity.election.result.provisional.ProvisionalCommunalResult;
import ceni.system.votesync.model.entity.election.result.provisional.ProvisionalDistrictResult;
import ceni.system.votesync.model.entity.election.result.provisional.ProvisionalFokontanyResult;
import ceni.system.votesync.model.entity.election.result.provisional.ProvisionalRegionalResult;
import ceni.system.votesync.model.view.election.stat.CommunalStat;
import ceni.system.votesync.model.view.election.stat.DistrictStat;
import ceni.system.votesync.model.view.election.stat.FokontanyStat;
import ceni.system.votesync.model.view.election.stat.RegionalStat;
import ceni.system.votesync.model.view.election.stat.ElectionVotersStat;
import ceni.system.votesync.model.view.election.stat.global.GlobalCommunalStat;
import ceni.system.votesync.model.view.election.stat.global.GlobalDistrictStat;
import ceni.system.votesync.model.view.election.stat.global.GlobalFokontanyStat;
import ceni.system.votesync.model.view.election.stat.global.GlobalRegionalStat;
import ceni.system.votesync.repository.entity.election.result.provisional.ProvisionalCommunalResultRepository;
import ceni.system.votesync.repository.entity.election.result.provisional.ProvisionalDistrictResultRepository;
import ceni.system.votesync.repository.entity.election.result.provisional.ProvisionalFokontanyResultRepository;
import ceni.system.votesync.repository.entity.election.result.provisional.ProvisionalRegionalResultRepository;
import ceni.system.votesync.repository.view.election.stat.CommunalStatRepository;
import ceni.system.votesync.repository.view.election.stat.DistrictStatRepository;
import ceni.system.votesync.repository.view.election.stat.FokontanyStatRepository;
import ceni.system.votesync.repository.view.election.stat.RegionalStatRepository;
import ceni.system.votesync.repository.view.election.stat.ElectionVotersStatRepository;
import ceni.system.votesync.repository.view.election.stat.global.GlobalCommunalStatRepository;
import ceni.system.votesync.repository.view.election.stat.global.GlobalDistrictStatRepository;
import ceni.system.votesync.repository.view.election.stat.global.GlobalFokontanyStatRepository;
import ceni.system.votesync.repository.view.election.stat.global.GlobalRegionalStatRepository;

@Service
public class ElectionStatService {

    private ElectionVotersStatRepository votersStatRepository;
    private FokontanyStatRepository fokontanyStatRepository;
    private GlobalFokontanyStatRepository globalFokontanyStatRepository;
    private CommunalStatRepository communalStatRepository;
    private GlobalCommunalStatRepository globalCommunalStatRepository;
    private DistrictStatRepository districtStatRepository;
    private GlobalDistrictStatRepository globalDistrictStatRepository;
    private RegionalStatRepository regionalStatRepository;
    private GlobalRegionalStatRepository globalRegionalStatRepository;

    private ProvisionalFokontanyResultRepository provisionalFokontanyResultRepository;
    private ProvisionalCommunalResultRepository provisionalCommunalResultRepository;
    private ProvisionalDistrictResultRepository provisionalDistrictResultRepository;
    private ProvisionalRegionalResultRepository provisionalRegionalResultRepository;

    public ElectionStatService(ElectionVotersStatRepository votersStatRepository,
            FokontanyStatRepository fokontanyStatRepository,
            GlobalFokontanyStatRepository globalFokontanyStatRepository, CommunalStatRepository communalStatRepository,
            GlobalCommunalStatRepository globalCommunalStatRepository,
            DistrictStatRepository districtStatRepository, GlobalDistrictStatRepository globalDistrictStatRepository,
            RegionalStatRepository regionalStatRepository,
            GlobalRegionalStatRepository globalRegionalStatRepository,
            ProvisionalFokontanyResultRepository provisionalFokontanyResultRepository,
            ProvisionalCommunalResultRepository provisionalCommunalResultRepository,
            ProvisionalDistrictResultRepository provisionalDistrictResultRepository,
            ProvisionalRegionalResultRepository provisionalRegionalResultRepository) {
        this.votersStatRepository = votersStatRepository;
        this.fokontanyStatRepository = fokontanyStatRepository;
        this.globalFokontanyStatRepository = globalFokontanyStatRepository;
        this.communalStatRepository = communalStatRepository;
        this.globalCommunalStatRepository = globalCommunalStatRepository;
        this.districtStatRepository = districtStatRepository;
        this.globalDistrictStatRepository = globalDistrictStatRepository;
        this.regionalStatRepository = regionalStatRepository;
        this.globalRegionalStatRepository = globalRegionalStatRepository;

        this.provisionalFokontanyResultRepository = provisionalFokontanyResultRepository;
        this.provisionalCommunalResultRepository = provisionalCommunalResultRepository;
        this.provisionalDistrictResultRepository = provisionalDistrictResultRepository;
        this.provisionalRegionalResultRepository = provisionalRegionalResultRepository;
    }

    public List<ElectionVotersStat> getVotersStats() {
        return this.votersStatRepository.findAll();
    }

    public List<FokontanyStat> getAllFokontanyStat(Integer electionTypeId) {
        FokontanyStat fokontanyStatExample = new FokontanyStat();
        fokontanyStatExample.setElectionTypeId(electionTypeId);
        return this.fokontanyStatRepository.findAll(Example.of(fokontanyStatExample));
    }

    public List<GlobalFokontanyStat> getAllGlobalFokontanyStat() {
        return this.globalFokontanyStatRepository.findAll();
    }

    public PagedModel<ProvisionalFokontanyResult> getFokontanyStatDetails(Integer divisionId, Integer electionTypeId,
            Pageable pageable) {
        if (electionTypeId == null) {
            return new PagedModel<>(this.provisionalFokontanyResultRepository.getDetails(divisionId, pageable));
        } else {
            return new PagedModel<>(this.provisionalFokontanyResultRepository.getDetailsWithElectionTypeId(divisionId,
                    electionTypeId, pageable));
        }
    }

    public List<CommunalStat> getAllCommunalStat(Integer electionTypeId) {
        CommunalStat communalStatExample = new CommunalStat();
        communalStatExample.setElectionTypeId(electionTypeId);
        return this.communalStatRepository.findAll(Example.of(communalStatExample));
    }

    public PagedModel<ProvisionalCommunalResult> getCommunalStatDetails(Integer divisionId, Integer electionTypeId,
            Pageable pageable) {
        if (electionTypeId == null) {
            return new PagedModel<>(this.provisionalCommunalResultRepository.getDetails(divisionId, pageable));
        } else {
            return new PagedModel<>(this.provisionalCommunalResultRepository.getDetailsWithElectionTypeId(divisionId,
                    electionTypeId, pageable));
        }
    }

    public List<GlobalCommunalStat> getAllGlobalCommunalStat() {
        return this.globalCommunalStatRepository.findAll();
    }

    public List<DistrictStat> getAllDistrictStat(Integer electionTypeId) {
        DistrictStat districtStatExample = new DistrictStat();
        districtStatExample.setElectionTypeId(electionTypeId);
        return this.districtStatRepository.findAll(Example.of(districtStatExample));
    }

    public List<GlobalDistrictStat> getAllGlobalDistrictStat() {
        return this.globalDistrictStatRepository.findAll();
    }

    public PagedModel<ProvisionalDistrictResult> getDistrictStatDetails(Integer divisionId, Integer electionTypeId,
            Pageable pageable) {
        if (electionTypeId == null) {
            return new PagedModel<>(this.provisionalDistrictResultRepository.getDetails(divisionId, pageable));
        } else {
            return new PagedModel<>(this.provisionalDistrictResultRepository.getDetailsWithElectionTypeId(divisionId,
                    electionTypeId, pageable));
        }
    }

    public List<RegionalStat> getAllRegionalStat(Integer electionTypeId) {
        RegionalStat regionalStatExample = new RegionalStat();
        regionalStatExample.setElectionTypeId(electionTypeId);
        return this.regionalStatRepository.findAll(Example.of(regionalStatExample));
    }

    public List<GlobalRegionalStat> getAllGlobalRegionalStat() {
        return this.globalRegionalStatRepository.findAll();
    }

    public PagedModel<ProvisionalRegionalResult> getRegionalStatDetails(Integer divisionId, Integer electionTypeId,
            Pageable pageable) {
        if (electionTypeId == null) {
            return new PagedModel<>(this.provisionalRegionalResultRepository.getDetails(divisionId, pageable));
        } else {
            return new PagedModel<>(this.provisionalRegionalResultRepository.getDetailsWithElectionTypeId(divisionId,
                    electionTypeId, pageable));
        }
    }
}
