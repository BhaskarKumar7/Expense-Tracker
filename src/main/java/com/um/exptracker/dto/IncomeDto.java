package com.um.exptracker.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class IncomeDto {

	private Long incomeId;
	@NotNull
	private Long bankAccountId;
	@NotNull
	private Double income;
	@NotNull
	private Long categoryId;
	private String description;
	private String transactionDate;
	private String categoryName;
 
}
