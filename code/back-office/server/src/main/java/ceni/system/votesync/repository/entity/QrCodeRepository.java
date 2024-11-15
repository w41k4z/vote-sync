package ceni.system.votesync.repository.entity;

import java.util.Optional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import ceni.system.votesync.model.entity.QrCode;
import ceni.system.votesync.repository.base.EntityRepository;

public interface QrCodeRepository extends EntityRepository<QrCode, Integer> {

    Optional<QrCode> findByContent(String content);

    @Modifying
    @Transactional
    @Query(value = "UPDATE code_qr SET date_expiration = CURRENT_DATE WHERE contenu = :content", nativeQuery = true)
    int invalidateByContent(@Param("content") String content);
}
