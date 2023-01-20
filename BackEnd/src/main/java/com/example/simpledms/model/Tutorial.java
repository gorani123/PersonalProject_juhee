package com.example.simpledms.model;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

/**
 * packageName : com.example.jpaexam.model
 * fileName : Dept
 * author : juhee
 * date : 2022-11-01
 * description : filedb 모델 클래스
 *                      JPA에서는 모델을 Entity(엔티티)라고 부름 (@Entity 어노테이션 사용하기 때문)
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * —————————————————————————————
 * 2022-10-19         juhee          최초 생성
 */
@Entity
@Table(name="TB_TUTORIAL")      // 테이블명 ***
@SequenceGenerator(                 //👀 아래 @GenerateValue()에서 써준거 세부적으로 써줘야 함
        name= "SQ_TUTORIAL_GENERATOR",
        sequenceName = "SQ_TUTORIAL",
        initialValue = 1,
        allocationSize = 1
)
@Getter
@Setter
@Builder
@NoArgsConstructor  // 생성자 자동 생성
@AllArgsConstructor // @Builder와 세트
@DynamicInsert
@DynamicUpdate
// @Where(clause="DELETE_YN = 'N'") : 이 엔티티에 접근하는 모든 조회들(sql)은 이 조건(DELETE_YN='N')인 것만 검색
@Where(clause="DELETE_YN = 'N'")
// sofe delete : 삭제하는 척만 하기. 화면에서는 삭제한 것 처럼 데이터가 안 보이지만 db에서는 데이터를 삭제하지 않음
@SQLDelete(sql="UPDATE TB_TUTORIAL SET DELETE_YN = 'Y', DELETE_TIME = TO_CHAR(SYSDATE, 'YYYY-MM-DD HH24:MI:SS') WHERE ID = ?")
public class Tutorial extends BaseTimeEntity {

//CREATE TABLE TB_TUTORIAL
//    ID                   NUMBER NOT NULL PRIMARY KEY,
//    TITLE                VARCHAR2(1000),
//    DESCRIPTION          VARCHAR2(1000),
//    PUBLISHED            VARCHAR2(1000),
//    DELETE_YN            VARCHAR2(1) DEFAULT 'N',
//    INSERT_TIME          VARCHAR2(255),
//    UPDATE_TIME          VARCHAR2(255),
//    DELETE_TIME          VARCHAR2(255)

    // 속성
    // @Id : 기본키(PrimaryKey). not null. 유일해야 함 (하나는 필수. 없으면 클래스명(Detp)에 빨간줄 뜸)
    // @GenerateValue(..SEQUENCE, ...) :오라클은 시퀀스 사용함 (시퀀스 사용_ORACLE, POSTGRE 등/ increment 사용_MYSQL, MARIA DB 등)

    // SHIFT + ALT + U : 카멜표기법으로 변경
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_TUTORIAL_GENERATOR") //👀
    private Integer id;     // 기본키 ID

    @Column
    private String title;

    @Column
    private String description;

    @Column
    private boolean published;


//    Title / Description / Published
//    이렇게 세 컬럼의 엑셀 파일이어야 정상적으로 업로드 됨(데이터 형식 맞춰야 함!)
//    이렇게 첫 행 기본키 빼고 둘째행부터 출력됨



}
