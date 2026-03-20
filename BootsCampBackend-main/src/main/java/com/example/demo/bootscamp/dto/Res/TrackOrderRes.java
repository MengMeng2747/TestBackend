package com.example.demo.bootscamp.dto.Res;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class TrackOrderRes {

    private String orderNumber;
    private String customerName;
    private String customerPhone;
    private String shippingAddress;
    private String status;
    private BigDecimal totalAmount;
    private LocalDateTime createdAt;
    private String shopName; // ✅ เพิ่ม shopName
    private List<TrackOrderItemRes> items;

    public TrackOrderRes(String orderNumber,
                         String customerName,
                         String customerPhone,
                         String shippingAddress,
                         String status,
                         BigDecimal totalAmount,
                         LocalDateTime createdAt,
                         String shopName, // ✅ เพิ่ม
                         List<TrackOrderItemRes> items) {
        this.orderNumber     = orderNumber;
        this.customerName    = customerName;
        this.customerPhone   = customerPhone;
        this.shippingAddress = shippingAddress;
        this.status          = status;
        this.totalAmount     = totalAmount;
        this.createdAt       = createdAt;
        this.shopName        = shopName; // ✅ เพิ่ม
        this.items           = items;
    }

    public String getOrderNumber()     { return orderNumber; }
    public String getCustomerName()    { return customerName; }
    public String getCustomerPhone()   { return customerPhone; }
    public String getShippingAddress() { return shippingAddress; }
    public String getStatus()          { return status; }
    public BigDecimal getTotalAmount() { return totalAmount; }
    public LocalDateTime getCreatedAt(){ return createdAt; }
    public String getShopName()        { return shopName; } // ✅ เพิ่ม
    public List<TrackOrderItemRes> getItems() { return items; }

    public static class TrackOrderItemRes {
        private String productName;
        private Integer quantity;
        private BigDecimal sellingPrice;

        public TrackOrderItemRes(String productName, Integer quantity, BigDecimal sellingPrice) {
            this.productName  = productName;
            this.quantity     = quantity;
            this.sellingPrice = sellingPrice;
        }

        public String getProductName()     { return productName; }
        public Integer getQuantity()       { return quantity; }
        public BigDecimal getSellingPrice(){ return sellingPrice; }
    }
}