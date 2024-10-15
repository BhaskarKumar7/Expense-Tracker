package com.um.exptracker.entity;

import com.um.exptracker.constants.AppConstant;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(schema = AppConstant.EXP_TRACKER_SCHEMA)
@Setter
@Getter
public class CategoryEntity {

	@Id
	@SequenceGenerator(schema = AppConstant.EXP_TRACKER_SCHEMA,allocationSize = 1,
	sequenceName = "seq_category",name = "category_seq_gen")
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "category_seq_gen")
	private Long categoryId;
	private String categoryName;
	private String categoryType;
}
