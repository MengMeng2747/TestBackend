package com.example.demo.bootscamp.service;

import com.example.demo.bootscamp.dto.Req.AdminLoginReq;
import com.example.demo.bootscamp.entity.UserEntity;
import com.example.demo.bootscamp.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    private final UserRepository userRepository;

    public AdminService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserEntity login(AdminLoginReq req) {

        // BR-02: หา user จาก email
        UserEntity user = userRepository
                .findByEmail(req.getEmail())
                .orElseThrow(() -> new RuntimeException("อีเมลหรือรหัสผ่านไม่ถูกต้อง"));

        // BR-02: เช็ค password
        if (!user.getPassword().equals(req.getPassword())) {
            throw new RuntimeException("อีเมลหรือรหัสผ่านไม่ถูกต้อง");
        }

        // BR-04: เช็ค role ต้องเป็น admin เท่านั้น
        if (!"admin".equals(user.getRole())) {
            throw new RuntimeException("คุณไม่มีสิทธิ์เข้าถึงส่วนนี้");
        }

        return user;
    }
}