package com.example.orderservice.controller;

import com.example.commondtos.dto.OrderRequestDto;
import com.example.orderservice.entity.PurchaseOrder;
import com.example.orderservice.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/create")
    public PurchaseOrder createOrder(@RequestBody OrderRequestDto orderRequestDto){
        return orderService.createOrder(orderRequestDto);
    }
    @GetMapping
    public List<PurchaseOrder> getOrders(){
        return orderService.getAllOrders();
    }
}
