package com.mojota.succulent.service;

import com.mojota.succulent.dao.NoteDetailRepository;
import com.mojota.succulent.dao.NoteOperateRepository;
import com.mojota.succulent.dao.NoteRepository;
import com.mojota.succulent.dto.NoteDTO;
import com.mojota.succulent.entity.Note;
import com.mojota.succulent.entity.NoteDetail;
import com.mojota.succulent.entity.NoteOperate;
import com.mojota.succulent.utils.BusinessException;
import com.mojota.succulent.utils.CodeConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @author jamie
 * @date 18-9-6
 */
@Service
public class NoteService {

    @Autowired
    private NoteRepository noteRepository;

    @Autowired
    private NoteDetailRepository noteDetailRepository;

    @Autowired
    private NoteOperateRepository noteOperateRepository;

    /**
     * 添加笔记
     */

    public void noteAdd(Note note) throws BusinessException {
        noteRepository.save(note);
    }

    /**
     * 添加笔记明细，更新note表时间
     */
    public void detailAdd(NoteDetail noteDetail) throws BusinessException {
        if (StringUtils.isEmpty(noteDetail.getContent()) && StringUtils.isEmpty
                (noteDetail.getPicUrls())) {//若内容为空，则不写入明细表
            throw new BusinessException(CodeConstants.CODE_BUSINESS_ERROR,
                    CodeConstants.MSG_BUSINESS_DATA_EMPTY);
        }
        noteDetailRepository.save(noteDetail);
    }

    /**
     * 编辑笔记明细
     */
    public void detailEdit(Long detailId, String content, String picUrls)
            throws BusinessException {
        if (detailId == null) {
            throw new BusinessException(CodeConstants.CODE_BUSINESS_ERROR,
                    CodeConstants.MSG_BUSINESS_NOTE_DETAIL_NOT_FOUND);
        }
        if (noteDetailRepository.updateDetailByDetailId(detailId, content,
                picUrls) <= 0) {
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
    public void notePermissionChange(Integer userId, Long noteId, int permission)
            throws BusinessException {
        if (noteId == null) {
            throw new BusinessException(CodeConstants.CODE_BUSINESS_ERROR,
                    CodeConstants.MSG_BUSINESS_NOTE_NOT_FOUND);
        }
        if (noteRepository.updatePermissionByNoteIdAndUserId(permission, noteId,
                userId) <= 0) {
            throw new BusinessException(CodeConstants.CODE_BUSINESS_ERROR,
                    CodeConstants.MSG_BUSINESS_DATA_NOT_FOUND);
        }
    }


    /**
     * 喜欢
     * 置状态并计数
     */
    @Transactional(rollbackOn = Exception.class)
    public void noteLike(Integer userId, Long noteId, int isLikey) throws
            BusinessException {
        NoteOperate noteOperate = noteOperateRepository
                .findNoteOperateByUserIdAndNoteId(userId, noteId);
        if (noteOperate != null) {
            noteOperate.setIsLikey(isLikey);
        } else {
            noteOperate = new NoteOperate();
            noteOperate.setUserId(userId);
            noteOperate.setNoteId(noteId);
            noteOperate.setIsLikey(isLikey);
        }
        noteOperateRepository.saveAndFlush(noteOperate);

        Note note = getNoteByNoteId(noteId);
        if (note == null) {
            throw new BusinessException(CodeConstants.CODE_BUSINESS_ERROR,
                    CodeConstants.MSG_BUSINESS_NOTE_NOT_FOUND);
        }
        int newCount = noteOperateRepository.countByNoteIdAndIsLikey(noteId, 1);
        note.setLikeyCount(newCount);
        noteRepository.saveAndFlush(note);
    }

    /**
     * 删除笔记条目
     */
    public void deleteNoteDetail(Long detailId) throws BusinessException {
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
        // note表未做onetomany关联，故这里并别删除
        noteDetailRepository.deleteByNoteId(noteId); // 先删子表
        noteRepository.deleteById(noteId);// 再删主表
    }

    public Note getNoteByNoteId(Long noteId) {
        return noteRepository.findNoteByNoteId(noteId);
    }

    /**
     * 按类型查询当前用户笔记
     */
    public List<NoteDTO> getNoteListByUserIdAndNoteType(Integer userId, Integer
            noteType, Long updateTime, Pageable pageable) {
        return noteRepository.findNoteDtos(userId, noteType, updateTime, pageable);
    }

    /**
     * 查询已公开的笔记
     */
    public List<NoteDTO> getMoments(Integer userId, Long updateTime, Pageable
            pageable) {
        return noteRepository.findMoments(userId, updateTime, pageable);
    }

    public List<NoteDetail> getDetails(Long noteId, Long createTime, Pageable
            pageable) {
        return noteDetailRepository
                .findByCreateTimeBeforeAndNote_NoteIdOrderByCreateTimeDesc
                        (createTime, noteId, pageable);
    }
}
