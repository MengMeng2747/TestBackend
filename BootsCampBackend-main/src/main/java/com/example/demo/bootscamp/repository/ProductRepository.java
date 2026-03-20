package com.example.demo.bootscamp.repository;

import com.example.demo.bootscamp.dto.Res.CatalogProductRes;
import com.example.demo.bootscamp.dto.Res.ResellerProductRes;
import com.example.demo.bootscamp.dto.Res.ShopProductRes;
import com.example.demo.bootscamp.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<ProductEntity, Integer> {

    // ✅ เพิ่ม p.imageUrl ใน query
    @Query("""
SELECT new com.example.demo.bootscamp.dto.Res.CatalogProductRes(
    p.id,
    p.name,
    p.imageUrl,
    p.costPrice,
    p.minPrice,
    p.stock
)
FROM ProductEntity p
""")
    List<CatalogProductRes> getCatalogProducts();

    @Query("""
    SELECT new com.example.demo.bootscamp.dto.Res.ResellerProductRes(
        p.id,
        p.name,
        rp.sellingPrice,
        p.stock
    )
    FROM ResellerProductEntity rp
    JOIN ProductEntity p ON rp.productId = p.id
    WHERE rp.resellerId = :resellerId
    """)
    List<ResellerProductRes> getResellerProducts(Long resellerId);

    @Query("""
SELECT new com.example.demo.bootscamp.dto.Res.ShopProductRes(
    p.id,
    p.name,
    p.imageUrl,
    rp.sellingPrice,
    p.stock
)
FROM ResellerProductEntity rp
JOIN ProductEntity p ON p.id = rp.productId
JOIN ShopEntity s ON s.userId = rp.resellerId
WHERE s.shopSlug = :slug
""")
    List<ShopProductRes> getProductsByShopSlug(String slug);
}