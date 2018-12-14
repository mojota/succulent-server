package com.mojota.succulent.controller;

import com.mojota.succulent.dto.ResponseInfo;
import com.mojota.succulent.entity.AppInfo;
import com.mojota.succulent.service.AppInfoService;
import com.mojota.succulent.utils.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jamie
 * @date 18-12-14
 */
@RestController
@RequestMapping("/app")
public class AppInfoController {
    @Autowired
    AppInfoService appInfoService;

    @PostMapping("/getLatestApp")
    public ResponseInfo<AppInfo> getLatestApp() {
        return ResponseUtil.success(appInfoService.getLatestApp());
    }
}
