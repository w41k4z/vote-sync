package ceni.system.votesync.service.entity.stat;

import java.util.List;

import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import ceni.system.votesync.model.view.election.stat.CommunalStat;
import ceni.system.votesync.model.view.election.stat.DistrictStat;
import ceni.system.votesync.model.view.election.stat.FokontanyStat;
import ceni.system.votesync.model.view.election.stat.RegionalStat;
import ceni.system.votesync.model.view.election.stat.VotersStat;
import ceni.system.votesync.model.view.election.stat.global.GlobalCommunalStat;
import ceni.system.votesync.model.view.election.stat.global.GlobalDistrictStat;
import ceni.system.votesync.model.view.election.stat.global.GlobalFokontanyStat;
import ceni.system.votesync.model.view.election.stat.global.GlobalRegionalStat;
import ceni.system.votesync.repository.view.election.stat.CommunalStatRepository;
import ceni.system.votesync.repository.view.election.stat.DistrictStatRepository;
import ceni.system.votesync.repository.view.election.stat.FokontanyStatRepository;
import ceni.system.votesync.repository.view.election.stat.RegionalStatRepository;
import ceni.system.votesync.repository.view.election.stat.VotersStatRepository;
import ceni.system.votesync.repository.view.election.stat.global.GlobalCommunalStatRepository;
import ceni.system.votesync.repository.view.election.stat.global.GlobalDistrictStatRepository;
import ceni.system.votesync.repository.view.election.stat.global.GlobalFokontanyStatRepository;
import ceni.system.votesync.repository.view.election.stat.global.GlobalRegionalStatRepository;

@Service
public class ElectionStatService {

    private VotersStatRepository votersStatRepository;
    private FokontanyStatRepository fokontanyStatRepository;
    private GlobalFokontanyStatRepository globalFokontanyStatRepository;
    private CommunalStatRepository communalStatRepository;
    private GlobalCommunalStatRepository globalCommunalStatRepository;
    private DistrictStatRepository districtStatRepository;
    private GlobalDistrictStatRepository globalDistrictStatRepository;
    private RegionalStatRepository regionalStatRepository;
    private GlobalRegionalStatRepository globalRegionalStatRepository;

    public ElectionStatService(VotersStatRepository votersStatRepository,
            FokontanyStatRepository fokontanyStatRepository,
            GlobalFokontanyStatRepository globalFokontanyStatRepository, CommunalStatRepository communalStatRepository,
            GlobalCommunalStatRepository globalCommunalStatRepository,
            DistrictStatRepository districtStatRepository, GlobalDistrictStatRepository globalDistrictStatRepository,
            RegionalStatRepository regionalStatRepository,
            GlobalRegionalStatRepository globalRegionalStatRepository) {
        this.votersStatRepository = votersStatRepository;
        this.fokontanyStatRepository = fokontanyStatRepository;
        this.globalFokontanyStatRepository = globalFokontanyStatRepository;
        this.communalStatRepository = communalStatRepository;
        this.globalCommunalStatRepository = globalCommunalStatRepository;
        this.districtStatRepository = districtStatRepository;
        this.globalDistrictStatRepository = globalDistrictStatRepository;
        this.regionalStatRepository = regionalStatRepository;
        this.globalRegionalStatRepository = globalRegionalStatRepository;
    }

    public List<VotersStat> getVotersStats() {
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

    public List<CommunalStat> getAllCommunalStat(Integer electionTypeId) {
        CommunalStat communalStatExample = new CommunalStat();
        communalStatExample.setElectionTypeId(electionTypeId);
        return this.communalStatRepository.findAll(Example.of(communalStatExample));
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

    public List<RegionalStat> getAllRegionalStat(Integer electionTypeId) {
        RegionalStat regionalStatExample = new RegionalStat();
        regionalStatExample.setElectionTypeId(electionTypeId);
        return this.regionalStatRepository.findAll(Example.of(regionalStatExample));
    }

    public List<GlobalRegionalStat> getAllGlobalRegionalStat() {
        return this.globalRegionalStatRepository.findAll();
    }
}
