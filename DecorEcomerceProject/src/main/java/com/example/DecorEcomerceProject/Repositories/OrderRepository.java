package com.example.DecorEcomerceProject.Repositories;

import com.example.DecorEcomerceProject.Entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {
    @Query(value = "SELECT * FROM orders WHERE user_id = :UserId",nativeQuery = true)
    List<Order> findAllByUserId(Long UserId);
}
