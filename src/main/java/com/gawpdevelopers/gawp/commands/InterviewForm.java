package com.gawpdevelopers.gawp.commands;

import com.gawpdevelopers.gawp.domain.Application;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * Used to send to the html file for the user to form.
 * It is get from the post request sent by the user to html form.
 */
public class InterviewForm {
    private Long id;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;
    private Application application;
    private String comment;
    private Double point;
    private String place;
    private String time;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Double getPoint() {
        return point;
    }

    public void setPoint(Double point) {
        this.point = point;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }
}
