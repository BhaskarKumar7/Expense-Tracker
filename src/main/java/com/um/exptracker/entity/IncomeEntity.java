package com.um.exptracker.entity;

import java.time.LocalDateTime;

import com.um.exptracker.constants.AppConstant;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(schema = AppConstant.EXP_TRACKER_SCHEMA)
@Getter
@Setter
public class IncomeEntity extends TimeStampEntity {

	@Id
	@SequenceGenerator(schema = AppConstant.EXP_TRACKER_SCHEMA, allocationSize = 1, initialValue = 1, sequenceName = "seq_income", name = "income_seq_gen")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "income_seq_gen")
	private Long incomeId;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "bank_account_id")
	private BankAccountEntity bankAccount;
	private Double income;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "category_id")
	private CategoryEntity category;
	private String description;
	private LocalDateTime transactionDate;
}
