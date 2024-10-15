package com.um.exptracker.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.um.exptracker.dto.ExpenseDto;
import com.um.exptracker.dto.ExpenseRequestDto;
import com.um.exptracker.dto.ServerResponseDto;
import com.um.exptracker.service.ExpenseService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/exptracker/expense")
@AllArgsConstructor
@Validated
public class ExpenseController {

	private ExpenseService expenseService;

	@PostMapping("/add")
	public ResponseEntity<ServerResponseDto> addIncome(@RequestBody @Valid ExpenseDto expenseDto) {
		return new ResponseEntity<>(expenseService.createExpenxe(expenseDto), HttpStatus.OK);
	}

	/*
	 * @PostMapping("/fetch") public ResponseEntity<List<ExpenseDto>>
	 * fetchExpenseBetweenDates(@RequestParam LocalDate startDate,
	 * 
	 * @RequestParam LocalDate endDate) { return new
	 * ResponseEntity<>(expenseService.getExpensesBetweenDates(startDate, endDate),
	 * HttpStatus.OK); }
	 */

	@PostMapping("/fetch")
	public ResponseEntity<List<ExpenseDto>> fetchExpenseBetweenDatesAndBankAccount(
			@Valid @RequestBody ExpenseRequestDto expenseRequestDto) {
		List<ExpenseDto> expenseDtosList = expenseService.getExpensesByDateRangeAndBankAccount(
				expenseRequestDto.getStartDate(), expenseRequestDto.getEndDate(), expenseRequestDto.getBankAccountId());
		return new ResponseEntity<>(expenseDtosList, HttpStatus.OK);
	}

}
