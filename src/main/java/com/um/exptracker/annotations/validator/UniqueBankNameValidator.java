package com.um.exptracker.annotations.validator;

import org.springframework.stereotype.Component;

import com.um.exptracker.annotations.UniqueBankName;
import com.um.exptracker.service.BankService;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class UniqueBankNameValidator implements ConstraintValidator<UniqueBankName, String> {

	private BankService bankService;
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		return bankService.doesBankNameExists(value) ? false : true;
	}

}
