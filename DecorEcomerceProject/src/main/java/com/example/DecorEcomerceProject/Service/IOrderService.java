package com.example.DecorEcomerceProject.Service;

import com.example.DecorEcomerceProject.Entities.DTO.OrderDTO;
import com.example.DecorEcomerceProject.Entities.Enum.OrderStatus;
import com.example.DecorEcomerceProject.Entities.Order;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface IOrderService {
    Object createOrder(OrderDTO orderDTO) throws Exception;
    Order updateOrder(Long id, Order order) throws IOException;
    Order cancelOrder(Long id) throws IOException;
    Optional<Order> getOrderById(Long id);
    List<Order> getAllOrder();
    List<Order> getAllOrderByUseId(Long id);
}
