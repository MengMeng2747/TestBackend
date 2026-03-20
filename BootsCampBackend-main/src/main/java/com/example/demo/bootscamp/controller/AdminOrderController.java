package com.example.demo.bootscamp.controller;

import com.example.demo.bootscamp.dto.Res.OrderRes;
import com.example.demo.bootscamp.service.AdminOrderService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/orders")
public class AdminOrderController {

    private final AdminOrderService adminOrderService;

    public AdminOrderController(AdminOrderService adminOrderService) {
        this.adminOrderService = adminOrderService;
    }

    // GET ALL — ส่ง OrderRes ที่มี shopName จริงจาก DB
    @GetMapping
    public List<OrderRes> getOrders() {
        return adminOrderService.getAllOrders();
    }

    // pending → shipped (BR-10)
    @PutMapping("/{id}/ship")
    public OrderRes shipOrder(@PathVariable Long id) {
        return adminOrderService.shipOrder(id);
    }

    // shipped → completed
    @PutMapping("/{id}/complete")
    public OrderRes completeOrder(@PathVariable Long id) {
        return adminOrderService.completeOrder(id);
    }
}