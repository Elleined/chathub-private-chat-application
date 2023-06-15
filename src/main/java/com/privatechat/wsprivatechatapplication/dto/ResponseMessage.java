package com.privatechat.wsprivatechatapplication.dto;

public record ResponseMessage(
        int senderId,
        String senderUsername,
        String messageContent,
        String senderPicture
) {
}
