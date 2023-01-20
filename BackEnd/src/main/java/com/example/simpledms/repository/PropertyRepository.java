package com.example.simpledms.repository;

import com.example.simpledms.model.Property;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * packageName : com.example.jpaexam.repository
 * fileName : FileDbRepository
 * author : juhee
 * date : 2022-11-01
 * description : JPA의 CRUD를 위한 인터페이스(JPA 매커니즘의 핵심!!)
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * —————————————————————————————
 * 2022-10-20         juhee          최초 생성
 */
@Repository
public interface PropertyRepository extends JpaRepository<Property, Integer> {
    // 🎃이름(propertyTitle)로 조회하는 like 검색 함수
    Page<Property> findAllByPropertyTitleContaining(String propertyTitle, Pageable pageable);

    Page<Property> findAllByPropertyPriceContaining(String propertyPrice, Pageable pageable);

    Page<Property> findAllByPropertyCityContaining(String propertyCity, Pageable pageable);


//리파지토리 -> 서비스 -> 컨트롤러

}



