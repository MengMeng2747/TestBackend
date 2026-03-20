package com.example.demo.bootscamp.dto.Res;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class WalletLogRes {

    private Integer       id;
    private Integer       orderId;
    private String        orderNumber;   // ← เพิ่ม: เลขออเดอร์จริง
    private Integer       userId;
    private BigDecimal    amount;
    private String        type;
    private LocalDateTime createdAt;

    public WalletLogRes() {}

    public WalletLogRes(Integer id,
                        Integer orderId,
                        String orderNumber,
                        Integer userId,
                        BigDecimal amount,
                        String type,
                        LocalDateTime createdAt) {
        this.id          = id;
        this.orderId     = orderId;
        this.orderNumber = orderNumber;
        this.userId      = userId;
        this.amount      = amount;
        this.type        = type;
        this.createdAt   = createdAt;
    }

    public Integer       getId()          { return id; }
    public Integer       getOrderId()     { return orderId; }
    public String        getOrderNumber() { return orderNumber; }
    public Integer       getUserId()      { return userId; }
    public BigDecimal    getAmount()      { return amount; }
    public String        getType()        { return type; }
    public LocalDateTime getCreatedAt()   { return createdAt; }
}