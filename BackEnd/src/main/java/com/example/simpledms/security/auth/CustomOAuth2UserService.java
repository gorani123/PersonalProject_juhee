package com.example.simpledms.security.auth;

import com.example.simpledms.repository.UserRepository;
import com.example.simpledms.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Collections;

/**
 * packageName : com.example.simplelogin.security.auth
 * fileName : CustomOAuth2UserService
 * author : kangtaegyung
 * date : 2022/12/16
 * description : oAuth2 성공 후 처리 #1 (db에 이메일, 유저명, 패스워드 저장)
 *              1) 구글에서 승인 후 주는 정보 : 이메일, 프로파일, 승인된토큰  -> 아이디, 패스워드 안보내줌
 *              2) db 저장 : username = 이메일.split("@") @앞에 있는 문자열을 저장_ 이메일 @ 앞에 있는걸 user명으로 셋팅
 *                          password = 123456 암호화해서 DB 저장
 * 요약 :
 * <p>
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022/12/16         kangtaegyung          최초 생성
 */
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    @Autowired
    private UserRepository userRepository;
    private HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        //DefaultOAuth2User 서비스를 통해 User 정보를 가져와야 하기 때문에 대리자 생성
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();

        OAuth2User oAuth2User = delegate.loadUser(userRequest);
        //네이버 로그인인지 구글로그인인지 서비스를 구분해주는 코드
        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        //OAuth2 로그인 진행시 키가 되는 필드값 프라이머리키와 같은 값 네이버 카카오 지원 x
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails()
                .getUserInfoEndpoint().getUserNameAttributeName();

        //OAuth2UserService를 통해 가져온 데이터를 담을 클래스
        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());

        //로그인 한 유저 정보
        User user = saveOrUpdate(attributes);

        //httpSession의 유저 속성을 설정
//        httpSession.setAttribute("user", new SessionUser(user));

        // 로그인한 유저를 리턴함
        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")),
                attributes.getAttributes(),
                attributes.getNameAttributeKey());
//        return new UserDetailsImpl(user, oAuth2User.getAttributes());
    }

//    User 저장하고 이미 있는 데이터면 Update
    private User saveOrUpdate(OAuthAttributes attributes) {
        User user = userRepository.findByEmail(attributes.getEmail())
                .map(entity -> entity.update(attributes.getUsername()))
                .orElse(attributes.toEntity());
        return userRepository.save(user);
    }
}