package com.PaymentApplication.repository;

import com.PaymentApplication.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ITransactionRepository extends JpaRepository<Transaction, Long>
{
    Optional<Transaction> findTransactionByOnlineShopOrderId(String onlineShopOrderId);
}
