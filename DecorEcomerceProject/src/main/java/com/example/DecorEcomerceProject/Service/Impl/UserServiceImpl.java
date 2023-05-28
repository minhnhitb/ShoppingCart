package com.example.DecorEcomerceProject.Service.Impl;

import com.example.DecorEcomerceProject.Entities.ShippingAddress;
import com.example.DecorEcomerceProject.Entities.User;
import com.example.DecorEcomerceProject.Repositories.ShippingAddressRepository;
import com.example.DecorEcomerceProject.Repositories.UserRepository;
import com.example.DecorEcomerceProject.Service.IUserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service

public class UserServiceImpl implements IUserService{
    private final ShippingAddressRepository shippingAddressRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(ShippingAddressRepository shippingAddressRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.shippingAddressRepository = shippingAddressRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User createUser(User user) {
        user.setName(user.getName());
        user.setUsername(user.getUsername());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setEmail(user.getEmail());
        user.setPhone(user.getPhone());
        user.setAddress(user.getAddress());
        return userRepository.save(user);
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(()->new EntityNotFoundException("Not found user with id: " + id));
    }

    @Override
    public User updateUser(Long id, User user) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Not found user with id: " + id));
        existingUser.setUsername(user.getUsername());
        existingUser.setPassword(passwordEncoder.encode(user.getPassword()));
        existingUser.setEmail(user.getEmail());
        existingUser.setPhone(user.getPhone());
        existingUser.setAddress(user.getAddress());
        // Update other fields as necessary

        return userRepository.save(existingUser);
    }

    @Override
    public String deleteUser(Long id) {
        Optional<User> user = userRepository.findById(id);

        if(!user.isPresent()){
            return "Not found user with id: " +id;
        }else{
            userRepository.delete(user.get());
            return "User with id: "+id+ " has been deleted!";
        }
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User getUserByPhone(String phone) {
        return  userRepository.findUserByPhone(phone);
    }

    @Override
    public User updateShippingAddress(Long userId, Long ShippingAddressId, ShippingAddress shippingAddress){
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("Not found user!"));
        ShippingAddress address = shippingAddressRepository.findById(ShippingAddressId).orElseThrow(() -> new EntityNotFoundException("Not found address!"));
        address.setActive(false);
        ShippingAddress newAddress = new ShippingAddress();
        newAddress.setName(shippingAddress.getName());
        newAddress.setPhone(shippingAddress.getPhone());
        newAddress.setAddress(shippingAddress.getAddress());
        newAddress.setUser(user);
        shippingAddressRepository.save(newAddress);
        shippingAddressRepository.save(address);
        return user;
    }
//    @Override
//    public User findUserByUsername(String username) {
//        return userRepository.findByUsername(username);
//    }


}
