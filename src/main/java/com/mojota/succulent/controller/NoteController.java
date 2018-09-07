package com.mojota.succulent.controller;

import com.mojota.succulent.entity.Note;
import com.mojota.succulent.dto.ResponseInfo;
import com.mojota.succulent.entity.NoteDetail;
import com.mojota.succulent.service.NoteService;
import com.mojota.succulent.utils.BusinessException;
import com.mojota.succulent.utils.CodeConstants;
import com.mojota.succulent.utils.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


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

    @PostMapping(value = "/diaryAdd")
    public ResponseInfo diaryAdd(@RequestParam Integer userId, @RequestParam String
            noteTitle, @RequestParam String content, @RequestParam int noteType,
                                 @RequestParam String strPicUrls) throws
            BusinessException {
        checkUser(userId);

        Note note = new Note();
        note.setUserId(userId);
        note.setNoteTitle(noteTitle);
        note.setNoteType(noteType);
        note.setStrPicUrls(strPicUrls);
        note.setUpdateTime(System.currentTimeMillis());
        noteService.noteAdd(note, content);
        return ResponseUtil.success(null);
    }


    @PostMapping(value = "/diaryDetailAdd")
    public ResponseInfo diaryDetailAdd(@RequestParam Integer userId, @RequestParam
            Long noteId, @RequestParam String content, @RequestParam String
                                               strPicUrls) throws BusinessException {
        checkUser(userId);

        NoteDetail noteDetail = new NoteDetail();
        noteDetail.setNoteId(noteId);
        noteDetail.setContent(content);
        noteDetail.setStrPicUrls(strPicUrls);
        noteDetail.setCreateTime(System.currentTimeMillis());
        noteService.detailAdd(noteDetail);
        return ResponseUtil.success(null);
    }

    @PostMapping(value = "/diaryDetailEdit")
    public ResponseInfo diaryDetailEdit(@RequestParam Integer userId,
                                        @RequestParam Integer detailId, @RequestParam
                                                String content, @RequestParam
                                                String strPicUrls) throws
            BusinessException {
        checkUser(userId);

        noteService.detailEdit(detailId, content, strPicUrls);
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
        noteService.notePermissionChange(noteId, permission);
        return ResponseUtil.success(null);
    }

    @PostMapping(value = "/deleteNoteDetail")
    public ResponseInfo deleteNoteDetail(@RequestParam Integer userId,
                                         @RequestParam Integer detailId) throws
            BusinessException {
        checkUser(userId);
        noteService.deleteNoteDetail(detailId);
        return ResponseUtil.success(null);
    }

    @PostMapping(value = "/deleteNote")
    public ResponseInfo deleteNoteDetail(@RequestParam Integer userId,
                                         @RequestParam Long noteId) throws
            BusinessException {
        checkUser(userId);
        noteService.deleteNote(noteId);
        return ResponseUtil.success(null);
    }
}
