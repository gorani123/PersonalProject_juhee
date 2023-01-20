package com.example.simpledms.controller;

import com.example.simpledms.model.Qna;
import com.example.simpledms.service.QnaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * packageName : com.example.simpledms.controller
 * fileName : QnaController
 * author : juhee
 * date : 2022-11-09
 * description : Qna 컨트롤러
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * —————————————————————————————
 * 2022-11-09         juhee          최초 생성
 */
@Slf4j
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:8081")
public class QnaController {

    @Autowired
    QnaService qnaService;

    // 검색어가 없으면 전체검색, 있으면 like 검색
    // 🎃질문제목(subject)으로 like 검색 : select -> GetMapping()
    @GetMapping("/qna")
    public ResponseEntity<Object> getQnaAll(@RequestParam String searchSelect,  // 셀렉트박스 선택(필수)
                                            @RequestParam(required = false) String searchKeyword,   // 검색어(선택)
                                            @RequestParam(defaultValue = "0") int page,      // 디폴트값(검색안할시 0페이지)
                                            @RequestParam(defaultValue = "3") int size ){    // 디폴트값(검색안할시 3페이지씩표시)

        try{
            // Pageable 객체 정의 ( page, size 값 설정 )
            Pageable pageable = PageRequest.of(page, size);

            Page<Qna> qnaPage;
            
            // Page 객체 정의
            //                     🤍 셀렉트박스, 대문자 Title
            if(searchSelect.equals("Subject")){
                qnaPage = qnaService.findAllBySubjectContaining(searchKeyword, pageable);
            } else {
                qnaPage = qnaService.findAllByNameContaining(searchKeyword, pageable);
//                Page<Qna> qnaPage = qnaService.findAllByNameContaining(searchKeyword, pageable); 위에 Page<Qna> qnaPage;안한경우
            }

            // 맵 자료구조에 넣어서 전송
            Map<String, Object> response = new HashMap<>();
            response.put("qna", qnaPage.getContent());
            response.put("currentPage", qnaPage.getNumber());
            response.put("totalItems", qnaPage.getTotalElements());
            response.put("totalPages", qnaPage.getTotalPages());

            // list배열이 비어있지 않으면
            if(qnaPage.isEmpty() == false) {
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

    // 🤡새로운 고객 정보 저장요청 함수 : insert -> @PostMapping()
    @PostMapping("/addqna")
    public ResponseEntity<Object> createQna(@RequestBody Qna qna){

        try{
            Qna qna2 = qnaService.save(qna);

            return new ResponseEntity<>(qna2, HttpStatus.OK); // OK 메시지

        } catch (Exception e){
            log.debug(e.getMessage());    // 로그 확인
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 🤩qno로 조회하는 함수 : get -> @GetMapping
    @GetMapping("/qna/{qno}")
    public ResponseEntity<Object> getQnaId(@PathVariable int qno){

        try{
            Optional<Qna> optionalQna = qnaService.findById(qno);

            // optional(null이 아니고), 데이터가 있으면(true)
        if(optionalQna.isPresent() == true) {
                // 데이터 + 성공 메시지 전송
//                log.debug("qno2 : "+qno);
                // get함수로 안에 있는 데이터 조회
                return new ResponseEntity<>(optionalQna.get(), HttpStatus.OK);
            } else {
                // 데이터 없음 메시지 클라이언트에게 전송
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

        } catch (Exception e){
            log.debug(e.getMessage());    // 로그 확인하는 어노테이션 위에 걸기@Slf4j
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);  // 서버 에러 발생 메시지 전송(클라이언트로 전송)

        }
    }

    // 😨새로운 고객 정보 수정요청 함수 : update -> @PutMapping()
    // int는 @PathVariable, 객체는 @RequestBody로 받음(덩치가 커서)
    @PutMapping("/qna/{qno}")
    public ResponseEntity<Object> updateQna(@PathVariable int qno,
                                                 @RequestBody Qna qna) {

        try {
            Qna qna2 = qnaService.save(qna);

            return new ResponseEntity<>(qna2, HttpStatus.OK); // OK 메시지

        } catch (Exception e) {
            log.debug(e.getMessage());    // 로그 확인
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    // 😤qno로 삭제하는 함수 : delete -> @DeleteMapping
    @DeleteMapping("/qna/deletion/{qno}")
    public ResponseEntity<Object> deleteQna(@PathVariable int qno){

        try{
//                log.debug("qno2 : "+qno);
            boolean bSuccess = qnaService.removeById(qno);

            if(bSuccess == true) {
//                log.debug("qno2 : "+qno);

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

}
