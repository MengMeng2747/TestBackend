package com.example.demo.bootscamp.repository;

import com.example.demo.bootscamp.entity.WalletLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WalletLogRepository extends JpaRepository<WalletLogEntity, Integer> {

    List<WalletLogEntity> findByUserId(Integer userId);

}