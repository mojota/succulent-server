package com.mojota.succulent.dao;

import com.mojota.succulent.entity.NoteDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author jamie
 * @date 18-9-6
 */
public interface NoteDetailRepository extends JpaRepository<NoteDetail, Integer> {

    @Transactional
    @Modifying
    @Query(value = "update NoteDetail set content=:content, strPicUrls = " +
            ":strPicUrls where detailId = :detailId")
    int updateDetailByDetailId(@Param("detailId") Integer detailId, @Param
            ("content") String content, @Param("strPicUrls") String strPicUrls);

    void deleteByNoteId(Long noteId);
}
