package com.ouhamza.ecommerce.controller;

import com.ouhamza.ecommerce.dto.Purchase;
import com.ouhamza.ecommerce.dto.PurchaseResponse;
import com.ouhamza.ecommerce.entity.Grocery;
import com.ouhamza.ecommerce.service.CheckoutService;

import java.util.Optional;

import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://grocery-delivery.s3-website.us-east-2.amazonaws.com/")
@RequestMapping("/api/checkout")
public class checkoutController {

    private CheckoutService checkoutService;


    public checkoutController(CheckoutService checkoutService) {
        this.checkoutService = checkoutService;
    }

    @PostMapping("/purchase")
    public PurchaseResponse purchase(@RequestBody Purchase purchase){
        PurchaseResponse response = this.checkoutService.placeOrder(purchase);
        return response;
    }
    
   
}
