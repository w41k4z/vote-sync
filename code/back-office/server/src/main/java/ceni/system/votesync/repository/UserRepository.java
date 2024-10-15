package ceni.system.votesync.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;

import ceni.system.votesync.model.User;

public interface UserRepository extends EntityRepository<User, Integer> {

    Optional<User> findByIdentifier(String identifier);

    @Query(value = "UPDATE utilisateurs SET id_role = :roleId, nom = :name, prenom = :firstName, contact = :contact, mot_de_passe = :password WHERE identifiant = :identifier", nativeQuery = true)
    User updateByIdentifier(int roleId, String name, String firstName, String contact, String password,
            String identifier);
}
