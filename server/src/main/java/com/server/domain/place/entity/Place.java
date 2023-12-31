package com.server.domain.place.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import com.server.domain.category.entity.Category;
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
public class Place extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long placeId;

    // @Column(unique = true)
    private Long apiId; // optnioal, default: 0(0 == custom point)

    private String name; // optional, default: 사용자 지정 장소
    private String address;

    private String phone;

    private String latitude;
    private String longitude;

    @Transient
    private Long schedulePlaceId;

    @Transient
    private Integer days;

    @Transient
    private Integer orders;

    // @Transient
    // private Boolean bookmark;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoryId")
    private Category category;
}
