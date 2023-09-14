package com.elleined.privatechat.controller;

import com.elleined.privatechat.dto.Message;
import com.elleined.privatechat.dto.UserDTO;
import com.elleined.privatechat.service.MessageStatusService;
import com.elleined.privatechat.service.UserService;
import com.elleined.privatechat.service.WSService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/private-chat")
public class PrivateChatController {

    private final WSService wsService;
    private final UserService userService;
    private final MessageStatusService messageStatusService;

    @GetMapping("/{recipientId}")
    public String goToPrivateChat(@PathVariable("recipientId") int recipientId,
                                  HttpSession session,
                                  Model model) {

        String username = (String) session.getAttribute("username");
        if (username == null) return "redirect:/login";

        UserDTO recipientDTO = userService.getById(recipientId);
        UserDTO sender = userService.getByUsername(username);

        model.addAttribute("sender", sender);
        model.addAttribute("recipient", recipientDTO);
        return "private-chat";
    }


    @PostMapping
    public ResponseEntity<?> sendPrivateMessage(@RequestBody Message message) throws InterruptedException {
        String body = message.body();
        if (body.isEmpty() || body.isBlank()) return ResponseEntity.badRequest().body("Please provide a message body!");

        Thread.sleep(1000);

        var messageStatus = messageStatusService.save();
        var responseMessage = wsService.sendPrivateMessage(message, messageStatus);

        return ResponseEntity.ok(responseMessage);
    }
}
