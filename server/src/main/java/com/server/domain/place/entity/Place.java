package com.server.domain.place.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Place {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long placeId;

    // @Column(unique = true)
    private long apiId; // optnioal, default: (0 or -1)(= custom)

    private String name; // optional, default: 사용자 지정 장소
    private String address;

    private String latitude; // 사용자 지정시 같은 좌표가
    private String longitude; // 중복될 것 같으니 복합키 고려
}
