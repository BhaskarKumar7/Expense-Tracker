package com.um.exptracker.service;

import java.util.Map;

import com.um.exptracker.dto.ServerResponseDto;
import com.um.exptracker.dto.UserDto;

public interface UserService {

	public boolean doesUserNameExists(String userName);
	public boolean doesEmailExists(String email);
	public boolean doesMobileNoExists(String mobileNo);
	public ServerResponseDto createUser(UserDto userDto);
	public ServerResponseDto updateUser(UserDto userDto);
	public UserDto fetchUserById(Long userId);
	public Map<String, Object> getIncomeAndExpenseReport(String fromDate,String toDate,Long bankAccId);
}
