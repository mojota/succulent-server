package com.mojota.succulent.controller;

import com.mojota.succulent.dto.ResponseInfo;
import com.mojota.succulent.entity.NoticeInfo;
import com.mojota.succulent.service.NoticeService;
import com.mojota.succulent.utils.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author jamie
 * @date 18-12-25
 */
@RestController
@RequestMapping("/notice")
public class NoticeController {

    @Autowired
    private NoticeService noticeService;

    /**
     * 获取通知
     */
    @PostMapping("/getNoticeList")
    public ResponseInfo getNoticeList(@RequestParam(required = false,
            defaultValue = "0") Integer userId, @RequestParam(required =
            false) Long noticeTime, @PageableDefault(page = 0,
            size = 1) Pageable pageable) {

        if (noticeTime == null) {
            noticeTime = System.currentTimeMillis();
        }
        if (pageable.getPageSize() == 1) { // 如果不传size，就取全部
            pageable = null;
        }
        List<NoticeInfo> list = noticeService.getNoticeList(userId, noticeTime, pageable);
        return ResponseUtil.success(list, pageable);
    }
}
