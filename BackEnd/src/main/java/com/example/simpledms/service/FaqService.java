package com.example.simpledms.service;

import com.example.simpledms.model.Faq;
import com.example.simpledms.repository.FaqRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;


/**
 * packageName : com.example.jpaexam.service.exam01
 * fileName : DeptService
 * author : juhee
 * date : 2022-11-01
 * description : Faq 게시글 서비스 클래스
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * —————————————————————————————
 * 2022-10-20         juhee          최초 생성
 */
// @Service : 자동으로 객체 만들어주는 어노테이션
@Service
public class FaqService {
    // 그전에는 DAO를 받았음. 그게 repository로 변경됨
    // 객체 생성
    @Autowired
    FaqRepository faqRepository;  // JPA CRUD 함수가 있는 인터페이스 객체

    // 전체 조회 함수 : findAll()
    // faqRepository. (점) 하면 JPA의 기본함수들 불러서 쓸 수 있음
    public Page<Faq> findAll(Pageable pageable){
        Page<Faq> page = faqRepository.findAll(pageable);

        return page;
    }

    // 전체 삭제 함수 : removeAll()
    // faqRepository 에 사용자함수 만들 필요 없이 JPA 기본함수 deleteAll() 사용하면 됨
    public void removeAll() {
        faqRepository.deleteAll();
    }


    // 게시글 정보 저장/수정 함수 -----🤡 😨
    public Faq save(Faq faq) {
        Faq faq2 = faqRepository.save(faq);
        return faq2;
    }

    // 게시글 번호로 조회하는 함수 -----🤩
    // faqRepository.findById(기본키) (기본함수 findById()의 매개변수에는 기본키 속성만 들어올 수 있음!!)
    public Optional<Faq> findById(int no) {
        Optional<Faq> optionalFaq = faqRepository.findById(no);

        return optionalFaq;
    }

    // 수정하는 함수 -----😨
    // 위의 save 함수가 공용으로 처리


    // 글번호(no)로 삭제하는 함수 -------😤
    public boolean removeById(int no) {
        // existsById(기본키) 있으면 삭제 실행 + true 리턴
        if (faqRepository.existsById(no) == true) {
            faqRepository.deleteById(no);
            return true;
        }
        // 없으면 그냥 false 리턴
        return false;
    }


    // 게시글 이름(title)으로 like 검색 : findAllByTitleContaining() -------🎃
    public Page<Faq> findAllByTitleContaining(String title, Pageable pageable) {
        Page<Faq> page = faqRepository.findAllByTitleContaining(title, pageable);

        return page;
    }


}
