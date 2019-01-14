package com.mojota.succulent.controller;

import com.aliyuncs.auth.sts.AssumeRoleResponse;
import com.aliyuncs.exceptions.ClientException;
import com.mojota.succulent.dto.OssStsResponse;
import com.mojota.succulent.service.OssService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static Logger logger = LoggerFactory.getLogger(OssController.class);

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

            logger.info("Access Key Id: " + credentials.getAccessKeyId());
            logger.info("Access Key Secret: " + credentials.getAccessKeySecret());
            logger.info("Security Token: " + credentials.getSecurityToken());
            logger.info("Expiration: " + credentials.getExpiration());
            logger.info("RequestId: " + response.getRequestId());
        } catch (ClientException e) {
            ossStsResponse.setStatusCode(201);
            ossStsResponse.setErrorCode(e.getErrCode());
            ossStsResponse.setErrorMessage(e.getErrMsg());
            logger.error("Failed：");
            logger.error("Error code: " + e.getErrCode());
            logger.error("Error message: " + e.getErrMsg());
            logger.error("RequestId: " + e.getRequestId());
        }
        return ossStsResponse;
    }
}
