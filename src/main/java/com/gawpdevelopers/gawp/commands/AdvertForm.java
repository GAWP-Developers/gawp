package com.gawpdevelopers.gawp.commands;


import com.gawpdevelopers.gawp.domain.AdvertType;
import org.springframework.format.annotation.DateTimeFormat;


import java.util.Date;

/**
 * Used to send to the html file for the user to form.
 * It is get from the post request sent by the user to html form.
 */
public class AdvertForm {
    private Long id;
    private String name;
    private Long gradID;
    private Long intInfoID;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date shareDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date deadlineDate;
    private AdvertType type;
    private String details;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getGradID() {
        return gradID;
    }

    public void setGradID(Long gradID) {
        this.gradID = gradID;
    }

    public Long getIntInfoID() {
        return intInfoID;
    }

    public void setIntInfoID(Long intInfoID) {
        this.intInfoID = intInfoID;
    }

    public Date getShareDate() {
        return shareDate;
    }

    public void setShareDate(Date shareDate) {
        this.shareDate = shareDate;
    }

    public Date getDeadlineDate() {
        return deadlineDate;
    }

    public void setDeadlineDate(Date deadlineDate) {
        this.deadlineDate = deadlineDate;
    }

    public AdvertType getType() {
        return type;
    }

    public void setType(AdvertType type) {
        this.type = type;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
