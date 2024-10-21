package ceni.system.votesync.repository.entity;

import java.util.Optional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ceni.system.votesync.model.entity.User;
import ceni.system.votesync.repository.base.EntityRepository;
import org.springframework.transaction.annotation.Transactional;

public interface UserRepository extends EntityRepository<User, Integer> {

    Optional<User> findByIdentifier(String identifier);

    @Modifying
    @Transactional
    @Query(value = "UPDATE utilisateurs SET id_role = :roleId, nom = :name, prenom = :firstName, contact = :contact, mot_de_passe = :password WHERE identifiant = :identifier", nativeQuery = true)
    int updateByIdentifier(
            @Param("roleId") Integer roleId,
            @Param("name") String name,
            @Param("firstName") String firstName,
            @Param("contact") String contact,
            @Param("password") String password,
            @Param("identifier") String identifier);
}
