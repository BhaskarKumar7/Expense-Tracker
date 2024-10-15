package com.um.exptracker.entity;

import java.time.LocalDateTime;

import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public class TimeStampEntity {

	private LocalDateTime createdTime;
	private LocalDateTime modifiedTime;
	private String createdBy;
	private String modifiedBy;
}
