package com.um.exptracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.um.exptracker.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

	UserEntity findByUserName(String userName);
	UserEntity findByEmail(String email);
	UserEntity findByMobileNo(String mobileNo);
	
}
