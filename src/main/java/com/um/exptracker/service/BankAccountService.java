package com.um.exptracker.service;

import java.util.List;

import com.um.exptracker.dto.BankAccountDto;
import com.um.exptracker.dto.ServerResponseDto;
import com.um.exptracker.enums.AccountTypeEnum;

public interface BankAccountService {

	public boolean doesAccountNoExists(String accountNo);
	public ServerResponseDto createBankAccount(BankAccountDto bankAccountDto);
	public List<AccountTypeEnum> fetchAllAccountTypes();
	public List<BankAccountDto> fetchAllBankAccounts(Long userId);
}
