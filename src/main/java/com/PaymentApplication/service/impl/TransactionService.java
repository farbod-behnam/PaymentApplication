package com.PaymentApplication.service.impl;

import com.PaymentApplication.dto.request.payment.PaymentOrderRequest;
import com.PaymentApplication.entity.Transaction;
import com.PaymentApplication.repository.ITransactionRepository;
import com.PaymentApplication.service.ITransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TransactionService implements ITransactionService
{

    private final ITransactionRepository transactionRepository;

    @Autowired
    public TransactionService(ITransactionRepository transactionRepository)
    {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public Transaction createTransaction(PaymentOrderRequest orderRequest)
    {
        Transaction transaction = new Transaction(
                null,
                orderRequest.getOrderId(),
                orderRequest.getTotalPrice(),
                orderRequest.getTransactionStatus()
        );

        return transactionRepository.save(transaction);
    }
}
