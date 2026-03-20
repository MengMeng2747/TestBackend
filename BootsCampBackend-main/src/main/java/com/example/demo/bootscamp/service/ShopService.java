package com.example.demo.bootscamp.service;

import com.example.demo.bootscamp.dto.Res.ShopProductRes;
import com.example.demo.bootscamp.entity.ShopEntity;
import com.example.demo.bootscamp.repository.ProductRepository;
import com.example.demo.bootscamp.repository.ShopRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShopService {

    private final ProductRepository productRepository;
    private final ShopRepository shopRepository;

    public ShopService(ProductRepository productRepository,
                       ShopRepository shopRepository) {
        this.productRepository = productRepository;
        this.shopRepository = shopRepository;
    }

    public List<ShopProductRes> getShopProducts(String slug) {

        // BR-24: ตรวจว่า slug มีในระบบไหม ถ้าไม่มี → 404
        shopRepository.findByShopSlug(slug)
                .orElseThrow(() -> new RuntimeException("ไม่พบร้านค้านี้"));

        return productRepository.getProductsByShopSlug(slug);
    }

    public String getShopUrl(Integer userId) {

        ShopEntity shop = shopRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("shop not found"));

        return "/shop/" + shop.getShopSlug();
    }
}