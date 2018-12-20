package com.mojota.succulent.dao;

import com.mojota.succulent.entity.User;
import org.hibernate.event.spi.SaveOrUpdateEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author jamie
 * @date 18-8-29
 */
public interface UserRepository extends JpaRepository<User, Integer> {
    int countUserByUserName(String userName);

    User findUserByUserNameAndPassword(String userName, String password);

    User findUserByUserId(int userId);

    @Transactional
    @Modifying
    @Query(value = "update User set avatarUrl = :avatarUrl where userId = :userId")
    int updateAvatarByUserId(@Param("avatarUrl") String avatarUrl, @Param
            ("userId") int userId);

    @Transactional
    @Modifying
    @Query(value = "update User set coverUrl = :coverUrl where userId = :userId")
    int updateCoverByUserId(@Param("coverUrl") String coverUrl, @Param
            ("userId") int userId);

    @Transactional
    @Modifying
    @Query(value = "update User set password = :password where userName = :userName")
    int updatePasswordByUserName(@Param("password") String password, @Param
            ("userName") String userName);
}
