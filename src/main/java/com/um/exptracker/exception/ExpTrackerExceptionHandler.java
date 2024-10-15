package com.um.exptracker.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExpTrackerExceptionHandler {

	@ExceptionHandler(value = UserNotFoundException.class)
	public ResponseEntity<Map<String, String>> handleUserNotFoundException(UserNotFoundException uex) {
		Map<String, String> responseMap = new HashMap<>();
		responseMap.put("error", uex.getMessage());
		return new ResponseEntity<>(responseMap, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(value = RuntimeException.class)
	public ResponseEntity<Map<String, String>> handleRunTimeException(RuntimeException re) {
		Map<String, String> respMap = new HashMap<>();
		respMap.put("error", re.getMessage());
		return new ResponseEntity<>(respMap, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(value = MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> handleMethodArgumentNotValidException(
			MethodArgumentNotValidException me) {
		Map<String, String> respMap = new HashMap<>();
		StringBuilder errors = new StringBuilder();
		me.getAllErrors().forEach(error -> {
			errors.append(error.getDefaultMessage()).append("\n");
		});
		respMap.put("message", errors.substring(0, errors.length() - 2));
		return new ResponseEntity<>(respMap, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(value = CategoryNotFoundException.class)
	public ResponseEntity<Map<String, String>> handleCategoryNotFoundException(CategoryNotFoundException cnex) {
		Map<String, String> responseMap = new HashMap<>();
		responseMap.put("error", cnex.getMessage());
		return new ResponseEntity<>(responseMap, HttpStatus.BAD_REQUEST);
	}
}
