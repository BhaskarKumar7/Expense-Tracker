package com.um.exptracker.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.um.exptracker.dto.IncomeDto;
import com.um.exptracker.dto.ServerResponseDto;
import com.um.exptracker.entity.BankAccountEntity;
import com.um.exptracker.entity.CategoryEntity;
import com.um.exptracker.entity.IncomeEntity;
import com.um.exptracker.repository.BankAccountRepository;
import com.um.exptracker.repository.CategoryRepository;
import com.um.exptracker.repository.IncomeRepository;
import com.um.exptracker.service.IncomeService;
import com.um.exptracker.utils.Mapper;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class IncomeServiceImpl implements IncomeService {

	private IncomeRepository incomeRepo;
	private BankAccountRepository bankAccountRepo;
	private CategoryRepository categoryRepo;
	private static final String CREATED_BY = "user";

	@Override
	public ServerResponseDto createIncome(IncomeDto incomeDto) {
		BankAccountEntity bankAccountEntity = bankAccountRepo.findById(incomeDto.getBankAccountId())
				.orElseThrow(() -> new RuntimeException("BankAccount Not Found"));
		IncomeEntity incomeEntity = Mapper.toIncomeEntity(incomeDto);
		incomeEntity.setBankAccount(bankAccountEntity);

		Optional<CategoryEntity> optional = categoryRepo.findById(incomeDto.getCategoryId());
		if (optional.isEmpty()) {
			throw new RuntimeException("category not found");
		}

		// check if income already exists with this category
		/*
		 * Optional<IncomeEntity> existingIncome =
		 * incomeRepo.findByCategory_CategoryId(incomeDto.getCategoryId()); if
		 * (existingIncome.isPresent()) { throw new
		 * RuntimeException("Income with this category already exists."); }
		 */

		incomeEntity.setCreatedTime(LocalDateTime.now());
		incomeEntity.setCreatedBy(CREATED_BY);
		incomeEntity.setCategory(optional.get());
		incomeEntity.setModifiedBy(CREATED_BY);
		incomeEntity.setModifiedTime(LocalDateTime.now());

		bankAccountEntity.setBalance(bankAccountEntity.getBalance() + incomeEntity.getIncome());
		bankAccountRepo.save(bankAccountEntity);

		IncomeEntity savedIncomeEntity = incomeRepo.save(incomeEntity);
		return new ServerResponseDto("income of " + savedIncomeEntity.getIncome() + " has been added",
				HttpStatus.OK.name());
	}

	@Override
	public List<IncomeDto> getIncomesBetweenDates(String startDate, String endDate) {
		LocalDateTime stDtTime = LocalDateTime.parse(startDate + "T00:00:00");
		LocalDateTime edDtTime = LocalDateTime.parse(endDate + "T23:59:59");
		List<IncomeEntity> incomesList = incomeRepo.findAllByTransactionDateBetween(stDtTime, edDtTime);
		return incomesList.stream().map(Mapper::toIncomeDTO).collect(Collectors.toList());
	}

	@Override
	public List<IncomeDto> getIncomesByDateRangeAndBankAccount(String startDate, String endDate, Long bankAccountId) {
		LocalDateTime stDtTime = LocalDateTime.parse(startDate + "T00:00:00");
		LocalDateTime edDtTime = LocalDateTime.parse(endDate + "T23:59:59");
		List<IncomeEntity> incomesList = incomeRepo.findByTransactionDateBetweenAndBankAccount_BankAccountId(stDtTime, edDtTime, bankAccountId);
		return incomesList.stream().map(Mapper::toIncomeDTO).collect(Collectors.toList());
	}

}
