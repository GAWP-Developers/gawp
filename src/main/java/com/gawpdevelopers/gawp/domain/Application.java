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
    private Long intID;     //  Interview ID - Will be Set Later
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ADVERT_ID")
    private Advert advert;  // Advert ID - Will be Set Later
    //TODO @NotNull
    private Long applicantID;
    private ApplicationStatus status;
    private String applicationDegree; //TODO Keep it or lose it.
    private Date lastUpdateDate;
    @OneToMany(mappedBy = "application")
    private List<Document> documents;   //TODO Plan is to keep documents in different folders for all applications.

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

    public Long getIntID() {
        return intID;
    }

    public void setIntID(Long intID) {
        this.intID = intID;
    }

    public Long getApplicantID() {
        return applicantID;
    }

    public void setApplicantID(Long applicantID) {
        this.applicantID = applicantID;
    }

    public ApplicationStatus getStatus() {
        return status;
    }

    public void setStatus(ApplicationStatus status) {
        this.status = status;
    }

    public String getApplicationDegree() {
        return applicationDegree;
    }

    public void setApplicationDegree(String applicationDegree) {
        this.applicationDegree = applicationDegree;
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