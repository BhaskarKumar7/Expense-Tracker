package com.um.exptracker.entity;

import java.util.List;

import com.um.exptracker.constants.AppConstant;
import com.um.exptracker.enums.GenderEnum;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(schema = AppConstant.EXP_TRACKER_SCHEMA)
@Getter
@Setter
public class UserEntity {

	@Id
	@SequenceGenerator(schema = AppConstant.EXP_TRACKER_SCHEMA,allocationSize = 1,initialValue = 1,
	sequenceName = "seq_user",name = "user_seq_gen")
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "user_seq_gen")
	private Long userId;
	private String firstName;
	private String lastName;
	private String userName;
	private String password;
	private String email;
	private String mobileNo;
	private String dateOfBirth;
	@Enumerated(EnumType.STRING)
	private GenderEnum gender;
	@OneToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	@JoinColumn(name = "address_id")
	private AddressEntity address;
	@OneToMany(mappedBy = "user",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	private List<BankAccountEntity> bankAccounts;
}
