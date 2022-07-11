package com.PaymentApplication.service;

import com.PaymentApplication.dto.request.payment.PaymentOrderRequest;
import com.PaymentApplication.entity.Transaction;

public interface ITransactionService
{
    public Transaction createTransaction(PaymentOrderRequest orderRequest);
}
