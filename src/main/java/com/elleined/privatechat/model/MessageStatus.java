package com.elleined.privatechat.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tbl_message_status")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MessageStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "status_id")
    private int id;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;

    public enum Status {ACTIVE, INACTIVE}
}
