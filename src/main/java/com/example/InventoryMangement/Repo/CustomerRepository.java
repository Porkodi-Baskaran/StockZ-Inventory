package com.example.InventoryMangement.Repo;

import com.example.InventoryMangement.Entity.Customerentity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customerentity,Integer>
{

}
