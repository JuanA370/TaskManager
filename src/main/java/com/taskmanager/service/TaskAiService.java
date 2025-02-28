package com.taskmanager.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.http.HttpHeaders;
@Service
public class TaskAiService {
	private final WebClient webClient;

    @Value("${openai.api.key}")
    private String openAiApiKey;

    @Value("${openai.model}")
    private String model;

    public TaskAiService() {
        this.webClient = WebClient.builder()
                .baseUrl("https://api.openai.com/v1/chat/completions")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + openAiApiKey)
                .build();
    }

    public String generateTaskSuggestion(String description) {
        String prompt = "Sugiere una tarea basada en la siguiente descripción: " + description;

        Map<String, Object> requestBody = Map.of(
                "model", model,
                "messages", List.of(Map.of("role", "user", "content", prompt)),
                "max_tokens", 100
        );

        return webClient.post()
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(Map.class)
                .map((response -> {
                    List<Map<String, Object>> choices = (List<Map<String, Object>>) response.get("choices");
                    if (choices != null && !choices.isEmpty()) {
                        Map<String, Object> firstChoice = choices.get(0);
                        Map<String, Object> message = (Map<String, Object>) firstChoice.get("message");
                        return (String) message.get("content");
                    }
                    return "No se pudo generar la sugerencia.";
                })
)
                .block();
    }
}
