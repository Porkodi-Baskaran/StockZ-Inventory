package com.example.InventoryMangement.purchase.controller;

import com.example.InventoryMangement.purchase.dto.PurchaseRequestDTO;
import com.example.InventoryMangement.purchase.entity.PurchaseMaster;
import com.example.InventoryMangement.purchase.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/purchase")
@CrossOrigin("*")
public class PurchaseController {

    @Autowired
    private PurchaseService purchaseService;

    @PostMapping("/add")
    public String addPurchase(@RequestBody PurchaseRequestDTO dto) {
        return purchaseService.addPurchase(dto);
    }
    
    @GetMapping("/{id}")
    public PurchaseMaster getById(@PathVariable Integer id) {
        return purchaseService.getPurchaseById(id);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Integer id) {
        return purchaseService.deletePurchase(id);
    }

}

