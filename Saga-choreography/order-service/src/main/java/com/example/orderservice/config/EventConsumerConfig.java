package com.example.orderservice.config;

import com.example.commondtos.event.PaymentEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
public class EventConsumerConfig {
    @Autowired
    private OrderStatusUpdateHandle handler;
    @Bean
    public Consumer<PaymentEvent> paymentEventConsumer(){
        // listen payment-event-topic
        // will check payment status
        // if payment status completed -> complete the order
        // else cancel its
        return (paymentEvent) -> handler.updateOrder(paymentEvent.getPaymentRequestDto().getOrderId(), purchaseOrder -> {
            purchaseOrder.setPaymentStatus(paymentEvent.getPaymentStatus());
        });
    }
}
