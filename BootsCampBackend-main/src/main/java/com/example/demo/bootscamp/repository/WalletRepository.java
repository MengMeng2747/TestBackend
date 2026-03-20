package com.example.demo.bootscamp.repository;

import com.example.demo.bootscamp.entity.WalletEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WalletRepository extends JpaRepository<WalletEntity, Integer> {

    Optional<WalletEntity> findByUserId(Integer userId);

}