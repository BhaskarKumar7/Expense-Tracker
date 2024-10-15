package com.um.exptracker.entity;

import com.um.exptracker.constants.AppConstant;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(schema = AppConstant.EXP_TRACKER_SCHEMA)
@Getter
@Setter
public class BankEntity {
	
	@Id
	@SequenceGenerator(schema = AppConstant.EXP_TRACKER_SCHEMA,allocationSize = 1,initialValue = 2,
	sequenceName = "seq_bank",name = "bank_seq_gen")
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "bank_seq_gen")
	private Long bankId;
	private String bankName;
	@OneToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	@JoinColumn(name = "address_id")
	private AddressEntity address;
}
