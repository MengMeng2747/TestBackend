package com.example.demo.bootscamp.controller;

import com.example.demo.bootscamp.dto.Res.AdminDashboardRes;
import com.example.demo.bootscamp.service.AdminDashboardService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminDashboardController {

    private final AdminDashboardService adminDashboardService;

    public AdminDashboardController(AdminDashboardService adminDashboardService) {
        this.adminDashboardService = adminDashboardService;
    }

    @GetMapping("/admin/dashboard")
    public AdminDashboardRes getDashboard() {
        return adminDashboardService.getDashboard();
    }
}