package com.patrones.backend.controller;

import com.patrones.backend.dto.CVRequest;
import com.patrones.backend.model.CV;
import com.patrones.backend.service.CVService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cvs")
public class CVController {

    private final CVService cvService;

    public CVController(CVService cvService) {
        this.cvService = cvService;
    }

    @PostMapping
    public ResponseEntity<CV> createCV(@AuthenticationPrincipal UserDetails userDetails, @Valid @RequestBody CVRequest request) {
        return ResponseEntity.ok(cvService.createCV(userDetails.getUsername(), request));
    }

    @GetMapping
    public ResponseEntity<List<CV>> getUserCVs(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(cvService.getUserCVs(userDetails.getUsername()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CV> getCVById(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long id) {
        return ResponseEntity.ok(cvService.getCVById(id, userDetails.getUsername()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CV> updateCV(
            @AuthenticationPrincipal UserDetails userDetails, 
            @PathVariable Long id, 
            @Valid @RequestBody CVRequest request) {
        return ResponseEntity.ok(cvService.updateCV(id, userDetails.getUsername(), request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCV(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long id) {
        cvService.deleteCV(id, userDetails.getUsername());
        return ResponseEntity.noContent().build();
    }
}
