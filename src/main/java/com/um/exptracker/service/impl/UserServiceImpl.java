package com.um.exptracker.service.impl;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.um.exptracker.dto.AddressDto;
import com.um.exptracker.dto.ExpenseDto;
import com.um.exptracker.dto.IncomeDto;
import com.um.exptracker.dto.ServerResponseDto;
import com.um.exptracker.dto.UserDto;
import com.um.exptracker.entity.AddressEntity;
import com.um.exptracker.entity.UserEntity;
import com.um.exptracker.enums.GenderEnum;
import com.um.exptracker.exception.UserNotFoundException;
import com.um.exptracker.repository.AddressRepository;
import com.um.exptracker.repository.UserRepository;
import com.um.exptracker.service.ExpenseService;
import com.um.exptracker.service.IncomeService;
import com.um.exptracker.service.UserService;
import com.um.exptracker.utils.Mapper;

@Service
//@AllArgsConstructor
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepo;
	@Autowired
	private AddressRepository addressRepo;
	@Autowired
	private Mapper mapper;
	@Autowired
	private IncomeService incomeService;
	@Autowired
	private ExpenseService expenseService;
	
	@Override
	public boolean doesUserNameExists(String userName) {
		return userRepo.findByUserName(userName) == null ? false : true;
	}

	@Override
	public boolean doesEmailExists(String email) {
		return userRepo.findByEmail(email) == null ? false : true;
	}

	@Override
	public boolean doesMobileNoExists(String mobileNo) {
		return userRepo.findByMobileNo(mobileNo) == null ? false : true;
	}

	@Override
	public ServerResponseDto createUser(UserDto userDto) {
		AddressEntity addressEntity = addressRepo.save(Mapper.toAddressEntity(userDto.getAddress()));
		UserEntity userEntity = mapper.toUserEntity(userDto);
		userEntity.setAddress(addressEntity);

		/* List<BankAccountEntity> bankAccounts = userDto.getBankAccounts().stream().map(bankAccountDTO -> {
			BankEntity bankEntity = bankRepo.findById(bankAccountDTO.getBankId())
					.orElseThrow(() -> new RuntimeException("Bank Not Found"));

			BankAccountEntity bankAccountEntity = mapper.toBankAccountEntity(bankAccountDTO);
			bankAccountEntity.setBank(bankEntity);
			bankAccountEntity.setUser(userEntity);
			return bankAccountEntity;
		}).collect(Collectors.toList());

		userEntity.setBankAccounts(bankAccounts); */

		UserEntity savedUserEntity = userRepo.save(userEntity);
		return new ServerResponseDto("user created successfuly with id : " + savedUserEntity.getUserId(),
				HttpStatus.OK.name());

		/*
		 * if(userDto != null) { userEntity = new UserEntity();
		 * userEntity.setFirstName(userDto.getFirstName());
		 * userEntity.setLastName(userDto.getLastName());
		 * userEntity.setUserName(userDto.getUserName());
		 * userEntity.setPassword(BCrypt.hashpw(userDto.getPassword(), salt));
		 * userEntity.setEmail(userDto.getEmail());
		 * userEntity.setMobileNo(userDto.getMobileNo());
		 * userEntity.setDateOfBirth(userDto.getDateOfBirth());
		 * userEntity.setGender(GenderEnum.valueOf(userDto.getGender().toUpperCase()));
		 * 
		 * if(userDto.getAddress() != null) { AddressDto addressDto =
		 * userDto.getAddress(); AddressEntity addressEntity = new AddressEntity();
		 * addressEntity.setCountry(addressDto.getCountry());
		 * addressEntity.setState(addressDto.getState());
		 * addressEntity.setCity(addressDto.getCity());
		 * addressEntity.setPincode(addressDto.getPincode());
		 * addressEntity.setLandmark(addressDto.getLandmark());
		 * addressEntity.setCreatedBy(CREATED_BY);
		 * addressEntity.setCreatedTime(LocalDateTime.now());
		 * 
		 * userEntity.setAddress(addressEntity); } userRepo.save(userEntity); }
		 */

	}

	@Override
	public UserDto fetchUserById(Long userId) {
		Optional<UserEntity> optional = userRepo.findById(userId);
		if (optional.isEmpty()) {
			throw new UserNotFoundException("no user found with the id");
		} else {
			return entityToDto(optional.get());
		}

	}

	@Override
	public ServerResponseDto updateUser(UserDto userDto) {
		Optional<UserEntity> optional = userRepo.findById(userDto.getUserId());
		UserEntity userEntity = null;
		if (!optional.isEmpty()) {
			userEntity = optional.get();
			userEntity.setFirstName(userDto.getFirstName());
			userEntity.setLastName(userDto.getLastName());
			userEntity.setUserName(userDto.getUserName());
			userEntity.setEmail(userDto.getEmail());
			userEntity.setMobileNo(userDto.getMobileNo());
			userEntity.setDateOfBirth(userDto.getDateOfBirth());
			userEntity.setGender(GenderEnum.valueOf(userDto.getGender()));

			if (userDto.getAddress() != null) {
				AddressEntity addressEntity = userEntity.getAddress();
				addressEntity.setCountry(userDto.getAddress().getCountry());
				addressEntity.setState(userDto.getAddress().getState());
				addressEntity.setCity(userDto.getAddress().getCity());
				addressEntity.setPincode(userDto.getAddress().getPincode());
				addressEntity.setLandmark(userDto.getAddress().getLandmark());
				addressEntity.setModifiedBy("user");
				addressEntity.setModifiedTime(LocalDateTime.now());
				userEntity.setAddress(addressEntity);
			}
			userRepo.save(userEntity);
			return new ServerResponseDto("user updated successfully", HttpStatus.OK.name());
		} else {
			throw new UserNotFoundException("no user found");
		}
	}

	private UserDto entityToDto(UserEntity userEntity) {
		UserDto userDto = new UserDto();
		if (userEntity != null) {
			if (userEntity.getAddress() != null) {
				AddressEntity addressEntity = userEntity.getAddress();
				AddressDto addressDto = new AddressDto();
				addressDto.setCountry(addressEntity.getCountry());
				addressDto.setState(addressEntity.getState());
				addressDto.setCity(addressEntity.getCity());
				addressDto.setPincode(addressEntity.getPincode());
				addressDto.setLandmark(addressEntity.getLandmark());
				userDto.setAddress(addressDto);
			}
			userDto.setUserId(userEntity.getUserId());
			userDto.setFirstName(userEntity.getFirstName());
			userDto.setLastName(userEntity.getLastName());
			userDto.setUserName(userEntity.getUserName());
			userDto.setEmail(userEntity.getEmail());
			userDto.setMobileNo(userEntity.getMobileNo());
			userDto.setDateOfBirth(userEntity.getDateOfBirth());
			userDto.setGender(userEntity.getGender().toString());
		}
		return userDto;
	}

	@Override
	public Map<String, Object> getIncomeAndExpenseReport(String fromDate, String toDate, Long bankAccId) {
		Map<String, Object> response = new HashMap<>();
		Double totalIncome = 0.0d;
		Double totalExpense = 0.0d;
		Double netIncome = 0.0d;
		List<IncomeDto> incomes = incomeService.getIncomesByDateRangeAndBankAccount(fromDate, toDate, bankAccId);
		List<ExpenseDto> expenses = expenseService.getExpensesByDateRangeAndBankAccount(fromDate, toDate, bankAccId);
		if(!CollectionUtils.isEmpty(incomes))	{
			for(IncomeDto income : incomes)	{
				totalIncome += income.getIncome();	
			}
		}
		if(!CollectionUtils.isEmpty(expenses))	{
			for(ExpenseDto expense : expenses)	{
				totalExpense += expense.getExpense();	
			}
		}
		netIncome = totalIncome - totalExpense;
		response.put("incomeList", incomes);
		response.put("expenseList", expenses);
		response.put("totalIncome", totalIncome);
		response.put("totalExpense", totalExpense);
		response.put("netIncome", netIncome);
		return response;
	}

}
