package com.example.simpledms.controller;

import com.example.simpledms.dto.filedb.ResponseMessageDto;
import com.example.simpledms.dto.property.ResponsePropertyDto;
import com.example.simpledms.model.Property;
import com.example.simpledms.service.PropertyService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.HashMap;
import java.util.Map;


/**
 * packageName : com.example.jpaexam.controller.exam07
 * fileName : Dept07Controller
 * author : juhee
 * date : 2022-11-01
 * description : property 컨트롤러 (@RestController) -
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * —————————————————————————————
 * 2022-10-21         juhee          최초 생성
 */
@Slf4j
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:8080")     // 8080에서 뷰 8081로 바꿈
public class PropertyController {

    @Autowired
    PropertyService propertyService;

    // Model to Dto 자동 변환(Dto to Model ) 외부 라이브러리(build.gradle 에 설치)
    ModelMapper modelMapper = new ModelMapper();


    // 👀
    // 갤러리 업로드(db저장)를 위한 컨트롤러 함수 😦
    // @RequestParam / @PathVariable
    // 😡 PostMapping
    @PostMapping("/property/upload")
    public ResponseEntity<Object> propertyUploadFile(@RequestParam("propertyTitle") String propertyTitle,
                                                     @RequestParam("property") MultipartFile property,
                                                     @RequestParam("propertyPrice") String propertyPrice,
                                                     @RequestParam("propertyAddress") String propertyAddress,
                                                     @RequestParam("propertyCity") String propertyCity,
                                                     @RequestParam("propertyBed") Integer propertyBed,
                                                     @RequestParam("propertyBath") Integer propertyBath
    ) {

        String message = ""; // front-end 로 전송할 메세지

        // 디버깅 출력
        log.debug("propertyTitle :" + propertyTitle);
        log.debug("property :" + property);
        log.debug("propertyPrice :" + propertyPrice);
        log.debug("propertyAddress :" + propertyAddress);
        log.debug("propertyCity :" + propertyCity);
        log.debug("propertyBed :" + propertyBed);
        log.debug("propertyBath :" + propertyBath);

        try {
            // db 저장 함수 호출 .store()           file
            propertyService.store(propertyTitle, property, propertyPrice, propertyAddress, propertyCity,propertyBed, propertyBath);

            // 메시지 내용
            message = "Upload the file successfully: " + property.getOriginalFilename();

            // message를 객체 생성자 형태로 Dto에 담아서 보내기 + 성공 메시지 OK 전송
            return new ResponseEntity<>(new ResponseMessageDto(message), HttpStatus.OK);

        } catch (Exception e) {
            log.debug(e.getMessage());   // 로그 확인하는 어노테이션 @Slf4j
            message = "Could not upload the file : " + property.getOriginalFilename();
            return new ResponseEntity<>(new ResponseMessageDto(message),
                    HttpStatus.INTERNAL_SERVER_ERROR);   // 서버 에러 발생 메시지 Vue 로 전송
        }
    }


    // 모든 매물 정보 + 타이틀로 검색하는 가져오는 함수🤩
    // 모든 매물 정보 + 타이틀로 검색하는 가져오는 함수🤩
    // 갤러리 파일 다운로드(조회) 함수_프앤 PropertyUpload.vue에서 이미지경로 data.propertyUrl 실행시 이 함수 사용됨 😵‍
    // url : /api/property/1

    @GetMapping("/property")
    public ResponseEntity<Object> getListFiles(@RequestParam(defaultValue = "Title") String searchSelect,  // 셀렉트박스 선택(필수)
                                               @RequestParam(required = false) String searchKeyword,   // 검색어(선택)
                                               @RequestParam(defaultValue = "0") int page,   // 디폴트값(검색안할시 0페이지)
                                               @RequestParam(defaultValue = "3") int size  // 디폴트값(검색안할시 3페이지씩표시)
    ) {
        // 디버깅
        log.debug("searchSelect :" + searchSelect);
        log.debug("searchKeyword :" + searchKeyword);
        log.debug("page :" + page);
        log.debug("size :" + size);
        if (searchSelect.equals("Title")) {
            try {
                Pageable pageable = PageRequest.of(page, size);
                Page<ResponsePropertyDto> propertyPage = propertyService
                        .findAllByPropertyTitleContaining(searchKeyword, pageable)
                        .map(dbFile -> {
                            // ServletUriComponentsBuilder : URL 만들어주는 클래스
                            String propertyDownloadUri = ServletUriComponentsBuilder
                                    .fromCurrentContextPath() // 갤러리 파일 경로
                                    .path("/api/property/")   // 뒤에 기본키(시퀀스번호)가 들어오기 전 경로는 하드코딩
                                    .path(dbFile.getPno().toString()) // "/api/property/1" 이미지파일 경로를 불러와서 문자로 바꿈
                                    .toUriString(); // 마지막에 호출( URL 완성됨 )
                            ResponsePropertyDto propertyDto = modelMapper.map(dbFile, ResponsePropertyDto.class);
                            if (dbFile.getPropertyData() != null) {
                                propertyDto.setPropertySize(dbFile.getPropertyData().length);   // .length : 갤러리 크기(사이즈)
                            }
                            propertyDto.setPropertyUrl(propertyDownloadUri);
                            return propertyDto;
                        });
                Map<String, Object> response = new HashMap<>();
                response.put("property", propertyPage.getContent());
                response.put("currentPage", propertyPage.getNumber());
                response.put("totalItems", propertyPage.getTotalElements());
                response.put("totalPages", propertyPage.getTotalPages());

                // list배열이 비어있지 않으면
                if (propertyPage.isEmpty() == false) {
                    //                           데이터 + 성공 메시지 전송
                    return new ResponseEntity<>(response, HttpStatus.OK);
                } else {
                    // 데이터 없음 메시지 클라이언트에게 전송
                    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                }

            } catch (Exception e) {
                log.debug(e.getMessage());
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);  // 서버 에러 발생 메시지 전송(클라이언트로 전송)
            }
        }
        if (searchSelect.equals("Price")) {
            try {
                Pageable pageable = PageRequest.of(page, size);
                Page<ResponsePropertyDto> propertyPage = propertyService.findAllByPropertyPriceContaining(searchKeyword, pageable).map(dbFile -> {
                    // ServletUriComponentsBuilder : URL 만들어주는 클래스
                    String propertyDownloadUri = ServletUriComponentsBuilder
                            .fromCurrentContextPath() // 갤러리 파일 경로
                            .path("/api/property/")   // 뒤에 기본키(시퀀스번호)가 들어오기 전 경로는 하드코딩
                            .path(dbFile.getPno().toString()) // "/api/property/1" 이미지파일 경로를 불러와서 문자로 바꿈
                            .toUriString(); // 마지막에 호출( URL 완성됨 )
                    ResponsePropertyDto propertyDto = modelMapper.map(dbFile, ResponsePropertyDto.class);
                    if (dbFile.getPropertyData() != null) {
                        propertyDto.setPropertySize(dbFile.getPropertyData().length);   // .length : 갤러리 크기(사이즈)
                    }
                    propertyDto.setPropertyUrl(propertyDownloadUri);
                    return propertyDto;
                });
                Map<String, Object> response = new HashMap<>();
                response.put("property", propertyPage.getContent());
                response.put("currentPage", propertyPage.getNumber());
                response.put("totalItems", propertyPage.getTotalElements());
                response.put("totalPages", propertyPage.getTotalPages());

                // list배열이 비어있지 않으면
                if (propertyPage.isEmpty() == false) {
                    //                           데이터 + 성공 메시지 전송
                    return new ResponseEntity<>(response, HttpStatus.OK);
                } else {
                    // 데이터 없음 메시지 클라이언트에게 전송
                    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                }

            } catch (Exception e) {
                log.debug(e.getMessage());
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);  // 서버 에러 발생 메시지 전송(클라이언트로 전송)
            }

        } else if (searchSelect.equals("City")) {
            try {
                Pageable pageable = PageRequest.of(page, size);
                Page<ResponsePropertyDto> propertyPage = propertyService.findAllByPropertyCityContaining(searchKeyword, pageable).map(dbFile -> {
                    // ServletUriComponentsBuilder : URL 만들어주는 클래스
                    String propertyDownloadUri = ServletUriComponentsBuilder
                            .fromCurrentContextPath() // 갤러리 파일 경로
                            .path("/api/property/")   // 뒤에 기본키(시퀀스번호)가 들어오기 전 경로는 하드코딩
                            .path(dbFile.getPno().toString()) // "/api/property/1" 이미지파일 경로를 불러와서 문자로 바꿈
                            .toUriString(); // 마지막에 호출( URL 완성됨 )
                    ResponsePropertyDto propertyDto = modelMapper.map(dbFile, ResponsePropertyDto.class);
                    if (dbFile.getPropertyData() != null) {
                        propertyDto.setPropertySize(dbFile.getPropertyData().length);   // .length : 갤러리 크기(사이즈)
                    }
                    propertyDto.setPropertyUrl(propertyDownloadUri);
                    return propertyDto;
                });
                Map<String, Object> response = new HashMap<>();
                response.put("property", propertyPage.getContent());
                response.put("currentPage", propertyPage.getNumber());
                response.put("totalItems", propertyPage.getTotalElements());
                response.put("totalPages", propertyPage.getTotalPages());

                // list배열이 비어있지 않으면
                if (propertyPage.isEmpty() == false) {
                    //                           데이터 + 성공 메시지 전송
                    return new ResponseEntity<>(response, HttpStatus.OK);
                } else {
                    // 데이터 없음 메시지 클라이언트에게 전송
                    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                }
            } catch (Exception e) {
                log.debug(e.getMessage());
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);  // 서버 에러 발생 메시지 전송(클라이언트로 전송)
            }
            // 맵 자료구조에 넣어서 전송
        }
        else {return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);}

    }

    // 갤러리 파일 다운로드(조회) 함수_프앤 PropertyUpload.vue에서 이미지경로 data.propertyUrl 실행시 이 함수 사용됨 😵‍
    // url : /api/property/1
    @GetMapping("/property/{pno}")
    public ResponseEntity<byte[]> getFile(@PathVariable int pno) {

        // id 로 조회하는 함수
        Property property = propertyService.getFile(pno).get();

        // 첨부파일 다운로드 : url Content-Type 규칙에 맞춰야 함
        return ResponseEntity.ok()
                .body(property.getPropertyData());  // 생성자 propertyData 의 getter함수 -> propertyData() : 갤러리 첨부파일 데이터
    }


    // pno로 이미지파일 삭제 함수
    @DeleteMapping("/property/deletion/{pno}")
    public ResponseEntity<Object> deleteProperty(@PathVariable int pno) {

        try {
            boolean bSuccess = propertyService.removeById(pno);

            if (bSuccess == true) {
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

        } catch (Exception e) {
            log.debug(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}



