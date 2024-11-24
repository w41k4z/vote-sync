package ceni.system.votesync.service.entity.alert;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PagedModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ceni.system.votesync.config.Status;
import ceni.system.votesync.dto.request.alert.UpdateAlertStatusRequest;
import ceni.system.votesync.exception.ImpossibleOperationException;
import ceni.system.votesync.model.entity.alert.Alert;
import ceni.system.votesync.model.entity.alert.AlertType;
import ceni.system.votesync.model.entity.alert.BaseAlert;
import ceni.system.votesync.model.view.VAlert;
import ceni.system.votesync.repository.entity.AlertRepository;
import ceni.system.votesync.repository.entity.BaseAlertRepository;
import ceni.system.votesync.repository.view.VAlertRepository;

@Service
public class AlertService {

    private BaseAlertRepository baseAlertRepository;
    private AlertRepository alertRepository;
    private VAlertRepository vAlertRepository;

    public AlertService(BaseAlertRepository baseAlertRepository, AlertRepository alertRepository,
            VAlertRepository vAlertRepository) {
        this.baseAlertRepository = baseAlertRepository;
        this.alertRepository = alertRepository;
        this.vAlertRepository = vAlertRepository;
    }

    public long countCurrentAlerts() {
        return this.vAlertRepository.count(AlertSpecification.nonResolvedAlert());
    }

    public PagedModel<VAlert> getCurrentAlerts(Integer electionId, Integer regionId, Integer districtId,
            Integer communeId,
            Integer fokontanyId, Pageable page) {
        Specification<VAlert> spec = AlertSpecification.filterPollingStationResult(regionId, districtId, communeId,
                fokontanyId);
        if (electionId != null) {
            spec = spec.and(AlertSpecification.withElectionId(electionId));
        }
        return new PagedModel<>(this.vAlertRepository.findAll(spec, page));
    }

    public void saveAlert(Integer alertTypeId, Integer electionId, Integer pollingStationId, Date alertDate,
            String description, Integer status) {
        BaseAlert alert = new BaseAlert();
        alert.setAlertTypeId(alertTypeId);
        alert.setElectionId(electionId);
        alert.setPollingStationId(pollingStationId);
        alert.setAlertDate(alertDate);
        alert.setDescription(description);
        alert.setStatus(status);
        this.baseAlertRepository.save(alert);
    }

    public Alert createSuspectRegistrationAlert(Integer electionId, String pollingStationCode, String description) {
        Alert newAlert = new Alert();
        newAlert.setElectionId(electionId);
        newAlert.setPollingStationCode(pollingStationCode);
        newAlert.setDescription(description);
        newAlert.setAlertTypeId(AlertType.UNSYNCED_DATA);
        newAlert.setAlertDate(Date.valueOf(LocalDate.now()));
        newAlert.setStatus(Status.PENDING);
        return newAlert;
    }

    public Alert createUnsyncedDataAlert(Integer electionId, String pollingStationCode, String description) {
        Alert newAlert = new Alert();
        newAlert.setElectionId(electionId);
        newAlert.setPollingStationCode(pollingStationCode);
        newAlert.setDescription(description);
        newAlert.setAlertTypeId(AlertType.UNSYNCED_DATA);
        newAlert.setAlertDate(Date.valueOf(LocalDate.now()));
        newAlert.setStatus(Status.PENDING);
        return newAlert;
    }

    public Alert createIncoherentDataAlert(Integer electionId, String pollingStationCode, String description) {
        Alert newAlert = new Alert();
        newAlert.setElectionId(electionId);
        newAlert.setPollingStationCode(pollingStationCode);
        newAlert.setDescription(description);
        newAlert.setAlertTypeId(AlertType.UNSYNCED_DATA);
        newAlert.setAlertDate(Date.valueOf(LocalDate.now()));
        newAlert.setStatus(Status.PENDING);
        return newAlert;
    }

    private void importAlerts() {
        this.alertRepository.importAlerts();
    }

    public void updateAlertStatus(UpdateAlertStatusRequest request) {
        if (List.of(Status.PENDING, Status.PROCESSING, Status.CLOSED).contains(request.getStatus())) {
            this.alertRepository.updateAlertStatus(request.getStatus(), request.getAlertId());
            return;
        }
        throw new ImpossibleOperationException("Invalid status for alerts.");
    }

    @Transactional
    public void saveAllAlerts(List<Alert> alerts) {
        this.alertRepository.saveAll(alerts);
        this.importAlerts();
        this.alertRepository.deleteAll(alerts);
    }
}
