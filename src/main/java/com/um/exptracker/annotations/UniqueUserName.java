package com.um.exptracker.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.um.exptracker.annotations.validator.UniqueUserNameValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = UniqueUserNameValidator.class)
public @interface UniqueUserName {
		String message() default "username already exists";
		Class<?> [] groups() default {};
		Class<? extends Payload>[] payload() default {}; 
}
