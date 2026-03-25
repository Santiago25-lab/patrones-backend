package com.patrones.backend.dto;

import jakarta.validation.constraints.NotBlank;

public class CVRequest {
    
    @NotBlank(message = "Title is required")
    private String title;
    
    private String professionalProfile;

    public CVRequest() {}

    public CVRequest(String title, String professionalProfile) {
        this.title = title;
        this.professionalProfile = professionalProfile;
    }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getProfessionalProfile() { return professionalProfile; }
    public void setProfessionalProfile(String professionalProfile) { this.professionalProfile = professionalProfile; }
}
