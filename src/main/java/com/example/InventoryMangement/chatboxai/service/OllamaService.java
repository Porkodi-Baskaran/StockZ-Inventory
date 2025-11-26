package com.example.InventoryMangement.chatboxai.service;


import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class OllamaService {

    private final String OLLAMA_URL = "http://localhost:11434/api/generate";

    public String getReply(String userMessage) {

        RestTemplate restTemplate = new RestTemplate();

        Map<String, Object> body = new HashMap<>();
        body.put("model", "gemma3:4b"); // use the same model you downloaded
        body.put("prompt", userMessage);
        body.put("stream", false);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

        ResponseEntity<Map> response =
                restTemplate.exchange(OLLAMA_URL, HttpMethod.POST, request, Map.class);

        return response.getBody().get("response").toString();
    }
}
