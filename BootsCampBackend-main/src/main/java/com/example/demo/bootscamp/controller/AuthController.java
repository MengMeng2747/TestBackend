package com.example.demo.bootscamp.controller;

import com.example.demo.bootscamp.dto.Req.RegisterReq;
import com.example.demo.bootscamp.dto.Req.LoginReq;
import com.example.demo.bootscamp.entity.UserEntity;
import com.example.demo.bootscamp.entity.ShopEntity;
import com.example.demo.bootscamp.repository.UserRepository;
import com.example.demo.bootscamp.repository.ShopRepository;
import com.example.demo.bootscamp.service.RegisterService;
import com.example.demo.bootscamp.service.LoginService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class AuthController {

    private final RegisterService registerService;
    private final LoginService loginService;
    private final UserRepository userRepository;
    private final ShopRepository shopRepository;

    public AuthController(RegisterService registerService,
                          LoginService loginService,
                          UserRepository userRepository,
                          ShopRepository shopRepository) {
        this.registerService = registerService;
        this.loginService    = loginService;
        this.userRepository  = userRepository;
        this.shopRepository  = shopRepository;
    }

    @PostMapping("/register")
    public String register(@Valid @RequestBody RegisterReq req) {
        return registerService.register(req);
    }

    @PostMapping("/login")
    public String login(@Valid @RequestBody LoginReq req, HttpSession session) {
        String result = loginService.login(req);

        // ถ้า login สำเร็จ (approved) → เก็บ session
        if (result.equals("/reseller/dashboard")) {
            Optional<UserEntity> userOpt = userRepository.findByEmail(req.getEmail());
            userOpt.ifPresent(user -> {
                session.setAttribute("resellerId", user.getId());
                session.setAttribute("resellerEmail", user.getEmail());
                session.setAttribute("resellerName", user.getName());
                session.setAttribute("role", user.getRole());
            });
        }

        return result;
    }

    // ── GET /api/me → คืนข้อมูล reseller ที่ login อยู่ ──────
    @GetMapping("/me")
    public ResponseEntity<Map<String, Object>> me(HttpSession session) {
        Integer resellerId = (Integer) session.getAttribute("resellerId");

        if (resellerId == null) {
            return ResponseEntity.status(401).body(Map.of("error", "ยังไม่ได้ login"));
        }

        Optional<UserEntity> userOpt = userRepository.findById(resellerId);
        if (userOpt.isEmpty()) {
            return ResponseEntity.status(404).body(Map.of("error", "ไม่พบ user"));
        }

        UserEntity user = userOpt.get();
        Optional<ShopEntity> shopOpt = shopRepository.findByUserId(resellerId);

        Map<String, Object> res = new HashMap<>();
        res.put("id",       user.getId());
        res.put("name",     user.getName());
        res.put("email",    user.getEmail());
        res.put("phone",    user.getPhone());
        res.put("address",  user.getAddress());
        res.put("status",   user.getStatus());
        res.put("shopName", shopOpt.map(ShopEntity::getShopName).orElse(""));
        res.put("shopSlug", shopOpt.map(ShopEntity::getShopSlug).orElse(""));

        return ResponseEntity.ok(res);
    }
}