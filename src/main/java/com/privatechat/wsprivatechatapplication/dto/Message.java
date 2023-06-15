package com.privatechat.wsprivatechatapplication.dto;

public record Message(
        String senderUsername,
        String body,
        int recipientId
) {
}
