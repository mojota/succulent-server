package com.mojota.succulent.service;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.auth.sts.AssumeRoleRequest;
import com.aliyuncs.auth.sts.AssumeRoleResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.mojota.succulent.dto.OssStsResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author jamie
 * @date 18-11-23
 */
@Service
public class OssService {

    @Value("${oss.accessKeyId}")
    private String mAccessKeyId;

    @Value("${oss.accessKeySecret}")
    private String mAccessKeySecret;

    @Value("${oss.expireTime}")
    private String mExpireTime;

    @Value("${oss.roleArn}")
    private String mRoleArn;

    /**
     * 获取临时token
     */
    public AssumeRoleResponse getSts() throws ClientException {
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

        // 添加endpoint（直接使用STS endpoint，前两个参数留空，无需添加region ID）
        DefaultProfile.addEndpoint("", "", "Sts", endpoint);
        // 构造default profile（参数留空，无需添加region ID）
        IClientProfile profile = DefaultProfile.getProfile("", accessKeyId,
                accessKeySecret);
        // 用profile构造client
        DefaultAcsClient client = new DefaultAcsClient(profile);
        final AssumeRoleRequest request = new AssumeRoleRequest();
        request.setMethod(MethodType.POST);
        request.setRoleArn(roleArn);
        request.setRoleSessionName(roleSessionName);
//        request.setPolicy(policy); // Optional
        final AssumeRoleResponse response = client.getAcsResponse(request);
        return response;
    }

    /**
     * 删除文件
     */
    public void deleteObjectByKeys(){

    }


}
