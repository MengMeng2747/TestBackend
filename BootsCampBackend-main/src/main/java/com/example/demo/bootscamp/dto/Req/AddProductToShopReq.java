package com.example.demo.bootscamp.dto.Req;

import java.math.BigDecimal;

public class AddProductToShopReq {

    public Integer reseller_id;
    public Long product_id;
    public BigDecimal selling_price;

    public Integer getReseller_id() {
        return reseller_id;
    }

    public void setReseller_id(Integer reseller_id) {
        this.reseller_id = reseller_id;
    }

    public Long getProduct_id() {
        return product_id;
    }

    public void setProduct_id(Long product_id) {
        this.product_id = product_id;
    }

    public BigDecimal getSelling_price() {
        return selling_price;
    }

    public void setSelling_price(BigDecimal selling_price) {
        this.selling_price = selling_price;
    }
}