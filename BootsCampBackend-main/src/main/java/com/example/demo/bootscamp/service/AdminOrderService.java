package com.example.demo.bootscamp.service;

import com.example.demo.bootscamp.dto.Res.OrderRes;
import com.example.demo.bootscamp.entity.*;
import com.example.demo.bootscamp.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminOrderService {

    private final OrdersRepository     orderRepository;
    private final OrderItemsRepository orderItemRepository;
    private final WalletRepository     walletRepository;
    private final WalletLogRepository  walletLogRepository;
    private final ShopRepository       shopRepository;

    public AdminOrderService(OrdersRepository orderRepository,
                             OrderItemsRepository orderItemRepository,
                             WalletRepository walletRepository,
                             WalletLogRepository walletLogRepository,
                             ShopRepository shopRepository) {
        this.orderRepository     = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.walletRepository    = walletRepository;
        this.walletLogRepository = walletLogRepository;
        this.shopRepository      = shopRepository;
    }

    // ── helper: แปลง OrderItemsEntity → OrderItemRes ─────────────────────────
    private List<OrderRes.OrderItemRes> toItemRes(Integer orderId) {
        return orderItemRepository.findByOrderId(orderId)
                .stream()
                .map(i -> new OrderRes.OrderItemRes(
                        i.getProductName(),
                        i.getQuantity(),
                        i.getSellingPrice(),
                        i.getCostPrice()
                ))
                .collect(Collectors.toList());
    }

    // ── helper: OrdersEntity → OrderRes ──────────────────────────────────────
    private OrderRes toRes(OrdersEntity o) {
        String shopName = shopRepository.findById(o.getShopId())
                .map(ShopEntity::getShopName)
                .orElse("Shop #" + o.getShopId());

        // ✅ ดึง items มาด้วย
        List<OrderRes.OrderItemRes> items = toItemRes(o.getId());

        return new OrderRes(
                o.getId(),
                o.getOrderNumber(),
                o.getShopId(),
                shopName,
                o.getCustomerName(),
                o.getCustomerPhone(),
                o.getShippingAddress(),
                o.getTotalAmount(),
                o.getResellerProfit(),
                o.getStatus(),
                o.getCreatedAt(),
                items // ✅ ส่ง items ไปด้วย
        );
    }

    // GET ALL ORDERS
    public List<OrderRes> getAllOrders() {
        return orderRepository.findAll()
                .stream()
                .map(this::toRes)
                .collect(Collectors.toList());
    }

    // SHIP ORDER (pending → shipped) BR-10
    @Transactional
    public OrderRes shipOrder(Long orderId) {

        OrdersEntity order = orderRepository.findById(orderId.intValue())
                .orElseThrow(() -> new RuntimeException("ไม่พบ order"));

        if (!"pending".equalsIgnoreCase(order.getStatus())) {
            throw new RuntimeException("ออเดอร์นี้ถูกจัดส่งแล้ว");
        }

        List<OrderItemsEntity> items = orderItemRepository.findByOrderId(order.getId());
        if (items.isEmpty()) throw new RuntimeException("order ไม่มีสินค้า");

        // คำนวณกำไร reseller (BR-11)
        BigDecimal totalProfit = BigDecimal.ZERO;
        for (OrderItemsEntity item : items) {
            BigDecimal profit = item.getSellingPrice()
                    .subtract(item.getCostPrice())
                    .multiply(BigDecimal.valueOf(item.getQuantity()));
            totalProfit = totalProfit.add(profit);
        }

        ShopEntity shop = shopRepository.findById(order.getShopId())
                .orElseThrow(() -> new RuntimeException("ไม่พบ shop"));
        Integer userId = shop.getUserId();

        WalletEntity wallet = walletRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("ไม่พบ wallet"));

        wallet.setBalance(wallet.getBalance().add(totalProfit));
        walletRepository.save(wallet);

        WalletLogEntity log = new WalletLogEntity();
        log.setWalletId(wallet.getId());
        log.setOrderId(order.getId());
        log.setUserId(userId);
        log.setAmount(totalProfit);
        log.setType("PROFIT");
        log.setCreatedAt(LocalDateTime.now());
        walletLogRepository.save(log);

        order.setStatus("shipped");
        return toRes(orderRepository.save(order));
    }

    // COMPLETE ORDER (shipped → completed)
    @Transactional
    public OrderRes completeOrder(Long orderId) {

        OrdersEntity order = orderRepository.findById(orderId.intValue())
                .orElseThrow(() -> new RuntimeException("ไม่พบ order"));

        if (!"shipped".equalsIgnoreCase(order.getStatus())) {
            throw new RuntimeException("ออเดอร์ต้องเป็น 'จัดส่งแล้ว' ก่อนจึงจะเสร็จสมบูรณ์ได้");
        }

        order.setStatus("completed");
        return toRes(orderRepository.save(order));
    }
}