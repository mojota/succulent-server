package com.mojota.succulent.dao;

import com.mojota.succulent.entity.NoteOperate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author jamie
 * @date 18-9-6
 */
public interface NoteOperateRepository extends JpaRepository<NoteOperate, Integer> {

//    @Transactional
//    @Modifying
//    @Query(value = "update NoteOperate set isLike = ?1 where userId = ?2 and " +
//            "noteId = ?3")
//    int updateIsLikeByNoteId(int isLike, Integer userId, Long noteId);

    NoteOperate findNoteOperateByUserIdAndNoteId(Integer userId, Long noteId);
}
