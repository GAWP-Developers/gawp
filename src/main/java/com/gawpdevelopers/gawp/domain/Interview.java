package com.gawpdevelopers.gawp.domain;


import javax.persistence.*;
import java.util.Date;

@Entity
public class Interview {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String place;
    private Date date;
    private String comment;
    private Integer point; // TODO make double
    @ManyToOne
    @JoinColumn(name = "INTINFO_ID")
    private InterviewInfo info;
    @OneToOne
    @JoinColumn(name = "APPLICATION_ID")
    private Application application;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getPoint() {
        return point;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }

    public InterviewInfo getInfo() {
        return info;
    }

    public void setInfo(InterviewInfo info) {
        this.info = info;
    }

    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }
}
