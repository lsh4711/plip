package com.server.domain.push.entity;

import javax.persistence.OneToOne;

import com.server.domain.member.entity.Member;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// @Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Push {
    private Long pushId;

    private String pushToken;

    @OneToOne(mappedBy = "push")
    private Member member;
}
