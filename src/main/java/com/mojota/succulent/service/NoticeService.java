package com.mojota.succulent.service;

import com.mojota.succulent.dao.NoticeRepository;
import com.mojota.succulent.entity.NoticeInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author jamie
 * @date 18-12-25
 */
@Service
public class NoticeService {
    @Autowired
    private NoticeRepository noticeRepository;

    /**
     * 分页获取通知
     */
    public List<NoticeInfo> getNoticeList(Integer userId, Long noticeTime,
                                          Pageable pageable) {
        return noticeRepository.findByNoticeTimeBeforeOrderByNoticeTimeDesc(noticeTime,
                pageable);
    }
}
