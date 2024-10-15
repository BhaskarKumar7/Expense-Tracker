package com.um.exptracker.entity;

import java.util.List;

import com.um.exptracker.constants.AppConstant;
import com.um.exptracker.enums.AccountTypeEnum;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(schema = AppConstant.EXP_TRACKER_SCHEMA)
@Getter
@Setter
public class BankAccountEntity {

	@Id
	@SequenceGenerator(schema = AppConstant.EXP_TRACKER_SCHEMA, allocationSize = 1, initialValue = 1, sequenceName = "seq_bank_acc", name = "bank_acc_seq_gen")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bank_acc_seq_gen")
	private Long bankAccountId;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "bank_id")
	private BankEntity bank;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private UserEntity user;
	private String accountNo;
	@Enumerated(EnumType.STRING)
	private AccountTypeEnum accountType;
	private Double balance;
	@OneToMany(mappedBy = "bankAccount", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<IncomeEntity> incomes;
	@OneToMany(mappedBy = "bankAccount", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<ExpenseEntity> expenses;
}
