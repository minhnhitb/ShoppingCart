package com.example.DecorEcomerceProject.Repositories;

import com.example.DecorEcomerceProject.Entities.DiscountHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DiscountHistoryRepository extends JpaRepository<DiscountHistory,Long> {
    void deleteByProductIdAndDiscountId(Long productId, Long discountId);
}
