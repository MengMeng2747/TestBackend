package com.example.demo.bootscamp.service;

import com.example.demo.bootscamp.dto.Req.AddProductToShopReq;
import com.example.demo.bootscamp.dto.Res.CatalogProductRes;
import com.example.demo.bootscamp.dto.Res.ResellerProductRes;
import com.example.demo.bootscamp.entity.ProductEntity;
import com.example.demo.bootscamp.entity.ResellerProductEntity;
import com.example.demo.bootscamp.repository.ProductRepository;
import com.example.demo.bootscamp.repository.ResellerProductRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ResellerCatalogService {

    private final ProductRepository          productRepository;
    private final ResellerProductRepository  resellerProductRepository;

    public ResellerCatalogService(ProductRepository productRepository,
                                  ResellerProductRepository resellerProductRepository) {
        this.productRepository         = productRepository;
        this.resellerProductRepository = resellerProductRepository;
    }

    // ── เพิ่ม / แก้ราคาสินค้าในร้าน ─────────────────────────────────────────
    public String addProduct(AddProductToShopReq req) {

        ProductEntity product = productRepository
                .findById(Math.toIntExact(req.product_id))
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // BR-20 stock
        if (product.getStock() <= 0) {
            throw new RuntimeException("สินค้าหมด");
        }

        // BR-19 min price
        if (req.selling_price.compareTo(product.getMinPrice()) < 0) {
            throw new RuntimeException("ราคาขายต่ำกว่าราคาขั้นต่ำ");
        }

        Optional<ResellerProductEntity> existing =
                resellerProductRepository.findByResellerIdAndProductId(
                        Long.valueOf(req.reseller_id),
                        req.product_id
                );

        // BR-21 update price
        if (existing.isPresent()) {
            ResellerProductEntity rp = existing.get();
            rp.setSellingPrice(req.selling_price);
            resellerProductRepository.save(rp);
            return "แก้ไขราคาสำเร็จ";
        }

        // add new product
        ResellerProductEntity rp = new ResellerProductEntity();
        rp.setResellerId(req.reseller_id);
        rp.setProductId(Math.toIntExact(req.product_id));
        rp.setSellingPrice(req.selling_price);
        rp.setCreatedAt(LocalDateTime.now());
        resellerProductRepository.save(rp);
        return "เพิ่มสินค้าเข้าร้านสำเร็จ";
    }

    // ── ลบสินค้าออกจากร้าน ───────────────────────────────────────────────────
    public String removeProduct(Long resellerId, Long productId) {
        ResellerProductEntity rp = resellerProductRepository
                .findByResellerIdAndProductId(resellerId, productId)
                .orElseThrow(() -> new RuntimeException("ไม่พบสินค้านี้ในร้าน"));

        resellerProductRepository.delete(rp);
        return "ลบสินค้าออกจากร้านสำเร็จ";
    }

    // ── ดึง catalog ทั้งหมด ──────────────────────────────────────────────────
    public List<CatalogProductRes> getCatalog() {
        return productRepository.getCatalogProducts();
    }

    // ── ดึงสินค้าในร้านของ reseller ─────────────────────────────────────────
    public List<ResellerProductRes> getResellerProducts(Integer resellerId) {
        return productRepository.getResellerProducts(Long.valueOf(resellerId));
    }
}