package com.mojota.succulent.service;

import com.mojota.succulent.dao.UserRepository;
import com.mojota.succulent.entity.User;
import com.mojota.succulent.utils.BusinessException;
import com.mojota.succulent.utils.ResultEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author jamie
 * @date 18-1-23
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OssService ossService;

    private static Logger logger = LoggerFactory.getLogger(UserService.class);


    /**
     * 注册用户
     */
    public User register(User user) throws BusinessException {
        // userName不可重复
        if (userRepository.countUserByUserName(user.getUserName()) > 0) {
            throw new BusinessException(ResultEnum.BUSINESS_ERROR_USER_REPEAT);
        } else {
            logger.info(user.getUserName() + "注册成功," + user.getRegisterTime());
            return userRepository.save(user);
        }
    }

    /**
     * 登录
     */
    public User login(String userName, String passwordMd5) throws BusinessException {
        User user = userRepository.findUserByUserNameAndPassword(userName,
                passwordMd5);
        if (user == null) {
            throw new BusinessException(ResultEnum.BUSINESS_ERROR_USER_WRONG);
        } else {
            return user;
        }
    }

    /**
     * qq登录
     * 若不存在则保存，已存在直接登录。
     */
    public User qqLogin(String userName, String passwordMd5, String nickname,
                        String avatarUrl) throws Exception {
        User user = userRepository.findUserByUserNameAndPassword(userName, passwordMd5);
        if (user == null) {
            user = new User();
            user.setUserName(userName);
            user.setPassword(passwordMd5);//密码加密
            user.setRegisterTime(System.currentTimeMillis());
            user.setNickname(nickname);
            user.setAvatarUrl(avatarUrl);
            user = userRepository.save(user);
            logger.info(user.getUserName() + "qq注册成功,时间:" + user.getRegisterTime());
        }
        return user;
    }

    /**
     * 修改用户信息
     *
     * @param userId
     * @param nickname
     * @param region
     * @return
     */
    public User edit(int userId, String nickname, String region) {
        User user = userRepository.findUserByUserId(userId);
        user.setNickname(nickname);
        user.setRegion(region);
        return userRepository.saveAndFlush(user);
    }

    /**
     * 修改头像
     *
     * @param userId
     * @param avatarUrl
     */
    public void editAvatarUrl(int userId, String avatarUrl) throws
            BusinessException {
        // 获取用户表中图片key
        List<String> objectKeys = new ArrayList<String>();
        User user = userRepository.findUserByUserId(userId);
        if (user != null && !StringUtils.isEmpty(user.getAvatarUrl())){
            objectKeys.add(user.getAvatarUrl());
        }

        if (userRepository.updateAvatarByUserId(avatarUrl, userId) > 0) {
            // 删除oss中的对应图片
            ossService.deleteObjectByKeys(objectKeys);
        } else {
            throw new BusinessException(ResultEnum.BUSINESS_DATA_NOT_FOUND);
        }
    }


    /**
     * 修改moments封面
     *
     * @param userId
     * @param coverUrl
     */
    public void editCoverUrl(int userId, String coverUrl) throws
            BusinessException {
        // 获取用户表中图片key
        List<String> objectKeys = new ArrayList<String>();
        User user = userRepository.findUserByUserId(userId);
        if (user != null && !StringUtils.isEmpty(user.getCoverUrl())){
            objectKeys.add(user.getCoverUrl());
        }

        if (userRepository.updateCoverByUserId(coverUrl, userId) > 0) {
            // 删除oss中的对应图片
            ossService.deleteObjectByKeys(objectKeys);
        } else {
            throw new BusinessException(ResultEnum.BUSINESS_DATA_NOT_FOUND);
        }
    }

    /**
     * 重置密码
     *
     * @param userName
     * @param passwordMd5
     */
    public void resetPwd(String userName, String passwordMd5) throws BusinessException {
        if (userRepository.updatePasswordByUserName(passwordMd5, userName) <= 0) {
            throw new BusinessException(ResultEnum.BUSINESS_ERROR_USER_NOT_EXIST);
        }
    }

    /**
     * 检查用户是否存在
     */
    public boolean isUserExist(String userName) {
        return (userRepository.countUserByUserName(userName) > 0);
    }

}
