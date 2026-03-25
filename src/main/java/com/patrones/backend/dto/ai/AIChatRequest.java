package com.patrones.backend.dto.ai;

import jakarta.validation.constraints.NotBlank;

public class AIChatRequest {

    @NotBlank(message = "Prompt is required")
    private String prompt;
    
    private String context; // Useful for passing the current CV state

    public AIChatRequest() {}

    public AIChatRequest(String prompt, String context) {
        this.prompt = prompt;
        this.context = context;
    }

    public String getPrompt() { return prompt; }
    public void setPrompt(String prompt) { this.prompt = prompt; }

    public String getContext() { return context; }
    public void setContext(String context) { this.context = context; }
}
