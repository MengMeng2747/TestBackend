package com.example.demo.bootscamp.controller;

import com.example.demo.bootscamp.dto.Req.AddProductToShopReq;
import com.example.demo.bootscamp.dto.Res.CatalogProductRes;
import com.example.demo.bootscamp.dto.Res.ResellerProductRes;
import com.example.demo.bootscamp.service.ResellerCatalogService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reseller/catalog")
public class ResellerCatalogController {

    private final ResellerCatalogService resellerCatalogService;

    public ResellerCatalogController(ResellerCatalogService resellerCatalogService) {
        this.resellerCatalogService = resellerCatalogService;
    }

    // catalog สินค้ากลาง
    @GetMapping
    public List<CatalogProductRes> getCatalog() {
        return resellerCatalogService.getCatalog();
    }

    // สินค้าในร้านของ reseller
    @GetMapping("/my-products/{resellerId}")
    public List<ResellerProductRes> getMyProducts(@PathVariable Integer resellerId) {
        return resellerCatalogService.getResellerProducts(resellerId);
    }

    // เพิ่ม / แก้ราคาสินค้าในร้าน
    @PostMapping("/add")
    public String addProduct(@RequestBody AddProductToShopReq req) {
        return resellerCatalogService.addProduct(req);
    }

    // ลบสินค้าออกจากร้าน
    @DeleteMapping("/remove")
    public String removeProduct(@RequestParam Long resellerId,
                                @RequestParam Long productId) {
        return resellerCatalogService.removeProduct(resellerId, productId);
    }
}