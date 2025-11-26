package com.example.InventoryMangement.chatboxai.controller;

import org.springframework.web.bind.annotation.*;

import com.example.InventoryMangement.chatboxai.dto.ChatRequest;
import com.example.InventoryMangement.chatboxai.service.ChatService;

@RestController
@RequestMapping("/api/chat")
@CrossOrigin("*")
public class HelpAIController {

    private final ChatService chatService;

    // FIXED: Constructor name must match class name
    public HelpAIController(ChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping
    public String chat(@RequestBody ChatRequest request) {
        return chatService.processMessage(request.getMessage());
    }

    @GetMapping("/history")
    public Object getHistory() {
        return chatService.getHistory();
    }
}

