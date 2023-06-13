package com.privatechat.wsprivatechatapplication.controller;

import com.privatechat.wsprivatechatapplication.dto.Message;
import com.privatechat.wsprivatechatapplication.dto.ResponseMessage;
import com.privatechat.wsprivatechatapplication.service.WSService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/private-chat")
public class PrivateChatController {

    private final WSService wsService;

    @GetMapping
    public String goToPrivateChat(HttpSession session) {
        String username = (String) session.getAttribute("username");
        if (username == null) return "redirect:/login";
        return "private-chat";
    }


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
