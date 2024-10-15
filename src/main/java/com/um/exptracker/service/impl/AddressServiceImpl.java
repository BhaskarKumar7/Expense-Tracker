package com.um.exptracker.service.impl;

import org.springframework.stereotype.Service;

import com.um.exptracker.entity.AddressEntity;
import com.um.exptracker.repository.AddressRepository;
import com.um.exptracker.service.AddressService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AddressServiceImpl implements AddressService {

	private AddressRepository addressRepo;
	
	@Override
	public AddressEntity fetchAddressById(Long addressId) {
	return addressRepo.findByAddressId(addressId);
	}

}
