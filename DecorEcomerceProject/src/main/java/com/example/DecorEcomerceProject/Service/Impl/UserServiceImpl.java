package com.example.DecorEcomerceProject.Service.Impl;

import com.example.DecorEcomerceProject.Entities.User;
import com.example.DecorEcomerceProject.Repositories.UserRepository;
import com.example.DecorEcomerceProject.Service.IUserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
@Service

public class UserServiceImpl implements IUserService{
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder){
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
        return userRepository.findById(id).orElseThrow(()->new EntityNotFoundException("User not found with id " + id));
    }

    @Override
    public User updateUser(Long id, User user) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));

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
        User user = userRepository.findById(id).get();
        if(user == null){
            return "Cannot find User " +id;
        }else{
            userRepository.delete(user);
            return "User "+id+ " has been deleted !";
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

//    @Override
//    public User findUserByUsername(String username) {
//        return userRepository.findByUsername(username);
//    }


}
