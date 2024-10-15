package com.um.exptracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.um.exptracker.entity.AddressEntity;

public interface AddressRepository extends JpaRepository<AddressEntity, Long> {

	public AddressEntity findByAddressId(Long addressId);
}
