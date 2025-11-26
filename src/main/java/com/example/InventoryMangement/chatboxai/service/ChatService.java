package com.example.InventoryMangement.chatboxai.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.InventoryMangement.Product.myservice.ProductService;
import com.example.InventoryMangement.Sales.Service.SalesService;
import com.example.InventoryMangement.chatboxai.entity.ChatMessage;
import com.example.InventoryMangement.chatboxai.repository.ChatRepository;
import com.example.InventoryMangement.purchase.service.PurchaseService;

@Service
public class ChatService {

    private final OllamaService ollamaService;
    private final PurchaseService purchaseService;
    private final SalesService salesService;
    private final ProductService productService;
    private final ChatRepository chatRepository;

    @Autowired
    public ChatService(
            OllamaService ollamaService,
            PurchaseService purchaseService,
            SalesService salesService,
            ProductService productService,
            ChatRepository chatRepository
    ) {
        this.ollamaService = ollamaService;
        this.purchaseService = purchaseService;
        this.salesService = salesService;
        this.productService = productService;
        this.chatRepository = chatRepository;
    }

    public String processMessage(String userMessage) {

        chatRepository.save(new ChatMessage(userMessage)); // save user message

        String msg = userMessage.toLowerCase();

     
        // ====================== SALES ========================

        if (msg.contains("sales") && msg.contains("summary")) {
            return salesService.getAllSales().toString();
        }

        if (msg.contains("all sales") || msg.contains("sales list")) {
            return salesService.getAllSales().toString();
        }

        // ====================== PRODUCT =======================

        if (msg.contains("stock") || msg.contains("products") || msg.contains("product list")) {
            return productService.viewAllProducts().toString();
        }

        // ====================== AI HANDLES OTHER QUESTIONS =======================

        String reply = ollamaService.getReply(userMessage);

        chatRepository.save(new ChatMessage("AI: " + reply));

        return reply;
    }

	public Object getHistory() {
		// TODO Auto-generated method stub
		return null;
	}

}
