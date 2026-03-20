package com.example.demo.bootscamp.dto.Res;

import java.math.BigDecimal;

public class ResellerProductRes {

    public Integer id;
    public String name;
    public BigDecimal selling_price;
    public Integer stock;

    public ResellerProductRes(Integer id, String name, BigDecimal selling_price, Integer stock) {
        this.id = id;
        this.name = name;
        this.selling_price = selling_price;
        this.stock = stock;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getSelling_price() {
        return selling_price;
    }

    public void setSelling_price(BigDecimal selling_price) {
        this.selling_price = selling_price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }
}