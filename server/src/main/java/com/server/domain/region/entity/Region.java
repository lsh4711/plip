package com.server.domain.region.entity;

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
public class Region {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long regionId;

	private String engName;

	private String korName;

	// @OneToMany(mappedBy = "region")
	// private List<Schedule> schedules;
}
