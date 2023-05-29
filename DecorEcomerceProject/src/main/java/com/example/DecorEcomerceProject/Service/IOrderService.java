package com.example.DecorEcomerceProject.Service;

import com.example.DecorEcomerceProject.Entities.DTO.OrderDTO;
import com.example.DecorEcomerceProject.Entities.Order;

import java.io.IOException;
import java.util.Optional;

public interface IOrderService {
    Object createOrder(OrderDTO orderDTO) throws IOException;
    Optional<Order> getOrderById(Long id);
}
