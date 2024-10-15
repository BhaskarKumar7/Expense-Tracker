package com.um.exptracker.service.impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.um.exptracker.entity.UserEntity;
import com.um.exptracker.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
	
	private final UserRepository userRepo;
	private final PasswordEncoder passwordEncoder;

	public UserEntity login(String email, String password) {
		UserEntity userEntity = userRepo.findByEmail(email);
		if(userEntity != null && (passwordEncoder.matches(password, userEntity.getPassword()))) {
				return userEntity;
		}
		return null;
	}
}
