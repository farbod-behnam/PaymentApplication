package com.PaymentApplication.service;

import com.PaymentApplication.entity.Wallet;
import org.springframework.stereotype.Service;

@Service
public interface IWalletService
{
    Wallet createWallet();
}
