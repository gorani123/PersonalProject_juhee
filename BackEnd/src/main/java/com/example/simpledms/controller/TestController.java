package com.example.simpledms.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * packageName : com.example.simplelogin.controller
 * fileName : TestController
 * author : juhee
 * date : 2022-11-29
 * description : 테스트용 페이지 컨트롤러 (대상 : /home, /user, /admin 메뉴 페이지 테스트 )
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * —————————————————————————————
 * 2022-11-29         juhee          최초 생성
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test")
public class TestController {
    // 🎃 모두가 접근 가능한 페이지
    @GetMapping("/all")
    public String allAccess() {
        return "Public Content";
    }

    // 권한 관리가 필요한 페이지
    // @PreAuthorize("hasRole('권한문자열1') or hasRole('권한문자열2')...") : 권한문자열1 or 권한문자열2 가진 유저 접근 가능
    @GetMapping("/user")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public String userAccess() {
        return "User Content";
    }
    
    // 권한 관리가 필요한 페이지 : ADMIN 만 접근가능
    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminAccess() {
        return "Admin Content";
    }
}
