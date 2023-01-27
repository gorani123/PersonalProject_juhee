package com.example.simpledms.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

/**
 * packageName : com.example.jpaexam.model
 * fileName : FAQ
 * author : juhee
 * date : 2022-11-01
 * description : 부서 모델 클래스
 *                      JPA에서는 모델을 Entity(엔티티)라고 부름 (@Entity 어노테이션 사용하기 때문)
 * 😦
 * JPA_ 간단한 프로젝트시 아주 빠름(복잡한건오류나서잘안됨).         Mybatis는 복잡한 프로젝트시 편리
 * JPA 메커니즘 : 클래스를 대상으로 (속성대로) 테이블 자동 생성
 *                                                      비교) Mybatis : 개발자가 직접 자바소스에 sql문 작성
 *     기본 제공하는 함수들은 자동으로 CRUD sql문을 만들어 줌
 *     sql문 작성에 필요한 노력을 절약할 수 있음
 *     (개발자는 제공되는 함수만 호출하면 됨. sql문은 JPA 라이브러리가 알아서 생성함)
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * —————————————————————————————
 * 2022-10-19         juhee          최초 생성
 */
// delete : soft delete(법정 의무보관기간을 위해 실제 데이터를 삭제하지 않음) / hard delete(완전 삭제)
// sofe delete : 삭제하는 척만 하기. 화면에서는 삭제한 것 처럼 데이터가 안 보이지만 db에서는 데이터를 삭제하지 않음(Y=삭제됨 N=삭제안됨)
//    사용법 1) @SQLDelete(sql="update문") : Delete문이 실행되지 않고 매개변수의 update문이 실행됨
//    사용법 2) @Where(clause="강제조건") : 대상 클래스(현재 엔티티)에서 sql문 실행 시 강제조건이 붙어 실행됨(DELETE_YN='N' 인 것만 검색)

//    no -> no / dname -> title / loc -> content

@Entity
@Table(name="TB_FAQ")      // 테이블명 ***
@SequenceGenerator(                 //👀 아래 @GenerateValue()에서 써준거 세부적으로 써줘야 함
        name= "SQ_FAQ_GENERATOR",
        sequenceName = "SQ_FAQ",
        initialValue = 1,
        allocationSize = 1
)
@Getter // 롬북
@Setter // 롬북
@NoArgsConstructor
// SQL문 자동생성시 null 컬럼데이터는 제외시키는 어노테이션: @DynamicInsert @DynamicUpdate
@DynamicInsert
@DynamicUpdate
// @Where(clause="DELETE_YN = 'N'") : 이 엔티티에 접근하는 모든 조회들(sql)은 이 조건(DELETE_YN='N')인 것만 검색
@Where(clause="DELETE_YN = 'N'")
// sofe delete : 삭제하는 척만 하기. 화면에서는 삭제한 것 처럼 데이터가 안 보이지만 db에서는 데이터를 삭제하지 않음
@SQLDelete(sql="UPDATE TB_FAQ SET DELETE_YN = 'Y', DELETE_TIME = TO_CHAR(SYSDATE, 'YYYY-MM-DD HH24:MI:SS') WHERE NO = ?")
public class Faq extends BaseTimeEntity {
    // 속성
    // @Id : 기본키(PrimaryKey). not null. 유일해야 함 (하나는 필수. 없으면 클래스명(Faq)에 빨간줄 뜸)
    // @GenerateValue(..SEQUENCE, ...) :오라클은 시퀀스 사용함 (시퀀스 사용_ORACLE, POSTGRE 등/ increment 사용_MYSQL, MARIA DB 등)
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_FAQ_GENERATOR") //👀
    private Integer no;     // 게시글 번호(no)

    @Column(columnDefinition = "VARCHAR2(255)")
    private String title;    // 게시글 이름(title)

    @Column(columnDefinition = "VARCHAR2(255)")
    private String content;    // 게시글 내용(content)

}
