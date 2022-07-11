package com.PaymentApplication.rabbitmq.service.impl;

import com.PaymentApplication.dto.request.payment.PaymentOrderRequest;
import com.PaymentApplication.rabbitmq.service.IOnlineShopService;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Service
class OnlineShopService implements IOnlineShopService
{

    @Value("${payment.app.rabbitmq.exchange}")
    private String PAYMENT_APP_EXCHANGE;

    @Value("${payment.app.rabbitmq.routingKey.order}")
    private String PAYMENT_APP_ORDER_ROUTING_KEY;

    private final RabbitTemplate rabbitTemplate;

    public OnlineShopService(RabbitTemplate rabbitTemplate)
    {
        this.rabbitTemplate = rabbitTemplate;
    }


    @Override
    public void sendBackOrder(PaymentOrderRequest orderRequest)
    {
        try
        {
            rabbitTemplate.convertAndSend(PAYMENT_APP_EXCHANGE, PAYMENT_APP_ORDER_ROUTING_KEY, orderRequest);
        } catch (AmqpException e)
        {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }
}
