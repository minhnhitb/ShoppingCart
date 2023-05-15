package com.example.DecorEcomerceProject.Entities;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column( unique = true, nullable = false)
    private String name;
    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @Column( nullable = false)
    private String password;
    @Column( unique = true, nullable = false)
    private String email;
    @Column(unique = true, nullable = false)
    private String phone;
    @Column(unique = true, nullable = false)
    private String address;
//    @OneToMany(mappedBy = "user")
//    private List<Order> orders;
//    @OneToOne(mappedBy = "customer")
//    private Cart cart;

}
