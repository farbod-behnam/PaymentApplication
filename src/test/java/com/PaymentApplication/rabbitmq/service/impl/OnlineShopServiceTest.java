package com.PaymentApplication.rabbitmq.service.impl;

import com.PaymentApplication.dto.request.payment.PaymentOrderRequest;
import com.PaymentApplication.enums.TransactionStatusEnum;
import com.PaymentApplication.rabbitmq.service.IOnlineShopService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;

import java.math.BigDecimal;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

class OnlineShopServiceTest
{

    private IOnlineShopService underTestOnlineShopService;

    private RabbitTemplate rabbitTemplate;


    @Value("${payment.app.rabbitmq.exchange}")
    private String PAYMENT_APP_EXCHANGE;


    @Value("${payment.app.rabbitmq.routingKey.order}")
    private String PAYMENT_APP_ORDER_ROUTING_KEY;

    @BeforeEach
    void setUp()
    {
        rabbitTemplate = Mockito.mock(RabbitTemplate.class);
        underTestOnlineShopService = new OnlineShopService(rabbitTemplate);
    }

    @Test
    void sendBackOrder_orderQueue_messageShouldBePublished()
    {
        // given
        PaymentOrderRequest paymentOrderRequest = new PaymentOrderRequest(
                "orderId19",
                "john.wick",
                new BigDecimal("1400"),
                TransactionStatusEnum.IN_PROCESS.name()
        );

        // then
        assertThatCode(() -> this.underTestOnlineShopService.sendBackOrder(paymentOrderRequest)).doesNotThrowAnyException();


        ArgumentCaptor<PaymentOrderRequest> paymentOrderRequestArgumentCaptor = ArgumentCaptor.forClass(PaymentOrderRequest.class);
        verify(rabbitTemplate).convertAndSend(eq(PAYMENT_APP_EXCHANGE), eq(PAYMENT_APP_ORDER_ROUTING_KEY), paymentOrderRequestArgumentCaptor.capture());
        PaymentOrderRequest capturedPaymentOrderRequest = paymentOrderRequestArgumentCaptor.getValue();

        assertThat(capturedPaymentOrderRequest.getOrderId()).isEqualTo(paymentOrderRequest.getOrderId());
        assertThat(capturedPaymentOrderRequest.getUsername()).isEqualTo(paymentOrderRequest.getUsername());
        assertThat(capturedPaymentOrderRequest.getTotalPrice()).isEqualTo(paymentOrderRequest.getTotalPrice());
        assertThat(capturedPaymentOrderRequest.getTransactionStatus()).isEqualTo(paymentOrderRequest.getTransactionStatus());
    }
}