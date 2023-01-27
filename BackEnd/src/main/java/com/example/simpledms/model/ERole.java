package com.example.simpledms.model;

/**
 * packageName : com.example.simplelogin.model
 * fileName : ERole
 * author : juhee
 * date : 2022-11-28
 * description : 역할을 정의하는 열거형
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * —————————————————————————————
 * 2022-11-28         juhee          최초 생성
 */

// spring security 에서 권한관리 정의 규칙 : ROLE_이름 (접두어로 ROLE_ 사용)
public enum ERole {

    ROLE_USER,
    ROLE_ADMIN

}
