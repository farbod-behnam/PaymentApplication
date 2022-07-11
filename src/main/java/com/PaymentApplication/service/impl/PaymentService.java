package com.PaymentApplication.service.impl;

import com.PaymentApplication.dto.request.payment.PaymentOrderRequest;
import com.PaymentApplication.entity.AppUser;
import com.PaymentApplication.entity.Transaction;
import com.PaymentApplication.entity.Wallet;
import com.PaymentApplication.enums.TransactionStatusEnum;
import com.PaymentApplication.exception.NotFoundException;
import com.PaymentApplication.rabbitmq.service.IOnlineShopService;
import com.PaymentApplication.service.IPaymentService;
import com.PaymentApplication.service.ITransactionService;
import com.PaymentApplication.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentService implements IPaymentService
{
    private final IUserService userService;
    private final ITransactionService transactionService;
    private final IOnlineShopService onlineShopService;

    @Autowired
    public PaymentService(IUserService userService, ITransactionService transactionService, IOnlineShopService onlineShopService)
    {
        this.userService = userService;
        this.transactionService = transactionService;
        this.onlineShopService = onlineShopService;
    }


    @Override
    public void chargeCard(PaymentOrderRequest paymentOrderRequest)
    {
        AppUser foundUser = userService.findUserByUsername(paymentOrderRequest.getUsername());
        Wallet userWallet = foundUser.getWallet();

        if (userWallet == null)
            throw new NotFoundException("User does not have a wallet yet");

        // if user does not have enough credit for purchasing the order
        if (userWallet.getCredit().compareTo(paymentOrderRequest.getTotalPrice()) < 0)
        {
            paymentOrderRequest.setTransactionStatus(TransactionStatusEnum.NOT_ENOUGH_CREDITS.name());
            Transaction transaction = transactionService.createTransaction(paymentOrderRequest);
            foundUser.addTransaction(transaction);


        }
        else
        {
            userWallet.subtractFee(paymentOrderRequest.getTotalPrice());
            paymentOrderRequest.setTransactionStatus(TransactionStatusEnum.PURCHASED.name());
            Transaction transaction = transactionService.createTransaction(paymentOrderRequest);
            foundUser.addTransaction(transaction);


        }

        onlineShopService.sendBackOrder(paymentOrderRequest);

    }
}
