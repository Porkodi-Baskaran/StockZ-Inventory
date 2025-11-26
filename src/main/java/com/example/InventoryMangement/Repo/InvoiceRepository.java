package com.example.InventoryMangement.Repo;

import com.example.InventoryMangement.Entity.Invoiceentity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceRepository  extends JpaRepository<Invoiceentity,Integer> {
}
