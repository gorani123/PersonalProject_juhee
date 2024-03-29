package com.example.simpledms.dto.request;

import lombok.*;

import javax.validation.constraints.NotBlank;

/**
 * packageName : com.example.simplelogin.dto.request
 * fileName : LoginRequest
 * author : juhee
 * date : 2022-11-29
 * description : 로그인 dto
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
public class LoginRequest {

    // @NotBlank : 공백 또는 null 유효성 체크 (공백, null 들어오면 에러 발생)
    @NotBlank
    private String username;

    @NotBlank
    private String password;

}
