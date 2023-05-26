package com.example.DecorEcomerceProject.Entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "discounthistories")
public class DiscountHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "discount_id")
    private Discount discount;
}
