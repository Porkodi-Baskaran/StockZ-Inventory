package com.example.InventoryMangement.ai.service;

import org.springframework.stereotype.Service;
import kong.unirest.*;

@Service
public class OllamaAiService {

    public String getAiInsight(String prompt) {

        try {
            HttpResponse<String> response = Unirest.post("http://localhost:11434/api/generate")
                    .header("Content-Type", "application/json")
                    .body("{\"model\": \"llama3\", \"prompt\": \"" + prompt + "\"}")
                    .asString();

            return response.getBody();

        } catch (Exception e) {
            return "AI service unavailable.";
        }
    }
}
