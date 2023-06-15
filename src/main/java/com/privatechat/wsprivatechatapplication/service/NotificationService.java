package com.privatechat.wsprivatechatapplication.service;

import com.privatechat.wsprivatechatapplication.dto.NotificationResponse;
import com.privatechat.wsprivatechatapplication.dto.ResponseMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final SimpMessagingTemplate simpMessagingTemplate;

    public void sendPrivateNotification(int recipientId) {
        var notificationResponse = new NotificationResponse(1, "You have a new message from UNKNOWN user");
        simpMessagingTemplate.convertAndSendToUser(String.valueOf(recipientId), "/chat/private-notification", notificationResponse);
    }
}
