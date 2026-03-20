package com.example.demo.bootscamp.dto.Res;

import java.math.BigDecimal;

public class ResellerOrderRes {

    private Integer    orderId;
    private String     orderNumber;   // ← เพิ่ม: เลขออเดอร์จริง
    private String     customerName;
    private String     productName;
    private Integer    quantity;
    private BigDecimal sellingPrice;
    private BigDecimal totalAmount;   // ← เพิ่ม: ยอดขายรวม
    private String     status;
    private String     createdAt;     // ← เพิ่ม: วันที่สั่งซื้อจริง

    public ResellerOrderRes() {}

    public ResellerOrderRes(Integer orderId,
                            String orderNumber,
                            String customerName,
                            String productName,
                            Integer quantity,
                            BigDecimal sellingPrice,
                            BigDecimal totalAmount,
                            String status,
                            String createdAt) {
        this.orderId      = orderId;
        this.orderNumber  = orderNumber;
        this.customerName = customerName;
        this.productName  = productName;
        this.quantity     = quantity;
        this.sellingPrice = sellingPrice;
        this.totalAmount  = totalAmount;
        this.status       = status;
        this.createdAt    = createdAt;
    }

    public Integer    getOrderId()      { return orderId; }
    public String     getOrderNumber()  { return orderNumber; }
    public String     getCustomerName() { return customerName; }
    public String     getProductName()  { return productName; }
    public Integer    getQuantity()     { return quantity; }
    public BigDecimal getSellingPrice() { return sellingPrice; }
    public BigDecimal getTotalAmount()  { return totalAmount; }
    public String     getStatus()       { return status; }
    public String     getCreatedAt()    { return createdAt; }
}