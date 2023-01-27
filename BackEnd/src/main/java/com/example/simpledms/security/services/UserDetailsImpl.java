package com.example.simpledms.security.services;

import com.example.simpledms.model.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * packageName : com.example.simplelogin.security.services
 * fileName : UserDetailsImpl
 * author : juhee
 * date : 2022-11-29
 * description : *****⚜Spring Security 의 인증대상 객체 ⚜*****
 *      UserDetails(인터페이스) : Spring Security 인증을 도입하고 싶은 대상(엔티티_모델)에 대해서
 *                              UserDetails 를 상속받아서 사용하도록 권고
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * —————————————————————————————
 * 2022-11-29         juhee          최초 생성
 */
// 인증대상 : User 의 관련 속성/함수
// UserDetails 인터페이스의 속성/함수
public class UserDetailsImpl implements UserDetails {

    // 시리얼 번호 상수(UserDetails 에 정의됨)
    private static final long serialVersionUID = 1L;

    private Long id;    // 시퀀스 id(개발자 속성) _UserModel 에 있는 시퀀스 번호
    private String username;    // Spring Security 에서 제공하는 속성
    private String email;       // 개발자 속성


    // @JsonIgnore : json  속성 변환 시 아래 속성은 무시
    @JsonIgnore
    private String password;    // Spring Security 에서 제공하는 속성

    // 권한 관련 속성 : Spring Security 에서 제공하는 속성
    // <? extends GrantedAuthority> : <자료형들>, GrantedAuthority 상속받은 자식클래스 타입도 허용
    // GrantedAuthority : Spring Security 에서 권한관리를(ROLE_USER, ROLE_ADMIN 등) 목적으로 만든 클래스
    private Collection<? extends GrantedAuthority> authorities; // Security 에서 제공하는 속성

    // 생성자
    public UserDetailsImpl(Long id,
                           String username,
                           String email,
                           String password, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
    }


    // 공용 빌더 함수
    // 매개변수 : user
    // 설명 : 매개변수 권한을 받아서 UserDetailsImpl 객체를 만드는 빌더함수
    public static UserDetailsImpl build(User user) {

        // 매개변수 user의 권한 추출하기
        // role.getName().name() : ERole (ROLE_USER, ROLE_ADMIN)
        // <GrantedAuthority> : spring security 에서 제공하는 권한관리 인터페이스
        // 구현클래스 종류 : SimpleGrantedAuthority 등
        // 아래 결과 : "ROLE_USER" -> GrantedAuthority 타입의 권한으로 변경
        //            -> 그래야 스프링 security 권한관리 기능 나중에 이용 가능
        List<GrantedAuthority> authorities =
                // user.getRoles : ROLE_USER, ROLE_ADMIN
                user.getRoles().stream()
                        .map(role -> new SimpleGrantedAuthority(role.getName().name()))
                        .collect(Collectors.toList());
        // User == UserDetailsImpl
        // User 의 role -> 문자열객체임(ROLE_USER 등)
        // UserDetailsImpl 의 role -> 문자열 아니고 GrantedAuthority 형태의 타입임(GrantedAuthority 안에 ROLE_USER 들어있음)
        return new UserDetailsImpl(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                authorities
        );
    }


    // 개발자가 만든 속성(email,id)의 getter
    public Long getId() {
        return id;
    }
    public String getEmail() {
        return email;
    }


    // spring security 에서 제공하는 속성의 getter
    // UserDetails 인터페이스 상속받은거에 다 있음(메서드 재정의)

    // 사용하는 거는 소스 수정_재정의(return null -> return authorities)
    // 권한관리 사용
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    // 사용
    @Override
    public String getPassword() {
        return password;
    }

    // 사용
    @Override
    public String getUsername() {
        return username;
    }

    // 사용
    // isAccountNonExpired() : 계정이 만료되었는지 여부 체크하는 함수. 리턴값 true -> 만료되지 않았음
    // return false -> true 로 변경. 계정에 만료기간 없음으로 강제 설정(하드코딩)
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // isAccountNonLocked() : 계정이 잠겨있는지 여부 체크하는 함수. 리턴값 true -> 계정이 잠겨있지 않음
    // return false -> true 로 변경. 계정 잠겨있지 않음으로 강제 설정(하드코딩)
    // (패스워드 오류 일정횟수 초과시 계정잠김 기능 설정)
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // Credentials 는 패스워드라는 의미
    // isCredentialsNonExpired() : 패스워드가 만료되었는지 여부 체크하는 함수. 리턴값 true -> 만료되지 않음
    // return false -> true 로 변경. 만료기능 없음으로 강제 설정(하드코딩)
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // isEnabled() : 계정이 사용가능한지 여부 체크하는 함수. 리턴값 true -> 사용할 수 있음
    // return false -> true 로 변경. 무조건 사용할 수 있도록 강제 설정(하드코딩)
    @Override
    public boolean isEnabled() {
        return true;
    }


    // equals 재정의_ ctrl + insert -> equals() 및 hashCode()클릭. hashCode()는 필요없으니 지우기
    // equals() : id가 같으면 같은 객체로 판단하는 함수
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDetailsImpl that = (UserDetailsImpl) o;
        return Objects.equals(id, that.id);
    }


}
