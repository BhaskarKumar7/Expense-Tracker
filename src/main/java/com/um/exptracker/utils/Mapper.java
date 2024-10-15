package com.um.exptracker.utils;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.um.exptracker.dto.AddressDto;
import com.um.exptracker.dto.BankAccountDto;
import com.um.exptracker.dto.BankDto;
import com.um.exptracker.dto.ExpenseDto;
import com.um.exptracker.dto.IncomeDto;
import com.um.exptracker.dto.UserDto;
import com.um.exptracker.entity.AddressEntity;
import com.um.exptracker.entity.BankAccountEntity;
import com.um.exptracker.entity.BankEntity;
import com.um.exptracker.entity.ExpenseEntity;
import com.um.exptracker.entity.IncomeEntity;
import com.um.exptracker.entity.UserEntity;
import com.um.exptracker.enums.AccountTypeEnum;
import com.um.exptracker.enums.GenderEnum;
import com.um.exptracker.service.AddressService;
import com.um.exptracker.service.BankService;

@Component

public class Mapper {

	private AddressService addressService;
	private BankService bankService;

	private static final String CREATED_BY = "user";
	@Value("${password.salt}")
	private String salt;

	public Mapper(
			AddressService addressService, 
			BankService bankService, 
			@Value("${password.salt}") String salt
	
			) {
		this.addressService = addressService;
		this.bankService = bankService;
		this.salt = salt;

	}

	public UserDto toUserDTO(UserEntity userEntity) {
		UserDto userDto = new UserDto();
		if (userEntity != null) {
			userDto.setUserId(userEntity.getUserId());
			userDto.setFirstName(userEntity.getFirstName());
			userDto.setLastName(userEntity.getLastName());
			userDto.setUserName(userEntity.getUserName());
			userDto.setDateOfBirth(userEntity.getDateOfBirth());
			userDto.setEmail(userEntity.getEmail());
			userDto.setMobileNo(userEntity.getMobileNo());
			userDto.setGender(userEntity.getGender().toString());
			if (userEntity.getAddress() != null) {
				userDto.setAddress(toAddressDTO(userEntity.getAddress()));
			}
			if (userEntity.getBankAccounts() != null) {
				userDto.setBankAccounts(userEntity.getBankAccounts().stream().map(Mapper::toBankAccountDTO)
						.collect(Collectors.toList()));

			}
		}
		return userDto;
	}

	public UserEntity toUserEntity(UserDto userDto) {
		UserEntity userEntity = new UserEntity();
		userEntity.setFirstName(userDto.getFirstName());
		userEntity.setLastName(userDto.getLastName());
		userEntity.setUserName(userDto.getUserName());
		userEntity.setPassword(BCrypt.hashpw(userDto.getPassword(), salt));
		userEntity.setDateOfBirth(userDto.getDateOfBirth());
		userEntity.setEmail(userDto.getEmail());
		userEntity.setMobileNo(userDto.getMobileNo());
		userEntity.setGender(GenderEnum.valueOf(userDto.getGender().toUpperCase()));
		userEntity.setAddress(toAddressEntity(userDto.getAddress()));
		return userEntity;
	}

	public AddressDto toAddressDTO(AddressEntity addressEntity) {
		AddressDto addressDto = new AddressDto();
		addressDto.setAddressId(addressEntity.getAddressId());
		addressDto.setCountry(addressEntity.getCountry());
		addressDto.setState(addressEntity.getState());
		addressDto.setCity(addressEntity.getCity());
		addressDto.setPincode(addressEntity.getPincode());
		addressDto.setLandmark(addressEntity.getLandmark());
		return addressDto;
	}

	public static AddressEntity toAddressEntity(AddressDto addressDto) {
		AddressEntity addressEntity = new AddressEntity();
		addressEntity.setCountry(addressDto.getCountry().toUpperCase());
		addressEntity.setState(addressDto.getState().toUpperCase());
		addressEntity.setCity(addressDto.getCity().toUpperCase());
		addressEntity.setPincode(addressDto.getPincode());
		addressEntity.setLandmark(addressDto.getLandmark());
		addressEntity.setCreatedBy(CREATED_BY);
		addressEntity.setCreatedTime(LocalDateTime.now());
		addressEntity.setModifiedBy(CREATED_BY);
		addressEntity.setModifiedTime(LocalDateTime.now());
		return addressEntity;
	}

	public static BankAccountDto toBankAccountDTO(BankAccountEntity bankAccountEntity) {
		BankAccountDto bankAccountDto = new BankAccountDto();
		bankAccountDto.setBankAccountId(bankAccountEntity.getBankAccountId());
		bankAccountDto.setBankId(bankAccountEntity.getBank().getBankId());
		bankAccountDto.setAccountNo(bankAccountEntity.getAccountNo());
		bankAccountDto.setAccountType(bankAccountEntity.getAccountType().toString());
		bankAccountDto.setBalance(bankAccountEntity.getBalance());
		return bankAccountDto;
	}

	public BankAccountEntity toBankAccountEntity(BankAccountDto bankAccountDto) {
		BankAccountEntity bankAccountEntity = new BankAccountEntity();
		bankAccountEntity.setAccountNo(bankAccountDto.getAccountNo());
		bankAccountEntity.setAccountType(AccountTypeEnum.valueOf(bankAccountDto.getAccountType()));
		bankAccountEntity.setBank(bankService.fetchBankByBankId(bankAccountDto.getBankId()));
		bankAccountEntity.setBalance(bankAccountDto.getBalance());
		return bankAccountEntity;
	}

	/*
	 * public BankDto toBankDTO(BankEntity bankEntity) { BankDto bankDto = new
	 * BankDto(); bankDto.setBankId(bankEntity.getBankId());
	 * bankDto.setBankName(bankEntity.getBankName());
	 * bankDto.setAddressId(bankEntity.getAddress().getAddressId());
	 * ((addressService.fetchAddressById(bankEntity.get))); return bankDto; }
	 */

	public BankEntity toBankEntity(BankDto bankDto) {

		BankEntity bankEntity = new BankEntity();
		bankEntity.setBankName(bankDto.getBankName().toUpperCase());
		bankEntity.setAddress(addressService.fetchAddressById(bankDto.getAddressId()));
		return bankEntity;

	}

	public static IncomeDto toIncomeDTO(IncomeEntity incomeEntity) {
		IncomeDto incomeDto = new IncomeDto();
		incomeDto.setIncomeId(incomeEntity.getIncomeId());
		incomeDto.setBankAccountId(incomeEntity.getBankAccount().getBankAccountId());
		incomeDto.setCategoryId(incomeEntity.getCategory().getCategoryId());
		incomeDto.setDescription(incomeEntity.getDescription());
		incomeDto.setTransactionDate(incomeEntity.getTransactionDate().toString());
		incomeDto.setIncome(incomeEntity.getIncome());
		incomeDto.setCategoryName(incomeEntity.getCategory().getCategoryName());
		return incomeDto;
	}

	public static IncomeEntity toIncomeEntity(IncomeDto incomeDto) {
		IncomeEntity incomeEntity = new IncomeEntity();
		incomeEntity.setDescription(incomeDto.getDescription());
		incomeEntity.setIncome(incomeDto.getIncome());
		incomeEntity.setTransactionDate(LocalDateTime.now());
		return incomeEntity;
	}

	public static ExpenseDto toExpenseDTO(ExpenseEntity expenseEntity) {
		ExpenseDto expenseDto = new ExpenseDto();
		expenseDto.setExpenseId(expenseEntity.getExpenseId());
		expenseDto.setBankAccountId(expenseEntity.getBankAccount().getBankAccountId());
		expenseDto.setCategoryId(expenseEntity.getCategory().getCategoryId());
		expenseDto.setDescription(expenseEntity.getDescription());
		expenseDto.setExpense(expenseEntity.getExpense());
		expenseDto.setCategoryName(expenseEntity.getCategory().getCategoryName());
		expenseDto.setTransactionDate(expenseEntity.getTransactionDate().toString());
		return expenseDto;
	}

	public static ExpenseEntity toExpenseEntity(ExpenseDto expenseDto) {
		ExpenseEntity expenseEntity = new ExpenseEntity();
		expenseEntity.setDescription(expenseDto.getDescription());
		expenseEntity.setExpense(expenseDto.getExpense());
		expenseEntity.setTransactionDate(LocalDateTime.now());
		return expenseEntity;
	}
}
