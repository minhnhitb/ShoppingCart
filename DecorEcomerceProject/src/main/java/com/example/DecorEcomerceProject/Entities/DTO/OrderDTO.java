package com.example.DecorEcomerceProject.Entities.DTO;

import com.example.DecorEcomerceProject.Entities.ShippingAddress;
import com.example.DecorEcomerceProject.Entities.User;
import com.example.DecorEcomerceProject.Entities.Voucher;
import lombok.Data;

import java.util.List;

@Data
public class OrderDTO {
    private boolean paymentType;
    private Voucher voucher;
    private ShippingAddress shippingAddress;
    private User user;
    private List<OrderItemDTO> orderItemDTOS;
}
