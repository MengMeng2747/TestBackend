package com.example.demo.bootscamp.service;

import com.example.demo.bootscamp.dto.Res.ResellerOrderRes;
import com.example.demo.bootscamp.entity.OrderItemsEntity;
import com.example.demo.bootscamp.entity.OrdersEntity;
import com.example.demo.bootscamp.repository.OrderItemsRepository;
import com.example.demo.bootscamp.repository.OrdersRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ResellerOrderService {

    private final OrdersRepository     ordersRepository;
    private final OrderItemsRepository orderItemsRepository;

    public ResellerOrderService(OrdersRepository ordersRepository,
                                OrderItemsRepository orderItemsRepository) {
        this.ordersRepository     = ordersRepository;
        this.orderItemsRepository = orderItemsRepository;
    }

    public List<ResellerOrderRes> getOrders(Long resellerId) {

        List<OrdersEntity> orders = ordersRepository.getResellerOrders(Math.toIntExact(resellerId));

        return orders.stream().map(o -> {

            // ดึง items ของแต่ละ order
            List<OrderItemsEntity> items = orderItemsRepository.findByOrderId(o.getId());

            // รวมชื่อสินค้าทั้งหมด
            String productName = items.stream()
                    .map(i -> i.getProductName() + " ×" + i.getQuantity())
                    .collect(Collectors.joining(", "));

            // จำนวนรวม
            int totalQty = items.stream()
                    .mapToInt(OrderItemsEntity::getQuantity)
                    .sum();

            // ราคาขายของ item แรก (ใช้แสดงราคา/ชิ้น)
            BigDecimal sellingPrice = items.isEmpty()
                    ? BigDecimal.ZERO
                    : items.get(0).getSellingPrice();

            // วันที่สั่งซื้อจริง
            String createdAt = o.getCreatedAt() != null
                    ? o.getCreatedAt().toString()
                    : null;

            return new ResellerOrderRes(
                    o.getId(),
                    o.getOrderNumber(),        // ← เลขออเดอร์จริง
                    o.getCustomerName(),
                    productName.isEmpty() ? null : productName,
                    totalQty,
                    sellingPrice,
                    o.getTotalAmount(),        // ← ยอดขายรวม
                    o.getStatus(),
                    createdAt                  // ← วันที่จริง
            );
        }).collect(Collectors.toList());
    }
}