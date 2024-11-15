package ceni.system.votesync.service.entity.election;

import java.sql.Date;

import org.springframework.stereotype.Service;

import ceni.system.votesync.exception.InvalidQrCodeException;
import ceni.system.votesync.model.entity.QrCode;
import ceni.system.votesync.repository.entity.QrCodeRepository;

@Service
public class QrCodeService {

    private QrCodeRepository qrCodeRepository;

    public QrCodeService(QrCodeRepository qrCodeRepository) {
        this.qrCodeRepository = qrCodeRepository;
    }

    public void validateQrCode(String qrCode) {
        QrCode code = this.qrCodeRepository.findByContent(qrCode)
                .orElseThrow(() -> new InvalidQrCodeException("QR code not found."));
        if (code.getExpirationDate().before(new Date(System.currentTimeMillis()))) {
            throw new InvalidQrCodeException("QR code expired.");
        }
    }

    public void invalidateQrCode(String qrCode) {
        this.qrCodeRepository.invalidateByContent(qrCode);
    }
}
