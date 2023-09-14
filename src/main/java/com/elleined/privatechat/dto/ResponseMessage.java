package com.elleined.privatechat.dto;

public record ResponseMessage(
        int senderId,
        String senderUsername,
        String messageContent,
        String senderPicture,
        String notificationMessage,
        int messageCount,
        int messageStatusId,
        String messageStatus
) {
}
