package com.mojota.succulent.dao;

import com.mojota.succulent.entity.Note;
import com.mojota.succulent.entity.NoteOperate;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author jamie
 * @date 18-9-6
 */
public interface NoteOperateRepository extends JpaRepository<NoteOperate, Integer> {

}
