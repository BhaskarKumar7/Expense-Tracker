package com.um.exptracker.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.um.exptracker.annotations.UniqueEmail;
import com.um.exptracker.annotations.UniqueMobileNo;
import com.um.exptracker.annotations.UniqueUserName;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class UserDto {

	private Long userId;
	@NotBlank
	private String firstName;
	@NotBlank
	private String lastName;
	@NotBlank
	@UniqueUserName
	private String userName;
	@NotBlank
	@JsonProperty(access = Access.WRITE_ONLY)
	private String password;
	@NotBlank
	@UniqueEmail
	private String email;
	@Pattern(regexp = "^[0-9]{10}$",message = " mobile number should be a 10 digit number")
	@UniqueMobileNo
	private String mobileNo;
	@NotBlank
	private String dateOfBirth;
	@NotBlank
	private String gender;
	@Valid
	@JsonProperty(value = "userAddress")
	private AddressDto address;
	private List<BankAccountDto> bankAccounts;
}
