package com.PaymentApplication.service;

import com.PaymentApplication.dto.request.payment.PaymentUserRequest;
import com.PaymentApplication.entity.AppUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public interface IUserService
{

    AppUser createUser(PaymentUserRequest userRequest);

    AppUser updateUser(PaymentUserRequest userRequest, Long userId);

    /**
     * It will save or update the user in the user table
     *
     * @param userRequest {@link PaymentUserRequest}
     * @return created or updated user {@link AppUser}
     */
    AppUser saveUserRecord(PaymentUserRequest userRequest);

    AppUser findUserByUsername(String username);

}
