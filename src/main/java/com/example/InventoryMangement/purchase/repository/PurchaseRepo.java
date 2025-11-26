package com.example.InventoryMangement.purchase.repository;

import java.util.List;

import com.example.InventoryMangement.purchase.entity.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;



public interface PurchaseRepo extends JpaRepository<Purchase, Integer> {
	List<Purchase> findByPurchaseMasterId(Integer purchaseMasterId);
}

