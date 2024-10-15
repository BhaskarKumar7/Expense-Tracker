package com.um.exptracker.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.um.exptracker.dto.BankAccountDto;
import com.um.exptracker.dto.BankDto;
import com.um.exptracker.dto.CategoryDto;
import com.um.exptracker.dto.ServerResponseDto;
import com.um.exptracker.dto.TempBankDto;
import com.um.exptracker.dto.UserDto;
import com.um.exptracker.entity.UserEntity;
import com.um.exptracker.enums.AccountTypeEnum;
import com.um.exptracker.service.BankAccountService;
import com.um.exptracker.service.BankService;
import com.um.exptracker.service.CategoryService;
import com.um.exptracker.service.UserService;
import com.um.exptracker.service.impl.AuthService;
import com.um.exptracker.utils.JWTUtil;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/exptracker")
@AllArgsConstructor
@Validated
//@CrossOrigin(origins = "*", allowedHeaders = "*", methods = { RequestMethod.POST, RequestMethod.GET,
//		RequestMethod.OPTIONS })
@CrossOrigin(value = "http://localhost:4200")
public class ExpenseTrackerController {

	private UserService userService;
	private CategoryService categoryService;
	private BankService bankService;
	private BankAccountService bankAccountService;
	private AuthService authService;
	private JWTUtil jwtUtil;
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody Map<String, String> credentials) {
		String email = credentials.get("email");
		String password = credentials.get("password");
		UserEntity userEntity = authService.login(email, password);
		if(userEntity != null) {
			String token = jwtUtil.generateToken(email);
			Map<String, String> respMap = new HashMap<>();
			respMap.put("token", token);
			respMap.put("userId", userEntity.getUserId().toString());
			return ResponseEntity.ok(respMap);
		}
		else {
			return ResponseEntity.status(401).body("Invalid Credentials");
		}
	}

	@PostMapping("/user")
	public ResponseEntity<ServerResponseDto> saveUser(@RequestBody @Valid UserDto userDto) {
		return new ResponseEntity<>(userService.createUser(userDto), HttpStatus.OK);
	}

	@PostMapping("/category")
	public ResponseEntity<ServerResponseDto> saveAllCategories(@RequestBody @Valid CategoryDto categoryDto) {
		return new ResponseEntity<>(categoryService.createCategory(categoryDto.getCategoryName()), HttpStatus.OK);
	}

	@PostMapping("/bankAccount")
	public ResponseEntity<ServerResponseDto> saveBankAccount(@RequestBody @Valid BankAccountDto bankAccountDto) {
		return new ResponseEntity<>(bankAccountService.createBankAccount(bankAccountDto), HttpStatus.OK);
	}

	@GetMapping("/banks")
	public ResponseEntity<List<BankDto>> getBanks() {
		return new ResponseEntity<>(bankService.fetchAllBanks(), HttpStatus.OK);
	}

	@GetMapping("/allAccTypes")
	public List<AccountTypeEnum> getAllAccountTypes() {
		return bankAccountService.fetchAllAccountTypes();
	}

	/*
	 * @PostMapping("/updateUser") public ResponseEntity<ServerResponseDto>
	 * modifyUser(@RequestBody UserDto userDto) { return new
	 * ResponseEntity<>(userService.updateUser(userDto),HttpStatus.OK); }
	 */

	/*
	 * public ResponseEntity<ServerResponseDto> addBankAccount(@RequestBody @Valid
	 * BankAccountDto bankAccountDto) {
	 * 
	 * 
	 * }
	 */

	@PostMapping("/bank")
	public ResponseEntity<ServerResponseDto> addBank(@RequestBody @Valid TempBankDto bankDto) {
		return new ResponseEntity<>(bankService.createBank(bankDto), HttpStatus.OK);
	}
	
	@PostMapping("/user/bankAccounts")
	public ResponseEntity<List<BankAccountDto>> getAllBankAccountsByUserId(@RequestBody Map<String, Long> payload) {
		return new ResponseEntity<>(bankAccountService.fetchAllBankAccounts(payload.get("userId")),HttpStatus.OK); 
	}
	
	@GetMapping("/categories/income")
	public ResponseEntity<List<CategoryDto>> getIncomeCategories() {
		return new ResponseEntity<>(categoryService.fetchCategoriesByType("INCOME"),HttpStatus.OK);
	}
	
	@GetMapping("/categories/expense")
	public ResponseEntity<List<CategoryDto>> getExpenseCategories() {
		return new ResponseEntity<>(categoryService.fetchCategoriesByType("EXPENSE"),HttpStatus.OK);
	}
	
	@PostMapping("/user/incomeExpReport")
	public ResponseEntity<Map<String, Object>> getIncomeExpenseReport(@RequestBody Map<String, String> payload){
		return new ResponseEntity<>(userService.getIncomeAndExpenseReport(payload
				.get("startDate"),payload.get("endDate"), Long.parseLong
				(payload.get("bankAccountId"))),HttpStatus.OK);
	}
}
