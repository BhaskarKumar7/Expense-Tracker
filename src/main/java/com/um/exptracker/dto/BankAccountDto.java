package com.um.exptracker.dto;

import com.um.exptracker.annotations.UniqueBankAccountNo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class BankAccountDto {

	private Long bankAccountId;
	private Long bankId;
	@Pattern(regexp = "^\\d{9,18}$" ,message = "account number should not be more than 18 digits")
	@UniqueBankAccountNo
	private String accountNo;
	@NotBlank
	private String accountType;
	@NotNull
	private Double balance;
	private Long userId;
	private String bankName;
}
