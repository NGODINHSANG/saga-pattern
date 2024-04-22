package com.example.paymentservice.service;

import com.example.commondtos.dto.OrderRequestDto;
import com.example.commondtos.dto.PaymentRequestDto;
import com.example.commondtos.event.OrderEvent;
import com.example.commondtos.event.PaymentEvent;
import com.example.commondtos.event.PaymentStatus;
import com.example.paymentservice.entity.UserBalance;
import com.example.paymentservice.entity.UserTransaction;
import com.example.paymentservice.repository.UserBalanceRepository;
import com.example.paymentservice.repository.UserTransactionRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class PaymentService {

    @Autowired
    private UserBalanceRepository userBalanceRepository;

    @Autowired
    private UserTransactionRepository userTransactionRepository;

    @PostConstruct
    public void initUserBalanceInDB(){
        userBalanceRepository.saveAll(Stream.of(new UserBalance(101, 5000),
                new UserBalance(102, 3000),
                new UserBalance(103, 4000),
                new UserBalance(104, 20000),
                new UserBalance(105, 999)).collect(Collectors.toList()));
    }

    // get the user id
    // check the balance availability
    // perform payment
    @Transactional
    public PaymentEvent newOrderEvent(OrderEvent orderEvent) {
        OrderRequestDto orderRequestDto = orderEvent.getOrderRequestDto();
        PaymentRequestDto paymentRequestDto = new PaymentRequestDto(orderRequestDto.getOrderId(),
                orderRequestDto.getUserId(), orderRequestDto.getAmount());
        return userBalanceRepository.findById(orderRequestDto.getUserId())
                .filter(userBalance -> userBalance.getPrice()>orderRequestDto.getAmount())
                .map(userBalance -> {
                    userBalance.setPrice(userBalance.getPrice() - orderRequestDto.getAmount());
                    userTransactionRepository.save(new UserTransaction(orderRequestDto.getOrderId(),
                            orderRequestDto.getUserId(), orderRequestDto.getAmount()));
                    return new PaymentEvent(paymentRequestDto, PaymentStatus.PAYMENT_COMPLETED);
                }).orElse(new PaymentEvent(paymentRequestDto, PaymentStatus.PAYMENT_FAILED));
    }
    @Transactional
    public void cancelOrderEvent(OrderEvent orderEvent) {
        userTransactionRepository.findById(orderEvent.getOrderRequestDto().getOrderId())
                .ifPresent(userTransaction -> {
                    userTransactionRepository.delete(userTransaction);
                    userTransactionRepository.findById(userTransaction.getUserId())
                            .ifPresent(userBalance-> userBalance.setAmount(userBalance.getAmount() + userTransaction.getAmount()));
                });
    }
}
