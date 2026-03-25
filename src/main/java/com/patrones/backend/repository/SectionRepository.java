package com.patrones.backend.repository;

import com.patrones.backend.model.Section;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SectionRepository extends JpaRepository<Section, Long> {
    List<Section> findByCvIdOrderByPositionAsc(Long cvId);
}
