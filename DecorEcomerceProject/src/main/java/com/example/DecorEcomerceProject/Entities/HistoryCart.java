package com.example.DecorEcomerceProject.Entities;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
@Data
@Entity
@Table(name = "history_carts")
public class HistoryCart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @Column(name = "order_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date orderDate;

}
