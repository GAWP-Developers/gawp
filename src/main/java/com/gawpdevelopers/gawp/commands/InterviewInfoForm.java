package com.gawpdevelopers.gawp.commands;

import com.gawpdevelopers.gawp.domain.Advert;
import com.gawpdevelopers.gawp.domain.Interview;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Used to send to the html file for the user to form.
 * It is get from the post request sent by the user to html form.
 */
public class InterviewInfoForm {
    private Long id;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date notifyDate;
    private Integer startTime;
    private Integer endTime;
    private Integer totalInterview;
    private Integer timePerInt;
    private List<String> days;
    private Integer numOfIntPerDay;
    private Integer timeBetweenInt;
    private Boolean isAnnounced;
    private Advert advert;
    @OneToMany(mappedBy = "info")
    private List<Interview> interviews;
    private String mailTemplate;
    private Integer lunchStartTime;
    private Integer lunchEndTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getNotifyDate() {
        return notifyDate;
    }

    public void setNotifyDate(Date notifyDate) {
        this.notifyDate = notifyDate;
    }

    public Integer getStartTime() {
        return startTime;
    }

    public void setStartTime(Integer startTime) {
        this.startTime = startTime;
    }

    public Integer getEndTime() {
        return endTime;
    }

    public void setEndTime(Integer endTime) {
        this.endTime = endTime;
    }

    public Integer getTotalInterview() {
        return totalInterview;
    }

    public void setTotalInterview(Integer totalInterview) {
        this.totalInterview = totalInterview;
    }

    public Integer getTimePerInt() {
        return timePerInt;
    }

    public void setTimePerInt(Integer timePerInt) {
        this.timePerInt = timePerInt;
    }

    public List<String> getDays() {
        return days;
    }

    public void setDays(List<String> days) {
        this.days = days;
    }

    public Integer getNumOfIntPerDay() {
        return numOfIntPerDay;
    }

    public void setNumOfIntPerDay(Integer numOfIntPerDay) {
        this.numOfIntPerDay = numOfIntPerDay;
    }

    public Integer getTimeBetweenInt() {
        return timeBetweenInt;
    }

    public void setTimeBetweenInt(Integer timeBetweenInt) {
        this.timeBetweenInt = timeBetweenInt;
    }

    public Boolean getAnnounced() {
        return isAnnounced;
    }

    public void setAnnounced(Boolean announced) {
        isAnnounced = announced;
    }

    public Advert getAdvert() {
        return advert;
    }

    public void setAdvert(Advert advert) {
        this.advert = advert;
    }

    public List<Interview> getInterviews() {
        return interviews;
    }

    public void setInterviews(List<Interview> interviews) {
        this.interviews = interviews;
    }

    public String getMailTemplate() {
        return mailTemplate;
    }

    public void setMailTemplate(String mailTemplate) {
        this.mailTemplate = mailTemplate;
    }

    public Integer getLunchStartTime() {
        return lunchStartTime;
    }

    public void setLunchStartTime(Integer lunchStartTime) {
        this.lunchStartTime = lunchStartTime;
    }

    public Integer getLunchEndTime() {
        return lunchEndTime;
    }

    public void setLunchEndTime(Integer lunchEndTime) {
        this.lunchEndTime = lunchEndTime;
    }
}
