package com.server.global.event.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Gift {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long giftId;

    @Column(unique = true)
    private String email;

    private String nickname;

    @Transient
    private byte[] giftCodeImage;

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setGiftCodeImage(byte[] giftCodeImage) {
        this.giftCodeImage = giftCodeImage;
    }
}
