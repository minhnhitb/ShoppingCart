package com.example.DecorEcomerceProject.Service.Impl;

import com.example.DecorEcomerceProject.Entities.*;
import com.example.DecorEcomerceProject.Entities.DTO.OrderDTO;
import com.example.DecorEcomerceProject.Entities.DTO.OrderItemDTO;
import com.example.DecorEcomerceProject.Entities.Enum.OrderStatus;
import com.example.DecorEcomerceProject.Repositories.DiscountRepository;
import com.example.DecorEcomerceProject.Repositories.OrderItemRepository;
import com.example.DecorEcomerceProject.Repositories.OrderRepository;
import com.example.DecorEcomerceProject.Repositories.ProductRepository;
import com.example.DecorEcomerceProject.Service.IOrderService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements IOrderService {
    private final ProductRepository productRepository;
    private final DiscountRepository discountRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final PaymentServiceImpl paymentService;
    public OrderServiceImpl(ProductRepository productRepository, DiscountRepository discountRepository, OrderRepository orderRepository, OrderItemRepository orderItemRepository, PaymentServiceImpl paymentService) {
        this.productRepository = productRepository;
        this.discountRepository = discountRepository;
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.paymentService = paymentService;
    }

    @Override
    @Transactional
    public Object createOrder(OrderDTO orderDTO) throws IOException {
        Order order = new Order();
        order.setStatus(OrderStatus.WAITING);
        order.setPaymentType(orderDTO.getPaymentType());
        order.setCreatedAt(LocalDateTime.now());
        order.setUser(orderDTO.getUser());
        order.setVoucher(orderDTO.getVoucher());
        order.setShippingAddress(orderDTO.getShippingAddress());
        List<OrderItem> orderItems = new ArrayList<>();
        for (OrderItemDTO orderItemDTO : orderDTO.getOrderItemDTOS()) {
            OrderItem orderItem = new OrderItem();
            Product product = productRepository.findById(orderItemDTO.getProductId()).get();
            orderItem.setProduct(product);
            List<DiscountHistory> discountHistories = product.getDiscountHistories();
            for (DiscountHistory discountHistory : discountHistories) {
                Discount discount = discountHistory.getDiscount();
                if (discount.getStart().isBefore(LocalDateTime.now()) && discount.getEnd().isAfter(LocalDateTime.now())&&discount.getLimit()>0) {
                    double discountAmount = Math.min(discount.getDiscountAmountMax(),
                            product.getPrice() * discount.getDiscountPercentage() / 100);
                    orderItem.setPrice(product.getPrice() - discountAmount);
                    orderItem.setDiscountHistory(discountHistory);
                    discount.setLimit(discount.getLimit()-1);
                    discountRepository.save(discount);
                    break;
                }
                orderItem.setPrice(product.getPrice());
            }
            orderItem.setQuantity(orderItemDTO.getQuantity());
            orderItems.add(orderItem);
        }
        order.setOrderItems(orderItems);
        double amount = 0;
        for (OrderItem orderItem : orderItems) {
            amount += orderItem.getPrice() * orderItem.getQuantity();
        }
        order.setAmount(amount);
        order = orderRepository.save(order);
        for (OrderItem orderItem : orderItems) {
            orderItem.setOrder(order);
            orderItemRepository.save(orderItem);
        }
        return paymentService.createPayment(order);
    }

    @Override
    public Optional<Order> getOrderById(Long Id){
        return orderRepository.findById(Id);
    }
}
