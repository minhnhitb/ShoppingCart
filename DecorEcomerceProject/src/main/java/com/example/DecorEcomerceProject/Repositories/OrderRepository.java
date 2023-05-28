package com.example.DecorEcomerceProject.Repositories;

import com.example.DecorEcomerceProject.Entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {
}
