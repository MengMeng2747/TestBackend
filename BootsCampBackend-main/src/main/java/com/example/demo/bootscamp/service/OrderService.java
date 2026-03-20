package com.example.demo.bootscamp.service;

import com.example.demo.bootscamp.dto.Req.CreateOrderReq;
import com.example.demo.bootscamp.dto.Req.OrderItemReq;
import com.example.demo.bootscamp.dto.Res.TrackOrderRes;
import com.example.demo.bootscamp.entity.*;
import com.example.demo.bootscamp.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final ProductRepository productRepository;
    private final OrdersRepository ordersRepository;
    private final OrderItemsRepository orderItemsRepository;
    private final ResellerProductRepository resellerProductRepository;
    private final ShopRepository shopRepository;

    public OrderService(ProductRepository productRepository,
                        OrdersRepository ordersRepository,
                        OrderItemsRepository orderItemsRepository,
                        ResellerProductRepository resellerProductRepository,
                        ShopRepository shopRepository) {
        this.productRepository         = productRepository;
        this.ordersRepository          = ordersRepository;
        this.orderItemsRepository      = orderItemsRepository;
        this.resellerProductRepository = resellerProductRepository;
        this.shopRepository            = shopRepository;
    }

    @Transactional
    public String createOrder(CreateOrderReq req) {

        BigDecimal total          = BigDecimal.ZERO;
        BigDecimal resellerProfit = BigDecimal.ZERO;

        ShopEntity shop = shopRepository.findById(Math.toIntExact(req.shop_id))
                .orElseThrow(() -> new RuntimeException("ไม่พบร้านค้า"));

        Integer resellerId = shop.getUserId();

        OrdersEntity order = new OrdersEntity();
        String orderNumber = "ORD-" + System.currentTimeMillis();

        order.setOrderNumber(orderNumber);
        order.setShopId(Math.toIntExact(req.shop_id));
        order.setCustomerName(req.customer_name);
        order.setCustomerPhone(req.customer_phone);
        order.setShippingAddress(req.shipping_address);
        order.setStatus("pending");
        order.setCreatedAt(LocalDateTime.now());
        order.setTotalAmount(BigDecimal.ZERO);
        order.setResellerProfit(BigDecimal.ZERO);

        order = ordersRepository.save(order);

        for (OrderItemReq item : req.items) {
            ProductEntity product = productRepository.findById(Math.toIntExact(item.product_id))
                    .orElseThrow(() -> new RuntimeException("ไม่พบสินค้า"));

            if (item.quantity > product.getStock()) {
                throw new RuntimeException("สินค้าไม่เพียงพอ");
            }

            ResellerProductEntity rp = resellerProductRepository
                    .findByResellerIdAndProductId(resellerId.longValue(), item.product_id)
                    .orElseThrow(() -> new RuntimeException("Reseller ไม่มีสินค้านี้"));

            BigDecimal sellingPrice = rp.getSellingPrice();

            OrderItemsEntity orderItem = new OrderItemsEntity();
            orderItem.setOrderId(order.getId());
            orderItem.setProductId(product.getId());
            orderItem.setProductName(product.getName());
            orderItem.setCostPrice(product.getCostPrice());
            orderItem.setSellingPrice(sellingPrice);
            orderItem.setQuantity(item.quantity);

            orderItemsRepository.save(orderItem);

            BigDecimal subtotal = sellingPrice.multiply(BigDecimal.valueOf(item.quantity));
            total = total.add(subtotal);

            BigDecimal profit = sellingPrice
                    .subtract(product.getCostPrice())
                    .multiply(BigDecimal.valueOf(item.quantity));
            resellerProfit = resellerProfit.add(profit);
        }

        order.setTotalAmount(total);
        order.setResellerProfit(resellerProfit);
        ordersRepository.save(order);

        return order.getOrderNumber();
    }

    @Transactional
    public String payOrder(String orderNumber) {

        OrdersEntity order = ordersRepository.findByOrderNumber(orderNumber)
                .orElseThrow(() -> new RuntimeException("ไม่พบออเดอร์"));

        if (!"pending".equalsIgnoreCase(order.getStatus())) {
            throw new RuntimeException("ออเดอร์นี้ชำระเงินแล้ว");
        }

        List<OrderItemsEntity> items = orderItemsRepository.findByOrderId(order.getId());

        for (OrderItemsEntity item : items) {
            ProductEntity product = productRepository.findById(item.getProductId())
                    .orElseThrow(() -> new RuntimeException("ไม่พบสินค้า"));

            if (item.getQuantity() > product.getStock()) {
                throw new RuntimeException("สินค้า " + product.getName() + " ไม่เพียงพอ");
            }

            product.setStock(product.getStock() - item.getQuantity());
            productRepository.save(product);
        }

        ordersRepository.save(order);
        return order.getOrderNumber();
    }

    public TrackOrderRes trackOrder(String orderNumber) {

        OrdersEntity order = ordersRepository.findByOrderNumber(orderNumber)
                .orElseThrow(() -> new RuntimeException("ไม่พบออเดอร์นี้"));

        // ✅ ดึง shopName โดย findById ก่อน ถ้าไม่เจอให้ลอง findByUserId
        String shopName = shopRepository.findById(order.getShopId())
                .map(ShopEntity::getShopName)
                .orElseGet(() ->
                        // fallback: หาจาก userId ถ้า shopId ใน order เป็น userId แทน
                        shopRepository.findByUserId(order.getShopId())
                                .map(ShopEntity::getShopName)
                                .orElse("—")
                );

        List<OrderItemsEntity> items = orderItemsRepository.findByOrderId(order.getId());

        List<TrackOrderRes.TrackOrderItemRes> itemResList = items.stream()
                .map(i -> new TrackOrderRes.TrackOrderItemRes(
                        i.getProductName(),
                        i.getQuantity(),
                        i.getSellingPrice()
                ))
                .collect(Collectors.toList());

        return new TrackOrderRes(
                order.getOrderNumber(),
                order.getCustomerName(),
                order.getCustomerPhone(),
                order.getShippingAddress(),
                order.getStatus(),
                order.getTotalAmount(),
                order.getCreatedAt(),
                shopName,
                itemResList
        );
    }

    public List<OrdersEntity> getOrdersByShop(Integer shopId) {
        return ordersRepository.findByShopId(shopId);
    }
}