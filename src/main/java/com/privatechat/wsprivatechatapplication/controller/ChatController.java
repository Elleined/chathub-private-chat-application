package com.privatechat.wsprivatechatapplication.controller;

import com.privatechat.wsprivatechatapplication.dto.Message;
import com.privatechat.wsprivatechatapplication.dto.ResponseMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

@Controller
@Slf4j
@RequiredArgsConstructor
public class ChatController {

    @MessageMapping("/private-message")
    @SendToUser("/queue/private-message")
    public ResponseMessage sendPrivateMessage(@Payload Message message) {
        return new ResponseMessage(HtmlUtils.htmlEscape(message.body()));
    }
}
