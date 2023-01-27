package com.example.simpledms.security.auth;

import com.example.simpledms.model.ERole;
import com.example.simpledms.model.Role;
import com.example.simpledms.model.User;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * packageName : com.example.simplelogin.security.auth
 * fileName : OAuthAttributes
 * author : kangtaegyung
 * date : 2022/12/16
 * description :
 * 요약 :
 * <p>
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022/12/16         kangtaegyung          최초 생성
 */

@Getter
public class OAuthAttributes {
    PasswordEncoder encoder = new BCryptPasswordEncoder();
    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String username;
    private String email;

    @Builder
    public OAuthAttributes(Map<String, Object> attributes,
                           String nameAttributeKey, String username,
                           String email) {
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.username = username;
        this.email = email;
    }

    //    OAuth2User에서 반환하는 사용자 정보는 Map이기 때문에 값 하나하나를 변환
    public static OAuthAttributes of(String registrationId,
                                     String userNameAttributeName,
                                     Map<String, Object> attributes) {
        return ofGoogle(userNameAttributeName, attributes);
    }

    private static OAuthAttributes ofGoogle(String userNameAttributeName,
                                            Map<String, Object> attributes) {
        return OAuthAttributes.builder()
                .username((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }


    //    OAuthAttributes에서 엔티티를 생성하는 시점은 처음 가입할 때
//    User 엔티티를 생성
    public User toEntity() {
        Set<Role> roles = new HashSet<>();
        Role userRole = new Role();
        userRole.setId(1);
        userRole.setName(ERole.ROLE_USER);

        roles.add(userRole); // 기본 User 롤 추가

        return User.builder()
                .username((String)this.email.split("@")[0])
                .email(this.email)
                .password(encoder.encode("123456"))
                .roles(roles)
                .build();
    }
}
