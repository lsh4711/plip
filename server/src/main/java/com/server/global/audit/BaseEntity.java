package com.server.global.audit;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public abstract class BaseEntity {
	@Column(updatable = false)
	private LocalDateTime createdAt;

	private LocalDateTime modifiedAt;

	@PrePersist
	public void prePersist() {
		LocalDateTime now = LocalDateTime.now().withNano(0);
		createdAt = now;
		modifiedAt = now;
	}

	@PreUpdate
	public void preUpdate() {
		modifiedAt = LocalDateTime.now().withNano(0);
	}
}
