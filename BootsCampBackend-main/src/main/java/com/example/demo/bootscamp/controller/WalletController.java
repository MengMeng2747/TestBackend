package com.example.demo.bootscamp.controller;

import com.example.demo.bootscamp.dto.Res.WalletRes;
import com.example.demo.bootscamp.service.WalletService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reseller")
public class WalletController {

    private final WalletService walletService;

    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    @GetMapping("/wallet")
    public WalletRes wallet(@RequestParam Integer userId){

        return walletService.getWallet(userId);

    }

}