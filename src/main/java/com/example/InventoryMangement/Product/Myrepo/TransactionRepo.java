package com.example.InventoryMangement.Product.Myrepo;

import com.example.InventoryMangement.Product.Entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepo extends JpaRepository<Transaction, Long> {
    void deleteByInvoiceNumber(String invoiceNumber);
}
