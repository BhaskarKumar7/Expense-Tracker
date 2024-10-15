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
@Getter
@Setter
public class AddressEntity extends TimeStampEntity{

	@Id
	@SequenceGenerator(schema = AppConstant.EXP_TRACKER_SCHEMA,allocationSize = 1,initialValue = 2,
	sequenceName = "seq_addrs",name = "addrs_seq_gen")
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "addrs_seq_gen")
	private Long addressId;
	private String country;
	private String state;
	private String city;
	private String pincode;
	private String landmark;
}
