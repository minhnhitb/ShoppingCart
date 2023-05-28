package com.example.DecorEcomerceProject.Repositories;

import com.example.DecorEcomerceProject.Entities.VoucherUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoucherUserRepository extends JpaRepository<VoucherUser,Long> {
}
