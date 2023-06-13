package com.privatechat.wsprivatechatapplication.service;

import com.privatechat.wsprivatechatapplication.dto.Message;
import com.privatechat.wsprivatechatapplication.dto.ResponseMessage;
import com.privatechat.wsprivatechatapplication.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class WSService {

    private final SimpMessagingTemplate simpMessagingTemplate;
    private final UserService userService;

    public void sendPrivateMessage(Message message) throws InterruptedException {
        Thread.sleep(1000);

        String picture = userService.getByUsername(message.sender()).picture();
        var responseMessage = new ResponseMessage(message.sender(), message.body(), picture);
        simpMessagingTemplate.convertAndSendToUser(message.recipientUUID(), "/chat/private-message", responseMessage);
    }
}
