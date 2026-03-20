package com.example.demo.bootscamp.repository;

import com.example.demo.bootscamp.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    Optional<UserEntity> findByEmail(String email);

    List<UserEntity> findByRole(String role);

    long countByRole(String role);

    boolean existsByEmail(String email);

    long countByStatus(String status);

    // Dashboard — นับเฉพาะ reseller ที่ approved แล้ว (2.5)
    long countByRoleAndStatus(String role, String status);

}