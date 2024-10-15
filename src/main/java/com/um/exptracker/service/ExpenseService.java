package com.um.exptracker.service;

import java.time.LocalDate;
import java.util.List;

import com.um.exptracker.dto.ExpenseDto;
import com.um.exptracker.dto.ServerResponseDto;

public interface ExpenseService {


	public ServerResponseDto createExpenxe(ExpenseDto expenseDto);
	public List<ExpenseDto> getExpensesBetweenDates(LocalDate startDate, LocalDate endDate);
	public List<ExpenseDto> getExpensesByDateRangeAndBankAccount(String startDate, String endDate,Long bankAccountId);
}
