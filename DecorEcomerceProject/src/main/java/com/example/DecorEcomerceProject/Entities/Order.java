package com.example.DecorEcomerceProject.Entities;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
@Data
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

//    @OneToMany(mappedBy = "order")
//    private List<OrderItem> orderItems;
    private Date createdAt;
    private Date updatedAt;




}
