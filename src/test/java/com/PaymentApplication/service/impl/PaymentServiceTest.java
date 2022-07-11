package com.PaymentApplication.service.impl;

import com.PaymentApplication.dto.request.payment.PaymentOrderRequest;
import com.PaymentApplication.entity.AppUser;
import com.PaymentApplication.entity.Transaction;
import com.PaymentApplication.entity.Wallet;
import com.PaymentApplication.enums.TransactionStatusEnum;
import com.PaymentApplication.exception.NotFoundException;
import com.PaymentApplication.service.IPaymentService;
import com.PaymentApplication.service.ITransactionService;
import com.PaymentApplication.service.IUserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class PaymentServiceTest
{

    private IPaymentService underTestPaymentService;

    @Mock
    private IUserService userService;

    @Mock
    private ITransactionService transactionService;

    @BeforeEach
    void setUp()
    {
        underTestPaymentService = new PaymentService(userService, transactionService, onlineShopService);
    }

    @Test
    void chargeCard_enoughCredit_shouldChargeTheCard()
    {

        // given
        PaymentOrderRequest paymentOrderRequest = new PaymentOrderRequest(
                "orderId19",
                "john.wick",
                new BigDecimal("1200"),
                TransactionStatusEnum.IN_PROCESS.name()
        );

        AppUser user = new AppUser(
                19L,
                "John",
                "Wick",
                "001666666666",
                "john.wick@gmail.com",
                "john.wick",
                "password1234",
                "USA",
                "This is an address",
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        Wallet wallet = new Wallet(
                1L,
                new BigDecimal("1300")
        );

        user.setWallet(wallet);

        Transaction transaction = new Transaction(
                19L,
                paymentOrderRequest.getOrderId(),
                paymentOrderRequest.getTotalPrice(),
                paymentOrderRequest.getTransactionStatus()
        );

        given(userService.findUserByUsername(anyString())).willReturn(user);
        given(transactionService.createTransaction(paymentOrderRequest)).willReturn(transaction);

        // when
        underTestPaymentService.chargeCard(paymentOrderRequest);

        // then
        assertThat(user.getTransactions().size()).isEqualTo(1);
        assertThat(wallet.getCredit()).isEqualTo(new BigDecimal("100"));
        assertThat(paymentOrderRequest.getTransactionStatus()).isEqualTo(TransactionStatusEnum.PURCHASED.name());

    }

    @Test
    void chargeCard_notEnoughCredit_shouldNotChargeTheCard()
    {
        // given
        PaymentOrderRequest paymentOrderRequest = new PaymentOrderRequest(
                "orderId19",
                "john.wick",
                new BigDecimal("1400"),
                TransactionStatusEnum.IN_PROCESS.name()
        );

        AppUser user = new AppUser(
                19L,
                "John",
                "Wick",
                "001666666666",
                "john.wick@gmail.com",
                "john.wick",
                "password1234",
                "USA",
                "This is an address",
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        Wallet wallet = new Wallet(
                1L,
                new BigDecimal("1300")
        );

        user.setWallet(wallet);

        Transaction transaction = new Transaction(
                19L,
                paymentOrderRequest.getOrderId(),
                paymentOrderRequest.getTotalPrice(),
                paymentOrderRequest.getTransactionStatus()
        );

        given(userService.findUserByUsername(anyString())).willReturn(user);
        given(transactionService.createTransaction(paymentOrderRequest)).willReturn(transaction);

        // when
        underTestPaymentService.chargeCard(paymentOrderRequest);

        // then
        assertThat(user.getTransactions().size()).isEqualTo(1);
        assertThat(wallet.getCredit()).isEqualTo(new BigDecimal("1300"));
        assertThat(paymentOrderRequest.getTransactionStatus()).isEqualTo(TransactionStatusEnum.NOT_ENOUGH_CREDITS.name());
    }

    @Test
    void chargeCard_userDoesNotHaveWallet_shouldNotChargeTheCard()
    {
        // given
        PaymentOrderRequest paymentOrderRequest = new PaymentOrderRequest(
                "orderId19",
                "john.wick",
                new BigDecimal("1400"),
                TransactionStatusEnum.IN_PROCESS.name()
        );

        AppUser user = new AppUser(
                19L,
                "John",
                "Wick",
                "001666666666",
                "john.wick@gmail.com",
                "john.wick",
                "password1234",
                "USA",
                "This is an address",
                LocalDateTime.now(),
                LocalDateTime.now()
        );


        given(userService.findUserByUsername(anyString())).willReturn(user);

        // when


        // then
        assertThatThrownBy(() -> underTestPaymentService.chargeCard(paymentOrderRequest))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining("User does not have a wallet yet");
    }
}