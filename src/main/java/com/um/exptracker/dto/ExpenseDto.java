package com.um.exptracker.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ExpenseDto {

	private Long expenseId;
	@NotNull
	private Long bankAccountId;
	@NotNull
	private Double expense;
	@NotNull
	private Long categoryId;
	private String description;
	private String transactionDate;
	private String categoryName;
}
