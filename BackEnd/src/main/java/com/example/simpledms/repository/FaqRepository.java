package com.example.simpledms.repository;

import com.example.simpledms.model.Faq;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * packageName : com.example.jpaexam.repository
 * fileName : FaqRepository
 * author : juhee
 * date : 2022-11-01
 * description : JPA의 CRUD를 위한 인터페이스(JPA 매커니즘의 핵심!!)
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * —————————————————————————————
 * 2022-10-20         juhee          최초 생성
 */
// @Repository : 서버가 기동될 떄 객체를 자동으로 생성해 주는 어노테이션(@Service, @Component, @Repository)
// extends JpaRepository<모델(엔터티)명, @ID 붙은 곳의 속성자료형(객체형태)> : JPA 인터페이스를 상속받아야 CRUD 함수 사용 가능
//                                     model폴더의 Faq클래스의 기본키인 @ID 속성
@Repository
public interface FaqRepository extends JpaRepository<Faq, Integer> {
    // 🎃글제목(title)으로 조회하는 like 검색 함수
    // 프엔, FaqDataService.js 에서 쿼리스트링 방식으로 url 보냈지만
    // 아래에서는 함수, 어떤 방식으로 정의하든 상관없음
    // 쿼리스트링 방식 url : ?변수명=값&변수명2=값2...

    // 아래, 쿼리메소드 방식으로 함수 정의
    // title로 like 검색 => TitleContaining
//    List<Faq> findAllByTitleContaining(String title);
    // 페이징 처리 추가 (Pageable)
    Page<Faq> findAllByTitleContaining(String title, Pageable pageable);


//리파지토리 -> 서비스 -> 컨트롤러

}



