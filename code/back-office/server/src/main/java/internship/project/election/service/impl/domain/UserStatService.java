package internship.project.election.service.impl.domain;

import java.util.List;

import org.springframework.stereotype.Service;

import internship.project.election.model.VUserStat;
import internship.project.election.repository.UserStatRepository;

@Service
public class UserStatService {

    private UserStatRepository repository;

    public UserStatService(UserStatRepository repository) {
        this.repository = repository;
    }

    public List<VUserStat> getUserStats() {
        return this.repository.findAll();
    }
}
