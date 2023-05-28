package com.example.DecorEcomerceProject.Repositories;

import com.example.DecorEcomerceProject.Entities.ShippingAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShippingAddressRepository extends JpaRepository<ShippingAddress,Long> {
}
