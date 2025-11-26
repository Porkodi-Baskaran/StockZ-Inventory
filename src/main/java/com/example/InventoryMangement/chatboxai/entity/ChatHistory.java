package com.example.InventoryMangement.chatboxai.entity;


import jakarta.persistence.*;

@Entity
@Table(name = "chat_history")
public class ChatHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userMessage;
    private String botReply;

    public ChatHistory() {}

    public ChatHistory(String userMessage, String botReply) {
        this.userMessage = userMessage;
        this.botReply = botReply;
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserMessage() {
		return userMessage;
	}

	public void setUserMessage(String userMessage) {
		this.userMessage = userMessage;
	}

	public String getBotReply() {
		return botReply;
	}

	public void setBotReply(String botReply) {
		this.botReply = botReply;
	}

    // getters and setters
}
