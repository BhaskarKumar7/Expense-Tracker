package com.um.exptracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.um.exptracker.entity.BankEntity;

public interface BankRepository extends JpaRepository<BankEntity, Long> {

	BankEntity findByBankName(String bankName);
	BankEntity findByBankId(Long bankId);
}
