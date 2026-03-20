package com.example.demo.bootscamp.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "shop_products")
public class ShopProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "shop_id")
    private Integer shopId;

    @Column(name = "product_id")
    private Integer productId;

    @Column(name = "selling_price")
    private BigDecimal sellingPrice;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Integer getShopId() { return shopId; }
    public void setShopId(Integer shopId) { this.shopId = shopId; }

    public Integer getProductId() { return productId; }
    public void setProductId(Integer productId) { this.productId = productId; }

    public BigDecimal getSellingPrice() { return sellingPrice; }
    public void setSellingPrice(BigDecimal sellingPrice) { this.sellingPrice = sellingPrice; }
}