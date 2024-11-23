package ceni.system.votesync.service;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import ceni.system.votesync.dto.AlertBody;

@Service
public class NotificationService {

    private SimpMessagingTemplate messagingTemplate;

    public NotificationService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void sendAlert(AlertBody alertBody) {
        messagingTemplate.convertAndSend("/notification/alerts", alertBody);
    }
}
