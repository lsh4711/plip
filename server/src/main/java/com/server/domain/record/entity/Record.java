package com.server.domain.record.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.server.domain.member.entity.Member;
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
    private String title;

    @Column(nullable = false)
    private String content;

    @ManyToOne
    @JoinColumn(name="memberId")
    private Member member;


    //Place_Schedules 외래키 Record:Place_Schedules -> N:1
    // @ManyToOne
    // @JoinColumn(name="shedule_place_id")
    // private Schedule_Place schedulePlace;

    @Builder
    public Record(Long recordId, String title, String content) {
        this.recordId = recordId;
        this.title = title;
        this.content = content;
    }
}
