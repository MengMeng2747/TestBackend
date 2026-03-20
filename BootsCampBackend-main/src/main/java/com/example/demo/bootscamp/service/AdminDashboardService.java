package com.example.demo.bootscamp.service;

import com.example.demo.bootscamp.dto.Res.AdminDashboardRes;
import com.example.demo.bootscamp.repository.OrdersRepository;
import com.example.demo.bootscamp.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class AdminDashboardService {

    private final OrdersRepository orderRepository;
    private final UserRepository userRepository;

    public AdminDashboardService(OrdersRepository orderRepository,
                                 UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
    }

    public AdminDashboardRes getDashboard() {

        AdminDashboardRes res = new AdminDashboardRes();

        // ออเดอร์
        Long totalOrders = orderRepository.count();
        Long pendingOrders = orderRepository.countByStatus("pending");

        // ยอดขาย + กำไร
        BigDecimal totalSales = orderRepository.sumTotalSales();
        BigDecimal totalProfit = orderRepository.sumTotalProfit();

        if (totalSales == null) totalSales = BigDecimal.ZERO;
        if (totalProfit == null) totalProfit = BigDecimal.ZERO;

        // 2.5 แก้ไข: นับเฉพาะ reseller ที่ approved แล้วเท่านั้น
        Long totalResellers = userRepository.countByRoleAndStatus("reseller", "approved");

        // 2.5 แก้ไข: นับเฉพาะ reseller ที่ pending (filter role=reseller ด้วย)
        Long pendingResellers = userRepository.countByRoleAndStatus("reseller", "pending");

        res.setTotalOrders(totalOrders);
        res.setPendingOrders(pendingOrders);
        res.setTotalSales(totalSales);
        res.setTotalProfit(totalProfit);
        res.setTotalResellers(totalResellers);
        res.setPendingResellers(pendingResellers);

        return res;
    }
}