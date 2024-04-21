package com.example.orderservice.service;

import com.example.commondtos.dto.OrderRequestDto;
import com.example.commondtos.event.OrderEvent;
import com.example.commondtos.event.OrderStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Sinks;

@Service
public class OrderStatusPublisher {
    private Sinks.Many<OrderEvent> orderSinks;

    public void publishOrderEvent(OrderRequestDto orderRequestDto, OrderStatus orderStatus){
        OrderEvent orderEvent = new OrderEvent(orderRequestDto, orderStatus);
        orderSinks.tryEmitNext(orderEvent);
    }
}
