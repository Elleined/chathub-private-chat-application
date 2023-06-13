package com.privatechat.wsprivatechatapplication.repository;

import com.privatechat.wsprivatechatapplication.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    @Query("select u from User u where u.UUID = ?1")
    Optional<User> fetchByUUID(String UUID);
    @Query("select u from User u where u.username = ?1")
    Optional<User> fetchByUsername(String username);

    @Query("SELECT u.username FROM User u")
    List<String> fetchAllUsername();
}