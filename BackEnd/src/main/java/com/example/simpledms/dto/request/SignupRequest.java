package com.example.simpledms.dto.request;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

/**
 * packageName : com.example.simplelogin.dto.request
 * fileName : SignupRequest
 * author : juhee
 * date : 2022-11-29
 * description : 회원가입 dto
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
public class SignupRequest {

    @NotBlank                   // 공백 또는 null 입력 불가
    @Size(min = 3, max = 20)    // 자리수 3-20까지만 허용
    private String username;    // 로그인 아이디

    @NotBlank
    @Size(min = 6, max = 40)    // 자리수 6-40까지만 허용
    private String password;    // 비번

    @NotBlank
    @Size(max = 50)             // 자리수 50자까지 허용
    @Email                      // 이메일 형식이 맞는지 체크
    private String email;       // 이메일

    private Set<String> role;   // 역할 (ROLE_USER, ROLE_ADMIN)

}
