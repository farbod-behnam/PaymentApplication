package com.PaymentApplication.service.impl;

import com.PaymentApplication.entity.Wallet;
import com.PaymentApplication.repository.IWalletRepository;
import com.PaymentApplication.service.IWalletService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class WalletServiceTest
{

    private IWalletService underTestWalletService;

    @Mock
    private IWalletRepository walletRepository;

    @BeforeEach
    void setUp()
    {
        MockitoAnnotations.openMocks(this);
        underTestWalletService = new WalletService(walletRepository);
    }

    @Test
    void createWallet_shouldReturnCreatedWallet()
    {
        // given
        Wallet wallet = new Wallet(
                42L,
                new BigDecimal("0.0")
        );


        // when
        underTestWalletService.createWallet();

        // then
        ArgumentCaptor<Wallet> walletArgumentCaptor = ArgumentCaptor.forClass(Wallet.class);
        verify(walletRepository).save(walletArgumentCaptor.capture());
        Wallet capturedWallet = walletArgumentCaptor.getValue();
        assertThat(capturedWallet.getId()).isEqualTo(null);
        assertThat(capturedWallet.getCredit()).isEqualTo(wallet.getCredit());
    }
}