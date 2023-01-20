package com.example.simpledms.repository;

import com.example.simpledms.model.Qna;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * packageName : com.example.simpledms.repository
 * fileName : QnaRepository
 * author : juhee
 * date : 2022-11-09
 * description :
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * —————————————————————————————
 * 2022-11-09         juhee          최초 생성
 */
@Repository
public interface QnaRepository extends JpaRepository<Qna, Integer> {

    // 🎃 조회 like 검색 함수: qna 제목인 Subject, 작성자인 Messageer 로 검색하는 함수 총 두개
    Page<Qna> findAllBySubjectContaining(String subject, Pageable pageable);
    Page<Qna> findAllByNameContaining(String name, Pageable pageable);
}
