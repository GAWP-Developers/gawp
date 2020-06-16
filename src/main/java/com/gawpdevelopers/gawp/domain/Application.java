package com.gawpdevelopers.gawp.domain;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class Application {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "APPLICATION_ID")
    private Long id;
    @OneToOne(mappedBy = "application")
    private Interview interview;
    @ManyToOne(fetch = FetchType.LAZY)
    private Advert advert;  // Advert ID - Will be Set Later
    //TODO @NotNull
    @ManyToOne
    @JoinColumn(name = "APPLICANT_ID", nullable = false)
    private Applicant applicant;
    private ApplicationStatus status;
    private Date lastUpdateDate;
    @OneToMany(mappedBy = "application")
    private List<Document> documents;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Advert getAdvert() {
        return advert;
    }

    public void setAdvert(Advert advert) {
        this.advert = advert;
    }

    public Interview getInterview() {
        return interview;
    }

    public void setInterview(Interview interview) {
        this.interview = interview;
    }

    public Applicant getApplicant() {
        return applicant;
    }

    public void setApplicant(Applicant applicant) {
        this.applicant = applicant;
    }

    public ApplicationStatus getStatus() {
        return status;
    }

    public void setStatus(ApplicationStatus status) {
        this.status = status;
    }

    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public List<Document> getDocuments() {
        return documents;
    }

    public void setDocuments(List<Document> documents) {
        this.documents = documents;
    }
}