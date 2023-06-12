package com.privatechat.wsprivatechatapplication.controller;

import com.privatechat.wsprivatechatapplication.dto.Message;
import com.privatechat.wsprivatechatapplication.dto.ResponseMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

import java.security.Principal;

@Controller
public class ChatController {

    @MessageMapping("/send-private-message")
    @SendToUser("/chat/private-message")
    public ResponseMessage sendPrivateMessage(@Payload Message message,
                                              Principal principal) {
        String responseMessage = String.format("Sending private message to user %s: %s", principal.getName(), HtmlUtils.htmlEscape(message.body()));
        return new ResponseMessage(responseMessage);
    }
}
