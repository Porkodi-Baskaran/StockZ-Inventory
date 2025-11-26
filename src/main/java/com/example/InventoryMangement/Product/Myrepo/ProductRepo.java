package com.example.InventoryMangement.Product.Myrepo;

import java.util.Optional;

import com.example.InventoryMangement.Product.Entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ProductRepo extends JpaRepository<Product, Integer>
{	
}
