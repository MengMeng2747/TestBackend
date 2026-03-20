package com.example.demo.bootscamp.controller;

import com.example.demo.bootscamp.dto.Req.AdminLoginReq;
import com.example.demo.bootscamp.entity.UserEntity;
import com.example.demo.bootscamp.service.AdminService;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminAuthController {

    private final AdminService adminService;

    public AdminAuthController(AdminService adminService) {
        this.adminService = adminService;
    }

    // BR-01: Login สำเร็จ → เก็บ session + redirect /admin/dashboard
    // BR-02: Login ผิด → throw error จาก AdminService
    @PostMapping("/login")
    public String login(@RequestBody AdminLoginReq req, HttpSession session) {

        UserEntity user = adminService.login(req);

        // เก็บข้อมูลลง Session
        session.setAttribute("adminUser", user.getId());
        session.setAttribute("role", user.getRole());
        session.setAttribute("name", user.getName());

        // BR-01: redirect ไป dashboard
        return "/admin/dashboard";
    }

    // Logout — ล้าง session
    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "ออกจากระบบสำเร็จ";
    }
}