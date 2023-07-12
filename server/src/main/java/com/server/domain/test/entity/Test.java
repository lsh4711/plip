package com.server.domain.test.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.server.domain.member.entity.Member;
import com.server.global.audit.BaseEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Test extends BaseEntity {
    @Id
    @GeneratedValue
    private long testId;

    private String accessToken;
    private String refreshToken;

    @ManyToOne
    @JoinColumn(name = "memberId")
    private Member member;
}
