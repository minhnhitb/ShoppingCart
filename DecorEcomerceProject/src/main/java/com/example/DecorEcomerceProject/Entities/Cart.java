package com.example.DecorEcomerceProject.Entities;

import lombok.Data;

import javax.persistence.*;
import java.util.List;
@Data
@Entity
@Table(name = "carts")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "cart")
    private List<CartItem> cartItems;
}
