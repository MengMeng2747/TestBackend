package com.example.demo.bootscamp.dto.Res;

import java.math.BigDecimal;
import java.util.List;

public class ResellerOrderRes {

    private Integer    orderId;
    private String     orderNumber;
    private String     customerName;
    private String     customerPhone;    // ✅ เพิ่ม
    private String     shippingAddress;  // ✅ เพิ่ม
    private String     productName;
    private Integer    quantity;
    private BigDecimal sellingPrice;
    private BigDecimal totalAmount;
    private String     status;
    private String     createdAt;
    private List<ResellerOrderItemRes> items; // ✅ เพิ่ม

    public ResellerOrderRes() {}

    public ResellerOrderRes(Integer orderId, String orderNumber,
                            String customerName, String customerPhone,
                            String shippingAddress,
                            String productName, Integer quantity,
                            BigDecimal sellingPrice, BigDecimal totalAmount,
                            String status, String createdAt,
                            List<ResellerOrderItemRes> items) {
        this.orderId         = orderId;
        this.orderNumber     = orderNumber;
        this.customerName    = customerName;
        this.customerPhone   = customerPhone;
        this.shippingAddress = shippingAddress;
        this.productName     = productName;
        this.quantity        = quantity;
        this.sellingPrice    = sellingPrice;
        this.totalAmount     = totalAmount;
        this.status          = status;
        this.createdAt       = createdAt;
        this.items           = items;
    }

    public Integer    getOrderId()         { return orderId; }
    public String     getOrderNumber()     { return orderNumber; }
    public String     getCustomerName()    { return customerName; }
    public String     getCustomerPhone()   { return customerPhone; }
    public String     getShippingAddress() { return shippingAddress; }
    public String     getProductName()     { return productName; }
    public Integer    getQuantity()        { return quantity; }
    public BigDecimal getSellingPrice()    { return sellingPrice; }
    public BigDecimal getTotalAmount()     { return totalAmount; }
    public String     getStatus()          { return status; }
    public String     getCreatedAt()       { return createdAt; }
    public List<ResellerOrderItemRes> getItems() { return items; }

    // ✅ Inner class
    public static class ResellerOrderItemRes {
        private String     productName;
        private Integer    quantity;
        private BigDecimal sellingPrice;

        public ResellerOrderItemRes(String productName, Integer quantity, BigDecimal sellingPrice) {
            this.productName  = productName;
            this.quantity     = quantity;
            this.sellingPrice = sellingPrice;
        }

        public String     getProductName()  { return productName; }
        public Integer    getQuantity()     { return quantity; }
        public BigDecimal getSellingPrice() { return sellingPrice; }
    }
}