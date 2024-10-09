package internship.project.election.service.domain;

import java.util.List;

import org.springframework.stereotype.Service;

import internship.project.election.model.view.VUserStat;
import internship.project.election.repository.view.VUserStatRepository;

@Service
public class UserStatService {

    private VUserStatRepository repository;

    public UserStatService(VUserStatRepository repository) {
        this.repository = repository;
    }

    public List<VUserStat> getUserStats() {
        return this.repository.findAll();
    }
}
