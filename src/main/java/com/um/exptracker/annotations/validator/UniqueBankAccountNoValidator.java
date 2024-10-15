package com.um.exptracker.annotations.validator;

import org.springframework.stereotype.Component;

import com.um.exptracker.annotations.UniqueBankAccountNo;
import com.um.exptracker.service.BankAccountService;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class UniqueBankAccountNoValidator implements ConstraintValidator<UniqueBankAccountNo, String> {

	private BankAccountService bankAccountService;
	
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		return bankAccountService.doesAccountNoExists(value) ? false : true;
	}

}
