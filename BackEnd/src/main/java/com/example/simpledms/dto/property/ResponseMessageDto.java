package com.example.simpledms.dto.filedb;

import lombok.*;

/**
 * packageName : com.example.simpledms.dto.filedb
 * fileName : ResponseMessageDto
 * author : juhee
 * date : 2022-11-10
 * description :
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
public class ResponseMessageDto {

    // 속성
    // 클라이언트(Vue)쪽으로 전달할 메시지
    private String message;


}
