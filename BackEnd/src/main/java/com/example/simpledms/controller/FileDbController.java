package com.example.simpledms.controller;

import com.example.simpledms.dto.filedb.ResponseFileDto;
import com.example.simpledms.dto.filedb.ResponseMessageDto;
import com.example.simpledms.model.FileDb;
import com.example.simpledms.service.FileDbService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
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
 * description : fileDb 컨트롤러 (@RestController) -
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * —————————————————————————————
 * 2022-10-21         juhee          최초 생성
 */
@Slf4j
@RestController
@RequestMapping("/api")
public class FileDbController {

    @Autowired
    FileDbService fileDbService;

    // Model to Dto 자동 변환(Dto to Model ) 외부 라이브러리(build.gradle 에 설치)
    ModelMapper modelMapper = new ModelMapper();


    // 👀
    // 이미지 업로드(db저장)를 위한 컨트롤러 함수 😦
    // 프엔    formData.append("fileTitle", title);
    //        formData.append("fileContent", content);
    //        formData.append("fileDb", fileDb);
    // @RequestParam / @PathVariable
    @PostMapping("/fileDb/upload")
    public ResponseEntity<Object> fileDbUploadFile(@RequestParam("fileTitle") String fileTitle,
                                                   @RequestParam("fileContent") String fileContent,
                                                   @RequestParam("fileDb") MultipartFile fileDb
    ) {

        String message = ""; // front-end 로 전송할 메세지

        // 디버깅 출력
        log.debug("fileTitle :" + fileTitle);
        log.debug("fileContent :" + fileContent);
        log.debug("fileDb :" + fileDb);

        try {
            // db 저장 함수 호출 .store()
            fileDbService.store(fileTitle, fileContent, fileDb);

            // 메시지 내용
            message = "Upload the file successfully: " + fileDb.getOriginalFilename();

            // message를 객체 생성자 형태로 Dto에 담아서 보내기 + 성공 메시지 OK 전송
            return new ResponseEntity<>(new ResponseMessageDto(message), HttpStatus.OK);

        } catch (Exception e) {
            log.debug(e.getMessage());   // 로그 확인하는 어노테이션 @Slf4j
            message = "Could not upload the file : " + fileDb.getOriginalFilename();
            return new ResponseEntity<>(new ResponseMessageDto(message),
                    HttpStatus.INTERNAL_SERVER_ERROR);   // 서버 에러 발생 메시지 Vue 로 전송
        }
    }


    // 모든 이미지 정보를 가져오는 함수🤩
    @GetMapping("/fileDb")
    public ResponseEntity<Object> getListFiles(@RequestParam(required = false) String fileTitle,
                                               @RequestParam(defaultValue = "0") int page,   // 디폴트값(검색안할시 0페이지)
                                               @RequestParam(defaultValue = "3") int size  // 디폴트값(검색안할시 3페이지씩표시)
    ) {

        try {
            // Pageable 객체 정의 ( page, size 값 설정 )
            Pageable pageable = PageRequest.of(page, size);

            // Upload 이미지 정보를 가져오는 함수👀
            // 리턴타입 fileDb -> ResponseFileDto 로 변경해야 함 => map() 사용_내부적으로 반복문 돌아감(fildAll -> 여러 건, list)
            Page<ResponseFileDto> filePage = fileDbService
                    .findAllByFileTitleContaining(fileTitle, pageable)
                    .map(dbFile -> {
                        // 자동적으로 반복문이 실행됨 : .map() 의 특징
                        // 1) 다운로드 URL 만들기  (url : /api/fileDb/1 이런 형태로 나와야 유저들이 클릭해서 다운 가능)
                        // ServletUriComponentsBuilder : URL 만들어주는 클래스
                        String fileDownloadUri = ServletUriComponentsBuilder
                                .fromCurrentContextPath() // 이미지 파일 경로
                                .path("/api/fileDb/")   // 뒤에 기본키(시퀀스번호)가 들어오기 전 경로는 하드코딩
                                .path(dbFile.getFid().toString()) // "/api/fileDb/1" 이미지파일 경로를 불러와서 문자로 바꿈
                                .toUriString(); // 마지막에 호출( URL 완성됨 )

                        // 2) 뷰로 보내기
                        // modelMapper 를 이용해서 dbFile(모델) 안에 든 fileDb -> ResponseFileDto 로 변환
                        // 속성명이 일치하는 5개만 DTO에 들어감(가공한 2개는 따로..)
                        // modelMapper.map(소스모델, 타겟DTO.class)
                        ResponseFileDto fileDto = modelMapper.map(dbFile, ResponseFileDto.class);

                        // DTO에 남은 가공된 2개 속성 : setter 이용해서 가공된 데이터 저장
                        fileDto.setFileSize(dbFile.getFileData().length);   // .length : 이미지 크기(사이즈)

                        fileDto.setFileUrl(fileDownloadUri);

                        return fileDto;
                    });

            // 맵 자료구조에 넣어서 전송
            Map<String, Object> response = new HashMap<>();
            response.put("fileDb", filePage.getContent());
            response.put("currentPage", filePage.getNumber());
            response.put("totalItems", filePage.getTotalElements());
            response.put("totalPages", filePage.getTotalPages());

            // list배열이 비어있지 않으면
            if (filePage.isEmpty() == false) {
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

    // 이미지 파일 다운로드(조회) 함수_프앤 FileDbUpload.vue에서 이미지경로 data.fileUrl 실행시 이 함수 사용됨 😵‍
    // url : /api/fileDb/1
    @GetMapping("/fileDb/{fid}")
    public ResponseEntity<byte[]> getFile(@PathVariable int fid) {

        // id 로 조회하는 함수
        FileDb fileDb = fileDbService.getFile(fid).get();

        // 첨부파일 다운로드 : url Content-Type 규칙에 맞춰야 함
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileDb.getFileName() + "\"")
                .body(fileDb.getFileData());  // 생성자 fileData 의 getter함수 -> getFileData() : 이미지 첨부파일 데이터
    }


    // fid로 이미지파일 삭제 함수
    @DeleteMapping("/fileDb/deletion/{fid}")
    public ResponseEntity<Object> deleteFileDb(@PathVariable int fid) {

        try {
            boolean bSuccess = fileDbService.removeById(fid);

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



