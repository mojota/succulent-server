package com.mojota.succulent.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import com.mojota.succulent.utils.CodeConstants;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

/**
 * @author jamie
 * @date 18-8-29
 */
@Entity
@Table(name = "user")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User {

//    @GeneratedValue(strategy = GenerationType.TABLE, generator =
// "pk_generator")
//    @TableGenerator(name = "pk_generator", table = "tb_generator",
//            pkColumnName = "pk_name", valueColumnName = "pk_value",
//            pkColumnValue = "PK",
//            initialValue = 100000, allocationSize = 1)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int userId;//用户id

    @NotEmpty(message = CodeConstants.MSG_BUSINESS_ERROR_USER_EMPTY)
    @Email(message = CodeConstants.MSG_BUSINESS_ERROR_EMAIL_FORMAT_INCORRECT)
    @Column(nullable = false, unique = true, length = 100)
    String userName; //用户名

    @NotEmpty(message = CodeConstants.MSG_BUSINESS_ERROR_PWD_EMPTY)
    @Length(min = 6, message = CodeConstants.MSG_BUSINESS_ERROR_PWD_SHORT)
    @Column(nullable = false, length = 20)
    @JsonIgnore
    String password; //密码

    @Column(length = 50)
    String nickname;//用户昵称

    String avatarUrl;//用户头像地址

    @Column(length = 100)
    String region; //地区

    @Column(length = 50)
    String email;

    @Column(length = 50)
    String phone;

    String coverUrl; // 封面图url

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }
}
