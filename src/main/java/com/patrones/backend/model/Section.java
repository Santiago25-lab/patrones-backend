package com.patrones.backend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "sections")
public class Section {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cv_id", nullable = false)
    private CV cv;

    @Column(nullable = false)
    private String sectionTitle;

    @Column(columnDefinition = "TEXT")
    private String content;

    private Integer position;

    public Section() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public CV getCv() { return cv; }
    public void setCv(CV cv) { this.cv = cv; }

    public String getSectionTitle() { return sectionTitle; }
    public void setSectionTitle(String sectionTitle) { this.sectionTitle = sectionTitle; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public Integer getPosition() { return position; }
    public void setPosition(Integer position) { this.position = position; }
}
