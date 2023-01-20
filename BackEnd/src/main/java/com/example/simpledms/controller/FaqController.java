package com.example.simpledms.controller;

import com.example.simpledms.model.Faq;
import com.example.simpledms.service.FaqService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;


/**
 * packageName : com.example.jpaexam.controller.exam07
 * fileName : Dept07Controller
 * author : juhee
 * date : 2022-11-01
 * description : Faq 컨트롤러 (@RestController)
 * 요약 :
 *
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * —————————————————————————————
 * 2022-10-21         juhee          최초 생성
 */
//@RestController : 리턴값 json 객체 형태로 출력 (@Controller: 홈페이지로 이동)
//@RequestMapping : 공통 url

//@CrossOrigin(허용할 사이트주소_Vue의 포트번호_8081_달라지니까체크하기👀) : CORS 보안을 해제시키는 어노테이션
//    CORS보안 : 기본적으로 한 사이트에서 한 포트만 사용가능한 기본 보안

    // 👀프엔 백엔 연동 URL : http://localhost:8081/faq 👀

@Slf4j
@RestController
//@CrossOrigin(origins = "http://localhost:8080") // 👀이렇게 요청이 오면 기본보안 해제해 주렴
//@CrossOrigin(origins = "http://localhost") // 11/07 포트번호 80으로 바꿈(포트번호생략가능)_nginx로 연동
// 11/7 오후, @CrossOrigin 대신 configration 폴더에 WebConfig 파일로 설정!!
@RequestMapping("/api")
public class FaqController {

    @Autowired
    FaqService faqService;

//
//    // 전체 조회 함수 : select -> 검색 -> GetMapping()
//    // 결과, 여러건이 나오니까 List로 받음
//    // Select ->  @GetMapping      C reate  (get방식, 웹브라우저)
//    // 클라이언트가 (form태그) Get방식 (url, 웹브라우저) 보내면
//    //              -> 서버 : @GetMapping("url")어노테이션으로  url로 받고 -> 내부 DB 에서 Select 요청
//    @GetMapping("/faq")
//    public ResponseEntity<Object> getFaqAll(){
//
//        try{
//            List<Faq> list = faqService.findAll();
//
//            // list배열이 비어있지 않으면
//            if(list.isEmpty() == false) {
//                //                         데이터 + 성공 메시지 전송
//                return new ResponseEntity<>(list, HttpStatus.OK);
//            } else {
//                // 데이터 없음 메시지 클라이언트에게 전송
//                return new ResponseEntity<>(list, HttpStatus.NO_CONTENT);
//            }
//
//        } catch (Exception e){
//            log.debug(e.getMessage());    // 로그 확인하는 어노테이션 위에 걸기@Slf4j
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);  // 서버 에러 발생 메시지 전송(클라이언트로 전송)
//
//        }
//    }

    // 전체 삭제 함수 : delete -> DeleteMapping()
    @DeleteMapping("/faq/all")
    public ResponseEntity<Object> removeAll(){

        try{
            faqService.removeAll();

            return new ResponseEntity<>(HttpStatus.OK); // OK 메시지

        } catch (Exception e){
            log.debug(e.getMessage());    // 로그 확인
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    // 🤡새로운 게시글 정보 저장요청 함수 : insert -> @PostMapping()
    @PostMapping("/faq")
    public ResponseEntity<Object> createFaq(@RequestBody Faq faq){

        try{
            Faq faq2 = faqService.save(faq);

            return new ResponseEntity<>(faq2, HttpStatus.OK); // OK 메시지

        } catch (Exception e){
            log.debug(e.getMessage());    // 로그 확인
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 🤩게시글 번호로 조회하는 함수 : get -> @GetMapping
    @GetMapping("/faq/{no}")
    public ResponseEntity<Object> getFaqId(@PathVariable int no){

        try{
//            log.debug("no : "+no);

            Optional<Faq> optionalFaq = faqService.findById(no);

            // optional(null이 아니고), 데이터가 있으면(true)
            if(optionalFaq.isPresent() == true) {
                // 데이터 + 성공 메시지 전송
//                log.debug("no2 : "+no);
                // get함수로 안에 있는 데이터 조회
                return new ResponseEntity<>(optionalFaq.get(), HttpStatus.OK);
            } else {
                // 데이터 없음 메시지 클라이언트에게 전송
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

        } catch (Exception e){
            log.debug(e.getMessage());    // 로그 확인하는 어노테이션 위에 걸기@Slf4j
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);  // 서버 에러 발생 메시지 전송(클라이언트로 전송)

        }
    }


    // 😨새로운 게시글 정보 수정요청 함수 : update -> @PutMapping()
    // int는 @PathVariable, 객체는 @RequestBody로 받음(덩치가 커서)
    @PutMapping("/faq/{no}")
    public ResponseEntity<Object> updateFaq(@PathVariable int no,
                                             @RequestBody Faq faq) {

        try {
            Faq faq2 = faqService.save(faq);

            return new ResponseEntity<>(faq2, HttpStatus.OK); // OK 메시지

        } catch (Exception e) {
            log.debug(e.getMessage());    // 로그 확인
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    // 😤게시글 번호(no)로 삭제하는 함수 : delete -> @DeleteMapping
    @DeleteMapping("/faq/deletion/{no}")
    public ResponseEntity<Object> deleteFaq(@PathVariable int no){
        log.debug("no2 : "+no);

        try{
//            log.debug("no : "+no);
            boolean bSuccess = faqService.removeById(no);

            if(bSuccess == true) {
                log.debug("no2 : "+no);

                // 성공 메시지 OK 전송
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                // 데이터 없음 메시지 클라이언트에게 전송
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

        } catch (Exception e){
            log.debug(e.getMessage());    // 로그 확인하는 어노테이션 위에 걸기@Slf4j
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);  // 서버 에러 발생 메시지 전송(클라이언트로 전송)

        }
    }

    // 검색어가 없으면 전체검색, 있으면 like 검색 ====> 위의 getFaqALl 지워야 함!!!!!!!!!!!!!!!!!!!!!!!!!
    // 🎃게시글명(title)으로 like 검색 : select -> GetMapping()
    // 프엔, 쿼리스트링
    // findByTitle(title){
    //    return http.get(`/faq?title=${title}`);          --> 매개변수 title
    // }
    //      프엔에서 파라미터({}사용) 매개변수 전송방식을 사용 -> 백엔, @PathVariable 로 매개변수 받음
    //      프엔에서 쿼리스트링(?사용) 매개변수 전송방식을 사용 -> 백엔, @RequestParam 으로 매개변수 받음
    //      ---> GET http://localhost:8000/api/faq?title=SA

    // 백엔, @RequestParam -> 매개변수 없는 경우, 파라미터,쿼리스트링 다 받을 수 있음 (/faq) (/faq?title=SA)
    // @RequestParam(required = false) : false 매개변수에 값이 없어도 에러가 발생하지않음(기본값은 required = true)
    // @RequestParam(defaultValue = "값") : 매개변수에 값이 없는 경우 기본값을 설정
    @GetMapping("/faq")
    public ResponseEntity<Object> getFaqAll(@RequestParam(required = false) String title,
                                            @RequestParam(defaultValue = "0") int page,
                                            @RequestParam(defaultValue = "3") int size ){

        try{
            // Pageable 객체 정의 ( page, size 값 설정 )
            Pageable pageable = PageRequest.of(page, size);      // 초기화.. null 대신 기본함수, emptyList()에 넣어서 사용하라고



//            // title이 null일 경우, 전체 검색
//            if(title == null) {
//                list = faqService.findAll();
//            }
//            else{
//                // title에 값이 있을 경우, 게시글명 like 검색
//                list = faqService.findAllByTitleContaining(title);
//            }

            // Page 객체 정의
//            Page<Faq> faqPage;
            // 페이징 처리되는 findAllBYDnameContaining() : dname에 값이 있을 경우, 부서명 like 검색
            Page<Faq> faqPage = faqService.findAllByTitleContaining(title, pageable);

            // 맵 자료구조에 넣어서 전송
            Map<String, Object> response = new HashMap<>();
            response.put("faq", faqPage.getContent());
            response.put("currentPage", faqPage.getNumber());
            response.put("totalItems", faqPage.getTotalElements());
            response.put("totalPages", faqPage.getTotalPages());

            // list배열이 비어있지 않으면
            if(faqPage.isEmpty() == false) {
                //                           데이터 + 성공 메시지 전송
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                // 데이터 없음 메시지 클라이언트에게 전송
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

        } catch (Exception e){
            log.debug(e.getMessage());    // 로그 확인하는 어노테이션 위에 걸기@Slf4j
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);  // 서버 에러 발생 메시지 전송(클라이언트로 전송)
        }
    }




    // select -> 검색 -> @GetMapping()
        // insert -> 추가 -> @PostMapping("url")어노테이션으로 url로 받고 -> 내부 DB 에서 insert 요청
        // update -> 수정 -> @PutMapping()
        // delete -> 삭제 -> @deleteMapping()

}



