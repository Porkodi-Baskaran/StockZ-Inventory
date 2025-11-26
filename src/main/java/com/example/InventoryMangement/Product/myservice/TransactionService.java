package com.example.InventoryMangement.Product.myservice;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.example.InventoryMangement.Product.Entity.*;
import com.example.InventoryMangement.Product.Myrepo.ProductRepo;
import com.example.InventoryMangement.Product.Myrepo.TransactionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepo transRepo;

    @Autowired
    private ProductRepo productRepo;

    public void recordtransaction(TransactionsDTO dto) {
        Product product = productRepo.findById(dto.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        Transaction transaction = new Transaction();
        transaction.setProduct(product);
        transaction.setType(dto.getType());
        transaction.setName(dto.getName());
        transaction.setDate(LocalDate.now());
        transaction.setQuantity(dto.getQuantity());
        transaction.setPricePerUnit(dto.getPricePerUnit());
        transaction.setTotalAmount(dto.getQuantity() * dto.getPricePerUnit());
        transaction.setCreatedAt(LocalDateTime.now());

        // set status only for purchase/sale if provided
        if (dto.getType() == TransactionType.PURCHASE || dto.getType() == TransactionType.SALE) {
            transaction.setStatus(dto.getStatus());
        } else {
            transaction.setStatus("COMPLETED");
        }

        // Save transaction
        transRepo.save(transaction);

        // If transaction is SALE or REDUCE_ADJUSTMENT, reduce stock
        if (dto.getType() == TransactionType.SALE || dto.getType() == TransactionType.REDUCE_ADJUSTMENT) {
            if (product.getCurrentStock() < dto.getQuantity()) {
                throw new RuntimeException("Insufficient stock for product: " + product.getId());
            }
            product.setCurrentStock(product.getCurrentStock() - dto.getQuantity());
        } else if (dto.getType() == TransactionType.PURCHASE || dto.getType() == TransactionType.ADD_ADJUSTMENT
                || dto.getType() == TransactionType.OPENING_STOCK) {
            product.setCurrentStock(product.getCurrentStock() + dto.getQuantity());
        }
        productRepo.save(product);
    }

    public List<Transaction> getAllTransaction() {
        return transRepo.findAll();
    }

    public Transaction getTransactionById(Integer id) {
        return transRepo.findById(id).orElseThrow(() -> new RuntimeException("Transaction not found"));
    }
}
