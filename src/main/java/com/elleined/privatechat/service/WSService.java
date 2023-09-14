package com.elleined.privatechat.service;

import com.elleined.privatechat.dto.Message;
import com.elleined.privatechat.dto.ResponseMessage;
import com.elleined.privatechat.dto.UserDTO;
import com.elleined.privatechat.model.MessageStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class WSService {

    private final SimpMessagingTemplate simpMessagingTemplate;
    private final NotificationService notificationService;
    private final UserService userService;

    public ResponseMessage sendPrivateMessage(Message message, MessageStatus messageStatus) {
        UserDTO sender = userService.getByUsername(message.senderUsername());
        String senderPicture = sender.picture();
        int senderId = sender.id();
        var notificationMessage = "You receive a new message from " + sender.username();
        var responseMessage = new ResponseMessage(senderId, message.senderUsername(), message.body(), senderPicture, notificationMessage, 1, messageStatus.getId(), messageStatus.getStatus().name());

        simpMessagingTemplate.convertAndSendToUser(String.valueOf(message.recipientId()), "/chat/private-message", responseMessage);
        notificationService.sendPrivateNotification(message.recipientId(), responseMessage);
        return responseMessage;
    }
}
