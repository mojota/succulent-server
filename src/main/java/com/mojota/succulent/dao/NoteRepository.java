package com.mojota.succulent.dao;

import com.mojota.succulent.dto.NoteDTO;
import com.mojota.succulent.entity.Note;
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
public interface NoteRepository extends JpaRepository<Note, Long> {

    @Transactional
    @Modifying
    @Query(value = "update Note set updateTime = :updateTime where noteId = :noteId")
    int updateUpdateTimeByNoteId(@Param("updateTime") long updateTime, @Param
            ("noteId") Long noteId);

    @Transactional
    @Modifying
    @Query(value = "update Note set noteTitle = :noteTitle where noteId = :noteId and " +
            "userId = :userId")
    int updateNoteTitleByNoteId(@Param("noteTitle") String noteTitle, @Param
            ("noteId") Long noteId, @Param("userId") Integer userId);

    @Transactional
    @Modifying
    @Query(value = "update Note set permission = ?1 where noteId = ?2 and userId =" +
            " ?3")
    int updatePermissionByNoteIdAndUserId(int permission, Long noteId, Integer
            userId);

    Note findNoteByNoteId(Long noteId);

//    List<Note> findNotesByUserId(Integer userId);

    @Query("select n.noteId as noteId, n.noteTitle as noteTitle, n.updateTime as " +
            "updateTime,n.permission as permission,n.likeyCount as likeyCount" +
            ",n.noteType as noteType,n.picUrls as picUrls,no.isLikey as " +
            "isLikey,u as userInfo from Note n inner join User u on n.userId = u" +
            ".userId left join NoteOperate no on n.noteId=no.noteId and n" +
            ".userId=no.userId where n.userId = ?1 and n.noteType = ?2 and n" +
            ".updateTime <?3 order by n.updateTime desc")
    List<NoteDTO> findNoteDtos(Integer userId, Integer noteType, Long updateTime,
                               Pageable pageable);

    @Query("select n.noteId as noteId, n.noteTitle as noteTitle, n.updateTime as " +
            "updateTime,n.permission as permission,n.likeyCount as likeyCount" +
            ",n.noteType as noteType,n.picUrls as picUrls,no.isLikey as " +
            "isLikey,u as userInfo from Note n inner join User u on n.userId = u" +
            ".userId left join NoteOperate no on n.noteId=no.noteId and no.userId " +
            "= ?1 where n.permission = 1 and n.updateTime <?2 order by n" +
            ".updateTime desc")
    List<NoteDTO> findMoments(Integer loginUserId, Long updateTime, Pageable pageable);

    @Query("select n.noteId as noteId, n.noteTitle as noteTitle, n.updateTime as " +
            "updateTime,n.permission as permission,n.likeyCount as likeyCount" +
            ",n.noteType as noteType,n.picUrls as picUrls,no.isLikey as " +
            "isLikey,u as userInfo from Note n inner join User u on n.userId = u" +
            ".userId left join NoteOperate no on n.noteId=no.noteId and no.userId " +
            "= ?1 where n.permission = 1 and n.userId = ?2 and n.updateTime <?3 order by n" +
            ".updateTime desc")
    List<NoteDTO> findUserMoments(Integer loginUserId, Integer userId, Long updateTime,
                                  Pageable pageable);

}
