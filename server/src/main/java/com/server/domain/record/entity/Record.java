package com.server.domain.record.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.server.domain.member.entity.Member;
import com.server.domain.schedule.entity.SchedulePlace;
import com.server.global.audit.BaseEntity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Record extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long recordId;


    @Column(nullable = false)
    private String content;

    //연관관계 매핑 Record:Member -> N:1
    @ManyToOne
    @JoinColumn(name = "memberId")
    private Member member;

    //연관관계 매핑 Record:SchedulePlace -> N:1
    @ManyToOne
    @JoinColumn(name = "schedulePlaceId")
    private SchedulePlace schedulePlace;

    @Builder
    public Record(Long recordId, String content) {
        this.recordId = recordId;
        this.content = content;
    }
}
