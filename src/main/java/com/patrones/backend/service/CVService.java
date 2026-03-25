package com.patrones.backend.service;

import com.patrones.backend.dto.CVRequest;
import com.patrones.backend.model.CV;
import com.patrones.backend.model.User;
import com.patrones.backend.repository.CVRepository;
import com.patrones.backend.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CVService {

    private final CVRepository cvRepository;
    private final UserRepository userRepository;

    public CVService(CVRepository cvRepository, UserRepository userRepository) {
        this.cvRepository = cvRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public CV createCV(String userEmail, CVRequest request) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        CV cv = new CV();
        cv.setUser(user);
        cv.setTitle(request.getTitle());
        cv.setProfessionalProfile(request.getProfessionalProfile());

        return cvRepository.save(cv);
    }

    @Transactional(readOnly = true)
    public List<CV> getUserCVs(String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return cvRepository.findByUserId(user.getId());
    }

    @Transactional(readOnly = true)
    public CV getCVById(Long id, String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return cvRepository.findByIdAndUserId(id, user.getId())
                .orElseThrow(() -> new RuntimeException("CV not found or access denied"));
    }

    @Transactional
    public CV updateCV(Long id, String userEmail, CVRequest request) {
        CV cv = getCVById(id, userEmail);
        cv.setTitle(request.getTitle());
        cv.setProfessionalProfile(request.getProfessionalProfile());
        return cvRepository.save(cv);
    }

    @Transactional
    public void deleteCV(Long id, String userEmail) {
        CV cv = getCVById(id, userEmail);
        cvRepository.delete(cv);
    }
}
