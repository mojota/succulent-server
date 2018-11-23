package com.mojota.succulent.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * oss返回临时token
 * @author jamie
 * @date 18-11-23
 */
public class OssStsResponse {
    @JsonProperty("StatusCode")
    private int StatusCode; //200成功,此处需与客户端sdk中对应
    @JsonProperty("ErrorCode")
    private String ErrorCode;
    @JsonProperty("ErrorMessage")
    private String ErrorMessage;

    @JsonProperty("AccessKeyId")
    private String AccessKeyId;
    @JsonProperty("AccessKeySecret")
    private String AccessKeySecret;
    @JsonProperty("SecurityToken")
    private String SecurityToken;
    @JsonProperty("Expiration")
    private String Expiration;

//    public int getStatusCode() {
//        return StatusCode;
//    }
//
    public void setStatusCode(int statusCode) {
        StatusCode = statusCode;
    }
//
//    public String getErrorCode() {
//        return ErrorCode;
//    }
//
    public void setErrorCode(String errorCode) {
        ErrorCode = errorCode;
    }
//
//    public String getErrorMessage() {
//        return ErrorMessage;
//    }
//
    public void setErrorMessage(String errorMessage) {
        ErrorMessage = errorMessage;
    }
//
//    public String getAccessKeyId() {
//        return AccessKeyId;
//    }
//
    public void setAccessKeyId(String accessKeyId) {
        AccessKeyId = accessKeyId;
    }
//
//    public String getAccessKeySecret() {
//        return AccessKeySecret;
//    }
//
    public void setAccessKeySecret(String accessKeySecret) {
        AccessKeySecret = accessKeySecret;
    }
//
//    public String getSecurityToken() {
//        return SecurityToken;
//    }
//
    public void setSecurityToken(String securityToken) {
        SecurityToken = securityToken;
    }

//    public String getExpiration() {
//        return Expiration;
//    }
//
    public void setExpiration(String expiration) {
        Expiration = expiration;
    }
}
