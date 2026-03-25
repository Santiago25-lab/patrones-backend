package com.patrones.backend.controller;

import com.patrones.backend.dto.ai.AIChatRequest;
import com.patrones.backend.dto.ai.AIChatResponse;
import com.patrones.backend.service.AIAssistantService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ai")
public class AIAssistantController {

    private final AIAssistantService aiAssistantService;

    public AIAssistantController(AIAssistantService aiAssistantService) {
        this.aiAssistantService = aiAssistantService;
    }

    @PostMapping("/chat")
    public ResponseEntity<AIChatResponse> chat(@Valid @RequestBody AIChatRequest request) {
        return ResponseEntity.ok(aiAssistantService.generateText(request));
    }

    @PostMapping("/optimize/profile")
    public ResponseEntity<AIChatResponse> optimizeProfile(@RequestBody String rawProfile) {
        return ResponseEntity.ok(aiAssistantService.optimizeProfessionalProfile(rawProfile));
    }
}
