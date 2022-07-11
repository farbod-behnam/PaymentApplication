package com.PaymentApplication.repository;

import com.PaymentApplication.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IUserRepository extends JpaRepository<AppUser, Long>
{
    Optional<AppUser> findAppUserByUsername(String username);
}
