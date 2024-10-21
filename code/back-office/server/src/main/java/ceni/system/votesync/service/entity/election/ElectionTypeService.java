package ceni.system.votesync.service.entity.election;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import ceni.system.votesync.model.entity.ElectionType;
import ceni.system.votesync.repository.entity.ElectionTypeRepository;

@Service
public class ElectionTypeService {

    private ElectionTypeRepository electionTypeRepository;

    public ElectionTypeService(ElectionTypeRepository electionTypeRepository) {
        this.electionTypeRepository = electionTypeRepository;
    }

    public List<ElectionType> getElectionTypes() {
        return this.electionTypeRepository.findAll();
    }

    public Optional<ElectionType> getElectionTypeById(Integer id) {
        return this.electionTypeRepository.findById(id);
    }
}
