package com.example.simpledms.dto;

import lombok.*;

/**
 * packageName : com.example.simplelogin.dto
 * fileName : MessageResponse
 * author : juhee
 * date : 2022-11-29
 * description : 에러 또는 성공 결과를 메시지로 전송할 dto
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * —————————————————————————————
 * 2022-11-29         juhee          최초 생성
 */

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MessageResponse {
    //속성
    private String message;
}
