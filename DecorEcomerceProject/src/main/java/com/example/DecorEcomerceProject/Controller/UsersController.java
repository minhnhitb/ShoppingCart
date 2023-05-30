package com.example.DecorEcomerceProject.Controller;

import com.example.DecorEcomerceProject.Entities.User;
import com.example.DecorEcomerceProject.Service.IUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UsersController {
    private final IUserService userService;
    public UsersController(IUserService userService){
        this.userService = userService;
    }
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }
    @PostMapping("/add")
    public ResponseEntity<Object> createUser(@Validated @RequestBody User user) throws Exception {
        try {
            Object createdUser = userService.createUser(user);
            return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }
    @GetMapping("/email")
    public ResponseEntity<User> getUserByEmail(@RequestParam("email") String email) {
        User user = userService.getByEmail(email);
        return ResponseEntity.ok(user);
    }
    @GetMapping("/phone")
    public ResponseEntity<User> getUserByPhone(@RequestParam("phone")String phone) {
        User user = userService.getUserByPhone(phone);
        return ResponseEntity.ok(user);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User userUpdate) {
        User user = userService.updateUser(id,userUpdate);
        return ResponseEntity.ok(user);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        return ResponseEntity.ok(userService.deleteUser(id));
    }
}
