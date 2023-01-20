package com.example.simpledms.service;

import com.example.simpledms.model.FileDb;
import com.example.simpledms.repository.FIleDbRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;


/**
 * packageName : com.example.jpaexam.service.exam01
 * fileName : DeptService
 * author : juhee
 * date : 2022-11-01
 * description : 부서 업무 서비스 클래스
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * —————————————————————————————
     * 2022-10-20         juhee          최초 생성
 */
@Service
public class FileDbService {
    // 객체 생성
    @Autowired
    FIleDbRepository fIleDbRepository;  // JPA CRUD 함수가 있는 인터페이스 객체

    // 모든 파일 조회 함수 : findAllFiles()
    // fIleDbRepository. (점) 하면 JPA의 기본함수들 불러서 쓸 수 있음
    public Page<FileDb> findAllFiles(Pageable pageable){
        Page<FileDb> page = fIleDbRepository.findAll(pageable);

        return page;
    }
    
    // ID(기본키)로 파일 조회 함수 : findById(기본키)_JPA 제공 기본함수
    // 한 건만 나오니까 페이징처리 의미 없음
    // .findById 의 리턴값은 optional!!(null체크)
    public Optional<FileDb> getFile(int id){
        Optional<FileDb> optionalFileDb= fIleDbRepository.findById(id);

        return optionalFileDb;
    }

    // fileTitle(이미지명)으로 like 검색 함수 : findAllByFileTitleContaining()
    // 여러건 -> 페이징처리
    public Page<FileDb> findAllByFileTitleContaining(String fileTitle, Pageable pageable){
        Page<FileDb> page = fIleDbRepository
                            .findAllByFileTitleContaining(fileTitle, pageable);

        return page;
    }


    // ID(기본키)로 파일 삭제 함수 : 한 건만 삭제됨(기본키는 중복이 없으니까)
    // deleteById(기본키)_JPA 제공 기본함수(리턴값 없음 -> 리턴하고싶어. 조건문 사용)
    public boolean removeById(int id){

        if(fIleDbRepository.existsById(id) == true){   // 검색한 id가 존재하면
            fIleDbRepository.deleteById(id);    // 삭제 실행
            return true;
        }
        return false;
    }

    // 👀
    // 이미지 업로드를 위한 특수 함수
    // 이미지 저장 함수 store() 😦
    // 프엔 함수 upload(title, content, fileDb)
    //         "Content-Type" : "multipart/form-data" 으로 보냄(multipart -> MultipartFile 로 받음
    public FileDb store(String fileTitle,    // 이미지 제목
                        String fileContent,  // 이미지 내용
                        MultipartFile file) throws IOException {    // 실제 이미지_ MultipartFIle 예외처리 필요

        // path(폴더경로) 제거 후 순수한 fileName 만 가져오기
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        // 1. FileDb 모델클래스에 만든 생성자에 경로 등 여러 정보를 저장_생성자 순서대로!!
        FileDb fileDb = new FileDb(fileTitle,
                                   fileContent,
                                   fileName,
                                   file.getContentType(),   // 이미지의 타입 정보(.jpb .png .jpeg.. )
                                   file.getBytes());        // 이미지의 크기(size)
        
        // 2. 위의 FileDb 를 DB에 저장(.save()함수) + return 
        return fIleDbRepository.save(fileDb);
    }


}
