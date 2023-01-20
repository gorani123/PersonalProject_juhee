package com.example.simpledms.service;

import com.example.simpledms.model.Qna;
import com.example.simpledms.repository.QnaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * packageName : com.example.simpledms.service
 * fileName : QnaService
 * author : juhee
 * date : 2022-11-09
 * description : Qna 서비스
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * —————————————————————————————
 * 2022-11-09         juhee          최초 생성
 *
 */@Service
public class QnaService {

     @Autowired
    QnaRepository qnaRepository;    // JPA CRUD 함수가 있는 인터페이스 객체


    // 전체 조회 함수 : findAll() -> 변경) 페이징 처리
    // qnarepository. (점) 하면 JPA의 기본함수들 불러서 쓸 수 있음
    public Page<Qna> findAll(Pageable pageable){
        Page<Qna> page = qnaRepository.findAll(pageable);

        return page;
    }

    // qno 로 조회하는 함수 -----🤩
    // qnaRepository.findById(기본키) (기본함수 findById()의 매개변수에는 기본키 속성만 들어올 수 있음!!)
    public Optional<Qna> findById(Integer qno) {
        Optional<Qna> optionalQna = qnaRepository.findById(qno);

        return optionalQna;
    }

    // Qna 정보 저장/수정 함수 -----🤡 😨
    public Qna save(Qna qna) {
        Qna qna2 = qnaRepository.save(qna);
        return qna2;
    }

    // Qno 로 삭제하는 함수 -------😤
    public boolean removeById(int qno) {
        // existsById(기본키) 있으면 삭제 실행 + true 리턴
        if (qnaRepository.existsById(qno) == true) {
            qnaRepository.deleteById(qno);
            return true;
        }
        // 없으면 그냥 false 리턴
        return false;
    }

    // Qna 제목인 Subject으로 like 검색 : findAllBySubjectContaining() => 페이징 처리 추가 -------🎃
    public Page<Qna> findAllBySubjectContaining(String subject, Pageable pageable) {
        Page<Qna> page = qnaRepository.findAllBySubjectContaining(subject, pageable);

        return page;
    }

    // Qna 작성자인 Name으로 like 검색 : findAllByNameContaining() => 페이징 처리 추가 -------🎃
    public Page<Qna> findAllByNameContaining(String message, Pageable pageable) {
        Page<Qna> page = qnaRepository.findAllByNameContaining(message, pageable);

        return page;
    }

}
