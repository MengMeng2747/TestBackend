package com.example.demo.bootscamp.repository;

import com.example.demo.bootscamp.entity.ShopEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ShopRepository extends JpaRepository<ShopEntity, Integer> {

    boolean existsByShopSlug(String shopSlug);

    Optional<ShopEntity> findByUserId(Integer userId);

    // BR-24: เช็คว่า slug มีในระบบไหม
    Optional<ShopEntity> findByShopSlug(String shopSlug);
}