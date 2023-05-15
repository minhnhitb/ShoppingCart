package com.example.DecorEcomerceProject.Entities;


import com.example.DecorEcomerceProject.Entities.Enum.ProductStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "products")
@Data
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;
    @Column
    private String description;
    @Column
    private int inventory;
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ProductStatus status;
    @Column(name = "unit_price")
    private BigDecimal price;
    @Column(name = "image_url")
    private String imageUrl;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id")
    private Category category;
    @Column(name = "date_created", nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @Column(name = "last_updated", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;
}

