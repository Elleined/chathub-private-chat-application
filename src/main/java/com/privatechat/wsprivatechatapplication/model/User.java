package com.privatechat.wsprivatechatapplication.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tbl_user")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "UUID", unique = true)
    private String UUID;
}
