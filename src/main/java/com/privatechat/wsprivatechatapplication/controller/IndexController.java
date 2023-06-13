package com.privatechat.wsprivatechatapplication.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping
public class IndexController {

    @GetMapping
    public String goToIndex(HttpSession session) {

        String username = (String) session.getAttribute("username");
        if (username == null) return "redirect:/login";
        return "index";
    }
}
