package com.example.InventoryMangement.Product.contoller;

import java.util.List;

import com.example.InventoryMangement.Product.Entity.Transaction;
import com.example.InventoryMangement.Product.Entity.TransactionsDTO;
import com.example.InventoryMangement.Product.myservice.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



import jakarta.validation.Valid;

@RestController
@RequestMapping("/transactions")
public class TransactionController
{
     @Autowired
	private TransactionService transactionservice;
	
     @GetMapping
     public ResponseEntity<List<Transaction>> getAllTransactions() 
     {
         return new ResponseEntity<>(transactionservice.getAllTransaction(), HttpStatus.OK);
     }
    @GetMapping("{id}")
    public ResponseEntity<Transaction> getTransactionsById(@PathVariable Integer id ) {
        return new ResponseEntity<>(transactionservice.getTransactionById(id), HttpStatus.OK);
    }

     
     @PostMapping
     public ResponseEntity<String> recordTransaction(@Valid @RequestBody TransactionsDTO dto) {
    	 transactionservice.recordtransaction(dto);
         return ResponseEntity.ok("Transaction recorded successfully");
     }
    
}
