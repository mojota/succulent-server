package com.mojota.succulent.service;

import com.mojota.succulent.dao.NoteDetailRepository;
import com.mojota.succulent.dao.NoteRepository;
import com.mojota.succulent.entity.Note;
import com.mojota.succulent.entity.NoteDetail;
import com.mojota.succulent.utils.BusinessException;
import com.mojota.succulent.utils.CodeConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;

/**
 * @author jamie
 * @date 18-9-6
 */
@Service
public class NoteService {

    @Autowired
    NoteRepository noteRepository;

    @Autowired
    NoteDetailRepository noteDetailRepository;

    /**
     * 添加笔记
     */

    @Transactional(rollbackOn = Exception.class)
    public void noteAdd(Note note, String content) throws BusinessException {
        Note resultNote = noteRepository.save(note);

        NoteDetail noteDetail = new NoteDetail();
        noteDetail.setNoteId(resultNote.getNoteId());
        noteDetail.setContent(content);
        noteDetail.setStrPicUrls(resultNote.getStrPicUrls());
        noteDetail.setCreateTime(resultNote.getUpdateTime());
        detailAdd(noteDetail);
    }

    /**
     * 添加笔记明细，更新note表时间
     */
    public void detailAdd(NoteDetail noteDetail) throws BusinessException {
        if (noteDetail.getNoteId() == null || noteDetail.getNoteId() == 0) {
            //若noteId为空，则不写入明细表
            throw new BusinessException(CodeConstants.CODE_BUSINESS_ERROR,
                    CodeConstants.MSG_BUSINESS_NOTE_NOT_FOUND);
        }
        if (StringUtils.isEmpty(noteDetail.getContent()) && StringUtils.isEmpty
                (noteDetail.getStrPicUrls())) {//若内容为空，则不写入明细表
            throw new BusinessException(CodeConstants.CODE_BUSINESS_ERROR,
                    CodeConstants.MSG_BUSINESS_DATA_EMPTY);
        }
        noteDetailRepository.save(noteDetail);
        noteRepository.updateUpdateTimeByNoteId(noteDetail.getCreateTime(),
                noteDetail.getNoteId());
    }

    /**
     * 编辑笔记明细
     */
    public void detailEdit(Integer detailId, String content, String strPicUrls)
            throws BusinessException {
        if (detailId == null) {
            throw new BusinessException(CodeConstants.CODE_BUSINESS_ERROR,
                    CodeConstants.MSG_BUSINESS_NOTE_DETAIL_NOT_FOUND);
        }
        if (noteDetailRepository.updateDetailByDetailId(detailId, content,
                strPicUrls) <= 0) {
            throw new BusinessException(CodeConstants.CODE_BUSINESS_ERROR,
                    CodeConstants.MSG_BUSINESS_DATA_NOT_FOUND);
        }
    }

    /**
     * 编辑笔记标题
     */
    public void noteTitleEdit(Long noteId, String noteTitle) throws
            BusinessException {
        if (noteId == null) {
            throw new BusinessException(CodeConstants.CODE_BUSINESS_ERROR,
                    CodeConstants.MSG_BUSINESS_NOTE_NOT_FOUND);
        }
        if (StringUtils.isEmpty(noteTitle)) {
            throw new BusinessException(CodeConstants.CODE_BUSINESS_ERROR,
                    CodeConstants.MSG_BUSINESS_DATA_EMPTY);
        }
        if (noteRepository.updateNoteTitleByNoteId(noteTitle, noteId) <= 0) {
            throw new BusinessException(CodeConstants.CODE_BUSINESS_ERROR,
                    CodeConstants.MSG_BUSINESS_DATA_NOT_FOUND);
        }
    }

    /**
     * 更改笔记权限
     */
    public void notePermissionChange(Long noteId, int permission) throws
            BusinessException {
        if (noteId == null) {
            throw new BusinessException(CodeConstants.CODE_BUSINESS_ERROR,
                    CodeConstants.MSG_BUSINESS_NOTE_NOT_FOUND);
        }
        if (noteRepository.updatePermissionByNoteId(noteId, permission) <= 0) {
            throw new BusinessException(CodeConstants.CODE_BUSINESS_ERROR,
                    CodeConstants.MSG_BUSINESS_DATA_NOT_FOUND);
        }
    }

    /**
     * 删除笔记条目
     */
    public void deleteNoteDetail(Integer detailId) throws BusinessException {
        if (detailId == null) {
            throw new BusinessException(CodeConstants.CODE_BUSINESS_ERROR,
                    CodeConstants.MSG_BUSINESS_NOTE_DETAIL_NOT_FOUND);
        }
        noteDetailRepository.deleteById(detailId);
    }

    /**
     * 删除笔记
     */
    @Transactional(rollbackOn = Exception.class)
    public void deleteNote(Long noteId) throws BusinessException {
        if (noteId == null) {
            throw new BusinessException(CodeConstants.CODE_BUSINESS_ERROR,
                    CodeConstants.MSG_BUSINESS_NOTE_NOT_FOUND);
        }
        noteRepository.deleteById(noteId);
        noteDetailRepository.deleteByNoteId(noteId);
    }
}
