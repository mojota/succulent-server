package com.mojota.succulent.controller;

import com.mojota.succulent.dao.TempCodeRepository;
import com.mojota.succulent.dto.ResponseInfo;
import com.mojota.succulent.entity.TempCodeInfo;
import com.mojota.succulent.entity.User;
import com.mojota.succulent.service.SendEmailService;
import com.mojota.succulent.service.UserService;
import com.mojota.succulent.utils.BusinessException;
import com.mojota.succulent.utils.ResponseUtil;
import com.mojota.succulent.utils.ResultEnum;
import com.mojota.succulent.utils.ToolUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author jamie
 * @date 18-8-29
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private SendEmailService sendEmailService;

    @Autowired
    private TempCodeRepository tempCodeRepository;

    /**
     * 注册用户
     */
    @PostMapping(value = "/register")
    public ResponseInfo<User> register(@Valid User user, BindingResult
            bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            throw new BusinessException(ResultEnum.BUSINESS_ERROR.getCode(),
                    bindingResult.getFieldError().getDefaultMessage());
        }

        if (StringUtils.isEmpty(user.getPassword())){
            throw new BusinessException(ResultEnum.BUSINESS_ERROR_PWD_EMPTY);
        }
        if (!StringUtils.isEmpty(user.getPassword()) && user.getPassword().length() < 6) {
            throw new BusinessException(ResultEnum.BUSINESS_ERROR_PWD_SHORT);
        }
        user.setPassword(ToolUtil.encode16bitMd5(user.getPassword()));//密码加密
        user.setEmail(user.getUserName());
        user.setRegisterTime(System.currentTimeMillis());
        return ResponseUtil.success(userService.register(user));
    }

    /**
     * 登录
     */
    @PostMapping(value = "/login")
    public ResponseInfo<User> login(@RequestParam String userName, @RequestParam
            String password) throws Exception {
        String passwordMd5 = ToolUtil.encode16bitMd5(password);
        return ResponseUtil.success(userService.login(userName, passwordMd5));
    }

    /**
     * 使用qq登录
     */
    @PostMapping("/qqLogin")
    public ResponseInfo<User> qqLogin(@RequestParam String userName,
                                      @RequestParam String password,
                                      @RequestParam(required = false) String nickname,
                                      @RequestParam(required = false) String avatarUrl) throws Exception {
        String passwordMd5 = ToolUtil.encode16bitMd5(password);
        return ResponseUtil.success(userService.qqLogin(userName, passwordMd5, nickname,
                avatarUrl));
    }

    /**
     * 重置密码
     */
    @PostMapping(value = "/resetPwd")
    public ResponseInfo resetPwd(@RequestParam String userName, @RequestParam
            String password, @RequestParam String tempCode) throws Exception {

        if (StringUtils.isEmpty(password)){
            throw new BusinessException(ResultEnum.BUSINESS_ERROR_PWD_EMPTY);
        }
        if (!StringUtils.isEmpty(password) && password.length() < 6) {
            throw new BusinessException(ResultEnum.BUSINESS_ERROR_PWD_SHORT);
        }
        // 验证码验证
        TempCodeInfo tempCodeInfo = tempCodeRepository.findByUserName(userName);
        if (!tempCode.equals(String.valueOf(tempCodeInfo.getCode()))){
            throw new BusinessException(ResultEnum.BUSINESS_ERROR_CODE_INCORRECT);
        }

        String passwordMd5 = ToolUtil.encode16bitMd5(password);
        userService.resetPwd(userName, passwordMd5);

        tempCodeRepository.deleteById(userName);
        return ResponseUtil.success(null);
    }

    /**
     * 修改用户信息
     *
     * @param userId
     * @param nickname
     * @param region
     * @return
     */
    @PostMapping(value = "/edit")
    public ResponseInfo<User> edit(@RequestParam int userId, @RequestParam
            String nickname, @RequestParam String region) throws Exception {
        User user = userService.edit(userId, nickname, region);
        return ResponseUtil.success(user);
    }

    /**
     * 修改头像地址
     *
     * @param userId
     * @param avatarUrl
     * @return
     */
    @PostMapping(value = "/editAvatar")
    public ResponseInfo editAvatarUrl(@RequestParam int userId, @RequestParam
            String avatarUrl) throws BusinessException {
        userService.editAvatarUrl(userId, avatarUrl);
        return ResponseUtil.success(null);
    }


    /**
     * 修改moments封面地址
     *
     * @param userId
     * @param coverUrl
     * @return
     */
    @PostMapping(value = "/editCover")
    public ResponseInfo editCoverUrl(@RequestParam int userId, @RequestParam
            String coverUrl) throws BusinessException {
        userService.editCoverUrl(userId, coverUrl);
        return ResponseUtil.success(null);
    }


    /**
     * 请求发送验证码
     */
    @PostMapping(value = "/sendCode")
    public ResponseInfo sendCode(@RequestParam String userName) throws BusinessException {
        if (StringUtils.isEmpty(userName)) {
            throw new BusinessException(ResultEnum.BUSINESS_ERROR_USER_EMPTY);
        }

        // 检查用户是否存在
        if (!userService.isUserExist(userName)){
            throw new BusinessException(ResultEnum.BUSINESS_ERROR_USER_NOT_EXIST);
        }

        int code = ToolUtil.randomCode();
        TempCodeInfo tempCodeInfo = new TempCodeInfo();
        tempCodeInfo.setUserName(userName);
        tempCodeInfo.setCode(code);
        tempCodeInfo.setCreateTime(System.currentTimeMillis());
        // 先保存到表中
        tempCodeRepository.saveAndFlush(tempCodeInfo);
        // 再发送邮件
        sendEmailService.sendCode(userName, String.valueOf(code));

        return ResponseUtil.success(null);
    }
}
