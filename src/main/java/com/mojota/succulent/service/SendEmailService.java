package com.mojota.succulent.service;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.dm.model.v20151123.SingleSendMailRequest;
import com.aliyuncs.dm.model.v20151123.SingleSendMailResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.mojota.succulent.utils.BusinessException;
import com.mojota.succulent.utils.ResultEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * 调用阿里云发送邮件
 *
 * @author jamie
 * @date 18-12-21
 */
@Service
public class SendEmailService {
    @Value("${email.accessKeyId}")
    private String mAccessKeyId;

    @Value("${email.accessKeySecret}")
    private String mAccessKeySecret;

    @Value("${email.accountName}")
    private String mAccountName; //阿里云控制台创建的发信地址

    private static Logger logger = LoggerFactory.getLogger(SendEmailService.class);

    /**
     * 发送验证码
     */
    public void sendCode(String toEmail, String code) throws BusinessException {
        String subject = "来自【多肉遇见你】的验证码";
        StringBuilder sb = new StringBuilder("【多肉遇见你】您的验证码是：");
        sb.append(code).append("\n\n\n 本邮件为系统自动发出，请勿回复!");
        String content = sb.toString();
        sendEmail(toEmail, "verificationCode", subject, content);
//            request.setHtmlBody("邮件正文");
    }

    public void sendEmail(String toEmail, String tagName, String subject,
                          String content) throws BusinessException {
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", mAccessKeyId,
                mAccessKeySecret);
        DefaultAcsClient client = new DefaultAcsClient(profile);
        SingleSendMailRequest request = new SingleSendMailRequest();
        try {
            request.setAccountName(mAccountName);
            request.setFromAlias("多肉遇见你");//发信人昵称
            request.setAddressType(1);//0 为随机账号；1 为发信地址。
//            request.setTagName("控制台创建的标签");
            request.setReplyToAddress(true);//使用管理控制台中配置的回信地址
            request.setToAddress(toEmail);
            //可以给多个收件人发送邮件，收件人之间用逗号分开，批量发信建议使用BatchSendMailRequest方式
            //request.setToAddress("邮箱1,邮箱2");
            request.setSubject(subject);
//            request.setHtmlBody("邮件正文");
            request.setTextBody(content);
            SingleSendMailResponse httpResponse = client.getAcsResponse(request);
            logger.info("Request ID: " + httpResponse.getRequestId());
        } catch (ServerException e) {
            throw new BusinessException(ResultEnum.BUSINESS_ERROR_SEND_CODE, e);
        } catch (ClientException e) {
            throw new BusinessException(ResultEnum.BUSINESS_ERROR_SEND_CODE, e);
        }
    }
}
