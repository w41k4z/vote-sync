package ceni.system.votesync.service.entity.user;

import java.util.List;

import org.springframework.stereotype.Service;

import ceni.system.votesync.model.view.VUserStat;
import ceni.system.votesync.repository.view.VUserStatRepository;

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
