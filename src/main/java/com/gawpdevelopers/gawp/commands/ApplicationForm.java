package com.gawpdevelopers.gawp.commands;

import com.gawpdevelopers.gawp.domain.Advert;
import com.gawpdevelopers.gawp.domain.Applicant;
import com.gawpdevelopers.gawp.domain.ApplicationStatus;
import com.gawpdevelopers.gawp.domain.Interview;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * Used to send to the html file for the user to form.
 * It is get from the post request sent by the user to html form.
 */
public class ApplicationForm {
    private Long id;
    private Advert advert;
    private Interview interview;
    private Applicant applicant;
    private ApplicationStatus status;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date lastUpdateDate;
    private List<Integer> docIDs;

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

    public List<Integer> getDocIDs() {
        return docIDs;
    }

    public void setDocIDs(List<Integer> docIDs) {
        this.docIDs = docIDs;
    }
}
