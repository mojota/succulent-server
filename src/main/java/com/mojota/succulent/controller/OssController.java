package com.mojota.succulent.controller;

import com.aliyuncs.auth.sts.AssumeRoleResponse;
import com.aliyuncs.exceptions.ClientException;
import com.mojota.succulent.dto.OssStsResponse;
import com.mojota.succulent.service.OssService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jamie
 * @date 18-11-23
 */

@RestController
@RequestMapping("/oss")
public class OssController {

    @Autowired
    OssService ossService;

    @RequestMapping("/getSts")
    public OssStsResponse getSts() {
        OssStsResponse ossStsResponse = new OssStsResponse();
        try {
            AssumeRoleResponse response = ossService.getSts();
            AssumeRoleResponse.Credentials credentials = response.getCredentials();
            ossStsResponse.setStatusCode(200);
            ossStsResponse.setAccessKeyId(credentials.getAccessKeyId());
            ossStsResponse.setAccessKeySecret(credentials.getAccessKeySecret());
            ossStsResponse.setSecurityToken(credentials.getSecurityToken());
            ossStsResponse.setExpiration(credentials.getExpiration());

            System.out.println("Access Key Id: " + credentials.getAccessKeyId());
            System.out.println("Access Key Secret: " + credentials.getAccessKeySecret());
            System.out.println("Security Token: " + credentials.getSecurityToken());
            System.out.println("Expiration: " + credentials.getExpiration());
            System.out.println("RequestId: " + response.getRequestId());

        } catch (ClientException e) {
            ossStsResponse.setStatusCode(201);
            ossStsResponse.setErrorCode(e.getErrCode());
            ossStsResponse.setErrorMessage(e.getErrMsg());

            System.out.println("Failed：");
            System.out.println("Error code: " + e.getErrCode());
            System.out.println("Error message: " + e.getErrMsg());
            System.out.println("RequestId: " + e.getRequestId());
        }
        return ossStsResponse;
    }
}
