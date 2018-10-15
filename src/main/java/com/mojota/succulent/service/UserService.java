package com.mojota.succulent.service;

import com.mojota.succulent.dao.UserRepository;
import com.mojota.succulent.entity.User;
import com.mojota.succulent.utils.BusinessException;
import com.mojota.succulent.utils.ResultEnum;
import com.mojota.succulent.utils.ToolUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * @author jamie
 * @date 18-1-23
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    /**
     * 注册用户
     */
    public User register(User user) throws BusinessException {
        // userName不可重复
        if (userRepository.countUserByUserName(user.getUserName()) > 0) {
            throw new BusinessException(ResultEnum.BUSINESS_ERROR_USER_REPEAT);
        } else {
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
     * 修改用户信息
     *
     * @param userId
     * @param nickname
     * @param region
     * @param password
     * @return
     */
    public User edit(int userId, String nickname, String region, String password)
            throws Exception {
        User user = userRepository.findUserByUserId(userId);
        user.setNickname(nickname);
        user.setRegion(region);
        if (!StringUtils.isEmpty(password)) {
            String passwordMd5 = ToolUtil.encode16bitMd5(password);
            user.setPassword(passwordMd5);
        }
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
        if (userRepository.updateAvatarByUserId(avatarUrl, userId) > 0) {
            return;
        } else {
            throw new BusinessException(ResultEnum.BUSINESS_DATA_NOT_FOUND);
        }
    }

//    public List<User> getUsers() {
//        return userMapper.getUsers();
//    }

//    public User getUser(String username) {
//        return userMapper.getUser(username);
//    }
}
