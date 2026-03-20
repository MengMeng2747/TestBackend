package com.example.demo.bootscamp.dto.Res;

import java.math.BigDecimal;

public class CatalogProductRes {

    public Integer id;
    public String name;
    public String imageUrl;   // ✅ เพิ่ม imageUrl
    public BigDecimal cost_price;
    public BigDecimal min_price;
    public Integer stock;

    public CatalogProductRes(
            Integer id,
            String name,
            String imageUrl,
            BigDecimal cost_price,
            BigDecimal min_price,
            Integer stock
    ) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.cost_price = cost_price;
        this.min_price = min_price;
        this.stock = stock;
    }

    public Integer getId() { return id; }
    public String getName() { return name; }
    public String getImageUrl() { return imageUrl; }
    public BigDecimal getCost_price() { return cost_price; }
    public BigDecimal getMin_price() { return min_price; }
    public Integer getStock() { return stock; }
}