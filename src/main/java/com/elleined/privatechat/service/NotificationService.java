package com.elleined.privatechat.service;

import com.elleined.privatechat.dto.ResponseMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final SimpMessagingTemplate simpMessagingTemplate;

    public void sendPrivateNotification(int recipientId, ResponseMessage responseMessage) {
        simpMessagingTemplate.convertAndSendToUser(String.valueOf(recipientId), "/chat/private-notification", responseMessage);
    }
}
