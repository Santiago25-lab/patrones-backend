package com.patrones.backend.dto;

import jakarta.validation.constraints.NotBlank;

public class TemplateRequest {

    @NotBlank(message = "Template name is required")
    private String name;

    private String description;
    private String config;

    public TemplateRequest() {}

    public TemplateRequest(String name, String description, String config) {
        this.name = name;
        this.description = description;
        this.config = config;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getConfig() { return config; }
    public void setConfig(String config) { this.config = config; }
}
