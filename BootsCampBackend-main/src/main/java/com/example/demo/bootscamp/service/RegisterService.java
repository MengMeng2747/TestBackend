package com.example.demo.bootscamp.service;

import com.example.demo.bootscamp.dto.Req.RegisterReq;
import com.example.demo.bootscamp.entity.ShopEntity;
import com.example.demo.bootscamp.entity.UserEntity;
import com.example.demo.bootscamp.entity.WalletEntity;
import com.example.demo.bootscamp.repository.ShopRepository;
import com.example.demo.bootscamp.repository.UserRepository;
import com.example.demo.bootscamp.repository.WalletRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class RegisterService {

    private final UserRepository userRepository;
    private final ShopRepository shopRepository;
    private final WalletRepository walletRepository;

    public RegisterService(UserRepository userRepository,
                           ShopRepository shopRepository,
                           WalletRepository walletRepository) {
        this.userRepository = userRepository;
        this.shopRepository = shopRepository;
        this.walletRepository = walletRepository;
    }

    @Transactional
    public String register(RegisterReq req) {

        // password match
        if (!req.getPassword().equals(req.getConfirmPassword())) {
            throw new RuntimeException("รหัสผ่านไม่ตรงกัน");
        }

        // email duplicate
        if (userRepository.existsByEmail(req.getEmail())) {
            throw new RuntimeException("อีเมลนี้ถูกใช้งานแล้ว");
        }

        // generate slug
        String slug = req.getShopName()
                .toLowerCase()
                .replaceAll("\\s+", "");

        // shop duplicate
        if (shopRepository.existsByShopSlug(slug)) {
            throw new RuntimeException("ชื่อร้านนี้ถูกใช้แล้ว");
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
        user.setCreatedAt(LocalDateTime.now()); // ← set วันที่สมัคร

        userRepository.save(user);

        // create shop
        ShopEntity shop = new ShopEntity();
        shop.setUserId(user.getId());
        shop.setShopName(req.getShopName());
        shop.setShopSlug(slug);

        shopRepository.save(shop);

        // create wallet
        WalletEntity wallet = new WalletEntity();
        wallet.setUserId(user.getId());
        wallet.setBalance(BigDecimal.ZERO);

        walletRepository.save(wallet);

        return "สมัครสำเร็จ รอ Admin อนุมัติ";
    }
}