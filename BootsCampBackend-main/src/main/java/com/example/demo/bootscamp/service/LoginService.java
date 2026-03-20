package com.example.demo.bootscamp.service;

import com.example.demo.bootscamp.dto.Req.LoginReq;
import com.example.demo.bootscamp.entity.UserEntity;
import com.example.demo.bootscamp.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginService {

    private final UserRepository userRepository;

    public LoginService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String login(LoginReq req){

        Optional<UserEntity> userOpt = userRepository.findByEmail(req.getEmail());

        // BR-18
        if(userOpt.isEmpty()){
            throw new RuntimeException("อีเมลหรือรหัสผ่านไม่ถูกต้อง");
        }

        UserEntity user = userOpt.get();

        if(!user.getPassword().equals(req.getPassword())){
            throw new RuntimeException("อีเมลหรือรหัสผ่านไม่ถูกต้อง");
        }

        // ตรวจ role
        if(!"reseller".equals(user.getRole())){
            throw new RuntimeException("ไม่ใช่บัญชี Reseller");
        }

        // BR-16
        if("pending".equals(user.getStatus())){
            return "บัญชีรออนุมัติ กรุณารอการติดต่อ";
        }

        // BR-17
        if("rejected".equals(user.getStatus())){
            return "บัญชีนี้ไม่ได้รับการอนุมัติ";
        }

        // BR-15
        return "/reseller/dashboard";
    }
}