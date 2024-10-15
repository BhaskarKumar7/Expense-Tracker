package com.um.exptracker.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.um.exptracker.entity.ExpenseEntity;

public interface ExpenseRepository extends JpaRepository<ExpenseEntity, Long> {

	List<ExpenseEntity> findAllByTransactionDateBetween(LocalDate startDate, LocalDate endDate);
	
	@Query("SELECT i FROM ExpenseEntity i WHERE i.transactionDate BETWEEN :startDate AND :endDate AND i.bankAccount.bankAccountId = :bankAccountId")
	List<ExpenseEntity> findByTransactionDateBetweenAndBankAccount_BankAccountId(
			@Param("startDate") LocalDateTime startDate,
			@Param("endDate") LocalDateTime endDate,
			@Param("bankAccountId") Long bankAccountId
			);
}
