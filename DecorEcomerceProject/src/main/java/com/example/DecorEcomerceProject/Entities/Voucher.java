package com.example.DecorEcomerceProject.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Voucher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany (fetch = FetchType.LAZY, mappedBy = "voucher")
    private List<Order> orders;
}
