package com.example.demo.bootscamp.service;

import com.example.demo.bootscamp.entity.ProductEntity;
import com.example.demo.bootscamp.repository.OrderItemsRepository;
import com.example.demo.bootscamp.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final OrderItemsRepository orderItemRepository;

    public ProductService(ProductRepository productRepository,
                          OrderItemsRepository orderItemRepository) {
        this.productRepository = productRepository;
        this.orderItemRepository = orderItemRepository;
    }

    // =============================
    // GET ALL PRODUCTS
    // =============================
    public List<ProductEntity> getAllProducts() {
        return productRepository.findAll();
    }

    // =============================
    // ADD PRODUCT
    // =============================
    public ProductEntity addProduct(ProductEntity product) {
        validatePrice(product);
        return productRepository.save(product);
    }

    // =============================
    // UPDATE PRODUCT
    // =============================
    public ProductEntity updateProduct(Integer id, ProductEntity req) {

        ProductEntity product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ไม่พบสินค้า"));

        validatePrice(req);

        product.setName(req.getName());
        product.setDescription(req.getDescription());
        product.setImageUrl(req.getImageUrl());
        product.setCostPrice(req.getCostPrice());
        product.setMinPrice(req.getMinPrice());
        product.setStock(req.getStock());

        return productRepository.save(product);
    }

    // =============================
    // DELETE PRODUCT
    // =============================
    @org.springframework.transaction.annotation.Transactional
    public void deleteProduct(Integer id) {

        ProductEntity product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ไม่พบสินค้า"));

        // BR-06: เช็คเฉพาะ order ที่ยังไม่เสร็จ (pending / shipped)
        int activeOrders = orderItemRepository
                .countActiveOrdersByProductId(product.getId());

        if (activeOrders > 0) {
            throw new RuntimeException("ไม่สามารถลบสินค้าได้ เนื่องจากมีออเดอร์ที่ยังไม่เสร็จสมบูรณ์");
        }

        // ลบ order_items ที่อ้างถึง product นี้ก่อน (completed orders เท่านั้น)
        orderItemRepository.deleteByProductId(product.getId());

        // BR-05: ไม่มีออเดอร์ค้าง → ลบได้
        productRepository.delete(product);
    }

    // =============================
    // VALIDATION PRICE
    // =============================
    private void validatePrice(ProductEntity product) {

        if (product.getCostPrice() == null || product.getMinPrice() == null) {
            throw new RuntimeException("กรุณากรอกราคาทุนและราคาขั้นต่ำ");
        }

        // BR-07: minPrice ต้อง >= costPrice
        if (product.getMinPrice().compareTo(product.getCostPrice()) < 0) {
            throw new RuntimeException("ราคาขั้นต่ำต้องมากกว่าหรือเท่าราคาทุน");
        }
    }
}