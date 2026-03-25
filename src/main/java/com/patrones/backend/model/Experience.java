package com.patrones.backend.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "experiences")
public class Experience {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cv_id", nullable = false)
    private CV cv;

    private String company;
    private String positionTitle;
    
    private LocalDate startDate;
    private LocalDate endDate;
    private Boolean isCurrent;

    @Column(columnDefinition = "TEXT")
    private String description;

    private Integer orderIndex;

    public Experience() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public CV getCv() { return cv; }
    public void setCv(CV cv) { this.cv = cv; }

    public String getCompany() { return company; }
    public void setCompany(String company) { this.company = company; }

    public String getPositionTitle() { return positionTitle; }
    public void setPositionTitle(String positionTitle) { this.positionTitle = positionTitle; }

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
