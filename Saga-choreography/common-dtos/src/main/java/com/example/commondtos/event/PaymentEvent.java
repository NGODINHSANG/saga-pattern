package com.example.commondtos.event;

import com.example.commondtos.dto.PaymentRequestDto;

import java.util.Date;
import java.util.UUID;

public class PaymentEvent implements Event{
    private UUID eventId = UUID.randomUUID();
    private Date eventDate = new Date();
    private PaymentRequestDto paymentRequestDto;
    private PaymentStatus paymentStatus;
    @Override
    public UUID getEventID() {
        return null;
    }

    @Override
    public Date getDate() {
        return null;
    }

    public PaymentEvent(PaymentRequestDto paymentRequestDto, PaymentStatus paymentStatus) {
        this.paymentRequestDto = paymentRequestDto;
        this.paymentStatus = paymentStatus;
    }
}
