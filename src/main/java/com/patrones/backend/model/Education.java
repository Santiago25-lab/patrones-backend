package com.patrones.backend.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "educations")
public class Education {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cv_id", nullable = false)
    private CV cv;

    private String institution;
    private String degree;
    
    private LocalDate startDate;
    private LocalDate endDate;
    private Boolean isCurrent;

    @Column(columnDefinition = "TEXT")
    private String description;

    private Integer orderIndex;

    public Education() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public CV getCv() { return cv; }
    public void setCv(CV cv) { this.cv = cv; }

    public String getInstitution() { return institution; }
    public void setInstitution(String institution) { this.institution = institution; }

    public String getDegree() { return degree; }
    public void setDegree(String degree) { this.degree = degree; }

    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }

    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }

    public Boolean getIsCurrent() { return isCurrent; }
    public void setIsCurrent(Boolean current) { isCurrent = current; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Integer getOrderIndex() { return orderIndex; }
    public void setOrderIndex(Integer orderIndex) { this.orderIndex = orderIndex; }
}
