package com.mojota.succulent.service;

import com.mojota.succulent.dao.NoteDetailRepository;
import com.mojota.succulent.dao.NoteOperateRepository;
import com.mojota.succulent.dao.NoteRepository;
import com.mojota.succulent.dto.NoteDTO;
import com.mojota.succulent.entity.Note;
import com.mojota.succulent.entity.NoteDetail;
import com.mojota.succulent.entity.NoteOperate;
import com.mojota.succulent.utils.BusinessException;
import com.mojota.succulent.utils.ResultEnum;
import com.mojota.succulent.utils.ToolUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
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

    @Autowired
    private OssService OssService;

    /**
     * 添加笔记
     */

    public void saveNote(Note note) throws BusinessException {
        noteRepository.saveAndFlush(note);
    }

    /**
     * 添加笔记明细，更新note表时间
     */
    public void detailAdd(NoteDetail noteDetail) throws BusinessException {
        if (StringUtils.isEmpty(noteDetail.getContent()) && StringUtils.isEmpty
                (noteDetail.getPicUrls())) {//若内容为空，则不写入明细表
            throw new BusinessException(ResultEnum.BUSINESS_DATA_EMPTY);
        }
        noteDetailRepository.save(noteDetail);
    }

    /**
     * 编辑笔记明细
     */
    public void detailEdit(Long detailId, String content, String picUrls)
            throws BusinessException {
        if (detailId == null) {
            throw new BusinessException(ResultEnum.BUSINESS_DATA_NOT_FOUND);
        }
        if (noteDetailRepository.updateDetailByDetailId(detailId, content,
                picUrls) <= 0) {
            throw new BusinessException(ResultEnum.BUSINESS_DATA_NOT_FOUND);
        }
    }

    /**
     * 编辑笔记标题
     */
    public void noteTitleEdit(Long noteId, String noteTitle) throws
            BusinessException {
        if (noteId == null) {
            throw new BusinessException(ResultEnum.BUSINESS_NOTE_NOT_FOUND);
        }
        if (StringUtils.isEmpty(noteTitle)) {
            throw new BusinessException(ResultEnum.BUSINESS_DATA_EMPTY);
        }
        if (noteRepository.updateNoteTitleByNoteId(noteTitle, noteId) <= 0) {
            throw new BusinessException(ResultEnum.BUSINESS_DATA_NOT_FOUND);
        }
    }

    /**
     * 更改笔记权限
     */
    public void notePermissionChange(Integer userId, Long noteId, int permission)
            throws BusinessException {
        if (noteId == null) {
            throw new BusinessException(ResultEnum.BUSINESS_NOTE_NOT_FOUND);
        }
        if (noteRepository.updatePermissionByNoteIdAndUserId(permission, noteId,
                userId) <= 0) {
            throw new BusinessException(ResultEnum.BUSINESS_DATA_NOT_FOUND);
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
            throw new BusinessException(ResultEnum.BUSINESS_NOTE_NOT_FOUND);
        }
        int newCount = noteOperateRepository.countByNoteIdAndIsLikey(noteId, 1);
        note.setLikeyCount(newCount);
        noteRepository.saveAndFlush(note);
    }

    /**
     * 删除笔记条目
     *
     */
    public void deleteNoteDetail(Long detailId, Long noteId) throws BusinessException {
        if (detailId == null) {
            throw new BusinessException(ResultEnum.BUSINESS_DATA_NOT_FOUND);
        }
        List<String> objectKeys = null;
        NoteDetail noteDetail = noteDetailRepository.findByDetailId(detailId);
        if (noteDetail != null) {
            // 先获取图片key
            if (!StringUtils.isEmpty(noteDetail.getPicUrls())) {
                objectKeys = ToolUtil.getStringList(noteDetail.getPicUrls(), ";");
            }
            // 再删除
            noteDetailRepository.deleteById(detailId);
        }
        // 以下即使出错也不回滚了,没什么必要
        // 删除oss中的对应图片们
        OssService.deleteObjectByKeys(objectKeys);

        // 将最新item的图片url更新到note表中
        List<NoteDetail> details = getDetails(noteId, null, new PageRequest(0, 1));
        if (details != null && details.size()>0) {
            String lastPicUrls = details.get(0).getPicUrls();

            Note note = getNoteByNoteId(noteId);
            if (note == null) {
                return;
            }
            note.setPicUrls(lastPicUrls);
            noteRepository.saveAndFlush(note);
        }
    }

    /**
     * 删除笔记
     */
    @Transactional(rollbackOn = Exception.class)
    public void deleteNote(Long noteId) throws BusinessException {
        if (noteId == null) {
            throw new BusinessException(ResultEnum.BUSINESS_NOTE_NOT_FOUND);
        }

        // 先获取子表中所有图片key
        List<String> objectKeys = new ArrayList<String>();
        List<NoteDetail> noteDetails = noteDetailRepository.findByNote_NoteId(noteId);
        for (NoteDetail noteDetail :noteDetails) {
            if (!StringUtils.isEmpty(noteDetail.getPicUrls())) {
                List<String> picUrls = ToolUtil.getStringList(noteDetail.getPicUrls(), ";");
                objectKeys.addAll(picUrls);
            }
        }
        // note表未做onetomany关联，故这里并别删除
        noteDetailRepository.deleteByNote_NoteId(noteId); // 先删子表
        noteRepository.deleteById(noteId);// 再删主表
        noteOperateRepository.deleteByNoteId(noteId); // 删赞相关记录
        // 删除oss中的对应图片们
        OssService.deleteObjectByKeys(objectKeys);
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
        if (createTime == null) {
            createTime = System.currentTimeMillis();
        }
        if (pageable.getPageSize() == 1000) { // 如果不传size，就取全部,1000为controller设置的默认值
            pageable = null;
        }
        return noteDetailRepository
                .findByCreateTimeBeforeAndNote_NoteIdOrderByCreateTimeDesc
                        (createTime, noteId, pageable);
    }
}
