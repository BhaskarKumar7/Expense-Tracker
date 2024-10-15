package com.um.exptracker.annotations.validator;

import org.springframework.stereotype.Component;

import com.um.exptracker.annotations.UniqueEmail;
import com.um.exptracker.service.UserService;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {

	private UserService userService;

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		return userService.doesEmailExists(value) ? false : true;
	}

}
