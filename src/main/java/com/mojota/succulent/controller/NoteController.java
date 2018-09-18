package com.mojota.succulent.controller;

import com.mojota.succulent.dto.NoteDTO;
import com.mojota.succulent.dto.ResponseInfo;
import com.mojota.succulent.entity.Note;
import com.mojota.succulent.entity.NoteDetail;
import com.mojota.succulent.service.NoteService;
import com.mojota.succulent.utils.BusinessException;
import com.mojota.succulent.utils.CodeConstants;
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
 * @date 18-9-6
 */
@RestController
@RequestMapping("/note")
public class NoteController {

    @Autowired
    private NoteService noteService;

    private void checkUser(Integer userId) throws BusinessException {
        // userId不可为空
        if (userId == null || userId == 0) {
            throw new BusinessException(CodeConstants.CODE_BUSINESS_ERROR,
                    CodeConstants.MSG_BUSINESS_ERROR_USER_NOT_LOGIN);
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
        noteService.noteAdd(note);

        return ResponseUtil.success(null);
    }


    @PostMapping(value = "/diaryAdd")
    public ResponseInfo diaryAdd(@RequestParam Integer userId, @RequestParam String
            noteTitle, @RequestParam String content, @RequestParam int noteType,
                                 @RequestParam String picUrls) throws
            BusinessException {
        checkUser(userId);

        long time = System.currentTimeMillis();
        Note note = new Note();
        note.setUserId(userId);
        note.setNoteTitle(noteTitle);
        note.setNoteType(noteType);
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


    @PostMapping(value = "/diaryDetailAdd")
    public ResponseInfo diaryDetailAdd(@RequestParam Integer userId, @RequestParam
            Long noteId, @RequestParam String content, @RequestParam String
                                               picUrls) throws BusinessException {
        checkUser(userId);

        long time = System.currentTimeMillis();
        Note note = noteService.getNoteByNoteId(noteId);
        if (note == null) {
            //若note为空，则不写入明细表
            throw new BusinessException(CodeConstants.CODE_BUSINESS_ERROR,
                    CodeConstants.MSG_BUSINESS_NOTE_NOT_FOUND);
        }
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
                                        @RequestParam Long detailId, @RequestParam
                                                String content, @RequestParam
                                                String picUrls) throws
            BusinessException {
        checkUser(userId);

        noteService.detailEdit(detailId, content, picUrls);
        return ResponseUtil.success(null);
    }

    @PostMapping(value = "/noteTitleEdit")
    public ResponseInfo noteTitleEdit(@RequestParam Integer userId,
                                      @RequestParam Long noteId, @RequestParam
                                              String noteTitle) throws
            BusinessException {
        checkUser(userId);
        noteService.noteTitleEdit(noteId, noteTitle);
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
            throw new BusinessException(CodeConstants.CODE_BUSINESS_ERROR,
                    CodeConstants.MSG_BUSINESS_NOTE_NOT_FOUND);
        }
        noteService.noteLike(userId, noteId, isLikey);
        return ResponseUtil.success(null);
    }


    @PostMapping(value = "/deleteNoteDetail")
    public ResponseInfo deleteNoteDetail(@RequestParam Integer userId,
                                         @RequestParam Long detailId) throws
            BusinessException {
        checkUser(userId);
        noteService.deleteNoteDetail(detailId);
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
        checkUser(userId);

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
    public ResponseInfo getMoments(@RequestParam Integer userId,
                                   @RequestParam(required = false) Long updateTime,
                                   @PageableDefault(page = 0, size = 1) Pageable
                                           pageable) throws BusinessException {

        if (updateTime == null) {
            updateTime = System.currentTimeMillis();
        }
        if (pageable.getPageSize() == 1) { // 如果不传size，就取全部
            pageable = null;
        }

        List<NoteDTO> list = noteService.getMoments(userId, updateTime, pageable);
        return ResponseUtil.success(list, pageable);
    }

    @PostMapping(value = "/getDiaryDetails")
    public ResponseInfo getDiaryDetails(@RequestParam Long noteId,
                                        @RequestParam(required = false) Long
                                                createTime,
                                        @PageableDefault(page = 0, size = 1) Pageable
                                                pageable) {
        if (createTime == null) {
            createTime = System.currentTimeMillis();
        }
        if (pageable.getPageSize() == 1) { // 如果不传size，就取全部
            pageable = null;
        }
        List<NoteDetail> diarys = noteService.getDetails(noteId, createTime,
                pageable);
        return ResponseUtil.success(diarys, pageable);
    }
}
