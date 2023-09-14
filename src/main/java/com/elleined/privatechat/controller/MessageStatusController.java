package com.elleined.privatechat.controller;

import com.elleined.privatechat.dto.Message;
import com.elleined.privatechat.service.MessageStatusService;
import com.elleined.privatechat.service.WSService;
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
    public void deleteAllMessageStatusById(@RequestParam("ids") List<Integer> statusIds) {
        messageStatusService.deleteAllById(statusIds);
    }
}
