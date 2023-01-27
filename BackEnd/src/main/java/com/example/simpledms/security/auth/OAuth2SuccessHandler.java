package com.example.simpledms.security.auth;

import com.example.simpledms.security.jwt.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * packageName : com.example.simplelogin.security.auth
 * fileName : OAuth2SuccessHandler
 * author : kangtaegyung
 * date : 2022/12/16
 * description : 1.
 * 요약 :
 * <p>
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022/12/16         kangtaegyung          최초 생성
 */
@Slf4j
@Component
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    @Autowired
    JwtUtils jwtUtils;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        //    인증된 객체를 홀더에 저장
        SecurityContextHolder.getContext().setAuthentication(authentication);

        //    인증된 유저 정보를 oAuth2User 에 저장
//        참고) Auth2 로그인은 인증객체가(authentication.getPrincipal()) OAuth2User 타입이고,
//             일반 로그인은 인증객체가(authentication.getPrincipal()) UserDetails 타입임
        OAuth2User oAuth2User = (OAuth2User)authentication.getPrincipal();

        log.info("Principal에서 꺼낸 OAuth2User = {}", oAuth2User);
        // 최초 로그인이라면 회원가입 처리를 한다.
        String targetUrl;
        log.info("토큰 발행 시작");

        // 이메일로 Token 발행
        String jwt = jwtUtils.generateJwtToken((String)oAuth2User.getAttributes().get("email"));

        log.info("{}", jwt);
        targetUrl = UriComponentsBuilder.fromUriString("http://localhost:8080/auth-redirect")
                .queryParam("accessToken", jwt)
                .queryParam("username", (String)((String) oAuth2User.getAttributes().get("email")).split("@")[0])
                .queryParam("email", (String)oAuth2User.getAttributes().get("email"))
                .build().toUriString();
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }
}