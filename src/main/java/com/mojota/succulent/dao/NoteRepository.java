package com.mojota.succulent.dao;

import com.mojota.succulent.entity.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author jamie
 * @date 18-9-6
 */
public interface NoteRepository extends JpaRepository<Note, Long> {

    @Transactional
    @Modifying
    @Query(value = "update Note set updateTime = :updateTime where noteId = :noteId")
    int updateUpdateTimeByNoteId(@Param("updateTime") long updateTime, @Param
            ("noteId") Long noteId);

    @Transactional
    @Modifying
    @Query(value = "update Note set noteTitle = :noteTitle where noteId = :noteId")
    int updateNoteTitleByNoteId(@Param("noteTitle") String noteTitle, @Param
            ("noteId") Long noteId);

    @Transactional
    @Modifying
    @Query(value = "update Note set permission = :permission where noteId = :noteId")
    int updatePermissionByNoteId(Long noteId, int permission);
}
