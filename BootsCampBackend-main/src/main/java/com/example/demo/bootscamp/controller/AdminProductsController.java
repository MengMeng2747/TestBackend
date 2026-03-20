package com.example.demo.bootscamp.controller;

import com.example.demo.bootscamp.entity.ProductEntity;
import com.example.demo.bootscamp.service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/products")
public class AdminProductsController {

    private final ProductService productService;

    public AdminProductsController(ProductService productService) {
        this.productService = productService;
    }

    // GET ALL PRODUCTS
    @GetMapping("all")
    public List<ProductEntity> getProducts() {
        return productService.getAllProducts();
    }

    // ADD PRODUCT
    @PostMapping("post")
    public ProductEntity addProduct(@RequestBody ProductEntity product) {
        return productService.addProduct(product);
    }

    // UPDATE PRODUCT
    @PutMapping("/put/{id}")
    public ProductEntity updateProduct(@PathVariable Long id,
                                       @RequestBody ProductEntity product) {
        return productService.updateProduct(Math.toIntExact(id), product);
    }

    // DELETE PRODUCT
    @DeleteMapping("/del/{id}")
    public String deleteProduct(@PathVariable Long id) {

        productService.deleteProduct(Math.toIntExact(id));

        return "ลบสินค้าเรียบร้อย";
    }

}