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

        String response = webClient.post()
                .uri(apiUrl + "?key=" + apiKey)
                .header("Content-Type", "application/json")
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        return extractTextFromResponse(response);
    }

    private String extractTextFromResponse(String response) {
        try {
            int textIndex = response.indexOf("\"text\":");
            if (textIndex != -1) {
                int start = response.indexOf("\"", textIndex + 7) + 1;
                int end = response.lastIndexOf("\"");
                return response.substring(start, end)
                        .replace("\\n", "\n")
                        .replace("\\'", "'");
            }
        } catch (Exception e) {
            return "Could not generate plan. Please try again!";
        }
        return "No response from AI. Please try again!";
    }
}