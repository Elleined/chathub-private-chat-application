package com.privatechat.wsprivatechatapplication.controller;

import com.privatechat.wsprivatechatapplication.dto.Message;
import com.privatechat.wsprivatechatapplication.model.MessageStatus;
import com.privatechat.wsprivatechatapplication.service.MessageStatusService;
import com.privatechat.wsprivatechatapplication.service.WSService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("messageStatuses/api")

public class MessageStatusController {

    private final MessageStatusService messageStatusService;
    private final WSService wsService;

    @DeleteMapping("/{statusId}")
    public void deleteMessageStatus(@PathVariable("statusId") int statusId,
                                    @RequestBody Message message) {
        messageStatusService.deleteMessageStatus(statusId);

        var messageStatus = messageStatusService.getById(statusId);
        wsService.sendPrivateMessage(message, messageStatus);
    }

    @DeleteMapping("/delete-all-by-id")
    public void deleteAllMessageStatusById(@RequestParam("ids") List<Integer> statusIds,
                                           @RequestBody List<Message> messages) {

//        messageStatusService.deleteAllById(statusIds);
//        messages.forEach(message -> wsService.sendPrivateMessage(message, ));
    }
}
