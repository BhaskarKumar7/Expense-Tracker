package com.um.exptracker.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.um.exptracker.annotations.validator.UniqueMobileNoValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = UniqueMobileNoValidator.class)
public @interface UniqueMobileNo {

	String message() default "mobile number already exists";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
