package com.server.global.event.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Builder;
import lombok.Getter;

@Entity
@Builder
@Getter
public class Gift {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long giftId;

    @Column(unique = true)
    private String email;
}
