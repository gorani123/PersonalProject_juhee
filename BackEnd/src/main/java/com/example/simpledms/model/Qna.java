package com.example.simpledms.model;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

/**
 * packageName : com.example.simpledms.model
 * fileName : Qna
 * author : juhee
 * date : 2022-11-09
 * description : Qna 모델
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * —————————————————————————————
 * 2022-11-09         juhee          최초 생성
 */
@Entity
@Table(name="TB_QNA")      // 테이블명
@SequenceGenerator(                 //👀 아래 @GenerateValue()에서 써준거 세부적으로 써줘야 함
        name= "SQ_QNA_GENERATOR",
        sequenceName = "SQ_QNA",
        initialValue = 1,
        allocationSize = 1
)
@Getter // 롬북
@Setter // 롬북
@Builder    // 디자인패턴. 생성자 대신 빌더로 객체를 만드는 방법
@NoArgsConstructor
// SQL문 자동생성시 null 컬럼데이터는 제외시키는 어노테이션: @DynamicInsert @DynamicUpdate
@AllArgsConstructor // @Builder와 세트
@DynamicInsert
@DynamicUpdate
// @Where(clause="DELETE_YN = 'N'") : 이 엔티티에 접근하는 모든 조회들(sql)은 이 조건(DELETE_YN='N')인 것만 검색
@Where(clause="DELETE_YN = 'N'")
// sofe delete : 삭제하는 척만 하기. 화면에서는 삭제한 것 처럼 데이터가 안 보이지만 db에서는 데이터를 삭제하지 않음
@SQLDelete(sql="UPDATE TB_QNA SET DELETE_YN = 'Y', DELETE_TIME = TO_CHAR(SYSDATE, 'YYYY-MM-DD HH24:MI:SS') WHERE QNO = ?")
public class Qna extends BaseTimeEntity {

    // 속성
    // @Id : 기본키(PrimaryKey). not null. 유일해야 함 (하나는 필수. 없으면 클래스명(Detp)에 빨간줄 뜸)
    // @GenerateValue(..SEQUENCE, ...) :오라클은 시퀀스 사용함 (시퀀스 사용_ORACLE, POSTGRE 등/ increment 사용_MYSQL, MARIA DB 등)
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_QNA_GENERATOR") //👀
    private Integer qno;     // 글번호

    @Column(columnDefinition = "VARCHAR2(255)")
    private String subject;

    @Column(columnDefinition = "VARCHAR2(255)")
    private String name;

    @Column(columnDefinition = "VARCHAR2(255)")
    private String message;

    @Column(columnDefinition = "VARCHAR2(255)")
    private String email;
}
