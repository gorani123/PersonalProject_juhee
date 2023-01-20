package com.example.simpledms.service;

import com.example.simpledms.model.Property;
import com.example.simpledms.repository.PropertyRepository;
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
public class PropertyService {
    // 객체 생성
    @Autowired
    PropertyRepository propertyRepository;  // JPA CRUD 함수가 있는 인터페이스 객체

    // 모든 파일 조회 함수 : findAllFiles()
    // fIleDbRepository. (점) 하면 JPA의 기본함수들 불러서 쓸 수 있음
    public Page<Property> findAllFiles(Pageable pageable){
        Page<Property> page = propertyRepository.findAll(pageable);

        return page;
    }

    // ID(기본키)로 파일 조회 함수 : findById(기본키)_JPA 제공 기본함수
    // 한 건만 나오니까 페이징처리 의미 없음
    // .findById 의 리턴값은 optional!!(null체크)
    public Optional<Property> getFile(int pno){
        Optional<Property> optionalProperty= propertyRepository.findById(pno);

        return optionalProperty;
    }

    // propertyTitle(이미지명)으로 like 검색 함수 : findAllByFileTitleContaining()
    // 여러건 -> 페이징처리
    public Page<Property> findAllByPropertyTitleContaining(String propertyTitle, Pageable pageable){
        Page<Property> page = propertyRepository
                            .findAllByPropertyTitleContaining(propertyTitle, pageable);
        return page;
    }

    // propertyPrice 로 like 검색 함수 : findAllByFileTitleContaining()
    // 여러건 -> 페이징처리
    public Page<Property> findAllByPropertyPriceContaining(String propertyPrice, Pageable pageable){
        Page<Property> page = propertyRepository
                            .findAllByPropertyPriceContaining(propertyPrice, pageable);
        return page;
    }

    // propertyCity 로 like 검색 함수 : findAllByFileTitleContaining()
    // 여러건 -> 페이징처리
    public Page<Property> findAllByPropertyCityContaining(String propertyCity, Pageable pageable){
        Page<Property> page = propertyRepository
                            .findAllByPropertyCityContaining(propertyCity, pageable);
        return page;
    }


    // ID(기본키)로 파일 삭제 함수 : 한 건만 삭제됨(기본키는 중복이 없으니까)
    // deleteById(기본키)_JPA 제공 기본함수(리턴값 없음 -> 리턴하고싶어. 조건문 사용)
    public boolean removeById(int pno){

        if(propertyRepository.existsById(pno) == true){   // 검색한 pno가 존재하면
            propertyRepository.deleteById(pno);    // 삭제 실행
            return true;
        }
        return false;
    }

    // 👀
    // 이미지 업로드를 위한 특수 함수
    // 이미지 저장 함수 store() 😦
    // 프엔 함수 upload(title, content, fileDb)
    //         "Content-Type" : "multipart/form-data" 으로 보냄(multipart -> MultipartFile 로 받음
    public Property store(String propertyTitle, MultipartFile file, String propertyPrice, String propertyAddress, String propertyCity, Integer propertyBed, Integer propertyBath    // 이미지 제목
                          ) throws IOException {    // 실제 이미지_ MultipartFIle 예외처리 필요

        // path(폴더경로) 제거 후 순수한 fileName 만 가져오기
        String propertyName = StringUtils.cleanPath(file.getOriginalFilename());

        // 1. Property 모델클래스에 만든 생성자에 경로 등 여러 정보를 저장_생성자 순서대로!!
        Property property = new Property(propertyTitle,         // 새로운 객체 Gallery의 생성자들 : propertyTitle, propertyName, file.getContentType(), file.getBytes()
                                   propertyName,
                                   file.getContentType(),   // 이미지의 타입 정보(.jpb .png .jpeg.. )
                                   file.getBytes(),
                propertyPrice, propertyAddress, propertyCity, propertyBed, propertyBath
                );        // 이미지의 크기(size)

        // 2. 위의 Gallery 를 DB에 저장(.save()함수) + return
        return propertyRepository.save(property);
    }


}
