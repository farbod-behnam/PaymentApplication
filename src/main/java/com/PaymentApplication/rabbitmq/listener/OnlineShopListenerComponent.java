package com.PaymentApplication.rabbitmq.listener;

import com.PaymentApplication.dto.request.payment.PaymentOrderRequest;
import com.PaymentApplication.dto.request.payment.PaymentUserRequest;
import com.PaymentApplication.service.IPaymentService;
import com.PaymentApplication.service.IUserService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OnlineShopListenerComponent
{

    private final IPaymentService paymentService;
    private final IUserService userService;

    @Autowired
    public OnlineShopListenerComponent(IPaymentService paymentService, IUserService userService)
    {
        this.paymentService = paymentService;
        this.userService = userService;
    }


    @RabbitListener(queues = "online_shop_order_queue")
    public void orderQueueListener(PaymentOrderRequest paymentOrderRequest)
    {
        paymentService.chargeCard(paymentOrderRequest);
    }

    @RabbitListener(queues = "online_shop_user_queue")
    public void userQueueListener(PaymentUserRequest paymentUserRequest)
    {
        userService.saveUserRecord(paymentUserRequest);
    }

}
