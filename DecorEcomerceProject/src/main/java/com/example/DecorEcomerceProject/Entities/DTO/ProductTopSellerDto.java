package com.example.DecorEcomerceProject.Entities.DTO;

import lombok.Data;

import java.math.BigDecimal;
@Data
public class ProductTopSellerDto {
    private long id;
    private String name;
    private String description;
    private int inventory;
    private BigDecimal price;
    private String imageUrl;
    private BigDecimal totalBuy;
}
