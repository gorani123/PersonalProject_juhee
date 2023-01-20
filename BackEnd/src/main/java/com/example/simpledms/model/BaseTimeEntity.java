package com.example.simpledms.model;

import lombok.Getter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * packageName : com.example.jpaexam.model
 * fileName : BaseTimeEntity
 * author : juhee
 * date : 2022-11-01
 * description : 공통 모델(엔티티) 인터페이스
 *              @MappedSuperclass 어노테이션을 이용한 클래스 _JPA 에서 자동으로 생성일자/수정일자 만들어주는 어노테이션
 *              포맷 : yyyy-MM-dd HH:mm:ss
 *              추상클래스(abstract)로 만들어 자식클에게 상속시킬거임(model > Dept, Emp)
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * —————————————————————————————
 * 2022-10-20         juhee          최초 생성
 */
@Getter
//@MappedSuperclass : 모델을 감시하다가 생성일자/ 수정일자를 자동으로 포함,업뎃시키는 어노테이션
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseTimeEntity {
    
    // 생성일자 속성
    private String insertTime;

    // 수정일자 속성
    private String updateTime;

    // soft delete 위한 속성 2개 추가
    private String deleteYn;
    private String deleteTime;
    
    ///////////////////////////////////////추가?????????????????????????
//    private String reservationYn;


    // 감시 함수
    // 대상 모델(엔터티)을 저장하기(insert) 전에 실행되는 함수
    @PrePersist
    void onPrePersist() {
        // 포맷 지정 : LocalDateTime.현재날짜now().포맷타입format(DateTimeFormatter.ofPattern("포맷"));
        this.insertTime = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    // 대상 모델(엔터티)을 수정하기(update) 전에 실행되는 함수
    @PreUpdate
    void  onPreUpdate() {
        this.updateTime = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        this.insertTime = this.updateTime;  // 저장시간을 수정시간으로 업뎃
    }

}
