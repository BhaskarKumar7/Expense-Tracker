package com.um.exptracker.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ExpenseRequestDto {

	@NotBlank(message = "start date should not be empty")
	private String startDate;
	@NotBlank(message = "end date should not be empty")
	private String endDate;
	@NotNull(message = "bank acc id should not be empty")
	private Long bankAccountId;
}
