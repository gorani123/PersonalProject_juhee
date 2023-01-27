package com.example.simpledms.security.services;

import com.example.simpledms.repository.UserRepository;
import com.example.simpledms.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * packageName : com.example.simplelogin.security.services
 * fileName : UserDetailsServiceImpl
 * author : juhee
 * date : 2022-11-29
 * description : User 정보가 DB에 있는지 체크하고, 있으면 UserDetails 객체를 생성하는 클래스
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * —————————————————————————————
 * 2022-11-29         juhee          최초 생성
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    // 메서드 구현(단축키 이용)
    // User 정보가 DB에 있는지 체크하고, 있으면 UserDetails 객체를 생성하는 함수
    @Override
    @Transactional  // JPA 사용하면 조회시 @Transactional 부분에 성능이 약간 향상됨
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // username에 해당하는 유저가 DB에 있는지 확인(userRepository 함수 불러옴. optional 처리됨-> optional 의 함수, orElseThrow 사용가능)
        // return : 있으면 user 객체
        //          없으면 Error 메시지 출력: "User Not Found with username: " + username
        User user = userRepository.findByUsername(username)
                .orElseThrow(()
                        -> new UsernameNotFoundException("User Not Found with username: " + username));

        return UserDetailsImpl.build(user); // 유저 객체 생성
    }



}
