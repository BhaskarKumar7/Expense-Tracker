package com.um.exptracker.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.um.exptracker.dto.ExpenseDto;
import com.um.exptracker.dto.ServerResponseDto;
import com.um.exptracker.entity.BankAccountEntity;
import com.um.exptracker.entity.CategoryEntity;
import com.um.exptracker.entity.ExpenseEntity;
import com.um.exptracker.repository.BankAccountRepository;
import com.um.exptracker.repository.CategoryRepository;
import com.um.exptracker.repository.ExpenseRepository;
import com.um.exptracker.service.ExpenseService;
import com.um.exptracker.utils.Mapper;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ExpenseServiceImpl implements ExpenseService {

	private ExpenseRepository expenseRepo;
	private BankAccountRepository bankAccountRepo;
	private CategoryRepository categoryRepo;
	private static final String CREATED_BY = "user";

	@Override
	public ServerResponseDto createExpenxe(ExpenseDto expenseDto) {
		BankAccountEntity bankAccountEntity = bankAccountRepo.findById(expenseDto.getBankAccountId())
				.orElseThrow(() -> new RuntimeException("BankAccount Not Found"));
		ExpenseEntity expenseEntity = Mapper.toExpenseEntity(expenseDto);
		expenseEntity.setBankAccount(bankAccountEntity);

		Optional<CategoryEntity> optional = categoryRepo.findById(expenseDto.getBankAccountId());
		if (optional.isEmpty()) {
			throw new RuntimeException("category not found");
		}

		
		expenseEntity.setCreatedBy(CREATED_BY);
		expenseEntity.setCreatedTime(LocalDateTime.now());
		expenseEntity.setModifiedBy(CREATED_BY);
		expenseEntity.setModifiedTime(LocalDateTime.now());
		expenseEntity.setCategory(optional.get());
		
		bankAccountEntity.setBalance(bankAccountEntity.getBalance() - expenseEntity.getExpense());
		bankAccountRepo.save(bankAccountEntity);
		ExpenseEntity savedExpenseEntity = expenseRepo.save(expenseEntity);
		return new ServerResponseDto("expense of " + savedExpenseEntity.getExpense() + " has been debited from your account",
				HttpStatus.OK.name());
	}

	@Override
	public List<ExpenseDto> getExpensesBetweenDates(LocalDate startDate, LocalDate endDate) {
		List<ExpenseEntity> expensesList = expenseRepo.findAllByTransactionDateBetween(startDate, endDate);
		return expensesList.stream().map(Mapper::toExpenseDTO).collect(Collectors.toList());
	}

	@Override
	public List<ExpenseDto> getExpensesByDateRangeAndBankAccount(String startDate, String endDate, Long bankAccountId) {
		LocalDateTime stDtTime = LocalDateTime.parse(startDate + "T00:00:00");
		LocalDateTime edDtTime = LocalDateTime.parse(endDate + "T23:59:59");
		List<ExpenseEntity> expenseList = expenseRepo.findByTransactionDateBetweenAndBankAccount_BankAccountId(stDtTime, edDtTime, bankAccountId);
		return expenseList.stream().map(Mapper::toExpenseDTO).collect(Collectors.toList());
	}

}
