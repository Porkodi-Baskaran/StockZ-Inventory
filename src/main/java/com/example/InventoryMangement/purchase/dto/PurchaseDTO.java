package com.example.InventoryMangement.purchase.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
public class PurchaseDTO {

    private Integer productId;
    private String supplierName;
    private Integer quantity;
    private Integer pricePerUnit;
    private Integer amountPaid;
    private Integer balanceAmount;
    private String invoiceNumber;
    private String paymentType;
	public Integer getProductId() {
		return productId;
	}
	public void setProductId(Integer productId) {
		this.productId = productId;
	}
	public String getSupplierName() {
		return supplierName;
	}
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
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
	public Integer getAmountPaid() {
		return amountPaid;
	}
	public void setAmountPaid(Integer amountPaid) {
		this.amountPaid = amountPaid;
	}
	public Integer getBalanceAmount() {
		return balanceAmount;
	}
	public void setBalanceAmount(Integer balanceAmount) {
		this.balanceAmount = balanceAmount;
	}
	public String getInvoiceNumber() {
		return invoiceNumber;
	}
	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}
	public String getPaymentType() {
		return paymentType;
	}
	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

    // getters and setters
    
}
