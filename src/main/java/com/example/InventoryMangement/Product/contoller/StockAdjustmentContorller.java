package com.example.InventoryMangement.Product.contoller;

import com.example.InventoryMangement.Product.Entity.StockAdjustment;
import com.example.InventoryMangement.Product.myservice.StockAdjustmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/stock-adjustment")
public class StockAdjustmentContorller {

	@Autowired
	private StockAdjustmentService adjustmentService;
	
	@PostMapping("/{productId}")
	public String adjustStock(@PathVariable Integer productId, @RequestBody StockAdjustment adjustment)
	{
		adjustmentService.adjustStock(productId, adjustment);
		return "Stock updated successfully";
	}
}
