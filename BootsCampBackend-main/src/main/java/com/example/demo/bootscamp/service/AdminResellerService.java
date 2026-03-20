package com.example.demo.bootscamp.service;

import com.example.demo.bootscamp.dto.Res.ResellerRes;
import com.example.demo.bootscamp.entity.UserEntity;
import com.example.demo.bootscamp.repository.ShopRepository;
import com.example.demo.bootscamp.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminResellerService {

    private final UserRepository userRepository;
    private final ShopRepository shopRepository;

    public AdminResellerService(UserRepository userRepository,
                                ShopRepository shopRepository) {
        this.userRepository = userRepository;
        this.shopRepository = shopRepository;
    }

    // GET ALL RESELLERS — รวม shopName และ createdAt จริงจาก DB
    public List<ResellerRes> getResellers() {
        return userRepository.findByRole("reseller")
                .stream()
                .map(u -> {
                    // หา shopName จาก shops table
                    String shopName = shopRepository.findByUserId(u.getId())
                            .map(s -> s.getShopName())
                            .orElse("—");

                    return new ResellerRes(
                            u.getId(),
                            u.getName(),
                            u.getEmail(),
                            u.getPhone(),
                            u.getRole(),
                            u.getStatus(),
                            u.getAddress(),
                            shopName,
                            u.getCreatedAt()   // ← วันที่สมัครจริงจาก DB
                    );
                })
                .collect(Collectors.toList());
    }

    // APPROVE
    public ResellerRes approveReseller(Long id) {
        UserEntity user = userRepository.findById(Math.toIntExact(id))
                .orElseThrow(() -> new RuntimeException("ไม่พบ user"));
        user.setStatus("approved");
        userRepository.save(user);
        return toRes(user);
    }

    // REJECT
    public ResellerRes rejectReseller(Long id) {
        UserEntity user = userRepository.findById(Math.toIntExact(id))
                .orElseThrow(() -> new RuntimeException("ไม่พบ user"));
        user.setStatus("rejected");
        userRepository.save(user);
        return toRes(user);
    }

    // ── helper ────────────────────────────────────────────────
    private ResellerRes toRes(UserEntity u) {
        String shopName = shopRepository.findByUserId(u.getId())
                .map(s -> s.getShopName())
                .orElse("—");
        return new ResellerRes(
                u.getId(), u.getName(), u.getEmail(), u.getPhone(),
                u.getRole(), u.getStatus(), u.getAddress(),
                shopName, u.getCreatedAt()
        );
    }
}