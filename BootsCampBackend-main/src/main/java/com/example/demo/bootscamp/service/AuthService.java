package com.example.demo.bootscamp.service;

import com.example.demo.bootscamp.dto.Req.RegisterReq;
import com.example.demo.bootscamp.entity.*;
import com.example.demo.bootscamp.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final ShopRepository shopRepository;
    private final WalletRepository walletRepository;

    public AuthService(UserRepository userRepository,
                       ShopRepository shopRepository,
                       WalletRepository walletRepository) {
        this.userRepository = userRepository;
        this.shopRepository = shopRepository;
        this.walletRepository = walletRepository;
    }

    @Transactional
    public void registerReseller(RegisterReq req) {

        // check email duplicate
        if (userRepository.existsByEmail(req.getEmail())) {
            throw new RuntimeException("อีเมลนี้ถูกใช้งานแล้ว");
        }

        // create user
        UserEntity user = new UserEntity();
        user.setName(req.getName());
        user.setEmail(req.getEmail());
        user.setPassword(req.getPassword());
        user.setPhone(req.getPhone());
        user.setRole("reseller");
        user.setStatus("pending");
        user.setAddress(req.getAddress());

        userRepository.save(user);

        // create shop
        ShopEntity shop = new ShopEntity();
        shop.setUserId(user.getId());
        shop.setShopName(req.getShopName());
        shop.setShopSlug(req.getShopName().toLowerCase().replace(" ", ""));

        shopRepository.save(shop);

        // create wallet
        WalletEntity wallet = new WalletEntity();
        wallet.setUserId(user.getId());
        wallet.setBalance(BigDecimal.ZERO);

        walletRepository.save(wallet);
    }
}