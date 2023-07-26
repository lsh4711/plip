package com.server.global.event.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

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

    private String nickname;

    // url 노출을 막고 요청 횟수를 줄이기위해 파일까지 한번에 전송
    @Transient
    private String giftCodeImage;

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setGiftCodeImage(String giftCodeImage) {
        this.giftCodeImage = giftCodeImage;
    }
}
