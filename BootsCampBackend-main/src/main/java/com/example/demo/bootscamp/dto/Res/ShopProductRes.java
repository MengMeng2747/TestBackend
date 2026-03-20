package com.example.demo.bootscamp.dto.Res;

import java.math.BigDecimal;

public class ShopProductRes {

    public Integer product_id;
    public String product_name;
    public String image;
    public BigDecimal price;
    public Integer stock;
    public Boolean isSoldOut; // BR-25: stock = 0 → true

    public ShopProductRes(Integer product_id,
                          String product_name,
                          String image,
                          BigDecimal price,
                          Integer stock) {
        this.product_id = product_id;
        this.product_name = product_name;
        this.image = image;
        this.price = price;
        this.stock = stock;
        this.isSoldOut = (stock != null && stock <= 0); // BR-25
    }

    public Integer getProduct_id() { return product_id; }
    public String getProduct_name() { return product_name; }
    public String getImage() { return image; }
    public BigDecimal getPrice() { return price; }
    public Integer getStock() { return stock; }
    public Boolean getIsSoldOut() { return isSoldOut; }
}