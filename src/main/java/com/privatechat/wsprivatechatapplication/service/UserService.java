package com.privatechat.wsprivatechatapplication.service;

import com.privatechat.wsprivatechatapplication.dto.UserDTO;
import com.privatechat.wsprivatechatapplication.model.User;
import com.privatechat.wsprivatechatapplication.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    public int save(UserDTO userDTO) {
        String uuid = UUID.fromString(userDTO.username()).toString();

        User user = User.builder()
                .name(userDTO.name())
                .username(userDTO.username())
                .UUID(uuid)
                .build();

        userRepository.save(user);
        log.debug("User saved successfully with id of {}", user.getId());
        return user.getId();
    }

    public boolean isUsernameExists(String username) {
        return userRepository.fetchAllUsername().contains(username);
    }
}
