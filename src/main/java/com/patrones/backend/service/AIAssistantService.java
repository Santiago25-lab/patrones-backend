package com.patrones.backend.service;

import com.patrones.backend.dto.ai.AIChatRequest;
import com.patrones.backend.dto.ai.AIChatResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.List;

@Service
public class AIAssistantService {

    @Value("${ai.provider:mock}")
    private String aiProvider;

    @Value("${ai.api.key:dummy}")
    private String apiKey;

    private final WebClient webClient;

    public AIAssistantService(WebClient.Builder webClientBuilder) {
        // You can set base URLs here if needed
        this.webClient = webClientBuilder.build();
    }

    public AIChatResponse generateText(AIChatRequest request) {
        if ("mock".equalsIgnoreCase(aiProvider) || "dummy".equals(apiKey)) {
            return generateMockResponse(request);
        }

        // Simplistic example for an OpenAI call via WebClient
        if ("openai".equalsIgnoreCase(aiProvider)) {
            return callOpenAI(request);
        }

        return generateMockResponse(request);
    }
    
    public AIChatResponse optimizeProfessionalProfile(String inputProfile) {
        String prompt = "Por favor, optimiza el siguiente perfil profesional para un CV formal y de alto impacto: " + inputProfile;
        return generateText(new AIChatRequest(prompt, "Hacerlo en tono profesional y orientado a resultados."));
    }

    private AIChatResponse generateMockResponse(AIChatRequest request) {
        // Intelligent mock response logic based on input keywords
        String promptText = request.getPrompt().toLowerCase();
        
        if (promptText.contains("experiencia") || promptText.contains("ventas")) {
            return new AIChatResponse("Experiencia comprobada en la gestión de ventas y atención al cliente, logrando superar metas comerciales y fidelizando cuentas clave a través de estrategias de negociación efectivas.");
        } else if (promptText.contains("perfil") || promptText.contains("soy")) {
            return new AIChatResponse("Profesional altamente proactivo, con gran capacidad de adaptación y aprendizaje rápido. Enfoque orientado a resultados y resolución de problemas, aportando valor en entornos dinámicos y de alta exigencia.");
        }
        
        return new AIChatResponse("Aquí tienes una versión mejorada y profesional: " + request.getPrompt());
    }

    private AIChatResponse callOpenAI(AIChatRequest request) {
        try {
            // Very simple mapping for typical OpenAI API (Assuming gpt-3.5-turbo or similar)
            Map<String, Object> bodyData = Map.of(
                "model", "gpt-3.5-turbo",
                "messages", List.of(
                        Map.of("role", "system", "content", "Eres un asistente experto en recursos humanos especializado en redacción de hojas de vida."),
                        Map.of("role", "user", "content", request.getContext() != null ? request.getContext() + "\n" + request.getPrompt() : request.getPrompt())
                ),
                "temperature", 0.7
            );

            Map<String, Object> responseMap = webClient.post()
                    .uri("https://api.openai.com/v1/chat/completions")
                    .header("Authorization", "Bearer " + apiKey)
                    .header("Content-Type", "application/json")
                    .bodyValue(bodyData)
                    .retrieve()
                    .bodyToMono(Map.class)
                    .block();

            // Parse response safely
            if (responseMap != null && responseMap.containsKey("choices")) {
                List<Map<String, Object>> choices = (List<Map<String, Object>>) responseMap.get("choices");
                if (!choices.isEmpty()) {
                    Map<String, Object> message = (Map<String, Object>) choices.get(0).get("message");
                    return new AIChatResponse((String) message.get("content"));
                }
            }
            return new AIChatResponse("No se pudo obtener una respuesta de la API de IA.");
        } catch (Exception e) {
            e.printStackTrace();
            return new AIChatResponse("Error al contactar con la IA: " + e.getMessage());
        }
    }
}
