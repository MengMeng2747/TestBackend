package com.example.demo.bootscamp.repository;

import com.example.demo.bootscamp.entity.OrdersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface OrdersRepository extends JpaRepository<OrdersEntity, Integer> {

    List<OrdersEntity> findByShopId(Integer shopId);

    Long countByStatus(String status);

    @Query("SELECT SUM(o.totalAmount) FROM OrdersEntity o WHERE o.status IN ('shipped', 'completed')")
    BigDecimal sumTotalSales();

    @Query("SELECT SUM(o.resellerProfit) FROM OrdersEntity o WHERE o.status IN ('shipped', 'completed')")
    BigDecimal sumTotalProfit();

    @Query("""
        SELECT o FROM OrdersEntity o
        JOIN ShopEntity s ON s.id = o.shopId
        WHERE s.userId = :resellerId
    """)
    List<OrdersEntity> getResellerOrders(@Param("resellerId") Integer resellerId);

    // BR-30/31: ค้นหา order ด้วยเลข order
    Optional<OrdersEntity> findByOrderNumber(String orderNumber);
}