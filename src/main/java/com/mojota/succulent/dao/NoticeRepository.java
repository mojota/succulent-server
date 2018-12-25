package com.mojota.succulent.dao;

import com.mojota.succulent.entity.NoticeInfo;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author jamie
 * @date 18-12-25
 */
public interface NoticeRepository extends JpaRepository<NoticeInfo, Long> {

    List<NoticeInfo> findByNoticeTimeBeforeOrderByNoticeTimeDesc(Long noticeTime,
                                                                    Pageable pageable);
}
