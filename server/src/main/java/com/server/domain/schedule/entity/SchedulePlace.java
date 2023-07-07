package com.server.domain.schedule.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.server.domain.place.entity.Place;
import com.server.domain.record.entity.Record;
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
public class SchedulePlace extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long schedulePlaceId;

    private Integer days;
    private Integer orders;
    private Boolean bookmark;
    // private LocalDateTime startDate; // 현재 불필요
    // private LocalDateTime endDate; // 현재 불필요

    @ManyToOne
    @JoinColumn(name = "scheduleId")
    private Schedule schedule;

    @ManyToOne
    @JoinColumn(name = "placeId")
    private Place place;

    @OneToMany(mappedBy = "schedulePlace")
    private List<Record> records;
}
