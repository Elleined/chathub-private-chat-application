package com.privatechat.wsprivatechatapplication.service;

import com.privatechat.wsprivatechatapplication.dto.Message;
import com.privatechat.wsprivatechatapplication.dto.ResponseMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class WSService {

    private final SimpMessagingTemplate simpMessagingTemplate;

    public void sendPrivateMessage(String recipientId, Message message) throws InterruptedException {
        Thread.sleep(1000);
        var responseMessage = new ResponseMessage(message.sender(), message.body());
        simpMessagingTemplate.convertAndSendToUser(recipientId, "/chat/private-message", responseMessage);
    }
}
