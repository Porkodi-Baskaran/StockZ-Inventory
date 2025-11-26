package com.example.InventoryMangement.chatboxai.entity;


public class ChatMessage {

    private String role = "user";   // fixed role
    private String content;

    public ChatMessage() {
    }

    public ChatMessage(String content) {
        this.role = "user";  // always user
        this.content = content;
    }

    public String getRole() {
        return role;
    }

    // Optional: prevent changing the role
    public void setRole(String role) {
        this.role = "user";
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

