package com.example.commondtos.event;

import com.example.commondtos.dto.OrderRequestDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@NoArgsConstructor
@Data
public class OrderEvent  implements Event {
    private OrderRequestDto orderRequestDto;
    private OrderStatus orderStatus;
    private UUID eventId= UUID.randomUUID();
    private Date eventDate = new Date();

    @Override
    public UUID getEventID() {
        return eventId;
    }

    @Override
    public Date getDate() {
        return eventDate;
    }

    public OrderEvent(OrderRequestDto orderRequestDto, OrderStatus orderStatus) {
        this.orderRequestDto = orderRequestDto;
        this.orderStatus = orderStatus;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public OrderRequestDto getOrderRequestDto() {
        return orderRequestDto;
    }

    public void setOrderRequestDto(OrderRequestDto orderRequestDto) {
        this.orderRequestDto = orderRequestDto;
    }
}
