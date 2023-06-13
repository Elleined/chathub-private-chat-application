package com.privatechat.wsprivatechatapplication.service;

import com.privatechat.wsprivatechatapplication.dto.UserDTO;
import com.privatechat.wsprivatechatapplication.exception.ResourceNotFoundException;
import com.privatechat.wsprivatechatapplication.model.User;
import com.privatechat.wsprivatechatapplication.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    public int save(UserDTO userDTO) {
        String uuid = UUID.randomUUID().toString();

        User user = User.builder()
                .username(userDTO.username())
                .description(userDTO.description())
                .UUID(uuid)
                .picture(userDTO.picture())
                .build();

        userRepository.save(user);
        log.debug("User with username of {} saved successfully", user.getUsername());
        return user.getId();
    }
    public boolean isUsernameExists(String username) {
        return userRepository.fetchAllUsername().contains(username);
    }

    public UserDTO getByUsername(String username) {
        User user = userRepository.fetchByUsername(username).orElseThrow(() -> new ResourceNotFoundException("User with username of " + username + " does not exists!"));
        return this.convertToDTO(user);
    }

    public UserDTO getByUUID(String UUID) {
        User user = userRepository.fetchByUUID(UUID).orElseThrow(() -> new ResourceNotFoundException("User with UUID of " + UUID + " does not exists!"));
        return this.convertToDTO(user);
    }

    public List<UserDTO> getAllExceptCurrentUser(int id) {
        return userRepository.findAll()
                .stream()
                .filter(user -> user.getId() != id)
                .map(this::convertToDTO)
                .toList();
    }

    public UserDTO convertToDTO(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .description(user.getDescription())
                .username(user.getUsername())
                .picture(user.getPicture())
                .UUID(user.getUUID())
                .build();
    }
}
