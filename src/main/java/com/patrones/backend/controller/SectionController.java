package com.patrones.backend.controller;

import com.patrones.backend.dto.SectionRequest;
import com.patrones.backend.model.Section;
import com.patrones.backend.service.SectionService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cvs/{cvId}/sections")
public class SectionController {

    private final SectionService sectionService;

    public SectionController(SectionService sectionService) {
        this.sectionService = sectionService;
    }

    @PostMapping
    public ResponseEntity<Section> createSection(
            @PathVariable Long cvId, 
            @AuthenticationPrincipal UserDetails userDetails, 
            @Valid @RequestBody SectionRequest request) {
        return ResponseEntity.ok(sectionService.createSection(cvId, userDetails.getUsername(), request));
    }

    @GetMapping
    public ResponseEntity<List<Section>> getCVSections(
            @PathVariable Long cvId, 
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(sectionService.getCVSections(cvId, userDetails.getUsername()));
    }

    @PutMapping("/{sectionId}")
    public ResponseEntity<Section> updateSection(
            @PathVariable Long cvId, 
            @PathVariable Long sectionId, 
            @AuthenticationPrincipal UserDetails userDetails, 
            @Valid @RequestBody SectionRequest request) {
        return ResponseEntity.ok(sectionService.updateSection(sectionId, userDetails.getUsername(), request));
    }

    @DeleteMapping("/{sectionId}")
    public ResponseEntity<Void> deleteSection(
            @PathVariable Long cvId, 
            @PathVariable Long sectionId, 
            @AuthenticationPrincipal UserDetails userDetails) {
        sectionService.deleteSection(sectionId, userDetails.getUsername());
        return ResponseEntity.noContent().build();
    }
}
