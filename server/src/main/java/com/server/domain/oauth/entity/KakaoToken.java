package com.server.domain.oauth.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.server.domain.member.entity.Member;
import com.server.global.audit.BaseEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class KakaoToken extends BaseEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    @Column
    private String accessToken;
    @Column
    private String refreshToken;

    @JoinColumn(name = "memberId")
    @OneToOne
    private Member member;

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
