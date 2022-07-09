package com.PaymentApplication.service.impl;

import com.PaymentApplication.dto.request.payment.PaymentOrderRequest;
import com.PaymentApplication.entity.Transaction;
import com.PaymentApplication.entity.Wallet;
import com.PaymentApplication.enums.TransactionStatusEnum;
import com.PaymentApplication.repository.ITransactionRepository;
import com.PaymentApplication.service.ITransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest
{

    private ITransactionService underTestTransactionService;

    @Mock
    private ITransactionRepository transactionRepository;

    @BeforeEach
    void setUp()
    {
        MockitoAnnotations.openMocks(this);
        underTestTransactionService = new TransactionService(transactionRepository);
    }

    @Test
    void createTransaction_shouldReturnCreatedTransaction()
    {
        // given

        PaymentOrderRequest paymentOrderRequest = new PaymentOrderRequest(
                "orderId19",
                "john",
                new BigDecimal("1919"),
                TransactionStatusEnum.IN_PROCESS.name()
        );


        Transaction transaction = new Transaction(
                19L,
                paymentOrderRequest.getOrderId(),
                paymentOrderRequest.getTotalPrice(),
                paymentOrderRequest.getTransactionStatus()

        );

        // when
        underTestTransactionService.createTransaction(paymentOrderRequest);

        // then
        ArgumentCaptor<Transaction> transactionArgumentCaptor = ArgumentCaptor.forClass(Transaction.class);
        verify(transactionRepository).save(transactionArgumentCaptor.capture());
        Transaction capturedTransaction = transactionArgumentCaptor.getValue();
        assertThat(capturedTransaction.getId()).isEqualTo(null);
        assertThat(capturedTransaction.getOnlineShopOrderId()).isEqualTo(transaction.getOnlineShopOrderId());
        assertThat(capturedTransaction.getTotalPrice()).isEqualTo(transaction.getTotalPrice());
        assertThat(capturedTransaction.getTransactionStatus()).isEqualTo(transaction.getTransactionStatus());
    }
}