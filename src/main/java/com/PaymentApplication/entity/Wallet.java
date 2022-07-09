package com.PaymentApplication.entity;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * user wallet for Payment application
 */
@Entity
@Table(name = "wallets")
public class Wallet
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    private BigDecimal credit;

    @OneToOne(mappedBy = "wallet", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private AppUser user;

    public Wallet()
    {
    }

    public Wallet(Long id, BigDecimal credit)
    {
        this.id = id;
        this.credit = credit;
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public BigDecimal getCredit()
    {
        return credit;
    }

    public void setCredit(BigDecimal credit)
    {
        this.credit = credit;
    }

    public AppUser getUser()
    {
        return user;
    }

    public void setUser(AppUser user)
    {
        this.user = user;
    }

    @Override
    public String toString()
    {
        return "Wallet [" +
                "id=" + id +
                ", credit=" + credit +
                ']';
    }
}
