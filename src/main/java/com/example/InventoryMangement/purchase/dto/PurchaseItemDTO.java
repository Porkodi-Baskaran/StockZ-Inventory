package com.example.InventoryMangement.purchase.dto;

public class PurchaseItemDTO {

    private Integer productId;
    private Integer quantity;
    private Integer pricePerUnit;
    private Integer gstPercentage;
	public Integer getProductId() {
		return productId;
	}
	public void setProductId(Integer productId) {
		this.productId = productId;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public Integer getPricePerUnit() {
		return pricePerUnit;
	}
	public void setPricePerUnit(Integer pricePerUnit) {
		this.pricePerUnit = pricePerUnit;
	}
	public Integer getGstPercentage() {
		return gstPercentage;
	}
	public void setGstPercentage(Integer gstPercentage) {
		this.gstPercentage = gstPercentage;
	}
    
    
}
