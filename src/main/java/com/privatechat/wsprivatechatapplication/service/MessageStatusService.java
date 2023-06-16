package com.privatechat.wsprivatechatapplication.service;

import com.privatechat.wsprivatechatapplication.model.MessageStatus;
import com.privatechat.wsprivatechatapplication.repository.MessageStatusRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MessageStatusService {

    private final MessageStatusRepository messageStatusRepository;

    public MessageStatus save() {
        MessageStatus messageStatus = MessageStatus.builder()
                .status(MessageStatus.Status.ACTIVE)
                .build();

        messageStatusRepository.save(messageStatus);
        log.debug("Message status save successfully with id of {}", messageStatus.getId());
        return messageStatus;
    }

    public MessageStatus getById(int statusId) {
        return messageStatusRepository.findById(statusId).orElseThrow();
    }

    public void deleteMessageStatus(int statusId) {
        MessageStatus messageStatus = messageStatusRepository.findById(statusId).orElseThrow();
        messageStatus.setStatus(MessageStatus.Status.INACTIVE);
        messageStatusRepository.save(messageStatus);
        log.debug("Message status with id of {} are now inactive ", messageStatus.getId());
    }

    public void deleteAllById(List<Integer> statusIds) {
        List<MessageStatus> messageStatuses = messageStatusRepository.findAllById(statusIds);
        messageStatuses.stream()
                .map(MessageStatus::getId)
                .forEach(this::deleteMessageStatus);
    }
}
