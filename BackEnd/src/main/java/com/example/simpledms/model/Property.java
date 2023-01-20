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
 * description : property 모델 클래스
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * —————————————————————————————
 * 2022-10-19         juhee          최초 생성
 */
@Entity
@Table(name="TB_PROPERTY")      // 테이블명 ***
@SequenceGenerator(                 //👀 아래 @GenerateValue()에서 써준거 세부적으로 써줘야 함
        name= "SQ_PROPERTY_GENERATOR",
        sequenceName = "SQ_PROPERTY",
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
// soft delete : 삭제하는 척만 하기. 화면에서는 삭제한 것 처럼 데이터가 안 보이지만 db에서는 데이터를 삭제하지 않음
@SQLDelete(sql="UPDATE TB_PROPERTY SET DELETE_YN = 'Y', DELETE_TIME = TO_CHAR(SYSDATE, 'YYYY-MM-DD HH24:MI:SS') WHERE PNO = ?")
public class Property extends BaseTimeEntity {
//    PNO                   NUMBER NOT NULL PRIMARY KEY,
//    PROPERTY_TITLE        VARCHAR2(1000),   -- 제목
//    PROPERTY_FILE_NAME    VARCHAR2(1000),   -- 사진파일 이름
//    PROPERTY_TYPE         VARCHAR2(1000),   -- 파일 타입(업로드시 자동입력_jpg 등)
//    PROPERTY_DATA         BLOB,         -- 파일속성
//    PROPERTY_PRICE        VARCHAR2(1000),   -- 매물가격
//    PROPERTY_ADDRESS      VARCHAR2(1000),   -- 세부주소
//    PROPERTY_CITY         VARCHAR2(1000),   -- 도시, 국가
//    PROPERTY_BED          NUMBER,       -- 방 갯수
//    PROPERTY_BATH         NUMBER,       -- 화장실 갯수

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_PROPERTY_GENERATOR") //👀
    private Integer pno;     // 기본키 PNO

    @Column
    private String propertyTitle;
    @Column
    private String propertyFileName;
    @Column
    private String propertyType;

    @Lob
    @Column
    private byte[] propertyData;

    @Column
    private String propertyPrice;
    @Column
    private String propertyAddress;
    @Column
    private String propertyCity;
    @Column
    private Integer propertyBed;
    @Column
    private Integer propertyBath;

    // pno 제외한 생성자
    public Property(String propertyTitle, String propertyFileName, String propertyType, byte[] propertyData, String propertyPrice, String propertyAddress, String propertyCity, Integer propertyBed, Integer propertyBath) {
        this.propertyTitle = propertyTitle;
        this.propertyFileName = propertyFileName;
        this.propertyType = propertyType;
        this.propertyData = propertyData;
        this.propertyPrice = propertyPrice;
        this.propertyAddress = propertyAddress;
        this.propertyCity = propertyCity;
        this.propertyBed = propertyBed;
        this.propertyBath = propertyBath;
    }
}
