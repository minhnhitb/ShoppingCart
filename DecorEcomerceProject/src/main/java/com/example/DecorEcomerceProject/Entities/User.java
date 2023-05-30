package com.example.DecorEcomerceProject.Entities;

import com.example.DecorEcomerceProject.Entities.Enum.Level;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @Column(nullable = false)
    private String address;
    @Column
    private int point;
    @Column
    private Level level;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY,mappedBy = "user")
    private List<VoucherUser> voucherUsers;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY,mappedBy = "user")
    private List<ShippingAddress> shippingAddresses;
}