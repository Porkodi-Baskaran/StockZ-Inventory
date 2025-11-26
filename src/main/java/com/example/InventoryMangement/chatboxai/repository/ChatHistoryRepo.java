package com.example.InventoryMangement.chatboxai.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.InventoryMangement.chatboxai.entity.ChatHistory;

public interface ChatHistoryRepo extends JpaRepository<ChatHistory, Long> {
}



