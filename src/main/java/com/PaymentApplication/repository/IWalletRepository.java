package com.PaymentApplication.repository;

import com.PaymentApplication.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IWalletRepository extends JpaRepository<Wallet, Long>
{
}
