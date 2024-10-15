package com.um.exptracker.dto;

import com.um.exptracker.annotations.UniqueBankName;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class BankDto {

	public BankDto(Long bankId, String bankName, Long addressId) {
		this.bankId=bankId;
		this.bankName=bankName;
		this.addressId=addressId;
	}
	private Long bankId;
	@NotBlank
	@UniqueBankName
	private String bankName;
	private Long addressId;
}
