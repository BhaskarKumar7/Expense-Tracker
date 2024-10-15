package com.um.exptracker.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.um.exptracker.annotations.validator.UniqueEmailValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = UniqueEmailValidator.class)
public @interface UniqueEmail {
	String message() default "email already exists";
	Class<?> [] groups() default {};
	Class<? extends Payload>[] payload() default {}; 

}
