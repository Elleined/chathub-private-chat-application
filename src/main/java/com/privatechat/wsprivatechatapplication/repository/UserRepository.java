package com.privatechat.wsprivatechatapplication.repository;

import com.privatechat.wsprivatechatapplication.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("SELECT u.username FROM User u")
    List<String> fetchAllUsername();
}