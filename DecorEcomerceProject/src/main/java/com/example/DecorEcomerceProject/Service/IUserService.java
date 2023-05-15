package com.example.DecorEcomerceProject.Service;

import com.example.DecorEcomerceProject.Entities.User;

import java.util.List;

public interface IUserService {
    User createUser(User user);
    User getUserById(Long id);
    User updateUser(Long id,User user);
    String deleteUser(Long id);
    List<User> getAllUsers();
//    User findUserByUsername(String username);

    User getByEmail(String email);
    User getUserByPhone(String phone);
}
