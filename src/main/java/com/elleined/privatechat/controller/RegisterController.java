package com.elleined.privatechat.controller;

import com.elleined.privatechat.dto.UserDTO;
import com.elleined.privatechat.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/register")
public class RegisterController {

    private final UserService userService;

    @GetMapping
    public String goToRegister(@ModelAttribute UserDTO userDTO) {
        return "register";
    }

    @PostMapping
    public String register(@Valid @ModelAttribute UserDTO userDTO,
                           BindingResult result,
                           Model model) {

        if (result.hasErrors()) return "register";
        if (userService.isUsernameExists(userDTO.username())) {
            model.addAttribute("isUsernameExists", true);
            model.addAttribute("usernameErrorMsg", "Username already associated with another account!");
            return "register";
        }

        userService.save(userDTO);
        return "redirect:/login";
    }
}
