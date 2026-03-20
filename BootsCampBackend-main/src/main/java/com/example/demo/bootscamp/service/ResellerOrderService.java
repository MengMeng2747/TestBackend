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

            List<OrderItemsEntity> items = orderItemsRepository.findByOrderId(o.getId());

            // ✅ productName รวมทุก item สำหรับแสดงในตาราง
            String productName = items.stream()
                    .map(i -> i.getProductName() + " ×" + i.getQuantity())
                    .collect(Collectors.joining(", "));

            int totalQty = items.stream().mapToInt(OrderItemsEntity::getQuantity).sum();

            BigDecimal sellingPrice = items.isEmpty()
                    ? BigDecimal.ZERO
                    : items.get(0).getSellingPrice();

            String createdAt = o.getCreatedAt() != null ? o.getCreatedAt().toString() : null;

            // ✅ แปลง items เป็น ResellerOrderItemRes
            List<ResellerOrderRes.ResellerOrderItemRes> itemResList = items.stream()
                    .map(i -> new ResellerOrderRes.ResellerOrderItemRes(
                            i.getProductName(),
                            i.getQuantity(),
                            i.getSellingPrice()
                    ))
                    .collect(Collectors.toList());

            return new ResellerOrderRes(
                    o.getId(),
                    o.getOrderNumber(),
                    o.getCustomerName(),
                    o.getCustomerPhone(),      // ✅ เบอร์โทร
                    o.getShippingAddress(),    // ✅ ที่อยู่จัดส่ง
                    productName.isEmpty() ? null : productName,
                    totalQty,
                    sellingPrice,
                    o.getTotalAmount(),
                    o.getStatus(),
                    createdAt,
                    itemResList                // ✅ รายการสินค้า
            );
        }).collect(Collectors.toList());
    }
}