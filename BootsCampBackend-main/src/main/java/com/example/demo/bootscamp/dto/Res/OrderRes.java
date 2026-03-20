package com.example.demo.bootscamp.dto.Res;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class OrderRes {

    private Integer     id;
    private String      orderNumber;
    private Integer     shopId;
    private String      shopName;       // ← join จาก shops table
    private String      customerName;
    private String      customerPhone;
    private String      shippingAddress;
    private BigDecimal  totalAmount;
    private BigDecimal  resellerProfit;
    private String      status;
    private String      createdAt;

    public OrderRes() {}

    public OrderRes(Integer id, String orderNumber, Integer shopId, String shopName,
                    String customerName, String customerPhone, String shippingAddress,
                    BigDecimal totalAmount, BigDecimal resellerProfit,
                    String status, LocalDateTime createdAt) {
        this.id              = id;
        this.orderNumber     = orderNumber;
        this.shopId          = shopId;
        this.shopName        = shopName;
        this.customerName    = customerName;
        this.customerPhone   = customerPhone;
        this.shippingAddress = shippingAddress;
        this.totalAmount     = totalAmount;
        this.resellerProfit  = resellerProfit;
        this.status          = status;
        this.createdAt       = createdAt != null ? createdAt.toString() : null;
    }

    public Integer    getId()              { return id; }
    public String     getOrderNumber()     { return orderNumber; }
    public Integer    getShopId()          { return shopId; }
    public String     getShopName()        { return shopName; }
    public String     getCustomerName()    { return customerName; }
    public String     getCustomerPhone()   { return customerPhone; }
    public String     getShippingAddress() { return shippingAddress; }
    public BigDecimal getTotalAmount()     { return totalAmount; }
    public BigDecimal getResellerProfit()  { return resellerProfit; }
    public String     getStatus()          { return status; }
    public String     getCreatedAt()       { return createdAt; }
}