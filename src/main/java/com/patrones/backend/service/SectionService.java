package com.patrones.backend.service;

import com.patrones.backend.dto.SectionRequest;
import com.patrones.backend.model.CV;
import com.patrones.backend.model.Section;
import com.patrones.backend.repository.SectionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SectionService {

    private final SectionRepository sectionRepository;
    private final CVService cvService;

    public SectionService(SectionRepository sectionRepository, CVService cvService) {
        this.sectionRepository = sectionRepository;
        this.cvService = cvService;
    }

    @Transactional
    public Section createSection(Long cvId, String userEmail, SectionRequest request) {
        CV cv = cvService.getCVById(cvId, userEmail);

        Section section = new Section();
        section.setCv(cv);
        section.setSectionTitle(request.getSectionTitle());
        section.setContent(request.getContent());
        section.setPosition(request.getPosition());

        return sectionRepository.save(section);
    }

    @Transactional(readOnly = true)
    public List<Section> getCVSections(Long cvId, String userEmail) {
        CV cv = cvService.getCVById(cvId, userEmail);
        return sectionRepository.findByCvIdOrderByPositionAsc(cv.getId());
    }

    @Transactional
    public Section updateSection(Long sectionId, String userEmail, SectionRequest request) {
        Section section = sectionRepository.findById(sectionId)
                .orElseThrow(() -> new RuntimeException("Section not found"));
        
        // Verify ownership indirectly via CV
        cvService.getCVById(section.getCv().getId(), userEmail);

        section.setSectionTitle(request.getSectionTitle());
        section.setContent(request.getContent());
        section.setPosition(request.getPosition());

        return sectionRepository.save(section);
    }

    @Transactional
    public void deleteSection(Long sectionId, String userEmail) {
        Section section = sectionRepository.findById(sectionId)
                .orElseThrow(() -> new RuntimeException("Section not found"));
        
        // Verify ownership
        cvService.getCVById(section.getCv().getId(), userEmail);

        sectionRepository.delete(section);
    }
}
