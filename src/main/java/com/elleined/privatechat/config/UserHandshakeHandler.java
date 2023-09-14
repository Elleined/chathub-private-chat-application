package com.elleined.privatechat.config;

import com.elleined.privatechat.dto.UserDTO;
import com.elleined.privatechat.service.UserService;
import com.sun.security.auth.UserPrincipal;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import java.security.Principal;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Component
public class UserHandshakeHandler extends DefaultHandshakeHandler {

    private final UserService userService;
    private final HttpSession httpSession;

    @Override
    protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler, Map<String, Object> attributes) {
        // HttpSession session = ((ServletServerHttpRequest) request).getServletRequest().getSession();
        String username = (String) httpSession.getAttribute("username");
        UserDTO userDTO = userService.getByUsername(username);
        return new UserPrincipal(String.valueOf(userDTO.id()));
    }
}
