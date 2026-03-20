package com.example.demo.bootscamp.controller;

import com.example.demo.bootscamp.dto.Res.ResellerOrderRes;
import com.example.demo.bootscamp.service.ResellerOrderService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reseller/orders")
public class ResellerOrderController {

    private final ResellerOrderService resellerOrderService;

    public ResellerOrderController(ResellerOrderService resellerOrderService) {
        this.resellerOrderService = resellerOrderService;
    }

    @GetMapping("/{resellerId}")
    public List<ResellerOrderRes> getOrders(@PathVariable Long resellerId){

        return resellerOrderService.getOrders(resellerId);

    }
}