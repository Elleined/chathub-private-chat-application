package com.privatechat.wsprivatechatapplication.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;

@Builder
public record UserDTO(
    Integer id,

    @NotEmpty(message = "Username should not be null")
    @NotBlank(message = "Username cannot be white space only")
    String username,

    @NotEmpty(message = "Name should not be null")
    @NotBlank(message = "Name cannot be white space only")
    String name,
    String UUID
) { }
