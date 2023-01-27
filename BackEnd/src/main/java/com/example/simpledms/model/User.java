package com.example.simpledms.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

/**
 * packageName : com.example.simplelogin.model
 * fileName : User
 * author : juhee
 * date : 2022-11-28
 * description : 유저 엔티티
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * —————————————————————————————
 * 2022-11-28         juhee          최초 생성
 */
@Entity
@SequenceGenerator(
        name = "SQ_USER_GENERATOR"
        , sequenceName = "SQ_USER"
        , initialValue = 1      // 시작값
        , allocationSize = 1    // 1씩 증가
)
@Table(name = "TB_USER",
        // unique 제약조건 어노테이션
        uniqueConstraints = {
            // @UniqueConstraint : 중복 허용하지 않는 컬럼이라는 어노테이션
            @UniqueConstraint(columnNames = "username"),
            @UniqueConstraint(columnNames = "email")
        }

)
@Getter
@Setter
@NoArgsConstructor
public class User {

    // 기본키 (없으면 에러남)
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE
            , generator = "SQ_USER_GENERATOR"
    )
    // 시퀀스 번호
    @Column
    private Long id;

    // 속성
    // 유효성 체크 : validation 라이브러리 설치완(build.gradle) -> 어노테이션 제공
    //       대상 : 컬트롤러가 동작하여 매개변수가 들어오면 변수값에 대해서 유효성 체크
    // @NotBlank : 공백("") 또는 null 들어오면 에러 발생
    // @Size()
    @NotBlank
    @Size(max = 20)
    private String username;    // 유저(로그인) id (체크 : 20자까지)

    @NotBlank
    @Size(max = 50)
    private String email;    // 이메일 (체크 : 50자까지)

    @NotBlank
    @Size(max = 120)
    private String password;    // 비밀번호 (체크 : 120자까지)

    // 역할 속성
    // @ManyToMany : 다대다 관계에서는 반드시 맵핑테이블(조인테이블)이 필요함
    @ManyToMany(fetch = FetchType.LAZY)
    // @JoinTable : 맵핑 테이블 @, 자동 생성됨
    // @joinColumns(name="조인컬럼명")
    @JoinTable( name = "TB_USER_ROLE", 
            joinColumns = @JoinColumn(name="USER_ID"),      // user 테이블과 조인하는 맵핑테이블
            inverseJoinColumns = @JoinColumn(name="ROLE_ID")    // role 조인하는 .. 놓침
    )
    private Set<Role> roles = new HashSet<>();

    // 생성자 (시퀀스_id 제외)
    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    //  TODO) 1. 소셜로그인 추가 : user
    @Builder
    public User(String username, String email, String password, Set<Role> roles) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.roles = roles;
    }

    //  TODO) 2. 소셜로그인 추가 : update 함수
    public User update(String name) {
        this.username = username;

        return this;
    }
}
