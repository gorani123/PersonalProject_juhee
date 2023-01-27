package com.example.simpledms.repository;

import com.example.simpledms.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * packageName : com.example.simplelogin.repository
 * fileName : UserRepository
 * author : juhee
 * date : 2022-11-29
 * description : 유저 인터페이스
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * —————————————————————————————
 * 2022-11-29         juhee          최초 생성
 */
//                                      JpaRepository<모델명, 모델의 기본키 타입>
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // 쿼리 메소드
    // 리턴값 null 체크 위해 optional 로 User 객체 감쌈
    // username(유저 로그인 id)으로 조회하는 함수
    Optional<User> findByUsername(String username);

    // username(로그인 id) 존재하는지 검사하는 함수(리턴값 true/false)
    Boolean existsByUsername(String username);

    // email 존재하는지 검사하는 함수(리턴값 true/false)
    Boolean existsByEmail(String email);

    // TODO) 3. 소셜 로그인 추가 : oauth2 에서 사용할 함수 findByEmail()
    //  소셜 로그인으로 반환되는 값 중 email을 통해 이미 생성된 사용자인지 처음 가입하는 사용자인지 판단하기 위한 함수
    Optional<User> findByEmail(String email);

}
