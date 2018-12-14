package com.mojota.succulent.dao;

import com.mojota.succulent.entity.AppInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * @author jamie
 * @date 18-12-14
 */
public interface AppInfoRepository extends JpaRepository<AppInfo, Integer> {

    @Query(value = "select * from app_info order by version_code desc limit 1;",
            nativeQuery = true)
    AppInfo findLatestAppInfo();
}
