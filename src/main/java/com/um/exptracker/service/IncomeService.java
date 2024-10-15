package com.um.exptracker.service;

import java.util.List;

import com.um.exptracker.dto.IncomeDto;
import com.um.exptracker.dto.ServerResponseDto;

public interface IncomeService {

	public ServerResponseDto createIncome(IncomeDto incomeDto);
	public List<IncomeDto> getIncomesBetweenDates(String startDate, String endDate);
	public List<IncomeDto> getIncomesByDateRangeAndBankAccount(String startDate, String endDate,Long bankAccountId);
}
