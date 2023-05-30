package com.example.DecorEcomerceProject.Service;

import com.example.DecorEcomerceProject.Entities.ShippingAddress;
import com.example.DecorEcomerceProject.Entities.User;

import java.sql.SQLException;
import java.util.List;

public interface IUserService {
    Object createUser(User user) throws Exception;
    User getUserById(Long id);
    User updateUser(Long id,User user);
    String deleteUser(Long id);
    List<User> getAllUsers();
//    User findUserByUsername(String username);
    User getByEmail(String email);
    User getUserByPhone(String phone);
    User updateShippingAddress(Long userId, Long ShippingAddressId, ShippingAddress shippingAddress);
}
