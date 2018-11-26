package com.mojota.succulent.dao;

import com.mojota.succulent.entity.NoteDetail;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author jamie
 * @date 18-9-6
 */
public interface NoteDetailRepository extends JpaRepository<NoteDetail, Long> {

    @Transactional
    @Modifying
    @Query(value = "update NoteDetail set content=:content, picUrls = " +
            ":picUrls where detailId = :detailId")
    int updateDetailByDetailId(@Param("detailId") Long detailId, @Param
            ("content") String content, @Param("picUrls") String picUrls);

    @Modifying
    void deleteByNote_NoteId(Long noteId);

    List<NoteDetail> findByCreateTimeBeforeAndNote_NoteIdOrderByCreateTimeDesc
            (Long createTime, Long noteId, Pageable pageable);

    List<NoteDetail> findByNote_NoteId(Long noteId);

    NoteDetail findByDetailId(Long detailId);


}
