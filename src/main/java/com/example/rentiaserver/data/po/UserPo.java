package com.example.rentiaserver.data.po;

import com.example.rentiaserver.data.api.BaseEntityPo;
import com.example.rentiaserver.delivery.po.DeliveryPo;
import com.example.rentiaserver.finance.po.UserWalletPo;
import com.example.rentiaserver.security.enums.UserRoles;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Entity
@Table(name = "USERS")
public class UserPo extends BaseEntityPo {

    @NotEmpty
    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    private String name;
    private String surname;
    private String phone;
    private String image;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRoles role;

    @Column(nullable = false)
    private Boolean logged;

    @Column(nullable = false)
    private String salt;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "USER_WALLET_ID", referencedColumnName = "ID", nullable = false)
    private UserWalletPo userWalletPo;

    @OneToMany(mappedBy = "authorPo", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<FeedbackPo> feedbackSent;

    @OneToMany(mappedBy = "userPo", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<FeedbackPo> feedbackReceived;

    @OneToMany(mappedBy = "authorPo", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<AnnouncementPo> announcementPos;

    @OneToMany(mappedBy = "userPo", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<DeliveryPo> deliveryPos;

    @ManyToMany
    @JoinTable(
            name = "USERS_ANNOUNCEMENTS",
            joinColumns = @JoinColumn(name = "USER_ID"),
            inverseJoinColumns = @JoinColumn(name = "ANNOUNCEMENT_ID")
    )
    private Set<AnnouncementPo> relatedAnnouncements;

    public UserPo(String email,
                  String password,
                  UserRoles role,
                  UserWalletPo userWalletPo) {
        this.email = email;
        this.password = password;
        this.role = role;
        this.userWalletPo = userWalletPo;
    }

    public UserPo() {
    }

    @Override
    public void init() {
        super.init();
        logged = false;
        role = UserRoles.ROLE_USER;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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

    public UserWalletPo getUserWalletPo() {
        return userWalletPo;
    }

    public void setUserWalletPo(UserWalletPo userWalletPo) {
        this.userWalletPo = userWalletPo;
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

    public Set<AnnouncementPo> getRelatedAnnouncements() {
        return relatedAnnouncements;
    }

    public void setRelatedAnnouncements(Set<AnnouncementPo> relatedAnnouncements) {
        this.relatedAnnouncements = relatedAnnouncements;
    }
}
