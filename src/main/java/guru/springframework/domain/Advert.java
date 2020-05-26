package guru.springframework.domain;

import javax.persistence.*;
import java.util.Date;


@Entity
public class Advert {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ADVERT_ID")
    private Long id;
    private String name;
    //TODO @OneToOne(fetch = FetchType.LAZY)
    //     @JoinColumn(name = "GRAD_ID")
    private Long gradID;
    //TODO @OneToOne(fetch = FetchType.LAZY)
    //     @JoinColumn(name = "INT_INFO_ID")
    private Long intInfoID;
    private Date shareDate;
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