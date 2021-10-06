package com.example.rentiaserver.data.po;

import com.example.rentiaserver.data.api.BaseEntityPo;
import com.example.rentiaserver.delivery.po.DeliveryPo;
import com.example.rentiaserver.constants.TableNamesConstants;
import com.example.rentiaserver.finance.po.UserWalletPo;
import com.example.rentiaserver.security.enums.UserRoles;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@Table(name = TableNamesConstants.USER_TABLE_NAME)
public class UserPo extends BaseEntityPo {

    @NotEmpty
    @Column(unique = true)
    private String email;

    @NotEmpty
    private String password;

    @NotNull
    @Enumerated(EnumType.STRING)
    private UserRoles role;

    @NotNull
    private Boolean logged;

    @NotNull
    @JsonIgnore
    private String salt;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_details_id", referencedColumnName = "id")
    private UserDetailsPo userDetails;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_wallet_id", referencedColumnName = "id")
    private UserWalletPo userWallet;

    @OneToMany(mappedBy = "author", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<FeedbackPo> feedbackSent;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<FeedbackPo> feedbackReceived;

    @OneToMany(mappedBy = "author", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<AnnouncementPo> announcements;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<DeliveryPo> commissions;

    @ManyToMany
    @JoinTable(
            name = "users_announcements",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "announcement_id")
    )
    private Set<AnnouncementPo> relatedAnnouncements;

    public UserPo(@NotEmpty String email,
                  @NotEmpty String password,
                  @NotNull UserRoles role,
                  UserDetailsPo userDetails,
                  UserWalletPo userWallet) {
        this.email = email;
        this.password = password;
        this.userDetails = userDetails;
        this.role = role;
        this.userWallet = userWallet;
    }

    public UserPo() {
    }

    @Override
    public void init() {
        super.init();
        logged = false;
        role = UserRoles.ROLE_USER;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public Boolean getLogged() {
        return logged;
    }

    public void setLogged(Boolean logged) {
        this.logged = logged;
    }

    public UserRoles getRoles() {
        return role;
    }

    public void setRoles(UserRoles role) {
        this.role = role;
    }

    public Set<AnnouncementPo> getAnnouncements() {
        return announcements;
    }

    public void setAnnouncements(Set<AnnouncementPo> announcements) {
        this.announcements = announcements;
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

    public UserDetailsPo getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(UserDetailsPo userDetails) {
        this.userDetails = userDetails;
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

    public UserRoles getRole() {
        return role;
    }

    public void setRole(UserRoles role) {
        this.role = role;
    }

    public Set<DeliveryPo> getCommissions() {
        return commissions;
    }

    public void setCommissions(Set<DeliveryPo> commissions) {
        this.commissions = commissions;
    }

    public Set<AnnouncementPo> getRelatedAnnouncements() {
        return relatedAnnouncements;
    }

    public void setRelatedAnnouncements(Set<AnnouncementPo> relatedAnnouncements) {
        this.relatedAnnouncements = relatedAnnouncements;
    }

    public UserWalletPo getUserWallet() {
        return userWallet;
    }

    public void setUserWallet(UserWalletPo userWallet) {
        this.userWallet = userWallet;
    }
}
