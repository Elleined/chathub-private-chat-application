package com.privatechat.wsprivatechatapplication.service;

import com.privatechat.wsprivatechatapplication.dto.UserDTO;
import com.privatechat.wsprivatechatapplication.exception.ResourceNotFoundException;
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
        String uuid = UUID.randomUUID().toString();

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

    public UserDTO getByUsername(String username) {
        User user = userRepository.fetchByUsername(username).orElseThrow(() -> new ResourceNotFoundException("User with username of " + username + " does not exists!"));
        return this.convertToDTO(user);
    }

    public UserDTO convertToDTO(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .username(user.getUsername())
                .UUID(user.getUUID())
                .build();
    }
}
