package com.example.simpledms.dto.property;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Lob;

/**
 * packageName : com.example.simpledms.dto.filedb
 * fileName : ResponseFileDto
 * author : juhee
 * date : 2022-11-10
 * description : file DTO
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * —————————————————————————————
 * 2022-11-10         juhee          최초 생성
 */
@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ResponsePropertyDto {

    // 속성 (propertyData는 필요없음)
 
    private Integer pno;
    private String propertyTitle;
    private String propertyFileName;
    private String propertyType;

    private String propertyPrice;
    private String propertyAddress;
    private String propertyCity;
    private Integer propertyBed;
    private Integer propertyBath;


    // 가공된 속성(모델에 없는 추가속성)
    private Integer propertySize;  // 갤러리 크기
    private String propertyUrl;    // 갤러리 다운로드 URL (url 클릭시 이미지가 다운됨)


}
