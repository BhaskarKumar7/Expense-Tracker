package com.um.exptracker.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.um.exptracker.dto.AddressDto;
import com.um.exptracker.dto.BankDto;
import com.um.exptracker.dto.ServerResponseDto;
import com.um.exptracker.dto.TempBankDto;
import com.um.exptracker.entity.AddressEntity;
import com.um.exptracker.entity.BankEntity;
import com.um.exptracker.repository.BankRepository;
import com.um.exptracker.service.BankService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class BankServiceImpl implements BankService {

	private BankRepository bankRepo;

	@Override
	public ServerResponseDto createBank(TempBankDto tempBankDto) {
		if (tempBankDto != null) {
			BankEntity bankEntity = new BankEntity();
			bankEntity.setBankName(tempBankDto.getBankName());
			AddressDto addressDto = tempBankDto.getAddress();
			AddressEntity addressEntity = new AddressEntity();
			addressEntity.setCountry(addressDto.getCountry());
			addressEntity.setCity(addressDto.getState());
			addressEntity.setState(addressDto.getState());
			addressEntity.setPincode(addressDto.getPincode());
			addressEntity.setLandmark(addressDto.getLandmark());
			addressEntity.setCreatedBy("USER");
			addressEntity.setCreatedTime(LocalDateTime.now());
			bankEntity.setAddress(addressEntity);
			BankEntity savedBankEntity = bankRepo.save(bankEntity);
			return new ServerResponseDto("Bank added successfully with id : " + savedBankEntity.getBankId(),
					HttpStatus.OK.name());
		}
		return new ServerResponseDto("bad entries", HttpStatus.BAD_REQUEST.name());

	}

	@Override
	public boolean doesBankNameExists(String bankName) {
		return bankRepo.findByBankName(bankName) == null ? false : true;
	}

	@Override
	public BankEntity fetchBankByBankId(Long bankId) {
		return bankRepo.findByBankId(bankId);
	}

	@Override
	public List<BankDto> fetchAllBanks() {

		List<BankEntity> bankEntitiesList = bankRepo.findAll();

		List<BankDto> bankDtosList = new ArrayList<>();
		bankEntitiesList.forEach(bank -> {
			bankDtosList.add(new BankDto(bank.getBankId(), bank.getBankName(), bank.getAddress().getAddressId()));
		});
		return bankDtosList;
	}

}
