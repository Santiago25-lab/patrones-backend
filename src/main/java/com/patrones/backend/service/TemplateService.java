package com.patrones.backend.service;

import com.patrones.backend.dto.TemplateRequest;
import com.patrones.backend.model.Template;
import com.patrones.backend.repository.TemplateRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TemplateService {

    private final TemplateRepository templateRepository;

    public TemplateService(TemplateRepository templateRepository) {
        this.templateRepository = templateRepository;
    }

    @Transactional
    public Template createTemplate(TemplateRequest request) {
        if (templateRepository.findByName(request.getName()).isPresent()) {
            throw new RuntimeException("Template with real name already exists");
        }
        
        Template template = new Template();
        template.setName(request.getName());
        template.setDescription(request.getDescription());
        template.setConfig(request.getConfig());
        
        return templateRepository.save(template);
    }

    @Transactional(readOnly = true)
    public List<Template> getAllTemplates() {
        return templateRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Template getTemplateById(Long id) {
        return templateRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Template not found"));
    }

    @Transactional
    public Template updateTemplate(Long id, TemplateRequest request) {
        Template template = getTemplateById(id);
        template.setName(request.getName());
        template.setDescription(request.getDescription());
        template.setConfig(request.getConfig());
        return templateRepository.save(template);
    }

    @Transactional
    public void deleteTemplate(Long id) {
        Template template = getTemplateById(id);
        templateRepository.delete(template);
    }
}
