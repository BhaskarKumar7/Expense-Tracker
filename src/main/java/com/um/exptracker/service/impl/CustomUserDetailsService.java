package com.um.exptracker.service.impl;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.um.exptracker.config.CustomUserDetails;
import com.um.exptracker.entity.UserEntity;
import com.um.exptracker.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

	private final UserRepository userRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserEntity userEntity = userRepo.findByEmail(username);
		if (userEntity == null) {
			throw new UsernameNotFoundException("User not found with email :" + username);
		}
		return new CustomUserDetails(userEntity);
	}

}
