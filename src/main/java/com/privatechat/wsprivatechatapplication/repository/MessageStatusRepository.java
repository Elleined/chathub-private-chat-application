package com.privatechat.wsprivatechatapplication.repository;

import com.privatechat.wsprivatechatapplication.model.MessageStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageStatusRepository extends JpaRepository<MessageStatus, Integer> {
}
