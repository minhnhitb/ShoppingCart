package com.example.DecorEcomerceProject.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "discounts")
@Data
public class Discount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "start_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date start;

    @Column(name = "end_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date end;

    @Column(name = "usage_limit", nullable = false)
    private int limit;

    @Column(name = "discount_percentage", nullable = false)
    private int discount_percentage;

    @Column(name = "discount_amount_max", nullable = false)
    private int discount_amount_max;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY,mappedBy = "discount")
    private List<DiscountHistory> discountHistories;

}
