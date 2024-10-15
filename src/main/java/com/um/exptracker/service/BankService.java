package com.um.exptracker.service;

import java.util.List;

import com.um.exptracker.dto.BankDto;
import com.um.exptracker.dto.ServerResponseDto;
import com.um.exptracker.dto.TempBankDto;
import com.um.exptracker.entity.BankEntity;

public interface BankService {

	public boolean doesBankNameExists(String bankName);

	public BankEntity fetchBankByBankId(Long bankId);
	
	public List<BankDto> fetchAllBanks();
	
	public ServerResponseDto createBank(TempBankDto tempBankDto);
}
