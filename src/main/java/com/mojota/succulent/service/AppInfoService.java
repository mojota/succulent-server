package com.mojota.succulent.service;

import com.mojota.succulent.dao.AppInfoRepository;
import com.mojota.succulent.entity.AppInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author jamie
 * @date 18-12-14
 */
@Service
public class AppInfoService {
    @Autowired
    AppInfoRepository appInfoRepository;

    /**
     * 查询最新app的信息
     */
    public AppInfo getLatestApp() {
        return appInfoRepository.findLatestAppInfo();
    }
}
