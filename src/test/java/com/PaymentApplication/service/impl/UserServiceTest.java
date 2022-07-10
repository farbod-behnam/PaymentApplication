package com.PaymentApplication.service.impl;

import com.PaymentApplication.dto.request.payment.PaymentUserRequest;
import com.PaymentApplication.entity.AppUser;
import com.PaymentApplication.entity.Wallet;
import com.PaymentApplication.exception.NotFoundException;
import com.PaymentApplication.repository.IUserRepository;
import com.PaymentApplication.service.IUserService;
import com.PaymentApplication.service.IWalletService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserServiceTest
{

    private IUserService underTestUserService;

    @Mock
    private IUserRepository userRepository;

    @Mock
    private IWalletService walletService;

    @BeforeEach
    void setUp()
    {
        MockitoAnnotations.openMocks(this);
        underTestUserService = new UserService(userRepository, walletService);
    }

    @Test
    void createUser_shouldReturnCreatedUser()
    {
        // given

        PaymentUserRequest userRequest = new PaymentUserRequest(
                "John",
                "Wick",
                "001666666666",
                "john.wick@gmail.com",
                "john.wick",
                "password1234",
                "USA",
                "This is an address",
                LocalDateTime.now(),
                LocalDateTime.now()
        );

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

        Wallet wallet = new Wallet(
                null,
                new BigDecimal("0.0")
        );

        given(walletService.createWallet()).willReturn(wallet);
        user.setWallet(wallet);

        // when
        underTestUserService.createUser(userRequest);

        // then
        ArgumentCaptor<AppUser> userArgumentCaptor = ArgumentCaptor.forClass(AppUser.class);
        verify(userRepository).save(userArgumentCaptor.capture());
        AppUser captorUser = userArgumentCaptor.getValue();

        assertThat(captorUser.getId()).isEqualTo(null);
        assertThat(captorUser.getFirstName()).isEqualTo(user.getFirstName());
        assertThat(captorUser.getLastName()).isEqualTo(user.getLastName());
        assertThat(captorUser.getPhoneNumber()).isEqualTo(user.getPhoneNumber());
        assertThat(captorUser.getEmail()).isEqualTo(user.getEmail());
        assertThat(captorUser.getUsername()).isEqualTo(user.getUsername());
        assertThat(captorUser.getPassword()).isEqualTo(user.getPassword());
        assertThat(captorUser.getCountry()).isEqualTo(user.getCountry());
        assertThat(captorUser.getAddress()).isEqualTo(user.getAddress());
        assertThat(captorUser.getCreatedAt()).isEqualTo(user.getCreatedAt());
        assertThat(captorUser.getUpdatedAt()).isEqualTo(user.getUpdatedAt());
        assertThat(captorUser.getWallet().getCredit()).isEqualTo(user.getWallet().getCredit());
    }

    @Test
    void updateUser_shouldReturnUpdatedUser()
    {
        // given
        Long userId = 19L;

        PaymentUserRequest userRequest = new PaymentUserRequest(
                "John",
                "Wick",
                "001666666666",
                "john.wick@gmail.com",
                "john.wick",
                "password1234",
                "USA",
                "This is an address",
                LocalDateTime.now(),
                LocalDateTime.now()
        );

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

        Wallet wallet = new Wallet(
                null,
                new BigDecimal("0.0")
        );

        given(walletService.createWallet()).willReturn(wallet);

        // when
        underTestUserService.updateUser(userRequest, userId);

        // then
        ArgumentCaptor<AppUser> userArgumentCaptor = ArgumentCaptor.forClass(AppUser.class);
        verify(userRepository).save(userArgumentCaptor.capture());
        AppUser captorUser = userArgumentCaptor.getValue();

        assertThat(captorUser.getId()).isEqualTo(userId);
        assertThat(captorUser.getFirstName()).isEqualTo(user.getFirstName());
        assertThat(captorUser.getLastName()).isEqualTo(user.getLastName());
        assertThat(captorUser.getPhoneNumber()).isEqualTo(user.getPhoneNumber());
        assertThat(captorUser.getEmail()).isEqualTo(user.getEmail());
        assertThat(captorUser.getUsername()).isEqualTo(user.getUsername());
        assertThat(captorUser.getPassword()).isEqualTo(user.getPassword());
        assertThat(captorUser.getCountry()).isEqualTo(user.getCountry());
        assertThat(captorUser.getAddress()).isEqualTo(user.getAddress());
        assertThat(captorUser.getCreatedAt()).isEqualTo(user.getCreatedAt());
        assertThat(captorUser.getUpdatedAt()).isEqualTo(user.getUpdatedAt());
    }

    @Test
    void saveUserRecord_UserDoesNotExist_shouldReturnCreatedUser()
    {
        // given

        PaymentUserRequest userRequest = new PaymentUserRequest(
                "John",
                "Wick",
                "001666666666",
                "john.wick@gmail.com",
                "john.wick",
                "password1234",
                "USA",
                "This is an address",
                LocalDateTime.now(),
                LocalDateTime.now()
        );

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

        Wallet wallet = new Wallet(
                null,
                new BigDecimal("0.0")
        );


        given(userRepository.findAppUserByUsername(anyString())).willReturn(Optional.empty());

        given(walletService.createWallet()).willReturn(wallet);
        user.setWallet(wallet);

        // when
        underTestUserService.saveUserRecord(userRequest);

        // then
        ArgumentCaptor<AppUser> userArgumentCaptor = ArgumentCaptor.forClass(AppUser.class);
        verify(userRepository).save(userArgumentCaptor.capture());
        AppUser captorUser = userArgumentCaptor.getValue();

        assertThat(captorUser.getId()).isEqualTo(null);
        assertThat(captorUser.getFirstName()).isEqualTo(user.getFirstName());
        assertThat(captorUser.getLastName()).isEqualTo(user.getLastName());
        assertThat(captorUser.getPhoneNumber()).isEqualTo(user.getPhoneNumber());
        assertThat(captorUser.getEmail()).isEqualTo(user.getEmail());
        assertThat(captorUser.getUsername()).isEqualTo(user.getUsername());
        assertThat(captorUser.getPassword()).isEqualTo(user.getPassword());
        assertThat(captorUser.getCountry()).isEqualTo(user.getCountry());
        assertThat(captorUser.getAddress()).isEqualTo(user.getAddress());
        assertThat(captorUser.getCreatedAt()).isEqualTo(user.getCreatedAt());
        assertThat(captorUser.getUpdatedAt()).isEqualTo(user.getUpdatedAt());
        assertThat(captorUser.getWallet().getCredit()).isEqualTo(user.getWallet().getCredit());
    }

    @Test
    void saveUserRecord_UserDoesExist_shouldReturnUpdatedUser()
    {
        // given

        PaymentUserRequest userRequest = new PaymentUserRequest(
                "John",
                "Wick",
                "001666666666",
                "john.wick@gmail.com",
                "john.wick",
                "password1234",
                "USA",
                "This is an address",
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        AppUser user = new AppUser(
                19L,
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

        Wallet wallet = new Wallet(
                null,
                new BigDecimal("0.0")
        );

        given(userRepository.findAppUserByUsername(anyString())).willReturn(Optional.of(user));


        // when
        underTestUserService.saveUserRecord(userRequest);

        // then
        ArgumentCaptor<AppUser> userArgumentCaptor = ArgumentCaptor.forClass(AppUser.class);
        verify(userRepository).save(userArgumentCaptor.capture());
        AppUser captorUser = userArgumentCaptor.getValue();

        assertThat(captorUser.getId()).isEqualTo(user.getId());
        assertThat(captorUser.getFirstName()).isEqualTo(user.getFirstName());
        assertThat(captorUser.getLastName()).isEqualTo(user.getLastName());
        assertThat(captorUser.getPhoneNumber()).isEqualTo(user.getPhoneNumber());
        assertThat(captorUser.getEmail()).isEqualTo(user.getEmail());
        assertThat(captorUser.getUsername()).isEqualTo(user.getUsername());
        assertThat(captorUser.getPassword()).isEqualTo(user.getPassword());
        assertThat(captorUser.getCountry()).isEqualTo(user.getCountry());
        assertThat(captorUser.getAddress()).isEqualTo(user.getAddress());
        assertThat(captorUser.getCreatedAt()).isEqualTo(user.getCreatedAt());
        assertThat(captorUser.getUpdatedAt()).isEqualTo(user.getUpdatedAt());
    }

    @Test
    void findUserByUsername_shouldReturnUser()
    {
       // given
        AppUser user = new AppUser(
                19L,
                "John",
                "Wick",
                "001666666666",
                "john.wick@gmail.com",
                "john.wick",
                "password1234",
                "USA",
                "This is an address",
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        given(userRepository.findAppUserByUsername(anyString())).willReturn(Optional.of(user));

       // when
       AppUser foundUser = underTestUserService.findUserByUsername(anyString());

       // then
        verify(userRepository).findAppUserByUsername(anyString());
        assertThat(foundUser).isEqualTo(user);
    }

    @Test
    void findUserByUsername_shouldThrowNotFoundException()
    {
        // given
        String username = "john";


        // when

        // then
        assertThatThrownBy(() -> underTestUserService.findUserByUsername(username))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining("User with username: [" + username + "] cannot be found");
    }
}