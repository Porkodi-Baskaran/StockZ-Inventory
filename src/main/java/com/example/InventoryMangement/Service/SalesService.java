package com.example.InventoryMangement.Service;

import com.example.InventoryMangement.Entity.Customer1;
import com.example.InventoryMangement.Entity.Invoice1;

import com.example.InventoryMangement.Entity.InvoiceItem1;
import com.example.InventoryMangement.PDFGenrator.PdfGeneratorService1;
import com.example.InventoryMangement.Repo.CustomerRepo1;
import com.example.InventoryMangement.Repo.InvoiceRepo1;
import com.example.InventoryMangement.Repo.ProductRepo1;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class SalesService {
@Autowired
    private final InvoiceRepo1 invoiceRepo;
    private final CustomerRepo1 customerRepo;
    private final PdfGeneratorService1 pdfGeneratorService;



    @Value("${inventory.low-stock.threshold:5}")
    private int lowStockThreshold;

    @Value("${admin.whatsapp.number:}")
    private String adminNumber;

    public SalesService(InvoiceRepo1 invoiceRepo, CustomerRepo1 customerRepo, ProductRepo1 productRepo, PdfGeneratorService1 pdfGeneratorService) {
        this.invoiceRepo = invoiceRepo;
        this.customerRepo = customerRepo;
        this.pdfGeneratorService = pdfGeneratorService;

    }


    public Invoice1 createSale(Invoice1 invoice)
    {
        if (invoice.getCustomer() != null && invoice.getCustomer().getId() != null) {
            var existing = customerRepo.findById(Math.toIntExact(invoice.getCustomer().getId()))
                    .orElseThrow(() -> new RuntimeException("Customer not found"));
            invoice.setCustomer(existing);
        }


        invoice.setInvoiceNo("INV-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        invoice.setDate(new Date());
        invoice.setStatus("UNPAID");


        double grandTotal = 0;

        for (InvoiceItem1 item : invoice.getItems()) {
            double lineTotal = (item.getQty() * item.getPrice()) * (1 - item.getDiscount() / 100);
            item.setTotal(lineTotal);
            item.setInvoice(invoice);
            grandTotal += lineTotal;

            // 🧠 Check stock level (optional if you track inventory)
          /*  if (item.getProduct() != null && item.getProduct().getStock() <= lowStockThreshold) {
                String alertMsg = String.format(
                        "⚠️ Low Stock Alert!\nProduct: %s\nRemaining Stock: %d\nPlease reorder soon.",
                        item.getProduct().getName(),
                        item.getProduct().getStock()
                );
                whatsappService.sendTextMessage(adminNumber, alertMsg);
            }*/
        }

            invoice.setTotal(grandTotal);
            invoice.setReceived(0);

            var savedInvoice = invoiceRepo.save(invoice);


            String pdfPath = pdfGeneratorService.generateInvoicePdf(savedInvoice);
            File pdfFile = new File(pdfPath);



            return savedInvoice;
        }


    private void updateStatus(Invoice1 inv) {
        if (inv.getReceived() >= inv.getTotal()) inv.setStatus("PAID");
        else if (inv.getReceived() > 0) inv.setStatus("PARTIAL");
        else inv.setStatus("UNPAID");
    }


    public List<Invoice1> getAllSales() {
        return invoiceRepo.findAll();
    }

    public Invoice1 getInvoice(Long id) {
        return invoiceRepo.findById(Math.toIntExact(id)).orElseThrow();
    }

    public Customer1 saveCustomer(Customer1 c) {
        return customerRepo.save(c);
    }

    public List<Customer1> getAllCustomers() {
        return customerRepo.findAll();
    }
}

