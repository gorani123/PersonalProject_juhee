package com.example.simpledms.dto.filedb;

import lombok.*;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;

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
public class ResponseFileDto {

    // 속성 (fildData는 필요없음)
    private Integer fid;
    private String fileTitle;
    private String fileContent;
    private String fileName;
    private String fileType;

    // 가공된 속성(모델에 없는 추가속성)
    private Integer fileSize;  // 이미지 크기
    private String fileUrl;    // 이미지 다운로드 URL (url 클릭시 이미지가 다운됨)

}
