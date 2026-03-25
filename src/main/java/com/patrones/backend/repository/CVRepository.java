package com.patrones.backend.repository;

import com.patrones.backend.model.CV;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CVRepository extends JpaRepository<CV, Long> {
    List<CV> findByUserId(Long userId);
    Optional<CV> findByIdAndUserId(Long id, Long userId);
}
