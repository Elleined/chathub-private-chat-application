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
public class PrivateChatController {

    private final WSService wsService;

    @PostMapping("/send-private-message/{recipientId}")
    public ResponseEntity<?> sendPrivateMessage(@PathVariable("recipientId") String recipientId,
                                                @RequestBody Message message) throws InterruptedException {
        String body = message.body();
        if (recipientId.isEmpty() || recipientId.isBlank()) return ResponseEntity.badRequest().body("Please provide recipient id!!");
        if (body.isEmpty() || body.isBlank()) return ResponseEntity.badRequest().body("Please provide a message body!");

        wsService.sendPrivateMessage(recipientId, message);
        return ResponseEntity.ok(new ResponseMessage(message.body()));
    }
}
