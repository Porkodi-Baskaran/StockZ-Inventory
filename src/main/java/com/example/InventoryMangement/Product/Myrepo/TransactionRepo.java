package com.example.InventoryMangement.Product.Myrepo;

import com.example.InventoryMangement.Product.Entity.Transaction;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepo extends JpaRepository<Transaction, Long> {
    void deleteByInvoiceNumber(String invoiceNumber);
    
 // Today sales
    @Query("""
    SELECT t.product.name, SUM(t.quantity)
    FROM Transaction t
    WHERE t.type='SALE' AND DATE(t.date) = CURRENT_DATE
    GROUP BY t.product.name
    """)
    List<Object[]> todaySales();

    // This month sales
    @Query("""
    SELECT t.product.name, SUM(t.quantity)
    FROM Transaction t
    WHERE t.type='SALE' 
    AND MONTH(t.date) = MONTH(CURRENT_DATE)
    AND YEAR(t.date) = YEAR(CURRENT_DATE)
    GROUP BY t.product.name
    """)
    List<Object[]> monthlySales();

    // This year sales
    @Query("""
    SELECT t.product.name, SUM(t.quantity)
    FROM Transaction t
    WHERE t.type='SALE' 
    AND YEAR(t.date) = YEAR(CURRENT_DATE)
    GROUP BY t.product.name
    """)
    List<Object[]> yearlySales();

    // Revenue queries

    @Query("""
    SELECT SUM(t.amountPaid)
    FROM Transaction t
    WHERE t.type='SALE' 
    AND DATE(t.date) = CURRENT_DATE
    """)
    Double todayRevenue();

    @Query("""
    SELECT SUM(t.amountPaid)
    FROM Transaction t
    WHERE t.type='SALE' 
    AND MONTH(t.date)=MONTH(CURRENT_DATE) 
    AND YEAR(t.date)=YEAR(CURRENT_DATE)
    """)
    Double monthlyRevenue();

    @Query("""
    SELECT SUM(t.amountPaid)
    FROM Transaction t
    WHERE t.type='SALE' 
    AND YEAR(t.date)=YEAR(CURRENT_DATE)
    """)
    Double yearlyRevenue();

    // Active customer count
    @Query("""
    SELECT COUNT(DISTINCT t.name)
    FROM Transaction t
    WHERE t.type='SALE'
    """)
    Long activeCustomers();

    // Active customer details
    @Query("""
    SELECT t.name, SUM(t.amountPaid)
    FROM Transaction t
    WHERE t.type='SALE'
    GROUP BY t.name
    """)
    List<Object[]> activeCustomerDetails();

    // Top selling products
    @Query("""
    SELECT t.product.name, SUM(t.quantity)
    FROM Transaction t
    WHERE t.type='SALE'
    AND MONTH(t.date)=MONTH(CURRENT_DATE)
    GROUP BY t.product.name
    ORDER BY SUM(t.quantity) DESC
    """)
    List<Object[]> topSellingProducts();

}
