package com.server.domain.member.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.server.global.audit.BaseEntity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
<<<<<<< HEAD
/**
 * TODO: 추후에 Auditable(생성일, 수정일) 추가
 * */
=======
>>>>>>> 21e43da99e0660e1051c1412b2b6dade9bafe523
public class Member extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;
<<<<<<< HEAD

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String nickname;

=======
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String nickname;
>>>>>>> 21e43da99e0660e1051c1412b2b6dade9bafe523
    @Enumerated(value = EnumType.STRING)
    @Column(length = 32, columnDefinition = "varchar(32) default 'ROLE_USER'")
    private Role role = Role.USER;

    @Builder
    public Member(Long memberId, String email, String password, String nickname) {
        this.memberId = memberId;
        this.email = email;
        this.password = password;
        this.nickname = nickname;
    }

    @Getter
    @RequiredArgsConstructor
    public enum Role {
        USER("ROLE_USER"),
        SOCIAL("ROLE_SOCIAL");
<<<<<<< HEAD

=======
>>>>>>> 21e43da99e0660e1051c1412b2b6dade9bafe523
        private final String value;
    }

    public void updateEncryptedPassword(String password) {
        this.password = password;
    }
}
