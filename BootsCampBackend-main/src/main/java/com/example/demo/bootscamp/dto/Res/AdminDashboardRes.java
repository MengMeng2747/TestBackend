package com.example.demo.bootscamp.dto.Res;

import java.math.BigDecimal;

public class AdminDashboardRes {

    private Long totalOrders;
    private Long pendingOrders;
    private BigDecimal totalSales;
    private BigDecimal totalProfit;
    private Long totalResellers;
    private Long pendingResellers;

    public Long getPendingResellers() {
        return pendingResellers;
    }

    public void setPendingResellers(Long pendingResellers) {
        this.pendingResellers = pendingResellers;
    }

    public Long getTotalOrders() {
        return totalOrders;
    }

    public void setTotalOrders(Long totalOrders) {
        this.totalOrders = totalOrders;
    }

    public Long getPendingOrders() {
        return pendingOrders;
    }

    public void setPendingOrders(Long pendingOrders) {
        this.pendingOrders = pendingOrders;
    }

    public BigDecimal getTotalSales() {
        return totalSales;
    }

    public void setTotalSales(BigDecimal totalSales) {
        this.totalSales = totalSales;
    }

    public BigDecimal getTotalProfit() {
        return totalProfit;
    }

    public void setTotalProfit(BigDecimal totalProfit) {
        this.totalProfit = totalProfit;
    }

    public Long getTotalResellers() {
        return totalResellers;
    }

    public void setTotalResellers(Long totalResellers) {
        this.totalResellers = totalResellers;
    }
}