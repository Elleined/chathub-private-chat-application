package com.privatechat.wsprivatechatapplication.controller;

import com.privatechat.wsprivatechatapplication.dto.Message;
import com.privatechat.wsprivatechatapplication.dto.ResponseMessage;
import com.privatechat.wsprivatechatapplication.service.WSService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping
@Slf4j
public class IndexController {

    private final WSService wsService;

    @PostMapping("/send-private-message/{recipientId}")
    public ResponseEntity<ResponseMessage> sendPrivateMessage(@PathVariable("recipientId") String recipientId,
                                                              @RequestBody Message message) {
        wsService.sendPrivateMessage(recipientId, message);
        return ResponseEntity.ok(new ResponseMessage(message.body()));
    }
}
