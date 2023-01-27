package com.example.simpledms.repository;

import com.example.simpledms.model.ERole;
import com.example.simpledms.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * packageName : com.example.simplelogin.repository
 * fileName : UserRepository
 * author : juhee
 * date : 2022-11-29
 * description : Role 인터페이스
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * —————————————————————————————
 * 2022-11-29         juhee          최초 생성
 */
//                                      JpaRepository<모델명, 모델의 기본키 타입>
@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

    // 쿼리 메소드
    // 권한명(Name)으로 조회하는 함수
    Optional<Role> findByName(ERole name);


}
