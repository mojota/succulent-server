package com.mojota.succulent.controller;

import com.mojota.succulent.dto.NoteDTO;
import com.mojota.succulent.dto.ResponseInfo;
import com.mojota.succulent.entity.Note;
import com.mojota.succulent.entity.NoteDetail;
import com.mojota.succulent.service.NoteService;
import com.mojota.succulent.service.OssService;
import com.mojota.succulent.utils.BusinessException;
import com.mojota.succulent.utils.ResponseUtil;
import com.mojota.succulent.utils.ResultEnum;
import com.mojota.succulent.utils.ToolUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;


/**
 * @author jamie
 * @date 18-9-6
 */
@RestController
@RequestMapping("/note")
public class NoteController {

    @Autowired
    private NoteService noteService;

    @Autowired
    private OssService ossService;

    private void checkUser(Integer userId) throws BusinessException {
        // userId不可为空
        if (userId == null || userId == 0) {
            throw new BusinessException(ResultEnum.BUSINESS_ERROR_USER_NOT_LOGIN);
        }
    }

    @PostMapping(value = "/noteAdd")
    public ResponseInfo noteAdd(@RequestParam Integer userId, @RequestParam String
            noteTitle, @RequestParam Integer permission, @RequestParam int noteType,
                                @RequestParam String picUrls) throws
            BusinessException {
        checkUser(userId);

        long time = System.currentTimeMillis();
        Note note = new Note();
        note.setUserId(userId);
        note.setNoteTitle(noteTitle);
        note.setPermission(permission);
        note.setNoteType(noteType);
        note.setPicUrls(picUrls);
        note.setUpdateTime(time);
        noteService.saveNote(note);

        return ResponseUtil.success(null);
    }


    @PostMapping(value = "/diaryAdd")
    public ResponseInfo diaryAdd(@RequestParam Integer userId, @RequestParam String
            noteTitle, @RequestParam String content, @RequestParam int noteType,
                                 @RequestParam String picUrls) throws
            BusinessException {
        checkUser(userId);
        //若标题为空，则不写入笔记表
        if (StringUtils.isEmpty(noteTitle)) {
            throw new BusinessException(ResultEnum.BUSINESS_NOTE_TITLE_EMPTY);
        }

        long time = System.currentTimeMillis();
        Note note = new Note();
        note.setUserId(userId);
        note.setNoteTitle(noteTitle);
        note.setNoteType(noteType);
        note.setPicUrls(picUrls);
        note.setUpdateTime(time);

        //若内容为空,则只写note表,不写入明细表
        if (StringUtils.isEmpty(content) && StringUtils.isEmpty(picUrls)) {
            noteService.saveNote(note);
        } else {
            NoteDetail noteDetail = new NoteDetail();
            noteDetail.setNote(note);
            noteDetail.setContent(content);
            noteDetail.setPicUrls(picUrls);
            noteDetail.setCreateTime(time);
            noteService.detailAdd(noteDetail);
        }
        return ResponseUtil.success(null);
    }


    @PostMapping(value = "/diaryDetailAdd")
    public ResponseInfo diaryDetailAdd(@RequestParam Integer userId, @RequestParam
            Long noteId, @RequestParam String content, @RequestParam String
                                               picUrls) throws BusinessException {
        checkUser(userId);

        long time = System.currentTimeMillis();
        Note note = noteService.getNoteByNoteId(noteId);
        if (note == null) {
            //若note为空，则不写入明细表
            throw new BusinessException(ResultEnum.BUSINESS_NOTE_NOT_FOUND);
        }
        //若内容为空，则不写入明细表
        if (StringUtils.isEmpty(content) && StringUtils.isEmpty(picUrls)) {
            throw new BusinessException(ResultEnum.BUSINESS_DATA_EMPTY);
        }
        note.setPicUrls(picUrls);
        note.setUpdateTime(time);
        NoteDetail noteDetail = new NoteDetail();
        noteDetail.setNote(note);
        noteDetail.setContent(content);
        noteDetail.setPicUrls(picUrls);
        noteDetail.setCreateTime(time);
        noteService.detailAdd(noteDetail);
        return ResponseUtil.success(null);
    }

    @PostMapping(value = "/diaryDetailEdit")
    public ResponseInfo diaryDetailEdit(@RequestParam Integer userId,
                                        @RequestParam Long detailId,
                                        @RequestParam Long noteId,
                                        @RequestParam String content,
                                        @RequestParam String picUrls) throws
            BusinessException {
        checkUser(userId);
        Note note = noteService.getNoteByNoteId(noteId);
        if (note == null) {
            //若note为空，则不写入明细表
            throw new BusinessException(ResultEnum.BUSINESS_NOTE_NOT_FOUND);
        }

        NoteDetail noteDetail = noteService.getNoteDetailByDetailId(detailId);
        // 删除oss中的对应图片们
        List<String> objectKeys = ToolUtil.getStringList(noteDetail.getPicUrls(), ";");
        List<String> newPicUrls = ToolUtil.getStringList(picUrls, ";");
        if (objectKeys != null && newPicUrls != null && newPicUrls.size() > 0) {
            ArrayList<String> deleteKeys = new ArrayList<String>();//要删除的图片key
            for (int i = 0; i<objectKeys.size();i++){
                if (!newPicUrls.contains(objectKeys.get(i))){//如果新的图片中没有，则可以删除
                    deleteKeys.add(objectKeys.get(i));
                }
            }
            ossService.deleteObjectByKeys(deleteKeys);
        }
        // 设置新的图片地址们
        note.setPicUrls(picUrls);
        noteService.saveNote(note);
        noteService.detailEdit(detailId, content, picUrls);
        return ResponseUtil.success(null);
    }

    @PostMapping(value = "/noteTitleEdit")
    public ResponseInfo noteTitleEdit(@RequestParam Integer userId,
                                      @RequestParam Long noteId, @RequestParam
                                              String noteTitle) throws
            BusinessException {
        checkUser(userId);
        noteService.noteTitleEdit(userId, noteId, noteTitle);
        return ResponseUtil.success(null);
    }

    @PostMapping(value = "/notePermissionChange")
    public ResponseInfo notePermissionChange(@RequestParam Integer userId,
                                             @RequestParam Long noteId, @RequestParam
                                                     int permission) throws
            BusinessException {
        checkUser(userId);
        noteService.notePermissionChange(userId, noteId, permission);
        return ResponseUtil.success(null);
    }

    @PostMapping(value = "/noteLike")
    public ResponseInfo noteLike(@RequestParam Integer userId,
                                 @RequestParam Long noteId, @RequestParam
                                         int isLikey) throws
            BusinessException {
        checkUser(userId);
        if (noteId == null) {
            throw new BusinessException(ResultEnum.BUSINESS_NOTE_NOT_FOUND);
        }
        noteService.noteLike(userId, noteId, isLikey);
        return ResponseUtil.success(null);
    }


    @PostMapping(value = "/deleteNoteDetail")
    public ResponseInfo deleteNoteDetail(@RequestParam Integer userId,
                                         @RequestParam Long detailId,
                                         @RequestParam Long noteId) throws
            BusinessException {
        checkUser(userId);
        noteService.deleteNoteDetail(detailId, noteId);
        return ResponseUtil.success(null);
    }

    @PostMapping(value = "/deleteNote")
    public ResponseInfo deleteNote(@RequestParam Integer userId,
                                   @RequestParam Long noteId) throws
            BusinessException {
        checkUser(userId);
        noteService.deleteNote(noteId);
        return ResponseUtil.success(null);
    }

    @PostMapping(value = "/getNoteList")
    public ResponseInfo getNoteListByUserIdAndNoteType(@RequestParam Integer userId,
                                                       @RequestParam Integer
                                                               noteType,
                                                       @RequestParam(required =
                                                               false) Long
                                                               updateTime,
                                                       @PageableDefault(page = 0,
                                                               size = 1) Pageable
                                                               pageable)
            throws BusinessException {

        if (updateTime == null) {
            updateTime = System.currentTimeMillis();
        }
        if (pageable.getPageSize() == 1) { // 如果不传size，就取全部
            pageable = null;
        }
        List<NoteDTO> list = noteService.getNoteListByUserIdAndNoteType(userId,
                noteType, updateTime, pageable);
        return ResponseUtil.success(list, pageable);
    }

    @PostMapping(value = "/getMoments")
    public ResponseInfo getMoments(@RequestParam Integer loginUserId,
                                   @RequestParam(required = false) Long updateTime,
                                   @PageableDefault(page = 0, size = 1) Pageable
                                           pageable) {

        if (updateTime == null) {
            updateTime = System.currentTimeMillis();
        }
        if (pageable.getPageSize() == 1) { // 如果不传size，就取全部
            pageable = null;
        }

        List<NoteDTO> list = noteService.getMoments(loginUserId, updateTime, pageable);
        return ResponseUtil.success(list, pageable);
    }


    @PostMapping(value = "/getUserMoments")
    public ResponseInfo getUserMoments(@RequestParam Integer loginUserId,
                                   @RequestParam Integer userId,
                                   @RequestParam(required = false) Long updateTime,
                                   @PageableDefault(page = 0, size = 1) Pageable
                                           pageable) {

        if (updateTime == null) {
            updateTime = System.currentTimeMillis();
        }
        if (pageable.getPageSize() == 1) { // 如果不传size，就取全部
            pageable = null;
        }

        List<NoteDTO> list = noteService.getUserMoments(loginUserId, userId, updateTime
                , pageable);
        return ResponseUtil.success(list, pageable);
    }


    @PostMapping(value = "/getDiaryDetails")
    public ResponseInfo getDiaryDetails(@RequestParam Long noteId,
                                        @RequestParam(required = false) Long
                                                createTime,
                                        @PageableDefault(page = 0, size = 1000) Pageable
                                                pageable) {
        List<NoteDetail> diarys = noteService.getDetails(noteId, createTime,
                pageable);
        return ResponseUtil.success(diarys, pageable);
    }
}
