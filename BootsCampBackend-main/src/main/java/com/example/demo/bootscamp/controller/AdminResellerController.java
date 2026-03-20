package com.example.demo.bootscamp.controller;

import com.example.demo.bootscamp.dto.Res.ResellerRes;
import com.example.demo.bootscamp.service.AdminResellerService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/resellers")
public class AdminResellerController {

    private final AdminResellerService adminResellerService;

    public AdminResellerController(AdminResellerService adminResellerService) {
        this.adminResellerService = adminResellerService;
    }

    // GET ALL — ส่ง ResellerRes ที่มี shopName + createdAt
    @GetMapping("/all")
    public List<ResellerRes> getResellers() {
        return adminResellerService.getResellers();
    }

    // APPROVE
    @PutMapping("/put/{id}/approve")
    public ResellerRes approve(@PathVariable Long id) {
        return adminResellerService.approveReseller(id);
    }

    // REJECT
    @PutMapping("/put/{id}/reject")
    public ResellerRes reject(@PathVariable Long id) {
        return adminResellerService.rejectReseller(id);
    }
}