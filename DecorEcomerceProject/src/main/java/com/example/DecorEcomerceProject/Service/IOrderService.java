package com.example.DecorEcomerceProject.Service;

import com.example.DecorEcomerceProject.Entities.DTO.OrderDTO;
import com.example.DecorEcomerceProject.Entities.Order;
import com.example.DecorEcomerceProject.ResponseAPI.ResponseObject;

import java.io.IOException;

public interface IOrderService {
    Order createOrder(OrderDTO orderDTO) throws IOException;
}
