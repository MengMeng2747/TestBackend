package com.example.demo.bootscamp.service;

import com.example.demo.bootscamp.dto.Res.WalletLogRes;
import com.example.demo.bootscamp.dto.Res.WalletRes;
import com.example.demo.bootscamp.entity.WalletEntity;
import com.example.demo.bootscamp.repository.OrdersRepository;
import com.example.demo.bootscamp.repository.WalletLogRepository;
import com.example.demo.bootscamp.repository.WalletRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WalletService {

    private final WalletRepository    walletRepository;
    private final WalletLogRepository walletLogRepository;
    private final OrdersRepository    ordersRepository;

    public WalletService(WalletRepository walletRepository,
                         WalletLogRepository walletLogRepository,
                         OrdersRepository ordersRepository) {
        this.walletRepository    = walletRepository;
        this.walletLogRepository = walletLogRepository;
        this.ordersRepository    = ordersRepository;
    }

    public WalletRes getWallet(Integer userId) {

        WalletEntity wallet = walletRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("wallet not found"));

        List<WalletLogRes> logs = walletLogRepository.findByUserId(userId)
                .stream()
                .map(l -> {
                    // join orderNumber จาก orders table
                    String orderNumber = null;
                    if (l.getOrderId() != null) {
                        orderNumber = ordersRepository.findById(l.getOrderId())
                                .map(o -> o.getOrderNumber())
                                .orElse("ORD-" + l.getOrderId());
                    }

                    return new WalletLogRes(
                            l.getId(),
                            l.getOrderId(),
                            orderNumber,       // ← เลขออเดอร์จริง
                            l.getUserId(),
                            l.getAmount(),
                            l.getType(),
                            l.getCreatedAt()
                    );
                })
                .collect(Collectors.toList());

        return new WalletRes(
                wallet.getUserId(),
                wallet.getBalance(),
                logs
        );
    }
}