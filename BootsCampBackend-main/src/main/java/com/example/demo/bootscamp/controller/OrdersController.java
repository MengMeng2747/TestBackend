package com.example.demo.bootscamp.controller;

import com.example.demo.bootscamp.dto.Req.CreateOrderReq;
import com.example.demo.bootscamp.dto.Res.TrackOrderRes;
import com.example.demo.bootscamp.entity.OrdersEntity;
import com.example.demo.bootscamp.service.OrderService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrdersController {

    private final OrderService orderService;

    public OrdersController(OrderService orderService) {
        this.orderService = orderService;
    }

    // BR-26: สร้าง order → คืน orderNumber
    @PostMapping
    public String createOrder(@RequestBody CreateOrderReq req) {
        return orderService.createOrder(req);
    }

    // BR-28 + BR-29: จ่ายเงิน (จำลอง) → ตัดสต็อก รับ orderNumber
    @PutMapping("/{orderNumber}/pay")
    public String payOrder(@PathVariable String orderNumber) {
        return orderService.payOrder(orderNumber);
    }

    // BR-30 + BR-31: ติดตาม order ด้วยเลข order
    @GetMapping("/track/{orderNumber}")
    public TrackOrderRes trackOrder(@PathVariable String orderNumber) {
        return orderService.trackOrder(orderNumber);
    }

    // ดู order ของร้าน
    @GetMapping("/shop/{shopId}")
    public List<OrdersEntity> getOrdersByShop(@PathVariable Integer shopId) {
        return orderService.getOrdersByShop(shopId);
    }
}