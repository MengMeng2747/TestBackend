package com.example.demo.bootscamp.repository;

import com.example.demo.bootscamp.entity.ResellerProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ResellerProductRepository extends JpaRepository<ResellerProductEntity, Long> {

    Optional<ResellerProductEntity> findByResellerIdAndProductId(Long resellerId, Long productId);

}