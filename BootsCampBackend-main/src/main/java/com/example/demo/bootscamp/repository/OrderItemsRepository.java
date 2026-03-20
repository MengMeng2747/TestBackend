package com.example.demo.bootscamp.repository;

import com.example.demo.bootscamp.entity.OrderItemsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderItemsRepository extends JpaRepository<OrderItemsEntity, Integer> {

    List<OrderItemsEntity> findByOrderId(Integer orderId);

    // เดิม — เช็คทุก order (ใช้ใน logic อื่น)
    boolean existsByProductId(Integer productId);

    // BR-06 — เช็คเฉพาะ order ที่ยังไม่เสร็จ (status = pending หรือ shipped)
    @Query(value = """
        SELECT COUNT(oi.id)
        FROM order_items oi
        INNER JOIN orders o ON o.id = oi.order_id
        WHERE oi.product_id = :productId
        AND o.status IN ('pending', 'shipped')
    """, nativeQuery = true)
    int countActiveOrdersByProductId(@Param("productId") Integer productId);

    // ลบ order_items ที่อ้างถึง product นี้ (ใช้ตอนลบ product ที่มีแค่ completed orders)
    void deleteByProductId(Integer productId);
}