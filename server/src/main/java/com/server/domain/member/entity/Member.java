package com.server.domain.member.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.server.domain.oauth.entity.KakaoToken;
import com.server.domain.push.entity.Push;
import com.server.domain.record.entity.Record;
import com.server.domain.schedule.entity.Schedule;
import com.server.global.audit.BaseEntity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Member extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;
    @Column(nullable = false)
    private String email;
    @Column
    private String password;
    @Column(nullable = false)
    private String nickname;
    @Enumerated(value = EnumType.STRING)
    @Column(length = 32, columnDefinition = "varchar(32) default 'ROLE_USER'")
    private Role role = Role.USER;

    // 전이 용도
    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE)
    private List<Schedule> schedules;

    // 전이 용도, 작동하는지 확인 필요

    @OneToOne(mappedBy = "member", cascade = CascadeType.REMOVE)
    private KakaoToken kakaoToken;

    @OneToOne(mappedBy = "member", cascade = CascadeType.REMOVE)
    private Push push;

    // 여행일지와 연관관계 설정
    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE)
    private List<Record> records;

    @Builder
    public Member(Long memberId, String email, String password, String nickname) {
        this.memberId = memberId;
        this.email = email;
        this.password = password;
        this.nickname = nickname;
    }

    @Getter
    public enum Role {
        ADMIN("ROLE_ADMIN", "ROLE_USER"),
        USER("ROLE_USER"),
        SOCIAL("ROLE_USER");
        // SOCIAL("ROLE_USER", "ROLE_SOCIAL");

        private final String[] roles;

        Role(String... roles) {
            this.roles = roles;
        }
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
