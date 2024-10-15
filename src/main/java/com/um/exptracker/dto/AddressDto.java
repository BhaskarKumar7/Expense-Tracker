package com.um.exptracker.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressDto {

	private Long addressId;
	@NotBlank
	private String country;
	@NotBlank
	private String state;
	@NotBlank
	private String city;
	@NotBlank
	@Pattern(regexp = "^[1-9]\\d{5}$")
	private String pincode;
	@NotBlank
	private String landmark;
}
