package com.um.exptracker.dto;

import com.um.exptracker.annotations.UniqueBankName;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TempBankDto {
	
	@NotBlank
	@UniqueBankName
	private String bankName;
	private AddressDto address;
}
