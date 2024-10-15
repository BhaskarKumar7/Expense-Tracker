package com.um.exptracker.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.um.exptracker.entity.BankAccountEntity;

public interface BankAccountRepository extends JpaRepository<BankAccountEntity, Long> {

	BankAccountEntity findByAccountNo(String accountNo);
	List<BankAccountEntity> findByUser_UserId(Long userId);
}
