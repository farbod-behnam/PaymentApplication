package com.PaymentApplication.rabbitmq.listener.component;

import com.PaymentApplication.dto.request.payment.PaymentOrderRequest;
import com.PaymentApplication.dto.request.payment.PaymentUserRequest;
import com.PaymentApplication.enums.TransactionStatusEnum;
import com.PaymentApplication.rabbitmq.listener.component.OnlineShopListenerComponent;
import com.PaymentApplication.service.IPaymentService;
import com.PaymentApplication.service.IUserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class OnlineShopListenerComponentTest
{


    private OnlineShopListenerComponent underTestOnlineShopListenerComponent;

    @Mock
    private IPaymentService paymentService;

    @Mock
    private IUserService userService;




    @BeforeEach
    void setUp()
    {
        underTestOnlineShopListenerComponent = new OnlineShopListenerComponent(paymentService, userService);
    }

    @Test
    void orderQueueListener_shouldGetPaymentOrderRequest()
    {
        // given
        PaymentOrderRequest paymentOrderRequest = new PaymentOrderRequest(
                "orderId19",
                "john",
                new BigDecimal("1919"),
                TransactionStatusEnum.IN_PROCESS.name()
        );


        // when
        underTestOnlineShopListenerComponent.orderQueueListener(paymentOrderRequest);

        // then
        ArgumentCaptor<PaymentOrderRequest> paymentOrderRequestArgumentCaptor = ArgumentCaptor.forClass(PaymentOrderRequest.class);
        verify(paymentService).chargeCard(paymentOrderRequestArgumentCaptor.capture());
        PaymentOrderRequest capturedPaymentOrderRequest = paymentOrderRequestArgumentCaptor.getValue();

        assertThat(capturedPaymentOrderRequest).isEqualTo(paymentOrderRequest);
    }

    @Test
    void userQueueListener_shouldGetPaymentUserRequest()
    {
        PaymentUserRequest paymentUserRequest = new PaymentUserRequest(
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

        // when
        underTestOnlineShopListenerComponent.userQueueListener(paymentUserRequest);

        // then
        ArgumentCaptor<PaymentUserRequest> paymentUserRequestArgumentCaptor = ArgumentCaptor.forClass(PaymentUserRequest.class);
        verify(userService).saveUserRecord(paymentUserRequestArgumentCaptor.capture());
        PaymentUserRequest capturedPaymentUserRequest = paymentUserRequestArgumentCaptor.getValue();

        assertThat(capturedPaymentUserRequest).isEqualTo(paymentUserRequest);
    }
}