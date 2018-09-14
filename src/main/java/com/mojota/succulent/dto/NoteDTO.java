package com.mojota.succulent.dto;

import com.mojota.succulent.entity.User;

/**
 * note返回
 *
 * @author jamie
 * @date 18-9-11
 */
public interface NoteDTO {
    User userInfo = null;
    Long noteId = null;//id
    String noteTitle = "";// 标题
    Long updateTime = null;// 更新时间
    Integer permission = 0; // 0保密 1公开
    Integer likeyCount = 0; // 赞数
    Integer isLikey = 0; // 0未赞 1已赞
    String picUrls = ""; // 封面图片地址们
    Integer noteType = 0; // 笔记类型 1-成长笔记 2-造景


    User getUserInfo();

    Long getNoteId();

    String getNoteTitle();

    Long getUpdateTime();

    Integer getPermission();

    Integer getLikeyCount();

    Integer getIsLikey();

    String getPicUrls();

    Integer getNoteType();

}
