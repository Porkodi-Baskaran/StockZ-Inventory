package com.example.InventoryMangement.chatboxai.repository;

import org.springframework.stereotype.Repository;

import com.example.InventoryMangement.chatboxai.entity.ChatMessage;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ChatRepository {

    private final List<ChatMessage> messages = new ArrayList<>();

    public void save(ChatMessage msg) {
        messages.add(msg);
    }

    public List<ChatMessage> getAll() {
        return messages;
    }
}
