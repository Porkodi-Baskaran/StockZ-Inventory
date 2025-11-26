package com.example.InventoryMangement.Repo;

import com.example.InventoryMangement.Entity.Productentity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Productentity, Long> {
    Optional<Productentity> findByName(String name);
}