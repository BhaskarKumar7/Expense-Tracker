package com.um.exptracker.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.um.exptracker.entity.IncomeEntity;

public interface IncomeRepository extends JpaRepository<IncomeEntity, Long> {

	List<IncomeEntity> findAllByTransactionDateBetween(LocalDateTime startDate, LocalDateTime endDate);
	
	Optional<IncomeEntity> findByCategory_CategoryId(Long categoryId);
	
	@Query("SELECT i FROM IncomeEntity i WHERE i.transactionDate BETWEEN :startDate AND :endDate AND i.bankAccount.bankAccountId = :bankAccountId")
	List<IncomeEntity> findByTransactionDateBetweenAndBankAccount_BankAccountId(
				@Param("startDate") LocalDateTime startDate,
				@Param("endDate") LocalDateTime endDate,
				@Param("bankAccountId") Long bankAccountId
			);
	
	 List<IncomeEntity> findByBankAccount_BankAccountId(Long bankAccountId);
}
