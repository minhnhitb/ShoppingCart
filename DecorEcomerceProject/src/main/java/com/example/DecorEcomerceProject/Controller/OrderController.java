package com.example.DecorEcomerceProject.Controller;

import com.example.DecorEcomerceProject.Entities.DTO.OrderDTO;
import com.example.DecorEcomerceProject.Entities.Order;
import com.example.DecorEcomerceProject.ResponseAPI.ResponseObject;
import com.example.DecorEcomerceProject.Service.IOrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order")
public class OrderController {
    private final IOrderService orderService;

    public OrderController(IOrderService orderService) {
        this.orderService = orderService;
    }
//    @GetMapping("/")
//    public ResponseEntity<?> getAllOrder(){
//        if (orderService.getAllDiscount().size()==0){
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("List discount is empty!");
//        }
//        return ResponseEntity.status(HttpStatus.OK).body(discountService.getAllDiscount());
//    }
    @PostMapping("/create")
    public ResponseEntity<?> createOrder(@Validated @RequestBody OrderDTO orderDTO) {
        try {
            Order order = orderService.createOrder(orderDTO);
            return ResponseEntity.ok(order);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
