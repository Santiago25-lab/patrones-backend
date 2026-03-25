package com.patrones.backend.controller;

import com.patrones.backend.model.CV;
import com.patrones.backend.model.Section;
import com.patrones.backend.service.CVService;
import com.patrones.backend.service.PDFExportService;
import com.patrones.backend.service.SectionService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cvs/{cvId}/export")
public class CVExportController {

    private final CVService cvService;
    private final SectionService sectionService;
    private final PDFExportService pdfExportService;

    public CVExportController(CVService cvService, SectionService sectionService, PDFExportService pdfExportService) {
        this.cvService = cvService;
        this.sectionService = sectionService;
        this.pdfExportService = pdfExportService;
    }

    @GetMapping("/pdf")
    public ResponseEntity<byte[]> exportPdf(
            @PathVariable Long cvId, 
            @AuthenticationPrincipal UserDetails userDetails) {
        
        CV cv = cvService.getCVById(cvId, userDetails.getUsername());
        List<Section> sections = sectionService.getCVSections(cvId, userDetails.getUsername());
        
        byte[] pdfBytes = pdfExportService.generateCVPdf(cv, sections);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "cv-" + cvId + ".pdf");
        
        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
    }
}
