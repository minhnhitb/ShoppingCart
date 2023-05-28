package com.example.DecorEcomerceProject.Entities.DTO;

import com.example.DecorEcomerceProject.Entities.Order;
import lombok.Data;

@Data
public class PaymentDTO {
    private Order order;
    private String IpAddr;
    private String locate;
}
