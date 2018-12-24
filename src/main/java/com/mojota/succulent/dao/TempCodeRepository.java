package com.mojota.succulent.dao;

import com.mojota.succulent.entity.TempCodeInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author jamie
 * @date 18-12-24
 */
public interface TempCodeRepository extends JpaRepository<TempCodeInfo, String> {

    TempCodeInfo findByUserName(String userName);
}
