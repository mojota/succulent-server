package com.mojota.succulent.controller;

import com.mojota.succulent.entity.ResponseInfo;
import com.mojota.succulent.entity.User;
import com.mojota.succulent.service.UserService;
import com.mojota.succulent.utils.CodeConstants;
import com.mojota.succulent.utils.BusinessException;
import com.mojota.succulent.utils.ResponseUtil;
import com.mojota.succulent.utils.ToolUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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

//    @RequestMapping("/")
//    public User add(){
//        User user = new User();
//        user.setUserName("uu1");
//        user.setPassword("pp1");
//        return userRepository.save(user);
//    }

    /**
     * 注册用户
     */
    @PostMapping(value = "/register")
    public ResponseInfo<User> register(@Valid User user, BindingResult
            bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            throw new BusinessException(CodeConstants.CODE_BUSINESS_ERROR,
                    bindingResult.getFieldError().getDefaultMessage());
        }
        user.setPassword(ToolUtil.encode16bitMd5(user.getPassword()));//密码加密
        user.setEmail(user.getUserName());
        ResponseInfo<User> resp = ResponseUtil.success(userService.register
                (user));
        return resp;
    }
//
//    /**
//     * 查询所有用户
//     */
//    @RequestMapping(value = "/getusers")
//    public ResponseInfo<List<User>> getUsers() {
//        return ResponseUtil.success(userService.getUsers());
//    }
//
//    /**
//     * 查询某用户
//     */
//    @RequestMapping(value = "/getuser")
//    public ResponseInfo<User> getUser(@RequestParam(value = "username",
// required = true) String
//                                              username) {
//        return ResponseUtil.success(userService.getUser(username));
//    }
}
