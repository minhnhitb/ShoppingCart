package com.example.DecorEcomerceProject.Repositories;

import com.example.DecorEcomerceProject.Entities.User;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    boolean existsByPhone(String phone);
    User findByEmail(String email);
    User findUserByPhone(String phone);
}
