package com.mojota.succulent.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.util.*;

/**
 * @author jamie
 * @date 18-1-25
 */
public class ToolUtil {
    private static Logger logger = LoggerFactory.getLogger(ToolUtil.class);

    public static String sensitiveFilePath = "sensitive_dict";//敏感词库文件路径
    public static Map<String, String> sensitiveWordMap;

    static {
        addWordToHashMap(readFileByLine(sensitiveFilePath));
    }


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


    /**
     * 字符串分隔后的数组
     */
    public static List<String> getStringList(String str, String separator) {
        if (!StringUtils.isEmpty(str)) {
            return Arrays.asList(str.split(separator));
        }
        return null;
    }


    /**
     * 生成4位随机码
     */
    public static Integer randomCode() {
        Random random = new Random();
        int code = random.nextInt(9000) + 1000;
        return code;
    }


    /**
     * 判断是否包含敏感字符
     */
    public static boolean containSensitiveWords(String text) {
        boolean isExist = false;
        for (int i = 0; i < text.length(); i++) {
            int matchFlag = checkSensitiveWord(text, i); //判断是否包含敏感字符
            if (matchFlag > 0) {    //大于0存在，返回true
                isExist = true;
            }
        }
        return isExist;
    }

    /**
     * 检查文字中是否包含敏感字符，检查规则如下：
     * 如果存在，则返回敏感词字符的长度，不存在返回0
     */
    public static int checkSensitiveWord(String text, int beginIndex) {
        boolean flag = false;    //敏感词结束标识位：用于敏感词只有1位的情况
        int matchFlag = 0;     //匹配标识数默认为0
        char word = 0;
        Map nowMap = sensitiveWordMap;
        for (int i = beginIndex; i < text.length(); i++) {
            word = text.charAt(i);
            nowMap = (Map) nowMap.get(word);     //获取指定key
            if (nowMap != null) {     //存在，则判断是否为最后一个
                matchFlag++;     //找到相应key，匹配标识+1
                if ("1".equals(nowMap.get("isEnd"))) {       //如果为最后一个匹配规则,结束循环，返回匹配标识数
                    flag = true;       //结束标志位为true
                    logger.info("发现敏感词："+text);
                    break;
                }
            } else {     //不存在，直接返回
                break;
            }
        }
        if (!flag) {
            matchFlag = 0;
        }
        return matchFlag;
    }


    /**
     * 读取文件中的敏感词到set中。
     */
    public static Set<String> readFileByLine(String path) {
        Set<String> keyWordSet = new HashSet<String>();
        File file = new File(path);
        if (!file.exists()) {      //文件流是否存在
            return keyWordSet;
        }
        BufferedReader reader = null;
        String temp = "";
        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)
                    , "UTF-8"));
            while ((temp = reader.readLine()) != null) {
                keyWordSet.add(temp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return keyWordSet;
    }


    /**
     * 将我们的敏感词库构建成了一个类似与一颗一颗的树，
     * 这样我们判断一个词是否为敏感词时就大大减少了检索的匹配范围。
     */
    private static void addWordToHashMap(Set<String> keyWordSet) {
        sensitiveWordMap = new HashMap(keyWordSet.size());     //初始化敏感词容器，减少扩容操作
        String key = null;
        Map nowMap = null;
        Map<String, String> newWorMap = null;
        //迭代keyWordSet
        Iterator<String> iterator = keyWordSet.iterator();
        while (iterator.hasNext()) {
            key = iterator.next();    //关键字
            nowMap = sensitiveWordMap;
            for (int i = 0; i < key.length(); i++) {
                char keyChar = key.charAt(i);       //转换成char型
                Object wordMap = nowMap.get(keyChar);       //获取
                if (wordMap != null) {        //如果存在该key，直接赋值
                    nowMap = (Map) wordMap;
                } else {     //不存在则，则构建一个map，同时将isEnd设置为0，因为他不是最后一个
                    newWorMap = new HashMap<String, String>();
                    newWorMap.put("isEnd", "0");     //不是最后一个
                    nowMap.put(keyChar, newWorMap);
                    nowMap = newWorMap;
                }
                if (i == key.length() - 1) {
                    nowMap.put("isEnd", "1");    //最后一个
                }
            }
        }
    }

}