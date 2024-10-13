package ceni.system.votesync.repository.view;

import org.springframework.data.jpa.repository.JpaRepository;

import ceni.system.votesync.model.view.VUserStat;

public interface VUserStatRepository extends JpaRepository<VUserStat, Integer> {

}
