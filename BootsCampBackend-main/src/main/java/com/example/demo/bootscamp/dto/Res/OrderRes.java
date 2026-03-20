package com.example.demo.bootscamp.dto.Res;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class OrderRes {

    private Integer     id;
    private String      orderNumber;
    private Integer     shopId;
    private String      shopName;
    private String      customerName;
    private String      customerPhone;
    private String      shippingAddress;
    private BigDecimal  totalAmount;
    private BigDecimal  resellerProfit;
    private String      status;
    private String      createdAt;
    private List<OrderItemRes> items; // ✅ เพิ่ม items

    public OrderRes() {}

    public OrderRes(Integer id, String orderNumber, Integer shopId, String shopName,
                    String customerName, String customerPhone, String shippingAddress,
                    BigDecimal totalAmount, BigDecimal resellerProfit,
                    String status, LocalDateTime createdAt,
                    List<OrderItemRes> items) { // ✅ เพิ่ม items
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
        this.items           = items;
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
    public List<OrderItemRes> getItems()   { return items; } // ✅ เพิ่ม

    // ✅ Inner class สำหรับ item แต่ละชิ้น
    public static class OrderItemRes {
        private String     productName;
        private Integer    quantity;
        private BigDecimal sellingPrice;
        private BigDecimal costPrice;

        public OrderItemRes(String productName, Integer quantity,
                            BigDecimal sellingPrice, BigDecimal costPrice) {
            this.productName  = productName;
            this.quantity     = quantity;
            this.sellingPrice = sellingPrice;
            this.costPrice    = costPrice;
        }

        public String     getProductName()  { return productName; }
        public Integer    getQuantity()     { return quantity; }
        public BigDecimal getSellingPrice() { return sellingPrice; }
        public BigDecimal getCostPrice()    { return costPrice; }
    }
}