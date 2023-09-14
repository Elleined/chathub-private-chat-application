package com.elleined.privatechat.repository;

import com.elleined.privatechat.model.MessageStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageStatusRepository extends JpaRepository<MessageStatus, Integer> {
}
