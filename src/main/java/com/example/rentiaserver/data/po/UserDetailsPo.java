package com.example.rentiaserver.data.po;

import com.example.rentiaserver.constants.TableNamesConstants;
import com.example.rentiaserver.data.api.BaseEntityPo;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = TableNamesConstants.USER_DETAILS_TABLE_NAME)
public class UserDetailsPo extends BaseEntityPo {

    @NotEmpty
    private String name;
    @NotEmpty
    private String surname;

    @NotEmpty
    private String nickname;

    private String phone;
    private String image;

    @OneToOne(mappedBy = "userDetails")
    private UserPo user;

    public UserDetailsPo(@NotEmpty String name, @NotEmpty String surname, @NotEmpty String nickname, String phone, String image) {
        this.name = name;
        this.surname = surname;
        this.nickname = nickname;
        this.phone = phone;
        this.image = image;
    }

    public UserDetailsPo() {
    }

    public UserPo getUser() {
        return user;
    }

    public void setUser(UserPo user) {
        this.user = user;
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

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
