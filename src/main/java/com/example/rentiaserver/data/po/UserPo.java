package com.example.rentiaserver.data.po;

import com.example.rentiaserver.data.api.BaseEntityPo;
import com.example.rentiaserver.delivery.po.DeliveryPo;
import com.example.rentiaserver.security.enums.UserRoles;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;
import java.util.Set;

@Entity
@Table(name = "USERS")
public class UserPo extends BaseEntityPo {

    @NotEmpty
    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String surname;

    @Column(unique = true)
    private String phone;

    @Column(nullable = false)
    private BigDecimal balance;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRoles role;

    @Column(nullable = false)
    private Boolean logged;

    @Column(nullable = false)
    private String salt;

    @OneToMany(mappedBy = "authorPo", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<FeedbackPo> feedbackSent;

    @OneToMany(mappedBy = "userPo", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<FeedbackPo> feedbackReceived;

    @OneToMany(mappedBy = "authorPo", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<AnnouncementPo> announcementPos;

    @OneToMany(mappedBy = "userPo", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<DeliveryPo> deliveryPos;

    public UserPo(String email, String password, String name, String surname, UserRoles role) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.role = role;
    }

    public UserPo() {
    }

    @Override
    public void init() {
        super.init();
        logged = false;
        role = UserRoles.ROLE_USER;
        balance = new BigDecimal("0.0");
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public UserRoles getRole() {
        return role;
    }

    public void setRole(UserRoles role) {
        this.role = role;
    }

    public Boolean getLogged() {
        return logged;
    }

    public void setLogged(Boolean logged) {
        this.logged = logged;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public Set<FeedbackPo> getFeedbackSent() {
        return feedbackSent;
    }

    public void setFeedbackSent(Set<FeedbackPo> feedbackSent) {
        this.feedbackSent = feedbackSent;
    }

    public Set<FeedbackPo> getFeedbackReceived() {
        return feedbackReceived;
    }

    public void setFeedbackReceived(Set<FeedbackPo> feedbackReceived) {
        this.feedbackReceived = feedbackReceived;
    }

    public Set<AnnouncementPo> getAnnouncementPos() {
        return announcementPos;
    }

    public void setAnnouncementPos(Set<AnnouncementPo> announcementPos) {
        this.announcementPos = announcementPos;
    }

    public Set<DeliveryPo> getDeliveryPos() {
        return deliveryPos;
    }

    public void setDeliveryPos(Set<DeliveryPo> deliveryPos) {
        this.deliveryPos = deliveryPos;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}
