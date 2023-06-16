package com.privatechat.wsprivatechatapplication.controller;

import com.privatechat.wsprivatechatapplication.dto.UserDTO;
import com.privatechat.wsprivatechatapplication.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/login")
public class LoginController {

    private final UserService userService;

    @GetMapping
    public String goToLogin(@ModelAttribute UserDTO userDTO,
                            HttpSession session) {

        String username = (String) session.getAttribute("username");
        if (username != null) return "redirect:/";

        return "login";
    }

    @PostMapping("/logout")
    public String login(@ModelAttribute UserDTO userDTO,
                        HttpSession session,
                        Model model) {

        if (!userService.isUsernameExists(userDTO.username())) {
            model.addAttribute("usernameDoesNotExists", true);
            model.addAttribute("usernameErrorMsg", "Username does not exists!");
            return "login";
        }

        log.debug("User with username of {} login successfully!", userDTO.username());
        session.setAttribute("username", userDTO.username());
        return "redirect:/";
    }
}
