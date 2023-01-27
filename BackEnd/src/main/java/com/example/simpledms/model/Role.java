package com.example.simpledms.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
 * packageName : com.example.simplelogin.model
 * fileName : Role
 * author : juhee
 * date : 2022-11-28
 * description : 역할 클래스
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * —————————————————————————————
 * 2022-11-28         juhee          최초 생성
 */

@Entity
@SequenceGenerator(
        name = "SQ_ROLE_GENERATOR"
        , sequenceName = "SQ_ROLE"
        , initialValue = 1      // 시작값
        , allocationSize = 1    // 1씩 증가
)
@Table(name = "TB_ROLE")
@Getter
@Setter
@NoArgsConstructor
public class Role {

    // 기본키
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_ROLE_GENERATOR")
    // 오라클 시퀀스 만드는 방법(mySQL은 다름)
    @Column
    private Integer id;

    @Enumerated(EnumType.STRING)
    private ERole name;

    // Erole............................................??
    public Role(ERole name) {
        this.name = name;
    }

}
