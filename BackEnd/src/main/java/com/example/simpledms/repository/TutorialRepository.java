package com.example.simpledms.repository;

import com.example.simpledms.model.FileDb;
import com.example.simpledms.model.Tutorial;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * packageName : com.example.jpaexam.repository
 * fileName : FileDbRepository
 * author : juhee
 * date : 2022-11-01
 * description :
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * —————————————————————————————
 * 2022-10-20         juhee          최초 생성
 */
@Repository
public interface TutorialRepository extends JpaRepository<Tutorial, Integer> {

}



