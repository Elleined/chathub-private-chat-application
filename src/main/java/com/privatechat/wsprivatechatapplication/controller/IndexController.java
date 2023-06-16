package com.privatechat.wsprivatechatapplication.controller;

import com.privatechat.wsprivatechatapplication.dto.UserDTO;
import com.privatechat.wsprivatechatapplication.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@Slf4j
@RequestMapping
@RequiredArgsConstructor
public class IndexController {

    private final UserService userService;

    @GetMapping
    public String goToIndex(HttpSession session,
                            Model model) {

        String username = (String) session.getAttribute("username");
        if (username == null) return "redirect:/login";

        int id = userService.getByUsername(username).id();
        List<UserDTO> users = userService.getAllExceptCurrentUser(id);
        model.addAttribute("users", users);
        return "index";
    }

    @GetMapping
    public String logout(HttpSession session) {

        String username = (String) session.getAttribute("username");
        session.invalidate();

        log.debug("{} logout successfully!", username);
        return "redirect:/login";
    }
}
