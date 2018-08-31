package com.mojota.succulent.utils;

import org.springframework.util.StringUtils;

import java.security.MessageDigest;

/**
 * @author jamie
 * @date 18-1-25
 */
public class ToolUtil {


    /**
     * 进行md5加密
     */
    public static String encodeMd5(String str) throws Exception {
        MessageDigest digest = MessageDigest.getInstance("MD5");
        byte[] result = digest.digest(str.getBytes()); // 得到加密后的字符组数
        StringBuffer sb = new StringBuffer();
        for (byte b : result) {
            //将原本是byte型的数向上提升为int型， 从而使得原本的负数转为了正数, 这里将int型的数直接转换成16进制表示
            String hex = Integer.toHexString(b & 0xff);
            //16进制可能是为1的长度，这种情况下，需要在前面补0，
            if (hex.length() == 1) {
                sb.append(0);
            }
            sb.append(hex);
        }
        return sb.toString();
    }


    /**
     * 16位的md5加密
     */
    public static String encode16bitMd5(String str) throws Exception {
        if (!StringUtils.isEmpty(str)) {
            return encodeMd5(str).substring(8, 24);
        }
        return "";
    }
}
