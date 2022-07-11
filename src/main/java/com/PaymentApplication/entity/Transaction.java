package com.PaymentApplication.entity;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * Transaction entity for orders that user make
 */
@Entity
@Table(name = "transactions")
public class Transaction
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    private String onlineShopOrderId;

    @Column
    private BigDecimal totalPrice;

    @Column
    private String transactionStatus;

    public Transaction()
    {
    }

    public Transaction(Long id, String onlineShopOrderId, BigDecimal totalPrice, String orderStatus)
    {
        this.id = id;
        this.onlineShopOrderId = onlineShopOrderId;
        this.totalPrice = totalPrice;
        this.transactionStatus = orderStatus;
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getOnlineShopOrderId()
    {
        return onlineShopOrderId;
    }

    public void setOnlineShopOrderId(String onlineShopOrderId)
    {
        this.onlineShopOrderId = onlineShopOrderId;
    }

    public BigDecimal getTotalPrice()
    {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice)
    {
        this.totalPrice = totalPrice;
    }

    public String getTransactionStatus()
    {
        return transactionStatus;
    }

    public void setTransactionStatus(String orderStatus)
    {
        this.transactionStatus = orderStatus;
    }

    @Override
    public String toString()
    {
        return "Order [" +
                "id=" + id +
                ", onlineShopOrderId='" + onlineShopOrderId + '\'' +
                ", totalPrice=" + totalPrice +
                ", transactionStatus='" + transactionStatus + '\'' +
                ']';
    }
}
