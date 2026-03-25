package com.patrones.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class SectionRequest {

    @NotBlank(message = "Section title is required")
    private String sectionTitle;

    private String content;
    
    @NotNull(message = "Position is required")
    private Integer position;

    public SectionRequest() {}

    public SectionRequest(String sectionTitle, String content, Integer position) {
        this.sectionTitle = sectionTitle;
        this.content = content;
        this.position = position;
    }

    public String getSectionTitle() { return sectionTitle; }
    public void setSectionTitle(String sectionTitle) { this.sectionTitle = sectionTitle; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public Integer getPosition() { return position; }
    public void setPosition(Integer position) { this.position = position; }
}
