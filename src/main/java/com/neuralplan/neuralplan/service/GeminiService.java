package com.neuralplan.neuralplan.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class GeminiService {

    @Value("${gemini.api.key}")
    private String apiKey;

    @Value("${gemini.api.url}")
    private String apiUrl;

    private final WebClient webClient;

    public GeminiService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }

    public String generateStudyPlan(String studentName, String subjects, String examDate, String weakTopics) {

        String prompt = String.format("""
                Create a detailed day-by-day study plan for a student with the following details:
                - Name: %s
                - Subjects: %s
                - Exam Date: %s
                - Weak Topics: %s
                
                Please provide:
                1. A day-by-day schedule from today until the exam
                2. Extra focus on weak topics
                3. Daily study hours recommendation
                4. Quick revision tips
                Keep it practical and motivating!
                """, studentName, subjects, examDate, weakTopics);

        String requestBody = String.format("""
                {
                    "contents": [{
                        "parts": [{
                            "text": "%s"
                        }]
                    }]
                }
                """, prompt.replace("\"", "'").replace("\n", "\\n"));

        try {
            String response = webClient.post()
                    .uri(apiUrl + "?key=" + apiKey)
                    .header("Content-Type", "application/json")
                    .bodyValue(requestBody)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            return extractTextFromResponse(response);

        } catch (Exception e) {
            if (e.getMessage() != null && e.getMessage().contains("429")) {
                return "⚠️ Too many requests! Gemini API rate limit hit. Please wait a minute and try again.";
            } else if (e.getMessage() != null && e.getMessage().contains("503")) {
                return "⚠️ Gemini API is temporarily unavailable. Please try again shortly.";
            } else {
                return "⚠️ Could not generate plan right now. Please try again! Error: " + e.getMessage();
            }
        }
    }

    private String extractTextFromResponse(String response) {
        try {
            com.fasterxml.jackson.databind.ObjectMapper mapper =
                    new com.fasterxml.jackson.databind.ObjectMapper();
            com.fasterxml.jackson.databind.JsonNode root = mapper.readTree(response);
            return root
                    .path("candidates")
                    .get(0)
                    .path("content")
                    .path("parts")
                    .get(0)
                    .path("text")
                    .asText();
        } catch (Exception e) {
            return "⚠️ Could not read AI response. Please try again!";
        }
    }
}