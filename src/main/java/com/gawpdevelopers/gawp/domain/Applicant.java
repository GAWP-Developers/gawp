package com.gawpdevelopers.gawp.domain;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class Applicant {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String fName;
    private String lName;
    private Date birthDate;
    private String phone;
    @Column(columnDefinition = "varchar(max)")
    private String Address;
    private String provider;
    private String userName;
    private String apiID;
    private String email;
    private boolean active;
    private String roles; // TODO why?
    private String  pictureUrl;
    @OneToMany(mappedBy = "applicant")
    private List<Application> applications;

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public String getApiID() {
        return apiID;
    }

    public void setApiID(String apiID) {
        this.apiID = apiID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }


    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
