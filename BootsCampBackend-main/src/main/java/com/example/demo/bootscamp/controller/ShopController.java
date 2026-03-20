package com.example.demo.bootscamp.controller;

import com.example.demo.bootscamp.dto.Res.ShopProductRes;
import com.example.demo.bootscamp.entity.ShopEntity;
import com.example.demo.bootscamp.repository.ShopRepository;
import com.example.demo.bootscamp.service.ShopService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/shop")
public class ShopController {

    private final ShopService shopService;
    private final ShopRepository shopRepository;

    public ShopController(ShopService shopService, ShopRepository shopRepository) {
        this.shopService    = shopService;
        this.shopRepository = shopRepository;
    }

    // แสดงสินค้าในร้าน
    @GetMapping("/{slug}")
    public List<ShopProductRes> getShop(@PathVariable String slug) {
        return shopService.getShopProducts(slug);
    }

    // คืน shop URL ของ reseller
    @GetMapping("/my-shop/{userId}")
    public String getMyShop(@PathVariable Integer userId) {
        return shopService.getShopUrl(userId);
    }

    // ── endpoint ใหม่: คืน shop_id + shopName จาก slug ──
    // ใช้ตอน checkout เพื่อหา shop_id จริงๆ
    @GetMapping("/info/{slug}")
    public ResponseEntity<Map<String, Object>> getShopInfo(@PathVariable String slug) {
        return shopRepository.findByShopSlug(slug)
                .map(shop -> {
                    Map<String, Object> res = new HashMap<>();
                    res.put("id",       shop.getId());
                    res.put("shopName", shop.getShopName());
                    res.put("shopSlug", shop.getShopSlug());
                    res.put("userId",   shop.getUserId());
                    return ResponseEntity.ok(res);
                })
                .orElse(ResponseEntity.notFound().build());
    }
}