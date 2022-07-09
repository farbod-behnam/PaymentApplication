package com.PaymentApplication.service.impl;

import com.PaymentApplication.dto.request.payment.PaymentUserRequest;
import com.PaymentApplication.entity.AppUser;
import com.PaymentApplication.entity.Wallet;
import com.PaymentApplication.exception.NotFoundException;
import com.PaymentApplication.repository.IUserRepository;
import com.PaymentApplication.service.IUserService;
import com.PaymentApplication.service.IWalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class UserService implements IUserService
{

    private final IUserRepository userRepository;
    private final IWalletService walletService;

    @Autowired
    public UserService(IUserRepository userRepository, IWalletService walletService)
    {
        this.userRepository = userRepository;
        this.walletService = walletService;
    }

    @Override
    public AppUser createUser(PaymentUserRequest userRequest)
    {
        AppUser user = new AppUser(
                null,
                userRequest.getFirstName(),
                userRequest.getLastName(),
                userRequest.getPhoneNumber(),
                userRequest.getEmail(),
                userRequest.getUsername(),
                userRequest.getPassword(),
                userRequest.getCountry(),
                userRequest.getAddress(),
                userRequest.getCreatedAt(),
                userRequest.getUpdatedAt()
        );

        // create a wallet for newly created user
        Wallet wallet = walletService.createWallet();

        user.setWallet(wallet);

        return userRepository.save(user);
    }

    @Override
    public AppUser updateUser(PaymentUserRequest userRequest, Long userId)
    {
        AppUser user = new AppUser(
                userId,
                userRequest.getFirstName(),
                userRequest.getLastName(),
                userRequest.getPhoneNumber(),
                userRequest.getEmail(),
                userRequest.getUsername(),
                userRequest.getPassword(),
                userRequest.getCountry(),
                userRequest.getAddress(),
                userRequest.getCreatedAt(),
                userRequest.getUpdatedAt()
        );

        return userRepository.save(user);
    }

    @Override
    public AppUser saveUserRecord(PaymentUserRequest userRequest)
    {
        Optional<AppUser> optionalUser = userRepository.findAppUserByUsername(userRequest.getUsername());

        // create the user if the user is not registered in database
        if (optionalUser.isEmpty())
            return createUser(userRequest);

        // update the user
        return updateUser(userRequest, optionalUser.get().getId());

    }

    @Override
    public AppUser findUserByUsername(String username)
    {
        Optional<AppUser> result = userRepository.findAppUserByUsername(username);

        if (result.isEmpty())
            throw new NotFoundException("User with username: [" + username + "] cannot be found");

        return result.get();
    }


}
