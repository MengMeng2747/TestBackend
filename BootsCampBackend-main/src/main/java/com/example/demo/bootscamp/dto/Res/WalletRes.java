package com.example.demo.bootscamp.dto.Res;

import java.math.BigDecimal;
import java.util.List;

public class WalletRes {

    private Integer userId;
    private BigDecimal balance;
    private List<WalletLogRes> logs;

    public WalletRes(Integer userId, BigDecimal balance, List<WalletLogRes> logs) {
        this.userId = userId;
        this.balance = balance;
        this.logs = logs;
    }

    public Integer getUserId() {
        return userId;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public List<WalletLogRes> getLogs() {
        return logs;
    }
}