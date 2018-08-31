package com.mojota.succulent.service;

import com.mojota.succulent.dao.UserRepository;
import com.mojota.succulent.entity.User;
import com.mojota.succulent.utils.BusinessException;
import com.mojota.succulent.utils.CodeConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author jamie
 * @date 18-1-23
 */
@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    /**
     * 注册用户
     */
    public User register(User user) throws BusinessException {
        // userName不可重复
        if (userRepository.countUserByUserName(user.getUserName()) > 0) {
            throw new BusinessException(CodeConstants.CODE_BUSINESS_ERROR,
                    CodeConstants.MSG_BUSINESS_ERROR_USER_REPEAT);
        } else {
            return userRepository.save(user);
        }
    }

//    public List<User> getUsers() {
//        return userMapper.getUsers();
//    }

//    public User getUser(String username) {
//        return userMapper.getUser(username);
//    }
}
