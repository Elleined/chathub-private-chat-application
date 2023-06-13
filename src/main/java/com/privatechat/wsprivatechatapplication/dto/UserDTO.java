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

    @NotEmpty(message = "Description should not be null")
    @NotBlank(message = "Description cannot be white space only")
    String description,

    @NotEmpty(message = "Picture should not be null")
    @NotBlank(message = "Picture cannot be white space only")
    String picture,
    String UUID
) { }
