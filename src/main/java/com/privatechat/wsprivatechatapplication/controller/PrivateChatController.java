package com.privatechat.wsprivatechatapplication.controller;

import com.privatechat.wsprivatechatapplication.dto.Message;
import com.privatechat.wsprivatechatapplication.dto.ResponseMessage;
import com.privatechat.wsprivatechatapplication.dto.UserDTO;
import com.privatechat.wsprivatechatapplication.service.UserService;
import com.privatechat.wsprivatechatapplication.service.WSService;
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

    @GetMapping("/{recipientId}")
    public String goToPrivateChat(@PathVariable("recipientId") int recipientId,
                                  HttpSession session,
                                  Model model) {

        String username = (String) session.getAttribute("username");
        if (username == null) return "redirect:/login";

        UserDTO recipientDTO = userService.getById(recipientId);
        model.addAttribute("username", username);
        model.addAttribute("recipient", recipientDTO);
        return "private-chat";
    }


    @PostMapping
    public ResponseEntity<?> sendPrivateMessage(@RequestBody Message message) throws InterruptedException {
        String body = message.body();
        if (body.isEmpty() || body.isBlank()) return ResponseEntity.badRequest().body("Please provide a message body!");

        wsService.sendPrivateMessage(message);

        String picture = userService.getByUsername(message.sender()).picture();
        return ResponseEntity.ok(new ResponseMessage(message.sender(), message.body(), picture));
    }
}
