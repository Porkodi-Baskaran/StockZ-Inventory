package com.example.InventoryMangement.purchase.service;

import java.time.LocalDate;
import java.util.List;

import com.example.InventoryMangement.Product.Entity.Product;
import com.example.InventoryMangement.Product.Entity.Transaction;
import com.example.InventoryMangement.Product.Entity.TransactionType;
import com.example.InventoryMangement.Product.Myrepo.ProductRepo;
import com.example.InventoryMangement.Product.Myrepo.TransactionRepo;
import com.example.InventoryMangement.purchase.dto.PurchaseItemDTO;
import com.example.InventoryMangement.purchase.dto.PurchaseRequestDTO;
import com.example.InventoryMangement.purchase.entity.Purchase;
import com.example.InventoryMangement.purchase.entity.PurchaseMaster;
import com.example.InventoryMangement.purchase.entity.Supplier;
import com.example.InventoryMangement.purchase.repository.PurchaseMasterRepo;
import com.example.InventoryMangement.purchase.repository.PurchaseRepo;
import com.example.InventoryMangement.purchase.repository.SupplierRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class PurchaseService {
	@Autowired
	private PurchaseMasterRepo purchaseMasterRepo;
	
    @Autowired
    private PurchaseRepo purchaseRepo;

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private TransactionRepo transactionRepo;
    
    @Autowired
    private SupplierRepo supplierRepo;
    
    
    Supplier supplier;

    @Transactional
    public String addPurchase(PurchaseRequestDTO dto) {

    	// 1. If supplierId is sent — use it
    	if (dto.getSupplierId() != null) {
    	    supplier = supplierRepo.findById(dto.getSupplierId())
    	            .orElse(null);
    	} else {
    	    supplier = null;
    	}

    	// 2. If supplier not found by ID, search by phone number
    	if (supplier == null && dto.getSupplierPhone() != null) {
    	    supplier = supplierRepo.findByPhoneNumber(dto.getSupplierPhone()).orElse(null);
    	}

    	// 3. If still not found → create new supplier
    	if (supplier == null) {
    	    supplier = new Supplier();
    	    supplier.setName(dto.getSupplierName());
    	    supplier.setAddress(dto.getSupplierAddress());
    	    supplier.setPhoneNumber(dto.getSupplierPhone());
    	    supplier = supplierRepo.save(supplier);
    	}

        PurchaseMaster master = new PurchaseMaster();
        master.setSupplier(supplier);
        master.setInvoiceNumber(dto.getInvoiceNumber());
        master.setPurchaseDate(LocalDate.now());
        master.setPaymentType(dto.getPaymentType());

        int grandTotal = 0;

        master = purchaseMasterRepo.save(master);

        // inside addPurchase loop (after saving master)
        for (PurchaseItemDTO itemDto : dto.getItems()) {

            Product product = productRepo.findById(itemDto.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            int base = itemDto.getQuantity() * itemDto.getPricePerUnit();
            int gst = (base * itemDto.getGstPercentage()) / 100;
            int total = base + gst;

            // Update currentStock
            if (product.getCurrentStock() == null) product.setCurrentStock(0);
            product.setCurrentStock(product.getCurrentStock() + itemDto.getQuantity());
            productRepo.save(product);

            // Save purchase item (existing code)
            Purchase item = new Purchase();
            item.setPurchaseMaster(master);
            item.setProduct(product);
            item.setQuantity(itemDto.getQuantity());
            item.setPricePerUnit(itemDto.getPricePerUnit());
            item.setGstPercentage(itemDto.getGstPercentage());
            item.setGstAmount(gst);
            item.setTotalAmount(total);
            purchaseRepo.save(item);

            // Create transaction per item
            Transaction txn = new Transaction();
            txn.setType(TransactionType.PURCHASE);
            txn.setProduct(product);
            txn.setQuantity(itemDto.getQuantity());
            txn.setPricePerUnit(itemDto.getPricePerUnit());
            txn.setTotalAmount(total);
            txn.setDate(master.getPurchaseDate());
            txn.setName(master.getSupplier() != null ? master.getSupplier().getName() : dto.getSupplierName());
            txn.setInvoiceNumber(master.getInvoiceNumber());
            txn.setReferenceId(master.getId().longValue());
            txn.setCreatedAt(java.time.LocalDateTime.now());
            txn.setStatus("COMPLETED");
            transactionRepo.save(txn);

            grandTotal += total;
        }
         return "Purchase Completed Successfully";
    }
    
    public PurchaseMaster getPurchaseById(Integer id) {
        return purchaseMasterRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Purchase not found"));
    }
    
    @Transactional
    public String deletePurchase(Integer id) {

        PurchaseMaster master = purchaseMasterRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Purchase not found"));

        List<Purchase> items = purchaseRepo.findByPurchaseMasterId(id);

        // Rollback stock
        // Rollback stock
        for (Purchase item : items) {
            Product product = item.getProduct();

            if (product.getCurrentStock() < item.getQuantity()) {
                throw new RuntimeException("Stock cannot go negative while deleting purchase");
            }

            product.setCurrentStock(product.getCurrentStock() - item.getQuantity());
            productRepo.save(product);
        }

        // Delete child items
        purchaseRepo.deleteAll(items);

        //Delete related transactions
        transactionRepo.deleteByInvoiceNumber(master.getInvoiceNumber());

        //Delete master
        purchaseMasterRepo.delete(master);

        return "Purchase deleted successfully";
    }


   }
