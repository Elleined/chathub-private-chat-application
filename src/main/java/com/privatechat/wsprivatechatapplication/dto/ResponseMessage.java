package com.privatechat.wsprivatechatapplication.dto;

public record ResponseMessage(
        String sender,
        String messageContent,
        String senderPicture
) { }
