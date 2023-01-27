package com.example.simpledms.configration;

import com.example.simpledms.security.jwt.AuthEntryPointJwt;
import com.example.simpledms.security.jwt.AuthTokenFilter;
import com.example.simpledms.security.services.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * packageName : com.example.simplelogin.configuration
 * fileName : WebSecurityConfig
 * author : juhee
 * date : 2022-11-29
 * description : AuthTokenFilter를 여기에 적용
 * // @Configuration : 스프링부트 설정 클래스
 * // spring security 라이브러리를 설치하면
 * // 기본적으로 모든 url 에 대해 인증을 진행함
 * // 내부적으로 사용하고 있는 로그인페이지로 자동 리다이렉트함
 * // 기본 user id : user , pwd: 콘솔에 보임
 * // application.properties 파일에 user/pwd 설정 가능
 * // 아래 클래스에서 인증/접근권한을 설정할 수 있음
 * // @Configuration : 스프링부트 설정 클래스
 * <p>
 * // securedEnabled, prePostEnabled, jsr250Enabled 3개의 옵션이 존재(활성화 @)
 * // 1.securedEnabled
 * //  @Secured 애노테이션을 사용하여 인가 처리를 하고 싶을때 사용하는 옵션이다.
 * //  단순 권한체크, spring 에서만 가능
 * //  기본값은 false
 * // 2.prePostEnabled
 * //  @PreAuthorize, @PostAuthorize 애노테이션을 사용하여 인가 처리를 하고 싶을때 사용하는 옵션이다.
 * //  다양하고 유연하게 권한체크 가능, 유연한 권한체크를 위한 el 언어 제공 : 예) 권한문자열이 140 이상일때만 통과 등
 * //  기본값은 false
 * // 3.jsr250Enabled
 * //  @RolesAllowed 애노테이션을 사용하여 인가 처리를 하고 싶을때 사용하는 옵션이다.
 * //  단순 권한체크, java 사용하는 곳은 모두 가능
 * //  기본값은 false
 * //@EnableGlobalMethodSecurity : 시큐리티 적용을 위한 어노테이션. prePostEnabled 권한관리옵션
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * —————————————————————————————
 * 2022-11-29         juhee          최초 생성
 */

// @Configuration : 스프링부트 설정 클래스
@Configuration
//@EnableGlobalMethodSecurity : 시큐리티 적용을 위한 어노테이션. prePostEnabled 권한관리옵션
@EnableGlobalMethodSecurity(
        // securedEnabled = true,
        // jsr250Enabled = true,
        prePostEnabled = true)

// spring security 버전이 올라가면서 extends WebSecurityConfigurerAdapter 사용 안 함
//public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
public class WebSecurityConfig {

    // DB 조회해서 User 가 있으면 User 객체를 UserDetails 로 리턴하는 함수가 있는 객체
    @Autowired
    UserDetailsServiceImpl userDetailsService;

    // 웹토큰 예외처리 객체(비인증/권한체크)
    @Autowired
    private AuthEntryPointJwt unauthorizedHandler;


    //  spring security 웹토큰 필터 클래스가 없으므로
//  JWT 토큰 필터 객체 생성
    //  과거 코딩방식
    //  @Override
    //  public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception { authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder()); }
    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter();
    }

    //  DB 에서 가져온 정보와 input 된 정보를 비교하는 함수
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        //  db에서 가져온 정보와 input 된 정보를 비교하는 객체
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userDetailsService); // DB 유저 조회 함수 객체(유저가 있는지 확인)
        authProvider.setPasswordEncoder(passwordEncoder()); // 패스워드에 암호화 적용(유저가 있으면)

        return authProvider;
    }


    // AuthenticationManager 클래스 : 인증을 담당하는 클래스
//  AuthenticationManager 를 컨트롤러에서 사용하기 위해 아래 함수 정의 (안하면 @Autodwired 로 가져올 때 에러 발생 가능)
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }


    // passwordEncoder() : 패스워드 암호화 함수 (BCryptPasswordEncoder 함수 호출)
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();   // Bcrypt 암호화 알고리즘 적용
    }

    /*
     * 스프링 시큐리티 룰(보안)을 무시하도록 하는 Url 규칙(여기 등록하면 규칙 적용하지 않음)
     */
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().antMatchers("/js/**", "/images/**", "/css/**");
    }


    //  스프링 시큐리티 룰을 무시하도록 하는 Url 규칙(여기 등록하면 규칙 적용하지 않음)
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // ***권한관리 메인 파트***
        http.cors()   // REST API 연결 cors (url 체크)
                .and(). // 연결
                csrf().disable() // csrf 보안 비활성화 (csrf 해킹.. 게시판 등에 피싱사이트 url 클릭 유도)
                // exception***() : 예외처리 builder 함수
                // 인증 예외처리는 웹토큰 예외처리 클래스 AuthEntryPointJwt 로 하겠다
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
                // 세션 안 쓰고(stateful) JWT 사용 예정(디폴트, 세션 사용_세션은 서버쪽에 저장하므로 긴밀한 통신 필요)
                // 우리는 웹토큰 기반 인증을 사용하므로 STATELESS
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                // api/auth/** 형태의 url 은 모든 사용자 접근 허용
                .authorizeRequests().antMatchers("/api/auth/**").permitAll()
                // api/test/** 형태의 url 은 모든 사용자 접근 허용
                .antMatchers("/api/test/**").permitAll()
                // 그외 url은 authenticated. 모든 사용자, 모든 접속에 대해서 인증이 필요함
                .anyRequest().authenticated();

        // DB와 입력값(id, pw) 비교하는 함수 호출(내부적으로 패스워드 암호화)
        http.authenticationProvider(authenticationProvider());

        // authenticationJwtTokenFilter() : 웹토큰 필터를 적용하는(유저 패스워드를 체크하는 필터 앞에 웹토큰 필터를 끼워넣는) 부분
        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class); // JWT 토큰 필터 적용

        return http.build();
    }

}
