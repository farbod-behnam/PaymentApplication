package com.PaymentApplication.service.impl;

import com.PaymentApplication.entity.Wallet;
import com.PaymentApplication.repository.IWalletRepository;
import com.PaymentApplication.service.IWalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@Transactional
public class WalletService implements IWalletService
{

    private final IWalletRepository walletRepository;

    @Autowired
    public WalletService(IWalletRepository walletRepository)
    {
        this.walletRepository = walletRepository;
    }

    @Override
    public Wallet createWallet()
    {
        Wallet wallet = new Wallet(
                null,
                new BigDecimal("0.0")
        );

        return walletRepository.save(wallet);
    }
}
