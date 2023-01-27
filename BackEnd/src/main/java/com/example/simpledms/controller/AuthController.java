
package com.example.simpledms.controller;

import com.example.simpledms.dto.MessageResponse;
import com.example.simpledms.dto.request.LoginRequest;
import com.example.simpledms.dto.request.SignupRequest;
import com.example.simpledms.dto.response.JwtResponse;
import com.example.simpledms.model.ERole;
import com.example.simpledms.model.Role;
import com.example.simpledms.model.User;
import com.example.simpledms.repository.RoleRepository;
import com.example.simpledms.repository.UserRepository;
import com.example.simpledms.security.jwt.JwtUtils;
import com.example.simpledms.security.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * packageName : com.example.simplelogin.controller
 * fileName : AuthController
 * author : juhee
 * date : 2022-11-29
 * description : 로그인, 회원가입 컨트롤러
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * —————————————————————————————
 * 2022-11-29         juhee          최초 생성
 */
//          (origins = "http:/localhost...") 원래 url 입력. "*"는 모든 url 의미(간단 테스트용)
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    // 바로 repository 불러서 코딩(실무에서는 이렇게 안 함)

    // 인증과 권한체크 처리를 위한 객체 (spring security 에서 제공_라이브러리 설치 시 사용가능)
    @Autowired
    AuthenticationManager authenticationManager;

    // 암호화시키는 객체(spring security 에서 제공_라이브러리 설치 시 사용가능)
    @Autowired
    PasswordEncoder encoder;

    // 웹토큰 유틸리티 객체(생성, 유효성체크 등)
    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    //     로그인 함수 ======================================================================================================
    // @Valid : 서버 유효성 체크 어노테이션 (발동조건: dto(loginRequest)에 걸린 @NotBlank, @Size 등)
    // @RequestBody : 바디로 받음
    // LoginRequest : dto
    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        // 1. 인증체크 시작
        // loginRequest 의 속성 username, password
        // 인증을 담당하는 객체, AuthenticationManager : 모델 User(==UserDetails)와 비슷. 인증된 객체
        // authenticationManager.authenticate() 호출 실행 후 리턴값 -> 인증된 객체(Authentication)
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );
        // 2. spring security 에서 인증된 객체는 setter 함수를 이용해 홀더에 넣음(SecurityContextHolder)
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 3. JWT 토큰 발행
        String jwt = jwtUtils.generateJwtToken(authentication);

        // 4. 인증된 객체에서 유저정보만 뽑아서.getPrincipal() UserDetails 에 저장
        //                                                            .getPrincipal() : 유저 정보 (getCredentials() : 패스워드정보)
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        // 5. 문자열 권한 정보 추출하기(GrantedAuthority 타입: ROLE_USER, ROLE_ADMIN 등.. 문자열 아니고)
        // 이걸 문자열 타입으로 원복
        // 배열이니 .stream()으로 맵..
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());  // .toList() : Stream 형을 List 배열로 원복
        //
        return ResponseEntity.ok(new JwtResponse(jwt,           // 토큰
                userDetails.getId(),        // 아이디
                userDetails.getUsername(),  // 유저네임
                userDetails.getEmail(),     // 이메일
                roles                       // role, 권한
        ));
    }

    //     회원가입 함수 ====================================================================================================
    // postmapping - requestbody
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signupRequest) {
        // 사용자가 DB에 있는지 확인하고, 있으면 예외처리하고 Vue 메시지 전송
        // (없으면 회원가입 함수)
        if (userRepository.existsByUsername(signupRequest.getUsername())) {
            //                                      dto에 MessageResponse 미리 만들어 놓음
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
        }

        // email DB에 있는지 확인하고, 있으면 예외처리하고 Vue 메시지 전송
        if (userRepository.existsByUsername(signupRequest.getEmail())) {
            //                                      dto에 MessageResponse 미리 만들어 놓음
            return ResponseEntity.badRequest().body(new MessageResponse("Error: email is already taken!"));
        }

        // 신규 사용자 생성(회원가입)
        // user 모델 생성자 통해 만들기
        User user = new User(signupRequest.getUsername(),
                signupRequest.getEmail(),
                encoder.encode(signupRequest.getPassword())     // 암호화 적용(encoder객체의 함수 encode 불러서 암호화할 비번 넣기)
        );

        Set<String> strRoles = signupRequest.getRole();     // Vue에서 전송한 권한(role) 적용
        Set<Role> roles = new HashSet<>();  // return 할 때 사용할 권한(role) 정보(Vue 로 전송됨)

        // Vue 에서 요청한 데이터에 role 정보가 없으면 디폴트(기본) ROLE_USER 로 생성
        // (User 권한을 가진 회원 생성)_ 관리자계정은 ROLE_ADMIN
        // 옵셔널이라 예외처리 필요
        // strRoles_Vue에서 전송한 권한정보(role) 가 없으면 디폴트로 ROLE_USER로 생성
        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: ROle is not found"));
            roles.add(userRole);
        }
        // Vue에서 전송한 데이터에 권한정보(role) 가 있으면
        //                      role = "admin" 이면 ROLE_ADMIN 저장해서 전송
        //                           = "..."   이면 ...     저장해서 전송
        //                           = 모두 아니면 ROLE_USER 저장해서 전송
        else {
            strRoles.forEach(role -> {
                // 권한정보(role)가 admin일 경우
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);

                        break;
                    default:
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }

        user.setRoles(roles);       // USER 객체에 role 넣어주기(ROLE_USER)
        userRepository.save(user);  // DB에 신규회원 생성 완료(save). role = ROLE_USER

        // Vue 에 성공메시지 전송
        return ResponseEntity.ok(new MessageResponse("User resistered successfully!"));


    }

}