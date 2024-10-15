package com.um.exptracker.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.um.exptracker.dto.BankAccountDto;
import com.um.exptracker.dto.ServerResponseDto;
import com.um.exptracker.entity.BankAccountEntity;
import com.um.exptracker.entity.UserEntity;
import com.um.exptracker.enums.AccountTypeEnum;
import com.um.exptracker.repository.BankAccountRepository;
import com.um.exptracker.repository.UserRepository;
import com.um.exptracker.service.BankAccountService;
import com.um.exptracker.service.BankService;
import com.um.exptracker.utils.Mapper;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class BankAccountServiceImpl implements BankAccountService {

	private BankAccountRepository bankAccountRepo;
	private BankService bankService;
	private UserRepository userRepo;
	
	
	@Override
	public boolean doesAccountNoExists(String accountNo) {
		return bankAccountRepo.findByAccountNo(accountNo) == null ? false : true;
	}

	@Override
	public ServerResponseDto createBankAccount(BankAccountDto bankAccountDto) {
		BankAccountEntity bankAccountEntity = null;
		if(bankAccountDto != null) {
			bankAccountEntity = new BankAccountEntity();
			bankAccountEntity.setAccountNo(bankAccountDto.getAccountNo());
			bankAccountEntity.setAccountType(AccountTypeEnum.valueOf(bankAccountDto.getAccountType()));
			bankAccountEntity.setBalance(bankAccountDto.getBalance());
			/*Setting bank and address values to bank account entity*/
			//if(bankAccountDto.getBank() != null) 
				/*added bank value to bank account entity*/
				bankAccountEntity.setBank(bankService.fetchBankByBankId(bankAccountDto.getBankId()));
			//bankAccountEntity.set
				Optional<UserEntity> userEntity = userRepo.findById(bankAccountDto.getUserId());
				if(userEntity.isPresent()) {
					bankAccountEntity.setUser(userEntity.get());
				}
				
		} 
		 BankAccountDto bDto = Mapper.toBankAccountDTO(bankAccountRepo.save(bankAccountEntity));
		return new ServerResponseDto("bank account added successfully with id " + bDto.getBankAccountId(), HttpStatus.OK.name());
	}

	@Override
	public List<AccountTypeEnum> fetchAllAccountTypes() {
		return Arrays.asList(AccountTypeEnum.values());
	}


	@Override
	public List<BankAccountDto> fetchAllBankAccounts(Long userId) {
		 List<BankAccountEntity> bankAccEntList = bankAccountRepo.findByUser_UserId(userId);
		 List<BankAccountDto> bankAccDtosList = new ArrayList<>();
		 bankAccEntList.forEach(bankAccEnt -> {
			 BankAccountDto bankAccountDto = new BankAccountDto();
			 bankAccountDto.setBankAccountId(bankAccEnt.getBankAccountId());
			 bankAccountDto.setAccountNo(bankAccEnt.getAccountNo());
			 bankAccountDto.setAccountType(bankAccEnt.getAccountType().toString());
			 bankAccountDto.setBankName(bankAccEnt.getBank().getBankName());
			 bankAccountDto.setBalance(bankAccEnt.getBalance());
			 bankAccDtosList.add(bankAccountDto);
		 });
		return bankAccDtosList;
	}

}
