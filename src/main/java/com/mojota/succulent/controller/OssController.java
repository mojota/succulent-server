package com.mojota.succulent.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.auth.sts.AssumeRoleRequest;
import com.aliyuncs.auth.sts.AssumeRoleResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.mojota.succulent.dto.OssStsResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jamie
 * @date 18-11-23
 */

@RestController
@RequestMapping("/oss")
public class OssController {

    @Value("${oss.accessKeyId}")
    private String mAccessKeyId;

    @Value("${oss.accessKeySecret}")
    private String mAccessKeySecret;

    @Value("${oss.expireTime}")
    private String mExpireTime;

    @Value("${oss.roleArn}")
    private String mRoleArn;


    @RequestMapping("/getSts")
    public OssStsResponse getSts(){
        OssStsResponse ossStsResponse = new OssStsResponse();
        String endpoint = "sts.aliyuncs.com";
        String accessKeyId = mAccessKeyId;
        String accessKeySecret = mAccessKeySecret;
        String roleArn = mRoleArn;
        String roleSessionName = "session-name";
//        String policy = "{\n" +
//                "    \"Version\": \"1\", \n" +
//                "    \"Statement\": [\n" +
//                "        {\n" +
//                "            \"Action\": [\n" +
//                "                \"oss:*\"\n" +
//                "            ], \n" +
//                "            \"Resource\": [\n" +
//                "                \"acs:oss:*:*:*\" \n" +
//                "            ], \n" +
//                "            \"Effect\": \"Allow\"\n" +
//                "        }\n" +
//                "    ]\n" +
//                "}";
        try {
            // 添加endpoint（直接使用STS endpoint，前两个参数留空，无需添加region ID）
            DefaultProfile.addEndpoint("", "", "Sts", endpoint);
            // 构造default profile（参数留空，无需添加region ID）
            IClientProfile profile = DefaultProfile.getProfile("", accessKeyId, accessKeySecret);
            // 用profile构造client
            DefaultAcsClient client = new DefaultAcsClient(profile);
            final AssumeRoleRequest request = new AssumeRoleRequest();
            request.setMethod(MethodType.POST);
            request.setRoleArn(roleArn);
            request.setRoleSessionName(roleSessionName);
//            request.setPolicy(policy); // Optional
            final AssumeRoleResponse response = client.getAcsResponse(request);
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


            return ossStsResponse;
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
