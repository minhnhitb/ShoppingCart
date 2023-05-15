package com.example.DecorEcomerceProject.Repositories;

import com.example.DecorEcomerceProject.Entities.Product;
import com.example.DecorEcomerceProject.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
//    User findByUsername(String username);
    User findByEmail(String email);
    User findUserByPhone(String phone);
}
