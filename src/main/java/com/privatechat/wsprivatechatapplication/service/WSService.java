package com.privatechat.wsprivatechatapplication.service;

import com.privatechat.wsprivatechatapplication.dto.Message;
import com.privatechat.wsprivatechatapplication.dto.ResponseMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

@Service
@Slf4j
@RequiredArgsConstructor
public class WSService {

    private final SimpMessagingTemplate simpMessagingTemplate;

    public void sendPrivateMessage(String recipientId, Message message) {
        var responseMessage = new ResponseMessage("User with id of " + recipientId + "  recieved a message " + HtmlUtils.htmlEscape(message.body()));
        simpMessagingTemplate.convertAndSendToUser(recipientId, "/chat/private-message", responseMessage);
    }
}
