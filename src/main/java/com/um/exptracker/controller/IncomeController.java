package com.um.exptracker.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.um.exptracker.dto.IncomeDto;
import com.um.exptracker.dto.IncomeRequestDto;
import com.um.exptracker.dto.ServerResponseDto;
import com.um.exptracker.service.IncomeService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/exptracker/income")
@AllArgsConstructor
@Validated
public class IncomeController {

	private IncomeService incomeService;

	@PostMapping("/add")
	public ResponseEntity<ServerResponseDto> addIncome(@RequestBody @Valid IncomeDto incomeDto) {
		return new ResponseEntity<>(incomeService.createIncome(incomeDto), HttpStatus.OK);
	}

	/*
	 * @PostMapping("/fetch") public ResponseEntity<List<IncomeDto>>
	 * fetchIncomeBetweenDates(
	 * 
	 * @RequestParam(value = "startDate") @DateTimeFormat(iso = ISO.DATE) String
	 * startDate,
	 * 
	 * @RequestParam(value = "endDate") @DateTimeFormat(iso = ISO.DATE) String
	 * endDate) { return new
	 * ResponseEntity<>(incomeService.getIncomesBetweenDates(startDate, endDate),
	 * HttpStatus.OK); }
	 */

	@PostMapping("/fetch")
	public ResponseEntity<List<IncomeDto>> fetchIncomeBetweenDatesAndBankAccount(
			@Valid @RequestBody IncomeRequestDto incomeRequestDto) {

		List<IncomeDto> incomeDtosList = incomeService.getIncomesByDateRangeAndBankAccount(
				incomeRequestDto.getStartDate(), incomeRequestDto.getEndDate(), incomeRequestDto.getBankAccountId());
		return new ResponseEntity<>(incomeDtosList, HttpStatus.OK);
	}
}
