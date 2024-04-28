package com.example.orderservice.service;

import com.example.commondtos.dto.OrderRequestDto;
import com.example.commondtos.event.OrderStatus;
import com.example.orderservice.entity.PurchaseOrder;
import com.example.orderservice.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderStatusPublisher orderStatusPublisher;

    @Transactional
    public PurchaseOrder createOrder(OrderRequestDto orderRequestDto){
        PurchaseOrder order = orderRepository.save(convertDtoToEntity(orderRequestDto));
        orderRequestDto.setOrderId(order.getId());

        // produce kafka event with status order created

        orderStatusPublisher.publishOrderEvent(orderRequestDto, OrderStatus.ORDER_CREATED);
        return  order;
    }

    public List<PurchaseOrder> getAllOrders(){
        return orderRepository.findAll();
    }

    private PurchaseOrder convertDtoToEntity(OrderRequestDto dto){
        PurchaseOrder purchaseOrder = new PurchaseOrder();
        purchaseOrder.setProductId(dto.getProductId());
        purchaseOrder.setUserId(dto.getUserId());
        purchaseOrder.setOrderStatus(OrderStatus.ORDER_CREATED);
        purchaseOrder.setPrice(dto.getAmount());
        return purchaseOrder;
    }
}
