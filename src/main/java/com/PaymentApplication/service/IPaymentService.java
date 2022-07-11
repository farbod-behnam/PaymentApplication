package com.PaymentApplication.service;

import com.PaymentApplication.dto.request.payment.PaymentOrderRequest;
import org.springframework.stereotype.Service;

@Service
public interface IPaymentService
{
    void chargeCard(PaymentOrderRequest paymentOrderRequest);
}
