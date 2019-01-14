package com.mojota.succulent.service;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.DeleteObjectsRequest;
import com.aliyun.oss.model.DeleteObjectsResult;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.auth.sts.AssumeRoleRequest;
import com.aliyuncs.auth.sts.AssumeRoleResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author jamie
 * @date 18-11-23
 */
@Service
public class OssService {

    @Value("${oss.endpoint}")
    private String mEndpoint;

    @Value("${oss.accessKeyId}")
    private String mAccessKeyId;

    @Value("${oss.accessKeySecret}")
    private String mAccessKeySecret;

    @Value("${oss.bucketName}")
    private String mBucketName;

    @Value("${oss.expireTime}")
    private String mExpireTime;

    @Value("${oss.roleArn}")
    private String mRoleArn;

    private static Logger logger = LoggerFactory.getLogger(OssService.class);
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
     * 批量删除文件
     */
    public void deleteObjectByKeys(List<String> objectKeys) {
        if (objectKeys == null || objectKeys.size() <= 0) {
            return;
        }
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(mEndpoint, mAccessKeyId,
                mAccessKeySecret);
        try {
            //quiet为true返回简单模式-返回删除失败的文件列表
            DeleteObjectsRequest request =
                    new DeleteObjectsRequest(mBucketName).withKeys(objectKeys);
            request.setQuiet(true);
            DeleteObjectsResult deleteObjectsResult = ossClient.deleteObjects(request);
            List<String> deletedObjects = deleteObjectsResult.getDeletedObjects();
            if (deletedObjects != null && deletedObjects.size() > 0) {
                logger.error("以下文件删除失败：");
                for (String object : deletedObjects) {
                    logger.error("\t" + object);
                }
            }
        } catch (OSSException oe) {
            logger.error("Error Message: " + oe.getErrorCode());
            logger.error("Error Code: " + oe.getErrorCode());
            logger.error("Request ID: " + oe.getRequestId());
            logger.error("Host ID: " + oe.getHostId());
        } finally {
            ossClient.shutdown();
        }
    }

}
