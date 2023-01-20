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
@Table(name="TB_FILE")      // 테이블명 ***
@SequenceGenerator(                 //👀 아래 @GenerateValue()에서 써준거 세부적으로 써줘야 함
        name= "SQ_FILE_GENERATOR",
        sequenceName = "SQ_FILE",
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
@SQLDelete(sql="UPDATE TB_FILE SET DELETE_YN = 'Y', DELETE_TIME = TO_CHAR(SYSDATE, 'YYYY-MM-DD HH24:MI:SS') WHERE FID = ?")
public class FileDb extends BaseTimeEntity {

//    CREATE TABLE TB_FILE
//    FID          NUMBER NOT NULL PRIMARY KEY,
//    FILE_TITLE   VARCHAR2(1000),  --> 4000byte까지 가능. 그것보다 큰 문자열은 자료형 CLOB 사용하면 됨
//    FILE_CONTENT VARCHAR2(1000),
//    FILE_NAME    VARCHAR2(1000),
//    FILE_TYPE    VARCHAR2(1000),
//    FILE_DATA    BLOB,           ----> 오라클에서 이미지, 동영상 등을 넣는 자료형 (mysql은 아마 mlob..? 다름)
//    DELETE_YN    VARCHAR2(1) DEFAULT 'N',

    // 속성
    // @Id : 기본키(PrimaryKey). not null. 유일해야 함 (하나는 필수. 없으면 클래스명(Detp)에 빨간줄 뜸)
    // @GenerateValue(..SEQUENCE, ...) :오라클은 시퀀스 사용함 (시퀀스 사용_ORACLE, POSTGRE 등/ increment 사용_MYSQL, MARIA DB 등)

    // SHIFT + ALT + U : 카멜표기법으로 변경
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_FILE_GENERATOR") //👀
    private Integer fid;     // 기본키 FID

    @Column
    private String fileTitle;

    @Column
    private String fileContent;

    @Column
    private String fileName;

    @Column
    private String fileType;
    
    // 큰 데이터를 취급하는 자료형 : BLOB(2진 파일_ 이미지,동영상 등), CLOB(4000byte 이상 문자열)
    // @Lob : DB 테이블의 BLOB/CLOB 자료형에 해당하는 컬럼일 경우 사용하는 어노테이션
    //        자료형은 byte[] 배열 사용
    @Lob
    @Column
    private byte[] fileData;

    
    // 아이디(FID) 제외 생성자
    public FileDb(String fileTitle, String fileContent, String fileName, String fileType, byte[] fileData) {
        this.fileTitle = fileTitle;
        this.fileContent = fileContent;
        this.fileName = fileName;
        this.fileType = fileType;
        this.fileData = fileData;
    }
}
