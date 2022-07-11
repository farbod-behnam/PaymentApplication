package com.PaymentApplication.rabbitmq.service;

import com.PaymentApplication.dto.request.payment.PaymentOrderRequest;
import org.springframework.stereotype.Service;

@Service
public interface IOnlineShopService
{
    void sendBackOrder(PaymentOrderRequest orderRequest);


}
