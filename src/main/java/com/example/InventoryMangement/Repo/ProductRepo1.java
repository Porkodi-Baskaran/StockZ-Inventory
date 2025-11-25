package com.example.InventoryMangement.Repo;

import com.example.InventoryMangement.Entity.Product1;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepo1 extends JpaRepository<Product1, Long> {
    Optional<Product1> findByName(String name);
}