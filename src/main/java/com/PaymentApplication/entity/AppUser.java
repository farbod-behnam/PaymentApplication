package com.PaymentApplication.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * User entity for Payment application
 */
@Entity
@Table(name = "users")
public class AppUser
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    private String firstName;

    @Column
    private String lastName;

    @Column
    private String phoneNumber;

    @Column
    private String email;

//    @ManyToMany(
//            cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH},
//            fetch = FetchType.LAZY)
//    @JoinTable(
//            name = "user_role",
//            joinColumns = @JoinColumn(name = "user_id"),
//            inverseJoinColumns = @JoinColumn(name = "role_id")
//    )
//    private Set<AppRole> roles;

    @Column
    private String username;

    @JsonIgnore
    @Column
    private String password;

    @Column
    private String country;

    @Column
    private String address;

    @Column
    private LocalDateTime createdAt;

    @Column
    private LocalDateTime updatedAt;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "wallet_id")
    private Wallet wallet;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private List<Transaction> transactions;

    public AppUser()
    {
    }

    public AppUser(Long id, String firstName, String lastName, String phoneNumber, String email, String username, String password, String country, String address, LocalDateTime createdAt, LocalDateTime updatedAt)
    {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.username = username;
        this.password = password;
        this.country = country;
        this.address = address;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    public String getPhoneNumber()
    {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber)
    {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

//    public Set<AppRole> getRoles()
//    {
//        return roles;
//    }
//
//    public void setRoles(Set<AppRole> roles)
//    {
//        this.roles = roles;
//    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getCountry()
    {
        return country;
    }

    public void setCountry(String country)
    {
        this.country = country;
    }

    public String getAddress()
    {
        return address;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public LocalDateTime getCreatedAt()
    {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt)
    {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt()
    {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt)
    {
        this.updatedAt = updatedAt;
    }

    public Wallet getWallet()
    {
        return wallet;
    }

    public void setWallet(Wallet wallet)
    {
        this.wallet = wallet;
    }

    public List<Transaction> getTransactions()
    {
        return transactions;
    }

    public void setTransactions(List<Transaction> orders)
    {
        this.transactions = orders;
    }

    public void addTransaction(Transaction transaction)
    {
        if (this.transactions == null)
            this.transactions = new ArrayList<>();

        this.transactions.add(transaction);
    }

//    public void addRole(AppRole role)
//    {
//        if (this.roles == null)
//            this.roles = new HashSet<>();
//
//        this.roles.add(role);
//    }

    @Override
    public String toString()
    {
        return "User [" +
                "id='" + id + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", country=" + country +
                ", address='" + address + '\'' +
                ']';
    }
}
