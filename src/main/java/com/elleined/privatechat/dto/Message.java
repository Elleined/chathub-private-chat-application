package com.elleined.privatechat.dto;

public record Message(
        String senderUsername,
        String body,
        int recipientId
) {
}
