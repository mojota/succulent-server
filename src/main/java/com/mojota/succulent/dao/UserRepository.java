package com.mojota.succulent.dao;

import com.mojota.succulent.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author jamie
 * @date 18-8-29
 */
public interface UserRepository extends JpaRepository<User, Integer> {
    int countUserByUserName(String userName);
}
