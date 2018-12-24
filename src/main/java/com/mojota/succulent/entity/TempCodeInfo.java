package com.mojota.succulent.entity;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 保存用户用验证码
 *
 * @author jamie
 * @date 18-8-29
 */
@Entity
@Table(name = "temp_code")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TempCodeInfo {

    @Id
    String userName;//用户名

    @Column
    Integer code;//验证码

    @Column
    Long createTime; // 生成验证码时间

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }
}
